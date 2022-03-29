package com.example.bananaalbum.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bananaalbum.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CameraFragment extends Fragment {
    public static final String EXTRA_INFO = "default";
    private static final String appID = "bananaAlbum";

    private File createImageFile(){
        @SuppressLint("SimpleDateFormat")
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "/JPEG_" + timeStamp + ".jpg";
        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(storageDir + imageFileName);
    }
    private static final int REQUEST_IMAGE_CAPTURE = 1208;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File photoFile = createImageFile();
        Uri imageUri = Uri.fromFile(photoFile);
        final  SharedPreferences myPrefs = requireActivity().getSharedPreferences(appID, 0);
        myPrefs.edit().putString("path", photoFile.getAbsolutePath()).apply();
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivity(takePictureIntent);
        if(imageUri == null){
            final SharedPreferences p = requireActivity().getSharedPreferences(appID, 0);
            final String path = p.getString("path", "");
            if(path.length() < 1){
                requireActivity().recreate();
            }
            imageUri = Uri.parse("file://" + path);
        }
        requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
        return view;
    }
}
