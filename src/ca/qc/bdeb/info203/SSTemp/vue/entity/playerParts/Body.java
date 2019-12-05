package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.*;

/**
 * Section principale du vaisseau
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Body extends PlayerPart {

    /**
     * Image lorsqu'il n'y a pas d'animation
     */
    private Image idleImage;

    /**
     * Animation lorsque le vaisseau absorbe un asteroide
     */
    private Animation absorbAnimation;

    /**
     * Couleur du vaisseau
     */
    private Color renderColor;

    /**
     * Nombre de frames ou l'image reste d'une couleur
     */
    private final int FLICKER_LENGTH = 4;

    /**
     * Nombre de fois que la couleur alterne lorsque le vaisseau prend des
     * degats
     */
    private final int MAX_FLICKER_COUNT = 8;

    /**
     * Nombre de frame depuis le dernier changement de couleur
     */
    private int frameCounter;

    /**
     *
     */
    private int flickerCount;

    /**
     * Indique si le vaisseau a pris des degats
     */
    private boolean takeDamage;

    /**
     * Indique si le vaisseau est invisible ou non
     */
    private boolean flicker;

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
                    flicker = false;
                    takeDamage = false;
                } else {
                    flicker = !flicker;
                }
            } else {
                frameCounter++;
            }
        }

        if (flicker) {
            renderColor = Color.transparent;
        } else {
            renderColor = null;
        }
    }

    public void startAnimation() {
        absorbAnimation.restart();
    }
}
