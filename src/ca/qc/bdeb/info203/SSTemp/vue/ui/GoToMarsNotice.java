package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class GoToMarsNotice extends Entity {

    private String warning = "INVENTAIRE PLEIN. APPUYEZ SUR E POUR RETOURNER SUR MARS";
    private boolean redFlicker;
    private int flickerLength;
    private int frameCounter;
    private int stringWidth;
    private int stringHeight;

    public GoToMarsNotice(int flickerLength, int screenWidth, int screenHeight) {
        super(screenWidth / 2, screenHeight / 2, 0, 0);
        this.flickerLength = flickerLength;
    }

    @Override
    public void dessiner(Graphics g) {
        this.stringWidth = g.getFont().getWidth(warning);
        this.stringHeight = g.getFont().getHeight(warning);
        if (frameCounter > flickerLength) {
            redFlicker = !redFlicker;
            frameCounter = 0;
        } else {
            frameCounter++;
        }
        if (redFlicker) {
            g.setColor(Color.red);
        }
        g.drawString(warning, getX() - stringWidth / 2, getY() - stringHeight / 2);
        g.setColor(Color.white);
    }

}
