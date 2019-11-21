package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class Asteroid extends Entity implements Mobile {

    private SpriteSheet spriteSheet;
    private int xSpeed;
    private int ySpeed;
    private int maxSpeed = 5;
    private int extraSpace = 300;

    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
        this.spriteSheet = spriteSheet;
        
        Random rnd = new Random();
        
        xSpeed = -rnd.nextInt(maxSpeed);
        
        if(rnd.nextInt(2) == 1){
            ySpeed = -rnd.nextInt(maxSpeed);
        }else{
            ySpeed = rnd.nextInt(maxSpeed);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(getImage(), getX(), getY());
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        if (getX() + getWidth() >= limiteX+extraSpace) {   
            xSpeed *= -1;
        } 
        
        if (getY() + getHeight()>= limiteY+extraSpace) {   
            setDetruire(true);
        }

        if (getX() <= -extraSpace) {
            setDetruire(true);
        }
        
        if (getY() <= -extraSpace) {
            ySpeed *= -1;
        }
        
        getImage().setRotation(getImage().getRotation()+ 1);
        
        setLocation(getX() + xSpeed, getY() + ySpeed);
    }

}
