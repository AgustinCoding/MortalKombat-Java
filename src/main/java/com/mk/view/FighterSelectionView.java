package com.mk.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.mk.model.Fighter;
import java.awt.*;
import java.util.ArrayList;

public class FighterSelectionView extends JDialog {
    private Fighter[] fighters;               // Array de luchadores disponibles para seleccionar
    private ArrayList<ImageIcon> images;     // Lista de imagenes cargadas para cada luchador
    private int currentIndex = 0;             // Indice del luchador actualmente seleccionado
    private Fighter currentFighter;           // Luchador actual seleccionado

    private JLabel imageLabel;                // Label para mostrar la imagen del luchador
    private JButton leftButton;               // Boton para seleccionar luchador anterior
    private JButton rightButton;              // Boton para seleccionar luchador siguiente
    private JButton confirmButton;            // Boton para confirmar seleccion

    private JTextArea description;            // Area de texto que muestra detalles del luchador

    public FighterSelectionView(Fighter[] fighters) {
        this.fighters = fighters;
        this.images = new ArrayList<>();
        this.currentFighter = fighters[0];   // Por defecto el primer luchador esta seleccionado

        setTitle("Seleccion de personaje");
        setSize(550, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // Solo se cierra este dialogo, no toda la app
        setLocationRelativeTo(null);                   // Centra la ventana en la pantalla
        setResizable(false);
        setLayout(new BorderLayout(10, 10));          // Espacios entre componentes

        render();          // Crea y organiza los componentes visuales
        loadImages();      // Carga las imagenes de los luchadores en memoria
        setFighter();      // Muestra el primer luchador en pantalla

        setVisible(true);  // Hace visible el dialogo
    }

    private void render() {
        getContentPane().setBackground(new Color(30, 30, 30)); // Fondo oscuro para toda la ventana

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen

        leftButton = new JButton("◄");
        rightButton = new JButton("►");

        // Estilo para los botones de navegacion (izquierda y derecha)
        for (JButton b : new JButton[]{leftButton, rightButton}) {
            b.setFocusPainted(false); // Quita el borde cuando el boton esta enfocado
            b.setBackground(new Color(50, 50, 50)); // Fondo gris oscuro
            b.setForeground(Color.WHITE);            // Texto blanco
        }

        // Panel para los controles de seleccion (botones y la imagen)
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setOpaque(false);  // Para que el fondo sea transparente y se vea el fondo general
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        selectionPanel.add(leftButton, BorderLayout.WEST);    // Boton para luchador anterior a la izquierda
        selectionPanel.add(imageLabel, BorderLayout.CENTER);  // Imagen al centro
        selectionPanel.add(rightButton, BorderLayout.EAST);   // Boton para luchador siguiente a la derecha

        // Area de texto para mostrar la descripcion y detalles del luchador
        description = new JTextArea(3, 30);
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setForeground(Color.WHITE);
        description.setBackground(new Color(45, 45, 45));
        description.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Detalles del personaje"));

        // Scroll para que la descripcion se pueda desplazar si es larga
        JScrollPane scrollPane = new JScrollPane(description);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Detalles del personaje",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                Color.LIGHT_GRAY
        ));
        scrollPane.setBackground(new Color(30, 30, 30)); // Fondo del scroll igual al fondo general

        // Boton para confirmar la seleccion del luchador
        confirmButton = new JButton("Seleccionar");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setBackground(new Color(70, 130, 180));  // Azul para resaltar
        confirmButton.setForeground(Color.WHITE);

        // Panel para contener el boton de confirmacion, centrado abajo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(confirmButton);

        // Organiza los paneles en el dialogo usando BorderLayout
        add(selectionPanel, BorderLayout.NORTH);  // Parte superior con controles e imagen
        add(scrollPane, BorderLayout.CENTER);     // Centro con la descripcion
        add(buttonPanel, BorderLayout.SOUTH);     // Abajo boton de confirmar
    }


    private void loadImages() {
        // Por cada luchador en el array, carga su imagen desde recursos
        for (Fighter f : fighters) {
            String path = f.getImage();  // Obtiene la ruta de la imagen
            java.net.URL url = getClass().getClassLoader().getResource(path);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url); // Carga la imagen si existe
                images.add(icon);
            } else {
                System.out.println("Imagen no encontrada: " + path);
                images.add(null);  // Si no se encontro la imagen, agrega null para no romper el indice
            }
        }
    }

    private void setFighter() {
        // Actualiza el luchador actual segun el indice actual
        currentFighter = fighters[currentIndex];

        // Construye el texto con informacion del luchador para mostrar en la descripcion
        String info = "Nombre: " + currentFighter.getName() + "\n"
                + "Poder base: " + currentFighter.getBaseStrength() + "\n"
                + currentFighter.getDescription();

        description.setText(info);

        // Obtiene la imagen correspondiente y la escala para que quede bien en la UI
        ImageIcon icon = images.get(currentIndex);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setIcon(null);  // Si no hay imagen, limpia el label
        }
    }

    // Getters para los botones, para que el controlador pueda agregar eventos
    public JButton getConfirmButton() {
        return this.confirmButton;
    }

    public JButton getLeftButton() {
        return this.leftButton;
    }

    public JButton getRightButton() {
        return this.rightButton;
    }

    // Avanza al siguiente luchador en la lista y actualiza la vista
    public void nextFighter() {
        currentIndex = (currentIndex + 1) % fighters.length; // Ciclo para ir al primero si pasa del ultimo
        setFighter();
    }

    // Retrocede al luchador anterior, ciclo para ir al ultimo si pasa del primero
    public void previousFighter() {
        currentIndex = (currentIndex - 1 + fighters.length) % fighters.length;
        setFighter();
    }

    // Devuelve el luchador que esta actualmente seleccionado
    public Fighter getCurrentFighter() {
        return fighters[currentIndex];
    }

}
