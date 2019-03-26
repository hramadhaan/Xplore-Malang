package com.xploremalang.xploremalang.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.R;

public class FeedbackFragment extends Fragment {
    private String rateId, UserId, Content, currentUserId, userContent, ContentId;

    private RatingBar mRating;
    private TextView txtRating;
    private Button btnSubmit;
    private DatabaseReference historyRatingIndoDB;
    private DatabaseReference mRatingDb;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.activity_feedback,container,false);

        mRating = (RatingBar) home.findViewById(R.id.ratingBar);

        historyRatingIndoDB = FirebaseDatabase.getInstance().getReference().child("feedback").child("rating");
        getRateInformation();

        return home;

//        private void displayCustomerRelatedObjects(){
//            mRating.setVisibility(View.VISIBLE);
//            mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//
//                }
//            });
        }


    private ViewStructure getIntent() {
        return null;
    }

    private void getRateInformation() {
        historyRatingIndoDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot child:dataSnapshot.getChildren()){
                        if (child.getKey().equals("Users")){
                            UserId = child.getValue().toString();
                            if (!UserId.equals(currentUserId)){
                                userContent = "Content";
                                getUserInformation("Users", UserId);
                            }
                        }
                        if (child.getKey().equals("Content")){
                            ContentId = child.getValue().toString();
                            if(!ContentId.equals(currentUserId)){
                                userContent = "Users";
                                getUserInformation("Content", ContentId);
                                displayUsersRelatedObjects();
                            }
                        }
                        if (child.getKey().equals("rating")){
                            mRating.setRating(Integer.valueOf(child.getValue().toString()));
                        }
                    }
                }
            }

            private void displayUsersRelatedObjects() {
                mRating.setVisibility(View.VISIBLE);
                mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        historyRatingIndoDB.child("rating").setValue(rating);
                        DatabaseReference mRatingDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Content").child(ContentId).child("rating");
                        mRatingDb.child(rateId).setValue(rating);
                    }
                });
            }

            private void getUserInformation(String users, String userId) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
