package abilities;

import entities.Player;

/**
 * Habilidad AMARILLA: MODO FANTASMA
 * Permite atravesar estelas sin morir durante 3 segundos
 *
 * HERENCIA: Extiende de Ability
 * POLIMORFISMO: Implementa activate() y deactivate() a su manera
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class GhostMode extends Ability {

    /**
     * Constructor de GhostMode
     * Duración: 125 frames (≈ 5 segundos a 25 FPS)
     */
    public GhostMode() {
        super("INVISIBILIDAD", 125, "desaparecer");
    }

    /**
     * Activa el modo fantasma: permite atravesar estelas
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
        owner.setInvisible(true);
        playSound();

        System.out.println("¡MODO FANTASMA! Atraviesa estelas");
    }

    /**
     * Desactiva el modo fantasma: vuelve a ser vulnerable
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente (no se usa)
     */
    @Override
    public void deactivate(Player owner, Player opponent) {
        owner.setInvisible(false);
        timer = 0;

        System.out.println("Modo fantasma desactivado. Vulnerable de nuevo.");
    }
}
