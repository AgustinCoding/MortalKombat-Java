package com.mk.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class InitDB {


    public static void initAll(){
        try(Connection conn = DBConnection.getConnection()){
            InitPlayerDB.init(conn);
            InitFightersDB.init(conn);

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
