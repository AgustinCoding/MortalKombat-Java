package com.mk.view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField nameField;
    private JButton confirmButton;

    public LoginView() {
        setTitle("Carga de usuario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        render();

        setVisible(true);
    }

    private void render() {
        // Panel superior con título
        JLabel titleLabel = new JLabel("Ingresa tu nombre");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel central con campo de texto
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        nameField = new JTextField(18);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(nameField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón
        confirmButton = new JButton("Continuar");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(120, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
