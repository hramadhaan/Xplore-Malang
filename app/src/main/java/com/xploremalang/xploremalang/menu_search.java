package com.xploremalang.xploremalang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class menu_search  extends AppCompatActivity {


    private EditText mSearchField;
    private Button mSearchBtn;
    private DatabaseReference mUserDatabase;
    private RecyclerView mResultList;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Konten").child(firebaseUser.getUid());


        mSearchField = (EditText) findViewById(R.id.search_edit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mSearchBtn = (Button) findViewById(R.id.button_search);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(menu_search.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("wisata").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<get_search, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<get_search, UsersViewHolder>(

                get_search.class,
                R.layout.fragment_home,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, get_search model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getNamakonten(), model.getDeksripsikonten(),model.getImage());

            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    public void clickExit(View view) {
        startActivity(new Intent(menu_search.this,Activity_Utama.class));
    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String namakonten, String deksripsikonten, String userImage){

            TextView nama_konten = (TextView) mView.findViewById(R.id.nama_konten);
            TextView deksripsi_konten = (TextView) mView.findViewById(R.id.deskripsi_konten);
            ImageView imageView = (ImageView) mView.findViewById(R.id.fotoKonten);


            nama_konten.setText(namakonten);
            deksripsi_konten.setText(deksripsikonten);

            Glide.with(ctx).load(userImage).into(imageView);


        }




    }


}
