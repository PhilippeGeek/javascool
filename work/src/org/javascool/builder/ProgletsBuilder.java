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
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import org.javascool.Core;

/** Cette factory contient les mécanismes de construction d'un application Java's Cool avec des proglets.
 *
 * @see <a href="ProgletsBuilder.java.html">code source</a>
 * @serial exclude
 */
public class ProgletsBuilder {
  // @factory
  private ProgletsBuilder() {}

  /** Teste si cette version de Java'sCool a la capacité de créer des jar.  */
  public static boolean hasProglets() {
    try {
      Class.forName("com.icl.saxon.TransformerFactoryImpl");
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
   * @param targetDir Le répertoire cible dans lequel la construction se fait. Si null utilise un répertoire temporaire.
   * @return La valeur true si la construction est sans erreur, false sinon.
   */
  public static boolean build(String[] proglets, String targetDir) {
    if(!hasProglets()) throw new IllegalArgumentException("Mauvaise configuration du builder, il faut utiliser le bon jar !");
    try {
      System.out.println("Scan des proglets à partir du répertoire: " + System.getProperty("user.dir"));
      // Installation du répertoire de travail.
      File buildDir;
      String tmpDir, jarDir, progletsDir;
      // Création d'un répertoire cible.
      if(targetDir == null) {
        buildDir = File.createTempFile("build", "");
        buildDir.deleteOnExit();
        buildDir.delete();
        buildDir.mkdirs();
      } else {
        buildDir = new File(targetDir);
        buildDir.mkdirs();
      }
      // Création du répertoire de travail.
      {
        tmpDir = buildDir + File.separator + "tmp";
        new File(tmpDir).mkdirs();
      }
      // Création du répertoire cible
      {
        jarDir = buildDir + File.separator + "jar";
        progletsDir = jarDir + File.separator + "org" + File.separator + "javascool" + File.separator + "proglets";
        new File(progletsDir).mkdirs();
      }
      DialogFrame.setUpdate("Installation 1/2", 10);
      // Expansion des classes javascool et des proglets existantes dans les jars
      {
        String javascoolJar = Macros.getResourceURL("org/javascool/builder/ProgletsBuilder.class").toString().replaceFirst("jar:file:([^!]*)!.*", "$1");
        jarExtract(javascoolJar, jarDir, "org/javascool");
        jarExtract(javascoolJar, jarDir, "org/fife");
        for(String jar : StringFile.list(System.getProperty("user.dir"), ".*\\.jar"))
          if(!jar.matches(".*/javascool-proglets.jar"))
            jarExtract(jar, jarDir, "org/javascool/proglets");
      }
      DialogFrame.setUpdate("Installation 2/2", 20);
      // Construction des proglets
      for(String proglet : proglets) {
        String name = proglet.replaceFirst(".*/", ""), progletDir = progletsDir + File.separator + name;
        System.out.println("\tCompilation de " + name + " ...");
        DialogFrame.setUpdate("Construction de " + name + " 1/4", 30);
        // Copie de tous les fichiers
        {
          new File(progletDir).mkdirs();
          copyFiles(proglet, progletDir);
        }
        DialogFrame.setUpdate("Construction de " + name + " 2/4", 40);
        // Vérification des spécifications
        {
          boolean error = false;
          Pml pml = new Pml().load(progletDir + File.separator + "proglet.pml");
          if(!name.matches("[a-z][a-zA-Z][a-zA-Z][a-zA-Z]+")) {
            System.out.println("Le nom de la proglet «" + name + "» est bizarre il ne doit contenir que des lettres faire au moins quatre caractères et démarrer par une minuscule");
            error = true;
          }
          if(!StringFile.exists(progletDir + File.separator + "help.xml")) {
            System.out.println("Pas de fichier d'aide pour " + name + ", la proglet ne sera pas construite.");
            error = true;
          }
          if(!pml.isDefined("author")) {
            System.out.println("Le champ «author» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
            error = true;
          }
          if(!pml.isDefined("title")) {
            System.out.println("Le champ «title» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
            error = true;
          }
          pml.save(progletDir + File.separator + "proglet.php");
          if(error) throw new IllegalArgumentException("La proglet ne respecte pas les spécifications");
        }
        // Traduction Hml -> Htm des docs
        {
          for(String doc : StringFile.list(progletDir, ".*\\.xml"))
            // @todo ici il faut remplacer le xslt par un fichier du tmp !!
            StringFile.save(doc.replaceFirst("\\.xml", "\\.htm"), Xml2Xml.run(StringFile.load(doc), "../work/src/org/javascool/builder/hdoc2htm.xslt"));
          // jarDir+ "/org/javascool/builder/hdoc2htm.xslt"));
        }
        DialogFrame.setUpdate("Construction de " + name + " 3/4", 50);
        // Lancement de la compilation dans le répertoire
        {
          String[] javaFiles = StringFile.list(progletDir, ".*\\.java");
          if(javaFiles.length > 0)
            javac(javaFiles);
        }
        DialogFrame.setUpdate("Construction de " + name + " 4/4", 60);
      }
      DialogFrame.setUpdate("Finalisation 1/2", 90);
      // Création de l'archive et du manifest
      {
        String version = "Java'sCool v4 on \"" + new Date() + "\" Revision #" + Core.revision;
        Pml manifest = new Pml().
                       set("Main-Class", "org.javascool.Core").
                       set("Manifest-version", version).
                       set("Created-By", "inria.fr (javascool.gforge.inria.fr) ©INRIA: CeCILL V2 + CreativeCommons BY-NC-ND V2").
                       set("Implementation-URL", "http://javascool.gforge.inria.fr").
                       set("Implementation-Vendor", "fuscia-accueil@inria.fr, ou=javascool.gforge.inria.fr, o=inria.fr, c=fr").
                       set("Implementation-Version", version).
                       save(tmpDir + "/manifest.jmf");
        jarCreate(System.getProperty("user.dir") + File.separator + "javascool-proglets.jar", tmpDir + "/manifest.jmf", jarDir);
        DialogFrame.setUpdate("Finalisation 2/2", 100);
      }
      rmDir(new File(tmpDir));
      System.out.println("Construction achevée avec succès");
      return true;
    } catch(Exception e) {
      System.out.println("Erreur inopinée lors de la construction (" + e.getMessage() + "): corriger l'erreur et relancer la construction");
      return false;
    }
  }
  /**
   * @see #build(String[], String)
   */
  public static boolean build(String[] proglets) {
    return build(proglets, null);
  }
  /**
   * @see #build(String[], String)
   */
  public static boolean build(String targetDir) {
    return build(getProglets(), targetDir);
  }
  /**
   * @see #build(String[], String)
   */
  public static boolean build() {
    return build(getProglets(), null);
  }
  /** Extrait une arborescence d'un jar. */
  private static void jarExtract(String jarFile, String destDir, String jarEntry) {
    /* @todo A valider
     *  JarFile j = new JarFile(jarFile);
     *  for(Enumeration<JarEntry> e: j.entries(); e.hasMoreElements()) {
     *  JarEntry f = e.nextElement();
     *  String n = f.getName();
     *  System.out.println(n);
     *  if (n.startsWith(jarEntry)) {
     *  String d = dstDir+File.separator+n;
     *  if (!new File(d).isDirectory()) {
     *  new File(d).getParent().mkdirs();
     *  copyFile(j.getInputStream(f), new FileOutputStream(d));
     *  }
     *  }
     *  }
     */
    Exec.run("unzip -q " + jarFile + " -d " + destDir + " " + jarEntry + "/**");
  }
  /** Crée un jar à partir d'une arborescence. */
  private static void jarCreate(String jarFile, String mfFile, String dir) {
    new File(jarFile).delete();

    /* @todo Remplacer l'appel systeme par une api
     */
    Exec.run("jar cfm " + jarFile + " " + mfFile + " -C " + dir + " .");
  }
  /** Copie un répertoire dans un autre en oubliant les svn. */
  private static void copyFiles(String srcDir, String dstDir) throws IOException {
    for(String s : StringFile.list(srcDir)) {
      String d = dstDir + File.separator + new File(s).getName();
      if(!new File(s).isDirectory())
        copyFile(new FileInputStream(s), new FileOutputStream(d));
      else if(!new File(s).getName().equals(".svn"))
        copyFiles(s, d);
    }
  }
  /** Copy un stream dans un autre. */
  private static void copyFile(InputStream in, OutputStream out) throws IOException {
    BufferedInputStream i = new BufferedInputStream(in, 2048);
    BufferedOutputStream o = new BufferedOutputStream(out, 2048);
    byte data[] = new byte[2048];
    for(int c; (c = i.read(data, 0, 2048)) != -1;)
      o.write(data, 0, c);
    o.close();
    i.close();
  }
  /** Lance la compilation java sur un groupe de fichiers. */
  private static void javac(String[] javaFiles) {
    if(!Java2Class.compile(javaFiles, true)) throw new IllegalArgumentException("Erreur de compilation java");
  }
  /** Détruit récursivement un fichier ou répertoire.
   * @param dir Le nom du répertoire.
   * */
  private static void rmDir(File dir) {
    for(File f : dir.listFiles())
      if(f.isDirectory())
        rmDir(f);
    dir.delete();
  }
  /* A discuter par rapport à l'ancienne implémentation.
   *  public static Boolean suppr(File r) {
   *     File[] fileList = r.listFiles();
   *     Boolean s = true;
   *     for (int i = 0; i < fileList.length; i++) {
   *         if (fileList[i].isDirectory()) {
   *             s = s && suppr(fileList[i]);
   *         }
   *         s = s && fileList[i].delete();
   *     }
   *     return s;
   *  }
   */
}
