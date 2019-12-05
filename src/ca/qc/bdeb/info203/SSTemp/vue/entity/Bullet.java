package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.CoreColorPicker;
import ca.qc.bdeb.info203.SSTemp.vue.res.Collisionable;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Projectiles tires par le vaisseau
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Bullet extends Entity implements Mobile, Collisionable {

    /**
     * Vitesse des projectiles
     */
    private final int SPEED = 15;

    /**
     * Position initiale du projectile en X
     */
    private final int INITIAL_X;

    /**
     * Objet qui calcule la couleur du projectile en fonction du nombre de roche
     * dans l'inventaire
     */
    private CoreColorPicker coreColorPicker;

    /**
     * Constructeur du projectile
     *
     * @param x Position en X du projectile
     * @param y Position en Y du projectile
     * @param imagePath Chemin vers l'image du projectile
     * @param coreColorPicker Objet qui calcule la couleur du projectile en
     * fonction du nombre de roche dans l'inventaire
     */
    public Bullet(int x, int y, String imagePath, CoreColorPicker coreColorPicker) {
        super(x + 92, y + 44, imagePath);
        this.INITIAL_X = x + 92;
        this.coreColorPicker = coreColorPicker;
    }
    
    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), coreColorPicker.getTransitionColor());
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
