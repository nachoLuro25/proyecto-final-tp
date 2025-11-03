package game;

/**
 * Clase que contiene todas las constantes de configuración del juego
 *
 * Patrón: CLASE DE CONSTANTES
 * - Centraliza todos los valores configurables
 * - Facilita el ajuste del juego
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class GameConfig {

    // ==================== DIMENSIONES ====================

    /** Ancho de la ventana del juego en píxeles */
    public static final int WIDTH = 1320;

    /** Alto de la ventana del juego en píxeles */
    public static final int HEIGHT = 770;

    /** Tamaño de cada unidad/celda en píxeles */
    public static final int UNIT = 12;

    // ==================== VELOCIDAD Y TIMING ====================

    /** Delay entre frames en milisegundos (40ms = 25 FPS) */
    public static final int DELAY = 40;

    /** Duración de habilidades en frames (125 frames ≈ 5 segundos a 25 FPS) */
    public static final int ABILITY_DURATION = 125;

    /** Duración del speed boost en frames (30 frames ≈ 1.2 segundos) */
    public static final int SPEED_BOOST_DURATION = 30;

    // ==================== JUGADORES ====================

    /** Posición inicial X del Jugador 1 */
    public static final int PLAYER1_START_X = WIDTH / 4;

    /** Posición inicial Y del Jugador 1 */
    public static final int PLAYER1_START_Y = HEIGHT / 2;

    /** Dirección inicial del Jugador 1 */
    public static final char PLAYER1_START_DIR = 'R';

    /** Posición inicial X del Jugador 2 */
    public static final int PLAYER2_START_X = 3 * WIDTH / 4;

    /** Posición inicial Y del Jugador 2 */
    public static final int PLAYER2_START_Y = HEIGHT / 2;

    /** Dirección inicial del Jugador 2 */
    public static final char PLAYER2_START_DIR = 'L';

    // ==================== IMÁGENES ====================

    /** Tamaño de las imágenes de las motos en píxeles */
    public static final int MOTO_SIZE = 55;

    /** Offset X para centrar la imagen de la moto */
    public static final int IMAGE_OFFSET_X = -22;

    /** Offset Y para centrar la imagen de la moto */
    public static final int IMAGE_OFFSET_Y = -22;

    // ==================== HABILIDADES ====================

    /** Radio de explosión del EMP en píxeles (5 unidades) */
    public static final int EXPLOSION_RANGE = UNIT * 5;

    /** Transparencia de jugador invisible (0.0f a 1.0f) */
    public static final float INVISIBLE_ALPHA = 0.3f;

    // ==================== UI ====================

    /** Tamaño de fuente para indicadores de estado */
    public static final int STATUS_FONT_SIZE = 14;

    /** Margen desde el borde izquierdo para info Jugador 1 */
    public static final int UI_MARGIN_LEFT = 10;

    /** Margen desde el borde derecho para info Jugador 2 */
    public static final int UI_MARGIN_RIGHT = 200;

    /** Posición Y inicial para textos de estado */
    public static final int UI_START_Y = 30;

    /** Espaciado entre líneas de texto */
    public static final int UI_LINE_SPACING = 20;

    // ==================== AUDIO ====================

    /** Volumen inicial de la música del menú en dB */
    public static final float MENU_MUSIC_VOLUME = -10.0f;

    /** Volumen inicial de la música del juego en dB */
    public static final float GAME_MUSIC_VOLUME = -15.0f;

    /** Volumen mínimo permitido en dB */
    public static final int VOLUME_MIN = -80;

    /** Volumen máximo permitido en dB */
    public static final int VOLUME_MAX = 6;

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Convierte frames a segundos
     * @param frames Cantidad de frames
     * @return Segundos equivalentes
     */
    public static double framesToSeconds(int frames) {
        return frames * DELAY / 1000.0;
    }

    /**
     * Convierte segundos a frames
     * @param seconds Cantidad de segundos
     * @return Frames equivalentes
     */
    public static int secondsToFrames(double seconds) {
        return (int) (seconds * 1000 / DELAY);
    }

    /**
     * Convierte decibelios a porcentaje (0-100)
     * @param db Valor en decibelios
     * @return Porcentaje (0-100)
     */
    public static int dbToPercentage(int db) {
        return (int) (((db - VOLUME_MIN) / (double) (VOLUME_MAX - VOLUME_MIN)) * 100);
    }

    /**
     * Convierte porcentaje a decibelios
     * @param percentage Porcentaje (0-100)
     * @return Valor en decibelios
     */
    public static int percentageToDb(int percentage) {
        return (int) (VOLUME_MIN + (percentage / 100.0) * (VOLUME_MAX - VOLUME_MIN));
    }

    // Constructor privado para prevenir instanciación
    private GameConfig() {
        throw new AssertionError("GameConfig no debe ser instanciado");
    }
}
