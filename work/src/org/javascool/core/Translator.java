package org.javascool.core;

/** Définit une traduction d'un code Jvs en code Java standard.
 * <p>Cette classe permet de définir des variantes de langage pour une proglet donnée.</p>
 *
 * @see <a href="Translator.java.html">code source</a>
 * @serial exclude
 */
public class Translator {
  /** Renvoie les déclarations d'import spécifiques à cette proglet.
   * <p>Ce sont les déclarations d'import spécifiques dont l'utilisateur a besoin pour que son code Jvs puisse se compiler.</p>
   * <p>- Par exemple: <tt>"import javax.swing.JPanel;import static org.javascool.proglets.maProglet.*;"</tt></p>
   * <p>Note: tous les imports liés aux fonctions de la proglet à l'usage des macros de JavaScool etc.. sont automatiquement prises en charge.</p>
   * @return Renvoie les imports en syntaxe Java (par défaut la chaîne vide).
   */
  public String getImports() {
    return "";
  }
  /** Transforme globalement le code pour passer des constructions spécifiques à Jvs à du java standard.
   * <p>Ce sont souvent des expression régulières appliquées à la chaîne, tout est ici de la responsabilité du concepteur de la proglet.</p>
   * <p>Note: toutes les traductions standard du passage de Jvs à Java sont automatiquement prises en charges.</p>   
   * <p>Les portions de code de la forme <tt>/* <i>code-jvs</i> @&lt;nojavac*</tt><tt>/<i>code-java-derive</ii>/*@nojavac>*</tt><tt>/</tt> issus de pseudo-code retraduit en Java par la méthode translate sont traités pour que seul le <i>code-jvs</i> soit affiché en cas d'erreur de syntaxe.</p>
   * @param code Le code Jvs en entrée.
   * @return Le code transformé en Java pour ce qui est spécifique de cette proglet (par défaut la chaîne en entrée).</p>
   */
  public String translate(String code) {
    return code;
  }
}
