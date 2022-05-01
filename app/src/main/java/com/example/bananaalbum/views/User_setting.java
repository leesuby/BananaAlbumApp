package com.example.bananaalbum.views;

import static com.example.bananaalbum.utils.DatabaseConnector.createConnection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bananaalbum.R;
import com.example.bananaalbum.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_setting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_layout);

        TextView name,email,bdate,addr;
        name= findViewById(R.id.user_name);
        email=findViewById(R.id.user_email);

        Account user = null;
        String sql;
        ResultSet rs;


        Connection conn = createConnection();
        PreparedStatement psm = null;

        try {
            sql = "SELECT * FROM users where username = ?";
            psm = conn.prepareStatement(sql);
            psm.setString(1, "thienlong");
            rs = psm.executeQuery();

            while (rs.next()) {
                user = new Account(rs.getString("name"), rs.getString("email"), rs.getDate("dob").toString()
                        , rs.getNString("address"));
            }
            conn.close();
            psm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        name.setText(user._name);
        email.setText(user._email);

    }
}