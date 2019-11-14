package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.entity.Asteroid;
import ca.qc.bdeb.info203.SSTemp.entity.Bullet;
import ca.qc.bdeb.info203.SSTemp.entity.Player;
import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import com.sun.jdi.connect.spi.TransportService;
import static java.lang.System.gc;
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
     * Largeur de l'écran.
     */
    private int largeurEcran;
    /**
     * Heuteur de l'écran.
     */
    private int hauteurEcran;
    
    private SpriteSheet playerSpriteSheet;

    private SpriteSheet asteroidSpriteSheet;
    
    private SpriteSheet bulletSpriteSheet;
    
    private Player player;

    

    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        
        try {
            playerSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerSpriteSheet.png", 96, 128);
            asteroidSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/AsteroidSpriteSheetT.png", 16, 16);
            bulletSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/BulletSpriteSheet.png", 16, 16);
        } catch (SlickException e) {
            System.out.println("SlickException :" + e);
        }

        player = new Player(0, 0, playerSpriteSheet, 0, 0);
        entites.add(player);
        mobiles.add(player);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        player.bouger(0, 0);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for (Entity entite : entites) {
            entite.dessiner(g);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_W:
                player.moveUp(true);
                break;
            case Input.KEY_S:
                player.moveDown(true);
                break;
            case Input.KEY_A:
                player.moveLeft(true);
                break;
            case Input.KEY_D:
                player.moveRight(true);
                break;
        }
    }
    
    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_ESCAPE:
                container.exit();
                break;
            case Input.KEY_W:
                player.moveUp(false);
                break;
            case Input.KEY_S:
                player.moveDown(false);
                break;
            case Input.KEY_A:
                player.moveLeft(false);
                break;
            case Input.KEY_D:
                player.moveRight(false);
                break;
        }
    }
}
