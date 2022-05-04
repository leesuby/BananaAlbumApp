package com.example.bananaalbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PictureRecylerAdapter extends RecyclerView.Adapter<PictureRecylerAdapter.PictureViewHolder> {


    private List<Picture> mListImage;
    private boolean iseditMode;
    private Context con;

    public void setData(List<Picture> list,Context con,boolean iseditMode){
        this.mListImage=list;
        this.con=con;
        this.iseditMode=iseditMode;
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

        if(picture.getResourceId() != 0) {
            holder.img.setImageResource(picture.getResourceId());
        }

        if(picture.getUri()!= null){
            Picasso.get().load(Uri.parse(picture.getUri())).into(holder.img);
        }

        //event click listener for every picture
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iseditMode==false)
                    onClickDetailPicture(picture);
                else
                    onClickEditPicture(holder,picture);
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

    public void onClickEditPicture(PictureViewHolder holder,Picture p){
        if(p.isChoosen() == false)
        {
            holder.isChooseBtn.setVisibility(View.VISIBLE);
            p.setChoosen(true);
        }
        else{
            holder.isChooseBtn.setVisibility(View.GONE);
            p.setChoosen(false);
        }
    }

    @Override
    public int getItemCount() {
        return this.mListImage != null ? mListImage.size() : 0;
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private RelativeLayout layout;
        private FloatingActionButton isChooseBtn;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.img_img);
            this.layout= itemView.findViewById(R.id.item_picture);
            this.isChooseBtn= itemView.findViewById(R.id.isChoosenBtn);
        }
    }
}
