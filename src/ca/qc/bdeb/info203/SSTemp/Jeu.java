package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.entity.Asteroid;
import ca.qc.bdeb.info203.SSTemp.entity.Background;
import ca.qc.bdeb.info203.SSTemp.entity.Bullet;
import ca.qc.bdeb.info203.SSTemp.entity.Mars;
import ca.qc.bdeb.info203.SSTemp.entity.Player;
import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.ArrayList;
import java.util.Random;
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

    private ArrayList<Entity> aDetruire = new ArrayList<>();
    /**
     * Largeur de l'écran.
     */
    private int largeurEcran;
    /**
     * Heuteur de l'écran.
     */
    private int hauteurEcran;

    private SpriteSheet playerBodySpriteSheet;

    private SpriteSheet playerCoreLaserSpriteSheet;

    private SpriteSheet playerCoreEffectSpriteSheet;

    private SpriteSheet playerRightPropulsorSpriteSheet;

    private SpriteSheet playerLeftPropulsorSpriteSheet;

    private SpriteSheet asteroidSpriteSheet;

    private SpriteSheet starSpriteSheet;

    private String bulletImagePath;

    private String planetChunkImagePath;

    private Player player;

    private Mars mars;

    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        loadSprites();

        Background b = new Background(largeurEcran, hauteurEcran, 100, planetChunkImagePath, starSpriteSheet);
        entites.add(b);
        mobiles.add(b);

        player = new Player(0, 0, playerBodySpriteSheet, playerCoreLaserSpriteSheet, playerCoreEffectSpriteSheet, playerRightPropulsorSpriteSheet, playerLeftPropulsorSpriteSheet, bulletImagePath);
        entites.add(player);
        mobiles.add(player);

        for (int i = 0; i < 20; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 512, 256, 64, 64);
            entites.add(asteroid);
            mobiles.add(asteroid);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        for (Mobile mobile : mobiles) {
            mobile.bouger(largeurEcran, hauteurEcran);
        }
        Bullet newBullet = player.shoot();
        if (newBullet != null) {
            entites.add(newBullet);
            mobiles.add(newBullet);
        }

        for (Entity entite : entites) {
            if (entite instanceof Bullet) {
                for (Entity entite2 : entites) {
                    if (entite2 instanceof Asteroid) {
                        if (entite.getRectangle().intersects(entite2.getRectangle())) {
                            entite2.setDetruire(true);
                            entite.setDetruire(true);
                        }
                    }
                }
            }
        }

        detruireEntites();
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
            case Input.KEY_SPACE:
                player.shootBullet(true);
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
            case Input.KEY_SPACE:
                player.shootBullet(false);
                break;
            case Input.KEY_E:

        }
    }

    private void loadSprites() {
        try {
            playerBodySpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerBodySpriteSheet.png", 123, 76);
            playerCoreLaserSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerCoreLaserSpriteSheet2.png", 71, 16);
            playerCoreEffectSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerCoreEffectSpriteSheet2.png", 19, 30);
            playerRightPropulsorSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerRightPropulsorSpriteSheet.png", 56, 34);
            playerLeftPropulsorSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerLeftPropulsorSpriteSheet.png", 56, 34);
            asteroidSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/AsteroidSpriteSheet.png", 16, 16);
            starSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/StarSpriteSheet.png", 2, 2);
            bulletImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Bullet2.png";
            planetChunkImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/BackgroundChunk.png";
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    private void detruireEntites() {
        for (Entity entite : entites) {
            if (entite.getDetruire()) {
                aDetruire.add(entite);
            }
        }
        entites.removeAll(aDetruire);
        mobiles.removeAll(aDetruire);
        for (Entity entite : aDetruire) {
            if (entite instanceof Asteroid) {
                Random rnd = new Random();
                Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 512, 256, 64, 64);
                entites.add(asteroid);
                mobiles.add(asteroid);
            }
        }
        aDetruire.clear();
    }
}
