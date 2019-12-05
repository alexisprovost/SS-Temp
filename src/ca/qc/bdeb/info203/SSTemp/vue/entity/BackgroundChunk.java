package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class BackgroundChunk extends Entity implements Mobile {

    private final int SCROLL_SPEED = 5;

    private ArrayList<Star> stars = new ArrayList<>();
    private Image planetImage;

    public BackgroundChunk(int x, int y, int width, int screenHeight, int density, String planetImagePath, SpriteSheet starSpriteSheet) {
        super(x, y, width, screenHeight * 2);
        initPlanet(planetImagePath);
        initStars(density, starSpriteSheet);
    }

    private void initPlanet(String imagePath) {
        try {
            this.planetImage = new Image(imagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

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
