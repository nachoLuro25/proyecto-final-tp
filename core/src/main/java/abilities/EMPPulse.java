package abilities;

import entities.Player;
import entities.Trail;
import java.awt.Point;
import java.util.Iterator;

public class EMPPulse extends Ability {

    private static final int EXPLOSION_RANGE = 60; // 12 UNIT * 5

    public EMPPulse() {
        super("EXPLOSIÓN", 0, "explosion");
    }

    @Override
    public void activate(Player owner, Player opponent) {
        // Verificar que no haya sido usada
        if (used) return;

        used = true;
        playSound();
        explodeTrails(owner, opponent);

        System.out.println("¡PULSO EMP! Estelas cercanas destruidas");
    }

    @Override
    public void deactivate(Player owner, Player opponent) {
        // No hay desactivación, es instantáneo
    }

    private void explodeTrails(Player owner, Player opponent) {
        Point center = owner.getPosition();

        // Eliminar estelas del oponente cercanas
        Trail opponentTrail = opponent.getTrail();
        Iterator<Point> iter = opponentTrail.getPoints().iterator();

        int eliminadas = 0;
        while (iter.hasNext()) {
            Point p = iter.next();

            // Calcular distancia Manhattan (más rápido que distancia euclidiana)
            if (Math.abs(p.x - center.x) <= EXPLOSION_RANGE &&
                Math.abs(p.y - center.y) <= EXPLOSION_RANGE) {
                iter.remove();
                eliminadas++;
            }
        }

        System.out.println("Estelas eliminadas: " + eliminadas);
    }
}
