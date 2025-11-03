package ui;

import audio.AudioManager;
import game.GameConfig;

import javax.swing.*;
import java.awt.*;

public class UIComponents {

    public static JButton crearBotonMenu(String rutaImagen, int ancho, int alto) {
        JButton btn = new JButton();

        try {
            ImageIcon icon = null;

            java.net.URL url = UIComponents.class.getClassLoader().getResource(rutaImagen.startsWith("/") ? rutaImagen.substring(1) : rutaImagen);

            if (url != null) {
                icon = new ImageIcon(url);
            } else {
                String path = rutaImagen.startsWith("/") ? rutaImagen.substring(1) : rutaImagen;
                icon = new ImageIcon(path);
            }

            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            ImageIcon iconNormal = new ImageIcon(img);

            Image imgHover = icon.getImage().getScaledInstance(
                (int)(ancho * 1.05), (int)(alto * 1.05), Image.SCALE_SMOOTH);
            ImageIcon iconHover = new ImageIcon(imgHover);

            btn.setIcon(iconNormal);

            final ImageIcon finalIconNormal = iconNormal;
            final ImageIcon finalIconHover = iconHover;

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btn.setIcon(finalIconHover);
                    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btn.setIcon(finalIconNormal);
                    btn.setCursor(Cursor.getDefaultCursor());
                }
            });

            System.out.println("✅ Botón cargado: " + rutaImagen);

        } catch (Exception e) {
            System.err.println("❌ Error cargando botón " + rutaImagen + ": " + e.getMessage());
            btn.setText("BOTÓN");
        }

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        return btn;
    }

    public static JSlider crearSliderVolumen(int valorInicial) {
        JSlider slider = new JSlider(GameConfig.VOLUME_MIN, GameConfig.VOLUME_MAX, valorInicial);
        slider.setOpaque(false);
        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.BLACK);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setBorder(null);
        slider.setFocusable(false);

        slider.setUI(new javax.swing.plaf.basic.BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(30, 90, 180));
                g2d.fillRoundRect(trackRect.x, trackRect.y + trackRect.height / 3,
                    trackRect.width, 6, 6, 6);

                g2d.setColor(new Color(0, 120, 255));
                int filledWidth = (int) ((slider.getValue() - slider.getMinimum())
                    / (double) (slider.getMaximum() - slider.getMinimum()) * trackRect.width);
                g2d.fillRoundRect(trackRect.x, trackRect.y + trackRect.height / 3,
                    filledWidth, 6, 6, 6);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 255, 255));
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintFocus(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, slider.getWidth(), slider.getHeight());
            }
        });

        return slider;
    }

    public static JPanel crearPanelVolumen(javax.sound.sampled.Clip clip, int valorInicial) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JLabel lblVolumen = new JLabel("VOLUMEN", JLabel.CENTER);
        lblVolumen.setFont(new Font("Consolas", Font.BOLD, 18));
        lblVolumen.setForeground(new Color(0, 255, 255));

        JSlider slider = crearSliderVolumen(valorInicial);
        slider.setMaximumSize(new Dimension(400, 50));

        JLabel lblPorcentaje = new JLabel(
            GameConfig.dbToPercentage(valorInicial) + "%", JLabel.CENTER);
        lblPorcentaje.setFont(new Font("Consolas", Font.BOLD, 16));
        lblPorcentaje.setForeground(Color.WHITE);

        slider.addChangeListener(e -> {
            int valorDB = slider.getValue();
            int porcentaje = GameConfig.dbToPercentage(valorDB);
            lblPorcentaje.setText(porcentaje + "%");

            if (!slider.getValueIsAdjusting() && clip != null) {
                AudioManager.getInstance().ajustarVolumen(clip, (float) valorDB);
            }
        });

        panel.add(lblVolumen, BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        panel.add(lblPorcentaje, BorderLayout.SOUTH);

        return panel;
    }

    public static JButton crearBotonEstandar(String texto, int ancho, int alto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Consolas", Font.BOLD, 16));
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(ancho, alto));
        btn.setMaximumSize(new Dimension(ancho, alto));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}
