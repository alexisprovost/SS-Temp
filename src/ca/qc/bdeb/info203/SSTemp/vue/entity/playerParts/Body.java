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
    private Color renderColor;

    private final int FLICKER_LENGTH = 3;
    private final int MAX_FLICKER_COUNT = 6;
    private int frameCounter;
    private int flickerCount;
    private boolean takeDamage;
    private boolean redFlicker;

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

    public void takeDamage() {
        takeDamage = true;
    }

    @Override
    public void dessiner(Graphics g) {
        verifyRenderColor();
        if (absorbAnimation.isStopped()) {
            g.drawImage(idleImage, getX(), getY(), renderColor);
        } else {
            g.drawAnimation(absorbAnimation, getX(), getY(), renderColor);
        }
    }

    private void verifyRenderColor() {
        if (takeDamage) {
            if (frameCounter > FLICKER_LENGTH) {
                flickerCount++;
                frameCounter = 0;
                if (flickerCount > MAX_FLICKER_COUNT) {
                    flickerCount = 0;
                    redFlicker = false;
                    takeDamage = false;
                } else {
                    redFlicker = !redFlicker;
                }
            } else {
                frameCounter++;
            }
        }

        if (redFlicker) {
            renderColor = Color.red;
        } else {
            renderColor = null;
        }
    }

    public void startAnimation() {
        absorbAnimation.restart();
    }
}
