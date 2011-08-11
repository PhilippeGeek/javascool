package org.javascool.proglets.analogiqueNumerique;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JApplet;

import org.javascool.tools.Macros;

public class Functions {
  public static ProgletPanel panel;

  /**/ public static void test() {
    /* Méthode brute
     *  {
     *  int v = 1023; while(v >= 0) {
     *  convaOut(v);
     *  if (convaCompare() == 1) {
     *  Macros.echo("valeur = "+v);
     *  break;
     *  }
     *  v = v - 1;
     *  }
     *  }
     */
    // Méthode dichotomique
    {
      int min = 0, max = 1024;
      while(min < max - 1) {
        // Macros.echo("La valeur est comprise entre " + (min) + " et " + (max - 1));
        int milieu = (min + max) / 2;
        convaOut(milieu);
        if(convaCompare() == 1)
          min = milieu;
        else
          max = milieu;
        Macros.sleep(1000);
      }
      convaOut(min);
      convaCompare();
      Macros.echo("La valeur vaut " + min);
    }
  }
  //
  // This defines the javascool interface
  //

  /** Applique une tension en sortie.
   * @param value La tension en milli-volts entre 0 et 1023.
   */
  static public void convaOut(int value) {
    ConvAnalogique.value = value;
    panel.out.setText(value + " mV");
  }
  private static int value = 0;

  /** Compare la tension appliquée en sortie à la tension inconnue.
   * @return -1 si la tension inconnue est plus petite et 1 si elle plus grande ou égale.
   */
  public static int convaCompare() {
    int r = panel.value.getValue() < value ? -1 : 1;
    panel.cmp.setText("" + r);
    return r;
  }
}
