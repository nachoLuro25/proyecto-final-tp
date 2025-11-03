package ui;

import javax.swing.*;
import java.awt.*;

public class VentanaAyuda extends JFrame {

    public VentanaAyuda() {
        setTitle("CYCLE WARS - Guía de Juego");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.BLACK);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("GUÍA DE BATALLA");
        titulo.setFont(new Font("Consolas", Font.BOLD, 36));
        titulo.setForeground(Color.CYAN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        panelPrincipal.add(crearSeccion(
            "OBJETIVO DEL JUEGO",
            "Sobrevive más tiempo que tu oponente. Tu moto deja un rastro de energía mortal.\n" +
                "Si tocas cualquier estela (incluida la tuya), ¡GAME OVER!\n" +
                "Usa estrategia, velocidad y habilidades para encerrar a tu rival.",
            Color.CYAN
        ));

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel tituloControles = new JLabel("CONTROLES");
        tituloControles.setFont(new Font("Consolas", Font.BOLD, 24));
        tituloControles.setForeground(Color.CYAN);
        panelPrincipal.add(tituloControles);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        panelPrincipal.add(crearPanelJugador("JUGADOR 1", "W A S D - Movimiento",
            "C - Habilidad Especial", Color.CYAN));
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        panelPrincipal.add(crearPanelJugador("JUGADOR 2", "↑ ↓ ← → - Movimiento",
            "M - Habilidad Especial", Color.CYAN));
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblGeneral = new JLabel("TECLA R - Reiniciar Partida | ESC - Pausa");
        lblGeneral.setFont(new Font("Consolas", Font.BOLD, 16));
        lblGeneral.setForeground(Color.WHITE);
        panelPrincipal.add(lblGeneral);

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel tituloHabilidades = new JLabel("HABILIDADES ESPECIALES");
        tituloHabilidades.setFont(new Font("Consolas", Font.BOLD, 24));
        tituloHabilidades.setForeground(Color.CYAN);
        panelPrincipal.add(tituloHabilidades);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel subtitulo = new JLabel("Cada jugador puede usar su habilidad UNA VEZ por partida. ¡Úsala sabiamente!");
        subtitulo.setFont(new Font("Consolas", Font.ITALIC, 13));
        subtitulo.setForeground(new Color(200, 200, 200));
        panelPrincipal.add(subtitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        panelPrincipal.add(crearHabilidad("AZUL - TURBO BOOST", "Duración: 5 segundos",
            "Tu moto alcanza el DOBLE de velocidad",
            "Perfecta para escapar de situaciones peligrosas o sorprender al rival",
            new Color(0, 150, 255)));

        panelPrincipal.add(crearHabilidad("AMARILLO - MODO FANTASMA", "Duración: 3 segundos",
            "Atraviesa estelas sin morir",
            "Ideal para escapar cuando estás rodeado o crear jugadas arriesgadas",
            new Color(255, 215, 0)));

        panelPrincipal.add(crearHabilidad("ROJO - PULSO EMP", "Efecto: Instantáneo",
            "Destruye todas las estelas en un radio cercano",
            "Crea espacio libre cuando el mapa está saturado",
            new Color(255, 50, 50)));

        panelPrincipal.add(crearHabilidad("VERDE - HACK NEURONAL", "Duración: 3 segundos",
            "Invierte los controles del enemigo",
            "Causa confusión total. ¡El rival no sabrá qué le pasó!",
            new Color(50, 255, 50)));

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.BLACK);

        JButton btnCerrar = new JButton("CERRAR");
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 18));
        btnCerrar.setBackground(new Color(50, 50, 50));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new Dimension(150, 45));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.BLACK);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBoton.add(btnCerrar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel crearSeccion(String titulo, String contenido, Color colorTitulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 24));
        lblTitulo.setForeground(colorTitulo);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txtContenido = new JTextArea(contenido);
        txtContenido.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtContenido.setForeground(Color.WHITE);
        txtContenido.setBackground(new Color(20, 20, 20));
        txtContenido.setEditable(false);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        txtContenido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorTitulo, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        txtContenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtContenido);

        return panel;
    }

    private JPanel crearPanelJugador(String nombre, String movimiento, String habilidad, Color colorBorde) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(15, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorde, 3),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Consolas", Font.BOLD, 18));
        lblNombre.setForeground(colorBorde);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMov = new JLabel(movimiento);
        lblMov.setFont(new Font("Consolas", Font.PLAIN, 15));
        lblMov.setForeground(Color.WHITE);
        lblMov.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblHab = new JLabel(habilidad);
        lblHab.setFont(new Font("Consolas", Font.PLAIN, 15));
        lblHab.setForeground(Color.WHITE);
        lblHab.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblNombre);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(lblMov);
        panel.add(lblHab);

        return panel;
    }

    private JPanel crearHabilidad(String nombre, String duracion, String efecto,
                                  String estrategia, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(15, 15, 15));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(850, 150));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Consolas", Font.BOLD, 18));
        lblNombre.setForeground(color);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDuracion = new JLabel(duracion);
        lblDuracion.setFont(new Font("Consolas", Font.PLAIN, 14));
        lblDuracion.setForeground(new Color(200, 200, 200));
        lblDuracion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEfecto = new JLabel(efecto);
        lblEfecto.setFont(new Font("Consolas", Font.BOLD, 15));
        lblEfecto.setForeground(Color.WHITE);
        lblEfecto.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEstrategia = new JLabel(estrategia);
        lblEstrategia.setFont(new Font("Consolas", Font.ITALIC, 13));
        lblEstrategia.setForeground(new Color(180, 180, 180));
        lblEstrategia.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblNombre);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(lblDuracion);
        panel.add(lblEfecto);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        panel.add(lblEstrategia);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return panel;
    }
}
