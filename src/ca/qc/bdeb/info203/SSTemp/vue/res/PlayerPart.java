package ca.qc.bdeb.info203.SSTemp.vue.res;

import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 * Classe Player Part
 * @author Manuel Ramirez, Alexis Provost
 */
public abstract class PlayerPart {
    /**
     * Offset des parts su vaisseau en x
     */
    private final int X_OFFSET;
    
    /**
     * Offset des parts su vaisseau en Y
     */
    private final int Y_OFFSET;

    /**
     * Largeur de l'entité
     */
    private int width;
    /**
     * Hauteur de l'entité
     */
    private int height;

    /**
     * Joueur
     */
    private Player player;

    /**
     * Sprite sheet parts vaisseau
     */
    private SpriteSheet spriteSheet;

    /**
     * Constructeur Player parts
     * @param player Objet Joueur
     * @param spriteSheet Sprite sheet player parts
     * @param xOffset Offset des parts su vaisseau en x
     * @param yOffset Offset des parts su vaisseau en y
     */
    public PlayerPart(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        this.player = player;
        this.X_OFFSET = xOffset;
        this.Y_OFFSET = yOffset;
        this.spriteSheet = spriteSheet;
        this.width = spriteSheet.getWidth() / spriteSheet.getHorizontalCount();
        this.height = spriteSheet.getHeight() / spriteSheet.getVerticalCount();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public int getX() {
        return player.getX() + X_OFFSET;
    }

    public int getY() {
        return player.getY() + Y_OFFSET;
    }

    /**
     * Dessine l'entité dans l'interface.
     *
     * @param g L'objet Graphics à utiliser pour dessiner dans la fenêtre de
     * l'application.
     */
    public abstract void dessiner(Graphics g);
}
