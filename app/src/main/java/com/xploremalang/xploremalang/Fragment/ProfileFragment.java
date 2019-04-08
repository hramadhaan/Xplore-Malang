package com.xploremalang.xploremalang.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xploremalang.xploremalang.R;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        ImageView gambar = view.findViewById(R.id.profile_image);
        Glide.with(getContext()).load(user.getPhotoUrl()).into(gambar);

        TextView nama = view.findViewById(R.id.nama);
        nama.setText(Objects.requireNonNull(user).getDisplayName());

        TextView email = view.findViewById(R.id.email);
        email.setText(user.getEmail());


    }
}
