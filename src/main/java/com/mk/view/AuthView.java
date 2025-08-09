package com.mk.view;

import javax.swing.*;
import java.awt.*;

public class AuthView extends JFrame {

    private JTextField usernameField;        // Campo para ingresar el nombre de usuario
    private JPasswordField passwordField;    // Campo para ingresar la contraseña (con ocultacion)
    private JButton loginButton;             // Boton para iniciar sesion
    private JButton registerButton;          // Boton para registrar nuevo usuario
    private JLabel errorLabel;               // Label para mostrar mensajes de error o exito

    public AuthView() {
        setTitle("Autenticacion");            // Titulo de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierra la aplicacion al cerrar ventana
        setSize(400, 280);                    // Tamaño fijo de la ventana
        setLocationRelativeTo(null);          // Centra la ventana en pantalla
        setResizable(false);                  // Evita que el usuario cambie tamaño
        setLayout(new BorderLayout(10, 10)); // Layout principal con espacios entre componentes

        render();                            // Metodo que crea y organiza los componentes visuales

        setVisible(true);                    // Hace visible la ventana
    }

    private void render() {
        // Cambia el color de fondo del contenedor principal a un gris oscuro personalizado
        getContentPane().setBackground(new Color(30, 30, 30));

        // JLabel que funciona como titulo de la ventana, centrado y con fuente en negrita
        JLabel titleLabel = new JLabel("Ingresa tus credenciales");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Fuente y tamaño
        titleLabel.setForeground(Color.WHITE);                   // Texto blanco para contraste
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);// Centrar texto horizontalmente
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Margenes arriba y abajo
        add(titleLabel, BorderLayout.NORTH);                     // Agrega el titulo arriba en el layout

        // Panel central con GridBagLayout para alinear etiquetas y campos lado a lado con margen
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);                            // Panel transparente para mostrar fondo
        GridBagConstraints gbc = new GridBagConstraints();      // Configura posicion y espaciado
        gbc.insets = new Insets(10, 10, 10, 10);                 // Margen alrededor de cada componente

        // Etiqueta y campo para el nombre de usuario
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;  // Columna 0
        gbc.gridy = 0;  // Fila 0
        centerPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;  // Columna 1 al lado derecho de la etiqueta
        centerPanel.add(usernameField, gbc);

        // Etiqueta y campo para la contraseña
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;  // Columna 0
        gbc.gridy = 1;  // Fila 1 (debajo del username)
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;  // Columna 1
        centerPanel.add(passwordField, gbc);

        // Agrega el panel central con los campos al centro del layout principal
        add(centerPanel, BorderLayout.CENTER);

        // Label para mostrar errores o mensajes al usuario, inicialmente vacio
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);                    // Color rojo para errores
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Espaciado interno

        // Panel para los botones, usando FlowLayout para ponerlos en linea horizontal
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);                           // Panel transparente para fondo

        // Boton para iniciar sesion con estilo personalizado
        loginButton = new JButton("Iniciar sesion");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);                     // Quita borde de foco azul
        loginButton.setBackground(new Color(70, 130, 180));     // Color azul personalizado
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(130, 30));  // Tamaño fijo para uniformidad

        // Boton para registrarse, estilo mas sobrio y discreto
        registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBackground(new Color(50, 50, 50));    // Gris oscuro
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(110, 30));

        // Agrega ambos botones al panel de botones
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Panel inferior que contiene el label de error y los botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(errorLabel, BorderLayout.NORTH);        // Label de error arriba
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);       // Botones abajo

        // Agrega el panel inferior a la parte baja de la ventana principal
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getters para acceder a componentes desde otras clases (controladores)

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    // Metodo para mostrar mensajes de error en rojo en el label de errores
    public void showError(String message) {
        errorLabel.setForeground(Color.RED);
        errorLabel.setText(message);
    }

    // Metodo para mostrar mensajes positivos en verde (ejemplo: exito en login)
    public void showMessage(String message){
        errorLabel.setForeground(Color.GREEN);
        errorLabel.setText(message);
    }

    // Metodo para limpiar cualquier mensaje de error o exito mostrado
    public void clearError() {
        errorLabel.setText(" ");
    }

    // Metodo para limpiar los campos de texto (usuario y password)
    public void clearFields(){
        usernameField.setText("");
        passwordField.setText("");
    }

    // Metodo para mostrar un dialogo emergente cuando el login es exitoso
    public void showSuccessLoginDialog(){
        JOptionPane.showMessageDialog(null,
                "Has iniciado sesion");
    }

}
