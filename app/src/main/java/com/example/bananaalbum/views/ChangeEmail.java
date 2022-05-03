package com.example.bananaalbum.views;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmail extends AppCompatActivity {

    private ImageButton backBtn;
    private TextView newEmail,ForgotPassword,currentpw;
    private Button cancelBtn, changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_email);
        backBtn = findViewById(R.id.btnbackChangeEmail);
        cancelBtn = findViewById(R.id.cancelbtne);
        newEmail = findViewById(R.id.NewEmail);
        currentpw = findViewById(R.id.CurrentPassword);
        changeBtn = findViewById(R.id.changeEmailBtn);
        ForgotPassword = findViewById(R.id.forgotpwE);
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
                updateEmail();
            }
        });
        ForgotPassword.setPaintFlags(ForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeEmail.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void updateEmail() {
        String email = newEmail.getText().toString().trim();
        String password = currentpw.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter new email!", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password is required!!", Toast.LENGTH_LONG).show();
        }
        else {
            Log.e(TAG, email);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), password);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ChangeEmail.this, "Update email successfully. Please validate your email.", Toast.LENGTH_LONG).show();

                                    finish();
                                }
                            }

                        });
                    }
                    else {
                        Toast.makeText(ChangeEmail.this, "Invalid password", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}