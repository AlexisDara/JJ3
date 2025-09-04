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

    /**
     * Dibuja la barra de vida del jefe.
     */
    public void dibujarBarraJefe(Graphics g, Jefe jefe) {
        if (jefe == null) return;
        int barWidth = (int)(Main.SCREEN_WIDTH * 0.35);
        int barHeight = 28;
        int x = (Main.SCREEN_WIDTH - barWidth) / 2;
        int y = 10;
        int vidaActual = jefe.getVidas();
        int vidaMax = jefe.getMaxVidas();
        int vidaWidth = (int)(barWidth * ((double)vidaActual / vidaMax));
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);
        g.setColor(Color.RED);
        g.fillRect(x, y, vidaWidth, barHeight);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("JEFE", x + 10, y + 22);
        g.drawString(vidaActual + "/" + vidaMax, x + barWidth - 80, y + 22);
    }

    public int getNivel() { return nivel; } 
    public int getPuntos() { return puntos; }
    public void setNivel(int n) { nivel = n; }
    public void setPuntos(int p) { puntos = p; }
}