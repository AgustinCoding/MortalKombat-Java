package com.mk.view;

import javax.swing.*;
import java.awt.*;

public class CombatLogView extends JFrame {
    private final JTextArea logArea;

    public CombatLogView(String title) {
        super(title);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de eventos"));
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }

    /** Añade un evento al log y hace scroll automático */
    public void appendEvent(String event) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(event + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}
