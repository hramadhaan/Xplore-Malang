package com.xploremalang.xploremalang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xploremalang.xploremalang.Content.IsiKonten;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    EditText edit_search;
    Button search_button;
    RecyclerView search_recyclerview;
    SearchAdapter searchAdapter;
    List<IsiKonten> isiKontens;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);

        edit_search = findViewById(R.id.search_edit);
        search_button = findViewById(R.id.button_search);
        search_recyclerview = findViewById(R.id.search_recycler);

        edit_search.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        search_recyclerview.setHasFixedSize(true);
        search_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        isiKontens = new ArrayList<>();
        searchAdapter = new SearchAdapter(getApplicationContext(),isiKontens);
        search_recyclerview.setAdapter(searchAdapter);

        readList();
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchList(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchList(final String s){
        Query query = FirebaseDatabase.getInstance().getReference("Konten").orderByChild("Wisata")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (edit_search.getText().toString().equals(s)){
                   isiKontens.clear();
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                       IsiKonten isiKonten = snapshot.getValue(IsiKonten.class);
                       isiKontens.add(isiKonten);
                   }
                   searchAdapter.notifyDataSetChanged();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readList(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Konten");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (edit_search.getText().toString().equals("")){
                    isiKontens.clear();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        IsiKonten isiKonten = snapshot.getValue(IsiKonten.class);
                        isiKontens.add(isiKonten);
                    }
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
