package com.example.bananaalbum.views;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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

public class SignUp extends AppCompatActivity {
    TextView SignUpUI;
    EditText Username, Password, Email;
    Button Register;
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
        SignUpUI = (TextView) findViewById(R.id.LoginUI);
        Username = (EditText) findViewById(R.id.SU_TextName);
        Password = (EditText) findViewById(R.id.SU_TextPassword);
        Email = (EditText) findViewById(R.id.SU_TextEmailAddress);
        Register = (Button) findViewById(R.id.ButtonRegister);
        // Action Sign in
        SignUpUI.setPaintFlags(SignUpUI.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SignUpUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        //Action Register
        PasswordManagement PM = new PasswordManagement();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Pass", PM.createPassword(Password.getText().toString()));
            }
        });
    }

}
