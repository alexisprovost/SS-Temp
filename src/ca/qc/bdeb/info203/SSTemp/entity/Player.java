package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile{
    
    
    
    public Player(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}