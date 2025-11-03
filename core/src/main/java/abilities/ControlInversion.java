package abilities;

import entities.Player;

/**
 * Habilidad VERDE: HACK NEURONAL
 * Invierte los controles del oponente durante 3 segundos
 * W se convierte en S, A en D, etc.
 *
 * HERENCIA: Extiende de Ability
 * POLIMORFISMO: Implementa activate() y deactivate() a su manera
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class ControlInversion extends Ability {

    /**
     * Constructor de ControlInversion
     * Duración: 125 frames (≈ 5 segundos a 25 FPS)
     */
    public ControlInversion() {
        super("CONFUSIÓN", 125, "confusion");
    }

    /**
     * Activa la inversión de controles en el oponente
     *
     * @param owner Jugador que activa la habilidad (no se afecta)
     * @param opponent Jugador oponente (sus controles se invierten)
     */
    @Override
    public void activate(Player owner, Player opponent) {
        // Verificar que no haya sido usada
        if (used) return;

        used = true;
        timer = duration;
        opponent.setControlsInverted(true); // ← Afecta al OPONENTE
        playSound();

        System.out.println("¡HACK NEURONAL! Controles del oponente invertidos");
    }

    /**
     * Desactiva la inversión de controles
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente (vuelve a controles normales)
     */
    @Override
    public void deactivate(Player owner, Player opponent) {
        opponent.setControlsInverted(false);
        timer = 0;

        System.out.println("Hack neuronal desactivado. Controles normales.");
    }
}
