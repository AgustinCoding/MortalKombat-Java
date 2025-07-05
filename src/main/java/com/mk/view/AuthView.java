package com.mk.view;

import javax.swing.*;
import java.awt.*;

public class AuthView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel errorLabel;

    public AuthView() {
        setTitle("Autenticación");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        render();

        setVisible(true);
    }

    private void render() {
        // Panel superior con título
        JLabel titleLabel = new JLabel("Ingresa tus credenciales");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel central con campos de texto
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Campo username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        // Campo password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Panel para mostrar errores
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout());

        loginButton = new JButton("Iniciar sesión");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(130, 30));

        registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(110, 30));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Panel que combina error y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(errorLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);


        add(bottomPanel, BorderLayout.SOUTH);
    }

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

    // Metodo para mostrar errores
    public void showError(String message) {
        errorLabel.setText(message);
    }

    // Metodo para limpiar errores
    public void clearError() {
        errorLabel.setText(" ");
    }

    public static void main(String[] args) {
        new AuthView();
    }
}