package com.example.bananaalbum.views;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bananaalbum.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {
    TextView username, email;
    LinearLayout logoutBtn;
    GoogleSignInAccount account;
    GoogleSignInClient mGoogleSignInClient;
    Context con;
    CircleImageView avatar;


    FirebaseAuth mAuth;
    Uri imgURI;
    String myURI = "";

    LinearLayout changePw,changeEmail,updateProfile, accountLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userSetting =  inflater.inflate(R.layout.user_setting_layout, container, false);

        // get components
        username = userSetting.findViewById(R.id.user_name);
        email = userSetting.findViewById(R.id.user_email);
        logoutBtn = userSetting.findViewById(R.id.Logout);
        avatar = userSetting.findViewById(R.id.user_avatar);
        changePw =userSetting.findViewById(R.id.change_password);
        changeEmail =userSetting.findViewById(R.id.change_email);
        updateProfile =userSetting.findViewById(R.id.update_profile);
        accountLayout = userSetting.findViewById(R.id.accLayout);
        // get Google account
        MainScreen mainScreen = (MainScreen)getActivity();
        account = mainScreen.account;
        mGoogleSignInClient = mainScreen.client;
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(account!=null){
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String str = personFamilyName + " " + personGivenName;
            String name = capitalizeString(str);
            username.setText(name);
            email.setText(personEmail);

            Picasso.get().load(account.getPhotoUrl()).into(avatar);
            accountLayout.setVisibility(View.GONE);
        }
        else if (user != null){
            String name = user.getDisplayName();
            Uri avatarURI = user.getPhotoUrl();
            if (name != null)
                username.setText(name);
                email.setText(user.getEmail());
            if (avatarURI != null)
                Picasso.get().load(avatarURI).into(avatar);
                accountLayout.setVisibility(View.VISIBLE);
        }


        // Logout button
        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScreen.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new ChangePassword()).commit();
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScreen.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new ChangeEmail()).commit();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainScreen, UpdateProfile.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 mainScreen.signOut();
            }
        });

        return userSetting;
    }



    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }


}