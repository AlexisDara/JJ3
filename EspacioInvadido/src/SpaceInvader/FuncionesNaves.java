package SpaceInvader;

import java.util.*;
import java.util.List;
import java.awt.*;

/**
 * Clase con funciones utilitarias para manejar las naves y sus interacciones.
 */
public class FuncionesNaves {
    /**
     * Detecta colisión entre dos rectángulos (naves, proyectiles, etc).
     */
    public static boolean colision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    /**
     * Genera una lista de naves enemigas según el nivel.
     */
    public static List<Object> generarEnemigos(int nivel, double velocidadBase) {
        List<Object> enemigos = new ArrayList<>();
        Random rand = new Random();
        int cantidad = 5 + nivel * 2;
        for (int i = 0; i < cantidad; i++) {
            int tipo;
            if (nivel >= 5 && rand.nextInt(10) > 7) tipo = 3; // Kamikaze
            else if (nivel >= 2 && rand.nextInt(10) > 5) tipo = 2; // Dispara
            else tipo = 1; // Devil
            if (nivel % 10 == 0 && i == 0) {
                // El jefe aparece como el primer enemigo en niveles múltiplos de 10
                enemigos.add(new Jefe(250, 50, velocidadBase * 1.5, 20));
            } else {
                String ruta;
                switch (tipo) {
                    case 1: ruta = "images/zombie.png"; break; // Devil
                    case 2: ruta = "images/zombie2.png"; break; // Dispara
                    case 3: ruta = "images/zombie3.png"; break; // Kamikaze
                    default: ruta = "images/zombie.png"; break;
                }
                enemigos.add(new NaveEnemigas(rand.nextInt(600), rand.nextInt(100), tipo, ruta, velocidadBase));
            }
        }
        return enemigos;
    }

    /**
     * Genera una lista de naves enemigas según el nivel, adaptada a diferentes tamaños de pantalla.
     */
    public static List<Object> generarEnemigos(int nivel, double velocidadBase, int screenWidth, int screenHeight) {
        List<Object> enemigos = new ArrayList<>();
        Random rand = new Random();
        int cantidad = 5 + nivel * 2;
        int margenX = (int)(screenWidth * 0.15);
        int anchoZona = screenWidth - 2 * margenX;
        int altoZona = (int)(screenHeight * 0.25); // Solo en el 25% superior
        for (int i = 0; i < cantidad; i++) {
            int tipo;
            if (nivel >= 5 && rand.nextInt(10) > 7) tipo = 3; // Kamikaze
            else if (nivel >= 2 && rand.nextInt(10) > 5) tipo = 2; // Dispara
            else tipo = 1; // Devil
            if (nivel % 10 == 0 && i == 0) {
                enemigos.add(new Jefe(screenWidth/2-70, 50, velocidadBase * 1.5, 20));
            } else {
                String ruta;
                switch (tipo) {
                    case 1: ruta = "images/zombie.png"; break; // Devil
                    case 2: ruta = "images/zombie2.png"; break; // Dispara
                    case 3: ruta = "images/zombie3.png"; break; // Kamikaze
                    default: ruta = "images/zombie.png"; break;
                }
                int x = margenX + rand.nextInt(Math.max(1, anchoZona));
                int y = -rand.nextInt(altoZona) - 40; // Aparecen fuera de la pantalla
                enemigos.add(new NaveEnemigas(x, y, tipo, ruta, velocidadBase));
            }
        }
        return enemigos;
    }

    // Mueve la formación de enemigos
    public static void moverFormacion(NaveEnemigas[][] formacion, JuegoPanel panel) {
        int pasosPorLado = panel.getLevels().getPasosPorLado();
        int desplazamientoX = panel.getLevels().getDesplazamientoX();
        int desplazamientoY = panel.getLevels().getDesplazamientoY();
        int movimientosDerecha = panel.movimientosDerecha;
        int movimientosIzquierda = panel.movimientosIzquierda;
        boolean moviendoDerecha = panel.moviendoDerecha;
        int dx = 0, dy = 0;
        if (moviendoDerecha) {
            dx = desplazamientoX;
            movimientosDerecha++;
            if (movimientosDerecha >= pasosPorLado) {
                moviendoDerecha = false;
                movimientosDerecha = 0;
                dy = desplazamientoY;
            }
        } else {
            dx = -desplazamientoX;
            movimientosIzquierda++;
            if (movimientosIzquierda >= pasosPorLado) {
                moviendoDerecha = true;
                movimientosIzquierda = 0;
                dy = desplazamientoY;
            }
        }
        for (int i = 0; i < formacion.length; i++) {
            for (int j = 0; j < formacion[0].length; j++) {
                if (formacion[i][j] != null) {
                    formacion[i][j].moverPorFormacion(dx, dy);
                }
            }
        }
        panel.movimientosDerecha = movimientosDerecha;
        panel.movimientosIzquierda = movimientosIzquierda;
        panel.moviendoDerecha = moviendoDerecha;
    }

    // Disparo de nave amarilla
    public static void disparoNaveAmarilla(NaveEnemigas[][] formacion, List<Proyectil> proyectilesEnemigos) {
        long ahora = System.currentTimeMillis();
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                NaveEnemigas enemigo = formacion[fila][col];
                if (enemigo != null && enemigo.getTipo() == 2) {
                    if (ahora - enemigo.getTiempoUltimoDisparo() > 5000) {
                        proyectilesEnemigos.add(new Proyectil(enemigo.getX() + enemigo.getAncho()/2, enemigo.getY() + enemigo.getAlto(), 0, 8, Color.YELLOW, 6, 24));
                        enemigo.setTiempoUltimoDisparo(ahora);
                    }
                }
            }
        }
    }

    // Comportamiento kamikaze
    public static void comportamientoKamikaze(NaveEnemigas[][] formacion, NavePrincipal nave, Levels levels, JuegoPanel panel) {
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                NaveEnemigas enemigo = formacion[fila][col];
                if (enemigo != null && enemigo.getTipo() == 3) {
                    if (!enemigo.isKamikazeLanzado()) {
                        enemigo.lanzarKamikaze();
                    }
                    enemigo.moverKamikaze(nave.getX(), nave.getY());
                    Rectangle rnav = new Rectangle(nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto());
                    if (enemigo.getHitbox().intersects(rnav)) {
                        formacion[fila][col] = null;
                        levels.perderVida();
                        panel.setTitilando(true);
                        panel.setTitilarFrames(JuegoPanel.getTitilarTotalFrames());
                        if (levels.getVidas() <= 0) {
                            panel.gameOver();
                            return;
                        }
                    }
                }
            }
        }
    }

    // Mover proyectiles
    public static void moverProyectiles(List<Proyectil> proyectilesJugador, List<Proyectil> proyectilesEnemigos, int BORDE_Y) {
        for (int i = 0; i < proyectilesJugador.size(); i++) {
            Proyectil p = proyectilesJugador.get(i);
            p.mover();
            if (p.getY() < BORDE_Y) proyectilesJugador.remove(i--);
        }
        for (int i = 0; i < proyectilesEnemigos.size(); i++) {
            Proyectil p = proyectilesEnemigos.get(i);
            p.mover();
            if (p.getY() > Main.SCREEN_HEIGHT - BORDE_Y) proyectilesEnemigos.remove(i--);
        }
    }
    public static void frenarProyectiles(List<Proyectil> proyectilesJugador, List<Proyectil> proyectilesEnemigos) {
        for (Proyectil p : proyectilesJugador) p.frenar();
        for (Proyectil p : proyectilesEnemigos) p.frenar();
    }

    // Colisiones
    public static void colisiones(NaveEnemigas[][] formacion, List<Proyectil> proyectilesJugador, List<Proyectil> proyectilesEnemigos, NavePrincipal nave, Levels levels, JuegoPanel panel, Hud hud) {
        // Proyectil jugador vs enemigos
        for (int i = 0; i < proyectilesJugador.size(); i++) {
            Proyectil pj = proyectilesJugador.get(i);
            Rectangle rpj = new Rectangle(pj.getX()-pj.getAncho()/2, pj.getY()-pj.getAlto()/2, pj.getAncho(), pj.getAlto());
            boolean hit = false;
            outer:
            for (int fila = 0; fila < formacion.length; fila++) {
                for (int col = 0; col < formacion[0].length; col++) {
                    NaveEnemigas enemigo = formacion[fila][col];
                    if (enemigo == null) continue;
                    if (rpj.intersects(enemigo.getHitbox())) {
                        if (enemigo.getTipo() == 2) {
                            enemigo.setVidas(enemigo.getVidas() - 1);
                            if (enemigo.getVidas() <= 0) {
                                AudioPlayer.playExplosion(); // Sonido de explosión
                                formacion[fila][col] = null;
                                hit = true;
                                levels.sumarPuntos(200);
                                hud.sumarPuntos(200);
                                break outer;
                            }
                        } else {
                            AudioPlayer.playExplosion(); // Sonido de explosión
                            formacion[fila][col] = null;
                            hit = true;
                            int puntos = (enemigo.getTipo() == 3 ? 300 : 100);
                            levels.sumarPuntos(puntos);
                            hud.sumarPuntos(puntos);
                            break outer;
                        }
                    }
                }
            }
            if (hit) {
                proyectilesJugador.remove(i--);
            }
        }
        // Proyectil enemigo vs jugador
        for (int i = 0; i < proyectilesEnemigos.size(); i++) {
            Proyectil pe = proyectilesEnemigos.get(i);
            Rectangle rpe = new Rectangle(pe.getX()-pe.getAncho()/2, pe.getY()-pe.getAlto()/2, pe.getAncho(), pe.getAlto());
            Rectangle rnav = new Rectangle(nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto());
            if (rpe.intersects(rnav)) {
                proyectilesEnemigos.remove(i--);
                levels.perderVida();
                panel.setTitilando(true);
                panel.setTitilarFrames(JuegoPanel.getTitilarTotalFrames());
                if (levels.getVidas() <= 0) {
                    panel.gameOver();
                    return;
                }
            }
        }
    }

    // Chequeos de estado: nave alcanzada, todos fuera, eliminar kamikazes, siguiente nivel
    public static void chequearEstados(NaveEnemigas[][] formacion, NavePrincipal nave, Levels levels, JuegoPanel panel) {
        boolean naveAlcanzada = false;
        int limiteY = (int)(Main.SCREEN_HEIGHT * 0.6); // Solo considerar enemigos que bajaron al 60% de la pantalla
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                NaveEnemigas enemigo = formacion[fila][col];
                if (enemigo != null) {
                    // Solo considerar "nave alcanzada" si el enemigo está visible y suficientemente abajo
                    if (enemigo.getTipo() == 3 && enemigo.getY() > Main.SCREEN_HEIGHT) {
                        formacion[fila][col] = null;
                        continue;
                    }
                    if (enemigo.getTipo() != 3 && enemigo.getY() > 0 && enemigo.getY() > limiteY && enemigo.getY() + enemigo.getAlto() >= nave.getY()) {
                        naveAlcanzada = true;
                        break;
                    }
                }
            }
            if (naveAlcanzada) break;
        }
        if (naveAlcanzada) {
            panel.gameOver();
            return;
        }
        if (panel.isTitilando()) {
            int tf = panel.getTitilarFrames();
            tf--;
            if (tf <= 0) {
                panel.setTitilando(false);
                panel.setTitilarFrames(0);
            } else {
                panel.setTitilarFrames(tf);
            }
        }
        boolean todosFuera = true;
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                NaveEnemigas enemigo = formacion[fila][col];
                if (enemigo != null && enemigo.getY() < Main.SCREEN_HEIGHT - (int)(Main.SCREEN_HEIGHT * 0.08)) {
                    todosFuera = false;
                    break;
                }
            }
        }
        boolean vacio = true;
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                if (formacion[fila][col] != null) {
                    vacio = false;
                    break;
                }
            }
        }
        if (vacio || todosFuera) {
            panel.siguienteNivel();
        }
        // Eliminar kamikazes fuera de pantalla
        for (int fila = 0; fila < formacion.length; fila++) {
            for (int col = 0; col < formacion[0].length; col++) {
                NaveEnemigas enemigo = formacion[fila][col];
                if (enemigo != null && enemigo.getTipo() == 3 && enemigo.getY() > Main.SCREEN_HEIGHT) {
                    formacion[fila][col] = null;
                }
            }
        }
    }
    public static void limpiarProyectiles(List<Proyectil> proyectilesJugador, List<Proyectil> proyectilesEnemigos) {
        proyectilesJugador.clear();
        proyectilesEnemigos.clear();
    }
}