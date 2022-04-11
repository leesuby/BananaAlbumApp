package com.example.bananaalbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Picture;
import com.example.bananaalbum.views.EditPhoto;
import com.example.bananaalbum.views.ViewAlbum;

import java.util.List;

public class PictureRecylerAdapter extends RecyclerView.Adapter<PictureRecylerAdapter.PictureViewHolder> {


    private List<Picture> mListImage;
    private Context con;

    public void setData(List<Picture> list,Context con){
        this.mListImage=list;
        this.con=con;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture picture = mListImage.get(position);
        if (picture == null){
            return;
        }

        holder.img.setImageResource(picture.getResourceId());

        //event click listener for every picture
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetailPicture(picture);
            }
        });

    }

    public void onClickDetailPicture(Picture p){
        Intent i = new Intent(con, EditPhoto.class);
        Bundle data = new Bundle();
        data.putSerializable("picture", p);
        i.putExtras(data);
        con.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return this.mListImage != null ? mListImage.size() : 0;
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private RelativeLayout layout;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.img_img);
            this.layout= itemView.findViewById(R.id.item_picture);
        }
    }
}
