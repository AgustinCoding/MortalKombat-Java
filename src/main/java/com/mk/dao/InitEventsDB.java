package com.mk.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitEventsDB {

    public static void init(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS events(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                winner TEXT NOT NULL,
                loser TEXT NOT NULL,
                date TEXT NOT NULL 
                )
                """; // Se envia la fecha en formato string


        Statement stmt = conn.createStatement();

        stmt.execute(sql);
    }

}
