package ca.qc.bdeb.info203.SSTemp.vue;

/**
 * Classe qui contient l'etat du voyage vers Mars
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class MarsState {

    /**
     * Indique si le jeu est sur pause ou non
     */
    private boolean gamePaused;

    /**
     * Indique si le vaisseau est en route vers Mars ou non
     */
    private boolean goingToMars;

    /**
     * Indique si le vaisseau est sur Mars ou non
     */
    private boolean onMars;

    /**
     * Indique si le parachute doit apparaitre ou non
     */
    private boolean spawnParachute;

    /**
     * Indique si le parachute doit disparaitre ou non
     */
    private boolean removeParachute;

    /**
     * Indique si le vaisseau est en train de quitter Mars ou non
     */
    private boolean leavingMars;

    /**
     * Indique si les asteroides devraient retourner a droite de l'ecran ou non
     */
    private boolean resetAsteroid;

    /**
     * Indique si les asteroides devraient disparaitre ou non
     */
    private boolean hideAsteroids;

    /**
     * Position initiale du vaisseau en X lorsqu'il va vers Mars
     */
    private int initialX;

    /**
     * Position initiale du vaisseau en Y lorsqu'il va vers Mars
     */
    private int initialY;

    public boolean isGamePaused() {
        return gamePaused;
    }

    public boolean isOnMars() {
        return onMars;
    }

    public boolean isSpawnParachute() {
        return spawnParachute;
    }

    public boolean isLeavingMars() {
        return leavingMars;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public boolean isGoingToMars() {
        return goingToMars;
    }

    public boolean isRemoveParachute() {
        return removeParachute;
    }

    public void setPauseGame(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void setOnMars(boolean onMars) {
        this.onMars = onMars;
    }

    public void setSpawnParachute(boolean spawnParachute) {
        this.spawnParachute = spawnParachute;
    }

    public void setLeaveMars(boolean leavingMars) {
        this.leavingMars = leavingMars;
    }

    public void setGoingToMars(boolean goingToMars) {
        this.goingToMars = goingToMars;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void setRemoveParachute(boolean removeParachute) {
        this.removeParachute = removeParachute;
    }

    public void setResetAsteroid(boolean resetAsteroid) {
        this.resetAsteroid = resetAsteroid;
    }

    public boolean isResetAsteroid() {
        return resetAsteroid;
    }

    public void setHideAsteroids(boolean hideAsteroids) {
        this.hideAsteroids = hideAsteroids;
    }

    public boolean isHideAsteroids() {
        return hideAsteroids;
    }

    /**
     * Initialise les coordonees du vaisseau quand il se rend vers Mars
     *
     * @param x Position initiale du vaisseau en X lorsqu'il va vers Mars
     * @param y Position initiale du vaisseau en Y lorsqu'il va vers Mars
     */
    public void setInitialCoordinates(int x, int y) {
        this.initialX = x;
        this.initialY = y;
    }

}
