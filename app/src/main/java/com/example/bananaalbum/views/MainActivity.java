package com.example.bananaalbum.views;

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

import com.example.bananaalbum.R;
import com.example.bananaalbum.adapters.TestAdapter;
import com.example.bananaalbum.model.Test;
import com.example.bananaalbum.viewmodels.TestViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Console;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvTest;
    private Button btnAddUser;


    private TestAdapter testAdapter;
    private TestViewModel testViewModel;

    private boolean editMode = false;

    private static final int REQUEST_IMAGE_CAPTURE = 1208;
    private static final String appID = "bananaAlbum";
    private Uri imageUri;
    private File createImageFile(){
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "/JPEG_" + timeStamp +".jpg";
        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(storageDir + imageFileName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main_screen);

        BottomNavigationView navTab = findViewById(R.id.nav_tab);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab,new HomeFragment()).commit();


        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;

                switch (item.getItemId()){
                    case R.id.home:
                        selected = new HomeFragment();
                        break;
                    case R.id.pub:
                        selected = new PublicFragment();
                        break;
                    case R.id.camera:
                        selected = new CameraFragment();
//                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                            StrictMode.setVmPolicy(builder.build());
//                        }
//                        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
//                            final File photoFile =  createImageFile();
//                            imageUri = Uri.fromFile(photoFile);
//                            final SharedPreferences myPrefs = getSharedPreferences(appID, 0);
//                            myPrefs.edit().putString("path", photoFile.getAbsolutePath()).apply();
//                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            startActivityIfNeeded(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                        }
//                        else{
//                            Toast.makeText(MainActivity.this, "Your camera app is not compatible.", Toast.LENGTH_SHORT).show();
//                        }
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab,selected).commit();

                return true;
            }
        });

//        rcvTest=findViewById(R.id.rcv_test);
//        btnAddUser=findViewById(R.id.btnAdd);
//
//        // set layout for RecyclerView
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rcvTest.setLayoutManager(linearLayoutManager);
//
//        // set ViewModel with LiveData
//        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);
//        testViewModel.getListTestLiveData().observe(this, new Observer<List<Test>>() {
//            @Override
//            public void onChanged(List<Test> tests) {
//                //set Adapter for RecyclerView
//                testAdapter= new TestAdapter(tests);
//                rcvTest.setAdapter(testAdapter);
//
//            }
//        });
//
//        //method for button add User
//        btnAddUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Test t = new Test(R.drawable.ic_launcher_background,"Long","Dep trai lam");
//                testViewModel.addTest(t);
//            }
//        });
    }

    private static final int REQUEST_PERMISSIONS = 2461;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean notPermission(){
        for(int i = 0; i < PERMISSIONS_COUNT; i++){
            if(checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    };

    @Override
    protected void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && notPermission()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS  && grantResults.length > 0){
            if(notPermission()){
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                recreate();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(imageUri == null){
                final SharedPreferences p = getSharedPreferences(appID, 0);
                final String path = p.getString("path", "");
                if(path.length() < 1){
                    recreate();
                    return;
                }
                imageUri = Uri.parse("file://" + path);
            }
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
        }
        else if(data == null){
            recreate();
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Loading", "Please Wait", true);
        editMode = true;

        dialog.cancel();
    }

}