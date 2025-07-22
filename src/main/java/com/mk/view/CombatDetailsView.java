package com.mk.view;

import com.mk.model.Player;
import com.mk.model.Fighter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CombatDetailsView extends JFrame {
    private final Player player;
    private final Player opponent;

    public CombatDetailsView(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        render();
    }

    private void render() {
        setTitle("Detalles del Combate");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Usamos GridBag para dar más control de proporciones
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        // Panel izquierdo (player)
        c.gridx = 0;
        c.weightx = 0.45;
        mainPanel.add(createFighterPanel(player), c);

        // VS en el centro
        c.gridx = 1;
        c.weightx = 0.10;
        JLabel vs = createImageLabel("images/vs.png");
        if (vs == null) {
            vs = new JLabel("VS", SwingConstants.CENTER);
            vs.setFont(new Font("Impact", Font.BOLD, 48));
            vs.setForeground(Color.RED);
        }
        mainPanel.add(vs, c);

        // Panel derecho (opponent)
        c.gridx = 2;
        c.weightx = 0.45;
        mainPanel.add(createFighterPanel(opponent), c);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFighterPanel(Player p) {
        Fighter f = p.getSelectedFighter();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Imagen escalada
        JLabel img = createImageLabel(f.getImage());
        if (img != null) {
            ImageIcon icon = (ImageIcon)img.getIcon();
            Image scaled = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(scaled));
            img.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(img);
        }

        panel.add(Box.createVerticalStrut(10));

        // Nombre del personaje
        JLabel name = new JLabel(f.getName(), SwingConstants.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 20));
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(name);

        // Username
        JLabel user = new JLabel("User: " + p.getUsername(), SwingConstants.CENTER);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        user.setForeground(Color.LIGHT_GRAY);
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(user);

        // Experiencia
        JLabel exp = new JLabel("EXP: " + p.getExp(), SwingConstants.CENTER);
        exp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        exp.setForeground(Color.LIGHT_GRAY);
        exp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exp);

        return panel;
    }

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
