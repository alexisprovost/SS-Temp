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
    private final int INITIAL_X;

    public Bullet(int x, int y, String imagePath) {
        super(x + 92, y + 44, imagePath);
        this.INITIAL_X = x + 92;
    }

    @Override
    public void dessiner(Graphics g) {
        //g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setLocation(getX() + SPEED, getY());
        if(getX() >= this.INITIAL_X + (limiteX/3)){
            Image i = getImage();
            i.setAlpha(i.getAlpha() - 0.04f);
        }
        if(getX() >= this.INITIAL_X + (limiteX/2)){
            setDetruire(true);
        }
    }
}
