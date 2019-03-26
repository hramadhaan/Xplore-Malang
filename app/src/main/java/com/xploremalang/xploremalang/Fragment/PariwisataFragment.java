package com.xploremalang.xploremalang.Fragment;

import android.support.v4.app.Fragment;

import com.xploremalang.xploremalang.Content.UploadListAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xploremalang.xploremalang.BuildConfig;
import com.xploremalang.xploremalang.Content.ActivityDetail;
import com.xploremalang.xploremalang.Content.IsiKonten;
import com.xploremalang.xploremalang.Content.TambahKonten;
import com.xploremalang.xploremalang.Content.UploadListAdapter;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.UploadFoto.ImageAdapter;
import com.xploremalang.xploremalang.Utilities.ServiceHelper;
import com.xploremalang.xploremalang.Utilities.WeatherEndpoint;
import com.xploremalang.xploremalang.Weather.data.OpenWeatherMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PariwisataFragment extends Fragment implements UploadListAdapter.OnItemClickListener {

    private UploadListAdapter.OnItemClickListener listener;
    private RecyclerView mRecyclerView;
    private UploadListAdapter mAdapter;
    private WeatherEndpoint helper = new ServiceHelper().getWeatherService();
    private ProgressBar mProgressBar;
    private DatabaseReference mDatabaseRef;
    private FirebaseStorage mStorage;

    private List<IsiKonten> mUpload = new ArrayList<>();

    private void openDetailActivity(String[] data) {
        Intent intent = new Intent(getContext(),ActivityDetail.class);
        intent.putExtra("JUDUL_KONTEN",data[0]);
        intent.putExtra("DESKRIPSI_KONTEN",data[1]);
        intent.putExtra("LATITUDE_KONTEN",data[2]);
        intent.putExtra("LONGTITUDE_KONTEN",data[3]);
        intent.putExtra("NAMA_KONTEN",data[4]);
        intent.putExtra("IMAGE_KONTEN",data[5]);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home,container,false);

        FloatingActionButton fab_home = home.findViewById(R.id.floating_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                INI BAKAL PINDAH KE ACTIVITY MENAMBAHKAN BARANG KE FIREBASE
                Intent keTambahKonten = new Intent(getActivity(), TambahKonten.class);
                startActivity(keTambahKonten);
            }
        });

        listener = this;
        mRecyclerView = home.findViewById(R.id.rv_home);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressBar = home.findViewById(R.id.barProgressHome);
        mProgressBar.setVisibility(View.VISIBLE);

        mUpload = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("konten");
        Query query = FirebaseDatabase.getInstance().getReference("konten")
                .orderByChild("namaKonten")
                .equalTo("Pariwisata");
        query.addListenerForSingleValueEvent(valueEventListener);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    IsiKonten isiKonten = postSnapshot.getValue(IsiKonten.class);
                    mUpload.add(isiKonten);
                }
                mAdapter = new UploadListAdapter(getActivity(),mUpload, listener);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(getView().INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return home;
    }

    @Override
    public void onItemClick(int position) {
        IsiKonten isiKonten = mUpload.get(position);
        String[] kontenData = {isiKonten.getJudul(),isiKonten.getDeskripsi(),isiKonten.getLatitude(),isiKonten.getLatitude(),isiKonten.getNamaKonten(),isiKonten.getmImageUrl()};
        openDetailActivity(kontenData);
    }

    @Override
    public void onShowItemClick(int position) {
        IsiKonten isiKonten = mUpload.get(position);
        String[] kontenData = {isiKonten.getJudul(),isiKonten.getDeskripsi(),isiKonten.getLatitude(),isiKonten.getLatitude(),isiKonten.getNamaKonten(),isiKonten.getmImageUrl()};
        openDetailActivity(kontenData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        IsiKonten selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getActivity(),"ItemDeleted",Toast.LENGTH_SHORT).show();
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUpload.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    IsiKonten isikonten = snapshot.getValue(IsiKonten.class);
                    mUpload.add(isikonten);
                }
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}
