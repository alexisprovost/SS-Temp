package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Manuel Ramirez
 */
public class HealthBar extends Entity {

    Modele modele;
    Image heartImage;

    public HealthBar(int x, int y, String imagePath, String hearthImagePath, Modele modele) {
        super(x, y, imagePath);
        this.modele = modele;
        try {
            this.heartImage = new Image(hearthImagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(heartImage, getX(), getY() + (getHeight() - heartImage.getHeight()) / 2);
        g.setColor(Color.red);
        g.fillRect(getX() + heartImage.getWidth() + 9, getY(), modele.getHealth(), getHeight());
        g.drawImage(getImage(), getX() + heartImage.getWidth() + 5, getY());
        g.setColor(Color.white);
        String health = modele.getHealth() + "/" + modele.getMaxHealth();
        int stringWidth = g.getFont().getWidth(health);
        int stringHeight = g.getFont().getHeight(health);
        g.drawString(health, getX() + heartImage.getWidth() + 5 + (getWidth() - stringWidth) / 2, getY() + (getHeight() - stringHeight) / 2);
    }

}
