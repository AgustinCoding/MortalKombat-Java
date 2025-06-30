package com.mk.dao;

import com.mk.dao.DBConnection;
import com.mk.model.Fighter;

import java.sql.*;
import java.util.ArrayList;


public class FighterDAO {
    // CLASE CON METODOS ESTATICOS
    // getByName
    // getByID
    // getAllString -> ArrayList<String>
    // getAllObjects -> ArrayList<Fighter>
    // setNewFighter -> void, params:
    // updateFighter -> void, params: ID, String tipoDatoAReemplazar, String nuevoDato (Si es int se parsea)
    // deleteFighter -> void, params: ID
    // deleteFighter -> void, params: String name |||  Overload 1

    /* Se elimina porque no es util, ademas, mezcla logica de presentacion con acceso a datos (Rompe el patron; DAO)
    public static ArrayList<String[]> getAllString() {
        ArrayList<String[]> stringArray = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fighters");

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    do {
                        String[] fighter = new String[6];
                        fighter[0] = rs.getString("name");
                        fighter[1] = "" + rs.getInt("power");
                        fighter[2] = rs.getString("gender");
                        fighter[3] = rs.getString("image");
                        fighter[4] = rs.getString("description");
                        fighter[5] = "" + rs.getInt("id");

                        stringArray.add(fighter);

                    } while (rs.next());
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return stringArray;
    }
*/

    public static ArrayList<Fighter> getAllObjects(){
        ArrayList<Fighter> objectArray = new ArrayList<>();

        try(Connection conn = DBConnection.getConnection()){
            if(conn != null) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fighters");

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    do {
                        objectArray.add(new Fighter(
                                rs.getString("name"),
                                rs.getInt("power"),
                                rs.getString("gender"),
                                rs.getString("image"),
                                rs.getString("description"),
                                rs.getInt("id")
                        ));
                    } while (rs.next());
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return objectArray;

    }

    public static Fighter getByID(int id){
        Fighter fighter = null;
        try(Connection conn = DBConnection.getConnection()){
            String sql = "SELECT * FROM fighters WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                fighter = new Fighter(
                        rs.getString("name"),
                        rs.getInt("power"),
                        rs.getString("gender"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getInt("id")
                );
            }else{
                System.out.println("Personaje no encontrado");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return fighter;
    }

    public static Fighter getByName(String name){
        Fighter fighter = null;
        try(Connection conn = DBConnection.getConnection()){
            String sql = "SELECT * FROM fighters WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                fighter = new Fighter(
                        rs.getString("name"),
                        rs.getInt("power"),
                        rs.getString("gender"),
                        rs.getString("image"),
                        rs.getString("description"),
                        rs.getInt("id")
                );
            }else{
                System.out.println("Personaje no encontrado");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return fighter;
    }



}
