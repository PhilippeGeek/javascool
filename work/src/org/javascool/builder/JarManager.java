/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.builder;

import org.javascool.tools.FileManager;

import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/** Met à disposition des fonctions de gestion de jar et répertoires de déploiement. */
public class JarManager {
  // @factory
  private JarManager() {}

  /** Extrait une arborescence d'un jar.
   * @param jarFile Jarre dont on extrait les fichiers.
   * @param destDir Dossier où on déploie les fichiers.
   * @param jarEntry Racine des sous-dossiers à extraire. Si null extrait tout les fichiers.
   */
  public static void jarExtract(String jarFile, String destDir, String jarEntry) {
    try {
      System.out.println("Extract files from " + jarFile + " to " + destDir + ((!jarEntry.isEmpty()) ? " which start with " + jarEntry : ""));
      JarFile jf = new JarFile(jarFile);
      Enumeration<JarEntry> entries = jf.entries();
      while(entries.hasMoreElements()) {
        JarEntry je = entries.nextElement();
        if(je.getName().startsWith(jarEntry) && !je.isDirectory()) {
          File dest = new File(destDir + File.separator + je.getName());
          dest.getParentFile().mkdirs();
          copyStream(ClassLoader.getSystemResourceAsStream(je.getName()), new FileOutputStream(dest));
        }
      }
    } catch(Exception ex) { throw new IllegalStateException(ex);
    }
  }
  /**
   * @see #jarExtract(String, String, String)
   */
  public static void jarExtract(String jarFile, String destDir) {
    jarExtract(jarFile, destDir, "");
  }
  /** Crée un jar à partir d'une arborescence.
   * @param jarFile Jar à construire. Elle est détruite avant d'être crée.
   * @param mfFile Fichier de manifeste (obligatoire).
   * @param srcDir Dossier source avec les fichiers à mettre en jarre.
   */
  public static void jarCreate(String jarFile, String mfFile, String srcDir) {
    try {
      new File(jarFile).delete();
      Manifest manifest = new Manifest(new FileInputStream(mfFile));
      manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
      JarOutputStream target = new JarOutputStream(new FileOutputStream(jarFile), manifest);
      copyFileToJar(new File(srcDir), target, new File(srcDir));
      target.close();
    } catch(Exception ex) { throw new RuntimeException(ex);
    }
  }
  /** Copie un répertoire dans un autre en oubliant les svn.
   * @param srcDir Dossier source.
   * @param dstDir Dossier cible.
   */
  public static void copyFiles(String srcDir, String dstDir) throws IOException {
    for(String s : FileManager.list(srcDir)) {
      String d = dstDir + File.separator + new File(s).getName();
      if(!new File(s).isDirectory())
        copyStream(new FileInputStream(s), new FileOutputStream(d));
      else if(!new File(s).getName().equals(".svn"))
        copyFiles(s, d);
    }
  }
  // Copy un stream dans un autre

  private static void copyStream(InputStream in, OutputStream out) throws IOException {
    BufferedInputStream i = new BufferedInputStream(in, 2048);
    BufferedOutputStream o = new BufferedOutputStream(out, 2048);
    byte data[] = new byte[2048];
    for(int c; (c = i.read(data, 0, 2048)) != -1;)
      o.write(data, 0, c);
    o.close();
    i.close();
  }
  // Ajoute un stream a un jar

  public static void copyFileToJar(File source, JarOutputStream target, File root) throws IOException {
    BufferedInputStream in = null;
    try {
      if(source.isDirectory()) {
        String name = source.getPath().replace(root.getAbsolutePath() + File.separator, "").replace(File.separator, "/");
        if(!name.isEmpty() && (source != root)) {
          if(!name.endsWith("/"))
            name += "/";
          JarEntry entry = new JarEntry(name);
          entry.setTime(source.lastModified());
          target.putNextEntry(entry);
          target.closeEntry();
        }
        for(File nestedFile : source.listFiles())
          copyFileToJar(nestedFile, target, root);
        return;
      }
      JarEntry entry = new JarEntry(source.getPath().replace(root.getAbsolutePath() + File.separator, "").replace(File.separator, "/"));
      entry.setTime(source.lastModified());
      target.putNextEntry(entry);
      // ProgletsBuilder.copyStream(new BufferedInputStream(new FileInputStream(source)), target);
      // @todo a factoriser avec ccopyFile !!!
      in = new BufferedInputStream(new FileInputStream(source));
      byte[] buffer = new byte[1024];
      while(true) {
        int count = in.read(buffer);
        if(count == -1)
          break;
        target.write(buffer, 0, count);
      }
      target.closeEntry();
    } catch(Throwable e) {
      e.printStackTrace(System.out); throw new IllegalStateException(e);
    }
  }
  /** Détruit récursivement un fichier ou répertoire.
   * * <p>Irréversible: à utiliser avec la plus grande prudence.</p>
   */
  public static void rmDir(File dir) {
    for(File f : dir.listFiles())
      if(f.isDirectory())
        rmDir(f);
    dir.delete();
  }
}
