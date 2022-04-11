package com.example.bananaalbum.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.PictureAdapter;
import com.example.bananaalbum.adapters.PictureRecylerAdapter;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.model.Picture;
import com.example.bananaalbum.viewmodels.AlbumViewModel;
import com.example.bananaalbum.viewmodels.PictureViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewAlbum extends AppCompatActivity {
    TextView AlbumName;
    GridView gridPic;
    RecyclerView rcvPic;
    ImageButton backBtn;
    FloatingActionButton floatingActionButton;
    PictureViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_album);

        //get data from fragment
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Album a = (Album) bundle.get("album");

        //get widget
        AlbumName = findViewById(R.id.tv_albumNameOnAlbum);
        rcvPic = findViewById(R.id.rcv_picture);
        backBtn = findViewById(R.id.btn_backAlbum);
        floatingActionButton = findViewById(R.id.flbtn_album);

        AlbumName.setText(a.getName());


        // ViewModel for RecylerView Picture
        viewModel = new ViewModelProvider(this).get(PictureViewModel.class);
        viewModel.getListPictureLiveData().observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(List<Picture> pictures) {
                PictureRecylerAdapter adapter = new PictureRecylerAdapter();
                adapter.setData(pictures, ViewAlbum.this);
                rcvPic.setAdapter(adapter);

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rcvPic.setLayoutManager(gridLayoutManager);
        rcvPic.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    floatingActionButton.hide();
                } else
                    floatingActionButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel = new ViewModelProvider(ViewAlbum.this).get(PictureViewModel.class);
                viewModel.addPicture(new Picture(R.drawable.test_ava4));
                Toast.makeText(ViewAlbum.this, "Add Successfully", Toast.LENGTH_SHORT).show();

            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
