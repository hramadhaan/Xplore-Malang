package com.xploremalang.xploremalang.Comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.AccountActivity.User;
import com.xploremalang.xploremalang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment_Activity extends AppCompatActivity {

    String postId;
    String publisher;

    EditText comment;
    CircleImageView profile;
    TextView post;
    FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);

        Toolbar toolbar = findViewById(R.id.comment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diskusi");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.comment_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,comments);
        recyclerView.setAdapter(commentAdapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        comment = findViewById(R.id.comment_edit_comment);
        profile = findViewById(R.id.comment_image_profile);
        post = findViewById(R.id.comment_post);

        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        publisher = intent.getStringExtra("publisher");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getText().toString().equals("")){
                    Toast.makeText(Comment_Activity.this,"Harap isi teks comment",Toast.LENGTH_SHORT).show();
                } else {
                    addComment();
                }
            }
        });

        getImage();
        readComment();

    }

    private void addComment() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postId);

        HashMap <String,Object> hashMap = new HashMap<>();
        hashMap.put("comment",comment.getText().toString());
        hashMap.put("publisher",firebaseUser.getUid());

        reference.push().setValue(hashMap);
        comment.setText("");

    }

    private void getImage(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext())
                        .load(user.getImageurl()).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readComment(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Comment comment = snapshot.getValue(Comment.class);
                    comments.add(comment);
                }
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
