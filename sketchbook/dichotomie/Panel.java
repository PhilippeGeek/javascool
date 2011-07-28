import org.javascool.tools.Macros;
import org.javascool.tools.Utils;

// Used to define the gui
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.Dimension;

// Used to define an icon/label
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;

// Used to define a button
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;

public class Panel extends JApplet {
  /** Définition de l'interface graphique de la proglet. */
  public static final ProgletPanel panel = new ProgletPanel();
  
  public void init() {
  	setContentPane(panel);
 	}
}
