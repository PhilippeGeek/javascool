package org.javascool;

import org.javascool.builder.ProgletsBuilder;
import org.javascool.tools.ErrorCatcher;
import org.javascool.widgets.MainFrame;

/** Lanceur de l'application "formateur" qui permet de construire des «proglets».
 *
 * @see <a href="Build.java.html">code source</a>
 * @serial exclude
 */
public class Build {

    /** Logo de l'application. */
    public static final String logo = "org/javascool/widgets/icons/logo-builder.png";

    /** Lanceur de la conversion Jvs en Java.
     * @param usage <tt>java org.javascool.Build  [-q [targetDir]]</tt>
     */
    public static void main(String[] usage) {
        if ((usage.length > 0) && (usage[0].equals("-h") || usage[0].equals("-help") || usage[0].equals("--help"))) {
            System.out.println("Java's Cool Builder - Construit un jar avec les proglets souhaitées");
            System.out.println("Usage : java -jar javascool-builder.jar [-q [targetDir]]");
            System.out.println("Options : ");
            System.out.println("\t-q\tPermet de lancer l'application en console sur toutes les proglets disponibles et sans interface graphique.");
            System.out.println("\ttargetDir\tLe répertoire cible dans lequel la construction se fait (ou un répertoire temporaire par défaut).");
            System.exit(0);
        }
        ErrorCatcher.checkJavaVersion(6);
        Core.setUncaughtExceptionAlert();
        MainFrame.getFrame();
        if (usage.length > 0 && usage[0].equals("-q")) {
            if (usage.length > 1) {
                System.exit(ProgletsBuilder.build(ProgletsBuilder.getProglets(), usage[1]) ? 0 : -1);
            } else {
                System.exit(ProgletsBuilder.build() ? 0 : -1);
            }
        } else {
            org.javascool.builder.DialogFrame.startFrame();
        }
    }
}
