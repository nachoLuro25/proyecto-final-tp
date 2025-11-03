package game;

import entities.Player;
import entities.Trail;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class GameRenderer {

    private Image imgPlayer1;
    private Image imgPlayer2;

    public GameRenderer(Player player1, Player player2) {
        loadImages(player1, player2);
    }

    public void loadImages(Player player1, Player player2) {
        try {
            String path1 = player1.getColorType().getImagePath();
            String path2 = player2.getColorType().getImagePath();

            java.net.URL url1 = getClass().getClassLoader().getResource(path1);
            java.net.URL url2 = getClass().getClassLoader().getResource(path2);

            if (url1 != null && url2 != null) {
                imgPlayer1 = new ImageIcon(url1).getImage();
                imgPlayer2 = new ImageIcon(url2).getImage();
            } else {
                imgPlayer1 = new ImageIcon(path1).getImage();
                imgPlayer2 = new ImageIcon(path2).getImage();
            }

            System.out.println("✅ Imágenes de jugadores cargadas correctamente");

        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
            try {
                imgPlayer1 = new ImageIcon("assets/Red.png").getImage();
                imgPlayer2 = new ImageIcon("assets/Blue.png").getImage();
            } catch (Exception e2) {
                System.out.println("Error en fallback: " + e2.getMessage());
            }
        }
    }

    public void render(Graphics g, Player player1, Player player2,
                       boolean running, boolean paused, ImageObserver observer) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (running) {
            drawTrail(g2d, player1);
            drawTrail(g2d, player2);

            drawPlayer(g2d, player1, imgPlayer1, observer);
            drawPlayer(g2d, player2, imgPlayer2, observer);

            drawStatusInfo(g2d, player1, player2);

            if (paused) {
                drawPauseIndicator(g2d);
            }
        }

        g2d.dispose();
    }

    private void drawTrail(Graphics2D g2d, Player player) {
        if (player.isInvisible()) {
            return;
        }

        g2d.setColor(player.getColor());
        Trail trail = player.getTrail();

        for (Point p : trail.getPoints()) {
            g2d.fillRect(p.x, p.y, GameConfig.UNIT, GameConfig.UNIT);
        }
    }

    private void drawPlayer(Graphics2D g2d, Player player, Image image, ImageObserver observer) {
        float alpha = player.isInvisible() ? GameConfig.INVISIBLE_ALPHA : 1.0f;

        if (image != null) {
            double angle = getRotationAngle(player.getDirection());
            drawRotatedImage(g2d, image,
                player.getPosition().x + GameConfig.IMAGE_OFFSET_X,
                player.getPosition().y + GameConfig.IMAGE_OFFSET_Y,
                GameConfig.MOTO_SIZE,
                GameConfig.MOTO_SIZE,
                angle, alpha, observer);
        } else {
            g2d.setColor(player.getColor());
            if (player.isInvisible()) {
                g2d.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, GameConfig.INVISIBLE_ALPHA));
            }
            g2d.fillRect(player.getPosition().x, player.getPosition().y,
                GameConfig.UNIT, GameConfig.UNIT);
        }
    }

    private void drawRotatedImage(Graphics2D g2d, Image image, int x, int y,
                                  int width, int height, double angle,
                                  float alpha, ImageObserver observer) {
        AffineTransform oldTransform = g2d.getTransform();
        Composite oldComposite = g2d.getComposite();

        if (alpha < 1.0f) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(angle);
        transform.translate(-width / 2, -height / 2);

        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, width, height, observer);

        g2d.setTransform(oldTransform);
        g2d.setComposite(oldComposite);
    }

    private double getRotationAngle(char direction) {
        switch (direction) {
            case 'U': return -Math.PI / 2;
            case 'D': return Math.PI / 2;
            case 'L': return Math.PI;
            case 'R': return 0;
            default: return 0;
        }
    }

    private void drawStatusInfo(Graphics2D g2d, Player player1, Player player2) {
        g2d.setFont(new Font("Arial", Font.BOLD, GameConfig.STATUS_FONT_SIZE));

        drawPlayerStatus(g2d, player1, GameConfig.UI_MARGIN_LEFT, true);

        drawPlayerStatus(g2d, player2,
            GameConfig.WIDTH - GameConfig.UI_MARGIN_RIGHT, false);
    }

    private void drawPlayerStatus(Graphics2D g2d, Player player, int x, boolean isPlayer1) {
        g2d.setColor(player.getColor());
        int y = GameConfig.UI_START_Y;

        String playerName = isPlayer1 ? "J1: " : "J2: ";
        String abilityStatus = player.getAbility().isUsed() ? "USADA" :
            player.getAbility().getName() + " (" + (isPlayer1 ? "C" : "M") + ")";
        g2d.drawString(playerName + abilityStatus, x, y);

        y += GameConfig.UI_LINE_SPACING;

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

    private void drawPauseIndicator(Graphics2D g2d) {
        g2d.setColor(new Color(0, 255, 255, 200));
        g2d.setFont(new Font("Consolas", Font.BOLD, 24));

        String pauseMsg = "JUEGO EN PAUSA - Presiona ESC para continuar";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (GameConfig.WIDTH - fm.stringWidth(pauseMsg)) / 2;

        g2d.drawString(pauseMsg, x, 50);
    }
}
