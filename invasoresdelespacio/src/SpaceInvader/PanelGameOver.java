package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Panel para mostrar Game Over y los puntos obtenidos.
 */
public class PanelGameOver extends JPanel {
    private int puntos;

    public PanelGameOver(int puntos, Main mainFrame) {
        this.puntos = puntos;
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    mainFrame.volverAlMenuPrincipal();
                }
            }
        });
        // Solicita el foco correctamente después de agregar el panel al frame
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", Main.SCREEN_WIDTH/2 - 160 , Main.SCREEN_HEIGHT/2 - 50);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + puntos, Main.SCREEN_WIDTH/2 - 80, Main.SCREEN_HEIGHT/2 + 40);
        // Texto para reiniciar
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.YELLOW);
        g.drawString("Toca R para volver al menu pricipal y volver a jugar", Main.SCREEN_WIDTH/2 - 275, Main.SCREEN_HEIGHT/2 + 100);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }
}