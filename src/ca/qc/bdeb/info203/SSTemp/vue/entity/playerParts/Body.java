package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.*;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Body extends PlayerPart {

    private Image idleImage;
    private Animation absorbAnimation;

    public Body(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initAbsorbAnimation();
    }

    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

    private void initAbsorbAnimation() {
        this.absorbAnimation = new Animation();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (!((i == 0 && j == 0) || (i == 3 && j == 2))) {
                    this.absorbAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 100);
                }
            }
        }
        this.absorbAnimation.setLooping(false);
        this.absorbAnimation.stop();
    }

    @Override
    public void dessiner(Graphics g) {
        if (absorbAnimation.isStopped()) {
            g.drawImage(idleImage, getX(), getY());
        } else {
            g.drawAnimation(absorbAnimation, getX(), getY());
        }
    }

    public void startAnimation() {
        absorbAnimation.restart();
    }
}
