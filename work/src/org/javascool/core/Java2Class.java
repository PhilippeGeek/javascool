/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.core;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


/** Définit le mécanisme de compilation en ligne d'un code Java et du chargement de la classe obtenue. 
 * @see <a href="Java2Class.java.html">code source</a>
 * <p>Note: utilise un sous ensemble du <tt>tools.jar</tt> de la JDK appelé ici <tt>javac.jar</tt> qui doit être dans le CLASSPATH.</p>
 * @serial exclude
 */
public class Java2Class {
  // @factory
  private Java2Class() {}
  
  /** Compile dans le système de fichier local, un code source Java.
   * <p>Les fichiers <tt>.class</tt> sont générés dans sur place.</p>
   * <p>Les erreurs de compilation sont affichées dans la console.</p>
   * @param javaFile Le nom du fichier jaava à compiler.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation.
   */
  public static boolean compile(String javaFile) {
    try {
      // Initialisation des objets dy compilateur
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // The compiler tool
      DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>(); // The diagnostic colector
      StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, Locale.FRENCH, null); // The file manager
      // Mise en place des fichiers
      List<File> sourceFileList = new ArrayList<File>();
      sourceFileList.add(new File(javaFile));
      Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
      // Lancement de la compilation
      JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
      task.call();
      fileManager.close();
      // Gestion des erreurs
      
      for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
	String javaDiagnostic = diagnostic.getMessage(Locale.FRENCH);
	String jvsDiagnostic = javaDiagnostic.split(" ", 2)[1];
	if (jvsDiagnostic.equals("not a statement")) {
	  jvsDiagnostic = "L'instruction n'est pas valide.\n (Il se peut qu'une variable indiquée n'existe pas)";
	} else if (jvsDiagnostic.equals("';' expected")) {
	  jvsDiagnostic = "Un ';' est attendu (il peut manquer, ou une parenthèse être incorrecte, ..)";
	} else if (jvsDiagnostic.matches(".*\\W*found\\W*:\\W([A-Za-z\\.]*)\\Wrequired:\\W([A-Za-z\\.]*)")) {
	  jvsDiagnostic = jvsDiagnostic.replaceAll("incompatible\\Wtypes\\W*found\\W*:\\W([A-Za-z\\.]*)\\Wrequired:\\W([A-Za-z\\.]*)", 
						   "Vous avez mis une valeur de type $1 alors qu'il faut une valeur de type $2");
	} else if (jvsDiagnostic.matches("package org\\.javascool\\.proglets\\.[A-Za-z0-9_]+ does not exist")) {
	  jvsDiagnostic = jvsDiagnostic.replaceAll("package org\\.javascool\\.proglets\\.([A-Za-z0-9_]+) does not exist", 
						   "La proglet $1 n'existe pas");
	} else {
	  jvsDiagnostic = "Erreur Java : \n" + jvsDiagnostic;
	}
	int line = (int) diagnostic.getLineNumber();
	System.out.println("-------------------\nErreur lors de la compilation à la ligne " + line + ".\n" + jvsDiagnostic + "\n-------------------\n");
	// En fait ici on choisit d'arrêter à la 1ère erreur pour pas embrouiller l'apprennant
	if (diagnostic.getKind().equals(Diagnostic.Kind.ERROR)) {
	  System.out.println("-------------------\nErreur fatale lors de la compilation, arrêt de la compilation\n-------------------\n");
	  return false;
	}
      }
      return true;
    } catch (Throwable e) {
      throw new RuntimeException(e + " when compiling: " + javaFile);
    }
  }
  
  /** Charge dynamiquement une classe Java qui implémente un Runnable, pour son e×écution au cours d'une session.
   * @param path Le chemin vers la classe Java à charger. La classe ne doit pas appartenir à un package, c'est-à-dire au package "default".
   * @return Une instanciation de cette classe Java.
   *
   * @throws IllegalArgumentException Si la classe n'est pas un Runnable.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors du chargement.
   */
  public static Runnable load(String path) throws Throwable {
    try {
      File javaClass = new File(path);
      URL[] urls = new URL[]{new URL("file:" + javaClass.getParent() + File.separator)};
      Class< ?> j_class = new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.java", ""));
      Object o = j_class.newInstance();
      if (!(o instanceof Runnable)) {
	throw new IllegalArgumentException("Erreur: chargement d'une classe qui n'est pas un Runnable");
      }
      return (Runnable) o;
    } catch (Throwable e) {
      throw new RuntimeException("Erreur: impossible de charger la class");
    }
  }
}

