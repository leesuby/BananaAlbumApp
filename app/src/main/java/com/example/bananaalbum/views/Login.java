package com.example.bananaalbum.views;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        //Intent intent = new Intent(MainActivity.this, Login.class);
        //startActivity(intent);

        //Button and Text
        TextView Email = (TextView) findViewById(R.id.TextEmailAddress);
        TextView Password = (TextView) findViewById(R.id.TextPassword);
        Button SignIn = (Button) findViewById(R.id.ButtonSignIn);
        Button Google = (Button) findViewById(R.id.ButtonGoogle);
        Button Facebook = (Button) findViewById(R.id.ButtonFacebook);
        TextView ForgorPassword = (TextView) findViewById(R.id.ButtonForgotPassword);
        TextView SignUp = (TextView) findViewById(R.id.ButtonSignUp);
        //Action Sign in
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().equals("admin") && Password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                }
            }

        });
        //Action Sign Up
        SignUp.setPaintFlags(SignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
