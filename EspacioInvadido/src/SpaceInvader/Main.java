package SpaceInvader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase principal que inicia el juego y gestiona los paneles.
 */
public class Main extends JFrame {
    // Variables para tamaño de pantalla
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private PanelInicio panelInicio;
    private JuegoPanel panelJuego;
    private PanelGameOver panelGameOver;

    public Main() {
        // Obtener tamaño de pantalla del usuario
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = (int) screenSize.getWidth();
        SCREEN_HEIGHT = (int) screenSize.getHeight();
        setTitle("Zombie Invader");
        setUndecorated(true); // Pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        panelInicio = new PanelInicio();
        setContentPane(panelInicio);
        setVisible(true);
        esperarInicio();
    }

    private void esperarInicio() {
        // Espera interacción con el botón o teclas
        panelInicio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    iniciarJuego();
                }
                
            }
        });
        panelInicio.setFocusable(true);
        panelInicio.requestFocusInWindow();
        panelInicio.botonEmpezar.addActionListener(e -> iniciarJuego());
    }

    public void iniciarJuego() {
        // Siempre crea un nuevo JuegoPanel y estado
        panelJuego = new JuegoPanel(this);
        setContentPane(panelJuego);
        revalidate();
        panelJuego.setFocusable(true);
        panelJuego.requestFocusInWindow();
    }

    protected void siguienteNivel() {
        if (panelJuego != null) {
            FuncionesNaves.limpiarProyectiles(panelJuego.getProyectilesJugador(), panelJuego.getProyectilesEnemigos());
        }
        // TODO Auto-generated method stub
        
    }

	public void gameOver(int puntos) {
        if (panelJuego != null) {
            FuncionesNaves.limpiarProyectiles(panelJuego.getProyectilesJugador(), panelJuego.getProyectilesEnemigos());
        }
        panelGameOver = new PanelGameOver(puntos, this);
        setContentPane(panelGameOver);
        revalidate();
        panelGameOver.setFocusable(true);
        panelGameOver.requestFocusInWindow();
    }
	
	public void mostrarVictoria(int puntos) {
        if (panelJuego != null) {
            FuncionesNaves.limpiarProyectiles(panelJuego.getProyectilesJugador(), panelJuego.getProyectilesEnemigos());
        }
        setContentPane(new PanelVictoria(this, puntos));
        revalidate();
    }

    public void volverAlMenuPrincipal() {
        panelJuego = null;
        panelGameOver = null;
        // Limpia el estado de vidas, niveles, etc. si es necesario
        panelInicio = new PanelInicio();
        setContentPane(panelInicio);
        revalidate();
        panelInicio.setFocusable(true);
        panelInicio.requestFocusInWindow();
        esperarInicio();
    }

    public static void main(String[] args) {
        new Main();
    }
}

/**
 * Panel principal del juego, gestiona lógica y renderizado.
 */
// Elimina la clase JuegoPanel y la clase Proyectil, ya que ahora están en archivos separados