package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Section du background
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class BackgroundChunk extends Entity implements Mobile {

    /**
     * Vitesse de defilement du background
     */
    private final int SCROLL_SPEED = 5;

    /**
     * Liste qui contient toutes les etoiles dans cette section du background
     */
    private ArrayList<Star> stars = new ArrayList<>();

    /**
     * Image d'une section de la planete
     */
    private Image planetImage;

    /**
     * Constructeur d'une section de la planete
     *
     * @param x Position de cette section en X
     * @param y Position de cette section en Y
     * @param width Longueur de cette section
     * @param screenHeight Hauteur de l'ecran
     * @param density Densite des etoiles dans cette section
     * @param planetImagePath Chemin vers l'image qui contient une section de la
     * planete
     * @param starSpriteSheet Spritesheet qui contient toutes les etoiles
     */
    public BackgroundChunk(int x, int y, int width, int screenHeight, int density, String planetImagePath, SpriteSheet starSpriteSheet) {
        super(x, y, width, screenHeight * 2);
        initPlanet(planetImagePath);
        initStars(density, starSpriteSheet);
    }

    /**
     * Initialise la planete au bas de l'ecran
     *
     * @param imagePath Chemin vers l'image qui contient une section de la
     * planete
     */
    private void initPlanet(String imagePath) {
        try {
            this.planetImage = new Image(imagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    /**
     * Initialise les etoiles dans le background
     *
     * @param density Densite des etoiles dans cette section
     * @param starSpriteSheet Spritesheet qui contient toutes les etoiles
     */
    private void initStars(int density, SpriteSheet starSpriteSheet) {
        for (int i = 0; i < density; i++) {
            stars.add(new Star(getWidth(), getHeight(), planetImage.getHeight(), this, starSpriteSheet));
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(planetImage, getX(), getY() + getHeight() - planetImage.getHeight());
        for (Star star : stars) {
            star.dessiner(g);
        }
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setLocation(getX() - SCROLL_SPEED, getY());
    }

}
