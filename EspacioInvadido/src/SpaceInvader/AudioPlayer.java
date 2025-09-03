package SpaceInvader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private static final String SOUND_PATH = "src/sounds/";

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
            File soundFile = new File(SOUND_PATH + fileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
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
