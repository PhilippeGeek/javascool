package org.javascool.proglets.ticTacToe;
import static org.javascool.macros.Macros.*;
import static org.javascool.proglets.ticTacToe.Functions.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;

/** Définit le panneau graphique de la proglet.
 * @see <a href="Panel.java.html">source code</a>
 * @serial exclude
 */
public class Panel extends JPanel {

  // Construction de la proglet
  public Panel() {
    Font f = new Font("Dialog", Font.PLAIN, 72);
    setLayout(new GridLayout(3, 3));
    for (int i = 1 ; i < 4 ; i++) {
       for (int j = 1 ; j < 4 ; j++){
         tictac[i][j] = new JButton(".");
         tictac[i][j].setEnabled(false);
         tictac[i][j].setFont(f);
         add(tictac[i][j]);
       }
    }
    
  }
  // Tableau de bouton permettant de créer la grille de jeu

  public static JButton  tictac[][] = new JButton[4][4];

  /** Démo de la proglet. */
  public static void start() {

  }
 
}

