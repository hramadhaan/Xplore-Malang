package com.xploremalang.xploremalang.Content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xploremalang.xploremalang.Fragment.FeedbackFragment;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.ulasan.DiskusiFeedback;

public class ActivityDetail extends AppCompatActivity{

    TextView deskripsi;
    ImageView fotoKonten;
    CollapsingToolbarLayout mToolbar = null;
    String mId_company = null;

//    INISIALISASI FEEDBACK
    String postId;
    String publisherId;


    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    private Context mContext;
    Button btn_lokasi;

    private void initializeWidgets(){
        deskripsi = findViewById(R.id.tv_deskripsi);
        fotoKonten = findViewById(R.id.fotoKonten);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mContext = ActivityDetail.this;


        ChildEventListener mChildEventListener;
        mUsers = FirebaseDatabase.getInstance().getReference("konten");
        mUsers.push().setValue(marker);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_feedback,
                new FeedbackFragment()).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_diskusi,
                new DiskusiFeedback()).commit();


        initializeWidgets();

        Intent i  = this.getIntent();
        String description = i.getExtras().getString("DESKRIPSI_KONTEN");
        String imageURL = i.getExtras().getString("IMAGE_KONTEN");
        String judul = i.getExtras().getString("JUDUL_KONTEN");

        final String latitude = i.getExtras().getString("LATITUDE_KONTEN");
        final String longitude = i.getExtras().getString("LONGTITUDE_KONTEN");

        btn_lokasi = (Button) findViewById(R.id.btn_lokasi);
        btn_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        mToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        mToolbar.setTitle(judul);

        deskripsi.setText(description);
        Glide.with(this)
                .load(imageURL)
                .centerCrop()
                .into(fotoKonten);

//        MEMBUAT FEEDBACK
    }

}
