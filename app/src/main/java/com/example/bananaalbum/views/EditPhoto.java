package com.example.bananaalbum.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.model.Picture;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class EditPhoto extends AppCompatActivity {

    private ImageView imageView;
    private  ArrayList<Bitmap> undoList;
    private Bitmap fixedBitmap = null;
    private int currentShowingIndex = 0;
    private FloatingActionButton btnUndo;
    private FloatingActionButton btnRedo;
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

        //get data from fragment
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Picture p = (Picture) bundle.get("picture");

        undoList = new ArrayList<>();
        btnUndo = findViewById(R.id.UndoBtn);
        btnRedo = findViewById(R.id.RedoBtn);
        imageView = findViewById(R.id.imageView5);
        imageView.setImageResource(p.getResourceId());
        BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
        Bitmap baseBitmap = draw.getBitmap();
        undoList.add(baseBitmap);


        BottomNavigationView navTab = findViewById(R.id.navBar1);
        navTab.setBackground(null);

        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint({"QueryPermissionsNeeded", "NonConstantResourceId"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                imageView.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Matrix matrix = new Matrix();
                switch (item.getItemId()){
                    case R.id.grayscale:
                        fixedBitmap = toGrayscale(bitmap);
                        break;
                    case R.id.rotateLeft:
                        matrix.postRotate(90);
                        fixedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        break;
                    case R.id.rotateRight:
                        matrix.postRotate(270);
                        fixedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        break;
                    case R.id.flip:
                        matrix.setScale(-1, 1);
                        fixedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        break;

                }
                imageView.setImageBitmap(fixedBitmap);
                try{
                    recycleBitmapList(++currentShowingIndex);
                    assert fixedBitmap != null;
                    undoList.add(fixedBitmap.copy(fixedBitmap.getConfig(),true));
                }catch (OutOfMemoryError error){
                    undoList.get(1).recycle();
                    undoList.remove(1);
                    assert fixedBitmap != null;
                    undoList.add(fixedBitmap.copy(fixedBitmap.getConfig(),true));
                }
                setButtonsVisibility();
                return true;
            }
        });

        FloatingActionButton btnAddAlbum = findViewById(R.id.savePhoto);
        btnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = draw.getBitmap();
                FileOutputStream outStream = null;
                File outFile = createImageFile();
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    assert outStream != null;
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outFile));
                sendBroadcast(intent);
                Toast.makeText(EditPhoto.this,"Save picture successfully",Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton btnBack = findViewById(R.id.Backbtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPhoto.this, MainScreen.class);
                startActivity(intent);
            }
        });

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fixedBitmap != null) {
                    if (!fixedBitmap.isRecycled()) {
                        fixedBitmap.recycle();
                    }
                }
                fixedBitmap = getUndoBitmap();
                imageView.setImageBitmap(fixedBitmap);
                setButtonsVisibility();
            }
        });

        btnRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fixedBitmap != null) {
                    if (!fixedBitmap.isRecycled()) {
                        fixedBitmap.recycle();
                    }
                }
                fixedBitmap = getRedoBitmap();
                imageView.setImageBitmap(fixedBitmap);
                setButtonsVisibility();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleBitmapList(0);
    }

    private void setButtonsVisibility() {
        if (currentShowingIndex > 0) {
            btnUndo.setEnabled(true);
        }else {
            btnUndo.setEnabled(false);
        }

        if (currentShowingIndex + 1 < undoList.size()) {
            btnRedo.setEnabled(true);
        }else {
            btnRedo.setEnabled(false);
        }
    }

    private void recycleBitmapList(int fromIndex){
        while (fromIndex < undoList.size()){
            undoList.get(fromIndex).recycle();
            undoList.remove(fromIndex);
        }
    }

    private Bitmap getUndoBitmap(){
        if (currentShowingIndex - 1 >= 0)
            currentShowingIndex -= 1;
        else
            currentShowingIndex = 0;
        return undoList.get(currentShowingIndex).copy(undoList.get(currentShowingIndex).getConfig(), true);
    }

    private Bitmap getRedoBitmap(){
        if (currentShowingIndex + 1 <= undoList.size())
            currentShowingIndex += 1;
        else
            currentShowingIndex = undoList.size() - 1;
        return undoList.get(currentShowingIndex).copy(undoList.get(currentShowingIndex).getConfig(), true);
    }

    private File createImageFile() {
        @SuppressLint("SimpleDateFormat") final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "/JPEG_" + timeStamp + ".jpg";
        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(storageDir + imageFileName);
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal) {
        Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(),bmpOriginal.getHeight(), bmpOriginal.getConfig());

        for (int i = 0; i < bmpOriginal.getWidth(); i++) {
            for (int j = 0; j < bmpOriginal.getHeight(); j++) {
                int p = bmpOriginal.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int gray = (int) ((r + g + b) /3);
                r = gray;
                g = gray;
                b = gray;
                bmpGrayscale.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        return bmpGrayscale;
    }
}