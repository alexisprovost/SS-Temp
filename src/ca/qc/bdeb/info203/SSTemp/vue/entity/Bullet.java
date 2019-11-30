package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.res.Collisionable;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Bullet extends Entity implements Mobile, Collisionable {

    private final int SPEED = 15;
    private final int INITIAL_X;

    public Bullet(int x, int y, String imagePath) {
        super(x + 92, y + 44, imagePath);
        this.INITIAL_X = x + 92;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), new Color(166, 200, 252));
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setLocation(getX() + SPEED, getY());
        if (getX() >= this.INITIAL_X + (limiteX / 3)) {
            Image i = getImage();
            i.setAlpha(i.getAlpha() - 0.04f);
        }
        if (getX() >= this.INITIAL_X + (limiteX / 2)) {
            setDetruire(true);
        }
    }
}
