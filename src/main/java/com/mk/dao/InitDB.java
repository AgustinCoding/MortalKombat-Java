package com.mk.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class InitDB {


    public static void initAll(){
        try(Connection conn = DBConnection.getConnection()){
            assert(conn != null);
            InitPlayerDB.init(conn);
            InitFightersDB.init(conn);
            InitEventsDB.init(conn);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
