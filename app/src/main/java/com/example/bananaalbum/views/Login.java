package com.example.bananaalbum.views;

import static android.content.ContentValues.TAG;
import static com.example.bananaalbum.utils.DatabaseConnector.createConnection;
import static com.example.bananaalbum.viewmodels.AuthentificationViewModel.checkUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bananaalbum.R;
import com.example.bananaalbum.viewmodels.AuthentificationViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

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
        mAuth=FirebaseAuth.getInstance();
        //Action Sign-in
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString());
            }
        });
        //Action Sign-in with Google
        SignInButton signInButton;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientID))
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

    private void SignInWithEmailAndPassword(String email, String pw) {
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pw)){
            Toast.makeText(this, "Please enter your password",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        user = mAuth.getCurrentUser();
                        if(user.isEmailVerified()==true){
                            Toast.makeText(Login.this, "Sign-in Successfully",Toast.LENGTH_SHORT).show();
                            ToMainScreen();
                            finish();}
                        else{
                            validateEmail(user);
                            Toast.makeText(Login.this, "Please validate your email",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Login.this, "Sign-in failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

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
            firebaseAuthWithGoogle(account);
            Toast.makeText(this, "Sign-in Successfully",Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ToMainScreen();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    private void ToMainScreen(){
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }
    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser user = mAuth.getCurrentUser();

        if(account != null ){
            Toast.makeText(this,"User Already Signed-in With Google Account",Toast.LENGTH_SHORT).show();
            ToMainScreen();
        }else if(user!=null){
            if(user.isEmailVerified()==false){

            }
            else{
                Toast.makeText(this,"User Already Signed-in",Toast.LENGTH_SHORT).show();
                ToMainScreen();
            }

        }
    }
    private void validateEmail(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Login.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
