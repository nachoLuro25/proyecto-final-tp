package ui;

import audio.AudioManager;
import game.GameConfig;
import game.TronGame;
import entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Menú que se muestra al finalizar el juego
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class MenuFinJuego extends JFrame {

    public MenuFinJuego(String ganador, Color colorGanador, Runnable onReiniciar,
                        Runnable onVolverSeleccion, Runnable onVolverMenu) {
        setTitle("FIN DEL JUEGO");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.BLACK);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel titulo = new JLabel("FIN DEL JUEGO");
        titulo.setFont(new Font("Consolas", Font.BOLD, 42));
        titulo.setForeground(new Color(255, 50, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Ganador
        JLabel lblGanador = new JLabel("GANADOR:");
        lblGanador.setFont(new Font("Consolas", Font.BOLD, 24));
        lblGanador.setForeground(new Color(0, 255, 255));
        lblGanador.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblGanador);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblNombreGanador = new JLabel(ganador.toUpperCase());
        lblNombreGanador.setFont(new Font("Consolas", Font.BOLD, 36));
        lblNombreGanador.setForeground(colorGanador);
        lblNombreGanador.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblNombreGanador);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Instrucción
        JLabel lblReiniciar = new JLabel("Presiona R para reiniciar");
        lblReiniciar.setFont(new Font("Consolas", Font.ITALIC, 16));
        lblReiniciar.setForeground(new Color(200, 200, 200));
        lblReiniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblReiniciar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));

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

        // Listener para tecla R
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    dispose();
                    onReiniciar.run();
                }
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
            }
        });
    }
}
