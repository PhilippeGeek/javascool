/*********************************************************************************
 * Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
 * Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
 *********************************************************************************/
package org.javascool.builder;

import org.javascool.tools.FileManager;
import org.javascool.tools.Xml2Xml;
import org.javascool.tools.Pml;
import org.javascool.core.Java2Class;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import org.javascool.Core;

/** Cette factory contient les mécanismes de construction d'une application Java's Cool avec des proglets.
 *
 * @see <a href="ProgletsBuilder.java.html">code source</a>
 * @serial exclude
 */
public class ProgletsBuilder {
  /** Définit le file separator dans une expression régulière. */
  private static final String fileRegexSeparator = File.separator.equals("\\") ? "\\\\" : File.separator;
    
  // @factory

  private ProgletsBuilder() {
  }

  /** Teste si cette version de Java'sCool a la capacité de créer des jar.  */
  public static boolean canBuildProglets() {
    try {
      Class.forName("com.icl.saxon.TransformerFactoryImpl");
      return true;
    } catch (Throwable e) {
      return false;
    }
  }

  /** Renvoie les proglets à construire. */
  public static String[] getProglets() {
    ArrayList<String> proglets = new ArrayList<String>();
    for (String dir : FileManager.list(System.getProperty("user.dir"))) {
      if (FileManager.exists(dir + File.separator + "proglet.pml")) {
	proglets.add(dir);
      }
    }
    return proglets.toArray(new String[proglets.size()]);
  }

  /** Construit une nouvelle archive avec les proglets proposées.
   * @param proglets Les proglets sélectionnées. Par défaut toutes les proglets disponibles.
   * @param targetDir Le répertoire cible dans lequel la construction se fait. Si null utilise un répertoire temporaire.
   * @param webdoc Si true compile la javadoc et les jars de chaque proglet (false par défaut).
   * @return La valeur true si la construction est sans erreur, false sinon.
   */
  public static boolean build(String[] proglets, String targetDir, boolean webdoc) {
    if (!canBuildProglets()) {
      throw new IllegalArgumentException("Mauvaise configuration du builder, il faut utiliser le bon jar !");
    }
    try {
      // Définition de la jarre cible.
      String targetJar = System.getProperty("user.dir") + File.separator + "javascool-proglets.jar";
      new File(targetJar).delete();
      System.out.println("Scan des proglets à partir du répertoire: " + System.getProperty("user.dir"));
      // Installation du répertoire de travail.
      File buildDir;
      String jarDir, progletsDir;
      // Création des répertoires cible.
      {
	if (targetDir == null) {
	  buildDir = new File(".build");
	} else {
	  buildDir = new File(targetDir);
	  JarManager.rmDir(buildDir);
	  buildDir.mkdirs();
	}
	jarDir = buildDir + File.separator + "jar";
	progletsDir = jarDir + File.separator + "org" + File.separator + "javascool" + File.separator + "proglets";
	new File(progletsDir).mkdirs();
      }
      DialogFrame.setUpdate("Installation 1/2", 10);
      // Expansion des classes javascool et des proglets existantes dans les jars
      {
	// Expansion des jars du sketchbook
	for (String jar : FileManager.list(System.getProperty("user.dir"), ".*\\.jar"))
	  if (!jar.matches(".*"+fileRegexSeparator+"javascool-(builder|proglets).jar"))
	    JarManager.jarExtract(jar, jarDir);
	// Expansion des jars des proglets
	for (String proglet : proglets)
	  for (String jar : FileManager.list(proglet, ".*\\.jar"))
	    JarManager.jarExtract(jar, jarDir);
	// Expansion des jars de javascool
	String javascoolJar = Core.javascoolJar();
	JarManager.jarExtract(javascoolJar, jarDir, "org/javascool");
	JarManager.jarExtract(javascoolJar, jarDir, "org/fife");
      }
      DialogFrame.setUpdate("Installation 2/2", 20);
      Integer level = 20;
      // Construction des proglets
      for (String proglet : proglets) {
	String name = new File(proglet).getName(), progletDir = progletsDir + File.separator + name;
	Pml pml = new Pml().load(proglet + File.separator + "proglet.pml");
	System.out.println("Compilation de " + name + " ...");
	if (pml.getBoolean("processing")) {
	  System.out.println("==>proglet processing (non pris en charge ici: à suivre !)");
	} else {
	  DialogFrame.setUpdate("Construction de " + name + " 1/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
	  // Copie de tous les fichiers
	  {
	    new File(progletDir).mkdirs();
	    JarManager.copyFiles(proglet, progletDir);
	  }
	  DialogFrame.setUpdate("Construction de " + name + " 2/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
	  // Vérification des spécifications
	  {
	    boolean error = false;
	    if (!(name.matches("[a-z][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]+") && name.length() <= 16)) {
	      System.out.println("Le nom de la proglet «" + name + "» est bizarre il ne doit contenir que des lettres faire au moins quatre caractères et au plus seize et démarrer par une lettre minuscule");
	      error = true;
	    }
	    if (!FileManager.exists(progletDir + File.separator + "help.xml")) {
	      System.out.println("Pas de fichier d'aide pour " + name + ", la proglet ne sera pas construite.");
	      error = true;
	    }
	    if (!pml.isDefined("author")) {
	      System.out.println("Le champ «author» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
	      error = true;
	    }
	    if (!pml.isDefined("title")) {
	      System.out.println("Le champ «title» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
	      error = true;
	    }
	    pml.save(progletDir + File.separator + "proglet.php");
	    if (error) {
	      throw new IllegalArgumentException("La proglet ne respecte pas les spécifications");
	    }
	  }
	  // Traduction Hml -> Htm des docs
	  {
	    for (String doc : FileManager.list(progletDir, ".*\\.xml"))
	      FileManager.save(doc.replaceFirst("\\.xml", "\\.htm"),
			       Xml2Xml.run(FileManager.load(doc),
					   buildDir.getPath()+File.separator+"jar"+File.separator+"org"+File.separator+"javascool"+File.separator+"builder"+File.separator+"hdoc2htm.xslt"));
	  }
	  DialogFrame.setUpdate("Construction de " + name + " 3/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
	  // Création d'une page de lancement de l'applet
	  FileManager.save(progletDir + File.separator + "applet-tag.htm",
			  "<applet width='560' height='620' code='org.javascool.widgets.PanelApplet' archive='./proglets/"+name+"/javascool-proglet-"+name+".jar'><param name='panel' value='org.javascool.proglets." + name + ".Panel'/><pre>Impossible de lancer " + name + ": Java n'est pas installé ou mal configuré</pre></applet>\n");
	  // Creation de la javadoc si option ok
	  if (webdoc) 
	    javadoc(progletDir, progletDir + File.separator + "api");
	  DialogFrame.setUpdate("Construction de " + name + " 4/4", level += (10 / proglets.length));
	}
      }
      // Lancement de la compilation de tous les java des proglets
      {
	String[] javaFiles = FileManager.list(progletsDir, ".*\\.java", 1);
	if (javaFiles.length > 0)
	  javac(javaFiles);
      }
      DialogFrame.setUpdate("Finalisation 1/2", 90);
      System.out.println("Compilation des jarres .. ");
      // Création des jarres avec le manifest
      {
	String version = "Java'sCool v4 on \"" + new Date() + "\" Revision #" + Core.revision;
	Pml manifest = new Pml().set("Main-Class", "org.javascool.Core").
	  set("Manifest-version", version).
	  set("Created-By", "inria.fr (javascool.gforge.inria.fr) ©INRIA: CeCILL V2 + CreativeCommons BY-NC-ND V2").
	  set("Implementation-URL", "http://javascool.gforge.inria.fr").
	  set("Implementation-Vendor", "fuscia-accueil@inria.fr, ou=javascool.gforge.inria.fr, o=inria.fr, c=fr").
	  set("Implementation-Version", version).
	  save(buildDir + "/manifest.jmf");
	// Création des archives pour chaque proglet
	if (webdoc)
	  for (String proglet : proglets) {
	    String name = new File(proglet).getName();
	    String javascoolPrefix = "org" + File.separator + "javascool" + File.separator;
	    String jarEntries[] = { 
	      javascoolPrefix + "Core", "org" + File.separator + "fife", 
	      javascoolPrefix + "builder", javascoolPrefix + "core", javascoolPrefix + "gui", javascoolPrefix + "macros", javascoolPrefix + "tools", javascoolPrefix + "widgets", 
	      javascoolPrefix + "proglets" + File.separator + name};
	    String tmpJar = buildDir + File.separator + "javascool-proglet-"+name+".jar";
	    JarManager.jarCreate(tmpJar, buildDir + "/manifest.jmf", jarDir, jarEntries);
	  }   
	// Elimination de la proglet sampleCode 
	JarManager.rmDir(new File(progletsDir + File.separator + "sampleCode"));
	// Création de l'archive principale
	JarManager.jarCreate(targetJar, buildDir + "/manifest.jmf", jarDir);
	// Signature et déplacement des "javascool-proglet-"+name+".jar" dans les répetoires des proglets
	if (webdoc) {
	  System.out.print("Signature des jarres: "); System.out.flush();
	  for (String proglet : proglets) {
	    String name = new File(proglet).getName();
	    String tmpJar = buildDir + File.separator + "javascool-proglet-"+name+".jar";
	    String signedJar = progletsDir + File.separator + name + File.separator + "javascool-proglet-"+name+".jar";
	    if (new File(signedJar).getParentFile().exists()) {
	      System.out.print(name+" .. "); System.out.flush();
	      String keystore = jarDir + File.separator + "org" + File.separator + "javascool" + File.separator + "builder" + File.separator + "javascool.key";
	      String args = "-storepass\tjavascool\t-keypass\tmer,d,azof\t-keystore\t"+keystore+"\t-signedjar\t"+signedJar+"\t"+tmpJar+"\tjavascool";
	      // @todo en fait bloquant !! sun.security.tools.JarSigner.main(args.split("\t")); donc remplacé par 
	      Exec.run("jarsigner\t"+args);
	    }
	  }
	  System.out.println("ok.");
	}
	DialogFrame.setUpdate("Finalisation 2/2", 100);
      }
      if (targetDir == null)
	JarManager.rmDir(buildDir);
      System.out.println("Construction achevée avec succès: «" + targetJar + "» a été créé");
      System.out.println("\tIl faut lancer «" + targetJar + "» pour tester/utiliser les proglets.");
      return true;
    } catch (Exception e) {
      e.printStackTrace(System.err);
      System.out.println("Erreur inopinée lors de la construction (" + e.getMessage() + "): corriger l'erreur et relancer la construction");
      return false;
    }
  }

  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(String[] proglets, String targetDir) {
    return build(proglets, targetDir, false);
  }


  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(String[] proglets, boolean webdoc) {
    return build(proglets, null, webdoc);
  }

  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(String targetDir, boolean webdoc) {
    return build(getProglets(), targetDir, webdoc);
  }


  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(String[] proglets) {
    return build(proglets, null, false);
  }

  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(String targetDir) {
    return build(getProglets(), targetDir, false);
  }

  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build(boolean webdoc) {
    return build(getProglets(), null, webdoc);
  }

  /**
   * @see #build(String[], String, boolean)
   */
  public static boolean build() {
    return build(getProglets(), null, false);
  }

  /** Lance la compilation java sur un groupe de fichiers. */
  private static void javac(String[] javaFiles) {
    if (!Java2Class.compile(javaFiles, true)) {
      throw new IllegalArgumentException("Erreur de compilation java");
    }
  }

  /** Construction de javadoc avec sources en java2html. */
  private static void javadoc(String srcDir, String apiDir) throws IOException {
    apiDir = new File(apiDir).getCanonicalPath();
    new File(apiDir).mkdirs();
    String files[] = FileManager.list(srcDir, ".*\\.java$");
    if (files.length > 0) {
      {
	// Construit l'appel à javadoc
	String argv = "-quiet\t-classpath\t" + Core.javascoolJar() + "\t-d\t" + apiDir
	  + "\t-link\thttp://download.oracle.com/javase/6/docs/api\t-link\thttp://javadoc.fifesoft.com/rsyntaxtextarea"
	  + "\t-public\t-author\t-windowtitle\tJava's Cool v4\t-doctitle\tJava's Cool v4\t-version\t-nodeprecated\t-nohelp\t-nonavbar\t-notree\t-charset\tUTF-8";
	for (String f : files) {
	  argv += "\t" + f;
	}
	// Lance javadoc
	try {
	  com.sun.tools.javadoc.Main.execute(argv.split("\t"));
	} catch (Throwable e) {
	  throw new IOException(e);
	}
      }
      // Construit les sources en HTML à partir de java2html
      {
	File htmlDir = new File(apiDir + files[0].replaceFirst(".*" + fileRegexSeparator + "org", File.separator + "org")).getParentFile();
	// Crée les sources à htmléiser
	for (String f : files) 
	  JarManager.copyFiles(f, apiDir + File.separator + f.replaceFirst(".*" + fileRegexSeparator + "org", "org") + ".java");
	// Lance java2html
	de.java2html.Java2HtmlApplication.main(("-srcdir\t" + apiDir).split("\t"));
	// Nettoie les sources à htmléiser
	for (String f : files)
	  new File(apiDir + File.separator + f.replaceFirst(".*" + fileRegexSeparator + "org", "org") + ".java").delete();
      }
    }
  }
}
