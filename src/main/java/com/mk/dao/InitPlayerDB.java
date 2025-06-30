package com.mk.dao;

import com.mk.model.Player;
import com.mk.dao.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class InitPlayerDB {

    public static void init(){
        try(Connection conn = DBConnection.getConnection()){
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS players(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        password_hash VARCHAR(255) NOT NULL
                    )
                    """;
            Statement statement = conn.createStatement();
            statement.execute(createTableSQL);

        }catch(SQLException e ){
            e.printStackTrace();
        }
    }



}
