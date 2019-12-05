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
 * Classe Information de jeu Score et Timer
 * @author Manuel Ramirez, Alexis Provost
 */
public class GameInfos extends Entity{
    /**
     * Modèle de jeu
     */
    private Modele modele;
    /**
     * Largeur d'écran
     */
    private int screenWidth;

    /**
     * Constructeur des informations de jeu
     * @param modele Modèle de jeu
     * @param screenWidth Laurgeur d'écran
     */
    public GameInfos(Modele modele, int screenWidth) {
        this.modele = modele;
        this.screenWidth = screenWidth;
    }
    
    @Override
    public void dessiner(Graphics g) {
        String minerals = "Minerais Envoyé Sur Mars: " + modele.getRockOnMars();
        String time = "Durée de la partie: " + modele.getElapsedTime();
        int mineralsStringWidth = g.getFont().getWidth(minerals);
        int timeStringWidth = g.getFont().getWidth(time);
        g.drawString(minerals, screenWidth - mineralsStringWidth - 10, 10);
        g.drawString(time, screenWidth - timeStringWidth - 10, 40);
    }
    
}
