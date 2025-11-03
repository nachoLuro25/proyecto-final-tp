package ui;

import audio.AudioManager;
import game.GameConfig;
import game.TronGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Menú que se muestra cuando el juego está en pausa
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class MenuPausa extends JFrame {

    private TronGame juego;

    public MenuPausa(TronGame juego, Runnable onResume, Runnable onVolverSeleccion,
                     Runnable onVolverMenu) {
        this.juego = juego;

        setTitle("PAUSA");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.BLACK);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel titulo = new JLabel("PAUSA");
        titulo.setFont(new Font("Consolas", Font.BOLD, 48));
        titulo.setForeground(new Color(0, 255, 255));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));

        // Panel de volumen
        AudioManager audioManager = AudioManager.getInstance();
        JPanel panelVolumen = UIComponents.crearPanelVolumen(
            audioManager.getMusicaJuego(),
            (int) GameConfig.GAME_MUSIC_VOLUME
        );
        panelVolumen.setMaximumSize(new Dimension(500, 150));
        panelVolumen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(panelVolumen);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnSeleccion = UIComponents.crearBotonEstandar("VOLVER A SELECCIÓN", 250, 45);
        btnSeleccion.addActionListener(e -> {
            dispose();
            onVolverSeleccion.run();
        });
        panelBotones.add(btnSeleccion);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnMenu = UIComponents.crearBotonEstandar("VOLVER AL MENÚ PRINCIPAL", 250, 45);
        btnMenu.addActionListener(e -> {
            dispose();
            onVolverMenu.run();
        });
        panelBotones.add(btnMenu);

        panelPrincipal.add(panelBotones);

        add(panelPrincipal, BorderLayout.CENTER);
        setVisible(true);
        requestFocus();

        // Listener para cerrar con ESC o X
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                onResume.run();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                    onResume.run();
                }
            }
        });
    }
}
