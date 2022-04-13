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
        String saltvalue = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            con = createConnection();
            st = con.createStatement();
            sql = "Select users.password, users.saltvalue from users where username = ?;";
            psm = con.prepareStatement(sql);
            psm.setString(1, username);
            rs = psm.executeQuery();
            while(rs.next()){
                encryptPassword = rs.getString("password");
                saltvalue = rs.getString("saltvalue");
            }
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(comparePassword(password,encryptPassword,saltvalue) == true)
            return true;
        else
            return false;
    }
}
