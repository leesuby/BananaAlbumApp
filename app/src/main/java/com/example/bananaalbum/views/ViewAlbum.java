package com.example.bananaalbum.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewAlbum extends AppCompatActivity {
    TextView AlbumName;
    RecyclerView rcvPic;
    ImageButton backBtn,editBtn,infoBtn,exitEditBtn;
    FloatingActionButton floatingAddActionButton,floatingDeleteActionButton;
    PictureViewModel viewModel;
    ViewGroup tcontainer;
    LinearLayout album_info;



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
        editBtn = findViewById(R.id.btn_editAlbum);
        infoBtn = findViewById(R.id.btn_infoAlbum);
        exitEditBtn = findViewById(R.id.btn_exitEditAlbum);
        floatingAddActionButton = findViewById(R.id.flbtnAdd_album);
        floatingDeleteActionButton = findViewById(R.id.flbtnDelete_album);
        tcontainer = findViewById(R.id.tcontainer);
        AlbumName.setText(a.getName());
        album_info = findViewById(R.id.album_info);


        // ViewModel for RecylerView Picture
        viewModel = new ViewModelProvider(this).get(PictureViewModel.class);
        //TODO mama: lấy toàn bộ ảnh thuộc AlbumName(biến ở trên) về

        viewModel.getListPictureLiveData().observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(List<Picture> pictures) {
                PictureRecylerAdapter adapter = new PictureRecylerAdapter();
                if(pictures.get(0).isEditMode()==true)
                    adapter.setData(pictures, ViewAlbum.this,true);
                else
                    adapter.setData(pictures, ViewAlbum.this,false);
                rcvPic.setAdapter(adapter);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rcvPic.setLayoutManager(gridLayoutManager);
        rcvPic.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    floatingAddActionButton.hide();
                } else
                    floatingAddActionButton.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        floatingAddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAlbum.this, AddPhoto.class);
                startActivity(intent);

            }
        });

        floatingDeleteActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewModel.IsDeletePicture()==true)
                    openConfirmDialog();
                else
                    Toast.makeText(ViewAlbum.this, "Please choose image you want to delete", Toast.LENGTH_SHORT).show();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.editMode(true);

                floatingAddActionButton.setVisibility(View.GONE);
                infoBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);

                exitEditBtn.setVisibility(View.VISIBLE);
                floatingDeleteActionButton.setVisibility(View.VISIBLE);
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            boolean visable;
            @Override
            public void onClick(View view) {


                TransitionManager.beginDelayedTransition(tcontainer);
                visable = !visable;
                album_info.setVisibility(visable? View.VISIBLE : View.GONE);
                if(visable){
                    infoBtn.setBackgroundResource(R.drawable.ic_outline_info);
                }
                else {
                    infoBtn.setBackgroundResource(R.drawable.ic_baseline_info);
                }


            }
        });

        exitEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.editMode(false);

                floatingAddActionButton.setVisibility(View.VISIBLE);
                infoBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.VISIBLE);

                exitEditBtn.setVisibility(View.GONE);
                floatingDeleteActionButton.setVisibility(View.GONE);
            }
        });

    }

    private void openConfirmDialog() {
        final Dialog dg = new Dialog(this);
        dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dg.setContentView(R.layout.dialog_confirm_deletepicture);

        Window window = dg.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button backBtn = dg.findViewById(R.id.btnBackConfirmDelete), acceptBtn = dg.findViewById(R.id.btnAcceptConfirmDelete);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dg.dismiss();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deletePicture();
                Toast.makeText(ViewAlbum.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                dg.dismiss();
            }
        });

        dg.show();

    }

    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = this.getSharedPreferences("uri", Context.MODE_PRIVATE);
        String uriString = sharedPref.getString("uri", "");
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove("uri");
        editor.apply();

        String AlbumName = this.AlbumName.getText().toString();

        //TODO mama: upload ảnh vào albumName của người dùng

//        if(uriString.length()!=0){
//            Picasso.get().load(Uri.parse(uriString)).into(up_avatar);
//        }
        Toast.makeText(this,uriString,Toast.LENGTH_SHORT).show();

    }
}
