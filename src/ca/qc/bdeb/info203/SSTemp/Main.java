package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.vue.Jeu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author SLevesque
 */
public class Main {

    /**
     * Largeur de l'écran.
     */
    public static int screenWidth;
    /**
     * Hauteur de l'écran.
     */
    public static int screenHeight;

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Jeu(1920, 1080));
            app.setDisplayMode(1920, 1080, true);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
            //app.setMinimumLogicUpdateInterval(15);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
