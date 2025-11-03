package utils;

import java.awt.Color;

/**
 * Enum que representa los colores disponibles para los jugadores
 * Cada color tiene asociado:
 * - Un Color de AWT para dibujar
 * - Una ruta de imagen de la moto
 * - Una ruta de imagen del perfil (para selección)
 *
 * @author Tu Nombre
 * @version 1.0
 */
public enum ColorType {
    ROJO(Color.RED, "assets/Red.png", "assets/perfilRojo.png"),
    AZUL(Color.BLUE, "assets/Blue.png", "assets/perfilAzul.png"),
    AMARILLO(Color.YELLOW, "assets/Yellow.png", "assets/perfilAmarillo.png"),
    VERDE(Color.GREEN, "assets/Green.png", "assets/perfilVerde.png");

    private final Color color;
    private final String imagePath;
    private final String profilePath;

    /**
     * Constructor del enum
     * @param color Color de AWT para el jugador
     * @param imagePath Ruta de la imagen de la moto
     * @param profilePath Ruta de la imagen del perfil
     */
    ColorType(Color color, String imagePath, String profilePath) {
        this.color = color;
        this.imagePath = imagePath;
        this.profilePath = profilePath;
    }

    /**
     * Obtiene el color de AWT
     * @return Color del jugador
     */
    public Color getColor() {
        return color;
    }

    /**
     * Obtiene la ruta de la imagen de la moto
     * @return Ruta del archivo de imagen
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Obtiene la ruta de la imagen del perfil
     * @return Ruta del archivo de perfil
     */
    public String getProfilePath() {
        return profilePath;
    }

    /**
     * Convierte un Color de AWT a un ColorType
     * @param color Color a convertir
     * @return ColorType correspondiente, o ROJO por defecto
     */
    public static ColorType fromColor(Color color) {
        for (ColorType type : values()) {
            if (type.color.equals(color)) {
                return type;
            }
        }
        return ROJO; // Color por defecto
    }
}
