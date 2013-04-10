package org.javascool.proglets.codagePixels;

import static org.javascool.proglets.codagePixels.Functions.*;
import static org.javascool.macros.Macros.*;

/** Démonstration de la proglet. 
 * @see <a href="Demo.java.html">code source</a>
 */
public class Demo {
  /** Lance la démo de la proglet. */
  public static void start() {
    for(int size = 256; size > 0; size /= 2) {
      reset(size, size);
      peace(size);
      sleep(1000 - size);
    }
  }
  /** Trace le signe de la paix dans l'image. */
  public static void peace(int radius) {
    circle(radius);
    for(int y = 0; y <= radius; y++) {
      setPixel(0, -y, "black");
      if(y < Math.rint(1 / Math.sqrt(2) * radius)) {
        setPixel(y, y, "black");
        setPixel(-y, y, "black");
      }
    }
  }
  /** Trace un disque circulaire au centre de l'image. */
  public static void circle(int radius) {
    for(int x = 0; x <= radius; x++)
      for(int y = 0; y <= radius; y++)
        if(radius * radius - x * x - y * y <= 1) {
          setPixel(x, y, "black");
          setPixel(x, -y, "black");
          setPixel(-x, y, "black");
          setPixel(-x, -y, "black");
        }
  }
}

