package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Manuel Ramirez
 */
public class DeathScreen extends Entity {

    private Modele modele;
    private Image deathBg;
    private int largeurEcran;
    private int longeurEcran;

    public DeathScreen(Modele modele, String imagePath, int largeurEcran, int longeurEcran) {
        this.largeurEcran = largeurEcran;
        this.longeurEcran = longeurEcran;
        this.modele = modele;
        
        try {
            this.deathBg = new Image(imagePath);

        } catch (SlickException e) {

        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(deathBg, 0, 0);

        String go = "GAME OVER";
        int sizeGo = g.getFont().getWidth(go);
        
        g.drawString(go, largeurEcran/2 - sizeGo/2, longeurEcran/2 - 45);
        
        String duree = "Durée de la partie: " + modele.getElapsedTime();
        int sizeDuree = g.getFont().getWidth(duree);
        
        g.drawString(duree, largeurEcran/2 - sizeDuree/2, longeurEcran/2 -15);
        
        String rockMars = "Minerais Envoyé Sur Mars: " + modele.getRockOnMars();
        int sizeRockMars = g.getFont().getWidth(rockMars);
        
        g.drawString(rockMars, largeurEcran/2 - sizeRockMars/2, longeurEcran/2+15);
        
        String restartKey = "Appuyez sur R pour rejouer ou sur ESC pour quitter";
        int sizeRestart = g.getFont().getWidth(restartKey);
        
        g.drawString(restartKey, largeurEcran/2 - sizeRestart/2, longeurEcran/2 + 45);
    }
}
