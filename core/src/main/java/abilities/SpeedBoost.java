package abilities;

import entities.Player;

/**
 * Habilidad AZUL: TURBO BOOST
 * Aumenta la velocidad del jugador al doble durante 5 segundos
 *
 * HERENCIA: Extiende de Ability
 * POLIMORFISMO: Implementa activate() y deactivate() a su manera
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class SpeedBoost extends Ability {

    /**
     * Constructor de SpeedBoost
     * Duración: 125 frames (≈ 5 segundos a 25 FPS)
     */
    public SpeedBoost() {
        super("VELOCIDAD", 125, "velocidad");
    }

    /**
     * Activa el turbo: duplica la velocidad del jugador
     *
     * @param owner Jugador que activa la habilidad
     * @param opponent Jugador oponente (no se usa en esta habilidad)
     */
    @Override
    public void activate(Player owner, Player opponent) {
        // Verificar que no haya sido usada
        if (used) return;

        used = true;
        timer = duration;
        owner.setSpeedBoost(true);
        playSound();

        System.out.println("¡TURBO ACTIVADO! Velocidad x2");
    }

    /**
     * Desactiva el turbo: vuelve a velocidad normal
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente (no se usa)
     */
    @Override
    public void deactivate(Player owner, Player opponent) {
        owner.setSpeedBoost(false);
        timer = 0;

        System.out.println("Turbo desactivado. Velocidad normal.");
    }
}
