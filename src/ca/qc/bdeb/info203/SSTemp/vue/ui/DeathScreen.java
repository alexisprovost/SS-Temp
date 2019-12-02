package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Manuel Ramirez
 */
public class DeathScreen extends Entity {

    private Image deathBg;
    private int largeurEcran;
    private int longeurEcran;

    public DeathScreen(String imagePath, int largeurEcran, int longeurEcran) {
        this.largeurEcran = largeurEcran;
        this.longeurEcran = longeurEcran;
        
        try {
            this.deathBg = new Image(imagePath);

        } catch (SlickException e) {

        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(deathBg, 0, 0);

        g.drawString("GAME OVER", largeurEcran/2, longeurEcran/2);
        
        g.drawString("Press R to restart", largeurEcran/2, longeurEcran/2 + 30);

        deathBg.setAlpha(0);

        while (deathBg.getAlpha() < 1) {
            deathBg.setAlpha(deathBg.getAlpha() + 0.000001f);
        }

    }

}
