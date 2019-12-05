package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.MarsState;
import ca.qc.bdeb.info203.SSTemp.vue.res.Collisionable;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 * Classe Astéroide
 * @author Manuel Ramirez, Alexis Provost
 */
public class Asteroid extends Entity implements Mobile, Collisionable {
    /**
     * Controlleur Mars
     */
    private MarsState controllerMars;

    /**
     * L'astéroide est à séparer
     */
    private boolean split = false;

    /**
     * Vitesse en x
     */
    private int xSpeed;
    /**
     * Vitesse en y
     */
    private int ySpeed;
    /**
     * Vitesse  Maximale
     */
    private int maxSpeed = 10;
    /**
     * Espace en dehors de l'écran pour le spawn des Astéroides
     */
    private int extraSpace = 500;
    /**
     * Hauteur de l'écran
     */
    private int hauteurEcran = 0;
    /**
     * Largeur de l'écran
     */
    private int largeurEcran = 0;

    /**
     * Constructeur d'astéroide
     * @param x Postion initilale en x
     * @param y Postion initilale en y
     * @param spriteSheet Sprite d'astéroide
     * @param ligne Ligne pour texture
     * @param colonne Colonne pour texture
     * @param width Largeur de l'astéroide
     * @param height Hauteur de l'astéroide
     * @param controllerMars Controlleur des voyages sur Mars
     * @param hauteurEcran Hauteur de l'écran
     * @param largeurEcran Largeur de l'écran
     */
    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height, MarsState controllerMars, int hauteurEcran, int largeurEcran) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
        this.controllerMars = controllerMars;
        this.hauteurEcran = hauteurEcran;
        this.largeurEcran = largeurEcran;
        Random rnd = new Random();
        chooseRandomSpeed(rnd);
        chooseRandomSprite(spriteSheet, rnd);
    }

    /**
     * Méthode qui choisi au hasard une nouvelle vitesse
     * @param rnd Objet Random
     */
    private void chooseRandomSpeed(Random rnd) {
        xSpeed = -rnd.nextInt(maxSpeed);

        switch (rnd.nextInt(3)) {
            case 1:
                ySpeed = -rnd.nextInt(maxSpeed);
                break;
            case 2:
                ySpeed = rnd.nextInt(maxSpeed);
                break;
            case 3:
                ySpeed = 0;
        }
    }

    /**
     * Choix au hasard des textures avec le Sprite
     * @param spriteSheet Sprite sheet d'astéroid
     * @param rnd Objet Random
     */
    private void chooseRandomSprite(SpriteSheet spriteSheet, Random rnd) {
        int randomSprite = rnd.nextInt(4);
        switch (getWidth()) {
            case 256:
                setImage(spriteSheet.getSubImage(256 * randomSprite, 0, getWidth(), getHeight()));
                break;
            case 128:
                setImage(spriteSheet.getSubImage(128 * randomSprite, 256, getWidth(), getHeight()));
                break;
            case 64:
                setImage(spriteSheet.getSubImage(64 * randomSprite + 512, 256, getWidth(), getHeight()));
                break;
            case 32:
                setImage(spriteSheet.getSubImage(32 * randomSprite + 512, 320, getWidth(), getHeight()));
                break;
            case 16:
                setImage(spriteSheet.getSubImage(16 * randomSprite + 512, 352, getWidth(), getHeight()));
                break;
            default:
                System.out.println("Invalid Asteroid Size");
                break;
        }
    }

    /**
     * Affiche l'astéroid
     * @param g Graphics Slick 2D
     */
    @Override
    public void dessiner(Graphics g) {
        if (!controllerMars.isHideAsteroids()) {
            g.drawImage(getImage(), getX(), getY());
        }
    }

    /**
     * Retourne si il doit être Split
     * @return Boolean à split
     */
    public boolean getSplit() {
        return split;
    }

    /**
     * Setter qui change la vitesse de l'astéroid en x
     * @param xSpeed Int nouvelle vitesse en x
     */
    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Setter qui change la vitesse de l'astéroid en y
     * @param ySpeed Int nouvelle vitesse en y
     */
    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Getter de la vitesse en x
     * @return Int de la vitesse en x
     */
    public int getxSpeed() {
        return xSpeed;
    }

    /**
     * Getter de la vitesse en y
     * @return Int de la vitesse en y
     */
    public int getySpeed() {
        return ySpeed;
    }

    /**
     * Setter si split ou non
     * @param split Boolean si à split out non
     */
    public void setSplit(boolean split) {
        this.split = split;
        setDetruire(true);
    }

    /**
     * Bouger l'astéroid
     * @param limiteX Limite de l'écran en X
     * @param limiteY Limite de l'écran en Y
     */
    @Override
    public void bouger(int limiteX, int limiteY) {
        Random rnd = new Random();

        boolean backWallHit = getX() + getWidth() >= limiteX + extraSpace;
        boolean bottomWallHit = getY() + getHeight() >= limiteY + extraSpace;
        boolean frontWallHit = getX() <= -extraSpace;
        boolean topWallHit = getY() <= -extraSpace;

        if (backWallHit || bottomWallHit || frontWallHit || topWallHit) {
            setLocation(largeurEcran + 150, rnd.nextInt(hauteurEcran));
            chooseRandomSpeed(rnd);
        }
        getImage().setRotation(getImage().getRotation() + 1);
        setLocation(getX() + xSpeed, getY() + ySpeed);
    }
}
