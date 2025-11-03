package ui;

import audio.AudioManager;

import javax.swing.*;
import java.awt.*;

public class VentanaConfiguracion extends JFrame {

    public VentanaConfiguracion(JFrame padre) {
        setTitle("Configuración - Cycle Wars");
        setSize(400, 200);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        AudioManager audioManager = AudioManager.getInstance();

        JPanel panelVolumen = UIComponents.crearPanelVolumen(
            audioManager.getMusicaMenu(),
            (int) game.GameConfig.MENU_MUSIC_VOLUME
        );
        panelVolumen.setBorder(null);

        add(panelVolumen, BorderLayout.CENTER);
        setVisible(true);
    }
}
