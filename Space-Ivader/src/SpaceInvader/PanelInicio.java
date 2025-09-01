package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel de inicio con GIF de fondo y botón para empezar.
 * Muestra controles y espera interacción del usuario.
 */
public class PanelInicio extends JPanel {
    public JButton botonEmpezar;
    private Image fondoGif;
    private boolean iniciado;

    public PanelInicio() {
        this.fondoGif = new ImageIcon("resources/spaceinvader.gif").getImage();
        this.botonEmpezar = new JButton("Empezar");
        this.iniciado = false;
        setLayout(null);
        botonEmpezar.setBounds(Main.SCREEN_WIDTH/2 - 75, Main.SCREEN_HEIGHT/2, 150, 50);
        add(botonEmpezar);
        botonEmpezar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciado = true;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Ajusta el fondo y los textos a la resolución actual
        g.drawImage(fondoGif, 0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Controles: A/D para mover, Espacio para disparar", Main.SCREEN_WIDTH/2 - 200, Main.SCREEN_HEIGHT/4);
        g.drawString("Presione A, D o Espacio para empezar", Main.SCREEN_WIDTH/2 - 180, Main.SCREEN_HEIGHT/4 + 40);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }

    public boolean isIniciado() {
        return iniciado;
    }
}