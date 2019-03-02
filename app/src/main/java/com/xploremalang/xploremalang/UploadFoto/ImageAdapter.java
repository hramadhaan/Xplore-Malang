package com.xploremalang.xploremalang.UploadFoto;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xploremalang.xploremalang.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewholder> {

    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context,List<Upload> uploads){
        mContext=context;
        mUploads=uploads;
    }

    @Override
    public ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewholder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {

    public TextView textViewName;
    public ImageView imageView;


        public ImageViewholder(View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.tv_description);
            imageView = itemView.findViewById(R.id.iv_upload);
        }
    }
}
