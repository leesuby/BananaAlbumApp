package com.example.bananaalbum.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView newPw,confirmPw,ForgotPassword;
    private Button cancelBtn, changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_password);
        backBtn = findViewById(R.id.btn_backChangePassword);
        cancelBtn = findViewById(R.id.cancel_btn_p);
        newPw = findViewById(R.id.NewPassword);
        changeBtn = findViewById(R.id.change_pw_btn);
        confirmPw = findViewById(R.id.ConfirmPassword);
        ForgotPassword = findViewById(R.id.forgotpw);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
        ForgotPassword.setPaintFlags(ForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void updatePassword() {
        String newPassword = newPw.getText().toString().trim();
        String confirmPassword = confirmPw.getText().toString().trim();
        if(TextUtils.isEmpty(newPassword)){
            Toast.makeText(this, "Please enter new password!", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this, "Please enter confirm password!", Toast.LENGTH_LONG).show();
        }
        else if (!newPassword.equals(confirmPassword)){
            Toast.makeText(this, "Confirm password is not true!", Toast.LENGTH_LONG).show();
        }
        else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(ChangePassword.this, "User password changed.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }


}