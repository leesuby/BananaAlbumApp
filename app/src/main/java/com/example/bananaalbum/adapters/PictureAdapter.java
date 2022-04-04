package com.example.bananaalbum.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.bananaalbum.R;

public class PictureAdapter extends ArrayAdapter<String> {

    String[] name;
    int[] picture;
    Context con;

    public PictureAdapter(@NonNull Context context, int resource, String[] name, int[] picture) {
        super(context, resource, name);
        this.picture = picture;
        this.con = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) con).getLayoutInflater();

        View item = inflater.inflate(R.layout.item_picture, null);

        ImageView img = item.findViewById(R.id.img_img);

        img.setImageResource(picture[position]);
        return (item);
    }

}
