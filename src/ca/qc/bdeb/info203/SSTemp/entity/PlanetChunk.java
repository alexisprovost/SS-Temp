/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import org.newdawn.slick.Graphics;

/**
 *
 * @author 1839083
 */
public class PlanetChunk extends Entity{

    public PlanetChunk(int x, int y, String imagePath) {
        super(x, y, imagePath);
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }
    
}
