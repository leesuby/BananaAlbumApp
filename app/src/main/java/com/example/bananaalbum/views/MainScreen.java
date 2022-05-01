package com.example.bananaalbum.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Album;
import com.example.bananaalbum.viewmodels.AlbumViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainScreen extends AppCompatActivity {

    private AlbumViewModel albumViewModel;

    private boolean editMode = false;

    private static final int REQUEST_IMAGE_CAPTURE = 1208;

    public GoogleSignInAccount account;
    public GoogleSignInClient client;
    FirebaseAuth mAuth;
    private SettingFragment settings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        settings = new SettingFragment();
        //Remove action bar
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        setContentView(R.layout.activity_main_screen);

        BottomNavigationView navTab = findViewById(R.id.navBar);


        ConstraintLayout topbar=findViewById(R.id.topbar);
        FrameLayout fr = findViewById(R.id.fragment_navTab);
        CircleImageView avatar = findViewById(R.id.imgProfile);

        navTab.setBackground(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new HomeFragment()).commit();
        // Account information from google
        account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            Picasso.get().load(account.getPhotoUrl()).into(avatar);
        }
        else if (user != null) {
            Picasso.get().load(user.getPhotoUrl()).into(avatar);
        }
        navTab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                switch (item.getItemId()) {
                    case R.id.home:
                    {

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);
                        params.weight = 6.0f;
                        topbar.setLayoutParams(params);
                        topbar.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);
                        FrameLayout fr = findViewById(R.id.fragment_navTab);

                        params1.weight=56.0f;
                        fr.setLayoutParams(params1);
                        selected = new HomeFragment();
                        break;
                    }
                    case R.id.pub:
                    {

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);
                        params.weight = 6.0f;
                        topbar.setLayoutParams(params);
                        topbar.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);


                        params1.weight=56.0f;
                        fr.setLayoutParams(params1);
                        selected =new PublicFragment();
                        break;
                    }
                    case R.id.camera:
                    {

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);
                        params.weight = 6.0f;
                        topbar.setVisibility(View.VISIBLE);
                        topbar.setLayoutParams(params);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);


                        params1.weight=56.0f;
                        fr.setLayoutParams(params1);
                        selected = new CameraFragment();
                        break;
                    }

                    case R.id.setting:
                    {
                        selected = settings;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                0, 0);
                        params.weight = 0.0f;
                        topbar.setLayoutParams(params);
                        topbar.setVisibility(View.INVISIBLE);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, 0);


                        params1.weight=62.0f;
                        fr.setLayoutParams(params1);
                        break;
                    }
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
            public void onClick(View view) { //TODO mama: upload album của người dùng lên firebase với album a
                Album a = new Album(name.getText().toString());
                albumViewModel = new ViewModelProvider(MainScreen.this).get(AlbumViewModel.class);
                albumViewModel.addAlbum(a);
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

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        if(account!=null){

            client.signOut()
                    .addOnCompleteListener( this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Toast.makeText(SettingFragment.this.con, "Sign-out Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(MainScreen.this,Login.class);
                            startActivity(intent);
                        }
                    });
        }
        else {
            Intent intent= new Intent(MainScreen.this,Login.class);
            startActivity(intent);
        }
    }

    public void updateProfile(String name, Uri avt) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(avt)
                .build();


        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener((new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainScreen.this,"Update profile sucessfully!",Toast.LENGTH_SHORT).show();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new SettingFragment()).commit();

                        }
                    }
                }));
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