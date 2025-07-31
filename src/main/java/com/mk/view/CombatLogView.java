package com.mk.view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class CombatLogView extends JFrame {
    private final JTextPane logPane;
    private final StyledDocument doc;
    private final Style successStyle;
    private final Style failureStyle;
    private final Style infoStyle;

    public CombatLogView(String title) {
        super(title);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        logPane = new JTextPane();
        logPane.setEditable(false);
        doc = logPane.getStyledDocument();

        // Estilo para ataques exitosos (verde)
        successStyle = logPane.addStyle("SuccessStyle", null);
        StyleConstants.setForeground(successStyle, new Color(0, 128, 0));

        // Estilo para ataques fallidos(rojo)
        failureStyle = logPane.addStyle("FailureStyle", null);
        StyleConstants.setForeground(failureStyle, Color.RED);

        // Estilo para mensajes informativos (gris oscuro)
        infoStyle = logPane.addStyle("InfoStyle", null);
        StyleConstants.setForeground(infoStyle, Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(logPane);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de eventos"));
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }


    public void appendEvent(String event) {
        SwingUtilities.invokeLater(() -> {
            Style style;
            String normalized = event.replace('–', '-'); // unificar guiones
            if (normalized.matches(".*-\\d+ HP.*") && !normalized.matches(".*-0 HP.*")) {
                style = successStyle;
            } else if (normalized.matches(".*(HP).*")) {
                style = failureStyle;
            } else {
                style = infoStyle;
            }
            try {
                doc.insertString(doc.getLength(), event + "\n", style);
                logPane.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }
}
