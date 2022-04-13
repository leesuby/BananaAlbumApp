package com.example.bananaalbum.viewmodels;

import android.util.Log;

import static com.example.bananaalbum.utils.DatabaseConnector.createConnection;
import com.example.bananaalbum.utils.PasswordManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AuthentificationViewModel {
    static String sql;
    static ResultSet rs;
    static Connection con ;
    static PreparedStatement psm = null;
    static Statement st;
    public static boolean checkUser(String username, String password) throws SQLException {
        String encryptPassword = "";
        try{
            Log.w("temp1","123");
            con = createConnection();
            Log.w("temp1","456");
            st = con.createStatement();
            sql = "Select password from users where username = ?;";
            psm = con.prepareStatement(sql);
            psm.setString(1, username);
            rs = psm.executeQuery();

            while(rs.next()){
                encryptPassword = rs.getString("password");
            }
            con.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        if(PasswordManagement.comparePassword(password,encryptPassword) == true)
            return true;
        else
            return false;
    }
}
