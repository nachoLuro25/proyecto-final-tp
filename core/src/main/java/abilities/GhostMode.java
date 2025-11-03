package abilities;

import entities.Player;

public class GhostMode extends Ability {

    public GhostMode() {
        super("INVISIBILIDAD", 125, "desaparecer");
    }

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

    @Override
    public void deactivate(Player owner, Player opponent) {
        owner.setInvisible(false);
        timer = 0;

        System.out.println("Modo fantasma desactivado. Vulnerable de nuevo.");
    }
}
