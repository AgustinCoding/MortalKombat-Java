package com.mk.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/*
ESTA ES UNA CLASE DE REFERENCIA - NO ENTRA EN FLUJO DE PROGRAMA
ESTE PROGRAMA ES LA INTERPRETACION DE ESTA CLASE EN EL PATRON DE DISEÑO MVC
 */


public class MortalKombatController {
    private static final String DB_URL = "jdbc:sqlite:mortal_kombat.db";
    private static Connection connection;
    private static int experiencia = 0;
    private static String jugadorPersonaje;
    private static String jugadorNombre;
    private static final Random random = new Random();
    private static final String[] COMENTARIOS_PELEA = {
            "Scorpion lanzó su kunai mientras Sub-Zero esquivaba con un slide!",
            "Liu Kang conectó un bicycle kick que hizo retroceder a su oponente!",
            "Raiden canalizó un rayo celestial desde los cielos!",
            "Johnny Cage ejecutó su shadow kick con una precisión mortal!",
            "Sonya Blade lanzó un kiss of death que impactó directamente!",
            "Kitana desplegó sus abanicos cortantes en un movimiento letal!",
            "Mileena ejecutó su sai fury con una velocidad impresionante!",
            "Jax conectó un ground pound que hizo temblar la arena!",
            "Kung Lao desató su hat spin con una precisión mortal!",
            "Baraka desgarró a su oponente con sus armas naturales!",
            "Reptile se volvió invisible antes de lanzar un ataque sorpresa!",
            "Shang Tsung robó la forma de su oponente para contraatacar!",
            "Kano ejecutó su knife throw desde una distancia peligrosa!",
            "Noob Saibot invocó su shadow clone para un ataque combinado!",
            "Nightwolf canalizó el espíritu del lobo para aumentar su fuerza!",
            "Sindel desató su banshee scream que rompió los tímpanos!",
            "Frost congeló el suelo creando una trampa mortal!",
            "Jade ejecutó un deadly boomerang que rebotó en los muros!",
            "Shao Kahn aplastó a su oponente con su martillo gigante!",
            "El combate alcanzó su clímax con una serie de ataques furiosos!"
    };

    public static void main(String[] args) {
        try {
            inicializarBaseDatos();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingresa tu nombre: ");
            jugadorNombre = scanner.nextLine().trim();

            ArrayList<Personaje> personajes = cargarPersonajes();
            mostrarPersonajes(personajes);

            System.out.print("Elige tu personaje (número): ");
            int eleccion = scanner.nextInt() - 1;
            Personaje personajeJugador = personajes.get(eleccion);
            jugadorPersonaje = personajeJugador.nombre;
            guardarJugador(jugadorNombre, jugadorPersonaje);
            cargarExperienciaJugador();

            reproducirSonido("mortal_kombat_theme.wav");

            while (true) {
                Personaje oponente;
                do {
                    oponente = personajes.get(random.nextInt(personajes.size()));
                } while (oponente.nombre.equals(jugadorPersonaje));

                mostrarDetallesPersonaje(personajeJugador, "TU PERSONAJE");
                mostrarDetallesPersonaje(oponente, "OPONENTE");

                int fuerzaJugador = random.nextInt(10) + 1 + experiencia + personajeJugador.fuerzaBase;
                int fuerzaOponente = random.nextInt(10) + 1 + oponente.fuerzaBase;

                System.out.println("\n=== MORTAL KOMBAT ===");
                System.out.printf("%s (%s) vs %s (CPU)\n", jugadorNombre, jugadorPersonaje, oponente.nombre);
                System.out.printf("TU FUERZA: %d (Base: %d + Exp: %d)\n", fuerzaJugador, personajeJugador.fuerzaBase, experiencia);
                System.out.printf("FUERZA OPONENTE: %d\n", fuerzaOponente);

                // Comentario aleatorio durante la pelea
                System.out.println("\n[COMBATE] " + COMENTARIOS_PELEA[random.nextInt(COMENTARIOS_PELEA.length)]);

                String resultado;
                if (fuerzaJugador > fuerzaOponente) {
                    resultado = "GANÓ";
                    System.out.println("\n¡FATALITY! ¡GANASTE! +2 de experiencia");
                    experiencia += 2;
                    actualizarExperiencia();
                    reproducirSonido("winner.wav");
                } else if (fuerzaOponente > fuerzaJugador) {
                    resultado = "PERDIÓ";
                    System.out.println("\n¡FLAWLESS VICTORY! Perdiste...");
                    reproducirSonido("looser.wav");
                } else {
                    resultado = "EMPATE";
                    System.out.println("\n¡DOUBLE FATALITY! Empate!");
                }

                grabarEvento(jugadorNombre, personajeJugador.id, oponente.id, resultado,
                        Math.max(fuerzaJugador, fuerzaOponente));

                // Mensaje "Finish Him/Her" después de 4 segundos
                Thread.sleep(4000);
                if (oponente.genero.equals("M")) {
                    System.out.println("\n=== FINISH HIM! ===");
                } else {
                    System.out.println("\n=== FINISH HER! ===");
                }
                reproducirSonido("finish_him.wav");

                // Anunciar resultado final
                Thread.sleep(2000);
                if (resultado.equals("GANÓ")) {
                    System.out.println("\n██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗ ██████╗ ");
                    System.out.println("██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗╚════██╗");
                    System.out.println("██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝ █████╔╝");
                    System.out.println("██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗ ╚═══██╗");
                    System.out.println("╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║██████╔╝");
                    System.out.println(" ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝╚═════╝ ");
                } else {
                    System.out.println("\n██╗      ██████╗  ██████╗ ███████╗███████╗██████╗ ██████╗ ");
                    System.out.println("██║     ██╔═══██╗██╔═══██╗██╔════╝██╔════╝██╔══██╗╚════██╗");
                    System.out.println("██║     ██║   ██║██║   ██║███████╗█████╗  ██████╔╝ █████╔╝");
                    System.out.println("██║     ██║   ██║██║   ██║╚════██║██╔══╝  ██╔══██╗ ╚═══██╗");
                    System.out.println("███████╗╚██████╔╝╚██████╔╝███████║███████╗██║  ██║██████╔╝");
                    System.out.println("╚══════╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝╚═════╝ ");
                }

                System.out.print("\n¿Nuevo combate? (s/n): ");
                if (!scanner.next().equalsIgnoreCase("s")) break;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inicialización de la base de datos
    private static void inicializarBaseDatos() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();

        // Tabla de jugadores
        stmt.execute("CREATE TABLE IF NOT EXISTS jugadores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "personaje TEXT NOT NULL, " +
                "experiencia INTEGER DEFAULT 0, " +
                "UNIQUE(nombre, personaje))");

        // Tabla de personajes
        stmt.execute("CREATE TABLE IF NOT EXISTS personajes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT UNIQUE NOT NULL, " +
                "fuerza_base INTEGER NOT NULL, " +
                "genero TEXT NOT NULL, " +
                "imagen TEXT NOT NULL, " +
                "descripcion TEXT)");

        // Tabla de eventos
        stmt.execute("CREATE TABLE IF NOT EXISTS eventos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "jugador_nombre TEXT NOT NULL, " +
                "personaje_id INTEGER NOT NULL, " +
                "oponente_id INTEGER NOT NULL, " +
                "resultado TEXT NOT NULL, " +
                "nivel_fuerza INTEGER NOT NULL)");

        // Insertar personajes si no existen
        if (!existenPersonajes()) {
            insertarPersonajes();
        }
        stmt.close();
    }

    // Verificar si ya existen personajes
    private static boolean existenPersonajes() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM personajes");
        return rs.getInt("count") > 0;
    }

    // Insertar los 20 personajes equilibrados
    private static void insertarPersonajes() throws SQLException {
        String[][] personajes = {
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

        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO personajes(nombre, fuerza_base, genero, imagen, descripcion) " +
                        "VALUES(?, ?, ?, ?, ?)");

        for (String[] p : personajes) {
            pstmt.setString(1, p[0]);
            pstmt.setInt(2, Integer.parseInt(p[1]));
            pstmt.setString(3, p[2]);
            pstmt.setString(4, p[3]);
            pstmt.setString(5, p[4]);
            pstmt.executeUpdate();
        }
        pstmt.close();
    }

    // Cargar personajes desde la base de datos
    private static ArrayList<Personaje> cargarPersonajes() throws SQLException {
        ArrayList<Personaje> lista = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM personajes");

        while (rs.next()) {
            lista.add(new Personaje(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("fuerza_base"),
                    rs.getString("genero"),
                    rs.getString("imagen"),
                    rs.getString("descripcion")
            ));
        }
        rs.close();
        stmt.close();
        return lista;
    }

    // Mostrar lista de personajes disponibles
    private static void mostrarPersonajes(ArrayList<Personaje> personajes) {
        System.out.println("\n=== SELECCIONA TU LUCHADOR ===");
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            System.out.printf("%2d. %-12s (Fuerza: %d | Género: %s)\n",
                    i+1, p.nombre, p.fuerzaBase, p.genero);
        }
    }

    // Mostrar detalles de un personaje con imagen
    private static void mostrarDetallesPersonaje(Personaje p, String titulo) {
        System.out.println("\n=== " + titulo + " ===");
        System.out.println("Nombre: " + p.nombre);
        System.out.println("Fuerza base: " + p.fuerzaBase);
        System.out.println("Género: " + p.genero);
        System.out.println("Descripción: " + p.descripcion);

        try {
            BufferedImage img = ImageIO.read(new File(p.imagen));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 300, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);
            JOptionPane.showMessageDialog(null, label, p.nombre, JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            System.out.println("Imagen no encontrada: " + p.imagen);
        }
    }

    // Reproducir efectos de sonido
    private static void reproducirSonido(String archivo) {
        new Thread(() -> {
            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    new File(archivo).getAbsoluteFile())) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (Exception e) {
                System.out.println("Error al reproducir sonido: " + e.getMessage());
            }
        }).start();
    }

    // Guardar jugador en la base de datos
    private static void guardarJugador(String nombre, String personaje) throws SQLException {
        String sql = "INSERT OR IGNORE INTO jugadores(nombre, personaje) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, personaje);
            pstmt.executeUpdate();
        }
    }

    // Cargar experiencia del jugador
    private static void cargarExperienciaJugador() throws SQLException {
        String sql = "SELECT experiencia FROM jugadores WHERE nombre = ? AND personaje = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jugadorNombre);
            pstmt.setString(2, jugadorPersonaje);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) experiencia = rs.getInt("experiencia");
        }
    }

    // Actualizar experiencia del jugador
    private static void actualizarExperiencia() throws SQLException {
        String sql = "UPDATE jugadores SET experiencia = ? WHERE nombre = ? AND personaje = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, experiencia);
            pstmt.setString(2, jugadorNombre);
            pstmt.setString(3, jugadorPersonaje);
            pstmt.executeUpdate();
        }
    }

    // Registrar evento de combate
    private static void grabarEvento(String jugador, int personajeId, int oponenteId,
                                     String resultado, int nivelFuerza) throws SQLException {
        String sql = "INSERT INTO eventos(jugador_nombre, personaje_id, oponente_id, resultado, nivel_fuerza) " +
                "VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, jugador);
            pstmt.setInt(2, personajeId);
            pstmt.setInt(3, oponenteId);
            pstmt.setString(4, resultado);
            pstmt.setInt(5, nivelFuerza);
            pstmt.executeUpdate();
        }
    }

    // Clase interna para representar personajes
    static class Personaje {
        int id;
        String nombre;
        int fuerzaBase;
        String genero;
        String imagen;
        String descripcion;

        Personaje(int id, String nombre, int fuerzaBase, String genero, String imagen, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.fuerzaBase = fuerzaBase;
            this.genero = genero;
            this.imagen = imagen;
            this.descripcion = descripcion;
        }
    }
}