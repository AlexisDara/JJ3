package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel de inicio con GIF de fondo y botón para empezar.
 * Muestra controles y espera interacción del usuario.
 */
public class PanelInicio extends JPanel {
    private Image fondoGif;
    private boolean iniciado;

    public PanelInicio() {
        this.fondoGif = new ImageIcon(getClass().getResource("/images/menu.png")).getImage();
        this.iniciado = false;
        setLayout(null);
        setFocusable(true);
    }
  
      

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Ajusta el fondo y los textos a la resolución actual
        g.drawImage(fondoGif, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Zombie Invaders", Main.SCREEN_WIDTH/2 - 200, Main.SCREEN_HEIGHT/4 + 100);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("Controles: A/D para mover, Espacio para disparar", Main.SCREEN_WIDTH/2 - 200, Main.SCREEN_HEIGHT/4 + 450);
        g.drawString("Presione A, D o Espacio para empezar", Main.SCREEN_WIDTH/2 - 180, Main.SCREEN_HEIGHT/4 + 500);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }

    public boolean isIniciado() {
        return iniciado;
    }
    @Override
    public boolean isFocusable() {
        return true;
    }
}