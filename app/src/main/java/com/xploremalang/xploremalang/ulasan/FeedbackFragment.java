package com.xploremalang.xploremalang.ulasan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xploremalang.xploremalang.R;

import java.util.HashMap;

public class FeedbackFragment extends Fragment {
    EditText addComment;
    ImageView image_profile;
    TextView post;

    String postId;
    String publisherId;

    FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feedback = inflater.inflate(R.layout.fragment_feedback,container,false);

        addComment = feedback.findViewById(R.id.add_comment);
        image_profile = feedback.findViewById(R.id.image_profile);
        post = feedback.findViewById(R.id.post);

        Intent intent = getActivity().getIntent();
        postId = intent.getStringExtra("postId");
        publisherId = intent.getStringExtra("publisherId");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addComment.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Harap isi Comment",Toast.LENGTH_SHORT).show();
                } else {
                    comentAdd();
                }
            }
        });


        return feedback;
    }

    private void comentAdd() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comment").child(postId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", addComment.getText().toString());
        hashMap.put("publisherId",firebaseUser.getUid());

        reference.push().setValue(hashMap);
        addComment.setText("");
    }

    private void getImage(){

    }

}
