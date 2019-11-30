package ca.qc.bdeb.info203.SSTemp.model;

/**
 *
 * @author 1850986
 */
public class Modele {

    private final int MAX_HEALTH = 384;
    private int health;
    private int rockInventory;
    private int rockOnMars;

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
        if (health < 0) {
            health = 0;
        }
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
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

}
