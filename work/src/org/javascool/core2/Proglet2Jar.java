package org.javascool.core2;

import java.io.File;
//import org.javascool.core.JarManager;
//import org.javascool.core.ProgletApplet;

/** Définit le mécanisme de compilation en ligne d'une proglet dans sa version jvs5.
 * - Attention il faut que la proglet ait été convertie en jvs5 (conversion des docs XML en HTML, du fichier de méta-donnée en .json).
 * @see <a href="Proglet2Jar.java.html">code source</a>
 * @serial exclude
 */
public class Proglet2Jar {
  // @factory
  private Proglet2Jar() {}

  /** Compile sous forme de jar une proglet donnée.
   * <p>Les erreurs de compilation ou de construction s'affichent dans la console.</p>
   * @param jarFile La jarre de stockage du résultat.
   * @param progletDir Le répertoire où se trouvent les fichiers de la proglet.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation ou construction.
   */
  public static boolean buildJar(String jarFile, String progletDir) {
    // Nom et paramètres de la proglet
    String name = new File(progletDir).getName();
    // AJOUTER new Pml().load(progletDir + File.separator + "proglet.pml");
    // Jarres à utiliser
    // Fichiers java à compiler
    
    // Expansion des jarres
    // Copie des fichiers
    // Compilation des sources

    // Création du jar
    
    // Création du html dans le même répetoire que le jar

    return false; 
  }

  // Teste la validite d'un nom de proglet.
  static void checkProgletName(String name) {
    if (!(4 <= name.length() && name.length() <= 16 && name.matches("[a-z][a-zA-Z0-9]+")))
      throw new IllegalArgumentException("le nom \""+ name + "\" est invalide,\n\t il ne doit contenir que des lettres, faire au moins quatre caractères et au plus seize et démarrer par une lettre minuscule");
  }
  
  /** Lanceur de la construction de proglet.
   * @param usage <tt>java org.javascool.core2.Proglet2Jar jarFile progletDir</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length == 2) {
      buildJar(usage[0], usage[1]);
    }
  }
}

