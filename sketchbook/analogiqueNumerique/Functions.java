package org.javascool.proglets.analogiqueNumerique;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JApplet;


public class Functions {
  public static ProgletPanel panel;

  /** Applique une tension en sortie.
   * @param value La tension en milli-volts entre 0 et 1023.
   */
  static public void convaOut(int value) {
    panel.value.setValue(value);
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
