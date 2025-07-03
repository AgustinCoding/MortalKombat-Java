package com.mk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mk.dao.*;
import com.mk.utils.PasswordHasher;

public class PlayerDAO {
    // Registar jugador
    // Iniciar sesion - cargar jugador
    // checkear contraseña
    // getters, setters

    public static void registerUser(String username, String pwd){
        try(Connection conn = DBConnection.getConnection()){
            String sql = "INSERT INTO players(username, password) VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, pwd);
            stmt.execute();


        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static void checkPwd(String username, String pwd){


    }

    public static void loadPlayer(String username){

    }

    public static boolean userExists(String username){
        try(Connection conn = DBConnection.getConnection()){
            String sql = "SELECT 1 FROM players WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }


}
