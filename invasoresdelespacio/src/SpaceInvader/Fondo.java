package SpaceInvader;

import java.awt.*;
import javax.swing.*;

/**
 * Clase para gestionar el fondo del juego.
 * Permite dibujar una imagen de fondo.
 */
public class Fondo {
    private Image imagen;

    public Fondo(String rutaImagen) {
        java.net.URL url = getClass().getResource("/images/fondo.png");
        if (url != null) {
            this.imagen = new ImageIcon(url).getImage();
        } else {
            this.imagen = null;
        }
    }

    /**
     * Dibuja el fondo en el panel.
     */
    public void dibujar(Graphics g, int width, int height) {
        g.drawImage(imagen, 0, 0, width, height, null);
    }
}