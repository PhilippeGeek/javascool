package org.javascool;

import org.javascool.tools.ErrorCatcher;

/** Lanceur de l'application "formateur" qui permet de construire des «proglets».
 *
 * @see <a href="Build.java.html">code source</a>
 * @serial exclude
 */
public class Build {
  public static void main(String[] args) {
    if((args.length > 0) && (args[0].equals("-h") || args[0].equals("-help") || args[0].equals("--help"))) {
      System.out.println("Java's Cool Builder - Construit un jar avec les proglets souhaitées");
      System.out.println("Usage : java -jar javascool-builder.jar [-q]");
      System.out.println("Options : ");
      System.out.println("\t-q\tPermet de lancer l'application en console sur toutes les proglets disponibles et sans interface graphique.");
      System.exit(0);
    }
    ErrorCatcher.checkJavaVersion(6);
    Core.setUpLookAndFeel();
    if((args.length > 0) && args[0].equals("-q"))
      System.exit(org.javascool.builder.Builder.build() ? 0 : -1);
    else
      org.javascool.builder.DialogFrame.startFrame();
  }
}
