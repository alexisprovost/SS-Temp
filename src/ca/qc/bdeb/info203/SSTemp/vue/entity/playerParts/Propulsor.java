package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Propulseur du vaisseau
 * 
 * @author Manuel Ramirez, Alexis Provost
 */
public class Propulsor extends PlayerPart {

    /**
     * Angle de rotation du propulseur
     */
    private int rotationAngle;

    /**
     * Image lorsque le vaisseau ne bouge pas
     */
    private Image idleImage;
    
    /**
     * Transition vers l'animation
     */
    private Animation transition;
    
    /**
     * Animation lorsque le vaisseau est en mouvement
     */
    private Animation activeAnimation;
    
    /**
     * Transition vers l'etat idle
     */
    private Animation reverseTransition;

    /**
     * Couleur du vaisseau
     */
    private Color renderColor;

    /**
     * Nombre de frames ou l'image reste d'une couleur
     */
    private final int FLICKER_LENGTH = 3;

    /**
     * Nombre de fois que la couleur alterne lorsque le vaisseau prend des
     * degats
     */
    private final int MAX_FLICKER_COUNT = 6;

    /**
     * Nombre de frame depuis le dernier changement de couleur
     */
    private int frameCounter;

    /**
     * Nombre de changement de couleur depuis les derniers degats
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

    /**
     * Constructeur du propulseur
     * 
     * @param player Joueur sur lequel cette partie est attache
     * @param spriteSheet Spritesheet qui contient les images de cette partie du vaisseau
     * @param xOffset Offset de cette composante en X par rapport au Player
     * @param yOffset Offset de cette composante en Y par rapport au Player
     */
    public Propulsor(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initTransition();
        initActiveAnimation();
        initReverseTransition();
    }

    /**
     * Initialise l'image du propulseur lorsque le vaisseau est immobile
     */
    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

    /**
     * Initialise la transition vers l'animation
     */
    private void initTransition() {
        this.transition = new Animation();
        this.transition.addFrame(getSpriteSheet().getSubImage(1, 0), 100);
        this.transition.addFrame(getSpriteSheet().getSubImage(2, 0), 100);
        this.transition.setLooping(false);
        this.transition.stop();
    }

    /**
     * Initialise l'animation lorsque le vaisseau est en mouvement
     */
    private void initActiveAnimation() {
        this.activeAnimation = new Animation();
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                activeAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 50);
            }
        }
        this.activeAnimation.stop();
    }

    /**
     * Intialise la transition vers l'etat idle
     */
    private void initReverseTransition() {
        this.reverseTransition = new Animation();
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(2, 0), 50);
        this.reverseTransition.addFrame(getSpriteSheet().getSubImage(1, 0), 50);
        this.reverseTransition.setLooping(false);
        this.reverseTransition.stop();
    }

    /**
     * Verifie quel animation jouer
     * 
     * @param moving Si le vaisseau est en mouvement ou non
     */
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

    /**
     * Trouve la couleur que cette partie du vaisseau devrait etre
     */
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
            renderColor = Color.red;
        } else {
            renderColor = null;
        }
    }

    /**
     * Determine la rotaton du propulseur
     * 
     * @param x Vitesse en X du vaisseau
     * @param y Vitesse en Y du vaisseau
     */
    public void setPropulsorRotation(double x, double y) {
        this.rotationAngle = (int) Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Indique a l'objet que le vaisseau a pris des degats
     */
    public void takeDamage() {
        takeDamage = true;
    }

    @Override
    public void dessiner(Graphics g) {
        verifyRenderColor();
        g.rotate(getX() + 38, getY() + 17, rotationAngle);

        if (!activeAnimation.isStopped()) {
            g.drawAnimation(activeAnimation, getX(), getY(), renderColor);
        } else if (!transition.isStopped()) {
            g.drawAnimation(transition, getX(), getY(), renderColor);
        } else if (!reverseTransition.isStopped()) {
            g.drawAnimation(reverseTransition, getX(), getY(), renderColor);
        } else {
            g.drawImage(idleImage, getX(), getY(), renderColor);
        }

        g.resetTransform();
    }
}
