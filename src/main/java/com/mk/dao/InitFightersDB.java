    package com.mk.dao;

    import com.mk.dao.DBConnection;

    import java.sql.*;

    public class InitFightersDB {

        private final static String[][] stringCharacters = {
                {"Scorpion", "7", "M", "images/scorpion.png", "Ninja espectral con kunai ardiente"},
                {"Sub-Zero", "7", "M", "images/subzero.png", "Maestro del hielo de Lin Kuei"},
                {"Liu Kang", "8", "M", "images/liukang.png", "Monje shaolin campeón de Mortal Kombat"},
                {"Raiden", "9", "M", "images/raiden.png", "Dios del trueno protector de la Tierra"},
                {"Johnny Cage", "6", "M", "images/johnnycage.png", "Actor famoso con poderes ancestrales"},
                {"Sonya Blade", "6", "F", "images/sonyablade.png", "Líder de las fuerzas especiales"},
                {"Kitana", "7", "F", "images/kitana.png", "Princesa de Edenia con abanicos mortales"},
                {"Mileena", "7", "F", "images/mileena.png", "Clon híbrido de Kitana con hambre de sangre"},
                {"Jax", "8", "M", "images/jax.png", "Soldado con implantes biónicos en sus brazos"},
                {"Kung Lao", "7", "M", "images/kunglao.png", "Maestro shaolin con sombrero cortante"},
                {"Baraka", "8", "M", "images/baraka.png", "Guerrero tarkatan con cuchillas naturales"},
                {"Reptile", "6", "M", "images/reptile.png", "Ninja reptiliano con habilidades de camuflaje"},
                {"Shang Tsung", "9", "M", "images/shangtsung.png", "Hechicero cambiaformas de Outworld"},
                {"Kano", "6", "M", "images/kano.png", "Mercenario líder del Dragón Negro"},
                {"Noob Saibot", "8", "M", "images/noobsaibot.png", "Espectro oscuro maestro de las sombras"},
                {"Nightwolf", "7", "M", "images/nightwolf.png", "Chamán nativo americano con poderes espirituales"},
                {"Sindel", "8", "F", "images/sindel.png", "Reina de Edenia con grito mortal"},
                {"Frost", "6", "F", "images/frost.png", "Aprendiz de Sub-Zero con poderes criogénicos"},
                {"Jade", "7", "F", "images/jade.png", "Guerrera leal a Kitana con bastón de combate"},
                {"Shao Kahn", "10", "M", "images/shaokahn.png", "Emperador tiránico de Outworld"}
        };


        public static void init(Connection conn) throws SQLException {

            // CREAR LA TABLA
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS fighters(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        power INTEGER,
                        gender TEXT,
                        imagepath TEXT,
                        description TEXT)
                    """;
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);

            // INSERTAR PERSONAJES
            String insertSQL = "INSERT INTO fighters (name, power, gender, imagepath, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // VERIFICAR SI PERSONAJES NO EXISTEN
            String countQuery = "SELECT COUNT(*) FROM fighters";
            ResultSet rs = stmt.executeQuery(countQuery);
            if (rs.next() && rs.getInt(1) == 0) {
                // SOLO INSERTAR SI LA TABLA ESTA VACIA
                for(String[] character : stringCharacters){
                    pstmt.setString(1, character[0]);
                    pstmt.setInt(2, Integer.parseInt(character[1]));
                    pstmt.setString(3, character[2]);
                    pstmt.setString(4, character[3]);
                    pstmt.setString(5, character[4]);
                    pstmt.executeUpdate();
                }
            }





    }
    }
