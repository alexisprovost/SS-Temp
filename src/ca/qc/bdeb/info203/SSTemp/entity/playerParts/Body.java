package ca.qc.bdeb.info203.SSTemp.entity.playerParts;

import ca.qc.bdeb.info203.SSTemp.res.PlayerPart;
import ca.qc.bdeb.info203.SSTemp.entity.Player;
import org.newdawn.slick.*;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Body extends PlayerPart {

    private Image idleImage;
    private Animation absorbAnimation;

    public Body(Player player, SpriteSheet spriteSheet, int xOffset, int yOffset) {
        super(player, spriteSheet, xOffset, yOffset);
        initIdleImage();
        initAbsorbAnimation();
    }

    private void initIdleImage() {
        this.idleImage = getSpriteSheet().getSubImage(0, 0);
    }

    private void initAbsorbAnimation() {

    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(idleImage, getX(), getY());
    }

}
