package com.mk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:mortal_kombat.db";
    private static Connection connection = null;

    public static Connection getConnection(){

        if(connection == null){
            try {
                connection = DriverManager.getConnection(URL);
                System.out.println("Conexion establecida con " + URL.split(":")[2]);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                System.out.println("Cerrando conexion a base de datos");
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
