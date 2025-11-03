package ui;

import utils.ColorType;

import javax.swing.*;
import java.awt.*;

public class VentanaSeleccion extends JFrame {

    private VentanaPrincipal ventanaPadre;
    private ColorType colorJugador1 = ColorType.ROJO;
    private ColorType colorJugador2 = ColorType.AZUL;

    private JButton botonSeleccionadoJ1;
    private JButton botonSeleccionadoJ2;

    public VentanaSeleccion(VentanaPrincipal padre) {
        this.ventanaPadre = padre;

        setTitle("Seleccionar Motos - Cycle Wars");
        setSize(1200, 650);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 30, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        panelPrincipal.setBackground(Color.BLACK);

        JPanel panelJ1 = crearPanelJugador("JUGADOR 1", ColorType.ROJO, true);
        panelPrincipal.add(panelJ1);

        JPanel panelJ2 = crearPanelJugador("JUGADOR 2", ColorType.AZUL, false);
        panelPrincipal.add(panelJ2);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setBackground(Color.BLACK);

        JButton volverBtn = new JButton("VOLVER");
        volverBtn.setFont(new Font("Consolas", Font.BOLD, 18));
        volverBtn.setBackground(Color.DARK_GRAY);
        volverBtn.setForeground(Color.WHITE);
        volverBtn.setPreferredSize(new Dimension(150, 45));
        volverBtn.addActionListener(e -> dispose());

        JButton comenzarBtn = new JButton("COMENZAR");
        comenzarBtn.setFont(new Font("Consolas", Font.BOLD, 20));
        comenzarBtn.setBackground(new Color(0, 0, 0));
        comenzarBtn.setForeground(Color.WHITE);
        comenzarBtn.setPreferredSize(new Dimension(200, 50));
        comenzarBtn.addActionListener(e -> {
            if (colorJugador1 == colorJugador2) {
                JOptionPane.showMessageDialog(this,
                    "⚠ Los jugadores no pueden elegir el mismo color",
                    "Error de selección",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            dispose();
            ventanaPadre.iniciarJuego(colorJugador1, colorJugador2);
        });

        panelInferior.add(volverBtn);
        panelInferior.add(comenzarBtn);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel crearPanelJugador(String titulo, ColorType colorInicial, boolean esJugador1) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JLabel label = new JLabel(titulo, SwingConstants.CENTER);
        label.setFont(new Font("Consolas", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JPanel opciones = new JPanel(new GridLayout(2, 2, 15, 15));
        opciones.setBackground(Color.BLACK);
        opciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel seleccion = new JLabel("Color: " + colorInicial.name(), SwingConstants.CENTER);
        seleccion.setFont(new Font("Consolas", Font.BOLD, 18));
        seleccion.setForeground(colorInicial.getColor());
        seleccion.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnRojo = crearBotonColor(ColorType.ROJO, seleccion, esJugador1);
        JButton btnAzul = crearBotonColor(ColorType.AZUL, seleccion, esJugador1);
        JButton btnAmarillo = crearBotonColor(ColorType.AMARILLO, seleccion, esJugador1);
        JButton btnVerde = crearBotonColor(ColorType.VERDE, seleccion, esJugador1);

        opciones.add(btnRojo);
        opciones.add(btnAzul);
        opciones.add(btnAmarillo);
        opciones.add(btnVerde);

        panel.add(label, BorderLayout.NORTH);
        panel.add(opciones, BorderLayout.CENTER);
        panel.add(seleccion, BorderLayout.SOUTH);

        if (esJugador1) {
            botonSeleccionadoJ1 = (colorInicial == ColorType.ROJO) ? btnRojo :
                (colorInicial == ColorType.AZUL) ? btnAzul :
                    (colorInicial == ColorType.AMARILLO) ? btnAmarillo : btnVerde;
            botonSeleccionadoJ1.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        } else {
            botonSeleccionadoJ2 = (colorInicial == ColorType.ROJO) ? btnRojo :
                (colorInicial == ColorType.AZUL) ? btnAzul :
                    (colorInicial == ColorType.AMARILLO) ? btnAmarillo : btnVerde;
            botonSeleccionadoJ2.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        }

        return panel;
    }

    private JButton crearBotonColor(ColorType colorType, JLabel labelSeleccion, boolean esJugador1) {
        JButton btn;
        try {
            ImageIcon icon = new ImageIcon(colorType.getProfilePath());
            Image img = icon.getImage().getScaledInstance(240, 220, Image.SCALE_SMOOTH);
            btn = new JButton(new ImageIcon(img));
        } catch (Exception e) {
            btn = new JButton(colorType.name());
            btn.setBackground(colorType.getColor());
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Consolas", Font.BOLD, 16));
        }

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        final JButton finalBtn = btn;

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JButton botonActual = esJugador1 ? botonSeleccionadoJ1 : botonSeleccionadoJ2;
                if (finalBtn != botonActual) {
                    finalBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JButton botonActual = esJugador1 ? botonSeleccionadoJ1 : botonSeleccionadoJ2;
                if (finalBtn != botonActual) {
                    finalBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                }
            }
        });

        btn.addActionListener(e -> {
            JButton botonAnterior = esJugador1 ? botonSeleccionadoJ1 : botonSeleccionadoJ2;
            if (botonAnterior != null) {
                botonAnterior.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }

            if (esJugador1) {
                colorJugador1 = colorType;
                botonSeleccionadoJ1 = finalBtn;
            } else {
                colorJugador2 = colorType;
                botonSeleccionadoJ2 = finalBtn;
            }

            labelSeleccion.setText("Color: " + colorType.name());
            labelSeleccion.setForeground(colorType.getColor());

            finalBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        });

        return btn;
    }
}
