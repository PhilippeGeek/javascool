/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
*******************************************************************************/

package org.javascool.tools;

// Used for URL formation
import java.net.URL;
import org.javascool.macros.Macros;
import java.io.File;
import java.io.IOException;

// Used for URL read
import java.io.InputStreamReader;
import java.io.BufferedReader;

// Used for URL write
import java.net.URLConnection;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

// Used for list/exists
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Enumeration;

/** Met à disposition des fonctions de gestion de fichiers locaux et distants.
 * <p>Lit/Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.</p>
 *
 * @see <a href="FileManager.java.html">code source</a>
 * @serial exclude
 */
public class FileManager {
  // @factory
  private FileManager() {}

  /** Lit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
   *
   * @param location Une URL (Universal Resource Location) de la forme: <div id="load-format"><table align="center">
   * <tr><td><tt>http:/<i>path-name</i></tt></td><td>pour aller chercher le contenu sur un site web</td></tr>
   * <tr><td><tt>http:/<i>path-name</i>?param_i=value_i&amp;..</tt></td><td>pour le récupérer sous forme de requête HTTP</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour le charger du système de fichier local ou en tant que ressource Java dans le CLASSPATH</td></tr>
   * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>pour le charger d'une archive
   *  <div>(exemple:<tt>jar:http://javascool.gforge.inria.fr/javascool.jar!/META-INF/MANIFEST.MF</tt>)</div></td></tr>
   * </table></div>
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static String load(String location) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(Macros.getResourceURL(location, true).openStream()), 10240);
      StringBuilder buffer = new StringBuilder();
      char chars[] = new char[10240];
      while(true) {
        int l = reader.read(chars);
        if(l == -1)
          break;
        buffer.append(chars, 0, l);
      }
      return buffer.toString();
    } catch(IOException e) { throw new RuntimeException(e + " when loading: " + location);
    }
  }
  /** Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
   *
   * @param location @optional<"stdout:"> Une URL (Universal Resource Location) de la forme: <div id="save-format"><table>
   * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>pour sauver sur un site FTP.</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour sauver dans le système de fichier local (le <tt>file:</tt> est optionnel).</td></tr>
   * <tr><td><tt>mailto:<i>address</i>?subject=<i>subject</i></tt></td><td>pour envoyer un courriel avec le texte en contenu.</td></tr>
   * <tr><td><tt>stdout:/</tt></td><td>pour l'imprimer dans la console.</td></tr>
   * </table></div>
   * @param string Le texte à sauvegarder.
   * @param backup Si true, dans le cas d'un fichier, crée une sauvegarde d'un fichier existant. Par défaut false.
   * * <p>Le fichier sauvegardé est doté d'un suffixe numérique unique.</p>
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static void save(String location, String string, boolean backup) {
    if(location.startsWith("stdout:")) {
      System.out.println("\n" + location + " " + string);
      return;
    }
    location = Macros.getResourceURL(location, false).toString();
    try {
      if(backup && !location.startsWith("file:")) throw new IllegalArgumentException("Impossible de procéder à un backup pour l'URL «" + location + "»");
      OutputStreamWriter writer = location.startsWith("file:") ? getFileWriter(location.substring(5), backup) : getUrlWriter(location);
      for(int i = 0; i < string.length(); i++)
        writer.write(string.charAt(i));
      writer.close();
    } catch(IOException e) { throw new RuntimeException(e + " when saving: " + location);
    }
  }
  /**
   * @see #save(String, String)
   */
  public static void save(String location, String string) {
    save(location, string, false);
  }
  /** Met en place le writer dans le cas d'une URL. */
  private static OutputStreamWriter getUrlWriter(String location) throws IOException {
    URL url = new URL(location);
    URLConnection connection = url.openConnection();
    connection.setDoOutput(true);
    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
    if(url.getProtocol().equals("mailto")) {
      int i = url.toString().indexOf("?subject=");
      if(i != -1)
        writer.write("Subject: " + url.toString().substring(i + 9) + "\n");
    }
    return writer;
  }
  /** Met en place le writer dans le cas d'un fichier. */
  private static OutputStreamWriter getFileWriter(String location, boolean backup) throws IOException {
    File file = new File(location), parent = file.getParentFile();
    if((parent != null) && (!parent.isDirectory()))
      parent.mkdirs();
    if(backup && file.exists()) {
      File newFile = File.createTempFile(file.getName(), "", parent);
      newFile.delete();
      file.renameTo(newFile);
    }
    return new OutputStreamWriter(new FileOutputStream(location), "UTF-8");
  }
  /** Détecte si une URL existe.
   * @param location Une URL (Universal Resource Location).
   * @return Renvoie true si l'URL existe et est lisible, false sinon.
   */
  public static boolean exists(String location) {
    if(location.matches("(ftp|http|https|jar):.*")) {
      try {
        return exists(new URL(location));
      } catch(IOException e) {
        return false;
      }
    } else {
      if(location.matches("file:.*"))
        location = location.substring(5);
      return new File(location).canRead();
    }
  }
  /**
   * @see #exists(String)
   */
  public static boolean exists(URL location) {
    try {
      location.openStream().close();
      return true;
    } catch(IOException e) {
      return false;
    }
  }
  /** Renvoie les fichiers d'un répertoire ou d'un jar.
   * @param folder Le nom du répertoire ou du fichier jar (fichier d'extension ".jar").
   * @param pattern Une regex qui définit le type de fichier (ex : <tt>".*\.java"</tt>). Par défaut tous les fichiers.
   * @return Une énumération des fichiers listés: le path canonique est renvoyé. Si le répertoire ou le jar ne peut être lu, renvoie une liste vide dans erreur.
   *
   * @throws IllegalArgumentException Si l'URL ne peut pas être listée.
   */
  public static String[] list(String folder, String pattern) {
    if(folder.matches("(ftp|http|https|jar):.*")) throw new IllegalArgumentException("Impossible de lister le contenu d'un URL de ce type: " + folder);
    if(folder.matches("file:.*"))
      folder = folder.substring(5);
    ArrayList<String> files = new ArrayList<String>();
    if(folder.matches(".*\\.jar")) {
      try {
        for(Enumeration<JarEntry> e = new JarFile(folder).entries(); e.hasMoreElements();) {
          String file = e.nextElement().getName();
          if((pattern == null) || file.matches(pattern))
            files.add("jar:" + folder + "!" + file);
        }
      } catch(Exception e) {}
    } else {
      try {
        for(File file : new File(folder).listFiles())
          if((pattern == null) || file.getName().matches(pattern))
            files.add(file.getCanonicalPath());
      } catch(Exception e) {}
    }
    return files.toArray(new String[files.size()]);
  }
  /**
   * @see #list(String, String)
   */
  public static String[] list(String folder) {
    return list(folder, null);
  }
  /** Crée un répertoire temporaire dans le répertoire temporaire de la machine.
   * @param prefix Prefix du répertoire.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static File createTempDir(String prefix) {
    try {
      File d = File.createTempFile(prefix, "");
      d.delete();
      d.mkdirs();
      d.deleteOnExit();
      return d;
    } catch(IOException e) { throw new RuntimeException(e + " when creating temporary directory");
    }
  }
}