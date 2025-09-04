package SpaceInvader;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que representa la nave principal controlada por el jugador.
 * Permite moverse a izquierda/derecha y disparar.
 */
public class NavePrincipal {
    private int x, y; // Posición de la nave
    private double velocidad; // Ahora es double para ser proporcional
    private Image imagen;
    private int ancho, alto;
    private boolean disparando;
    private boolean imagenCargada;

    // Nuevo constructor con velocidad
    public NavePrincipal(int x, int y, String rutaImagen, double velocidad) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        try {
            java.net.URL imgURL = getClass().getResource("/images/soldaditoprime.gif");
            if (imgURL != null) {
                this.imagen = new ImageIcon(imgURL).getImage();
                this.imagenCargada = (this.imagen != null && this.imagen.getWidth(null) > 0);
            } else {
                this.imagenCargada = false;
            }
        } catch (Exception e) {
            this.imagenCargada = false;
        }
        this.ancho = (imagenCargada) ? imagen.getWidth(null) : 140;
        this.alto = (imagenCargada) ? imagen.getHeight(null) : 140;
        this.disparando = false;
    }

    // Mueve la nave a la izquierda
    public void moverIzquierda() {
        x -= velocidad;
    }

    // Mueve la nave a la derecha
    public void moverDerecha() {
        x += velocidad;
    }

    // Dispara un proyectil
    public void disparar() {
        disparando = true;
        AudioPlayer.playShoot(); // Sonido de disparo
    }

    // Dibuja la nave en pantalla
    public void dibujar(Graphics g) {
        if (imagenCargada) {
            // Dibuja la imagen escalada al doble de su tamaño original
            g.drawImage(imagen, x, y, ancho * 4, alto * 4, null);
        } else {
            // Dibuja un triángulo verde si no hay imagen
            g.setColor(Color.GREEN);
            int[] px = {x, x+ancho, x+ancho*2};
            int[] py = {y+alto*2, y, y+alto*2};
            g.fillPolygon(px, py, 3);
        }
    }

    // Getters y setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public boolean isDisparando() { return disparando; }
    public void setDisparando(boolean d) { disparando = d; }
}