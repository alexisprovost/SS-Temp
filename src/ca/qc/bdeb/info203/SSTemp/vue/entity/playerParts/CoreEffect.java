package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.CoreColorPicker;
import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Effet au centre du vaisseau
 * 
 * @author Manuel Ramirez, Alexis Provost
 */
public class CoreEffect extends PlayerPart {

    /**
     * Animation de base de cet objet
     */
    private Animation idleAnimation;
    
    /**
     * Objet qui calcule la couleur du core en fonction du nombre de roche
     * dans l'inventaire
     */
    private CoreColorPicker coreColorPicker;

    /**
     * Constructeur de l'effet au centre du vaisseau
     * 
     * @param player Joueur sur lequel cette partie est attache
     * @param spriteSheet Spritesheet qui contient les images de cette partie du vaisseau
     * @param xOffset Offset de cette composante en X par rapport au Player
     * @param yOffset Offset de cette composante en Y par rapport au Player
     * @param coreColorPicker Objet qui calcule la couleur du core en fonction du nombre de roche
     * dans l'inventaire
     */
    public CoreEffect(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset, CoreColorPicker coreColorPicker) {
        super(player, spriteSheet, xOffset, yOffset);
        this.coreColorPicker = coreColorPicker;
        initIdleAnimation();
    }

    /**
     * Initialise l'animation de base de cet objet
     */
    public void initIdleAnimation() {
        this.idleAnimation = new Animation();
        Image blankFrame = null;
        try {
            blankFrame = new Image(0, 0);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
        for (int i = 0; i < 3; i++) {
            idleAnimation.addFrame(blankFrame, 200);
            for (int j = 0; j < 5; j++) {
                idleAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 100);
            }
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawAnimation(idleAnimation, getX(), getY(), coreColorPicker.getTransitionColor());
    }

}
