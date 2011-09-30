package org.javascool.proglets.commSerie;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/** Définit une proglet javascool qui permet d'utiliser toute les classes des swings.
 *
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;
  public Panel() {
     setLayout(new BorderLayout());
     removeAll();
  }
  public void removeAll() {
    super.removeAll();
     add(spanel = new SerialInterfacePanel(), BorderLayout.NORTH);
     serial = spanel.getSerialInterface();
  }
  SerialInterfacePanel spanel;
  SerialInterface serial;
}
