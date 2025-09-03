package SpaceInvader;

import java.awt.*;

public class Proyectil {
    private int x, y, dx, dy;
    private Color color;
    private int ancho, alto;
    public Proyectil(int x, int y, int dx, int dy, Color color, int ancho, int alto) {
        this.x = x; this.y = y; this.dx = dx; this.dy = dy; this.color = color;
        this.ancho = ancho; this.alto = alto;
    }
    public void mover() { x += dx; y += dy; }
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.drawLine(x, y, x, y + 15 * (dy > 0 ? 1 : -1));
    }
    public int getY() { return y; }
    public int getX() { return x; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}
