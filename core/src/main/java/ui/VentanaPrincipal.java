package ui;

import audio.AudioManager;
import utils.ColorType;
import game.TronGame;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private AudioManager audioManager;
    private Image fondoMenu;

    public VentanaPrincipal() {
        audioManager = AudioManager.getInstance();

        setTitle("CYCLE WARS - Menú Principal");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        audioManager.inicializarMusicaMenu();

        try {
            fondoMenu = new ImageIcon("assets/fondoMenu.png").getImage();
        } catch (Exception e) {
            System.out.println("Error cargando fondo: " + e.getMessage());
        }

        JPanel panelConFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondoMenu != null) {
                    g.drawImage(fondoMenu, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        int anchoVentana = 1400;
        int anchoBoton = 300;
        int altoBoton = 80;
        int xCentrado = (anchoVentana - anchoBoton) / 2;
        int yInicial = 430;
        int espaciado = 90;

        JButton jugarBtn = UIComponents.crearBotonMenu("/assets/jugar.png", 200, 50);
        jugarBtn.setBounds(xCentrado, yInicial, anchoBoton, altoBoton);
        jugarBtn.addActionListener(e -> abrirSeleccionJugadores());
        panelConFondo.add(jugarBtn);

        JButton configBtn = UIComponents.crearBotonMenu("/assets/configuracion.png", 200, 50);
        configBtn.setBounds(xCentrado, yInicial + espaciado, anchoBoton, altoBoton);
        configBtn.addActionListener(e -> new VentanaConfiguracion(this));
        panelConFondo.add(configBtn);

        JButton ayudaBtn = UIComponents.crearBotonMenu("/assets/comojugar.png", 200, 70);
        ayudaBtn.setBounds(xCentrado, yInicial + (espaciado * 2), anchoBoton, altoBoton);
        ayudaBtn.addActionListener(e -> new VentanaAyuda());
        panelConFondo.add(ayudaBtn);

        JButton salirBtn = UIComponents.crearBotonMenu("/assets/salir.png", 200, 50);
        salirBtn.setBounds(xCentrado, yInicial + (espaciado * 3), anchoBoton, altoBoton);
        salirBtn.addActionListener(e -> {
            audioManager.detenerMusicaMenu();
            System.exit(0);
        });
        panelConFondo.add(salirBtn);

        setContentPane(panelConFondo);
        setVisible(true);
    }

    public void abrirSeleccionJugadores() {
        new VentanaSeleccion(this);
    }

    public void iniciarJuego(ColorType colorType1, ColorType colorType2) {
        audioManager.detenerMusicaMenu();

        JFrame frameJuego = new JFrame("CYCLE WARS - Juego");
        TronGame tronGame = new TronGame(colorType1, colorType2);
        frameJuego.add(tronGame);
        frameJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameJuego.pack();
        frameJuego.setLocationRelativeTo(null);
        frameJuego.setResizable(false);
        frameJuego.setVisible(true);

        this.dispose();

        frameJuego.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                tronGame.dispose();
            }
        });
    }

    @Override
    public void dispose() {
        audioManager.detenerMusicaMenu();
        super.dispose();
    }
}
