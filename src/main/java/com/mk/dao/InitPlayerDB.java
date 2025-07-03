package com.mk.dao;

import com.mk.model.Player;
import com.mk.dao.DBConnection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class InitPlayerDB {

    public static void init(Connection conn) throws SQLException {

        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS players(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password_hash VARCHAR(255) NOT NULL
                )
                """;
        Statement statement = conn.createStatement();
        statement.execute(createTableSQL);


    }



}
