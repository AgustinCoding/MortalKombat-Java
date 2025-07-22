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
        getContentPane().setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Ingresa tus credenciales");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(18);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Iniciar sesión");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(130, 30));

        registerButton = new JButton("Registrarse");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setFocusPainted(false);
        registerButton.setBackground(new Color(50, 50, 50));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(110, 30));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
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


    // Metodo para mostrar errores en rojo
    public void showError(String message) {
        errorLabel.setForeground(Color.RED);
        errorLabel.setText(message);
    }

    // Metodo para mostrar mensajes en verde
    public void showMessage(String message){
        errorLabel.setForeground(Color.GREEN);
        errorLabel.setText(message);
    }

    // Metodo para limpiar errores
    public void clearError() {
        errorLabel.setText(" ");
    }

    // Metodo para limpiar texto en campos de input
    public void clearFields(){
        usernameField.setText("");
        passwordField.setText("");
    }

    public void showSuccessLoginDialog(){
        JOptionPane.showMessageDialog(null,
                "Has iniciado sesion");
    }

}