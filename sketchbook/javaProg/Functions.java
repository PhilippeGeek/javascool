/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved . *
*******************************************************************************/

package org.javascool.proglets.javaProg;

import static org.javascool.macros.Macros.*;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/** Définit les fonctions de la proglet qui permet d'utiliser toute les classes des swings.
 *
 * @see <a href="http://java.sun.com/docs/books/tutorial/uiswing">Java Swing tutorial</a>
 * @see <a href="http://java.sun.com/javase/6/docs/api/javax/swing/package-summary.html">Java Swing API</a>
 * @see <a href="Functions.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  /** Nettoie le panneau d'affichage  la proglet. */
  public static void removeAll() {
    getPane().removeAll();
  }
  /** Renvoie le panneau d'affichage de la proglet. */
  public static JLayeredPane getPane() {
    return getProgletPane();
  }
  /** Crée et montre une icône sur le display en (x,y) de taille (w, h) à la profondeur p.
   * @param location  L'URL (Universal Resource Location) où se trouve l'icone.
   * @param x Abcisse du coin inférieur gauche de l'image.
   * @param y Ordonnée du coin inférieur gauche de l'image.
   * @param p Profondeur du tracé de 1 le plus "profond" avec des valeurs plus grandes pour les plans de devant.
   * @return L'objet correspondant à l'icône qui peut être:
   * <p>manipuler ensuite avec la construction <tt>JLabel icon = showIcon(..);</tt></p>
   * <p>- déplacé avec la construction <tt>icon.setLocation(x, y);</tt></p>
   * <p>- rendu visible/invisible avec la construction <tt>icon.setVisible(trueOrFalse);</tt></p>
   */
  public static JLabel showIcon(String location, int x, int y, int p) {
    JLabel icon = new JLabel();
    icon.setIcon(getIcon(location));
    icon.setLocation(x, y);
    getPane().add(icon, new Integer(p), 0);
    return icon;
  }
}
