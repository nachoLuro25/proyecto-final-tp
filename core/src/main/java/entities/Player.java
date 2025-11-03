package entities;

import abilities.Ability;
import utils.ColorType;
import java.awt.Point;
import java.awt.Color;

public abstract class Player {

    protected Point position;
    protected char direction;
    protected ColorType colorType;
    protected Trail trail;

    protected Ability ability;

    protected boolean speedBoost;
    protected boolean invisible;
    protected boolean controlsInverted;

    public Player(int startX, int startY, char initialDirection, ColorType colorType) {
        this.position = new Point(startX, startY);
        this.direction = initialDirection;
        this.colorType = colorType;
        this.trail = new Trail();

        this.trail.addPoint(new Point(position));

        this.speedBoost = false;
        this.invisible = false;
        this.controlsInverted = false;
    }

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

    public void changeDirection(char newDirection) {
        // Prevenir giros de 180 grados
        if (direction == 'U' && newDirection == 'D') return;
        if (direction == 'D' && newDirection == 'U') return;
        if (direction == 'L' && newDirection == 'R') return;
        if (direction == 'R' && newDirection == 'L') return;

        this.direction = newDirection;
    }

    public boolean checkCollision(Player opponent, int maxWidth, int maxHeight) {
        if (position.x < 0 || position.x >= maxWidth ||
            position.y < 0 || position.y >= maxHeight) {
            return true;
        }

        if (invisible) {
            return false;
        }

        for (Point p : trail.getPoints()) {
            if (p.equals(position) && p != trail.getLastPoint()) {
                return true;
            }
        }

        for (Point p : opponent.getTrail().getPoints()) {
            if (p.equals(position)) {
                return true;
            }
        }

        return false;
    }

    public void activateAbility(Player opponent) {
        if (ability != null && !ability.isUsed()) {
            ability.activate(this, opponent);
        }
    }

    public void updateAbility(Player opponent) {
        if (ability != null) {
            ability.update(this, opponent);
        }
    }

    public void reset(int startX, int startY, char initialDirection) {
        this.position = new Point(startX, startY);
        this.direction = initialDirection;
        this.trail.clear();
        this.trail.addPoint(new Point(position));

        this.speedBoost = false;
        this.invisible = false;
        this.controlsInverted = false;

        if (ability != null) {
            ability.setUsed(false);
        }
    }

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
