package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.entity.Asteroid;
import ca.qc.bdeb.info203.SSTemp.entity.Bullet;
import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.ArrayList;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Mathieu Grenon, Stéphane Lévesque
 */
public class Jeu extends BasicGame {

    /**
     * Container du jeu.
     */
    private GameContainer container;
    /**
     * Tout ce qui bouge.
     */
    private ArrayList<Mobile> mobiles = new ArrayList<>();
    /**
     * Toutes les entités.
     */
    private ArrayList<Entity> entites = new ArrayList<>();
    /**
     * Les touches enfoncées.
     */
    private ArrayList<Integer> touches = new ArrayList<>(); // 
    /**
     * L’entrée (souris/touches de clavier, etc.)
     */
    private Input input;
    /**
     * Largeur de l'écran.
     */
    private int largeurEcran;
    /**
     * Heuteur de l'écran.
     */
    private int hauteurEcran;

    private Asteroid asteroid;

    private SpriteSheet asteroidSpriteSheet;

    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;

        //Load sprites
        try {
            //16x16?
            asteroidSpriteSheet = new SpriteSheet("sprites/AsteroidSpriteSheetT.png", 16, 16);
        } catch (SlickException e) {
            System.out.println("SlickException :" + e);
        } catch (RuntimeException e) {
            System.out.println("Error Sprite File Not Found!");
        }

        //Spawn Entity Asteroid (For Testing)
        asteroid = new Asteroid(0, 0, asteroidSpriteSheet, 0, 0, 256, 256);
        entites.add(asteroid);
        mobiles.add(asteroid);
    }

    public void update(GameContainer container, int delta) throws SlickException {

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for (Entity entite : entites) {
            entite.dessiner(g);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_ESCAPE:
                container.exit();
                break;
        }
    }
}
