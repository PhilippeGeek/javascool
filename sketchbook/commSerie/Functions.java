/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved . *
*******************************************************************************/

package org.javascool.proglets.commSerie;

import static org.javascool.macros.Macros.*;
import javax.swing.JPanel;

/** Définit les fonctions de la proglet qui permet d'utiliser toute les classes des swings.
 *
 * @see <a href="http://java.sun.com/docs/books/tutorial/uiswing">Java Swing tutorial</a>
 * @see <a href="http://java.sun.com/javase/6/docs/api/javax/swing/package-summary.html">Java Swing API</a>
 * @see <a href="Functions.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  /** Renvoie le panneau d'affichage de la proglet. */
  public static JPanel getPane() {
    return getProgletPane();
  }
}
