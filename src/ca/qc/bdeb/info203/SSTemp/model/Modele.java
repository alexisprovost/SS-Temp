package ca.qc.bdeb.info203.SSTemp.model;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author 1850986
 */
public class Modele {

    private final int MAX_HEALTH = 384;
    private final int MAX_INVENTORY = 16384;
    private int health;
    private int rockInventory;
    private int rockOnMars;
    private boolean playerIsDead = false;
    private int nbEnvoieSurMars = 0;
    private Instant gameStart;
    private Instant now;
    private String time = "";

    public Modele() {
        this.health = MAX_HEALTH;
    }

    public void removeHealth(int asteroidSize) {
        switch (asteroidSize) {
            case 256:
                health -= 128;
                break;
            case 128:
                health -= 96;
                break;
            case 64:
                health -= 78;
                break;
            case 32:
                health -= 64;
                break;
            case 16:
                health -= 48;
                break;
            default:
                System.out.println("Invalid Asteroid Size");
                break;
        }
        if (health <= 0) {
            playerIsDead = true;
            health = 0;
        }
    }

    public void fillInventory(int asteroidSize) {
        rockInventory += (asteroidSize * asteroidSize) / 2;
        if (rockInventory > MAX_INVENTORY) {
            rockInventory = MAX_INVENTORY;
        }
    }

    public void envoyerSurMars() {
        rockOnMars += rockInventory;
        rockInventory = 0;
        nbEnvoieSurMars++;
    }

    public double getFilledPercentage() {
        return (double) rockInventory / (double) MAX_INVENTORY;
    }

    public boolean isInventoryFull() {
        return rockInventory >= MAX_INVENTORY;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public int getMaxInventory() {
        return MAX_INVENTORY;
    }

    public int getHealth() {
        return health;
    }

    public int getRockInventory() {
        return rockInventory;
    }

    public int getRockOnMars() {
        return rockOnMars;
    }

    public boolean isPlayerIsDead() {
        return playerIsDead;
    }

    public int getNbEnvoieSurMars() {
        return nbEnvoieSurMars;
    }

    public void startTime() {
        gameStart = Instant.now();
    }

    public String getElapsedTime() {
        if (!playerIsDead) {
            now = Instant.now();
            Duration timeElapsed = Duration.between(gameStart, now);
            long secondsElapsed = timeElapsed.toSeconds();
            time = String.format("%02d:%02d", (secondsElapsed % 3600) / 60, (secondsElapsed % 60));
        }
        return time;
    }
}
