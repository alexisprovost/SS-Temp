package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Manuel Ramirez, Alexis Provost
 */
public class Player extends Entity implements Mobile{
    
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;
    
    private final int MAX_SPEED = 12;
    private int speedX = 0;
    private int speedY = 0;
    private int acceleration = 1;
    
    public Player(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne) {
        super(x, y, spriteSheet, ligne, colonne);
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        setSpeed();
        setLocation(getX() + speedX, getY() + speedY);
    }
    
    private void setSpeed(){
        if(moveUp && speedY > -MAX_SPEED){
            speedY -= acceleration;
        }else if(!moveUp && speedY < 0){
            speedY += acceleration;
        }
        if(moveDown && speedY < MAX_SPEED){
            speedY += acceleration;
        }else if(!moveDown && speedY > 0){
            speedY -= acceleration;
        }
        if(moveLeft && speedX > -MAX_SPEED){
            speedX -= acceleration;
        }else if(!moveLeft && speedX < 0){
            speedX += acceleration;
        }
        if(moveRight && speedX < MAX_SPEED){
            speedX += acceleration;
        }else if(!moveRight && speedX > 0){
            speedX -= acceleration;
        }
    }
    
    public void moveUp(boolean moveUp){
        this.moveUp = moveUp;
    }
    
    public void moveDown(boolean moveDown){
        this.moveDown = moveDown;
    }
    
    public void moveLeft(boolean moveLeft){
        this.moveLeft = moveLeft;
    }
    
    public void moveRight(boolean moveRight){
        this.moveRight = moveRight;
    }
}