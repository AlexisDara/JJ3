package SpaceInvader;

import java.awt.*;
import javax.swing.*;

public class Jefe {
    private int x, y;
    private double velocidad;
    private Image imagen;
    private int ancho, alto;
    private int vidas;
    private boolean imagenCargada;

    public Jefe(int x, int y, double velocidad, int vidas) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vidas = vidas;
        try {
            String ruta = "/images/zombie4.png";
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                this.imagen = new ImageIcon(url).getImage();
                this.imagenCargada = (this.imagen != null && this.imagen.getWidth(null) > 0);
            } else {
                this.imagenCargada = false;
            }
        } catch (Exception e) {
            this.imagenCargada = false;
        }
        this.ancho = (imagenCargada) ? imagen.getWidth(null) : 140;
        this.alto = (imagenCargada) ? imagen.getHeight(null) : 140;
    }

    public void mover() {
        // Lógica de movimiento del jefe (puedes personalizar)
        x += velocidad;
        if (x < 0 || x > Main.SCREEN_WIDTH - ancho) {
            velocidad = -velocidad;
        }
    }

    public void dibujar(Graphics g) {
        if (imagenCargada) {
            g.drawImage(imagen, x, y, ancho, alto, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, ancho, alto);
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, ancho, alto);
    }

    public int getVidas() { return vidas; }
    public void setVidas(int v) { this.vidas = 100; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}
