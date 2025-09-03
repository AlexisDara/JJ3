package SpaceInvader;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que representa una nave enemiga genérica.
 * Se puede extender para diferentes tipos de enemigos.
 */
public class NaveEnemigas {
    protected int x, y;
    protected double velocidad;
    protected Image imagen;
    protected int ancho, alto;
    protected int tipo; // 0: normal, 1: zombi1, 2: kamikaze, 3: jefe, 4: jefe
    private boolean imagenCargada;
    private boolean bajando = true;
    private int objetivoY = 0;
    private int direccionX = 1;
    protected int vidas = 1; // Por defecto 1 vida
    private long tiempoUltimoDisparo = 0; // Para nave amarilla
    private boolean kamikazeLanzado = false; // Para nave violeta
    private int kamikazeObjetivoX = -1;
    private int kamikazeObjetivoY = -1;

    public NaveEnemigas(int x, int y, int tipo, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.velocidad = 3;
        try {
            String ruta = getRutaImagenPorTipo(tipo);
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

    public NaveEnemigas(int x, int y, int tipo, String rutaImagen, double velocidad) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.velocidad = velocidad;
        try {
            String ruta = getRutaImagenPorTipo(tipo);
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
        this.ancho = (imagenCargada) ? imagen.getWidth(null) : 40;
        this.alto = (imagenCargada) ? imagen.getHeight(null) : 40;
        this.bajando = true;
        this.objetivoY = (int)(Main.SCREEN_HEIGHT * 0.5) + (int)(Math.random() * (Main.SCREEN_HEIGHT * 0.05));
        this.direccionX = Math.random() < 0.5 ? -1 : 1;
    }

    // Movimiento básico hacia abajo
    public void mover() {
        if (bajando) {
            y += velocidad;
            if (y >= objetivoY) {
                bajando = false;
            }
        } else {
            x += direccionX * velocidad * 0.7;
            // Rebote en bordes
            if (x < (int)(Main.SCREEN_WIDTH * 0.15) || x > Main.SCREEN_WIDTH - (int)(Main.SCREEN_WIDTH * 0.15) - ancho) {
                direccionX *= -1;
            }
        }
    }

    // Permite mover la nave según el desplazamiento de la formación
    public void moverPorFormacion(int dx, int dy) {
        // Si es kamikaze y ya se lanzó, ignora el movimiento grupal
        if (tipo == 3 && kamikazeLanzado) return;
        this.x += dx;
        this.y += dy;
    }

    // Dibuja la nave enemiga
    public void dibujar(Graphics g) {
        int drawAncho = (int)(Main.SCREEN_WIDTH * 0.07); // 7% del ancho de pantalla
        int drawAlto = (int)(Main.SCREEN_HEIGHT * 0.11); // 11% del alto de pantalla
        if (imagenCargada) {
            g.drawImage(imagen, x, y, drawAncho, drawAlto, null); // Dibuja la imagen de forma responsive
        } else {
            // Si no carga la imagen, dibuja un cuadrado del color según el tipo
            if (tipo == 1) g.setColor(Color.ORANGE);
            else if (tipo == 2) g.setColor(Color.YELLOW);
            else if (tipo == 3) g.setColor(Color.MAGENTA);
            else g.setColor(Color.RED);
            g.fillRect(x, y, drawAncho, drawAlto);
        }
        this.ancho = drawAncho;
        this.alto = drawAlto;
    }

    // Métodos para comportamientos especiales
    public void disparar() {
        // Implementar en subclases si es necesario
    }

    public void moverKamikaze(int objetivoX, int objetivoY) {
        // Solo toma la posición inicial del jugador al lanzarse
        if (bajando) {
            y += velocidad;
            if (y >= this.objetivoY) bajando = false;
        } else {
            // Si aún no tiene objetivo, lo toma una sola vez
            if (kamikazeObjetivoX == -1 && kamikazeObjetivoY == -1) {
                kamikazeObjetivoX = objetivoX;
                kamikazeObjetivoY = objetivoY;
            }
            // Baja en línea recta hacia la posición inicial del jugador
            if (x < kamikazeObjetivoX) x += velocidad;
            else if (x > kamikazeObjetivoX) x -= velocidad;
            y += velocidad;
        }
    }

    // Método estático para obtener la ruta de imagen según el tipo de nave enemiga
    public static String getRutaImagenPorTipo(int tipo) {
        switch (tipo) {
            case 1:
                return "/images/zombie.png";
            case 2:
                return "/images/zombie2.png";
            case 3:
                return "/images/zombie3.png";
            case 4:
                return "/images/zombie4.png";
            default:
                return "/images/zombie.png";
        }
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getTipo() { return tipo; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public Rectangle getHitbox() {
        return new Rectangle(x, y, ancho, alto);
    }
    public void setVidas(int v) {
        this.vidas = v;
        if (this.vidas <= 0) {
            AudioPlayer.playExplosion();
        }
    }
    public int getVidas() { return vidas; }
    public boolean isKamikazeLanzado() { return kamikazeLanzado; }
    public void lanzarKamikaze() { kamikazeLanzado = true; }
    public long getTiempoUltimoDisparo() { return tiempoUltimoDisparo; }
    public void setTiempoUltimoDisparo(long t) { tiempoUltimoDisparo = t; }
}