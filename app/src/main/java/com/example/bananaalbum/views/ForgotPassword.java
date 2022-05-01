package com.example.bananaalbum.views;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword  extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView Email;
    Button SendEmail;
    String emailAddress;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
        //get text and button
        Email = (TextView)findViewById(R.id.EmailForgotPassword);
        SendEmail = (Button)findViewById(R.id.ButtonSendEmail);
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send email
                emailAddress = Email.getText().toString();
                if (TextUtils.isEmpty(emailAddress)) {
                    Toast.makeText(getApplication(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "An email was sent to you",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e(TAG, "sendEmailChangePassword", task.getException());
                                        Toast.makeText(ForgotPassword.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}