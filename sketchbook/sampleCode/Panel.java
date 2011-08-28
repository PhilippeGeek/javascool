package org.javascool.proglets.sampleCode;
import static org.javascool.macros.Macros.*;
import static org.javascool.proglets.sampleCode.Functions.*;
import javax.swing.JPanel;
import javax.swing.JLabel;

/** Définit le panneau graphique de la proglet.
 * @see <a href="Panel.java.html">source code</a>
 * @serial exclude
 */
public class Panel extends JPanel {
  // Construction de la proglet
  public Panel() {
    // On crée un label
    label = new JLabel("");
    // Et on l'ajoute au panel
    add(label);
  }
  // Ce label sera utilisé par la routine Functions.setMessage()
  JLabel label;

  /** Démo de la proglet. */
  public void start() {
    // On boucle pour 10 clignotements entre oui et non
    for(int i = 0; i < 10; i++) {
      // Si i modulo 2 == 0, donc si i est pair, on affiche OUI, sinon NON.
      setMessage(i % 2 == 0 ? "OUI" : "NON");
      // On temporise 500 msec, donc 1/2 seconde.
      sleep(500);
    }
  }
}

