package ca.qc.bdeb.info203.SSTemp.vue;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import org.newdawn.slick.Color;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class CoreColorPicker {

    private int redTransition;
    private int greenTransition;
    private int blueTransition;

    private Color minColor;
    private Modele modele;

    public CoreColorPicker(Color maxColor, Color minColor, Modele modele) {
        this.redTransition = maxColor.getRed() - minColor.getRed();
        this.greenTransition = maxColor.getGreen() - minColor.getGreen();
        this.blueTransition = maxColor.getBlue() - minColor.getBlue();
        this.minColor = minColor;
        this.modele = modele;
    }

    public Color getTransitionColor() {
        double percentage = modele.getFilledPercentage();
        int red = (int) (minColor.getRed() + redTransition * percentage);
        int green = (int) (minColor.getGreen() + greenTransition * percentage);
        int blue = (int) (minColor.getBlue() + blueTransition * percentage);
        return new Color(red, green, blue);
    }
}
