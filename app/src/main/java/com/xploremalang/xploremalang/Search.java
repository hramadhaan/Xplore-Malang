package com.xploremalang.xploremalang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {

    EditText Cari;
    Button submit_search;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        Cari = findViewById(R.id.search_edit);
        submit_search = findViewById(R.id.button_search);

        submit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Search.this,Activity_Utama.class));
            }
        });
    }


}
