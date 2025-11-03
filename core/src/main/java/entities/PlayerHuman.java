package entities;

import abilities.*;
import utils.ColorType;

/**
 * Clase que representa a un jugador humano
 *
 * HERENCIA: Extiende de Player
 * - Hereda todos los atributos y métodos de Player
 * - Agrega funcionalidad específica para jugadores humanos
 *
 * @author Tu Nombre
 * @version 1.0
 */
public class PlayerHuman extends Player {

    private int playerNumber; // 1 o 2

    /**
     * Constructor de PlayerHuman
     * Asigna automáticamente la habilidad según el color
     *
     * @param startX Posición X inicial
     * @param startY Posición Y inicial
     * @param initialDirection Dirección inicial
     * @param colorType Tipo de color del jugador
     * @param playerNumber Número de jugador (1 o 2)
     */
    public PlayerHuman(int startX, int startY, char initialDirection,
                       ColorType colorType, int playerNumber) {
        super(startX, startY, initialDirection, colorType);
        this.playerNumber = playerNumber;

        // Asignar habilidad según el color (POLIMORFISMO)
        assignAbilityByColor();
    }

    /**
     * Asigna la habilidad correspondiente según el color del jugador
     *
     * POLIMORFISMO: Cada ColorType tiene una habilidad diferente
     * pero todas son del tipo Ability (clase padre)
     */
    private void assignAbilityByColor() {
        switch (colorType) {
            case ROJO:
                this.ability = new EMPPulse();
                break;
            case AZUL:
                this.ability = new SpeedBoost();
                break;
            case AMARILLO:
                this.ability = new GhostMode();
                break;
            case VERDE:
                this.ability = new ControlInversion();
                break;
            default:
                this.ability = null;
        }
    }

    /**
     * Procesa la entrada de teclado y cambia la dirección
     * Maneja la inversión de controles si está activa
     *
     * @param input Dirección deseada ('U', 'D', 'L', 'R')
     */
    public void processInput(char input) {
        // Si los controles están invertidos, invertir la entrada
        if (controlsInverted) {
            input = invertDirection(input);
        }

        changeDirection(input);
    }

    /**
     * Invierte una dirección (para la habilidad de inversión de controles)
     * @param dir Dirección original
     * @return Dirección invertida
     */
    private char invertDirection(char dir) {
        switch (dir) {
            case 'U': return 'D';
            case 'D': return 'U';
            case 'L': return 'R';
            case 'R': return 'L';
            default: return dir;
        }
    }

    /**
     * Obtiene el número de jugador
     * @return 1 o 2
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Obtiene la cantidad de movimientos por frame
     * Depende de si tiene SpeedBoost activo
     *
     * @return 2 si tiene velocidad aumentada, 1 si no
     */
    public int getMovesPerFrame() {
        return speedBoost ? 2 : 1;
    }

    /**
     * Verifica si puede usar su habilidad
     * @return true si aún no la usó, false si ya la usó
     */
    public boolean canUseAbility() {
        return ability != null && !ability.isUsed();
    }

    /**
     * Obtiene el nombre de la habilidad del jugador
     * @return Nombre de la habilidad
     */
    public String getAbilityName() {
        return ability != null ? ability.getName() : "SIN HABILIDAD";
    }

    /**
     * Verifica si la habilidad está actualmente activa
     * @return true si está activa, false si no
     */
    public boolean isAbilityActive() {
        return ability != null && ability.isActive();
    }

    /**
     * Obtiene el tiempo restante de la habilidad
     * @return Frames restantes (0 si no está activa)
     */
    public int getAbilityTimeLeft() {
        return ability != null ? ability.getTimer() : 0;
    }
}
