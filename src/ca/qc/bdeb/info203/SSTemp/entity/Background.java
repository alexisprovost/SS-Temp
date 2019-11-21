package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class Background extends Entity implements Mobile{

    private int density;
    private ArrayList<PlanetChunk> planetChunks = new ArrayList<>();
    
    public Background(int x, int y, int width, int height, int density, String planetImagePath, SpriteSheet starSpriteSheet) {
        super(x, y, width, height);
        initPlanetChunks(planetImagePath);
    }

    private void initPlanetChunks(String planetImagePath){
        final int planetChunkWidth = 192;
        final int planetChunkHeight = 252;
        int nbPlanetChunk = (int)Math.ceil(((double)getWidth())/planetChunkWidth);
        for (int i = 0; i < nbPlanetChunk; i++) {
            planetChunks.add(new PlanetChunk(i * planetChunkWidth, getHeight() - planetChunkHeight, planetImagePath));
        }
    }
    
    @Override
    public void dessiner(Graphics g) {
        for(PlanetChunk planetChunk : planetChunks){
            planetChunk.dessiner(g);
        }
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
