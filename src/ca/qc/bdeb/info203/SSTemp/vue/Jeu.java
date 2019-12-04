package ca.qc.bdeb.info203.SSTemp.vue;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.entity.*;
import ca.qc.bdeb.info203.SSTemp.vue.res.*;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Bullet;
import ca.qc.bdeb.info203.SSTemp.vue.ui.DeathScreen;
import ca.qc.bdeb.info203.SSTemp.vue.ui.GoToMarsNotice;
import ca.qc.bdeb.info203.SSTemp.vue.ui.HealthBar;
import ca.qc.bdeb.info203.SSTemp.vue.ui.InventoryBar;
import ca.qc.bdeb.info203.SSTemp.vue.ui.NbMars;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

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
     * Tous les collisionables.
     */
    private ArrayList<Collisionable> collisionables = new ArrayList<>();
    /**
     * Tous les éléments UI.
     */
    private ArrayList<Entity> ui = new ArrayList<>();
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

    private String deathBg;

    private String bulletImagePath;

    private String planetChunkImagePath;

    private String marsImagePath;

    private String barImagePath;

    private String heartImagePath;

    private String rockImagePath;

    private Player player;

    private MarsState controllerMars;

    private CoreColorPicker coreColorPicker;

    private Parachute parachute;

    private Music music;

    private Modele modele;

    private GoToMarsNotice goToMarsNotice;

    private UnicodeFont font;

    private boolean musicPaused;

    private float musicVolume = 0.1f;

    private DeathScreen deathScreen;
    
    private boolean deathEnable;
    

    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    public void init(GameContainer container) throws SlickException {
        this.container = container;
        loadSprites();
        loadFont();
        initializeGame();
    }

    private void initializeGame() {
        modele = new Modele();
        goToMarsNotice = null;
        coreColorPicker = new CoreColorPicker(Color.red, new Color(166, 200, 252), modele);
        controllerMars = new MarsState();

        HealthBar healthBar = new HealthBar(10, 10, barImagePath, heartImagePath, modele);
        ui.add(healthBar);

        InventoryBar inventoryBar = new InventoryBar(10, 50, barImagePath, rockImagePath, modele, coreColorPicker);
        ui.add(inventoryBar);

        NbMars nbMars = new NbMars(modele, largeurEcran);
        ui.add(nbMars);

        Background b = new Background(largeurEcran, hauteurEcran, 100, planetChunkImagePath, marsImagePath, starSpriteSheet, controllerMars);
        entites.add(b);
        mobiles.add(b);

        player = new Player(largeurEcran / 16, hauteurEcran / 2, playerBodySpriteSheet, playerCoreLaserSpriteSheet, playerCoreEffectSpriteSheet, playerRightPropulsorSpriteSheet, playerLeftPropulsorSpriteSheet, bulletImagePath, controllerMars, coreColorPicker);
        entites.add(player);
        mobiles.add(player);
        collisionables.add(player);

        startMusic(1, musicVolume, "ca/qc/bdeb/info203/SSTemp/sounds/background.ogg");

        asteroidAppearance();
        
        deathEnable = true;
    }

    private void asteroidAppearance() {
        for (int i = 0; i < 20; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 0, 0, 256, 256, controllerMars, hauteurEcran, largeurEcran);
            entites.add(asteroid);
            mobiles.add(asteroid);
            collisionables.add(asteroid);
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        spawnParachute();
        avoidInstantDeath();

        for (Mobile mobile : mobiles) {
            mobile.bouger(largeurEcran, hauteurEcran);
        }

        spawnBullet();
        manageCollisons();
        detruireEntites();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setFont(font);
        for (Entity entite : entites) {
            entite.dessiner(g);
        }
        for (Entity uiEntity : ui) {
            uiEntity.dessiner(g);
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
        } else if (key == Input.KEY_R && modele.isPlayerIsDead()) {
            restart();
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
            playerCoreLaserSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerCoreLaserSpriteSheet.png", 71, 16);
            playerCoreEffectSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerCoreEffectSpriteSheet.png", 19, 30);
            playerRightPropulsorSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerRightPropulsorSpriteSheet.png", 56, 34);
            playerLeftPropulsorSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/PlayerLeftPropulsorSpriteSheet.png", 56, 34);
            asteroidSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/AsteroidSpriteSheet.png", 16, 16);
            starSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/StarSpriteSheet.png", 2, 2);
            parachuteSpriteSheet = new SpriteSheet("ca/qc/bdeb/info203/SSTemp/sprites/ParachuteSpriteSheet.png", 78, 118);
            bulletImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Bullet.png";
            planetChunkImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/BackgroundChunk.png";
            marsImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Mars.png";
            barImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Bar.png";
            heartImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Heart.png";
            rockImagePath = "ca/qc/bdeb/info203/SSTemp/sprites/Rock.png";
            deathBg = "ca/qc/bdeb/info203/SSTemp/sprites/deathBg.png";
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    private void loadFont() {
        try {
            Font tempFont = Font.createFont(Font.TRUETYPE_FONT, new File("Mecha.ttf"));
            tempFont = tempFont.deriveFont(Font.PLAIN, 24f);
            font = new UnicodeFont(tempFont);
            font.getEffects().add(new ColorEffect());
            font.addAsciiGlyphs();
            font.loadGlyphs();
        } catch (FontFormatException ffe) {
            System.out.println("FontFormatException :" + ffe);
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("IOException :" + ioe);
            System.exit(1);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
    }

    private void restart() {
        mobiles.clear();

        entites.clear();

        collisionables.clear();

        ui.clear();

        music.stop();
        
        initializeGame();
    }

    private void detruireEntites() {
        ArrayList<Entity> aDetruire = new ArrayList<>();
        ArrayList<Asteroid> aSplit = new ArrayList<>();

        for (Entity entite : entites) {
            if (entite.getDetruire()) {
                aDetruire.add(entite);
            }
            if (entite instanceof Asteroid) {
                Asteroid asteroidToSplit = (Asteroid) entite;
                if (asteroidToSplit.getSplit()) {
                    aSplit.add(asteroidToSplit);
                }
            }
        }
        entites.removeAll(aDetruire);
        mobiles.removeAll(aDetruire);
        collisionables.removeAll(aDetruire);
        splitAsteroid(aSplit);
    }

    private void splitAsteroid(ArrayList<Asteroid> aSplit) {
        for (Asteroid entite : aSplit) {
            Random rnd = new Random();
            int lastSize = entite.getWidth();
            int lastPosX = entite.getX();
            int lastPosY = entite.getY();
            Asteroid asteroid1;
            Asteroid asteroid2;

            switch (lastSize) {
                case 256:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, controllerMars, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX - 20, lastPosY - rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, controllerMars, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 128:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, controllerMars, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, controllerMars, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 64:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, controllerMars, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, controllerMars, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 32:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, controllerMars, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, controllerMars, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
            }
        }
    }

    private void addNewAsteroids(Asteroid asteroid1, Asteroid asteroid2) {
        entites.add(asteroid1);
        mobiles.add(asteroid1);
        collisionables.add(asteroid1);
        entites.add(asteroid2);
        mobiles.add(asteroid2);
        collisionables.add(asteroid2);
    }

    private void startMusic(float pitch, float volume, String path) {
        try {
            music = new Music(path);
            music.loop(pitch, volume);
            musicPaused = false;
        } catch (SlickException e) {
            System.out.println("File not found or Library Missing");
        }
    }

    private void pauseMusic() {
        if (musicPaused) {
            music.loop(1, musicVolume);
            musicPaused = false;
        } else {
            music.stop();
            musicPaused = true;
        }
    }

    private void spawnBullet() {
        Bullet newBullet = player.shoot();
        if (newBullet != null) {
            entites.add(newBullet);
            mobiles.add(newBullet);
            collisionables.add(newBullet);
        }
    }

    private void spawnParachute() {
        if (controllerMars.isSpawnParachute()) {
            modele.envoyerSurMars();
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

    private void avoidInstantDeath() {
        if (controllerMars.isResetAsteroid()) {
            for (Entity entite : entites) {
                if (entite instanceof Asteroid) {
                    ((Asteroid) entite).setLocation(largeurEcran + 200, entite.getY());
                }
            }
            controllerMars.setResetAsteroid(false);
            controllerMars.setHideAsteroids(false);
        }        
    }

    private void manageCollisons() {
        if (!controllerMars.isGamePaused()) {
            for (Collisionable collisionable : collisionables) {
                if (collisionable instanceof Bullet) {
                    bulletAsteroidCollisions((Bullet) collisionable);
                }
                if (collisionable instanceof Player) {
                    playerAsteroidCollisions((Player) collisionable);
                    checkIfDead();
                }
            }
        }
    }

    

    private void checkIfDead() {
        if (modele.isPlayerIsDead() && deathEnable) {
            deathEnable = false;
            float time = music.getPosition();
            music.stop();
            music.play(0.5f, musicVolume);
            music.setPosition(time);
            deathScreen = new DeathScreen(deathBg, largeurEcran, hauteurEcran);
            ui.add(deathScreen);
            controllerMars.setGamePaused(true);
        }
    }

    private void bulletAsteroidCollisions(Bullet bullet) {
        for (Collisionable collisionable : collisionables) {
            if (collisionable instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) collisionable;
                if (bullet.getRectangle().intersects(asteroid.getRectangle())) {
                    asteroid.setSplit(true);
                    bullet.setDetruire(true);
                }
            }
        }
    }

    private void playerAsteroidCollisions(Player player) {
        for (Collisionable collisionable : collisionables) {
            if (collisionable instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) collisionable;
                if (player.getRectangle().intersects(asteroid.getRectangle())) {
                    if (canCollectAsteroid(player, asteroid)) {
                        modele.fillInventory(asteroid.getWidth());
                        if (modele.isInventoryFull()) {
                            goToMarsNotice = new GoToMarsNotice(30, largeurEcran, hauteurEcran);
                            ui.add(goToMarsNotice);
                        }
                        player.collectAsteroid();
                    } else {
                        modele.removeHealth(asteroid.getWidth());
                    }
                    asteroid.setDetruire(true);
                }
            }
        }
    }

    private boolean canCollectAsteroid(Player player, Asteroid asteroid) {
        boolean canCollectAsteroid;
        if (modele.isInventoryFull()) {
            canCollectAsteroid = false;
        } else if (asteroid.getWidth() >= 128) {
            canCollectAsteroid = false;
        } else {
            int playerTop = player.getY();
            int playerBottom = playerTop + player.getHeight();
            int asteroidTop = asteroid.getY();
            int asteroidBottom = asteroidTop + asteroid.getHeight();
            boolean topClear = asteroidTop >= playerTop;
            boolean bottomClear = asteroidBottom <= playerBottom;
            boolean insideFront = asteroid.getX() > player.getX();
            if (insideFront) {
                if (topClear && bottomClear) {
                    canCollectAsteroid = true;
                } else if (topClear) {
                    canCollectAsteroid = playerBottom - asteroidTop > (player.getX() + player.getWidth()) - asteroid.getX();
                } else if (bottomClear) {
                    canCollectAsteroid = asteroidBottom - playerTop > (player.getX() + player.getWidth()) - asteroid.getX();
                } else {
                    canCollectAsteroid = false;
                }
            } else {
                canCollectAsteroid = false;
            }
        }
        return canCollectAsteroid;
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
        ui.remove(goToMarsNotice);
        goToMarsNotice = null;
    }
}
