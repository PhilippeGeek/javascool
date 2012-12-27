/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.polyominos;

import org.dnsalias.avon.polyominos.*;
import org.javascool.macros.Macros.*;

/**
 * Les fonctions de la « proglet polyominos », accessibles depuis Javascool.
 *
 * @author Lionel Avon
 * @since 2012-06-19
 */
public class Functions {

    /**
     * See <a href="http://javascool.gforge.inria.fr/index.php?page=developers&action=doc-proglets">doc-proglets</a>.
     * @return l'instance de la proglet
     */
    private static Panel getPane() {
        return org.javascool.macros.Macros.getProgletPane();
    }

    public static void setTexte(String s) {
        getPane().setTexte(s);
        getPane().repaint();
    }

    public static void setOmbre(Ombre ombre) {
        getPane().getPlanN2JPanel().setOmbre(ombre);
        getPane().repaint();
    }

    public static Ombre getOmbre() {
        return getPane().getPlanN2JPanel().getOmbre();
    }

    public static void setAssemblage(Assemblage assemblage) {
        getPane().getPlanN2JPanel().setAssemblage(assemblage);
        getPane().repaint();
    }

    public static void setRunnableGo(Runnable r) {
        getPane().setRunnableGo(r);
    }

    public static void setRunnableMAJOmbre(Runnable r) {
        getPane().getPlanN2JPanel().setRunnableMAJOmbre(r);
    }
}

/*
    public static Runnable getRunnableMAJOmbre() {
        return getPane().getPlanN2JPanel().getRunnableMAJOmbre();
    }

    public static Runnable getRunnableGo() {
        return getPane().getRunnableGo();
    }
*/
