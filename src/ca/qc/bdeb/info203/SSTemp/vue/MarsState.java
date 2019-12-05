package ca.qc.bdeb.info203.SSTemp.vue;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class MarsState {

    private boolean gamePaused;
    private boolean goingToMars;
    private boolean onMars;
    private boolean spawnParachute;
    private boolean removeParachute;
    private boolean leavingMars;
    private boolean resetAsteroid;
    private boolean hideAsteroids;

    private int initialX;
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

    public void setInitialCoordinates(int x, int y) {
        this.initialX = x;
        this.initialY = y;
    }
}
