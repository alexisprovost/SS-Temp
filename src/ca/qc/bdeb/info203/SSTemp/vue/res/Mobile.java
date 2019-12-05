package ca.qc.bdeb.info203.SSTemp.vue.res;

import org.lwjgl.util.Rectangle;

/**
 * Objet mobile du jeu.
 *
 * @author Mathieu Grenon, Stéphane Lévesque, Manuel Ramirez, Alexis Provost
 */
public interface Mobile {

    /**
     * Déplace l'entité mobile.
     *
     * @param limiteX Limite horizontale.
     * @param limiteY Limite verticale.
     */
    public void bouger(int limiteX, int limiteY);

    public Rectangle getRectangle();
}
