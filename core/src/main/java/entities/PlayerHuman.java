package entities;

import abilities.*;
import utils.ColorType;

public class PlayerHuman extends Player {

    private int playerNumber;

    public PlayerHuman(int startX, int startY, char initialDirection,
                       ColorType colorType, int playerNumber) {
        super(startX, startY, initialDirection, colorType);
        this.playerNumber = playerNumber;

        assignAbilityByColor();
    }

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

    public void processInput(char input) {
        if (controlsInverted) {
            input = invertDirection(input);
        }

        changeDirection(input);
    }

    private char invertDirection(char dir) {
        switch (dir) {
            case 'U': return 'D';
            case 'D': return 'U';
            case 'L': return 'R';
            case 'R': return 'L';
            default: return dir;
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getMovesPerFrame() {
        return speedBoost ? 2 : 1;
    }

    public boolean canUseAbility() {
        return ability != null && !ability.isUsed();
    }

    public String getAbilityName() {
        return ability != null ? ability.getName() : "SIN HABILIDAD";
    }

    public boolean isAbilityActive() {
        return ability != null && ability.isActive();
    }

    public int getAbilityTimeLeft() {
        return ability != null ? ability.getTimer() : 0;
    }
}
