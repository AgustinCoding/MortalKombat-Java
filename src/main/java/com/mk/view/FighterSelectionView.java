package com.mk.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.mk.model.Fighter;
import java.awt.*;
import java.util.ArrayList;

public class FighterSelectionView extends JDialog {
    private Fighter[] fighters;
    private ArrayList<ImageIcon> images;
    private int currentIndex = 0;
    private Fighter currentFighter;

    private JLabel imageLabel;
    private JButton leftButton;
    private JButton rightButton;
    private JButton confirmButton;

    private JTextArea description;

    public FighterSelectionView(Fighter[] fighters) {
        this.fighters = fighters;
        this.images = new ArrayList<>();
        this.currentFighter = fighters[0];

        setTitle("Selección de personaje");
        setSize(550, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        render();
        loadImages();
        setFighter();

        setVisible(true);
    }

    private void render() {
        getContentPane().setBackground(new Color(30, 30, 30));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        leftButton = new JButton("◄");
        rightButton = new JButton("►");

        for (JButton b : new JButton[]{leftButton, rightButton}) {
            b.setFocusPainted(false);
            b.setBackground(new Color(50, 50, 50));
            b.setForeground(Color.WHITE);
        }

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setOpaque(false);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        selectionPanel.add(leftButton, BorderLayout.WEST);
        selectionPanel.add(imageLabel, BorderLayout.CENTER);
        selectionPanel.add(rightButton, BorderLayout.EAST);

        description = new JTextArea(3, 30);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(Color.WHITE);
        description.setBackground(new Color(45, 45, 45));
        description.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Detalles del personaje"));

        JScrollPane scrollPane = new JScrollPane(description);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Detalles del personaje",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                Color.LIGHT_GRAY
        ));

        scrollPane.setBackground(new Color(30, 30, 30));

        confirmButton = new JButton("Seleccionar");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setBackground(new Color(70, 130, 180));
        confirmButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(confirmButton);

        add(selectionPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void loadImages() {
        for (Fighter f : fighters) {
            String path = f.getImage();
            java.net.URL url = getClass().getClassLoader().getResource(path);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                images.add(icon);
            } else {
                System.out.println("Imagen no encontrada: " + path);
                images.add(null);
            }
        }
    }

    private void setFighter() {
        currentFighter = fighters[currentIndex];

        String info = "Nombre: " + currentFighter.getName() + "\n"
                + "Poder base: " + currentFighter.getBaseStrength() + "\n"
                + currentFighter.getDescription();

        description.setText(info);

        ImageIcon icon = images.get(currentIndex);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setIcon(null);
        }
    }

    public JButton getConfirmButton() {
        return this.confirmButton;
    }

    public JButton getLeftButton() {
        return this.leftButton;
    }

    public JButton getRightButton() {
        return this.rightButton;
    }

    public void nextFighter() {
        currentIndex = (currentIndex + 1) % fighters.length;
        setFighter();
    }

    public void previousFighter() {
        currentIndex = (currentIndex - 1 + fighters.length) % fighters.length;
        setFighter();
    }

    public Fighter getCurrentFighter() {
        return fighters[currentIndex];
    }



}
