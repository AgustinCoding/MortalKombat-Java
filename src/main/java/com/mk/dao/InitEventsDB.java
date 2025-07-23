package com.mk.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitEventsDB {

    public void init(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXSISTS events(
                id INTEGEER PRIMARY KEY AUTOINCREMENT,
                winner TEXT NOT NULL,
                loser TEXT NOT NULL,
                date DATETIME NOT NULL
                )
                """;


        Statement stmt = conn.prepareStatement(sql);

    }

}
