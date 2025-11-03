package game;

import entities.Player;
import entities.Trail;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 * Clase responsable de renderizar (dibujar) todos los elementos del juego
 *
 * SEPARACIÓN DE RESPONSABILIDADES:
 * - TronGame maneja la lógica
 * - GameRenderer maneja el dibujado
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class GameRenderer {

    private Image imgPlayer1;
    private Image imgPlayer2;

    /**
     * Constructor de GameRenderer
     * Carga las imágenes de las motos
     *
     * @param player1 Jugador 1
     * @param player2 Jugador 2
     */
    public GameRenderer(Player player1, Player player2) {
        loadImages(player1, player2);
    }

    /**
     * Carga las imágenes de las motos según los colores de los jugadores
     * @param player1 Jugador 1
     * @param player2 Jugador 2
     */
    public void loadImages(Player player1, Player player2) {
        try {
            String path1 = player1.getColorType().getImagePath();
            String path2 = player2.getColorType().getImagePath();

            // Intentar cargar desde resources
            java.net.URL url1 = getClass().getClassLoader().getResource(path1);
            java.net.URL url2 = getClass().getClassLoader().getResource(path2);

            if (url1 != null && url2 != null) {
                imgPlayer1 = new ImageIcon(url1).getImage();
                imgPlayer2 = new ImageIcon(url2).getImage();
            } else {
                // Fallback: cargar desde ruta directa
                imgPlayer1 = new ImageIcon(path1).getImage();
                imgPlayer2 = new ImageIcon(path2).getImage();
            }

            System.out.println("✅ Imágenes de jugadores cargadas correctamente");

        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
            // Fallback final a imágenes por defecto
            try {
                imgPlayer1 = new ImageIcon("assets/Red.png").getImage();
                imgPlayer2 = new ImageIcon("assets/Blue.png").getImage();
            } catch (Exception e2) {
                System.out.println("Error en fallback: " + e2.getMessage());
            }
        }
    }

    /**
     * Renderiza todo el estado del juego
     *
     * @param g Graphics del panel
     * @param player1 Jugador 1
     * @param player2 Jugador 2
     * @param running ¿El juego está corriendo?
     * @param paused ¿El juego está pausado?
     * @param observer Observer para cargar imágenes
     */
    public void render(Graphics g, Player player1, Player player2,
                       boolean running, boolean paused, ImageObserver observer) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Activar antialiasing para mejor calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (running) {
            // Dibujar estelas
            drawTrail(g2d, player1);
            drawTrail(g2d, player2);

            // Dibujar motos
            drawPlayer(g2d, player1, imgPlayer1, observer);
            drawPlayer(g2d, player2, imgPlayer2, observer);

            // Dibujar información de estado
            drawStatusInfo(g2d, player1, player2);

            // Mostrar indicador de pausa
            if (paused) {
                drawPauseIndicator(g2d);
            }
        }

        g2d.dispose();
    }

    /**
     * Dibuja la estela de un jugador
     * @param g2d Graphics2D
     * @param player Jugador
     */
    private void drawTrail(Graphics2D g2d, Player player) {
        // Si el jugador es invisible, no dibujar su estela
        if (player.isInvisible()) {
            return;
        }

        g2d.setColor(player.getColor());
        Trail trail = player.getTrail();

        for (Point p : trail.getPoints()) {
            g2d.fillRect(p.x, p.y, GameConfig.UNIT, GameConfig.UNIT);
        }
    }

    /**
     * Dibuja un jugador (su moto)
     * @param g2d Graphics2D
     * @param player Jugador
     * @param image Imagen de la moto
     * @param observer Observer para cargar la imagen
     */
    private void drawPlayer(Graphics2D g2d, Player player, Image image, ImageObserver observer) {
        float alpha = player.isInvisible() ? GameConfig.INVISIBLE_ALPHA : 1.0f;

        if (image != null) {
            // Dibujar imagen rotada
            double angle = getRotationAngle(player.getDirection());
            drawRotatedImage(g2d, image,
                player.getPosition().x + GameConfig.IMAGE_OFFSET_X,
                player.getPosition().y + GameConfig.IMAGE_OFFSET_Y,
                GameConfig.MOTO_SIZE,
                GameConfig.MOTO_SIZE,
                angle, alpha, observer);
        } else {
            // Fallback: dibujar rectángulo de color
            g2d.setColor(player.getColor());
            if (player.isInvisible()) {
                g2d.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, GameConfig.INVISIBLE_ALPHA));
            }
            g2d.fillRect(player.getPosition().x, player.getPosition().y,
                GameConfig.UNIT, GameConfig.UNIT);
        }
    }

    /**
     * Dibuja una imagen rotada
     * @param g2d Graphics2D
     * @param image Imagen a dibujar
     * @param x Posición X
     * @param y Posición Y
     * @param width Ancho
     * @param height Alto
     * @param angle Ángulo de rotación en radianes
     * @param alpha Transparencia (0.0 a 1.0)
     * @param observer Observer para cargar la imagen
     */
    private void drawRotatedImage(Graphics2D g2d, Image image, int x, int y,
                                  int width, int height, double angle,
                                  float alpha, ImageObserver observer) {
        AffineTransform oldTransform = g2d.getTransform();
        Composite oldComposite = g2d.getComposite();

        // Aplicar transparencia si es necesario
        if (alpha < 1.0f) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        // Calcular centro de rotación
        int centerX = x + width / 2;
        int centerY = y + height / 2;

        // Aplicar transformaciones
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(angle);
        transform.translate(-width / 2, -height / 2);

        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, width, height, observer);

        // Restaurar estado original
        g2d.setTransform(oldTransform);
        g2d.setComposite(oldComposite);
    }

    /**
     * Obtiene el ángulo de rotación según la dirección
     * @param direction Dirección ('U', 'D', 'L', 'R')
     * @return Ángulo en radianes
     */
    private double getRotationAngle(char direction) {
        switch (direction) {
            case 'U': return -Math.PI / 2;  // 90° arriba
            case 'D': return Math.PI / 2;   // 90° abajo
            case 'L': return Math.PI;       // 180° izquierda
            case 'R': return 0;             // 0° derecha
            default: return 0;
        }
    }

    /**
     * Dibuja la información de estado de ambos jugadores
     * @param g2d Graphics2D
     * @param player1 Jugador 1
     * @param player2 Jugador 2
     */
    private void drawStatusInfo(Graphics2D g2d, Player player1, Player player2) {
        g2d.setFont(new Font("Arial", Font.BOLD, GameConfig.STATUS_FONT_SIZE));

        // Información Jugador 1 (izquierda)
        drawPlayerStatus(g2d, player1, GameConfig.UI_MARGIN_LEFT, true);

        // Información Jugador 2 (derecha)
        drawPlayerStatus(g2d, player2,
            GameConfig.WIDTH - GameConfig.UI_MARGIN_RIGHT, false);
    }

    /**
     * Dibuja el estado de un jugador
     * @param g2d Graphics2D
     * @param player Jugador
     * @param x Posición X
     * @param isPlayer1 ¿Es el jugador 1?
     */
    private void drawPlayerStatus(Graphics2D g2d, Player player, int x, boolean isPlayer1) {
        g2d.setColor(player.getColor());
        int y = GameConfig.UI_START_Y;

        // Nombre y estado de habilidad
        String playerName = isPlayer1 ? "J1: " : "J2: ";
        String abilityStatus = player.getAbility().isUsed() ? "USADA" :
            player.getAbility().getName() + " (" + (isPlayer1 ? "C" : "M") + ")";
        g2d.drawString(playerName + abilityStatus, x, y);

        y += GameConfig.UI_LINE_SPACING;

        // Estados activos
        if (player.isSpeedBoost()) {
            int seconds = (int) GameConfig.framesToSeconds(
                player.getAbility().getTimer()) + 1;
            g2d.drawString("VELOCIDAD: " + seconds + "s", x, y);
            y += GameConfig.UI_LINE_SPACING;
        }

        if (player.isInvisible()) {
            int seconds = (int) GameConfig.framesToSeconds(
                player.getAbility().getTimer()) + 1;
            g2d.drawString("INVISIBLE: " + seconds + "s", x, y);
            y += GameConfig.UI_LINE_SPACING;
        }

        if (player.isControlsInverted()) {
            int seconds = (int) GameConfig.framesToSeconds(
                player.getAbility().getTimer()) + 1;
            g2d.drawString("CONTROLES INVERTIDOS: " + seconds + "s", x, y);
        }
    }

    /**
     * Dibuja el indicador de pausa
     * @param g2d Graphics2D
     */
    private void drawPauseIndicator(Graphics2D g2d) {
        g2d.setColor(new Color(0, 255, 255, 200));
        g2d.setFont(new Font("Consolas", Font.BOLD, 24));

        String pauseMsg = "JUEGO EN PAUSA - Presiona ESC para continuar";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (GameConfig.WIDTH - fm.stringWidth(pauseMsg)) / 2;

        g2d.drawString(pauseMsg, x, 50);
    }
}
