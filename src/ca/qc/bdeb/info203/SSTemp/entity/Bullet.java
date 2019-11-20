package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Bullet extends Entity implements Mobile {

    private final int SPEED = 15;

    public Bullet(int x, int y, Image image) {
        super(x + 92, y + 44, image);
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setLocation(getX() + SPEED, getY());
    }
}
