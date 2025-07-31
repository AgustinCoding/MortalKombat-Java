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
    private static final String FINISH_SOUND = "finish_him.wav";
    private static final String FATALITY_SOUND = "fatality.wav";

    private JFrame winnerFrame;
    private JFrame loserFrame;

    public EndCombatView(Player winner, Player loser) {
        SwingUtilities.invokeLater(() -> {
            SoundPlayer.play(FINISH_SOUND);
            showWinner(winner);
            showLoser(loser);

            // Timer configurado para que no se repita animacion ni sonido
            Timer fatalityStarter = new Timer(2000, e -> {
                // Parar cualquier sonido anterior por si está en loop
                SoundPlayer.stop();

                // Lanzar fatality
                SoundPlayer.play(FATALITY_SOUND);
                animateFatality();
                showFatalityText();

                ((Timer)e.getSource()).stop();
            });
            fatalityStarter.setRepeats(false);
            fatalityStarter.start();


        });
    }


    private void showWinner(Player winner) {
        winnerFrame = new JFrame("¡Victoria!");
        winnerFrame.setUndecorated(true);
        winnerFrame.setSize(400, 500);
        winnerFrame.setLocationRelativeTo(null);

        JPanel cp = new JPanel(new BorderLayout());
        cp.setBackground(Color.BLACK);
        cp.setBorder(new LineBorder(new Color(212, 175, 55), 5)); // borde dorado
        winnerFrame.setContentPane(cp);

        // Imagen del ganador
        String imgPath = winner.getSelectedFighter().getImage();
        URL url = getClass().getClassLoader().getResource(imgPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
            JLabel pic = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            cp.add(pic, BorderLayout.CENTER);
        }

        JLabel name = new JLabel(winner.getUsername() + " GANA", SwingConstants.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 24));
        name.setForeground(new Color(212, 175, 55)); // texto dorado
        name.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        cp.add(name, BorderLayout.SOUTH);

        winnerFrame.setVisible(true);
    }

    private void showLoser(Player loser) {
        loserFrame = new JFrame();
        loserFrame.setUndecorated(true);
        loserFrame.setSize(300, 380);

        // Posición abajo/derecha del centro
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width/2 + 200;
        int y = screen.height/2 + 50;
        loserFrame.setLocation(x, y);

        JPanel cp = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180)); // fondo semitransparente
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cp.setOpaque(false);
        cp.setBorder(new LineBorder(Color.DARK_GRAY, 3));
        loserFrame.setContentPane(cp);

        // Imagen del perdedor
        String imgPath = loser.getSelectedFighter().getImage();
        URL url = getClass().getClassLoader().getResource(imgPath);
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel pic = new TransparentImageLabel(new ImageIcon(img), 0.6f);
            pic.setHorizontalAlignment(SwingConstants.CENTER);
            cp.add(pic, BorderLayout.CENTER);
        }

        JLabel text = new JLabel("DERROTADO", SwingConstants.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 18));
        text.setForeground(Color.LIGHT_GRAY);
        cp.add(text, BorderLayout.SOUTH);

        loserFrame.setVisible(true);
    }

    private void animateFatality() {
        // Animación simple: desvanecimiento y movimiento hacia abajo
        Timer timer = new Timer(30, new ActionListener() {
            float alpha = 1.0f;
            int yPos = loserFrame.getY();

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.03f;
                yPos += 5;

                if (alpha <= 0) {
                    ((Timer)e.getSource()).stop();
                    loserFrame.dispose();
                    return;
                }

                // Actualizar posición y transparencia
                loserFrame.setLocation(loserFrame.getX(), yPos);

                // Actualizar transparencia de todos los componentes
                for (Component comp : loserFrame.getContentPane().getComponents()) {
                    if (comp instanceof TransparentImageLabel) {
                        ((TransparentImageLabel) comp).setAlpha(alpha);
                    } else {
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
        // Crear label FATALITY! encima del ganador
        JLabel fatality = new JLabel("FATALITY!", SwingConstants.CENTER);
        fatality.setFont(new Font("Arial", Font.BOLD, 48));
        fatality.setForeground(Color.RED);
        fatality.setSize(winnerFrame.getWidth(), 100);
        fatality.setLocation(0, winnerFrame.getHeight() / 3);

        // ANimacion parpadeo en label
        Timer blinkTimer = new Timer(500, e -> {
            if (fatality.getForeground().equals(Color.RED)) {
                fatality.setForeground(new Color(255, 215, 0)); // dorado
            } else {
                fatality.setForeground(Color.RED);
            }
        });
        blinkTimer.setRepeats(true);
        blinkTimer.start();

        JLayeredPane lp = winnerFrame.getLayeredPane();
        lp.add(fatality, JLayeredPane.POPUP_LAYER);
        winnerFrame.repaint();

        Timer endTimer = new Timer(3000, e -> {
            blinkTimer.stop();                // deja de parpadear
            ((Timer)e.getSource()).stop();    // Detiene el timer (3 segundos)

            // 1) dialogo informativo
            JOptionPane.showMessageDialog(
                    winnerFrame,
                    "¡El combate fue épico!\nPero aun no es todo..",
                    "Fin del combate",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // 2) quitar el label de FATALITY
            lp.remove(fatality);
            winnerFrame.repaint();

            // 3) cerrar la ventana de Victoria
            winnerFrame.dispose();

        });
        endTimer.setRepeats(false);
        endTimer.start();
    }




    // Clase para imágenes transparentes
    static class TransparentImageLabel extends JLabel {
        private float alpha;

        public TransparentImageLabel(Icon icon, float alpha) {
            super(icon);
            this.alpha = alpha;
            setOpaque(false);
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
}