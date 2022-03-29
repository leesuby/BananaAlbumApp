package com.example.bananaalbum.views;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bananaalbum.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class PublicFragment extends Fragment {
    private Bitmap bitmap;
    private static final int RI = 12345;
    private Uri imageUri;
    private int width = 0;
    private int height = 0;
    private static final int MAX_PIXEL_COUNT = 2048;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public,container,false);
        imageView = (ImageView) requireActivity().findViewById(R.id.imageView2);

        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        final Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        final Intent chooserIntent = Intent.createChooser(intent, "Select Image");
        startActivityForResult(chooserIntent, RI);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == MainActivity.RESULT_OK) {
            if (requestCode == RI) {
                imageUri = data.getData();
            }
            System.out.println(imageUri.toString());
        }
//        new Thread(){
//          public void run(){
//              bitmap = null;
//              final BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
//              bmpOptions.inBitmap = bitmap;
//              bmpOptions.inJustDecodeBounds = true;
//              try(InputStream input = requireContext().getContentResolver().openInputStream(imageUri)){
//                  bitmap = BitmapFactory.decodeStream(input, null, bmpOptions);
//              }catch (IOException e){
//                  e.printStackTrace();
//              }
//              bmpOptions.inJustDecodeBounds = false;
//              width = bmpOptions.outWidth;
//              height = bmpOptions.outHeight;
//              int resizeScale = 1;
//              if(width > MAX_PIXEL_COUNT){
//                  resizeScale = width / MAX_PIXEL_COUNT;
//              }else if(height > MAX_PIXEL_COUNT){
//                  resizeScale = height / MAX_PIXEL_COUNT;
//              }
//              if(width / resizeScale > MAX_PIXEL_COUNT || height / resizeScale > MAX_PIXEL_COUNT){
//                  resizeScale++;
//              }
//
//              bmpOptions.inSampleSize = resizeScale;
//              InputStream input = null;
//              try{
//                  input = requireContext().getContentResolver().openInputStream(imageUri);
//                  System.out.println("aaaa");
//              }catch (FileNotFoundException e){
//                  e.printStackTrace();
//                  requireActivity().recreate();
//              }
//              System.out.println(input.toString());
//              bitmap = BitmapFactory.decodeStream(input, null, bmpOptions);
//              System.out.println(bitmap.toString());
//              requireActivity().runOnUiThread(new Runnable() {
//                  @Override
//                  public void run() {
//                      imageView.setImageBitmap(bitmap);
//                  }
//              });
//          }
//        }.start();
    }
}
