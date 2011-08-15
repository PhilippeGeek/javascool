/*******************************************************************************
*           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
*******************************************************************************/

package org.javascool;

// Used to set Win look and feel
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;

import org.javascool.gui.Desktop;
import org.javascool.tools.ErrorCatcher;

/** Lanceur de l'application "apprenant" qui permet de manipuler des «proglets».  *
 *
 * @see <a href="Core.java.html">source code</a>
 * @serial exclude
 */
public class Core {
  /** Titre de l'application. */
  public static final String title = "Java's Cool 4";
  /** Logode l'application. */
  public static final String logo = "org/javascool/widgets/icons/logo.png";
  /** Numéro de révision de l'application.*/
  public static final int revision = 362;
  // Note: Il est obtenu par la commande Unix <tt>svn info | grep Revision | sed 's/.*: //'</tt>

  /** Affiche le message de "about". */
  static void showAboutMessage() {
    org.javascool.tools.Macros.message(title + "est un logiciel conçut en colaboration avec : <br/><center>"
                                       + "Philippe VIENNE<br/>"
                                       + "Guillaume MATHERON<br/>"
                                       + " et Inria<br/>"
                                       + "</center><br/>"
                                       + "Il est distribué sous les conditions de la licence CeCILL<br/>", true);
  }
  /** Définit le look and feel de l'application. */
  static void setUpLookAndFeel() {
    try {
      for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        if("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
    } catch(Exception e1) {
      String os = System.getProperty("os.name");
      if(os.startsWith("Windows")) {
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception e2) {}
      } else {
        if(System.getProperty("os.name").toUpperCase().contains("MAC")) {
          try {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
          } catch(Exception e2) {}
        }
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e2) {
          System.err.println("Note: Utilisaton du thème Java (et non du système)");
        }
      }
    }
  }
  /** Mets en place le système d'alerte en cas d'erreur non gérée. */
  static void setUncaughtExceptionAlert() {
    ErrorCatcher.setUncaughtExceptionAlert("<h1>Détection d'une anomalie liée à Java:</h1>\n" +
                                           "Il y a un problème de compatibilité avec votre système, nous allons vous aider:<ul>\n" +
                                           "  <li>Copier/Coller tous les éléments de cette fenêtre et</li>\n" +
                                           "  <li>Envoyez les par mail à <b>fuscia-accueil@inria.fr</b> avec toute information utile.</li>" +
                                           " </ul>",
                                           revision);
  }
  /** Lanceur de l'application.
   * @param usage <tt>java -jar javascool.jar</tt>
   */
  public static void main(String[] usage) {
    if((usage.length > 0) && (usage[0].equals("-h") || usage[0].equals("-help") || usage[0].equals("--help"))) {
      System.out.println("Java's Cool Core - lance l'interface pour travailler avec les proglets");
      System.out.println("Usage : java -jar javascool.jar");
      System.exit(0);
    }
    System.err.println("" + title + " is starting ...");
    ErrorCatcher.checkJavaVersion(6);
    setUpLookAndFeel();
    // -setUncaughtExceptionAlert();
    Desktop.getInstance().getFrame();
  }
}
