package com.mk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:mortal_kombat.db";

    public static Connection getConnection(){

        try{
            Connection conn = DriverManager.getConnection(URL);
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }

    }

}
