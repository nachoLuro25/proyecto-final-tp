package entities;

import abilities.Ability;
import utils.ColorType;
import java.awt.Point;
import java.awt.Color;

/**
 * Clase abstracta que representa a un jugador
 *
 * APLICACIÓN DE HERENCIA Y POLIMORFISMO:
 * - Es la clase padre de todos los tipos de jugadores
 * - Define atributos y comportamientos comunes
 * - PlayerHuman extiende esta clase
 *
 * @author Tu Nombre
 * @version 1.0
 */
public abstract class Player {

    // Atributos básicos
    protected Point position;           // Posición actual en el mapa
    protected char direction;           // Dirección actual ('U', 'D', 'L', 'R')
    protected ColorType colorType;      // Tipo de color (enum)
    protected Trail trail;              // Estela que deja la moto

    // Habilidad especial
    protected Ability ability;          // Habilidad según el color

    // Estados especiales (afectan al jugador)
    protected boolean speedBoost;       // ¿Tiene velocidad aumentada?
    protected boolean invisible;        // ¿Es invisible/puede atravesar estelas?
    protected boolean controlsInverted; // ¿Controles invertidos?

    /**
     * Constructor de Player
     * @param startX Posición X inicial
     * @param startY Posición Y inicial
     * @param initialDirection Dirección inicial
     * @param colorType Tipo de color del jugador
     */
    public Player(int startX, int startY, char initialDirection, ColorType colorType) {
        this.position = new Point(startX, startY);
        this.direction = initialDirection;
        this.colorType = colorType;
        this.trail = new Trail();

        // Agregar posición inicial a la estela
        this.trail.addPoint(new Point(position));

        // Estados iniciales
        this.speedBoost = false;
        this.invisible = false;
        this.controlsInverted = false;
    }

    /**
     * Mueve al jugador en la dirección actual
     * @param unit Tamaño del movimiento (12 píxeles normalmente)
     */
    public void move(int unit) {
        switch (direction) {
            case 'U': position.y -= unit; break;
            case 'D': position.y += unit; break;
            case 'L': position.x -= unit; break;
            case 'R': position.x += unit; break;
        }

        // Agregar nueva posición a la estela
        trail.addPoint(new Point(position));
    }

    /**
     * Cambia la dirección del jugador
     * Previene giros de 180° (no puedes ir directamente en reversa)
     *
     * @param newDirection Nueva dirección ('U', 'D', 'L', 'R')
     */
    public void changeDirection(char newDirection) {
        // Prevenir giros de 180 grados
        if (direction == 'U' && newDirection == 'D') return;
        if (direction == 'D' && newDirection == 'U') return;
        if (direction == 'L' && newDirection == 'R') return;
        if (direction == 'R' && newDirection == 'L') return;

        this.direction = newDirection;
    }

    /**
     * Verifica si el jugador colisionó con algo
     * @param opponent Jugador oponente
     * @param maxWidth Ancho máximo del mapa
     * @param maxHeight Alto máximo del mapa
     * @return true si hay colisión, false si no
     */
    public boolean checkCollision(Player opponent, int maxWidth, int maxHeight) {
        // Colisión con bordes
        if (position.x < 0 || position.x >= maxWidth ||
            position.y < 0 || position.y >= maxHeight) {
            return true;
        }

        // Si es invisible, no colisiona con estelas
        if (invisible) {
            return false;
        }

        // Colisión con su propia estela (excepto el último punto)
        for (Point p : trail.getPoints()) {
            if (p.equals(position) && p != trail.getLastPoint()) {
                return true;
            }
        }

        // Colisión con estela del oponente
        for (Point p : opponent.getTrail().getPoints()) {
            if (p.equals(position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Activa la habilidad especial del jugador
     * @param opponent Jugador oponente (necesario para algunas habilidades)
     */
    public void activateAbility(Player opponent) {
        if (ability != null && !ability.isUsed()) {
            ability.activate(this, opponent);
        }
    }

    /**
     * Actualiza el estado de la habilidad (para duraciones)
     * Se llama en cada frame del juego
     *
     * @param opponent Jugador oponente
     */
    public void updateAbility(Player opponent) {
        if (ability != null) {
            ability.update(this, opponent);
        }
    }

    /**
     * Reinicia el jugador a su estado inicial
     * @param startX Posición X inicial
     * @param startY Posición Y inicial
     * @param initialDirection Dirección inicial
     */
    public void reset(int startX, int startY, char initialDirection) {
        this.position = new Point(startX, startY);
        this.direction = initialDirection;
        this.trail.clear();
        this.trail.addPoint(new Point(position));

        // Resetear estados
        this.speedBoost = false;
        this.invisible = false;
        this.controlsInverted = false;

        // Resetear habilidad
        if (ability != null) {
            ability.setUsed(false);
        }
    }

    // ==================== GETTERS Y SETTERS ====================

    public Point getPosition() {
        return position;
    }

    public char getDirection() {
        return direction;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public Color getColor() {
        return colorType.getColor();
    }

    public Trail getTrail() {
        return trail;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public boolean isSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(boolean speedBoost) {
        this.speedBoost = speedBoost;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isControlsInverted() {
        return controlsInverted;
    }

    public void setControlsInverted(boolean controlsInverted) {
        this.controlsInverted = controlsInverted;
    }
}
