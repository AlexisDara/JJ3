package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelVictoria extends JPanel {
    private Image fondo;
    public PanelVictoria(Main mainFrame, int puntos) {
        fondo = new ImageIcon(getClass().getResource("/images/victory.png")).getImage();
        setLayout(new BorderLayout());
     
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}