package SpaceInvader;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {
    private static final String SOUND_PATH = "sounds/";

    public static void playExplosion() {
        playSound("explosion.wav");
    }

    public static void playShoot() {
        playSound("disparo.wav");
    }

    public static void playLose() {
        playSound("perder.wav");
    }

    public static void playWin() {
        playSound("ganar.wav");
    }

    public static void playMusic() {
        playSound("musica.wav");
    }

    private static void playSound(String fileName) {
        try {
            InputStream audioSrc = AudioPlayer.class.getResourceAsStream("/sounds/" + fileName);
            if (audioSrc == null) {
                System.err.println("No se encontró el archivo de sonido en el classpath: /sounds/" + fileName);
                return;
            }
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            // Liberar recursos cuando termine de reproducir
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir el sonido: " + fileName);
            e.printStackTrace();
        }
    }
}