package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class Asteroid extends Entity implements Mobile {

    private SpriteSheet spriteSheet;
    private Animation animationIdle;

    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
        this.spriteSheet = spriteSheet;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
            setLocation(getX() + 1, getY());
    }

}
