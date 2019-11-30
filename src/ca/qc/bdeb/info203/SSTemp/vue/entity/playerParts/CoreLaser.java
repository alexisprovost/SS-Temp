package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class CoreLaser extends PlayerPart {

    private Image idleImage;
    private Animation shootingAnimation;
    private Animation reverseTransition;

    public CoreLaser(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initShootingAnimation();
        initReverseTransition();
    }

    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

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

    private void initReverseTransition() {
        this.reverseTransition = new Animation();
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(0, 2), 30);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(1, 1), 30);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(0, 1), 30);
        this.reverseTransition.setLooping(false);
        this.reverseTransition.stop();
    }

    public void startAnimation() {
        this.shootingAnimation.restart();
    }

    public void startTransition() {
        this.reverseTransition.restart();
    }

    public boolean isShooting() {
        return !shootingAnimation.isStopped();
    }

    public boolean isIdle() {
        return shootingAnimation.isStopped() && reverseTransition.isStopped();
    }

    @Override
    public void dessiner(Graphics g) {
        if (!shootingAnimation.isStopped()) {
            g.drawAnimation(shootingAnimation, getX(), getY(), new Color(166, 200, 252));

        } else if (!reverseTransition.isStopped()) {
            g.drawAnimation(reverseTransition, getX(), getY(), new Color(166, 200, 252));
        } else {
            g.drawImage(idleImage, getX(), getY(), new Color(166, 200, 252));
        }
    }
}
