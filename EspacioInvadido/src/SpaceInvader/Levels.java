package SpaceInvader;

import java.util.*;

/**
 * Clase que gestiona toda la lógica de niveles, formaciones y avance de nivel.
 */
public class Levels {
    private int nivel;
    private int vidas;
    private int puntos;
    private int[][] formacionActual;
    private int pasosPorLado;
    private int desplazamientoX;
    private int desplazamientoY;
    private int startX, startY, sepX, sepY;
	public int sumarPuntos;

    public Levels(int screenWidth, int screenHeight) {
        this.nivel = 1;
        this.vidas = 3;
        this.puntos = 0;
        this.startX = (int)(screenWidth * 0.18);
        this.startY = (int)(screenHeight * 0.10);
        this.sepX = (int)(screenWidth * 0.06);
        this.sepY = (int)(screenHeight * 0.06);
        setConfigForLevel(nivel);
    }

    public void siguienteNivel() {
        nivel++;
        setConfigForLevel(nivel);
    }

    public void reiniciar() {
        nivel = 1;
        vidas = 3;
        puntos = 0;
        setConfigForLevel(nivel);
    }

    public int getNivel() { return nivel; }
    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public void sumarPuntos(int p) { puntos += p; }
    public void perderVida() { vidas--; }
    public void setNivel(int n) { nivel = n; setConfigForLevel(nivel); }

    public int[][] getFormacionActual() { return formacionActual; }
    public int getPasosPorLado() { return pasosPorLado; }
    public int getDesplazamientoX() { return desplazamientoX; }
    public int getDesplazamientoY() { return desplazamientoY; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getSepX() { return sepX; }
    public int getSepY() { return sepY; }

    /**
     * Configura la formación y parámetros de movimiento según el nivel.
     * Tipos de nave: 1=rojo (débil), 2=amarillo (2 vidas), 3=violeta (kamikaze)
     */
    private void setConfigForLevel(int nivel) {
        switch (nivel) {
            case 1:
                formacionActual = new int[][] {
                    {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                    {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1}
                };
                pasosPorLado = 20;
                desplazamientoX = 1;
                desplazamientoY = 12;
                break;
            case 2:
                formacionActual = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                };
                pasosPorLado = 20;
                desplazamientoX = 4;
                desplazamientoY = 13;
                break;
            case 3:
                formacionActual = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {3, 0, 3, 0, 3, 0, 3, 0, 3, 0}
                };
                pasosPorLado = 20;
                desplazamientoX = 1;
                desplazamientoY = 14;
                break;
            case 4:
                formacionActual = new int[][] {
                    {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2},
                    {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1},
                    {3, 3, 0, 0, 3, 3, 0, 0, 3, 3, 0}
                };
                pasosPorLado = 22;
                desplazamientoX = 2;
                desplazamientoY = 14;
                break;
            case 5:
                formacionActual = new int[][] {
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
                };
                pasosPorLado = 22;
                desplazamientoX = 3;
                desplazamientoY = 15;
                break;
            case 6:
                formacionActual = new int[][] {
                    {3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };
                pasosPorLado = 24;
                desplazamientoX = 3;
                desplazamientoY = 16;
                break;
            case 7:
                formacionActual = new int[][] {
                    {3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
                    {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
                    {2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2}
                };
                pasosPorLado = 24;
                desplazamientoX = 4;
                desplazamientoY = 17;
                break;
            case 8:
                formacionActual = new int[][] {
                    {2, 3, 2, 3, 2, 3, 2, 3, 2, 3, 2},
                    {3, 2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };
                pasosPorLado = 26;
                desplazamientoX = 4;
                desplazamientoY = 18;
                break;
            case 9:
                formacionActual = new int[][] {
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                };
                pasosPorLado = 28;
                desplazamientoX = 5;
                desplazamientoY = 19;
                break;
            case 10:
                formacionActual = new int[][] {
                    {0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0}
                };
                pasosPorLado = 30;
                desplazamientoX = 2;
                desplazamientoY = 20;
                break;
            default:
                formacionActual = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
                };
                pasosPorLado = 20;
                desplazamientoX = 1;
                desplazamientoY = 15;
                break;
        }
    }
}