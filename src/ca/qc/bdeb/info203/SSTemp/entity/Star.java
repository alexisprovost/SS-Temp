package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class Star extends Entity {

    private BackgroundChunk backgroundChunk;

    public Star(int width, int height, int planetHeight, BackgroundChunk backgroundChunk, SpriteSheet spriteSheet) {
        this.backgroundChunk = backgroundChunk;
        setImage(getRandomImage(spriteSheet));
        setRandomLocation(width, height, planetHeight);
    }

    private void setRandomLocation(int width, int height, int planetHeight) {
        Random r = new Random();
        int relativeX = r.nextInt(width);
        int relativeY = r.nextInt(height - planetHeight);
        setLocation(relativeX, relativeY);
    }

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
