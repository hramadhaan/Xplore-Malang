package com.xploremalang.xploremalang.AccountActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xploremalang.xploremalang.R;

public class Search extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void clickExit(View v){
        moveTaskToBack(true);
        System.exit(1);
    }
}
