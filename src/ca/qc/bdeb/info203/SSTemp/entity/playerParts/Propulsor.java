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
public class Propulsor extends PlayerPart {

    private int rotationAngle;

    private Image idleImage;
    private Animation transition;
    private Animation activeAnimation;
    private Animation reverseTransition;

    public Propulsor(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initTransition();
        initActiveAnimation();
        initReverseTransition();
    }

    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

    private void initTransition() {
        this.transition = new Animation();
        this.transition.addFrame(getSpriteSheet().getSubImage(1, 0), 100);
        this.transition.addFrame(getSpriteSheet().getSubImage(2, 0), 100);
        this.transition.setLooping(false);
        this.transition.stop();
    }

    private void initActiveAnimation() {
        this.activeAnimation = new Animation();
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                activeAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 50);
            }
        }
        this.activeAnimation.stop();
    }

    private void initReverseTransition() {
        this.reverseTransition = new Animation();
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(2, 0), 50);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(1, 0), 50);
        this.reverseTransition.setLooping(false);
        this.reverseTransition.stop();
    }

    public void verifyPropulsorState(boolean moving) {
        if (moving) {
            if (!transition.isStopped()) {
                if (transition.getFrame() == transition.getFrameCount() - 1) {
                    transition.stop();
                    activeAnimation.restart();
                }
            } else if (!reverseTransition.isStopped()) {
                reverseTransition.stop();
                transition.restart();
            } else if (activeAnimation.isStopped()) {
                transition.restart();
            }
        } else {
            if (!activeAnimation.isStopped()) {
                activeAnimation.stop();
                reverseTransition.restart();
            } else if (!transition.isStopped()) {
                transition.stop();
                reverseTransition.restart();
            } else if (!reverseTransition.isStopped()) {
                if (reverseTransition.getFrame() == reverseTransition.getFrameCount() - 1) {
                    reverseTransition.stop();
                }
            }
        }
    }

    public void setPropulsorRotation(double x, double y) {
        this.rotationAngle = (int) Math.toDegrees(Math.atan2(y, x));
    }

    @Override
    public void dessiner(Graphics g) {

        g.rotate(getX() + 38, getY() + 17, rotationAngle);

        if (!activeAnimation.isStopped()) {
            g.drawAnimation(activeAnimation, getX(), getY());
        } else if (!transition.isStopped()) {
            g.drawAnimation(transition, getX(), getY());
        } else if (!reverseTransition.isStopped()) {
            g.drawAnimation(reverseTransition, getX(), getY());
        } else {
            g.drawImage(idleImage, getX(), getY());
        }

        g.resetTransform();
    }
}
