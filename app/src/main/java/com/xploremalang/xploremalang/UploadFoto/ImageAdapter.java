package com.xploremalang.xploremalang.UploadFoto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.xploremalang.xploremalang.AccountActivity.User;
import com.xploremalang.xploremalang.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewholder> {

    public Context mContext;
    public List<Upload> mPost;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;

    public ImageAdapter(Context mContext, List<Upload> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,viewGroup,false);
        return new ImageViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewholder imageViewholder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Upload upload = mPost.get(i);
        Glide.with(mContext).load(upload.getImageFeed()).into(imageViewholder.upload_foto);

        if (upload.getDeskripsi().equals("")){
            imageViewholder.upload_deskripsi.setVisibility(View.GONE);
        } else {
            imageViewholder.upload_deskripsi.setVisibility(View.VISIBLE);
            imageViewholder.upload_deskripsi.setText(upload.getDeskripsi());
        }

        publisherInfo(imageViewholder.upload_akun,imageViewholder.upload_nama,upload.getPublisher());
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {
        public ImageView upload_foto;
        public CircleImageView upload_akun;
        public TextView upload_nama,upload_deskripsi;

        public ImageViewholder(@NonNull View itemView) {
            super(itemView);

            upload_akun = itemView.findViewById(R.id.upload_akun);
            upload_foto = itemView.findViewById(R.id.upload_foto);
            upload_nama = itemView.findViewById(R.id.upload_nama);
            upload_deskripsi = itemView.findViewById(R.id.upload_deskripsi);

        }
    }

    private void publisherInfo (final CircleImageView upload_akun, final TextView upload_nama, final String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user =dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(upload_akun);
                upload_nama.setText(user.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
