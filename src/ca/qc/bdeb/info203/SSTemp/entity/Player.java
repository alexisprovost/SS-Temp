package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile {

    private Image blankFrame;

    private SpriteSheet bodySpriteSheet;
    private SpriteSheet coreLaserSpriteSheet;
    private SpriteSheet coreEffectSpriteSheet;
    private SpriteSheet propulsorSpriteSheet;

    private Image bodyIdleImage;
    private Animation bodyAbsorbAnimation;

    private Animation coreEffectIdleAnimation;
    private Image coreLaserIdleImage;
    private Animation coreLaserTransition;
    private Animation coreLaserShootingAnimation;

    private Image rightPropulsorIdleImage;
    private Animation rightPropulsorTransition;
    private Animation rightPropulsorReverseTransition;
    private Animation rightPropulsorActiveAnimation;
    private Image leftPropulsorIdleImage;
    private Animation leftPropulsorTransition;
    private Animation leftPropulsorReverseTransition;
    private Animation leftPropulsorActiveAnimation;

    private boolean inLaserTransition;
    private boolean inPropulsorTransition;
    private boolean inReversePropulsorTransition;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    private final int MAX_SPEED = 8;
    private int speedX = 0;
    private int speedY = 0;
    private int acceleration = 1;

    public Player(int x, int y, SpriteSheet bodySpriteSheet, SpriteSheet coreLaserSpriteSheet, SpriteSheet coreEffectSpriteSheet, SpriteSheet propulsorSpriteSheet) {
        super(x, y, 96, 128);
        this.bodySpriteSheet = bodySpriteSheet;
        this.coreLaserSpriteSheet = coreLaserSpriteSheet;
        this.coreEffectSpriteSheet = coreEffectSpriteSheet;
        this.propulsorSpriteSheet = propulsorSpriteSheet;
        try {
            this.blankFrame = new Image(0, 0);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
        }

        loadBodyAnimations();
        loadCoreLaserAnimations();
        loadCoreEffectAnimation();
        loadPropulsorAnimations();
    }

    @Override
    public void dessiner(Graphics g) {

        g.drawAnimation(coreEffectIdleAnimation, getX() + 33, getY() + 57);

//        if (bodyAbsorbAnimation.isStopped()) {
        g.drawImage(bodyIdleImage, getX() + 10, getY() - 13);
//        } else {
//            g.drawAnimation(bodyAbsorbAnimation, getX() + 10, getY() - 13);
//        }

//        if (inLaserTransition) {
//            g.drawAnimation(coreLaserTransition, getX() + 40, getY());
//        } else if (coreLaserTransition.isStopped()) {
        g.drawImage(coreLaserIdleImage, getX() + 40, getY());
//        } else {
//            g.drawAnimation(coreLaserShootingAnimation, getX() + 40, getY());
//        }

        if (inPropulsorTransition) {
            g.drawAnimation(rightPropulsorTransition, getX() + 62, getY() + 97);
            g.drawAnimation(leftPropulsorTransition, getX(), getY() + 97);
        } else if (inReversePropulsorTransition) {
            g.drawAnimation(rightPropulsorReverseTransition, getX() + 62, getY() + 97);
            g.drawAnimation(leftPropulsorReverseTransition, getX(), getY() + 97);
        } else if (rightPropulsorActiveAnimation.isStopped()) {
            g.drawImage(rightPropulsorIdleImage, getX() + 62, getY() + 97);
            g.drawImage(leftPropulsorIdleImage, getX(), getY() + 97);
        } else {
            g.drawAnimation(rightPropulsorActiveAnimation, getX() + 62, getY() + 97);
            g.drawAnimation(leftPropulsorActiveAnimation, getX(), getY() + 97);
        }
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setSpeed();
        verifyPropulsorState();
        setLocation(getX() + speedX, getY() + speedY);
    }

    private void setSpeed() {
        if (moveUp && speedY > -MAX_SPEED) {
            speedY -= acceleration;
        } else if (!moveUp && speedY < 0) {
            speedY += acceleration;
        }
        if (moveDown && speedY < MAX_SPEED) {
            speedY += acceleration;
        } else if (!moveDown && speedY > 0) {
            speedY -= acceleration;
        }
        if (moveLeft && speedX > -MAX_SPEED) {
            speedX -= acceleration;
        } else if (!moveLeft && speedX < 0) {
            speedX += acceleration;
        }
        if (moveRight && speedX < MAX_SPEED) {
            speedX += acceleration;
        } else if (!moveRight && speedX > 0) {
            speedX -= acceleration;
        }
    }

    public void moveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void moveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void moveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void moveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    private void loadBodyAnimations() {
        this.bodyIdleImage = bodySpriteSheet.getSubImage(0, 0);
    }

    private void loadCoreLaserAnimations() {
        this.coreLaserIdleImage = coreLaserSpriteSheet.getSubImage(0, 0);
    }

    private void loadCoreEffectAnimation() {
        this.coreEffectIdleAnimation = new Animation();
        int frameCounter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                frameCounter++;
                coreEffectIdleAnimation.addFrame(coreEffectSpriteSheet.getSubImage(j, i), 100);
                if (frameCounter % 5 == 0) {
                    coreEffectIdleAnimation.addFrame(blankFrame, 200);
                }
            }
        }
    }

    private void loadPropulsorAnimations() {
        this.rightPropulsorIdleImage = propulsorSpriteSheet.getSubImage(1, 0);
        this.leftPropulsorIdleImage = propulsorSpriteSheet.getSubImage(0, 0);

        this.rightPropulsorTransition = new Animation();
        this.rightPropulsorTransition.addFrame(propulsorSpriteSheet.getSubImage(3, 0), 100);
        this.rightPropulsorTransition.addFrame(propulsorSpriteSheet.getSubImage(5, 0), 100);
        this.rightPropulsorTransition.setLooping(false);

        this.leftPropulsorTransition = new Animation();
        this.leftPropulsorTransition.addFrame(propulsorSpriteSheet.getSubImage(2, 0), 100);
        this.leftPropulsorTransition.addFrame(propulsorSpriteSheet.getSubImage(4, 0), 100);
        this.leftPropulsorTransition.setLooping(false);

        this.rightPropulsorReverseTransition = new Animation();
        this.rightPropulsorReverseTransition.addFrame(propulsorSpriteSheet.getSubImage(5, 0), 100);
        this.rightPropulsorReverseTransition.addFrame(propulsorSpriteSheet.getSubImage(3, 0), 100);
        this.rightPropulsorReverseTransition.setLooping(false);

        this.leftPropulsorReverseTransition = new Animation();
        this.leftPropulsorReverseTransition.addFrame(propulsorSpriteSheet.getSubImage(4, 0), 100);
        this.leftPropulsorReverseTransition.addFrame(propulsorSpriteSheet.getSubImage(2, 0), 100);
        this.leftPropulsorReverseTransition.setLooping(false);

        this.rightPropulsorActiveAnimation = new Animation();
        for (int i = 1; i < 2; i++) {
            for (int j = 1; j < 6; j += 2) {
                rightPropulsorActiveAnimation.addFrame(propulsorSpriteSheet.getSubImage(j, i), 50);
            }
        }
        this.rightPropulsorActiveAnimation.stop();

        this.leftPropulsorActiveAnimation = new Animation();
        for (int i = 1; i < 2; i++) {
            for (int j = 0; j < 6; j += 2) {
                leftPropulsorActiveAnimation.addFrame(propulsorSpriteSheet.getSubImage(j, i), 50);
            }
        }
        this.leftPropulsorActiveAnimation.stop();
    }

    private void verifyPropulsorState() {
        if (moveDown || moveLeft || moveRight || moveUp) {
            if (inPropulsorTransition) {
                if (rightPropulsorTransition.isStopped()) {
                    rightPropulsorActiveAnimation.start();
                    leftPropulsorActiveAnimation.start();
                    inPropulsorTransition = false;
                }
            } else if (rightPropulsorActiveAnimation.isStopped()) {
                rightPropulsorTransition.start();
                leftPropulsorTransition.start();
                inPropulsorTransition = true;
            }
        } else {
            if (inReversePropulsorTransition) {
                if (rightPropulsorReverseTransition.isStopped()) {
                    inReversePropulsorTransition = false;
                }
            } else if (!rightPropulsorActiveAnimation.isStopped()) {
                rightPropulsorActiveAnimation.stop();
                leftPropulsorActiveAnimation.stop();
                inReversePropulsorTransition = true;
            }
        }
    }
}
