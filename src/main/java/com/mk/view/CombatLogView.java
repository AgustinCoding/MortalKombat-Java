package com.mk.view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class CombatLogView extends JFrame {
    private final JTextPane logPane;          // Componente para mostrar texto con estilos
    private final StyledDocument doc;         // Documento que maneja el contenido con estilos
    private final Style successStyle;         // Estilo para ataques exitosos (color verde)
    private final Style failureStyle;         // Estilo para ataques fallidos (color rojo)
    private final Style infoStyle;            // Estilo para mensajes informativos (gris oscuro)

    public CombatLogView(String title) {
        super(title);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        logPane = new JTextPane();
        logPane.setEditable(false);           // El usuario no puede editar el registro
        doc = logPane.getStyledDocument();    // Obtiene el documento editable con estilos

        // Definicion del estilo para ataques exitosos: texto verde
        successStyle = logPane.addStyle("SuccessStyle", null);
        StyleConstants.setForeground(successStyle, new Color(0, 128, 0));  // Verde oscuro

        // Definicion del estilo para ataques fallidos: texto rojo
        failureStyle = logPane.addStyle("FailureStyle", null);
        StyleConstants.setForeground(failureStyle, Color.RED);

        // Definicion del estilo para mensajes informativos: texto gris oscuro
        infoStyle = logPane.addStyle("InfoStyle", null);
        StyleConstants.setForeground(infoStyle, Color.DARK_GRAY);

        // JScrollPane para añadir scroll al JTextPane y borde con titulo
        JScrollPane scroll = new JScrollPane(logPane);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de eventos"));
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }


    public void appendEvent(String event) {
        // Usamos invokeLater para asegurar que la actualizacion del GUI
        // se haga en el hilo correcto (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            Style style;

            // Unificamos los guiones para evitar inconsistencias en los textos
            String normalized = event.replace('–', '-');

            // Condicion para asignar estilo segun el contenido del texto
            if (normalized.matches(".*-\\d+ HP.*") && !normalized.matches(".*-0 HP.*")) {
                // Si el evento indica daño real (menos HP distinto de 0), se pone en verde
                style = successStyle;
            } else if (normalized.matches(".*(HP).*")) {
                // Si menciona HP pero sin daño (ej: bloqueo), se pone en rojo
                style = failureStyle;
            } else {
                // Para cualquier otro mensaje informativo se usa gris oscuro
                style = infoStyle;
            }

            try {
                // Inserta el texto al final del documento con el estilo correspondiente
                doc.insertString(doc.getLength(), event + "\n", style);

                // Desplaza el scroll para que el ultimo evento sea visible
                logPane.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();  // Imprime error si hay problema con el documento
            }
        });
    }
}
