package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.vue.Jeu;
import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Stéphane Lévesque, Manuel Ramirez, Alexis Provost
 */
public class Main {

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Largeur de l'écran.
     */
    private static int screenWidth = (int) screenSize.getWidth();
    /**
     * Hauteur de l'écran.
     */
    private static int screenHeight = (int) screenSize.getHeight();

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Jeu(screenWidth, screenHeight));
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
