package com.mk.view;

import com.mk.model.Player;
import com.mk.model.Fighter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CombatDetailsView extends JFrame {
    private final Player player;    // Jugador principal
    private final Player opponent;  // Oponente del jugador

    public CombatDetailsView(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        render();  // Metodo que crea y muestra la interfaz
    }

    private void render() {
        setTitle("Detalles del Combate");    // Titulo de la ventana
        setSize(900, 450);                   // Tamaño fijo de la ventana
        setLocationRelativeTo(null);         // Centrar en pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana

        // Panel principal con GridBagLayout para controlar proporciones y posicion
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.DARK_GRAY); // Fondo gris oscuro para contraste

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;   // Componentes ocupan todo el espacio disponible
        c.gridy = 0;                        // Solo una fila
        c.insets = new Insets(10, 10, 10, 10); // Margen entre componentes

        // Panel izquierdo con info del jugador principal
        c.gridx = 0;                       // Columna 0 (izquierda)
        c.weightx = 0.45;                 // Peso relativo del espacio (45%)
        mainPanel.add(createFighterPanel(player), c);

        // Etiqueta "VS" en el centro, con peso menor para espacio reducido
        c.gridx = 1;                      // Columna 1 (centro)
        c.weightx = 0.10;                // Peso 10%
        JLabel vs = createImageLabel("images/vs.png");  // Intenta cargar imagen VS
        if (vs == null) {                // Si no existe la imagen, pone texto en rojo grande
            vs = new JLabel("VS", SwingConstants.CENTER);
            vs.setFont(new Font("Impact", Font.BOLD, 48));
            vs.setForeground(Color.RED);
        }
        mainPanel.add(vs, c);

        // Panel derecho con info del oponente
        c.gridx = 2;                     // Columna 2 (derecha)
        c.weightx = 0.45;               // Peso 45%
        mainPanel.add(createFighterPanel(opponent), c);

        add(mainPanel);                 // Agrega panel principal al JFrame
        setVisible(true);              // Muestra la ventana
    }

    // Metodo que crea el panel con la informacion de un jugador y su luchador seleccionado
    private JPanel createFighterPanel(Player p) {
        Fighter f = p.getSelectedFighter();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Organiza verticalmente
        panel.setBackground(Color.GRAY);                          // Fondo gris para panel
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Borde blanco de 2px
        panel.setAlignmentY(Component.TOP_ALIGNMENT);             // Alineacion arriba

        // Imagen del luchador escalada a 250x300 pixeles para uniformidad visual
        JLabel img = createImageLabel(f.getImage());
        if (img != null) {
            ImageIcon icon = (ImageIcon)img.getIcon();
            Image scaled = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(scaled));
            img.setAlignmentX(Component.CENTER_ALIGNMENT);      // Centrar horizontalmente
            panel.add(img);
        }

        panel.add(Box.createVerticalStrut(10));                  // Espacio vertical entre imagen y texto

        // Nombre del luchador centrado y en negrita grande
        JLabel name = new JLabel(f.getName(), SwingConstants.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(name);

        // Nombre de usuario del jugador, en gris claro y fuente mediana
        JLabel user = new JLabel("User: " + p.getUsername(), SwingConstants.CENTER);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        user.setForeground(Color.LIGHT_GRAY);
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(user);

        // Experiencia del jugador, con estilo similar al usuario
        JLabel exp = new JLabel("EXP: " + p.getExp(), SwingConstants.CENTER);
        exp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        exp.setForeground(Color.LIGHT_GRAY);
        exp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exp);

        return panel; // Retorna el panel completo con toda la info
    }

    // Metodo que crea un JLabel con la imagen dada, busca en recursos y si no existe devuelve null
    private JLabel createImageLabel(String imagePath) {
        URL url = getClass().getClassLoader().getResource(imagePath);
        if (url != null) {
            return new JLabel(new ImageIcon(url), SwingConstants.CENTER);
        } else {
            System.err.println("No se pudo cargar la imagen: " + imagePath);
            return null;
        }
    }
}
