package com.example.bananaalbum.views;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;
import com.example.bananaalbum.viewmodels.AuthentificationViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.sql.SQLException;

public class Login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 0;

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
        TextView ForgotPassword = (TextView) findViewById(R.id.ButtonForgotPassword);
        TextView SignUp = (TextView) findViewById(R.id.ButtonSignUp);

        //Action Sign-in

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(AuthentificationViewModel.checkUser(Email.getText().toString(),Password.getText().toString()) == true){
                        ToMainScreen();
                    }else{
                        Toast.makeText(Login.this,"Wrong username or password",Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    System.out.println(throwables.toString());
                }
            }
        });
        //Action Sign-in with Google
        SignInButton signInButton;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.ButtonGoogle:
                        signIn();
                        break;
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
        //Action Forgot Passwords
        ForgotPassword.setPaintFlags(ForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Sign-in Successfully",Toast.LENGTH_SHORT).show();
            ToMainScreen();
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private void ToMainScreen(){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Toast.makeText(this,"User Already Signed-in",Toast.LENGTH_SHORT).show();
            ToMainScreen();
        }
    }
}
