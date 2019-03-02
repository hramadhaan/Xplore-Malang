package com.xploremalang.xploremalang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.UploadFoto.Upload;
import com.xploremalang.xploremalang.UploadFoto.UploadActivity;

public class FeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View van = inflater.inflate(R.layout.fragment_feed,container,false);

        FloatingActionButton fab1 = (FloatingActionButton) van.findViewById(R.id.fab_action1);
        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Anda Memilih Ambil Foto",Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) van.findViewById(R.id.fab_action2);
        fab2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent keUpload = new Intent(getActivity(), UploadActivity.class);
                startActivity(keUpload);
            }
        });

        return van;
    }

    public void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
