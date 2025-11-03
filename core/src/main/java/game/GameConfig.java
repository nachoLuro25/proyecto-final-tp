package game;

public class GameConfig {

    public static final int WIDTH = 1320;

    public static final int HEIGHT = 770;

    public static final int UNIT = 12;

    public static final int DELAY = 40;

    public static final int ABILITY_DURATION = 125;

    public static final int SPEED_BOOST_DURATION = 30;

    public static final int PLAYER1_START_X = WIDTH / 4;

    public static final int PLAYER1_START_Y = HEIGHT / 2;

    public static final char PLAYER1_START_DIR = 'R';

    public static final int PLAYER2_START_X = 3 * WIDTH / 4;

    public static final int PLAYER2_START_Y = HEIGHT / 2;

    public static final char PLAYER2_START_DIR = 'L';

    public static final int MOTO_SIZE = 55;

    public static final int IMAGE_OFFSET_X = -22;

    public static final int IMAGE_OFFSET_Y = -22;

    public static final int EXPLOSION_RANGE = UNIT * 5;

    public static final float INVISIBLE_ALPHA = 0.3f;

    public static final int STATUS_FONT_SIZE = 14;

    public static final int UI_MARGIN_LEFT = 10;

    public static final int UI_MARGIN_RIGHT = 200;

    public static final int UI_START_Y = 30;

    public static final int UI_LINE_SPACING = 20;

    public static final float MENU_MUSIC_VOLUME = -10.0f;

    public static final float GAME_MUSIC_VOLUME = -15.0f;

    public static final int VOLUME_MIN = -80;

    public static final int VOLUME_MAX = 6;

    public static double framesToSeconds(int frames) {
        return frames * DELAY / 1000.0;
    }

    public static int secondsToFrames(double seconds) {
        return (int) (seconds * 1000 / DELAY);
    }

    public static int dbToPercentage(int db) {
        return (int) (((db - VOLUME_MIN) / (double) (VOLUME_MAX - VOLUME_MIN)) * 100);
    }

    public static int percentageToDb(int percentage) {
        return (int) (VOLUME_MIN + (percentage / 100.0) * (VOLUME_MAX - VOLUME_MIN));
    }

    // Constructor privado para prevenir instanciación
    private GameConfig() {
        throw new AssertionError("GameConfig no debe ser instanciado");
    }
}
