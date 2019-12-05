package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 * Parachute qui est depose sur Mars
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Parachute extends Entity implements Mobile {

    /**
     * Vitesse de chute du parachute
     */
    private final int FALLING_SPEED = 3;

    /**
     * Hauteur du plancher sur Mars
     */
    private final int FLOOR_HEIGHT = 196;

    /**
     * Spritesheet qui contient l'image du parachute
     */
    private SpriteSheet spriteSheet;

    /**
     * Constructeur du parachute
     *
     * @param x Position de parachute en X
     * @param y Position de parachute en Y
     * @param spriteSheet Spritesheet qui contient l'image du parachute
     */
    public Parachute(int x, int y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 0, 0);
        this.spriteSheet = spriteSheet;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        if (getY() < limiteY - FLOOR_HEIGHT) {
            setLocation(getX(), getY() + FALLING_SPEED);
        } else if (getImage() == spriteSheet.getSubImage(0, 0)) {
            setImage(spriteSheet.getSubImage(1, 0));
        }
    }

}
