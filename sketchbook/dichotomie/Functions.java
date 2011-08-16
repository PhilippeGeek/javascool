package org.javascool.proglets.dichotomie;
import static org.javascool.tools.Macros.*;

/** Définit une proglet javascool qui permet de simuler la tortue logo.
 * @see <a href="about-proglet.htm">Description</a>
 * @see <a href="the-proglet.htm">La proglet</a>
 * @see <a href="http://fr.wikipedia.org/wiki/Logo_(langage)#Primitives_graphiques">La référence du langage logo</a>
 * @see <a href="TortueLogo.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  private static Panel getPane() {
    return getProgletPane();
  }
  /** Renvoie le nombre de page du livre. */
  public static int dichoLength() {
    return getPane().pays.length;
  }
  /** Ouvre le livre à une page et compare un nom au nom affiché sur cette page.
   * @param name Le nom à comparer.
   * @param page L'index de la page, de 0 à dichoLength() exclu.
   * @return -1 si le nom se situe avant celui de la page, +1 si le nom se situe après celui de la page, 0 si il correspond à celui de la page.
   */
  public static int dichoCompare(String name, int page) {
    page = getPane().show(page);
    return noAccent(name).compareTo(noAccent(getPane().pays[page][0]));
  }
  private static String noAccent(String name) {
    return name.replaceAll("[éè]", "e").replace("É", "E").replace("Î", "I").replace("ô", "o").replace("ã", "a");
  }
}
