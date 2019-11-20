package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.entity.playerParts.Body;
import ca.qc.bdeb.info203.SSTemp.entity.playerParts.CoreEffect;
import ca.qc.bdeb.info203.SSTemp.entity.playerParts.CoreLaser;
import ca.qc.bdeb.info203.SSTemp.entity.playerParts.Propulsor;
import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile {

    private final int MAX_SPEED = 8;
    private final int ACCELERATION = 1;

    private Body body;
    private CoreEffect coreEffect;
    private CoreLaser coreLaser;
    private Propulsor rightPropulsor;
    private Propulsor leftPropulsor;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    private int speedX = 0;
    private int speedY = 0;

    public Player(int x, int y, SpriteSheet bodySpriteSheet, SpriteSheet coreLaserSpriteSheet, SpriteSheet coreEffectSpriteSheet, SpriteSheet rightPropulsorSpriteSheet, SpriteSheet leftPropulsorSpriteSheet) {
        super(x, y, 128, 96);
        this.body = new Body(this, bodySpriteSheet, 22, 10);
        this.coreEffect = new CoreEffect(this, coreEffectSpriteSheet, 52, 33);
        this.coreLaser = new CoreLaser(this, coreLaserSpriteSheet, 57, 40);
        this.rightPropulsor = new Propulsor(this, rightPropulsorSpriteSheet, -20, 62);
        this.leftPropulsor = new Propulsor(this, leftPropulsorSpriteSheet, -20, 0);
    }

    @Override
    public void dessiner(Graphics g) {
        body.dessiner(g);
        coreEffect.dessiner(g);
        coreLaser.dessiner(g);
        rightPropulsor.dessiner(g);
        leftPropulsor.dessiner(g);
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setSpeed();
        boolean verificationX = getX() + speedX + getWidth() <= limiteX && getX() + speedX >= 0;
        boolean verificationY = getY() + speedY + getHeight() <= limiteY && getY() + speedY >= 0;
        if (verificationX) {
            setLocation(getX() + speedX, getY());
        }
        if (verificationY) {
            setLocation(getX(), getY() + speedY);
        }
        boolean moving = moveUp || moveDown || moveLeft || moveRight;
        rightPropulsor.verifyPropulsorState(moving);
        rightPropulsor.setPropulsorRotation(speedX, speedY);
        leftPropulsor.verifyPropulsorState(moving);
        leftPropulsor.setPropulsorRotation(speedX, speedY);
    }

    private void setSpeed() {
        if (moveUp && speedY > -MAX_SPEED) {
            speedY -= ACCELERATION;
        } else if (!moveUp && speedY < 0) {
            speedY += ACCELERATION;
        }
        if (moveDown && speedY < MAX_SPEED) {
            speedY += ACCELERATION;
        } else if (!moveDown && speedY > 0) {
            speedY -= ACCELERATION;
        }
        if (moveLeft && speedX > -MAX_SPEED) {
            speedX -= ACCELERATION;
        } else if (!moveLeft && speedX < 0) {
            speedX += ACCELERATION;
        }
        if (moveRight && speedX < MAX_SPEED) {
            speedX += ACCELERATION;
        } else if (!moveRight && speedX > 0) {
            speedX -= ACCELERATION;
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
}
