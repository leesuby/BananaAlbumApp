package com.example.bananaalbum.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bananaalbum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    CircleImageView up_avatar;
    TextView fullname;
    Button updateBtn;
    ImageButton backBtn;
    FirebaseAuth mAuth;
    String name ="";
    private Uri avtUri;
    private static final int RI = 12346;
    String uriString ="";
    private Uri imageUri;
    public static final int AUTHORIZATION_REQUEST_CODE = 1994;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_profile);
        up_avatar = findViewById(R.id.avt);
        fullname = findViewById(R.id.fullname);
        updateBtn = findViewById(R.id.updateBtn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        backBtn = findViewById(R.id.btn_backUpdateProfile);

        getUserInfo();

        up_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                final Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                final Intent chooserIntent = Intent.createChooser(intent, "Select Image");
                startActivityForResult(chooserIntent, RI);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname= fullname.getText().toString().trim();
                updateProfile(fname);
                uploadImage();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RI) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(up_avatar);
                if (requestCode == AUTHORIZATION_REQUEST_CODE) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    uploadImage();
                }

            }
            else if (resultCode == RESULT_CANCELED) {
                if (requestCode == AUTHORIZATION_REQUEST_CODE) {
                    Toast.makeText(this, "Cannot save without authentication", Toast.LENGTH_LONG).show();
                }
            }
            ;
        }}

    private void getUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user== null)
            return;
        name =user.getDisplayName();

        if (name != null)
            fullname.setHint(name);


    }
    public void updateProfile(String name) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();


        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener((new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            Toast.makeText(UpdateProfile.this,"Update profile sucessfully!",Toast.LENGTH_SHORT).show();


                        }
                    }
                }));


    }

    public  void uploadImage(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageRef = storageReference.child( user.getUid()).child("profile.png");
            UploadTask uploadTask = imageRef.putFile(imageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    int i = 1 + 1;
                    // TODO properly handle this error.
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // this is where we will end up if our image uploads successfully.
                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                    Task<Uri> downloadUrl = imageRef.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            String imageReference = uri.toString();
                            reference.child("data").child(user.getUid()).child("profile").setValue(imageReference);
                            Toast.makeText(UpdateProfile.this,"Update avatar sucessfully!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
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
                            Toast.makeText(UpdateProfile.this,"Update profile sucessfully!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateProfile.this, MainScreen.class);
                            startActivity(intent);

                        }
                    }
                }));
    }


//    class SRefThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//
//            while (true) {
//                url = sharedPref.getString("uri", "123");
//                fullname.setText(url);
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

    }
}