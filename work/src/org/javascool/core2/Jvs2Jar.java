package org.javascool.core2;

/** Définit le mécanisme de compilation en ligne d'un programme javasccol d'une proglet donnée dans sa version jvs5.
 * - Attention il faut que la proglet ait été convertie en jvs5 (conversion des docs XML en HTML, du fichier de méta-donnée en .json).
 * @see <a href="Jvs2Jar.java.html">code source</a>
 * @serial exclude
 */
public class Jvs2Jar {
  // @factory
  private Jvs2Jar() {}

  /** Compile sous forme de jar un programme javasccol d'une proglet donnée.
   * <p>Les erreurs de compilation ou de construction s'affichent dans la console.</p>
   * @param jarFile La jarre de stockage du résultat.
   * @param progletJar La jarre de stockage de la proglet.
   * @param jvsFile Le fichier source en .jvs.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation ou construction.
   */
  public static boolean build(String jarFile, String progletJar, String jvsFile) {
    // Expansion du jar 

    // Creation du fichier java

    // Compilation du source

    // Creation du jar


    return false;
  }
  /** Lanceur de la conversion Jvs en Java.
   * @param usage <tt>java org.javascool.core2.Jvs2Jar jarFile progletJar jvsFile</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length == 3) {
      build(usage[0], usage[1], usage[2]);
    }
  }
}

