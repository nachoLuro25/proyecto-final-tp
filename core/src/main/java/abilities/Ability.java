package abilities;

import entities.Player;
import audio.AudioManager;

public abstract class Ability {

    protected boolean used;
    protected int duration;
    protected int timer;
    protected String name;
    protected String soundEffect;

    public Ability(String name, int duration, String soundEffect) {
        this.name = name;
        this.duration = duration;
        this.soundEffect = soundEffect;
        this.used = false;
        this.timer = 0;
    }

    public abstract void activate(Player owner, Player opponent);

    public abstract void deactivate(Player owner, Player opponent);

    public void update(Player owner, Player opponent) {
        if (timer > 0) {
            timer--;
            if (timer <= 0) {
                deactivate(owner, opponent);
            }
        }
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getTimer() {
        return timer;
    }

    public String getName() {
        return name;
    }

    protected void playSound() {
        if (soundEffect != null) {
            AudioManager.getInstance().reproducirEfecto(soundEffect);
        }
    }

    public boolean isActive() {
        return timer > 0;
    }
}
