package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static AudioManager instance;
    private Map<String, Clip> clips;

    // Clips específicos para control de volumen
    private Clip musicaMenu;
    private Clip musicaJuego;

    // Constantes para nombres de archivos
    private static final String RUTA_CONFUSION = "assets/confusion.wav";
    private static final String RUTA_DESAPARECER = "assets/desaparecer.wav";
    private static final String RUTA_EXPLOSION = "assets/explosion.wav";
    private static final String RUTA_MUSICA_JUEGO = "assets/musicaJuego.wav";
    private static final String RUTA_MUSICA_MENU = "assets/musicaMenu.wav";
    private static final String RUTA_VELOCIDAD = "assets/velocidad.wav";

    private AudioManager() {
        clips = new HashMap<>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void inicializarMusicaMenu() {
        try {
            musicaMenu = cargarClip(RUTA_MUSICA_MENU);
            if (musicaMenu != null) {
                musicaMenu.loop(Clip.LOOP_CONTINUOUSLY);
                musicaMenu.start();
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar música del menú: " + e.getMessage());
        }
    }

    public void inicializarMusicaJuego() {
        try {
            // Cargar efectos de sonido
            cargarEfecto("confusion", RUTA_CONFUSION);
            cargarEfecto("desaparecer", RUTA_DESAPARECER);
            cargarEfecto("explosion", RUTA_EXPLOSION);
            cargarEfecto("velocidad", RUTA_VELOCIDAD);
            cargarEfecto("derrota", RUTA_DESAPARECER); // Reusar sonido

            // Cargar y reproducir música del juego
            musicaJuego = cargarClip(RUTA_MUSICA_JUEGO);
            if (musicaJuego != null) {
                musicaJuego.loop(Clip.LOOP_CONTINUOUSLY);
                musicaJuego.start();
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar música del juego: " + e.getMessage());
        }
    }

    private void cargarEfecto(String nombre, String rutaArchivo) {
        try {
            Clip clip = cargarClip(rutaArchivo);
            if (clip != null) {
                clips.put(nombre, clip);
            }
        } catch (Exception e) {
            System.err.println("Error cargando efecto " + nombre + ": " + e.getMessage());
        }
    }

    private Clip cargarClip(String rutaArchivo) {
        try {
            File archivoAudio = new File(rutaArchivo);
            if (!archivoAudio.exists()) {
                System.err.println("Archivo no encontrado: " + rutaArchivo);
                return null;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error cargando clip " + rutaArchivo + ": " + e.getMessage());
            return null;
        }
    }

    public void reproducirEfecto(String nombre) {
        Clip clip = clips.get(nombre);
        if (clip != null) {
            // Si está sonando, reiniciarlo
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Efecto no encontrado: " + nombre);
        }
    }

    public void detenerMusicaMenu() {
        if (musicaMenu != null && musicaMenu.isRunning()) {
            musicaMenu.stop();
            musicaMenu.close();
        }
    }

    public void detenerMusicaJuego() {
        if (musicaJuego != null && musicaJuego.isRunning()) {
            musicaJuego.stop();
            musicaJuego.close();
        }
    }

    public void ajustarVolumen(Clip clip, float valorDB) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // Asegurar que esté dentro del rango permitido
                valorDB = Math.max(valorDB, gainControl.getMinimum());
                valorDB = Math.min(valorDB, gainControl.getMaximum());

                gainControl.setValue(valorDB);
            } catch (Exception e) {
                System.err.println("Error ajustando volumen: " + e.getMessage());
            }
        }
    }

    public Clip getMusicaMenu() {
        return musicaMenu;
    }

    public Clip getMusicaJuego() {
        return musicaJuego;
    }

    public void liberarRecursos() {
        detenerMusicaMenu();
        detenerMusicaJuego();

        for (Clip clip : clips.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        clips.clear();
    }
}
