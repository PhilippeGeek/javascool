package org.javascool.core;

import java.io.File;
import org.javascool.tools.FileManager;

import org.javascool.widgets.MainFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JButton;
import org.javascool.macros.Macros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import org.javascool.tools.UserConfig;

/** Définit le mécanisme de compilation en ligne d'un programme javascool d'une proglet donnée dans sa version jvs5.
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
    System.out.println("Compilation de "+new File(jvsFile).getName()+"..");
    if (name == null)
      throw new IllegalArgumentException("Le nom de la proglet est ambigu ou indéfini");
    if (!new File(jvsFile).isFile())
      throw new IllegalArgumentException("Le fichier "+jvsFile+" n'existe pas");
    try {
      // Répertoire temporaire de compilation
      String jarDir = UserConfig.createTempDir("jvs-build-").getAbsolutePath();
      // Extraction des classes de javascool et nettoyage des classes inutiles
      JarManager.jarExtract(Utils.javascoolJar(), jarDir);
      for (String d : new String[] { 
	  "org/fife", // RSyntaxTextArea.jar
	  "com/sun/tools/javac", "com/sun/source/tree", "com/sun/source/util" // javac.jar
	})
	JarManager.rmDir(new File(jarDir + File.separator + d.replaceAll("/", File.separator)));
      // Chargement de la proglet
      Proglet proglet = new Proglet().load("org" + File.separator + "javascool" + File.separator + "proglets" + File.separator + name);
      Jvs2Java jvs2java = proglet.getJvs2java();
      // Compilation du source
      String javaCode = jvs2java.translate(FileManager.load(jvsFile), new File(jvsFile).getName().replaceFirst("\\.jvs$", ""));
      String javaFile = jarDir + File.separator + jvs2java.getClassName() + ".java";
      FileManager.save(javaFile, javaCode);
      if(!Java2Class.compile(javaFile))
	return false;
      // Construction de la jarre
      String mfData = 
	"Manifest-Version: 1.0\n" +
	"Main-Class: "+jvs2java.getClassName()+"\n" +
	"Implementation-URL: http://javascool.gforge.inria.fr\n";
      JarManager.jarCreate(jarFile, mfData, jarDir);
      JarManager.rmDir(new File(jarDir));
      System.out.println("achevée avec succès :\n Le fichier '"+jvsFile+".jar' est disponible");
      return true;
    } catch (Throwable e) {
      System.out.println(e);
      return false;
    }
  }  
  /**
   * @see #build(String, String, String)
   */
  public static boolean build(String name, String jvsFile) {
    return build(name, jvsFile, jvsFile+".jar");
  }
  /**
   * @see #build(String, String, String)
   */
  public static boolean build(String jvsFile) {
    return build(Utils.javascoolProglet(), jvsFile, jvsFile+".jar");
  }
}
