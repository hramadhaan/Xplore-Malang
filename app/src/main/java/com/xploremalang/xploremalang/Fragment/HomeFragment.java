package com.xploremalang.xploremalang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.Content.IsiKonten;
import com.xploremalang.xploremalang.Content.TambahKonten;
import com.xploremalang.xploremalang.Content.UploadListAdapter;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.UploadFoto.Upload;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{

    private RecyclerView recyclerView;
    private UploadListAdapter uploadListAdapter;
    private Spinner spinner;
    private ProgressBar progressBar;

    private DatabaseReference databaseReference;
    private List<IsiKonten> isiKontens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View van = inflater.inflate(R.layout.fragment_home,container,false);

        FloatingActionButton fab = (FloatingActionButton)van.findViewById(R.id.floating_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konten = new Intent(getActivity(), TambahKonten.class);
                startActivity(konten);
            }
        });



        recyclerView = van.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = van.findViewById(R.id.barProgressHome);

        isiKontens = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Konten");

        spinner = van.findViewById(R.id.spinner_home);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        semua();
                        break;
                    case 1:
                        pariwisata();
                        break;
                    case 2:
                        kuliner();
                        break;
                    case 3:
                        oleholeh();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return van;
    }

    private void oleholeh() {
        Query oleh = FirebaseDatabase.getInstance().getReference("Konten").orderByChild("jenis_konten").equalTo("Oleh Oleh");
        oleh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isiKontens.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    IsiKonten isiKonten = postSnapshot.getValue(IsiKonten.class);
                    isiKontens.add(isiKonten);
                    progressBar.setVisibility(getView().INVISIBLE);

                }
                uploadListAdapter = new UploadListAdapter(getActivity(),isiKontens);
                recyclerView.setAdapter(uploadListAdapter);
                uploadListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void kuliner() {

        Query kuli = FirebaseDatabase.getInstance().getReference("Konten").orderByChild("jenis_konten").equalTo("Kuliner");
        kuli.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isiKontens.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    IsiKonten isiKonten = postSnapshot.getValue(IsiKonten.class);

                    isiKontens.add(isiKonten);
                    progressBar.setVisibility(getView().INVISIBLE);

                }
                uploadListAdapter = new UploadListAdapter(getActivity(),isiKontens);
                recyclerView.setAdapter(uploadListAdapter);
                uploadListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void pariwisata() {

        Query pari = FirebaseDatabase.getInstance().getReference("Konten").orderByChild("jenis_konten").equalTo("Pariwisata");
        pari.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isiKontens.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    IsiKonten isiKonten = postSnapshot.getValue(IsiKonten.class);

                    isiKontens.add(isiKonten);
                    progressBar.setVisibility(getView().INVISIBLE);

                }
                uploadListAdapter = new UploadListAdapter(getActivity(),isiKontens);
                recyclerView.setAdapter(uploadListAdapter);
                uploadListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void semua(){
        Query semua = FirebaseDatabase.getInstance().getReference("Konten");
        semua.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isiKontens.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    IsiKonten isiKonten = postSnapshot.getValue(IsiKonten.class);
                    isiKontens.add(isiKonten);
                    progressBar.setVisibility(getView().INVISIBLE);

                }
                uploadListAdapter = new UploadListAdapter(getActivity(),isiKontens);
                recyclerView.setAdapter(uploadListAdapter);
                uploadListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}