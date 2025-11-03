package abilities;

import entities.Player;

public class ControlInversion extends Ability {

    public ControlInversion() {
        super("CONFUSIÓN", 125, "confusion");
    }

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

    @Override
    public void deactivate(Player owner, Player opponent) {
        opponent.setControlsInverted(false);
        timer = 0;

        System.out.println("Hack neuronal desactivado. Controles normales.");
    }
}
