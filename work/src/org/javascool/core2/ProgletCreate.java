package org.javascool.core2;

import java.io.File;
import java.util.HashMap;
import org.javascool.tools.FileManager;

/** Définit le mécanisme de création d'une proglet vide dans sa version jvs5.
 * @see <a href="ProgletCreate.java.html">code source</a>
 * @serial exclude
 */
public class ProgletCreate {
  // @factory

  private ProgletCreate() {}

  /** Lanceur de la création dune proglet.
   * @param usage <tt>java org.javascool.core2.ProgletCreate progletDir</tt>
   */
  public static void main(String[] usage) {
    if (usage.length == 1) {
      String location = usage[0];
      String name = new File(location).getName();
      Proglet2Jar.checkProgletName(name);
      // Copie les fichiers exemple dans le répertoire
      {
	new File(location).mkdirs;
	for (String fileName : filePatterns.keySet()) {
	  String pathName = location + File.separator + fileName;
	  if (new File(pathName).exists()) {
	    System.out.println("le fichier " + fileName + " existe déjà, on ne le modifie pas");
	  } else {
	    FileManager.save(pathName, FileManager.load(fileName).replaceAll("@name", name), true);
	  }
	  System.out.println("La proglet «" + name + "» est crée dans " + location + ".");
	}
      }
    }
  }
  private static HashMap<String,String> filePatterns = new HashMap<String,String>();

  static {

    filePatterns.put("proglet.json", 
		     "{" +
		     "  \"title\"  : \"Exemple de «proglet»\"," +
		     "  \"author\" : \"Prenom Nom <email@serveur.com>\"," +
		     "  \"icon\"   : \"sample.png\"" +
		     "}");

    filePatterns.put("help.html", 
		     "<div><h2>\"La «proglet» @name\"</h2>" +
		     "  <div class=\"objectif\">" +
		     "  </div>" +
		     "  <div class=\"intros\">" +
		     "    <div><h3>titre item</h3>" +
		     "    </div>" +
		     "  etc.." +
		     "  </div>" +
		     "  <div class=\"works\">" +
		     "    <div><h3>titre item</h3>" +
		     "    </div>" +
		     "  etc.." +
		     "  </div>" +
		     "  <div class=\"notes\">" +
		     "    <div id=\"référence note\"><h3>titre item</h3>" +
		     "    </div>" +
		     "  etc.." +
		     "  </div>" +
		     "</div>");

    filePatterns.put("completion.json", 
		     "[" +
		     "  {" +
		     "    \"name\"  : \"nom de la complétion\"," +
		     "    \"title\" : \"description en ligne\"," +
		     "    \"code\"  : \"texte source de la complétion\"," +
		     "    \"doc\"   : \"Texte qui documente la fonction de l'on complète\"" +
		     "  } " +
		     "  // , autres keyword " +
		     "]");
		     
    filePatterns.put("Functions.java", 
		     "package org.javascool.proglets.@name;" +
		     "import static org.javascool.macros.Macros.*;" +
		     "" +
		     "/** Définit les fonctions pour manipuler la proglet «@name» (FICHIER À DÉTRUIRE SI NON UTILISÉ)." +
		     " *" +
		     " * @see <a href=\"Functions.java.html\">code source</a>" +
		     " * @serial exclude" +
		     " */" +
		     "public class Functions {" +
		     "  private static final long serialVersionUID = 1L;" +
		     "  // @factory" +
		     "  private Functions() {}" +
		     "  /** Renvoie l'instance de la proglet pour accéder à ses éléments." +
		     "   * <p> Utilisé dans une construction de type <tt>getPane().appelDeMethode(..)</tt>.</p>" +
		     "   */" +
		     "  private static Panel getPane() {" +
		     "     return org.javascool.macros.Pane.getProgletPane();" +
		     "  }" +
		     "" +
		     "  //@todo Définir ici les fonctions <tt>public static</tt>" +
		     "" +
		     "}");

    filePatterns.put("Panel.java", 
		     "package org.javascool.proglets.@name;" +
		     "import static org.javascool.macros.Macros.*;" +
		     "import static org.javascool.proglets.@name.Functions.*;" +
		     "import javax.swing.JPanel;" +
		     "" +
		     "/** Définit le panneau graphique de la proglet «@name» (FICHIER À DÉTRUIRE SI NON UTILISÉ)." +
		     " *" +
		     " * @see <a href=\"Panel.java.html\">code source</a>" +
		     " * @serial exclude" +
		     " */" +
		     "public class Panel extends JPanel /* ou tout autre Component pertinent. */ {" +
		     "  private static final long serialVersionUID = 1L;" +
		     "" +
		     "  // @bean" +
		     "public Panel() {  // @todo Définir ici la construction de l'objet graphique" +
		     "  }" +
		     "" +
		     "}");

    filePatterns.put("Translator.java", 
		     "package org.javascool.proglets.@name;" +
		     "" +
		     "/** Définit la traduction d'un code Jvs en code Java  pour manipuler la proglet «@name» (FICHIER À DÉTRUIRE SI NON UTILISÉ)." +
		     " *" +
		     " * @see <a href=\"Translator.java.html\">code source</a>" +
		     " * @serial exclude" +
		     " */" +
		     "public class Translator extends org.javascool.core.Translator {" +
		     "    @Override" +
		     "     public String getImports() {" +
		     "    return \"\";" +
		     "  }" +
		     "    @Override" +
		     "  public String translate(String code) {" +
		     "    return code;" +
		     "  }" +
		     "}");
  }
}
