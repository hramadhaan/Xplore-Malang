package com.xploremalang.xploremalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xploremalang.xploremalang.AccountActivity.LoginActivity;
import com.xploremalang.xploremalang.UploadFoto.UploadActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread (){
            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();

                }
            }
        };
        myThread.start();
    }
}
