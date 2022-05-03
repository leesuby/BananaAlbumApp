package com.example.bananaalbum.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.bananaalbum.R;

public class ChangePassword extends Fragment {

    ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View changepw =  inflater.inflate(R.layout.activity_change_password, container,false );
        back = changepw.findViewById(R.id.btn_backChangePassword);
        MainScreen mainScreen = (MainScreen)getActivity();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScreen.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_navTab, new SettingFragment()).commit();
            }
        });
        return changepw;
    }
}