/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.builder;

import org.javascool.tools.StringFile;
import org.javascool.tools.Macros;
import org.javascool.tools.Xml2Xml;
import org.javascool.tools.Pml;
import org.javascool.core.Java2Class;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

/** Cette factory contient les mécanismes de construction d'un application Java's Cool avec des proglets.
 * @see <a href="Builder.java.html">code source</a>
 * @serial exclude
 */
public class Builder {
  // @factory
  private Builder() {}

  /** Teste si cette version de Java'sCool a la capacité de créer des jar.  */
  public static boolean hasProglets() {
    try {
      // -Class.forName("com.sun.tools.javac.Main");
      return true;
    } catch(Throwable e) {
      return false;
    }
  }
  /** Renvoie les proglets à construire. */
  public static String[] getProglets() {
    ArrayList<String> proglets = new ArrayList<String>();
    for(String dir : StringFile.list(System.getProperty("user.dir")))
      if(StringFile.exists(dir + File.separator + "proglet.pml"))
        proglets.add(dir);
    return proglets.toArray(new String[proglets.size()]);
  }
  /** Construit une nouvelle archive avec les proglets proposées.
   * @param proglets Les proglets sélectionnées. Par défaut toutes les proglets disponibles.
   */
  public static void build(String[] proglets) {
    try {
      System.out.println("Scan des proglets à partir du répertoire: " + System.getProperty("user.dir"));
      // Installation du répertoire de travail.
      File buildDir; String tmpDir, jarDir, progletsDir;
      // Création d'un répertoire temporaire.
      buildDir = File.createTempFile("build", null);
      buildDir.deleteOnExit();
      buildDir.delete();
      buildDir.mkdirs();
      // Création du répertoire de travail.
      tmpDir = buildDir + File.separator + "tmp";
      new File(tmpDir).mkdirs();
      StringFile.save(tmpDir+ "/hdoc2html.xslt", "org/javascool/buidler/hdoc2htm.xslt");
      StringFile.save(tmpDir+ "/hml2htm.xslt", "org/javascool/buidler/hml2htm.xslt");
      // Création du répertoire cible
      jarDir = buildDir + File.separator + "jar";
      progletsDir = jarDir + File.separator + "org" + File.separator + "javascool" + File.separator + "proglets";
      new File(progletsDir).mkdirs();
      DialogFrame.setUpdate("Installation 1/2", 10);
      System.out.println(Exec.run("ls\t-R\t"+buildDir));
      // Expansion des classes javascool et des proglets existantes dans les jars
      String javascoolJar = Macros.getResourceURL("org/javascool/builder/Builder.class").toString().replaceFirst("jar:([^!]*)!.*", "$1");
      jarExtract(javascoolJar, jarDir, "org.javascool");
      jarExtract(javascoolJar, jarDir, "org.fife");
      for(String jar : StringFile.list(System.getProperty("user.dir"), ".*\\.jar"))
	jarExtract(jar, jarDir, "org/javascool/proglets");
      DialogFrame.setUpdate("Installation 2/2", 20);
      // Construction des proglets
      for (String proglet: proglets) {
	String name = proglet.replaceFirst(".*/", ""), progletDir = progletsDir + File.separator + name;
	// Copie de tous les fichiers
	new File(progletDir).mkdirs();
	copyFiles(proglet, progletDir);
	// Traduction Hml -> Htm des docs
	for(String doc : StringFile.list(progletDir, ".*\\.xml"))
	  StringFile.save(doc.replaceFirst("\\.xml", "\\.htm"), Xml2Xml.run(StringFile.load(doc), tmpDir+ "/hdoc2html.xslt"));
	// Lancement de la compilation dans le répertoire
	String[] javaFiles = StringFile.list(progletDir, ".*\\.java");
	if (javaFiles.length > 0) 
	  javac(javaFiles);
      }
      // Création de l'archive
      DialogFrame.setUpdate("Finalisation 1/2", 90);
      Pml manifest = new Pml().
	set("Main-Class", "org.javascool.Core").
	save(tmpDir+"/manifest.mf");
      jarCreate(System.getProperty("user.dir") + File.separator + "javascool-proglets.jar", tmpDir+"/manifest.mf", jarDir);
      DialogFrame.setUpdate("Finalisation 2/2", 100);
      System.err.println(Exec.run("ls -R "+buildDir));
    } catch(Exception e) { 
      System.out.println("Echec lors de l'installation ("+e+") "+
			 "\n Copier/Coller tous les éléments de cette fenêtre et" + 
			 " \n Envoyez les par mail à fuscia-accueil@inria.fr avec toute information utile.");
      return;
    }

  }
  /**
   * @see #build(String[])
   */
  public static void build() {
    build(getProglets());
  }

  /** Extrait une arborescence d'un jar. */
  private static void jarExtract(String jarFile, String destDir, String jarEntry) {
    jarFile = jarFile.replaceFirst("file:", "");
    System.err.println("jar -x "+jarFile+" -C "+destDir+" "+jarEntry);
    Exec.run("jar xf "+jarFile+" -C "+destDir+" "+jarEntry);
  }

  /** Crée un jar à partir d'une arborescence. */
  private static void jarCreate(String jarFile, String mfFile, String dir) {
    jarFile = jarFile.replaceFirst("file:", "");
    new File(jarFile).delete();
    if (mfFile != null) 
      Exec.run("jar cfm "+jarFile+" "+mfFile+" -C "+dir+" .");
    else
      Exec.run("jar cfM "+jarFile+" -C "+dir+" .");
  }

  /** Copie un répertoire dans un autre. */
  private static void copyFiles(String dstDir, String srcDir) throws IOException {
    File tmpJar = File.createTempFile("copy", ".jar");
    jarCreate(tmpJar.getPath(), null, srcDir);
    jarExtract(tmpJar.getPath(), dstDir, srcDir);
    tmpJar.delete();
  }

  /** Lance la compilation java sur un groupe de fichiers. */
  private static void javac(String[] javaFiles) {
    System.err.println("javac *.java");
    //- Java2Class.compile(javaFiles);
  }

  /** Détruit récursivement un fichier ou répertoire.
   * @param dir Le nom du répertoire.
   * /
  private static void rmDir(File dir) {
    for (File f : dir.listFiles())
      if (f.isDirectory())
	rmDir(f);
    dir.delete();
  }
  /* A discuter par rapport à l'ancienne implémentation.
    public static Boolean suppr(File r) {
        File[] fileList = r.listFiles();
        Boolean s = true;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                s = s && suppr(fileList[i]);
            }
            s = s && fileList[i].delete();
        }
        return s;
    }
  */
}
