package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class JuegoPanel extends JPanel implements ActionListener, KeyListener {
    private NavePrincipal nave;
    private Levels levels;
    private Fondo fondo;
    private Timer timer;
    private Main mainFrame;
    private int cuentaAtras;
    private double velocidadBase;
    private int cuentaAtrasFrames;
    private boolean mostrandoContador;
    private List<Proyectil> proyectilesJugador = new ArrayList<>();
    private List<Proyectil> proyectilesEnemigos = new ArrayList<>();
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;
    private static final int BORDE_X = (int)(Main.SCREEN_WIDTH * 0.15);
    private static final int BORDE_Y = (int)(Main.SCREEN_HEIGHT * 0.08);
    private boolean titilando = false;
    private int titilarFrames = 0;
    private static final int TITILAR_TOTAL_FRAMES = 30 * 2;
    private NaveEnemigas[][] formacion;
    private int pasosPorLado, desplazamientoX, desplazamientoY;
    int movimientosDerecha = 0, movimientosIzquierda = 0;
    boolean moviendoDerecha = true;
    private Hud hud;

    // Variables para el jefe
    private Jefe jefe = null;
    private long tiempoUltimaHabilidadJefe = 0;
    private long tiempoUltimoDisparoTriple = 0;
    private int balasDisparadasEnRafaga = 0;

    public JuegoPanel(Main mainFrame) {
        this.mainFrame = mainFrame;
        this.velocidadBase = Main.SCREEN_HEIGHT / 480.0 * 4.0;
        this.nave = new NavePrincipal((int)(Main.SCREEN_WIDTH*0.45), (int)(Main.SCREEN_HEIGHT*0.88), "images/soldaditoprime.gif", velocidadBase);
        this.levels = new Levels(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        this.fondo = new Fondo("imagenes/fondo_zombi.png");
        this.formacion = crearFormacionDesdeMatriz(levels.getFormacionActual(), levels);
        this.pasosPorLado = levels.getPasosPorLado();
        this.desplazamientoX = levels.getDesplazamientoX();
        this.desplazamientoY = levels.getDesplazamientoY();
        this.timer = new Timer(30, this);
        this.cuentaAtras = 3;
        this.cuentaAtrasFrames = 90;
        this.mostrandoContador = true;
        this.hud = new Hud();
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    private NaveEnemigas[][] crearFormacionDesdeMatriz(int[][] matriz, Levels levels) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        NaveEnemigas[][] formacion = new NaveEnemigas[filas][columnas];
        int startX = levels.getStartX();
        int startY = levels.getStartY();
        int sepX = levels.getSepX();
        int sepY = levels.getSepY();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int tipo = matriz[i][j];
                if (tipo > 0) {
                    String rutaImagen = NaveEnemigas.getRutaImagenPorTipo(tipo);
                    formacion[i][j] = new NaveEnemigas(startX + j * sepX, startY + i * sepY, tipo, rutaImagen);
                    if (tipo == 2) formacion[i][j].setVidas(2); // Amarilla: 2 vidas
                    if (tipo == 4) formacion[i][j] = new Jefe(startX + j * sepX, startY + i * sepY, velocidadBase * 1.5); // Jefe especial
                }
            }
        }
        return formacion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mostrandoContador) {
            if (cuentaAtrasFrames > 0) {
                cuentaAtrasFrames--;
                if (cuentaAtrasFrames % 30 == 0 && cuentaAtras > 0) cuentaAtras--;
            } else {
                mostrandoContador = false;
            }
            repaint();
            return;
        }
        if (leftPressed && nave.getX() > BORDE_X) nave.moverIzquierda();
        if (rightPressed && nave.getX() + nave.getAncho() < Main.SCREEN_WIDTH - BORDE_X) nave.moverDerecha();
        if (spacePressed && !nave.isDisparando()) {
            proyectilesJugador.add(new Proyectil(nave.getX() + nave.getAncho()/2, nave.getY(), 0, (int)(-6*velocidadBase), Color.CYAN, 6, 24));
            nave.setDisparando(true);
        }
        FuncionesNaves.moverFormacion(formacion, this);
        FuncionesNaves.disparoNaveAmarilla(formacion, proyectilesEnemigos);
        FuncionesNaves.comportamientoKamikaze(formacion, nave, levels, this);
        FuncionesNaves.moverProyectiles(proyectilesJugador, proyectilesEnemigos, BORDE_Y);
        FuncionesNaves.colisiones(formacion, proyectilesJugador, proyectilesEnemigos, nave, levels, this, hud);
        FuncionesNaves.chequearEstados(formacion, nave, levels, this);

        // Detectar jefe
        jefe = detectarJefe();
        if (jefe != null) {
            jefe.mover();
            long ahora = System.currentTimeMillis();
            if (ahora - tiempoUltimaHabilidadJefe > 5000) {
                if (balasDisparadasEnRafaga < 3 && ahora - tiempoUltimoDisparoTriple > 1000) {
                    disparoTripleJefe(jefe);
                    balasDisparadasEnRafaga++;
                    tiempoUltimoDisparoTriple = ahora;
                }
                if (balasDisparadasEnRafaga >= 3) {
                    tiempoUltimaHabilidadJefe = ahora;
                    balasDisparadasEnRafaga = 0;
                }
            }
        }
        repaint();
    }

    private Jefe detectarJefe() {
        for (int i = 0; i < formacion.length; i++) {
            for (int j = 0; j < formacion[0].length; j++) {
                if (formacion[i][j] instanceof Jefe) {
                    return (Jefe) formacion[i][j];
                }
            }
        }
        return null;
    }

    private void disparoTripleJefe(Jefe jefe) {
        int x = jefe.getX() + jefe.getAncho()/2;
        int y = jefe.getY() + jefe.getAlto();
        proyectilesEnemigos.add(new Proyectil(x, y, 0, 10, Color.RED, 8, 32));
        proyectilesEnemigos.add(new Proyectil(x, y, -5, 10, Color.RED, 8, 32));
        proyectilesEnemigos.add(new Proyectil(x, y, 5, 10, Color.RED, 8, 32));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        fondo.dibujar(g, getWidth(), getHeight());
        boolean mostrarNave = true;
        if (titilando && ((titilarFrames / 15) % 2 == 0)) {
            mostrarNave = false;
        }
        if (mostrarNave) {
            nave.dibujar(g);
        }
        for (int i = 0; i < formacion.length; i++) {
            for (int j = 0; j < formacion[0].length; j++) {
                if (formacion[i][j] != null) {
                    formacion[i][j].dibujar(g);
                }
            }
        }
        for (Proyectil p : proyectilesJugador) p.dibujar(g);
        for (Proyectil p : proyectilesEnemigos) p.dibujar(g);
        hud.dibujar(g, levels.getVidas(), levels.getNivel());
        if (mostrandoContador) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.drawString(String.valueOf(cuentaAtras), getWidth()/2 - 30, getHeight()/2);
        }
        // Barra de vida del jefe
        if (jefe != null) {
            hud.dibujarBarraJefe(g, jefe);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (mostrandoContador) return;
        if (e.getKeyCode() == KeyEvent.VK_A) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_D) rightPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) spacePressed = true;
        if (e.getKeyCode() == KeyEvent.VK_P) {
            siguienteNivel();
        }
    }
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_D) rightPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spacePressed = false;
            nave.setDisparando(false);
        }
    }
    public void keyTyped(KeyEvent e) {}

    // Métodos auxiliares para titilar, perder vida, etc.
    public void setTitilando(boolean t) { this.titilando = t; }
    public void setTitilarFrames(int f) { this.titilarFrames = f; }
    public int getTitilarFrames() { return this.titilarFrames; }
    public static int getTitilarTotalFrames() { return TITILAR_TOTAL_FRAMES; }
    public void gameOver() {
        if (timer != null) timer.stop();
        mainFrame.gameOver(levels.getPuntos());
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (timer != null) timer.stop();
    }

    public void siguienteNivel() {
        // Frenar balas antes de pasar de nivel o mostrar victoria
        FuncionesNaves.limpiarProyectiles(proyectilesJugador, proyectilesEnemigos);
        if (levels.getNivel() < 10) {
            levels.siguienteNivel();
            formacion = crearFormacionDesdeMatriz(levels.getFormacionActual(), levels);
            pasosPorLado = levels.getPasosPorLado();
            desplazamientoX = levels.getDesplazamientoX();
            desplazamientoY = levels.getDesplazamientoY();
            movimientosDerecha = 0;
            movimientosIzquierda = 0;
            moviendoDerecha = true;
        } else if (levels.getNivel() == 10) {
            mainFrame.mostrarVictoria(levels.getPuntos());
        }
    }
    public NaveEnemigas[][] getFormacion() { return formacion; }
    public void setFormacion(NaveEnemigas[][] f) { this.formacion = f; }
    public NavePrincipal getNave() { return nave; }
    public Levels getLevels() { return levels; }
    public List<Proyectil> getProyectilesJugador() { return proyectilesJugador; }
    public List<Proyectil> getProyectilesEnemigos() { return proyectilesEnemigos; }
    public boolean isTitilando() { return titilando; }
    public void setMostrandoContador(boolean m) { this.mostrandoContador = m; }
    public boolean isMostrandoContador() { return mostrandoContador; }
}