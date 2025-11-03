package abilities;

import entities.Player;
import audio.AudioManager;

/**
 * Clase abstracta que representa una habilidad especial
 *
 * APLICACIÓN DE POLIMORFISMO:
 * Cada habilidad concreta (SpeedBoost, GhostMode, etc.)
 * extiende esta clase e implementa su propio comportamiento
 *
 * @author Tu Nombre
 * @version 1.0
 */
public abstract class Ability {

    protected boolean used;           // ¿Ya fue usada?
    protected int duration;           // Duración en frames
    protected int timer;              // Contador actual
    protected String name;            // Nombre de la habilidad
    protected String soundEffect;    // Sonido asociado

    /**
     * Constructor de la habilidad
     * @param name Nombre de la habilidad
     * @param duration Duración en frames (125 frames ≈ 5 segundos)
     * @param soundEffect Nombre del efecto de sonido
     */
    public Ability(String name, int duration, String soundEffect) {
        this.name = name;
        this.duration = duration;
        this.soundEffect = soundEffect;
        this.used = false;
        this.timer = 0;
    }

    /**
     * Método abstracto que cada habilidad debe implementar
     * Define qué hace la habilidad al activarse
     *
     * @param owner Jugador que activa la habilidad
     * @param opponent Jugador oponente
     */
    public abstract void activate(Player owner, Player opponent);

    /**
     * Método abstracto para desactivar la habilidad
     * Define qué hacer cuando la habilidad termina
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente
     */
    public abstract void deactivate(Player owner, Player opponent);

    /**
     * Actualiza el temporizador de la habilidad
     * Se llama en cada frame del juego
     *
     * @param owner Jugador dueño de la habilidad
     * @param opponent Jugador oponente
     */
    public void update(Player owner, Player opponent) {
        if (timer > 0) {
            timer--;
            if (timer <= 0) {
                deactivate(owner, opponent);
            }
        }
    }

    /**
     * Verifica si la habilidad ya fue usada
     * @return true si ya fue usada, false si no
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Establece si la habilidad fue usada
     * @param used true para marcarla como usada
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Obtiene el tiempo restante de la habilidad
     * @return Frames restantes
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Obtiene el nombre de la habilidad
     * @return Nombre de la habilidad
     */
    public String getName() {
        return name;
    }

    /**
     * Reproduce el sonido de la habilidad
     */
    protected void playSound() {
        if (soundEffect != null) {
            AudioManager.getInstance().reproducirEfecto(soundEffect);
        }
    }

    /**
     * Verifica si la habilidad está actualmente activa
     * @return true si está activa, false si no
     */
    public boolean isActive() {
        return timer > 0;
    }
}
