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

        animationIdle = loadAnimation(spriteSheet, 0, 1024, 0);
    }

    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x+= 256) {
            animation.addFrame(spriteSheet.getSubImage(x, y, 256, 256), 1000);
        }
        return animation;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawAnimation(animationIdle, getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
