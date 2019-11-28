package ca.qc.bdeb.info203.SSTemp;

import ca.qc.bdeb.info203.SSTemp.entity.*;
import ca.qc.bdeb.info203.SSTemp.res.*;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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

    private ArrayList<Entity> aSplit = new ArrayList<>();
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

    private SpriteSheet parachuteSpriteSheet;

    private String bulletImagePath;

    private String planetChunkImagePath;

    private String marsImagePath;

    private Player player;

    private ControllerMars controllerMars;

    private Parachute parachute;

    private Sound sound;

    private boolean musicPaused;

    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        loadSprites();

        controllerMars = new ControllerMars();

        Background b = new Background(largeurEcran, hauteurEcran, 100, planetChunkImagePath, marsImagePath, starSpriteSheet, controllerMars);
        entites.add(b);
        mobiles.add(b);

        player = new Player(0, 0, playerBodySpriteSheet, playerCoreLaserSpriteSheet, playerCoreEffectSpriteSheet, playerRightPropulsorSpriteSheet, playerLeftPropulsorSpriteSheet, bulletImagePath, controllerMars);
        entites.add(player);
        mobiles.add(player);

        //Setup Music
        //Maktone - Razor1911 Chipdisk 02
        startMusic();

        for (int i = 0; i < 20; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 0, 0, 256, 256, controllerMars, hauteurEcran, largeurEcran);
            //Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 0, 256, 128, 128, controllerMars, hauteurEcran, largeurEcran);
            //Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 512, 256, 64, 64, controllerMars, hauteurEcran, largeurEcran);
            //Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 512, 320, 32, 32, controllerMars, hauteurEcran, largeurEcran);
            //Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 512, 352, 16, 16, controllerMars, hauteurEcran, largeurEcran);
            entites.add(asteroid);
            mobiles.add(asteroid);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        spawnParachute();

        for (Mobile mobile : mobiles) {
            mobile.bouger(largeurEcran, hauteurEcran);
        }

        spawnBullet();
        manageCollisons();
        detruireEntites();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for (Entity entite : entites) {
            entite.dessiner(g);
        }
        if (player.getHealth() <= 0) {
            g.drawString("Dead", 10, 10);
            
        } else {
            g.drawString(player.getHealth() + "", 10, 10);
        }

    }

    @Override
    public void keyPressed(int key, char c) {
        if (!controllerMars.isGamePaused()) {
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
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            container.exit();
        } else if (key == Input.KEY_M) {
            pauseMusic();
        }
        if (!controllerMars.isGamePaused()) {
            switch (key) {
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
                    goToMars();
                    break;
            }
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
            parachuteSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/Parachute.png", 31, 50);
            bulletImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Bullet2.png";
            planetChunkImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/BackgroundChunk.png";
            marsImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Mars.png";
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    //Séparer chaque entité
    private void detruireEntites() {
        for (Entity entite : entites) {
            if (entite.getDetruire()) {
                aDetruire.add(entite);
            }
            if (entite.getSplit()) {
                aSplit.add(entite);
            }
        }
        entites.removeAll(aDetruire);
        mobiles.removeAll(aDetruire);
        for (Entity entite : aDetruire) {
            if (entite instanceof Asteroid) {

            }
        }
        aDetruire.clear();

        entites.removeAll(aSplit);
        mobiles.removeAll(aSplit);
        for (Entity entite : aSplit) {
            if (entite instanceof Asteroid) {
                Random rnd = new Random();
                int lastSize = entite.getWidth();
                int lastPosX = entite.getX();
                int lastPosY = entite.getY();
                Asteroid asteroid1 = null;
                Asteroid asteroid2 = null;

                switch (lastSize) {
                    case 256:
                        asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, controllerMars, hauteurEcran, largeurEcran);
                        asteroid2 = new Asteroid(lastPosX - 20, lastPosY - rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, controllerMars, hauteurEcran, largeurEcran);
                        splitAsteroid(asteroid1, asteroid2);
                        break;
                    case 128:
                        asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, controllerMars, hauteurEcran, largeurEcran);
                        asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, controllerMars, hauteurEcran, largeurEcran);
                        splitAsteroid(asteroid1, asteroid2);
                        break;
                    case 64:
                        asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, controllerMars, hauteurEcran, largeurEcran);
                        asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, controllerMars, hauteurEcran, largeurEcran);
                        splitAsteroid(asteroid1, asteroid2);
                        break;
                    case 32:
                        asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, controllerMars, hauteurEcran, largeurEcran);
                        asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, controllerMars, hauteurEcran, largeurEcran);
                        splitAsteroid(asteroid1, asteroid2);
                        break;
                    case 16:
                        //Capture part
                        break;
                    default:
                        System.out.println("Size not valid error");
                        ;
                }
            }
        }
        aSplit.clear();
    }

    private void startMusic() {
        try {
            sound = new Sound("ca/qc/bdeb/info203/SSTemp/sounds/background.ogg");
            sound.loop();
            musicPaused = false;
        } catch (SlickException e) {
            System.out.println("File not found or Library Missing");
        }
    }

    private void pauseMusic() {
        if (musicPaused) {
            sound.loop();
            musicPaused = false;
        } else {
            sound.stop();
            musicPaused = true;
        }

    }

    private void splitAsteroid(Asteroid asteroid1, Asteroid asteroid2) {
        entites.add(asteroid1);
        mobiles.add(asteroid1);
        entites.add(asteroid2);
        mobiles.add(asteroid2);
    }

    private void spawnBullet() {
        Bullet newBullet = player.shoot();
        if (newBullet != null) {
            entites.add(newBullet);
            mobiles.add(newBullet);
        }
    }

    private void spawnParachute() {
        if (controllerMars.isSpawnParachute()) {
            this.parachute = new Parachute(player.getX() + 23, player.getY() + 23, parachuteSpriteSheet);
            entites.add(parachute);
            mobiles.add(parachute);
            controllerMars.setSpawnParachute(false);
        }
        if (controllerMars.isRemoveParachute()) {
            parachute.setDetruire(true);
            controllerMars.setRemoveParachute(false);
        }
    }

    private void manageCollisons() {
        for (Entity entite : entites) {
            if (entite instanceof Bullet) {
                for (Entity asteroid : entites) {
                    if (asteroid instanceof Asteroid) {
                        if (entite.getRectangle().intersects(asteroid.getRectangle())) {
                            asteroid.setSplit(true);
                            entite.setDetruire(true);
                        }
                    }
                }
            }
            if (entite instanceof Player) {
                for (Entity asteroid : entites) {
                    if (asteroid instanceof Asteroid) {
                        if (player.getRectangle().intersects(asteroid.getRectangle())) {
                            player.setHealth(player.getHealth() - 1);
                            asteroid.setDetruire(true);
                        }
                    }
                }
            }

            if (entite instanceof Asteroid) {
                for (Entity asteroid : entites) {
                    if (asteroid instanceof Asteroid) {
                        if (entite.getRectangle().intersects(asteroid.getRectangle())) {
                            /*((Asteroid) entite).setxSpeed(((Asteroid) entite).getxSpeed()*-1);
                            ((Asteroid) entite).setySpeed(((Asteroid) entite).getySpeed()*-1);
                            ((Asteroid) asteroid).setxSpeed(((Asteroid) asteroid).getxSpeed()*-1);
                            ((Asteroid) asteroid).setySpeed(((Asteroid) asteroid).getySpeed()*-1);*/
                        }
                    }
                }
            }
        }
    }

    private void goToMars() {
        player.moveDown(false);
        player.moveLeft(false);
        player.moveUp(false);
        player.moveRight(true);
        player.shootBullet(false);
        controllerMars.setPauseGame(true);
        controllerMars.setGoingToMars(true);
        controllerMars.setInitialCoordinates(player.getX(), player.getY());
    }
}
