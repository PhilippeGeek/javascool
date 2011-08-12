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

public class Panel extends JApplet {
  public void init() {
    setContentPane(Functions.panel = new ProgletPanel());
  }
  public void start() {
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
        Functions.convaOut(milieu);
        if(Functions.convaCompare() == 1)
          min = milieu;
        else
          max = milieu;
        Macros.sleep(1000);
      }
      Functions.convaOut(min);
      Functions.convaCompare();
      Macros.echo("La valeur vaut " + min);
  }
  }
}
