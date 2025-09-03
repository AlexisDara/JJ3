package SpaceInvader;

import java.awt.*;

/**
 * HUD para mostrar puntos y nivel.
 */
public class Hud {
    private int puntos;
    private int nivel;

    public Hud() {
        this.puntos = 0;
        this.nivel = 1;
    }

    /**
     * Suma puntos al contador.
     */
    public void sumarPuntos(int p) {
        puntos += p;
    }

    /**
     * Sube el nivel.
     */
    public void subirNivel() {
        nivel++;
    }

    /**
     * Dibuja el HUD en pantalla.
     */
    public void dibujar(Graphics g, int vidas, int nivel) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        int x = 10;
        int y = 40;
        int sep = 35;
        g.drawString("Puntos: " + puntos, x, y);
        g.drawString("Nivel: " + nivel, x, y + sep);
        g.drawString("Vidas: " + vidas, x, y + sep * 2);
    }

    public int getNivel() { return nivel; } 
    public int getPuntos() { return puntos; }
    public void setNivel(int n) { nivel = n; }
    public void setPuntos(int p) { puntos = p; }
}