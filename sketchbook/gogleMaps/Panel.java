/*******************************************************************************
* David.Pichardie@inria.fr, Copyright (C) 2011.           All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.gogleMaps;

// Used to define the gui
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Définit une proglet javascool qui permet de tracer des chemins sur une carte de France.
 * @see <a href="doc-files/about-proglet.htm">Description</a>
 * @see <a href="doc-files/the-proglet.htm">La proglet</a>
 * @see <a href="GogleMap.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;
  public Panel() {
    add(main = new GogleMapPanel());
    voisins = main.arcs;
    latitudes = main.latitudes;
    longitudes = main.longitudes;
  }
  public GogleMapPanel main;
}

