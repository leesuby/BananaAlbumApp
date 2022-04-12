package com.example.bananaalbum.model;

import static com.example.bananaalbum.utils.DatabaseConnector.createConnection;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public String _name;
    public String _email, _bdate, _address;
    public Account(String name,String email, String bdate, String address){
        _name=name;
        _email=email;
        _bdate=bdate;
        _address=address;
    }
    public static Account getAccountByID(String username) {
        Account temp = null;
        String sql;
        ResultSet rs;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = createConnection();
        PreparedStatement psm = null;

        try {
            sql = "SELECT * FROM users where username = ";
            psm = conn.prepareStatement(sql);
            psm.setString(1, username);
            rs = psm.executeQuery();

            while (rs.next()) {
                temp = new Account(rs.getString("name"), rs.getString("email"), rs.getDate("bdate").toString()
                        , rs.getNString("address"));
            }

            conn.close();
            psm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }


}
