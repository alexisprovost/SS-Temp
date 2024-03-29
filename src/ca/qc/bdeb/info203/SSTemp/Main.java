package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.vue.Jeu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Main {

    /**
     * Largeur de l'écran.
     */
    private static int screenWidth;
    /**
     * Hauteur de l'écran.
     */
    private static int screenHeight;

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Jeu());

            screenWidth = app.getScreenWidth();
            screenHeight = app.getScreenHeight();

            //app.setMinimumLogicUpdateInterval(15);
            app.setDisplayMode(screenWidth, screenHeight, true);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
