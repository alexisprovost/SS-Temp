package ca.qc.bdeb.info203.SSTemp.vue;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import org.newdawn.slick.Color;

/**
 * Classe qui calcule la couleur du core en fonction du nombre de roche dans
 * l'inventaire
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class CoreColorPicker {

    /**
     * Trasition a faire dans le rouge pour passer de la couleur initiale a la
     * couleur finale
     */
    private int redTransition;

    /**
     * Trasition a faire dans le vert pour passer de la couleur initiale a la
     * couleur finale
     */
    private int greenTransition;

    /**
     * Trasition a faire dans le bleu pour passer de la couleur initiale a la
     * couleur finale
     */
    private int blueTransition;

    /**
     * Couleur du core quand l'inventaire est vide
     */
    private Color minColor;

    /**
     * Modele de la partie
     */
    private Modele modele;

    /**
     * Constructeur du calculateur de couleur
     *
     * @param maxColor Couleur du core quand l'inventaire est plein
     * @param minColor Couleur du core quand l'inventaire est vide
     * @param modele Modele de la partie
     */
    public CoreColorPicker(Color maxColor, Color minColor, Modele modele) {
        this.redTransition = maxColor.getRed() - minColor.getRed();
        this.greenTransition = maxColor.getGreen() - minColor.getGreen();
        this.blueTransition = maxColor.getBlue() - minColor.getBlue();
        this.minColor = minColor;
        this.modele = modele;
    }

    /**
     * Calcule la couleur que doit etre le core en fonction du nombre de roches
     * dans l'inventaire
     *
     * @return Couleur que doit etre le core
     */
    public Color getTransitionColor() {
        double percentage = modele.getFilledPercentage();
        int red = (int) (minColor.getRed() + redTransition * percentage);
        int green = (int) (minColor.getGreen() + greenTransition * percentage);
        int blue = (int) (minColor.getBlue() + blueTransition * percentage);
        return new Color(red, green, blue);
    }
}
