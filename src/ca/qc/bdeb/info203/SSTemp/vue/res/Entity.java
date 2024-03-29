package ca.qc.bdeb.info203.SSTemp.vue.res;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Entité du jeu.
 *
 * @author Manuel Ramirez, Alexis Provost
 * @author Mathieu Grenon, Stéphane Lévesque,
 */
public abstract class Entity {

    /**
     * Coordonnée X
     */
    private int x;
    /**
     * Coordonnée Y
     */
    private int y;
    /**
     * Largeur de l'entité
     */
    private int width;
    /**
     * Hauteur de l'entité
     */
    private int height;
    /**
     * Image à utiliser pour représenter l'entité
     */
    private Image image;
    /**
     * Ne pas détruire si false
     */
    private boolean detruire = false;

    /**
     * Constructeur d'Entite sans parametres
     *
     */
    public Entity() {
    }

    /**
     * Constructeur d'Entite avec image sur le disque
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param image image de l'entite
     */
    public Entity(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        try {
            this.image = new Image(imagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Constructeur d'Entite avec SpriteSheet
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param spriteSheet SpriteSheet qui contient l'image
     * @param ligne la ligne dans le SpriteSheet de l'image
     * @param colonne la colonne dans le SpriteSheet de l'image
     */
    public Entity(int x, int y, SpriteSheet spriteSheet, int colonne, int ligne) {
        this.x = x;
        this.y = y;
        this.image = spriteSheet.getSubImage(colonne, ligne);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Constructeur d'Entite avec SpriteSheet si les images ne sont pas toutes
     * la même taille
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param spriteSheet SpriteSheet qui contient l'image
     * @param ligne la ligne dans le SpriteSheet de l'image
     * @param colonne la colonne dans le SpriteSheet de l'image
     * @param width la longueur de l'image dans le SpriteSheet
     * @param height la heuteur de l'image dans le SpriteSheet
     */
    public Entity(int x, int y, SpriteSheet spriteSheet, int colonne, int ligne, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = spriteSheet.getSubImage(colonne, ligne, width, height);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Constructeur d'Entite sans fichier d'image
     *
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param width largeur de l'image
     * @param height hauteur de l'image
     */
    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(SpriteSheet spriteSheet, int colonne, int ligne) {
        this.image = spriteSheet.getSubImage(colonne, ligne);
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getDetruire() {
        return detruire;
    }

    public void setDetruire(boolean detruire) {
        this.detruire = detruire;
    }

    /**
     * Dessine l'entité dans l'interface.
     *
     * @param g L'objet Graphics à utiliser pour dessiner dans la fenêtre de
     * l'application.
     */
    public abstract void dessiner(Graphics g);
}
