package main;

import ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación CYCLE WARS
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class Main {

    /**
     * Método principal que ejecuta el programa
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater asegura que la GUI se cree en el Event Dispatch Thread
        // Esto es importante para evitar problemas de concurrencia en aplicaciones Swing
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal();
        });
    }
}
