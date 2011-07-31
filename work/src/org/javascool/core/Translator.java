package org.javascool.core;

/** Définit une traduction d'un code Jvs en code Java standard.
 * <p>Cette classe permet de définir des variantes de langage pour une proglet donnée.</p>
 */
public class Translator {
  /** Renvoie les déclarations d'import spécifiques à cette proglet.
   * <p>Ce sont les déclarations d'import spécifiques dont l'utilisateur a besoin pour que son code Jvs puisse se compiler.</p>
   * <p>- Par exemple: <tt>"import javax.swing.JPanel;"</tt></p>
   * <p>Note: tous les imports liés aux fonctions de la proglet à l'usage des macros de JavaScool etc.. sont automatiquement prises en charge.</p>
   * @return Renvoie les imports en syntaxe Java (par défaut la chaîne vide).
   */
  public String getImports() {
    return "";
  }
  /** Transforme globalement le code pour passer des constructions spécifiques à Jvs à du java standard.
   * <p>Ce sont souvent des expression régulières appliquées à la chaîne, tout est ici de la responsabilité du concepteur de la proglet.</p>
   * <p>Note: toutes les traductions standard du paassage de Jvs à Java sont automatiquement prises en charges.</p>
   * @param code Le code Jvs en entrée.
   * @return Le code transformé en Java pour ce qui est spécifique de cette proglet (par défaut la chaîne en entrée).</p>
   */
  public String translate(String code) {
    return code;
  }
}

