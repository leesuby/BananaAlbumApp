package com.example.bananaalbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.views.ViewAlbum;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    List<Album> listAlbum;
    Context con;

    public AlbumAdapter(List<Album> listAlbum, Context con) {
        this.listAlbum = listAlbum;
        this.con = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album a = listAlbum.get(position);
        int listImage[] = {R.drawable.test_ava2, R.drawable.test_ava3, R.drawable.test_ava4};
        holder.AlbumName.setText(a.getName());
        holder.AlbumThumbnail.setImageResource(listImage[position % 3]);

        //click listener for every album
        holder.itemAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetail(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAlbum != null ? listAlbum.size() : 0;
    }

    private void onClickDetail(Album a) {
        Intent i = new Intent(con, ViewAlbum.class);
        Bundle data = new Bundle();
        data.putSerializable("album", a);
        i.putExtras(data);
        con.startActivity(i);
    }

    public void setListAlbum(ArrayList<Album> listAlbum) {
        this.listAlbum = listAlbum;
        notifyDataSetChanged();
    }

    public void release() {
        this.con = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout itemAlbum;
        public TextView AlbumName;
        public ImageView AlbumThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            AlbumName = itemView.findViewById(R.id.tv_albumName);
            AlbumThumbnail = itemView.findViewById(R.id.im_AlbumThumbnail);
            itemAlbum = itemView.findViewById(R.id.item_album);
        }
    }

}
