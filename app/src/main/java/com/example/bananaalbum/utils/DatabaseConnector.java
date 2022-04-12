package com.example.bananaalbum.utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://us-cdbr-east-05.cleardb.net/heroku_cbbb99416ea560f";
    static String user = "bfdc852d3fdbbc"; // add username của connection trong mysql vào đây
    static String pass = "625441f0"; // add password của connection trong mysql vào đây

    public static Connection createConnection() {
        Connection conn = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, user, pass);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
