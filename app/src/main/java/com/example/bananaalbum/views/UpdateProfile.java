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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bananaalbum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    CircleImageView up_avatar;
    TextView fullname;
    Button updateBtn;
    FirebaseAuth mAuth;
    String name ="";
    private Uri avtUri;
    private static final int RI = 12346;
    String uriString ="";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View update_profile =  inflater.inflate(R.layout.activity_update_profile, container,false );
        MainScreen mainScreen = (MainScreen)getActivity();
        up_avatar = update_profile.findViewById(R.id.avt);
        fullname = update_profile.findViewById(R.id.fullname);
        updateBtn = update_profile.findViewById(R.id.updateBtn);
        mAuth = FirebaseAuth.getInstance();
        getUserInfo();

        up_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               uploadAvatar();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname= fullname.getText().toString().trim();
                if(fname.length()==0 &&uriString.length()==0){
                    Toast.makeText(mainScreen, "Information you want to update is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fname==null || fname.length()==0){
                    fname = name;
                }
                if (uriString.length()!=0){
                    avtUri=Uri.parse(uriString);
                }

                mainScreen.updateProfile(fname,avtUri);
            }
        });




        return update_profile;

    }

    private void uploadAvatar() {
        Intent intent= new Intent(getActivity(),AddPhoto.class);
        startActivity(intent);
    }








    private void getUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user== null)
            return;
        name =user.getDisplayName();
        avtUri = user.getPhotoUrl();
        if (name != null)
            fullname.setHint(name);

        if (avtUri != null)
            Picasso.get().load(avtUri).into(up_avatar);
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
        SharedPreferences sharedPref = getContext().getSharedPreferences("uri", Context.MODE_PRIVATE);
        uriString = sharedPref.getString("uri", "");
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove("uri");
        editor.apply();
        if(uriString.length()!=0){
            Picasso.get().load(Uri.parse(uriString)).into(up_avatar);
        }
        Toast.makeText(getActivity(),uriString,Toast.LENGTH_SHORT).show();
    }
}