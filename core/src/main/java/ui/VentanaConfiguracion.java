package ui;

import audio.AudioManager;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de configuración de volumen
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class VentanaConfiguracion extends JFrame {

    public VentanaConfiguracion(JFrame padre) {
        setTitle("Configuración - Cycle Wars");
        setSize(400, 200);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        AudioManager audioManager = AudioManager.getInstance();

        // Panel de volumen
        JPanel panelVolumen = UIComponents.crearPanelVolumen(
            audioManager.getMusicaMenu(),
            (int) game.GameConfig.MENU_MUSIC_VOLUME
        );
        panelVolumen.setBorder(null);

        add(panelVolumen, BorderLayout.CENTER);
        setVisible(true);
    }
}
