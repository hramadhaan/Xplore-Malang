package com.xploremalang.xploremalang.Content;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.Resource;
import com.xploremalang.xploremalang.R;

public class ActivityDetail extends AppCompatActivity {

    TextView deskripsi;
    ImageView fotoKonten;
    String judul;
    CollapsingToolbarLayout mToolbar = null;
    String mId_company = null;

    private void initializeWidgets(){
        deskripsi = findViewById(R.id.tv_deskripsi);
        fotoKonten = findViewById(R.id.fotoKonten);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

    }
}
