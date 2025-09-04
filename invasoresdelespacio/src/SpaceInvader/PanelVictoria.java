package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelVictoria extends JPanel {
    private Image fondo;
    private int puntos;

    public PanelVictoria(Main mainFrame, int puntos) {
        this.puntos = puntos;
        fondo = new ImageIcon(getClass().getResource("/images/victory.png")).getImage();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    mainFrame.volverAlMenuPrincipal();
                }
            }
        });
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.GREEN);
        g.drawString("¡VICTORIA!", getWidth() / 2 - 160, getHeight() / 2 - 50);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + puntos, getWidth() / 2 - 80, getHeight() / 2 + 40);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.YELLOW);
        g.drawString("Toca R para volver al menu principal y volver a jugar", getWidth() / 2 - 275, getHeight() / 2 + 100);
    }
}