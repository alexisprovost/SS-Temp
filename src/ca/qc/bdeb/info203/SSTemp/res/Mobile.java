package ca.qc.bdeb.info203.SSTemp.res;

import org.lwjgl.util.Rectangle;

/**
 * Objet mobile du jeu.
 * @author Mathieu Grenon, Stéphane Lévesque
 */
public interface Mobile {
    
    /**
     * Déplace l'entité mobile.
     * @param limiteX Limite horizontale.
     * @param limiteY Limite verticale.
     */
    public void bouger(int limiteX, int limiteY);
    
    public Rectangle getRectangle();
}