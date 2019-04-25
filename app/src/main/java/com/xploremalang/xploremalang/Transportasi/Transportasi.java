package com.xploremalang.xploremalang.Transportasi;

import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xploremalang.xploremalang.R;

public class Transportasi extends AppCompatActivity {

    private static final String TAG = "Transportasi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportasi);

        ImageView showDialogButton1 = findViewById(R.id.macito);
        showDialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        ImageView showDialogButton2 = findViewById(R.id.ojol);
        showDialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                showDialog2();
            }
        });
        ImageView showDialogButton3 = findViewById(R.id.motor);
        showDialogButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view3) {
                showDialog3();
            }
        });
        ImageView showDialogButton4 = findViewById(R.id.mobil);
        showDialogButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view4) {
                showDialog4();
            }
        });

        ImageView showDialogButton5 = findViewById(R.id.angkutan);
        showDialogButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view5) {
                showDialog5();
            }
        });
        ImageView showDialogButton6 = findViewById(R.id.becak);
        showDialogButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view6) {
                showDialog6();
            }
        });
    }

    protected void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.macito, null);

        Button cancelButton = view.findViewById(R.id.cancelButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        alertDialog.show();


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );
//                dismissDialog();
                alertDialog.dismiss();
            }
        });

    }

    protected void showDialog2() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view2 = inflater.inflate(R.layout.ojol, null);

        Button cancelButton = view2.findViewById(R.id.cancelButton);


        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view2)
                .create();

        alertDialog.show();



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button");
//   dismissDialog();
                alertDialog.dismiss();
            }
        });
    }

    protected void showDialog3() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view3 = inflater.inflate(R.layout.rental_motor, null);


        Button cancelButton = view3.findViewById(R.id.cancelButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view3)
                .create();

        alertDialog.show();



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );
//                dismissDialog();
                alertDialog.dismiss();
            }
        });

    }
    protected void showDialog4() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view4 = inflater.inflate(R.layout.rental_mobil, null);


        Button cancelButton = view4.findViewById(R.id.cancelButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view4)
                .create();

        alertDialog.show();


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );
//                dismissDialog();
                alertDialog.dismiss();
            }
        });

    }

    protected void showDialog5() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view5 = inflater.inflate(R.layout.angkutan, null);


        Button cancelButton = view5.findViewById(R.id.cancelButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view5)
                .create();

        alertDialog.show();


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );
//                dismissDialog();
                alertDialog.dismiss();
            }
        });

    }
    protected void showDialog6() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view6 = inflater.inflate(R.layout.becak, null);


        Button cancelButton = view6.findViewById(R.id.cancelButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view6)
                .create();

        alertDialog.show();




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button" );
//                dismissDialog();
                alertDialog.dismiss();
            }
        });

    }
}