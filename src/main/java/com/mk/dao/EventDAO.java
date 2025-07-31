package com.mk.dao;

import com.mk.model.Combat;
import com.mk.dao.DBConnection;
import com.mk.model.Player;


import java.time.LocalDateTime;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class EventDAO {


    public static void addEvent(Combat combat){
        String winnerName = combat.getWinnerPlayer().getUsername();
        String loserName = combat.getLoserPlayer().getUsername();
        String datetime = combat.getDatetime().toString();

        String sql = "INSERT INTO events(winner, loser, date) VALUES (?, ?, ?)";

        try(Connection conn = DBConnection.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, winnerName);
            pstmt.setString(2, loserName);
            pstmt.setString(3, datetime);

            pstmt.execute();

        }catch(SQLException e){
         e.printStackTrace();
        }



    }


}
