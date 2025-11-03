package abilities;

import entities.Player;
import entities.Trail;
import java.awt.Point;
import java.util.Iterator;

/**
 * Habilidad ROJA: PULSO EMP
 * Destruye todas las estelas cercanas en un radio de 5 unidades
 * Efecto instantáneo
 *
 * HERENCIA: Extiende de Ability
 * POLIMORFISMO: Implementa activate() y deactivate() a su manera
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class EMPPulse extends Ability {

    private static final int EXPLOSION_RANGE = 60; // 12 UNIT * 5

    /**
     * Constructor de EMPPulse
     * Duración: 0 (es instantáneo)
     */
    public EMPPulse() {
        super("EXPLOSIÓN", 0, "explosion");
    }

    /**
     * Activa el pulso EMP: destruye estelas cercanas
     *
     * @param owner Jugador que activa la habilidad
     * @param opponent Jugador oponente (sus estelas serán eliminadas)
     */
    @Override
    public void activate(Player owner, Player opponent) {
        // Verificar que no haya sido usada
        if (used) return;

        used = true;
        playSound();
        explodeTrails(owner, opponent);

        System.out.println("¡PULSO EMP! Estelas cercanas destruidas");
    }

    /**
     * Desactiva el pulso (no hace nada, es instantáneo)
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente
     */
    @Override
    public void deactivate(Player owner, Player opponent) {
        // No hay desactivación, es instantáneo
    }

    /**
     * Elimina las estelas del oponente que estén cerca del jugador
     *
     * @param owner Jugador que activa la habilidad
     * @param opponent Jugador oponente
     */
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
