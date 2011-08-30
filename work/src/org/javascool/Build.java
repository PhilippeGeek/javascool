package org.javascool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            System.out.println("\t-v\tPermet de lancer l'application en mode verbose, toute les étapes sont affiché.");
            System.out.println("\t-w\tPermet de lancer l'application en console et génère les fichiers jaavadoc et jars des proglets.");
            System.out.println("\ttargetDir\tLe répertoire cible dans lequel la construction se fait (ou un répertoire temporaire par défaut).");
            System.exit(0);
        }
        ErrorCatcher.checkJavaVersion(6);
        Core.setUncaughtExceptionAlert();
        MainFrame.getFrame();
        ArrayList<String> o = new ArrayList<String>();
        o.addAll(Arrays.asList(usage));
        boolean makewebproglets = o.contains("-w") || o.contains("--web");
        boolean nographics = o.contains("-q") || o.contains("--quiet") || o.contains("--q") || makewebproglets;
        boolean verbose = o.contains("-v") || o.contains("--verbose");
        String path = null;
        if (o.size() > 0) {
            if (!((String) o.toArray()[o.size() - 1]).startsWith("-")) {
                try {
                    path = new File((String) o.toArray()[o.size() - 1]).getCanonicalPath();
                    if (new File(".").getCanonicalPath().equals(path)) {
                        System.out.println("Le répertoire des proglets et celui du build ne peuvent pas être identiques");
                        throw new IllegalArgumentException("Le répertoire des proglets et celui du build ne peuvent pas être identiques");
                    }
                } catch (IOException ex) {
                    throw new IllegalArgumentException((String) o.toArray()[0] + " not found, or error when try open");
                }
                nographics = true;
            }
        }
        ProgletsBuilder.setVerbose(verbose);
        if (nographics) {
            System.exit(ProgletsBuilder.build(path, makewebproglets) ? 0 : -1);
        } else {
            org.javascool.builder.DialogFrame.startFrame();
        }
    }
}
