/*******************************************************************************
*           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
*******************************************************************************/

package org.javascool;

import org.javascool.gui.Desktop;
import org.javascool.macros.Macros;
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
  /** Aide de JVS */
  public static final String help = "org/javascool/macros/memo-macros.htm";
  /** Numéro de révision de l'application.*/
  public static final String revision = "4.0.704"; // @revision automatiquement mis à jour par ant -f work/build.xml classes

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
  /** Mets en place le système d'alerte en cas d'erreur non gérée. */
  static void setUncaughtExceptionAlert() {
    ErrorCatcher.setUncaughtExceptionAlert("<h1>Détection d'une anomalie liée à Java:</h1>\n" +
                                           "Il y a un problème de compatibilité avec votre système, nous allons vous aider:<ul>\n" +
                                           "  <li>Copier/Coller tous les éléments de cette fenêtre et</li>\n" +
                                           "  <li>Envoyez les par mail à <b>javascool@googlegroups.com</b> avec toute information utile.</li>" +
                                           " </ul>",
                                           revision);
  }
  /** Retrouve le chemin du jar courant.
   * @return Le chemin du jar
   * @throws RuntimeException lorsque l'application n'a pas été démarré depuis un jar
   */
  public static String javascoolJar() {
    String jar = "";
    jar = Macros.getResourceURL("org/javascool/Core.class").toString().replace("%20", " ").replaceFirst("jar:file:([^!]*)!.*", "$1");
    if(jar.endsWith(".jar"))
      return jar;
    else throw new RuntimeException("Java's cool n'a pas été démarré depuis un Jar");
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
    setUncaughtExceptionAlert();
    Desktop.getInstance().getFrame();
  }
}
