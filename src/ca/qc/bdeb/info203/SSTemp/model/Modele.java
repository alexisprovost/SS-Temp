package ca.qc.bdeb.info203.SSTemp.model;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Modele {

    /**
     * Points de vie maximaux du joueur
     */
    private final int MAX_HEALTH = 384;

    /**
     * Espace maximal dans l'inventaire
     */
    private final int MAX_INVENTORY = 16384;

    /**
     * Points de vie actuels du joueur
     */
    private int health;

    /**
     * Espace utilise actuel dans l'inventaire
     */
    private int rockInventory;

    /**
     * Quantite de roche deja envoyees sur Mars
     */
    private int rockOnMars;

    /**
     * Indique si le joueur est mort
     */
    private boolean playerIsDead = false;

    /**
     * Instant ou la partie a commence
     */
    private Instant gameStart;

    /**
     * Instant actuel
     */
    private Instant now;

    /**
     * Texte affichant le temps depuis le debut de la partie
     */
    private String time = "";

    /**
     * Constructeur du modele
     */
    public Modele() {
        this.health = MAX_HEALTH;
    }

    /**
     * Enleve de la vie au joueur en fonction de la taille de l'asteroide touche
     *
     * @param asteroidSize Taille de l'asteroide touche
     */
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

    /**
     * Remplit l'inventaire avec un asteroide
     *
     * @param asteroidSize Taille de l'asteroide rammasse
     */
    public void fillInventory(int asteroidSize) {
        rockInventory += (asteroidSize * asteroidSize) / 2;
        if (isInventoryFull()) {
            rockInventory = MAX_INVENTORY;
        }
    }

    /**
     * Vide l'inventaire et envoit tout sur Mars
     */
    public void envoyerSurMars() {
        rockOnMars += rockInventory;
        rockInventory = 0;
    }

    /**
     * Calcule la fraction de l'inventaire qui est remplie
     *
     * @return Fraction de l'inventaire qui est remplie
     */
    public double getFilledPercentage() {
        return (double) rockInventory / (double) MAX_INVENTORY;
    }

    /**
     * Verifie si l'inventaire est plein
     *
     * @return True si l'inventaire est plein, false si non
     */
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

    /**
     * Determine le debut de la partie
     */
    public void startTime() {
        gameStart = Instant.now();
    }

    /**
     * Calcule le temps ecoule depuis le debut de la partie
     *
     * @return
     */
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
