package com.example.bananaalbum.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPhoto extends AppCompatActivity {
    private Bitmap bitmap;
    private static final int RI = 12345;
    Bundle uri = new Bundle();
    private Uri imageUri;
    private static final int MAX_PIXEL_COUNT = 2048;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        final Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        final Intent chooserIntent = Intent.createChooser(intent, "Select Image");
        startActivityForResult(chooserIntent, RI);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == RI) {
                imageUri = data.getData();
                SharedPreferences sf = getSharedPreferences("uri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("uri", imageUri.toString());
                editor.commit();
                finish();
            }

        }
        ;
    }

    public Uri getImageUri() {
        return imageUri;
    }


}
