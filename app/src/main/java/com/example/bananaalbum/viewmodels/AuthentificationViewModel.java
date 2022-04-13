package com.example.bananaalbum.viewmodels;

import android.os.StrictMode;
import android.util.Log;

import static com.example.bananaalbum.utils.DatabaseConnector.createConnection;
import static com.example.bananaalbum.utils.PasswordManagement.comparePassword;

import com.example.bananaalbum.utils.PasswordManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AuthentificationViewModel {
    static String sql;
    static ResultSet rs;
    static Connection con =null ;
    static PreparedStatement psm = null;
    static Statement st;
    public static boolean checkUser(String username,String password)  {
        String encryptPassword = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            Log.w("temp1","123");
            con = createConnection();
            Log.w("temp1","456");
            st = con.createStatement();
            sql = "Select users.password from users where username = ?;";
            psm = con.prepareStatement(sql);
            psm.setString(1, username);
            rs = psm.executeQuery();

            while(rs.next()){
                encryptPassword = rs.getString("password");
            }
            Log.w("temp4",encryptPassword);
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(comparePassword(encryptPassword,password) == true)
            return true;
        else
            return false;
    }
}
