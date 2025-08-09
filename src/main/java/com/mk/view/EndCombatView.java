package com.mk.view;

import com.mk.model.Player;
import com.mk.utils.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class EndCombatView {
    // Constantes para los sonidos usados en el fin del combate
    private static final String FINISH_SOUND = "finish_him.wav";
    private static final String FATALITY_SOUND = "fatality.wav";

    private JFrame winnerFrame;  // Ventana para mostrar el ganador
    private JFrame loserFrame;   // Ventana para mostrar el perdedor

    public EndCombatView(Player winner, Player loser) {
        // Ejecuta todo en el hilo de la interfaz grafica
        SwingUtilities.invokeLater(() -> {
            SoundPlayer.play(FINISH_SOUND);  // Reproduce sonido inicial "Finish Him"
            showWinner(winner);               // Muestra ventana del ganador
            showLoser(loser);                 // Muestra ventana del perdedor

            // Timer que tras 2 segundos inicia la animacion y sonido de "Fatality"
            Timer fatalityStarter = new Timer(2000, e -> {
                SoundPlayer.stop();           // Para sonidos anteriores (por si estan en loop)
                SoundPlayer.play(FATALITY_SOUND);  // Reproduce sonido "Fatality"
                animateFatality();            // Ejecuta la animacion de desaparicion del perdedor
                showFatalityText();           // Muestra texto animado "FATALITY!" en ventana ganador

                ((Timer)e.getSource()).stop();  // Detiene este timer para que no se repita
            });
            fatalityStarter.setRepeats(false); // No repetir la ejecucion del timer
            fatalityStarter.start();
        });
    }

    private void showWinner(Player winner) {
        // Configura la ventana para el ganador, sin barra de titulo y tamaño fijo
        winnerFrame = new JFrame("¡Victoria!");
        winnerFrame.setUndecorated(true);
        winnerFrame.setSize(400, 500);
        winnerFrame.setLocationRelativeTo(null);  // Centrada en pantalla

        JPanel cp = new JPanel(new BorderLayout());
        cp.setBackground(Color.BLACK);
        cp.setBorder(new LineBorder(new Color(212, 175, 55), 5)); // Borde dorado
        winnerFrame.setContentPane(cp);

        // Carga y muestra la imagen del luchador ganador, escalada a 350x350 px
        String imgPath = winner.getSelectedFighter().getImage();
        URL url = getClass().getClassLoader().getResource(imgPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            JLabel pic = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            cp.add(pic, BorderLayout.CENTER);
        }

        // Texto con el nombre del ganador y estilo dorado abajo
        JLabel name = new JLabel(winner.getUsername() + " GANA", SwingConstants.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 24));
        name.setForeground(new Color(212, 175, 55));
        name.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        cp.add(name, BorderLayout.SOUTH);

        winnerFrame.setVisible(true);
    }

    private void showLoser(Player loser) {
        // Configura ventana para el perdedor, sin decoracion y tamaño fijo
        loserFrame = new JFrame();
        loserFrame.setUndecorated(true);
        loserFrame.setSize(300, 380);

        // Posiciona la ventana abajo a la derecha del centro de pantalla
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width / 2 + 200;
        int y = screen.height / 2 + 50;
        loserFrame.setLocation(x, y);

        // Panel con fondo semitransparente personalizado (con paintComponent)
        JPanel cp = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180)); // Negro semi-transparente
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cp.setOpaque(false);
        cp.setBorder(new LineBorder(Color.DARK_GRAY, 3));
        loserFrame.setContentPane(cp);

        // Carga y muestra imagen del luchador perdedor, escalada a 250x250 px
        // Usa una etiqueta con transparencia para dar efecto visual
        String imgPath = loser.getSelectedFighter().getImage();
        URL url = getClass().getClassLoader().getResource(imgPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel pic = new TransparentImageLabel(new ImageIcon(img), 0.6f);  // 60% opaco
            pic.setHorizontalAlignment(SwingConstants.CENTER);
            cp.add(pic, BorderLayout.CENTER);
        }

        // Texto "DERROTADO" abajo en gris claro
        JLabel text = new JLabel("DERROTADO", SwingConstants.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 18));
        text.setForeground(Color.LIGHT_GRAY);
        cp.add(text, BorderLayout.SOUTH);

        loserFrame.setVisible(true);
    }

    private void animateFatality() {
        // Animacion simple para desaparecer la ventana del perdedor:
        // baja y se desvanece progresivamente
        Timer timer = new Timer(30, new ActionListener() {
            float alpha = 1.0f;          // Opacidad inicial completa
            int yPos = loserFrame.getY(); // Posicion vertical inicial

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.03f;  // Disminuye opacidad
                yPos += 5;       // Mueve la ventana hacia abajo

                if (alpha <= 0) {
                    ((Timer) e.getSource()).stop();  // Detiene la animacion
                    loserFrame.dispose();             // Cierra ventana perdedor
                    return;
                }

                // Actualiza posicion vertical de la ventana
                loserFrame.setLocation(loserFrame.getX(), yPos);

                // Actualiza transparencia de todos los componentes dentro de la ventana perdedora
                for (Component comp : loserFrame.getContentPane().getComponents()) {
                    if (comp instanceof TransparentImageLabel) {
                        ((TransparentImageLabel) comp).setAlpha(alpha);
                    } else {
                        // Ajusta fondo con transparencia variable
                        comp.setBackground(new Color(0, 0, 0, (int)(180 * alpha)));
                    }
                    comp.repaint();
                }
                loserFrame.repaint();
            }
        });
        timer.start();
    }

    private void showFatalityText() {
        // Crea un label "FATALITY!" rojo y grande centrado en la ventana del ganador
        JLabel fatality = new JLabel("FATALITY!", SwingConstants.CENTER);
        fatality.setFont(new Font("Arial", Font.BOLD, 48));
        fatality.setForeground(Color.RED);
        fatality.setSize(winnerFrame.getWidth(), 100);
        fatality.setLocation(0, winnerFrame.getHeight() / 3);

        // Animacion de parpadeo alternando rojo y dorado
        Timer blinkTimer = new Timer(500, e -> {
            if (fatality.getForeground().equals(Color.RED)) {
                fatality.setForeground(new Color(255, 215, 0)); // dorado
            } else {
                fatality.setForeground(Color.RED);
            }
        });
        blinkTimer.setRepeats(true);
        blinkTimer.start();

        // Añade el label a la capa superior del JFrame ganador para que quede encima de todo
        JLayeredPane lp = winnerFrame.getLayeredPane();
        lp.add(fatality, JLayeredPane.POPUP_LAYER);
        winnerFrame.repaint();

        // Timer para detener la animacion y cerrar la ventana despues de 3 segundos
        Timer endTimer = new Timer(3000, e -> {
            blinkTimer.stop();                 // Para el parpadeo
            ((Timer) e.getSource()).stop();   // Para el timer de cierre

            // Muestra un dialogo informativo de que el combate termino
            JOptionPane.showMessageDialog(
                    winnerFrame,
                    "¡El combate fue épico!\nPero aun no es todo..",
                    "Fin del combate",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Quita el label de "FATALITY!" y refresca la ventana
            lp.remove(fatality);
            winnerFrame.repaint();

            // Finalmente cierra la ventana del ganador
            winnerFrame.dispose();

        });
        endTimer.setRepeats(false);
        endTimer.start();
    }


    // Clase interna para mostrar imagenes con transparencia variable
    static class TransparentImageLabel extends JLabel {
        private float alpha;

        public TransparentImageLabel(Icon icon, float alpha) {
            super(icon);
            this.alpha = alpha;
            setOpaque(false);
        }

        // Cambia la opacidad y redibuja el componente
        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Aplica transparencia al pintar el componente
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
}
