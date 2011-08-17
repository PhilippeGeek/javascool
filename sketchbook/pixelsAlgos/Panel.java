/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.pixelsAlgos;

import static org.javascool.proglets.pixelsAlgos.Functions.*;
import org.javascool.macros.Macros;
import org.javascool.widgets.IconOutput;

/** Définit une proglet qui permet de manipuler les pixels d'une image.
 *
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends IconOutput {
  private static final long serialVersionUID = 1L;
  // @bean
  public Panel() {}

  /** Démo de la proglet. */
  public static void start() {
    for(int size = 256; size > 0; size /= 2) {
      smileyReset(size, size);
      peace(size);
      Macros.sleep(1000 - size);
    }
  }
  /** Trace le signe de la paix dans l'image. */
  static private void peace(int radius) {
    circle(radius);
    for(int y = 0; y <= radius; y++) {
      smileySet(0, -y, "black");
      if(y < Math.rint(1 / Math.sqrt(2) * radius)) {
        smileySet(y, y, "black");
        smileySet(-y, y, "black");
      }
    }
  }
  /** Trace un disque circulaire au centre de l'image. */
  static private void circle(int radius) {
    for(int x = 0; x <= radius; x++)
      for(int y = 0; y <= radius; y++)
        if(radius * radius - x * x - y * y <= 1) {
          smileySet(x, y, "black");
          smileySet(x, -y, "black");
          smileySet(-x, y, "black");
          smileySet(-x, -y, "black");
        }
  }
}
