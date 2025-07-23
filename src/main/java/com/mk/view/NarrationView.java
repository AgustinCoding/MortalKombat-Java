package com.mk.view;

import javax.swing.*;
import java.awt.*;

public class NarrationView extends JFrame {

    private JTextArea narrationTextArea;
    private JPanel loadingPanel;
    private JPanel narrationPanel;
    private JButton closeButton;
    private JProgressBar loadingBar;
    private JLabel loadingLabel;
    private CardLayout cardLayout;

    public NarrationView(String player1Name, String player2Name, String fighter1, String fighter2) {
        initializeComponents(player1Name, player2Name, fighter1, fighter2);
        setupLayout();
        setupStyling();
        setVisible(true);
    }

    private void initializeComponents(String player1Name, String player2Name, String fighter1, String fighter2) {
        setTitle("MORTAL KOMBAT - Cronica Batalla");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Panel de loading
        loadingPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());

        loadingLabel = new JLabel("Generando narracion con magia negra y macumba..");
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel detailsLabel = new JLabel(player1Name + " (" + fighter1 + ") vs " + player2Name + " (" + fighter2 + ")");
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        loadingBar = new JProgressBar();
        loadingBar.setIndeterminate(true);
        loadingBar.setStringPainted(true);
        loadingBar.setString("Cargando...");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(loadingLabel, gbc);

        gbc.gridy = 1;
        centerPanel.add(detailsLabel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        centerPanel.add(loadingBar, gbc);

        loadingPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel de narración
        narrationPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("CRONICA DE LA BATALLA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        narrationTextArea = new JTextArea();
        narrationTextArea.setEditable(false);
        narrationTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        narrationTextArea.setLineWrap(true);
        narrationTextArea.setWrapStyleWord(true);
        narrationTextArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(narrationTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);

        narrationPanel.add(titleLabel, BorderLayout.NORTH);
        narrationPanel.add(scrollPane, BorderLayout.CENTER);
        narrationPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(loadingPanel, "loading");
        add(narrationPanel, "narration");
    }

    private void setupLayout() {
        cardLayout.show(getContentPane(), "loading");
    }

    private void setupStyling() {
        // Colores
        Color darkBlue = new Color(25, 25, 50);
        Color lightGray = new Color(240, 240, 240);
        Color gold = new Color(255, 215, 0);
        Color darkRed = new Color(150, 0, 0);

        // Panel de loading
        loadingPanel.setBackground(darkBlue);
        loadingLabel.setForeground(gold);

        JLabel detailsLabel = (JLabel) ((JPanel) loadingPanel.getComponent(0)).getComponent(1);
        detailsLabel.setForeground(Color.WHITE);

        loadingBar.setBackground(darkBlue);
        loadingBar.setForeground(gold);

        // Panel de narración
        narrationPanel.setBackground(lightGray);

        JLabel titleLabel = (JLabel) narrationPanel.getComponent(0);
        titleLabel.setForeground(darkRed);

        narrationTextArea.setBackground(Color.WHITE);
        narrationTextArea.setForeground(Color.BLACK);
        narrationTextArea.setBorder(BorderFactory.createLoweredBevelBorder());


        // Botón
        closeButton.setBackground(darkRed);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);

        // Hover effect simple
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(180, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(darkRed);
            }
        });
    }

    public void showNarration(String narrationText) {
        SwingUtilities.invokeLater(() -> {
            narrationTextArea.setText(narrationText);
            narrationTextArea.setCaretPosition(0);
            cardLayout.show(getContentPane(), "narration");
        });
    }
}