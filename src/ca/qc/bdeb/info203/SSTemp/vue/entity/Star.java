package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Etoiles dans le background
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Star extends Entity {

    /**
     * Region du background ou se trouve l'etoile
     */
    private BackgroundChunk backgroundChunk;

    /**
     * Constructeur des etoiles
     *
     * @param width Largeur de la region du background ou se trouve l'etoile
     * @param height Hauteur de la region du background ou se trouve l'etoile
     * @param planetHeight Hauteur de la planete
     * @param backgroundChunk Region du background ou se trouve l'etoile
     * @param spriteSheet Spritesheet ou se trouve toutes les etoiles
     */
    public Star(int width, int height, int planetHeight, BackgroundChunk backgroundChunk, SpriteSheet spriteSheet) {
        this.backgroundChunk = backgroundChunk;
        setImage(getRandomImage(spriteSheet));
        setRandomLocation(width, height, planetHeight);
    }

    /**
     * Place l'etoile a un endroit aleatoire
     *
     * @param width Largeur de la region du background ou se trouve l'etoile
     * @param height Hauteur de la region du background ou se trouve l'etoile
     * @param planetHeight Hauteur de la planete
     */
    private void setRandomLocation(int width, int height, int planetHeight) {
        Random r = new Random();
        int relativeX = r.nextInt(width);
        int relativeY = r.nextInt(height - planetHeight);
        setLocation(relativeX, relativeY);
    }

    /**
     * Selectionne une image aleatoire pour l'etoile
     *
     * @param spriteSheet Spritesheet ou se trouve toutes les etoiles
     * @return Image a prendre
     */
    private Image getRandomImage(SpriteSheet spriteSheet) {
        Image image;
        Random r = new Random();
        int starType = r.nextInt(7);
        if (starType < 6) {
            image = spriteSheet.getSubImage(starType, 0);
        } else {
            image = spriteSheet.getSubImage(0, 2, 12, 10);
        }
        return image;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX() + backgroundChunk.getX(), getY() + backgroundChunk.getY());
    }

}
