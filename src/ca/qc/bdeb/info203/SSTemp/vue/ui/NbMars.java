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

    public NbMars(Modele modele) {
        this.modele = modele;
    }
    
    @Override
    public void dessiner(Graphics g) {
        g.drawString("Nb Envoyé Sur Mars: " + modele.getNbEnvoieSurMars(), 148, 100);
    }
    
}
