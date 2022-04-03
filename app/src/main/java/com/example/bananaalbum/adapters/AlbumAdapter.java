package com.example.bananaalbum.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends  RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    List<Album> listAlbum;


    public AlbumAdapter(List<Album> listAlbum) {
        this.listAlbum = listAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int listImage[] = {R.drawable.test_ava2,R.drawable.test_ava3,R.drawable.test_ava4};
        holder.AlbumName.setText(listAlbum.get(position).getName());
        holder.AlbumThumbnail.setImageResource(listImage[position % 3]);
    }

    @Override
    public int getItemCount() {
        return listAlbum.size();
    }

    public void setListAlbum(ArrayList<Album> listAlbum) {
        this.listAlbum = listAlbum;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView AlbumName;
        public ImageView AlbumThumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            AlbumName = itemView.findViewById(R.id.tv_albumName);
            AlbumThumbnail = itemView.findViewById(R.id.im_AlbumThumbnail);
        }
    }

}
