package ca.qc.bdeb.info203.SSTemp.vue.res;

import org.lwjgl.util.Rectangle;

/**
 * Objet mobile du jeu.
 *
 * @author Manuel Ramirez, Alexis Provost
 * @author Mathieu Grenon, Stéphane Lévesque
 */
public interface Mobile {

    /**
     * Déplace l'entité mobile.
     *
     * @param limiteX Limite horizontale.
     * @param limiteY Limite verticale.
     */
    public void bouger(int limiteX, int limiteY);

    /**
     * La hitbox de l'object mobile
     * 
     * @return Rectangle qui sert de hitbox
     */
    public Rectangle getRectangle();
}
