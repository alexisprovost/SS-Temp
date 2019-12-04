package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.CoreColorPicker;
import ca.qc.bdeb.info203.SSTemp.vue.MarsState;
import ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts.*;
import ca.qc.bdeb.info203.SSTemp.vue.res.Collisionable;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile, Collisionable {

    private final int MAX_SPEED = 8;
    private final int ACCELERATION = 1;

    private Body body;
    private CoreEffect coreEffect;
    private CoreLaser coreLaser;
    private Propulsor rightPropulsor;
    private Propulsor leftPropulsor;

    private MarsState controllerMars;
    private CoreColorPicker coreColorPicker;

    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean shootBullet;

    private boolean bulletPending;

    private String bulletImagePath;

    private int speedX = 0;
    private int speedY = 0;

    public Player(int x, int y, SpriteSheet bodySpriteSheet, SpriteSheet coreLaserSpriteSheet, SpriteSheet coreEffectSpriteSheet, SpriteSheet rightPropulsorSpriteSheet, SpriteSheet leftPropulsorSpriteSheet, String bulletImagePath, MarsState controllerMars, CoreColorPicker coreColorPicker) {
        super(x, y, 128, 96);
        this.body = new Body(this, bodySpriteSheet, 22, 10);
        this.coreEffect = new CoreEffect(this, coreEffectSpriteSheet, 52, 33, coreColorPicker);
        this.coreLaser = new CoreLaser(this, coreLaserSpriteSheet, 57, 40, coreColorPicker);
        this.rightPropulsor = new Propulsor(this, rightPropulsorSpriteSheet, -20, 62);
        this.leftPropulsor = new Propulsor(this, leftPropulsorSpriteSheet, -20, 0);
        this.bulletImagePath = bulletImagePath;
        this.controllerMars = controllerMars;
        this.coreColorPicker = coreColorPicker;
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
        if (controllerMars.isGamePaused()) {
            deplacementMars(limiteX, limiteY);
        } else {
            deplacementNormal(limiteX, limiteY);
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

    public Bullet shoot() {
        Bullet bullet = null;
        if (!coreLaser.isShooting() && bulletPending) {
            bulletPending = false;
            bullet = new Bullet(getX(), getY(), bulletImagePath, coreColorPicker);
            coreLaser.startTransition();
        }
        if (coreLaser.isIdle() && shootBullet) {
            bulletPending = true;
            coreLaser.startAnimation();
        }
        return bullet;
    }

    private void deplacementNormal(int limiteX, int limiteY) {
        boolean verificationX = getX() + speedX + getWidth() <= limiteX && getX() + speedX >= 0;
        boolean verificationY = getY() + speedY + getHeight() <= limiteY && getY() + speedY >= 0;
        if (verificationX) {
            setLocation(getX() + speedX, getY());
        }
        if (verificationY) {
            setLocation(getX(), getY() + speedY);
        }
    }

    private void deplacementMars(int limiteX, int limiteY) {
        setLocation(getX() + speedX, getY());
        if (controllerMars.isGoingToMars()) {
            if (getX() > limiteX + 500) {
                setLocation(-(getWidth() + 500), (limiteY - getHeight()) / 2);
                controllerMars.setGoingToMars(false);
                controllerMars.setOnMars(true);
                controllerMars.setHideAsteroids(true);
            }
        } else if (controllerMars.isOnMars()) {
            if (controllerMars.isLeavingMars()) {
                if (getX() > limiteX + 500) {
                    setLocation(-500, controllerMars.getInitialY());
                    controllerMars.setOnMars(false);
                    controllerMars.setRemoveParachute(true);
                    controllerMars.setLeaveMars(false);
                }
            } else if (getX() > (limiteX - getWidth()) / 2) {
                controllerMars.setSpawnParachute(true);
                controllerMars.setLeaveMars(true);
            }
        } else if (getX() > controllerMars.getInitialX()) {
            controllerMars.setResetAsteroid(true);
            controllerMars.setGamePaused(false);
            moveRight(false);
        }
    }

    public void collectAsteroid() {
        body.startAnimation();
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

    public void shootBullet(boolean shootBullet) {
        this.shootBullet = shootBullet;
    }
}
