package ca.qc.bdeb.info203.SSTemp.vue.entity;

import ca.qc.bdeb.info203.SSTemp.vue.MarsState;
import ca.qc.bdeb.info203.SSTemp.vue.res.Entity;
import ca.qc.bdeb.info203.SSTemp.vue.res.Mobile;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Background du jeu
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Background extends Entity implements Mobile {

    /**
     * Longueur d'une tuile de la planete au bas de l'ecran
     */
    private final int CHUNK_WIDTH = 192;

    /**
     * Chemin vers l'image de la planete
     */
    private String planetImagePath;

    /**
     * Spritesheet qui contient les etoiles
     */
    private SpriteSheet starSpriteSheet;

    /**
     * Image du background quand le vaisseau est sur Mars
     */
    private Image marsBackground;

    /**
     * Etat du vaisseau lors du voyage vers Mars
     */
    private MarsState marsState;

    /**
     * Densite des etoiles dans le background
     */
    private int density;

    /**
     * Liste qui contient toutes le tuiles du background
     */
    private ArrayList<BackgroundChunk> backgroundChunks = new ArrayList<>();

    /**
     * Constructeur du background
     *
     * @param width Largeur du background
     * @param height Hauteur du background
     * @param density Densite des etoiles dans le background
     * @param planetImagePath Spritesheet qui contient les etoiles
     * @param marsImagePath Image du background quand le vaisseau est sur Mars
     * @param starSpriteSheet Spritesheet qui contient les etoiles
     * @param marsState Etat du vaisseau lors du voyage vers Mars
     */
    public Background(int width, int height, int density, String planetImagePath, String marsImagePath, SpriteSheet starSpriteSheet, MarsState marsState) {
        super(0, 0, width, height);
        this.planetImagePath = planetImagePath;
        this.starSpriteSheet = starSpriteSheet;
        this.density = density;
        this.marsState = marsState;
        initBackgroundChunks();
        initMarsImage(marsImagePath);
    }

    /**
     * Initialise les sections du background necessaire pour remplir l'ecran
     */
    private void initBackgroundChunks() {
        int nbPlanetChunk = (int) Math.ceil(((double) getWidth()) / CHUNK_WIDTH) + 1;
        for (int i = 0; i < nbPlanetChunk; i++) {
            int x = i * CHUNK_WIDTH;
            backgroundChunks.add(new BackgroundChunk(x, -getHeight(), CHUNK_WIDTH, getHeight(), density, planetImagePath, starSpriteSheet));
        }
    }

    /**
     * Initialise l'image du background sur Mars
     *
     * @param marsImagePath Chemin vers l'image du background sur Mars
     */
    private void initMarsImage(String marsImagePath) {
        try {
            this.marsBackground = new Image(marsImagePath);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        if (marsState.isOnMars()) {
            g.drawImage(marsBackground, 0, 0);
        } else {
            for (BackgroundChunk planetChunk : backgroundChunks) {
                planetChunk.dessiner(g);
            }
        }
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        if (!marsState.isOnMars()) {
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
