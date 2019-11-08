package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Bullet extends Entity implements Mobile {

    private int frameCounter = 0;
    private int animationFrame = 0;
    private boolean animationReverse = false;
    private SpriteSheet spriteSheet;

    public Bullet(int x, int y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 0);
        this.spriteSheet = spriteSheet;
    }

    @Override
    public void dessiner(Graphics g) {
        if (frameCounter >= 10) {
            if (animationReverse) {
                animationFrame--;
                if (animationFrame == 0) {
                    animationReverse = false;
                }
            } else {
                animationFrame++;
                if (animationFrame == 2) {
                    animationReverse = true;
                }
            }
            setImage(spriteSheet, 0, animationFrame);
            frameCounter = 0;
        }
        g.drawImage(getImage(), 100, 100);
        frameCounter++;
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
