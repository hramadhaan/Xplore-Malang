package com.xploremalang.xploremalang.Content;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.Resource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.Fragment.HomeFragment;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.ulasan.FeedbackFragment;

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



        initializeWidgets();

        Intent i  = this.getIntent();
        String description = i.getExtras().getString("DESKRIPSI_KONTEN");
        String imageURL = i.getExtras().getString("IMAGE_KONTEN");
        String judul = i.getExtras().getString("JUDUL_KONTEN");

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
