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

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.PictureAdapter;
import com.example.bananaalbum.model.Album;

public class ViewAlbum extends AppCompatActivity {
    TextView AlbumName;
    GridView gridPic;
    ImageButton backBtn;
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
        gridPic = findViewById(R.id.gv_picture);
        backBtn = findViewById(R.id.btn_backAlbum);

        AlbumName.setText(a.getName());

        PictureAdapter adapter = new PictureAdapter(this, R.layout.item_picture, name, picture);
        gridPic.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
