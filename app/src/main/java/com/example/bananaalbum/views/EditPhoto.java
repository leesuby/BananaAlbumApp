package com.example.bananaalbum.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bananaalbum.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class EditPhoto extends AppCompatActivity {
    private float angle = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_edit);

        BottomNavigationView navTab = findViewById(R.id.navBar1);
        navTab.setBackground(null);

        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint({"QueryPermissionsNeeded", "NonConstantResourceId"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                ImageView imageView = findViewById(R.id.imageView5);
                switch (item.getItemId()){

                    case R.id.grayscale:
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0);
                        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                        imageView.setColorFilter(filter);
                        break;
                    case R.id.rotate:
                        angle = angle + 90;
                        imageView.setRotation(angle);
                        System.out.println(angle);
                }
                return true;
            }
        });
    }
}