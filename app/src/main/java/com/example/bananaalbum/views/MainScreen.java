package com.example.bananaalbum.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.viewmodels.AlbumViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreen extends AppCompatActivity {

    private AlbumViewModel albumViewModel;

    private boolean editMode = false;

    private static final int REQUEST_IMAGE_CAPTURE = 1208;

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

        BottomNavigationView navTab = findViewById(R.id.navBar);

        navTab.setBackground(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new HomeFragment()).commit();


        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        selected = new HomeFragment();
                        break;
                    case R.id.pub:
                        selected = new PublicFragment();
                        break;
                    case R.id.camera:
                        selected = new CameraFragment();
                        break;
                    case R.id.setting:
                        selected = new SettingFragment();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, selected).commit();

                return true;
            }
        });
        FloatingActionButton btnAddAlbum = findViewById(R.id.addAlbumbtn);
        btnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddAlbumDialog();

            }
        });
    }

    private void openAddAlbumDialog() {
        final Dialog dg = new Dialog(this);
        dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dg.setContentView(R.layout.dialog_addalbum);

        Window window = dg.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText name = dg.findViewById(R.id.editText_AlbumName);
        Button backBtn = dg.findViewById(R.id.btnBackAddAlbum), acceptBtn = dg.findViewById(R.id.btnAcceptAddAlbum);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dg.dismiss();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumViewModel = new ViewModelProvider(MainScreen.this).get(AlbumViewModel.class);
                albumViewModel.addAlbum(new Album(name.getText().toString()));
                Toast.makeText(MainScreen.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                dg.dismiss();
            }
        });

        dg.show();

    }


    private static final int REQUEST_PERMISSIONS = 2461;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSIONS_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean notPermission() {
        for (int i = 0; i < PERMISSIONS_COUNT; i++) {
            if (checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    ;

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && notPermission()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (notPermission()) {
                ((ActivityManager) this.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
                recreate();
            }
        }
    }


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