package abilities;

import entities.Player;

public class SpeedBoost extends Ability {

    public SpeedBoost() {
        super("VELOCIDAD", 125, "velocidad");
    }

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

    @Override
    public void deactivate(Player owner, Player opponent) {
        owner.setSpeedBoost(false);
        timer = 0;

        System.out.println("Turbo desactivado. Velocidad normal.");
    }
}
