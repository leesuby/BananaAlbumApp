package com.example.bananaalbum.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;

import java.util.ArrayList;

public class AlbumAdapter extends  RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    ArrayList<Album> listAlbum = new ArrayList<>();


    public AlbumAdapter(ArrayList<Album> listAlbum) {
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
        holder.AlbumName.setText(listAlbum.get(position).getName());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            AlbumName = itemView.findViewById(R.id.tv_albumName);
        }
    }

}
