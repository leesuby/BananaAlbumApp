package com.example.bananaalbum.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.concurrent.Executor;

public class SettingFragment extends Fragment {
    TextView username, email;
    LinearLayout logoutBtn;
    GoogleSignInAccount account;
    GoogleSignInClient mGoogleSignInClient;
    Context con;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userSetting =  inflater.inflate(R.layout.user_setting_layout, container, false);
        // get components
        username = userSetting.findViewById(R.id.user_name);
        email = userSetting.findViewById(R.id.user_email);
        logoutBtn = userSetting.findViewById(R.id.Logout);
        // get Google account
        MainScreen mainScreen = (MainScreen)getActivity();
        account = mainScreen.account;
        mGoogleSignInClient = mainScreen.client;
        if(account!=null){
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String str = personFamilyName + " " + personGivenName;
            String name = capitalizeString(str);
            username.setText(name);
            email.setText(personEmail);
        }
        // Logout button
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