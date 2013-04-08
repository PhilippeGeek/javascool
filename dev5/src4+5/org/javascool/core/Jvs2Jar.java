package org.javascool.core;

import java.io.File;
import org.javascool.Core;
import org.javascool.tools.FileManager;

/** Définit le mécanisme de compilation en ligne d'un programme javasccol d'une proglet donnée dans sa version jvs5.
 * - Attention il faut que la proglet ait été convertie en jvs5 (conversion des docs XML en HTML, du fichier de méta-donnée en .json).
 * @see <a href="Jvs2Jar.java.html">code source</a>
 * @serial exclude
 */
public class Jvs2Jar {
  // @factory
  private Jvs2Jar() {}

  /** Compile sous forme de jar un programme javascool d'une proglet donnée.
   * <p>Les erreurs de compilation ou de construction s'affichent dans la console.</p>
   * @param name Nom de la proglet.
   * @param jvsFile Le fichier source en .jvs.
   * @param jarFile La jarre de stockage du résultat.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation ou construction.
   */
  public static boolean build(String name, String jvsFile, String jarFile) {
    System.out.println("jvs2jar -proglet "+name+" -in "+jvsFile+" -out "+jarFile);
    try {
      // Répertoire temporaire de compilation
      String jarDir = FileManager.createTempDir("jvs-build-").getAbsolutePath();
      // Extraction des classes de javascool
      JarManager.jarExtract(Core.javascoolJar(), jarDir);
      System.err.println("1>"+ Core.javascoolJar());
      // Chargement de la proglet
      Proglet proglet = new Proglet().load("org" + File.separator + "javascool" + File.separator + "proglets" + File.separator + name);
      Jvs2Java jvs2java = proglet.getJvs2java();
      System.err.println("2>"+ name);
      // Compilation du source
      String javaCode = jvs2java.translate(FileManager.load(jvsFile));
      String javaFile = jarDir + File.separator + jvs2java.getClassName() + ".java";
      System.err.println("3>"+ javaFile);
      FileManager.save(javaFile, javaCode);
      System.err.println("3>"+ javaFile);
      if(!Java2Class.compile(javaFile))
	return false;
      System.err.println("4> done");
      // Nettoyage des arborescences inutiles
      for (String d : new String[] { 
	  "com/com/sun/javadoc", "com/com/sun/tools/javadoc", "com/com/sun/tools/doclets", "com/com/sun/jarsigner",
	  "org/fife", "de/tisje", "de/java2html",
	  "org/javascool/builder",
	  "com/sun/tools/javac"
	})
	JarManager.rmDir(new File(jarDir + File.separator + d.replaceAll("/", File.separator)));
      System.err.println("5> done");
      // Construction de la jarre
      String mfData = 
	"Manifest-Version: 1.0\n" +
	"Main-Class: "+jvs2java.getClassName()+"\n" +
	"Implementation-URL: http://javascool.gforge.inria.fr\n";
      JarManager.jarCreate(jarFile, mfData, jarDir);
      System.err.println("6>" + jarFile);
      JarManager.rmDir(new File(jarDir));
      return true;
    } catch (Throwable e) {
      System.err.println(e);
      return false;
    }
  }
  /** Lanceur de la conversion Jvs en Java.
   * @param usage <tt>java org.javascool.core2.Jvs2Jar progletName jvsFile jarFile</tt>
   */
  public static void main(String[] usage) {
    System.err.println(">>"+usage.length);
    // @main
    if(usage.length == 3) {
      build(usage[0], usage[1], usage[2]);
    }
  }
}

