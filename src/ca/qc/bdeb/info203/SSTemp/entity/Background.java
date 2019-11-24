package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class Background extends Entity implements Mobile {

    private final int CHUNK_WIDTH = 192;

    private String planetImagePath;
    private SpriteSheet starSpriteSheet;
    private Image marsBakcground;

    private ControllerMars controllerMars;

    private int density;
    private ArrayList<BackgroundChunk> backgroundChunks = new ArrayList<>();

    public Background(int width, int height, int density, String planetImagePath, String marsImagePath, SpriteSheet starSpriteSheet, ControllerMars controllerMars) {
        super(0, 0, width, height);
        this.planetImagePath = planetImagePath;
        this.starSpriteSheet = starSpriteSheet;
        this.density = density;
        this.controllerMars = controllerMars;
        initBackgroundChunks();
        initMarsImage(marsImagePath);
    }

    private void initBackgroundChunks() {
        int nbPlanetChunk = (int) Math.ceil(((double) getWidth()) / CHUNK_WIDTH) + 1;
        for (int i = 0; i < nbPlanetChunk; i++) {
            int x = i * CHUNK_WIDTH;
            backgroundChunks.add(new BackgroundChunk(x, -getHeight(), CHUNK_WIDTH, getHeight(), density, planetImagePath, starSpriteSheet));
        }
    }

    private void initMarsImage(String marsImagePath) {
        try {
            this.marsBakcground = new Image(marsImagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        if (controllerMars.isOnMars()) {
            g.drawImage(marsBakcground, 0, 0);
        } else {
            for (BackgroundChunk planetChunk : backgroundChunks) {
                planetChunk.dessiner(g);
            }
        }
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        if (!controllerMars.isOnMars()) {
            for (BackgroundChunk backgroundChunk : backgroundChunks) {
                backgroundChunk.bouger(limiteX, limiteY);
            }
            BackgroundChunk firstBackgroundChunk = backgroundChunks.get(0);
            if (firstBackgroundChunk.getX() + firstBackgroundChunk.getWidth() < 0) {
                firstBackgroundChunk.setDetruire(true);
                backgroundChunks.remove(firstBackgroundChunk);
                int x = backgroundChunks.get(backgroundChunks.size() - 1).getX() + CHUNK_WIDTH;
                backgroundChunks.add(new BackgroundChunk(x, firstBackgroundChunk.getY(), CHUNK_WIDTH, getHeight(), density, planetImagePath, starSpriteSheet));
            }
        }
    }
}
