package ca.qc.bdeb.info203.SSTemp.vue;

import ca.qc.bdeb.info203.SSTemp.model.Modele;
import ca.qc.bdeb.info203.SSTemp.vue.entity.*;
import ca.qc.bdeb.info203.SSTemp.vue.res.*;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Bullet;
import ca.qc.bdeb.info203.SSTemp.vue.ui.DeathScreen;
import ca.qc.bdeb.info203.SSTemp.vue.ui.GoToMarsNotice;
import ca.qc.bdeb.info203.SSTemp.vue.ui.HealthBar;
import ca.qc.bdeb.info203.SSTemp.vue.ui.InventoryBar;
import ca.qc.bdeb.info203.SSTemp.vue.ui.GameInfos;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
 * @author Mathieu Grenon, Stéphane Lévesque, Manuel Ramirez, Alexis Provost
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
     * Les astéroïdes à ajouter
     */
    private ArrayList<Asteroid> asteroidsToAdd = new ArrayList<>();
    /**
     * Largeur de l'écran.
     */
    private int largeurEcran;
    /**
     * Hauteur de l'écran.
     */
    private int hauteurEcran;

    /**
     * Sprite Sheet du vaisseau
     */
    private SpriteSheet playerBodySpriteSheet;

    /**
     * Sprite Sheet du laser
     */
    private SpriteSheet playerCoreLaserSpriteSheet;

    /**
     * Sprite Sheet des effets du coeur du vaisseau
     */
    private SpriteSheet playerCoreEffectSpriteSheet;

    /**
     * Sprite Sheet du propulseur droit du vaisseau
     */
    private SpriteSheet playerRightPropulsorSpriteSheet;

    /**
     * Sprite Sheet du propulseur gauche du vaisseau
     */
    private SpriteSheet playerLeftPropulsorSpriteSheet;

    /**
     * Sprite Sheet des astéroïdes
     */
    private SpriteSheet asteroidSpriteSheet;

    /**
     * Sprite Sheet des étoiles
     */
    private SpriteSheet starSpriteSheet;

    /**
     * Sprite Sheet du parachute
     */
    private SpriteSheet parachuteSpriteSheet;

    /**
     * Chemin de l'image d'arrière-plan de l'écran de mort
     */
    private String deathBg;

    /**
     * Chemin de l'image des balles
     */
    private String bulletImagePath;

    /**
     * Chemin de l'image d'une partie de l'arrière-plan
     */
    private String planetChunkImagePath;

    /**
     * Chemin de l'image de l'arrière-plan de Mars
     */
    private String marsImagePath;

    /**
     * Chemin de l'image de la barre de minérais
     */
    private String barImagePath;

    /**
     * Chemin de l'image de la barre de vie
     */
    private String heartImagePath;

    /**
     * Chemin de l'image de l'icône de la barre d'inventaire
     */
    private String rockImagePath;

    /**
     * Joueur
     */
    private Player player;

    /**
     * État de Mars
     */
    private MarsState marsState;

    /**
     * Couleur du core du vaisseau
     */
    private CoreColorPicker coreColorPicker;

    /**
     * Parachute
     */
    private Parachute parachute;

    /**
     * Musique d'arrière-plan
     */
    private Music music;

    /**
     * Modèle
     */
    private Modele modele;

    /**
     * Alerte d'inventaire plein
     */
    private GoToMarsNotice goToMarsNotice;

    /**
     * Police d'écriture personnalisée
     */
    private UnicodeFont font;

    /**
     * Musique en pause
     */
    private boolean musicPaused;

    /**
     * Volume de la musique
     */
    private float musicVolume = 0.1f;

    /**
     * Écran de mort
     */
    private DeathScreen deathScreen;

    /**
     * Écran de mort activée
     */
    private boolean deathEnable;

    /**
     * Constructeur de Jeu
     * @param largeur Largeur de l'écran
     * @param hauteur Hauteur de l'écran
     */
    public Jeu(int largeur, int hauteur) {
        super("SS-Temp");
        this.largeurEcran = largeur;
        this.hauteurEcran = hauteur;
    }

    /**
     * Initialisation Slick 2D
     * @param container Conteneur de jeu Slick 2D
     * @throws SlickException Exception Slick 2D
     */
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        loadSprites();
        loadFont();
        initializeGame();
    }

    /**
     * Méthode d'initialisation du jeu
     */
    private void initializeGame() {
        modele = new Modele();
        goToMarsNotice = null;
        coreColorPicker = new CoreColorPicker(Color.red, new Color(166, 200, 252), modele);
        marsState = new MarsState();

        //Création de l'interface de l'utilisateur
        HealthBar healthBar = new HealthBar(10, 10, barImagePath, heartImagePath, modele);
        ui.add(healthBar);

        InventoryBar inventoryBar = new InventoryBar(10, 50, barImagePath, rockImagePath, modele, coreColorPicker);
        ui.add(inventoryBar);

        GameInfos gameInfos = new GameInfos(modele, largeurEcran);
        ui.add(gameInfos);

        //Création du fond
        Background b = new Background(largeurEcran, hauteurEcran, 100, planetChunkImagePath, marsImagePath, starSpriteSheet, marsState);
        entites.add(b);
        mobiles.add(b);

        //Nouveau Joueur
        player = new Player(largeurEcran / 16, hauteurEcran / 2, playerBodySpriteSheet, playerCoreLaserSpriteSheet, playerCoreEffectSpriteSheet, playerRightPropulsorSpriteSheet, playerLeftPropulsorSpriteSheet, bulletImagePath, marsState, coreColorPicker);
        entites.add(player);
        mobiles.add(player);
        collisionables.add(player);

        //Lecture de la musique
        startMusic(1, musicVolume, "ca/qc/bdeb/info203/SSTemp/sounds/background.ogg");

        //Planification de la création des astéroïdes
        scheduleAsteroidAppearance();

        //reActive l'écran de mort
        deathEnable = true;

        //Démare le temps de jeu
        modele.startTime();
    }

    /**
     * Méthode de mise à jour de Slick 2D
     * @param container Conteneur Slick 2D
     * @param delta Delta Slick 2D
     * @throws SlickException Exception Slick 2D
     */
    public void update(GameContainer container, int delta) throws SlickException {
        //Ajout des nouveaux astéroïdes à ajouter
        entites.addAll(asteroidsToAdd);
        mobiles.addAll(asteroidsToAdd);
        collisionables.addAll(asteroidsToAdd);

        asteroidsToAdd.clear();

        spawnParachute();
        avoidInstantDeath();

        //Bouge les mobiles
        for (Mobile mobile : mobiles) {
            mobile.bouger(largeurEcran, hauteurEcran);
        }

        spawnBullet();
        manageCollisons();
        detruireEntites();
    }

    /**
     * Render Slick 2D
     * @param container Conteneur Slick 2D
     * @param g Graphiques Slick 2D
     * @throws SlickException Exception Slick 2D
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        //Changement de la police d'écriture
        g.setFont(font);

        //Afficher les entités et UI
        for (Entity entite : entites) {
            entite.dessiner(g);
        }
        for (Entity uiEntity : ui) {
            uiEntity.dessiner(g);
        }
    }

    /**
     * Méthode qui gère les entrées clavier
     * @param key Touche clavier enfoncée par l'utilisateur en KEYCODE (@see org.newdawn.slick.Input)
     * @param c Touche clavier enfoncée par l'utilisateur en Char
     */
    @Override
    public void keyPressed(int key, char c) {
        if (!marsState.isGamePaused()) {
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

    /**
     * Méthode qui gère les relâches de touches clavier
     * @param key Touche clavier enfoncée par l'utilisateur en KEYCODE (@see org.newdawn.slick.Input)
     * @param c Touche clavier enfoncée par l'utilisateur en Char
     */
    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            container.exit();
        } else if (key == Input.KEY_M) {
            pauseMusic();
        } else if (key == Input.KEY_R && modele.isPlayerIsDead()) {
            restart();
        }

        //Touches non fonctionnelles lors d'un voyage sur mars
        if (!marsState.isGamePaused()) {
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

    /**
     * Chargement des Sprites
     */
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

    /**
     * Chargement de la police d'écriture personnalisé
     */
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

    /**
     * Planification de l'apparition d'astéroïdes
     */
    private void scheduleAsteroidAppearance() {
        Random rnd = new Random();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    asteroidsToAdd.add(new Asteroid(largeurEcran + 150, rnd.nextInt(hauteurEcran), asteroidSpriteSheet, 0, 0, 256, 256, marsState, hauteurEcran, largeurEcran));
                }
            }
        }, 0, 15000);
    }

    /**
     * Réinitialisation de nouvelle partie
     */
    private void restart() {
        mobiles.clear();

        entites.clear();

        collisionables.clear();

        ui.clear();

        music.stop();

        initializeGame();
    }

    /**
     * Destruction d'entités
     */
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

    /**
     * Séparation d'astéroïdes lorsqu'une balle rentre un contact avec un astéroïde
     * @param aSplit Arraylist des astéroïdes à séparer
     */
    private void splitAsteroid(ArrayList<Asteroid> aSplit) {
        for (Asteroid entite : aSplit) {
            Random rnd = new Random();
            int lastSize = entite.getWidth();
            int lastPosX = entite.getX();
            int lastPosY = entite.getY();
            Asteroid asteroid1;
            Asteroid asteroid2;

            //Choix selon grandeur originale
            switch (lastSize) {
                case 256:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, marsState, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX - 20, lastPosY - rnd.nextInt(lastSize), asteroidSpriteSheet, 0, 256, 128, 128, marsState, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 128:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, marsState, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 256, 64, 64, marsState, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 64:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, marsState, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 320, 32, 32, marsState, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
                case 32:
                    asteroid1 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, marsState, hauteurEcran, largeurEcran);
                    asteroid2 = new Asteroid(lastPosX + 20, lastPosY + rnd.nextInt(lastSize), asteroidSpriteSheet, 512, 352, 16, 16, marsState, hauteurEcran, largeurEcran);
                    addNewAsteroids(asteroid1, asteroid2);
                    break;
            }
        }
    }

    /**
     * Nouveaux astéroïdes lors de la séparation
     * @param asteroid1 Partie 1 de la séparation
     * @param asteroid2 Partie 2 de la séparation
     */
    private void addNewAsteroids(Asteroid asteroid1, Asteroid asteroid2) {
        entites.add(asteroid1);
        mobiles.add(asteroid1);
        collisionables.add(asteroid1);
        entites.add(asteroid2);
        mobiles.add(asteroid2);
        collisionables.add(asteroid2);
    }

    /**
     * Lecture de la musique
     * @param pitch Pitch du son
     * @param volume Volume de la musique
     * @param path Chemin du fichier audio .ogg
     */
    private void startMusic(float pitch, float volume, String path) {
        try {
            music = new Music(path);
            music.loop(pitch, volume);
            musicPaused = false;
        } catch (SlickException e) {
            System.out.println("Slick Error - Music");
        }
    }

    /**
     * Met en pause la musique
     */
    private void pauseMusic() {
        if (musicPaused) {
            music.loop(1, musicVolume);
            musicPaused = false;
        } else {
            music.stop();
            musicPaused = true;
        }
    }

    /**
     * Fais la création des balles (missiles) et l'ajout aux listes
     */
    private void spawnBullet() {
        Bullet newBullet = player.shoot();
        if (newBullet != null) {
            entites.add(newBullet);
            mobiles.add(newBullet);
            collisionables.add(newBullet);
        }
    }

    /**
     * Crée le parachute lors du voyage sur mars
     */
    private void spawnParachute() {
        if (marsState.isSpawnParachute()) {
            modele.envoyerSurMars();
            this.parachute = new Parachute(player.getX() + 23, player.getY() + 23, parachuteSpriteSheet);
            entites.add(parachute);
            mobiles.add(parachute);
            marsState.setSpawnParachute(false);
        }
        if (marsState.isRemoveParachute()) {
            parachute.setDetruire(true);
            marsState.setRemoveParachute(false);
        }
    }

    /**
     * Place les astéroïdes existants à l’extrémité droite du jeu pour ne pas mourir des que l’on revient de mars
     */
    private void avoidInstantDeath() {
        if (marsState.isResetAsteroid()) {
            for (Entity entite : entites) {
                if (entite instanceof Asteroid) {
                    ((Asteroid) entite).setLocation(largeurEcran + 200, entite.getY());
                }
            }
            marsState.setResetAsteroid(false);
            marsState.setHideAsteroids(false);
        }
    }

    /**
     * Gère les collisions avec le joueur, missile avec les astéroïdes
     */
    private void manageCollisons() {
        if (!marsState.isHideAsteroids() && !marsState.isGamePaused()) {
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

    /**
     * Regarde si le joueur est mort si oui affiche l'écran de mort
     */
    private void checkIfDead() {
        if (modele.isPlayerIsDead() && deathEnable) {
            deathEnable = false;
            float time = music.getPosition();
            music.stop();
            music.play(0.5f, musicVolume);
            //music.loop();
            music.setPosition(time);
            deathScreen = new DeathScreen(modele, deathBg, largeurEcran, hauteurEcran);
            ui.add(deathScreen);
            marsState.setGamePaused(true);
        }
    }

    /**
     * Gère les collisions des missiles avec les astéroïdes
     * @param bullet Missile
     */
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

    /**
     * Gère les collisions du joueur avec les astéroïdes
     * @param player Joueur
     */
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
                        player.takeDamage();
                    }
                    asteroid.setDetruire(true);
                }
            }
        }
    }

    /**
     * Vérifie si la grandeur de l'astéroïde est assez petite pour être ramassé et si l'impact est en avant du joueur
     * @param player Joueur
     * @param asteroid Astéroïde
     * @return Boolean qui est true si l'impact est valide
     */
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

    /**
     * Désactive les fonctionnalités du jeu lors du voyage sur mars
     */
    private void goToMars() {
        player.moveDown(false);
        player.moveLeft(false);
        player.moveUp(false);
        player.moveRight(true);
        player.shootBullet(false);
        marsState.setPauseGame(true);
        marsState.setGoingToMars(true);
        marsState.setInitialCoordinates(player.getX(), player.getY());
        ui.remove(goToMarsNotice);
        goToMarsNotice = null;
    }
}
