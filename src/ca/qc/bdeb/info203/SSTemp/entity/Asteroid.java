package ca.qc.bdeb.info203.SSTemp.entity;

import ca.qc.bdeb.info203.SSTemp.res.Entity;
import ca.qc.bdeb.info203.SSTemp.res.Mobile;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class Asteroid extends Entity implements Mobile {

    private ControllerMars controllerMars;

    private boolean split = false;

    private SpriteSheet spriteSheet;
    private int xSpeed;
    private int ySpeed;
    private int maxSpeed = 5;
    private int extraSpace = 500;
    private int hauteurEcran = 0;
    private int largeurEcran = 0;

    public Asteroid(int x, int y, SpriteSheet spriteSheet, int ligne, int colonne, int width, int height, ControllerMars controllerMars, int hauteurEcran, int largeurEcran) {
        super(x, y, spriteSheet, ligne, colonne, width, height);
        this.spriteSheet = spriteSheet;
        this.controllerMars = controllerMars;
        this.hauteurEcran = hauteurEcran;
        this.largeurEcran = largeurEcran;

        Random rnd = new Random();

        xSpeed = -rnd.nextInt(maxSpeed);

        if (rnd.nextInt(2) == 1) {
            ySpeed = -rnd.nextInt(maxSpeed);
        } else {
            ySpeed = rnd.nextInt(maxSpeed);
        }
    }

    @Override
    public void dessiner(Graphics g) {
        if (!controllerMars.isOnMars()) {
            g.drawImage(getImage(), getX(), getY());
            //g.drawRect(getX(),getY(),getWidth(),getHeight());
            //g.setColor(Color.blue);
        }
    }
    
    public boolean getSplit(){
        return split;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }
    
    
    
    public void setSplit(boolean split){
        this.split = split;
    }

    @Override
    public void bouger(int limiteX, int limiteY) {
        Random rnd = new Random();
        
        if (getX() + getWidth() >= limiteX + extraSpace) {
            xSpeed *= -1;
        }

        if (getY() + getHeight() >= limiteY + extraSpace) {
            //Just TP
            //setDetruire(true);
            setLocation(largeurEcran + 150, rnd.nextInt(hauteurEcran));  
        }

        if (getX() <= -extraSpace) {
            //Just TP
            //setDetruire(true);
            setLocation(largeurEcran + 150, rnd.nextInt(hauteurEcran));
        }

        if (getY() <= -extraSpace) {
            ySpeed *= -1;
        }

        getImage().setRotation(getImage().getRotation() + 1);

        setLocation(getX() + xSpeed, getY() + ySpeed);
    }

}
