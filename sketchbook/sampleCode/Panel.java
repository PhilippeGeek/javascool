package org.javascool.proglets.sampleCode;
import javax.swing.JApplet;
import org.javascool.tools.Macros;

public class Panel extends JApplet {
  private static ProgletPanel panel = new ProgletPanel();

  /** Constructeur par défaut */
  public Panel() {
    // Affiche panel en tant que panel principal de cette applet
    setContentPane(panel);
  }
  /** Récupérer le ProgletPanel affiché */
  public static ProgletPanel getPanel() {
    return panel;
  }
  /** Juste pour montrer que cette méthode peut être implémentée */
  public void init() {
    // On demande un texte à l'utilisateur et on l'affiche en label
    panel.setText(Macros.readString());
  }
}
