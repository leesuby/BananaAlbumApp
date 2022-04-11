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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.PictureAdapter;
import com.example.bananaalbum.adapters.PictureRecylerAdapter;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.model.Picture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewAlbum extends AppCompatActivity {
    TextView AlbumName;
    GridView gridPic;
    RecyclerView rcvPic;
    ImageButton backBtn;
    FloatingActionButton floatingActionButton;

    String[] name = {"1", "2", "3", "4", "5"};
    int[] picture = {R.drawable.test_ava, R.drawable.test_ava2, R.drawable.test_ava3, R.drawable.test_ava4, R.drawable.app_name2};

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

        PictureRecylerAdapter adapter = new PictureRecylerAdapter();

        List<Picture> list = new ArrayList<>();

        for(int i = 0;i<25;i++){
            list.add(new Picture(picture[i % 5]));
        }

        adapter.setData(list,this);
        rcvPic.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);

        rcvPic.setLayoutManager(gridLayoutManager);

        rcvPic.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if( dy > 0){
                    floatingActionButton.hide();
                }else
                    floatingActionButton.show();
                super.onScrolled(recyclerView, dx, dy);
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
