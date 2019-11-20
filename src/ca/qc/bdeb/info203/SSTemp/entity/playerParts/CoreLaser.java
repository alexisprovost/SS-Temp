package ca.qc.bdeb.info203.SSTemp.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.entity.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class CoreLaser extends PlayerPart {

    private Image idleImage;
    private Animation transition;
    private Animation shootingAnimation;
    private Animation reverseTransition;

    public CoreLaser(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initTransition();
        initShootingAnimation();
        initReverseTransition();
    }

    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

    private void initTransition() {

    }

    private void initShootingAnimation() {

    }

    private void initReverseTransition() {

    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(idleImage, getX(), getY());
    }

}
