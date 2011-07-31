/*********************************************************************************
 * Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
 * Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
 *********************************************************************************/

package org.javascool.core;

import java.awt.Frame;
import java.awt.Container;
import org.javascool.widgets.HtmlDisplay;


/** Définit les mécanismes de compilation, exécution, gestion de proglet JavaScool. 
 * @see <a href="Engine.java.html">code source</a>
 * @serial exclude
 */
public class Engine {

  // @bean
  public Engine() {}

  /** Mécanisme de compilation du fichier Jvs. */
  public void doCompile() {
    throw new RuntimeException("Non implémenté");
  }

  /** Mécanisme de lancement du programme compilé. */
  public void doRun() {
    throw new RuntimeException("Non implémenté");
  }

  /** Mécanisme de chargement d'une proglet.
   * @param proglet Le nom de la proglet.
   * @return La proglet en fonctionnement.
   * @throws IllegalArgumentException Si le proglet n'exite pas.
   */
  public Proglet setProglet(String proglet) {
    throw new RuntimeException("Non implémenté");
  }

  /** Renvoie la proglet en cours de fonction.
   * @return La proglet en fonctionnement.
   */
  public Proglet getProglet() {
    throw new RuntimeException("Non implémenté");
  }
}
