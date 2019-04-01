package com.xploremalang.xploremalang.Content;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.xploremalang.xploremalang.Comment.Comment_Activity;
import com.xploremalang.xploremalang.R;

import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ImageViewHolder>{

    public Context mContext;
    public List<IsiKonten> mPost;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;

    public UploadListAdapter(Context mContext, List<IsiKonten> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_model, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final IsiKonten uploadCurrent = mPost.get(i);
        imageViewHolder.wisata_model.setText(uploadCurrent.getWisata());
        imageViewHolder.deskripsi_model.setText(uploadCurrent.getDescription());
        Glide.with(mContext)
                .load(uploadCurrent.getPostImage())
                .fitCenter()
                .centerCrop()
                .into(imageViewHolder.image_model);

        isLiked(uploadCurrent.getPostId(),imageViewHolder.likes);
        nrLikes(imageViewHolder.count_like,uploadCurrent.getPostId());
        getComments(uploadCurrent.getPostId(),imageViewHolder.count_comment);

        imageViewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewHolder.likes.getTag().equals("Like")){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes")
                            .child(uploadCurrent.getPostId())
                            .child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Likes")
                            .child(uploadCurrent.getPostId())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        imageViewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Comment_Activity.class);
                intent.putExtra("postId",uploadCurrent.getPostId());
                intent.putExtra("publisherId",uploadCurrent.getPublisher());
                mContext.startActivity(intent);
            }
        });

        imageViewHolder.count_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Comment_Activity.class);
                intent.putExtra("postId",uploadCurrent.getPostId());
                intent.putExtra("publisherId",uploadCurrent.getPublisher());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_model,likes,comment;
        public TextView wisata_model,deskripsi_model,count_like,count_comment;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            count_like = itemView.findViewById(R.id.count_like);
            image_model = itemView.findViewById(R.id.image_model);
            wisata_model = itemView.findViewById(R.id.wisata_model);
            deskripsi_model = itemView.findViewById(R.id.deskripsi_model);
            likes = itemView.findViewById(R.id.like_model);
            comment = itemView.findViewById(R.id.comment_model);
            count_comment = itemView.findViewById(R.id.count_comment);

        }
    }

    private void getComments(String postId, final TextView count_comment){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count_comment.setText("View all"+dataSnapshot.getChildrenCount()+"comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isLiked(String postId, final ImageView imageView){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_clicked_like);
                    imageView.setTag("Liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_heart);
                    imageView.setTag("Like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void nrLikes(final TextView count_like, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count_like.setText(dataSnapshot.getChildrenCount()+" likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}