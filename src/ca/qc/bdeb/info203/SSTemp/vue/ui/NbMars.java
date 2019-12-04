/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.SSTemp.vue.ui;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Alexis
 */
public class NbMars extends Entity{
    
    private Modele modele;
    private int screenWidth;

    public NbMars(Modele modele, int screenWidth) {
        this.modele = modele;
        this.screenWidth = screenWidth;
    }
    
    @Override
    public void dessiner(Graphics g) {
        String text = "Minerais Envoyé Sur Mars: " + modele.getRockOnMars();
        int stringWidth = g.getFont().getWidth(text);
        g.drawString(text, screenWidth - stringWidth - 10, 10);
    }
    
}
