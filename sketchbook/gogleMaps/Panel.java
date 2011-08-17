/*******************************************************************************
* David.Pichardie@inria.fr, Copyright (C) 2011.           All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.gogleMaps;

// Used to define the gui
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.javascool.macros.Macros;

/** Définit une proglet javascool qui permet de tracer des chemins sur une carte de France.
 *
 * @see <a href="GogleMap.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;
  // @bean
  public Panel() {
    add(main = new GogleMapPanel());
    Functions.voisins = main.arcs;
    Functions.latitudes = main.latitudes;
    Functions.longitudes = main.longitudes;
  }
  GogleMapPanel main;

  /** Démo de la proglet. */
  public void start() {
    Macros.message("Pour la démo . . utiliser les boutons \"Parcours en profondeur\" +  \"Parcours en largeur\" !");
  }
}
