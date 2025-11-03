package utils;

import java.awt.Color;

public enum ColorType {
    ROJO(Color.RED, "assets/Red.png", "assets/perfilRojo.png"),
    AZUL(Color.BLUE, "assets/Blue.png", "assets/perfilAzul.png"),
    AMARILLO(Color.YELLOW, "assets/Yellow.png", "assets/perfilAmarillo.png"),
    VERDE(Color.GREEN, "assets/Green.png", "assets/perfilVerde.png");

    private final Color color;
    private final String imagePath;
    private final String profilePath;

    ColorType(Color color, String imagePath, String profilePath) {
        this.color = color;
        this.imagePath = imagePath;
        this.profilePath = profilePath;
    }

    public Color getColor() {
        return color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public static ColorType fromColor(Color color) {
        for (ColorType type : values()) {
            if (type.color.equals(color)) {
                return type;
            }
        }
        return ROJO;
    }
}
