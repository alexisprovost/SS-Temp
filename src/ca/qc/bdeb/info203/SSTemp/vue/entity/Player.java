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
 * Entite du joueur
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile, Collisionable {

    /**
     * Vitesse maximale du vaisseau
     */
    private final int MAX_SPEED = 8;

    /**
     * Acceleration du vaisseau
     */
    private final int ACCELERATION = 1;

    /**
     * Section principale du vaisseau
     */
    private Body body;

    /**
     * Effet autour du core du vaisseau
     */
    private CoreEffect coreEffect;

    /**
     * Core du vaisseau
     */
    private CoreLaser coreLaser;

    /**
     * Propulseur droit du vaisseau
     */
    private Propulsor rightPropulsor;

    /**
     * Propulseur gauche du vaisseau
     */
    private Propulsor leftPropulsor;

    /**
     * Etat du vaisseau lors du voyage vers Mars
     */
    private MarsState marsState;

    /**
     * Calcule la couleur du core en fonction du nombre de roche dans
     * l'inventaire
     */
    private CoreColorPicker coreColorPicker;

    /**
     * Indique si le vaisseau se deplace vers le haut
     */
    private boolean moveUp;

    /**
     * Indique si le vaisseau se deplace vers le bas
     */
    private boolean moveDown;

    /**
     * Indique si le vaisseau se deplace vers la gauche
     */
    private boolean moveLeft;

    /**
     * Indique si le vaisseau se deplace vers la droite
     */
    private boolean moveRight;

    /**
     * Indique si le vaisseau est en train de tirer
     */
    private boolean shootBullet;

    /**
     * Indique si le vaisseau a debute l'animation de tir
     */
    private boolean bulletPending;

    /**
     * Chemin vers l'image du projectile
     */
    private String bulletImagePath;

    /**
     * Vitesse du vaisseau en X
     */
    private int speedX = 0;

    /**
     * Vitesse du vaisseau en Y
     */
    private int speedY = 0;

    /**
     * Constructeur du joueur
     *
     * @param x Position du joueur en X
     * @param y Position du joueur en Y
     * @param bodySpriteSheet Spritesheet qui contient les animations du body
     * @param coreLaserSpriteSheet Spritesheet qui contient les animations du
     * core
     * @param coreEffectSpriteSheet Spritesheet qui contient les effets autour
     * du core
     * @param rightPropulsorSpriteSheet Spritesheet qui contient les animations
     * du propulseur droit
     * @param leftPropulsorSpriteSheet Spritesheet qui contient les animations
     * du propulseur gauche
     * @param bulletImagePath Chemin vers l'image du projectile
     * @param marsState Etat du vaisseau lors du voyage vers Mars
     * @param coreColorPicker Objet qui calcule la couleur du projectile en
     * fonction du nombre de roche dans l'inventaire
     */
    public Player(int x, int y, SpriteSheet bodySpriteSheet, SpriteSheet coreLaserSpriteSheet, SpriteSheet coreEffectSpriteSheet, SpriteSheet rightPropulsorSpriteSheet, SpriteSheet leftPropulsorSpriteSheet, String bulletImagePath, MarsState marsState, CoreColorPicker coreColorPicker) {
        super(x, y, 128, 96);
        this.body = new Body(this, bodySpriteSheet, 22, 10);
        this.coreEffect = new CoreEffect(this, coreEffectSpriteSheet, 52, 33, coreColorPicker);
        this.coreLaser = new CoreLaser(this, coreLaserSpriteSheet, 57, 40, coreColorPicker);
        this.rightPropulsor = new Propulsor(this, rightPropulsorSpriteSheet, -20, 62);
        this.leftPropulsor = new Propulsor(this, leftPropulsorSpriteSheet, -20, 0);
        this.bulletImagePath = bulletImagePath;
        this.marsState = marsState;
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
        if (marsState.isGamePaused()) {
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

    /**
     * Determine la vitesse en X et en Y du vaisseau
     */
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

    /**
     * Tire un nouveau projectile
     *
     * @return
     */
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

    /**
     * Deplace le vaisseau s'il n'est pas sur Mars
     *
     * @param limiteX Limite de l'ecran en X
     * @param limiteY Limite de l'ecran en Y
     */
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

    /**
     * Deplace le vaisseau s'il est sur Mars
     *
     * @param limiteX Limite de l'ecran en X
     * @param limiteY Limite de l'ecran en Y
     */
    private void deplacementMars(int limiteX, int limiteY) {
        setLocation(getX() + speedX, getY());
        if (marsState.isGoingToMars()) {
            if (getX() > limiteX + 500) {
                setLocation(-(getWidth() + 500), (limiteY - getHeight()) / 2);
                marsState.setGoingToMars(false);
                marsState.setOnMars(true);
                marsState.setHideAsteroids(true);
            }
        } else if (marsState.isOnMars()) {
            if (marsState.isLeavingMars()) {
                if (getX() > limiteX + 500) {
                    setLocation(-500, marsState.getInitialY());
                    marsState.setOnMars(false);
                    marsState.setRemoveParachute(true);
                    marsState.setLeaveMars(false);
                }
            } else if (getX() > (limiteX - getWidth()) / 2) {
                marsState.setSpawnParachute(true);
                marsState.setLeaveMars(true);
            }
        } else if (getX() > marsState.getInitialX()) {
            marsState.setResetAsteroid(true);
            marsState.setGamePaused(false);
            moveRight(false);
        }
    }

    /**
     * Commence l'animation des degats
     */
    public void takeDamage() {
        body.takeDamage();
        rightPropulsor.takeDamage();
        leftPropulsor.takeDamage();
    }

    /**
     * Commence l'animation recolte d'asteroides
     */
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
