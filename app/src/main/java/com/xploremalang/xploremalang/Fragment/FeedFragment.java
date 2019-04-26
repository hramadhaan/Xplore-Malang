package com.xploremalang.xploremalang.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.UploadFoto.ImageAdapter;
import com.xploremalang.xploremalang.UploadFoto.Upload;
import com.xploremalang.xploremalang.UploadFoto.UploadActivity;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;


    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View van = inflater.inflate(R.layout.fragment_feed,container,false);
        

        FloatingActionButton fab2 = (FloatingActionButton) van.findViewById(R.id.fab_action2);
        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent keUpload = new Intent(getActivity(), UploadActivity.class);
                startActivity(keUpload);
            }
        });

        mRecyclerView = van.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //        PORTRAIT
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
            gridLayoutManager.setReverseLayout(true);
//            gridLayoutManager.setStackFromEnd(true);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }



        mProgressCircle = van.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Feeds");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload  = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(getActivity(),mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(getView().INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return van;
    }

    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
