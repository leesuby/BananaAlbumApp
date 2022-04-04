package com.example.bananaalbum.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class EditPhoto extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        BottomNavigationView navTab = findViewById(R.id.navBar1);
        navTab.setBackground(null);

        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint({"QueryPermissionsNeeded", "NonConstantResourceId"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        ImageView imageView = findViewById(R.id.imageView5);
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0);
                        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                        imageView.setColorFilter(filter);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
            }
        });
    }
}
