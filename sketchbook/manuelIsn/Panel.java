// ----------------------------------------------------------------------
// Isn.java
// ----------------------------------------------------------------------
// Julien Cervelle et Gilles Dowek, version javascool.

package org.javascool.proglets.manuelIsn;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Color;
import java.util.ArrayList;


/** Définit une proglet qui permet de manipuler les pixels d'une image.
 *
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends JComponent {
  private static final long serialVersionUID = 1L;
  // @bean
  public Panel() {
   setOpaque(true);
   setBackground(Color.WHITE);
  }
  // Définit une forme géométrique colorée
  private class ColorShape { Shape shape; Color foreground, background; }
  // Liste des formes géométriques
  private ArrayList<ColorShape> shapes = new ArrayList<ColorShape>();

  /** Initialise le graphique avec un tracé de tailles (w, h). */
  public void reset(int w, int h) {
    setPreferredSize(new Dimension(w, h));
    shapes.clear();
    repaint();
  }
  /** Ajoute une forme géométrique au graphique. */
  public void add(Shape shape, Color foreground, Color background) {
    ColorShape s = new ColorShape();
    s.shape = shape;
    s.foreground = foreground;
    s.background = background;
    shapes.add(s);
    repaint();
  }
  /** Routine interne de tracé, ne pas utiliser. */
  @Override
  public void paint(Graphics g) {
    try {
      for(ColorShape s : shapes) {
	if(s.background != null) {
	  g.setColor(s.background);
	  ((Graphics2D) g).fill(s.shape);
	}
	if(s.foreground != null) {
	  g.setColor(s.foreground);
	  ((Graphics2D) g).draw(s.shape);
	}
      }
    } catch(Exception e) {}
  }
}

