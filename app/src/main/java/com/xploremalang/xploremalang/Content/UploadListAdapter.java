package com.xploremalang.xploremalang.Content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.xploremalang.xploremalang.R;

import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ImageViewHolder> {


    private FirebaseStorage storageReference;
    private Context mContext;
    private List<IsiKonten> mUploads;
    private OnItemClickListener mListener;

    public UploadListAdapter(Context context, List<IsiKonten> uploads, OnItemClickListener onItemClickListener) {
        mContext = context;
        mUploads = uploads;
        mListener = onItemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_model, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        IsiKonten uploadCurrent = mUploads.get(position);
        holder.judulKonten.setText(uploadCurrent.getJudul());
        Log.i("REF","nanya");

        Glide.with(mContext)
                .load(uploadCurrent.getmImageUrl())
                .fitCenter()
                .centerCrop()
                .into(holder.gambarKonten);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView judulKonten;
        public ImageView gambarKonten;

        public ImageViewHolder(View itemView) {
            super(itemView);
            judulKonten = itemView.findViewById(R.id.text_row);
            gambarKonten = itemView.findViewById(R.id.foto_row);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onShowItemClick(int position);

        void onDeleteItemClick(int position);
    }


}