package ca.qc.bdeb.info203.SSTemp.vue.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.vue.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.vue.entity.Player;
import javax.swing.JRootPane;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez
 */
public class CoreEffect extends PlayerPart {

    private Animation idleAnimation;

    public CoreEffect(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleAnimation();
    }

    public void initIdleAnimation() {
        this.idleAnimation = new Animation();
        Image blankFrame = null;
        try {
            blankFrame = new Image(0, 0);
        } catch (SlickException se) {
            System.out.println("SlickException :" + se);
            System.exit(1);
        }
        for (int i = 0; i < 3; i++) {
            idleAnimation.addFrame(blankFrame, 200);
            for (int j = 0; j < 5; j++) {
                idleAnimation.addFrame(getSpriteSheet().getSubImage(j, i), 100);
            }
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawAnimation(idleAnimation, getX(), getY(), new Color(166, 200, 252));
    }

}
