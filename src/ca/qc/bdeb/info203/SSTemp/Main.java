package ca.qc.bdeb.info203.SSTemp;

import java.awt.Dimension;
import java.awt.Toolkit;
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
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            screenWidth = (int)screenSize.getWidth();
            screenHeight = (int)screenSize.getHeight();
            AppGameContainer app = new AppGameContainer(new Jeu(screenWidth, screenHeight));
            app.setDisplayMode(screenWidth, screenHeight, false);
            app.setShowFPS(false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
