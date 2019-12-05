package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.CoreColorPicker;
import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Core au centre du vaisseau
 * 
 * @author Manuel Ramirez, Alexis Provost
 */
public class CoreLaser extends PlayerPart {

    /**
     * Image de base du core
     */
    private Image idleImage;
    
    /**
     * Animation lorsque le vaisseau tire un projectile
     */
    private Animation shootingAnimation;
    
    /**
     * Transition pour que le core retourne a son etat idle
     */
    private Animation reverseTransition;
    
    /**
     * Objet qui calcule la couleur du core en fonction du nombre de roche
     * dans l'inventaire
     */
    private CoreColorPicker coreColorPicker;

    /**
     * Constructeur du core au centre du vaisseau
     * 
     * @param player Joueur sur lequel cette partie est attache
     * @param spriteSheet Spritesheet qui contient les images de cette partie du vaisseau
     * @param xOffset Offset de cette composante en X par rapport au Player
     * @param yOffset Offset de cette composante en Y par rapport au Player
     * @param coreColorPicker Objet qui calcule la couleur du core en
     * fonction du nombre de roche dans l'inventaire
     */
    public CoreLaser(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset, CoreColorPicker coreColorPicker) {
        super(player, spriteSheet, xOffset, yOffset);
        this.coreColorPicker = coreColorPicker;
        initIdleImage();
        initShootingAnimation();
        initReverseTransition();
    }

    /**
     * Initialise l'image de base du core
     */
    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }
    
    /**
     * Initialise l'animation de tir
     */
    private void initShootingAnimation() {
        this.shootingAnimation = new Animation();
        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                this.shootingAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 10);
            }
        }
        this.shootingAnimation.setLooping(false);
        this.shootingAnimation.stop();
    }

    /**
     * Initialise la transition pour que le core retourne a son etat idle
     */
    private void initReverseTransition() {
        this.reverseTransition = new Animation();
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(0, 2), 30);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(1, 1), 30);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(0, 1), 30);
        this.reverseTransition.setLooping(false);
        this.reverseTransition.stop();
    }

    /**
     * Commence l'animation
     */
    public void startAnimation() {
        this.shootingAnimation.restart();
    }

    /**
     * Commence la transition vers l'etat idle
     */
    public void startTransition() {
        this.reverseTransition.restart();
    }

    /**
     * Verifie si le vaisseau est en train de tirer
     * 
     * @return Vrai si le vaisseau est en train de tirer, faux si non
     */
    public boolean isShooting() {
        return !shootingAnimation.isStopped();
    }

    /**
     * Verifie si le vaisseau est dans son etat normal
     * 
     * @return Vrai si le vaisseau est dans son etat normal, faux si non
     */
    public boolean isIdle() {
        return shootingAnimation.isStopped() && reverseTransition.isStopped();
    }

    @Override
    public void dessiner(Graphics g) {
        if (!shootingAnimation.isStopped()) {
            g.drawAnimation(shootingAnimation, getX(), getY(), coreColorPicker.getTransitionColor());

        } else if (!reverseTransition.isStopped()) {
            g.drawAnimation(reverseTransition, getX(), getY(), coreColorPicker.getTransitionColor());
        } else {
            g.drawImage(idleImage, getX(), getY(), coreColorPicker.getTransitionColor());
        }
    }
}
