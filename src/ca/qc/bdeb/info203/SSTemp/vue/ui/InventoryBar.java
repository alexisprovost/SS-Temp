package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.CoreColorPicker;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class InventoryBar extends Entity {
    /**
     * Modèle du jeu
     */
    private Modele modele;
    /**
     * Choix de couleur
     */
    private CoreColorPicker coreColorPicker;
    /**
     * Image d'icone de roche
     */
    private Image rockImage;

    /**
     * Constructeur Barre d'inventaire
     * 
     * @param x Position en x
     * @param y Position en y
     * @param imagePath Chemin du fichier d'image de la barre
     * @param rockImagePath Chemin de l'icone de la roche
     * @param modele Modèle du jeu
     * @param coreColorPicker Choix de couleur barre
     */
    public InventoryBar(int x, int y, String imagePath, String rockImagePath, Modele modele, CoreColorPicker coreColorPicker) {
        super(x, y, imagePath);
        this.modele = modele;
        this.coreColorPicker = coreColorPicker;
        try {
            this.rockImage = new Image(rockImagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(rockImage, getX(), getY() + (getHeight() - rockImage.getHeight()) / 2);
        g.setColor(coreColorPicker.getTransitionColor());
        g.fillRect(getX() + rockImage.getWidth() + 9, getY(), modele.getRockInventory() / ((float) modele.getMaxInventory() / 384), getHeight());
        g.drawImage(getImage(), getX() + rockImage.getWidth() + 5, getY());
        g.setColor(Color.white);
        String inventory = modele.getRockInventory() + "/" + modele.getMaxInventory();
        int stringWidth = g.getFont().getWidth(inventory);
        int stringHeight = g.getFont().getHeight(inventory);
        g.drawString(inventory, getX() + rockImage.getWidth() + 5 + (getWidth() - stringWidth) / 2, getY() + (getHeight() - stringHeight) / 2 - 1);
    }
}
