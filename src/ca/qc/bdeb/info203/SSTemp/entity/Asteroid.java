package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Asteroid extends Entity implements Mobile {

    private SpriteSheet spriteSheet;
    private Animation[] asteroidFrames = new Animation[4];
    private int direction = 0;
    private boolean moving = false;
    private int x;
    private int y;

    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;

        asteroidFrames[0] = loadAnimation(spriteSheet, 0, 1, 0);
    }

    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    @Override
    public void dessiner(Graphics g) {
        //g.drawImage(asteroidFrames[0], 0, 0);
        g.drawAnimation(asteroidFrames[direction + (moving ? 4 : 0)], x, y);
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
