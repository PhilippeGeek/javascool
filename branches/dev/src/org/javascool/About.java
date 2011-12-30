package org.javascool;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.javascool.macros.Macros;

/** Définit le panneau de "about" de javascool et son bouton d'ouverture.
 */
public class About {

  /** Titre de l'application. */
  public static final String title = "Java's Cool 4";
  /** Logode l'application. */
  public static final String logo = "org/javascool/widgets/icons/logo.png";

  /** Numéro de révision de l'application.*/
  public static final String revision = "4.0.0"; // @revision automatiquement mis à jour par ant -f work/build.xml classes


  /** Affiche le message de "about". */
  public static void showAboutMessage() {
    Macros.message(title + " ("+revision+") est un logiciel conçu par : <br/><center>"
                   + "Philippe VIENNE<br/>"
                   + "Guillaume MATHERON<br/>"
                   + " et Inria<br/>"
                   + "</center>"
		   + "en collaboration avec David Pichardie, Philippe Lucaud, etc.. et le conseil de Robert Cabane<br/><br/>"
                   + "Il est distribué sous les conditions de la licence CeCILL et GNU GPL V3<br/>", true);
  }

  /** Renvoie une bouton (sous forme de logo) qui affiche le panneau de about lors de son clic. */
  public static JLabel getAboutMessage()  {
	  ImageIcon ic=Macros.getIcon(logo);
	  Image img = ic.getImage();  
	  Image newimg = img.getScaledInstance(30,30,  java.awt.Image.SCALE_SMOOTH);  
	  ic = new ImageIcon(newimg);  
	  
        JLabel logoLabel = new JLabel(ic);
        logoLabel.setPreferredSize(new Dimension(30, 30));
        logoLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showAboutMessage();
            }
        });
        return logoLabel;
  }
}
