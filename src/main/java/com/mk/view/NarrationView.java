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
        // Titulo de la ventana y configuracion basica
        setTitle("MORTAL KOMBAT - Cronica Batalla");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana, no toda la app
        setSize(800, 600); // Tamaño fijo de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Usa un CardLayout para poder mostrar diferentes paneles en la misma ventana intercambiandolos
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // --- Panel de loading (pantalla de carga) ---
        loadingPanel = new JPanel(new BorderLayout()); // Panel principal con BorderLayout
        JPanel centerPanel = new JPanel(new GridBagLayout()); // Panel interno con GridBagLayout para posicionar componentes

        // Etiqueta que indica que se esta analizando el combate y generando narracion
        loadingLabel = new JLabel("Analizando combate y generando narracion..");
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Etiqueta que muestra los nombres de los jugadores y sus luchadores
        JLabel detailsLabel = new JLabel(player1Name + " (" + fighter1 + ") vs " + player2Name + " (" + fighter2 + ")");
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Barra de progreso indeterminada (animacion continua) para indicar carga en proceso
        loadingBar = new JProgressBar();
        loadingBar.setIndeterminate(true); // No muestra progreso concreto, solo animacion
        loadingBar.setStringPainted(true); // Muestra texto sobre la barra
        loadingBar.setString("Cargando..."); // Texto que aparece en la barra

        // Configuracion para posicionar los elementos en el centerPanel con GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre componentes
        centerPanel.add(loadingLabel, gbc);

        gbc.gridy = 1;
        centerPanel.add(detailsLabel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que la barra se expanda horizontalmente
        gbc.weightx = 1.0; // Peso para que ocupe espacio horizontal disponible
        centerPanel.add(loadingBar, gbc);

        // Añade el centerPanel (con las etiquetas y barra) al centro del loadingPanel
        loadingPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Panel de narracion (pantalla que muestra la cronica) ---
        narrationPanel = new JPanel(new BorderLayout());

        // Titulo principal de la cronica arriba, centrado y con margen
        JLabel titleLabel = new JLabel("CRONICA DE LA BATALLA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Area de texto para mostrar la narracion, no editable, con salto automatico de linea
        narrationTextArea = new JTextArea();
        narrationTextArea.setEditable(false);
        narrationTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        narrationTextArea.setLineWrap(true); // Salto de linea automatico
        narrationTextArea.setWrapStyleWord(true); // No cortar palabras a la mitad
        narrationTextArea.setMargin(new Insets(15, 15, 15, 15)); // Margen interno

        // Scroll para que se pueda desplazar si la narracion es larga
        JScrollPane scrollPane = new JScrollPane(narrationTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Boton para cerrar la ventana de la cronica
        closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose()); // Cierra esta ventana al hacer click

        // Panel para poner el boton centrado abajo
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);

        // Organiza los componentes en el panel de narracion con BorderLayout
        narrationPanel.add(titleLabel, BorderLayout.NORTH);
        narrationPanel.add(scrollPane, BorderLayout.CENTER);
        narrationPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Añade los dos paneles al JFrame con identificadores para CardLayout
        add(loadingPanel, "loading");
        add(narrationPanel, "narration");
    }


    private void setupLayout() {
        cardLayout.show(getContentPane(), "loading");
    }

    private void setupStyling() {
        // Define colores personalizados para un mejor estilo visual
        Color darkBlue = new Color(25, 25, 50);
        Color lightGray = new Color(240, 240, 240);
        Color gold = new Color(255, 215, 0);
        Color darkRed = new Color(150, 0, 0);

        // Estilos para el panel de carga
        loadingPanel.setBackground(darkBlue);
        loadingLabel.setForeground(gold);

        // Obtiene la etiqueta de detalles para cambiar color (tiene que castearse)
        JLabel detailsLabel = (JLabel) ((JPanel) loadingPanel.getComponent(0)).getComponent(1);
        detailsLabel.setForeground(Color.WHITE);

        loadingBar.setBackground(darkBlue);
        loadingBar.setForeground(gold);

        // Estilos para el panel de narracion
        narrationPanel.setBackground(lightGray);

        JLabel titleLabel = (JLabel) narrationPanel.getComponent(0);
        titleLabel.setForeground(darkRed);

        narrationTextArea.setBackground(Color.WHITE);
        narrationTextArea.setForeground(Color.BLACK);
        narrationTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

        detailsLabel.setForeground(darkRed);

        // Estilo del boton cerrar
        closeButton.setBackground(darkRed);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);

        // Efecto hover para cambiar el color del boton al pasar el mouse por encima
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