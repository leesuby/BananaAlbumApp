package com.example.bananaalbum.views;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;
import com.example.bananaalbum.utils.PasswordManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    TextView LoginUI;
    EditText Username, Password, Email;
    Button Register;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        //Button and Text
        LoginUI = (TextView) findViewById(R.id.LoginUI);
        Password = (EditText) findViewById(R.id.SU_TextPassword);
        Email = (EditText) findViewById(R.id.SU_TextEmailAddress);
        Register = (Button) findViewById(R.id.ButtonRegister);
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        // Action Sign in
        LoginUI.setPaintFlags(LoginUI.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        LoginUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        //Action Register
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString();
                String pass = Password.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                }
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "ERROR",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    startActivity(new Intent(SignUp.this, Login.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}
