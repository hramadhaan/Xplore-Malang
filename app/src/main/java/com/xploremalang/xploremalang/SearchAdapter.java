package com.xploremalang.xploremalang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xploremalang.xploremalang.Content.IsiKonten;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context mContext;
    private List<IsiKonten> mKonten;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    public SearchAdapter(Context mContext, List<IsiKonten> mKonten) {
        this.mContext = mContext;
        this.mKonten = mKonten;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_list,viewGroup,false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        reference = FirebaseDatabase.getInstance().getReference("Konten");
        final IsiKonten isiKonten = mKonten.get(i);
        viewHolder.wisata_search.setText(isiKonten.getWisata());
        viewHolder.deskripsi_search.setText(isiKonten.getDescription());
        Glide.with(mContext).load(isiKonten.getPostImage()).into(viewHolder.image_search);
    }

    @Override
    public int getItemCount() {
        return mKonten.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView wisata_search,deskripsi_search;
        public ImageView image_search;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wisata_search = itemView.findViewById(R.id.wisata_search);
            deskripsi_search = itemView.findViewById(R.id.deskripsi_search);
            image_search = itemView.findViewById(R.id.image_search);
        }
    }
}
