package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class Asteroid extends Entity implements Mobile {

    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
    }
    
    @Override
    public void dessiner(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
