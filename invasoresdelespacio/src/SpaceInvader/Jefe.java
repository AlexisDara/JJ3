package SpaceInvader;

import java.awt.*;
import javax.swing.*;

public class Jefe extends NaveEnemigas {
    public static final int MAX_VIDAS = 50;

    public Jefe(int x, int y, double velocidad) {
        super(x, y, 4, "/images/zombie4.png", velocidad);
        this.vidas = MAX_VIDAS;
        this.y = 30; // Mantiene al jefe en la parte superior
        this.ancho = (int)(Main.SCREEN_WIDTH * 0.18); // Tamaño grande
        this.alto = (int)(Main.SCREEN_HEIGHT * 0.22);
        this.velocidad = Math.max(2, Math.min(velocidad, 6)); // Velocidad moderada
    }
    @Override
    public void mover() {
        // Movimiento horizontal en la parte superior, no traspasa bordes invisibles del jugador
        int bordeX = (int)(Main.SCREEN_WIDTH * 0.15);
        x += velocidad;
        if (x < bordeX) {
            x = bordeX;
            velocidad = -velocidad;
        } else if (x > Main.SCREEN_WIDTH - bordeX - ancho) {
            x = Main.SCREEN_WIDTH - bordeX - ancho;
            velocidad = -velocidad;
        }
    }
    @Override
    public void recibirImpacto() {
        if (this.vidas > 0) this.vidas--;
        if (this.vidas <= 0) {
            AudioPlayer.playExplosion();
        }
    }
    @Override
    public void dibujar(Graphics g) {
        if (imagenCargada) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, ancho, alto);
        }
    }
    public int getVidas() {
        return vidas;
    }
    public int getMaxVidas() {
        return MAX_VIDAS;
    }
    // Habilidad especial: disparo triple controlado desde JuegoPanel
}