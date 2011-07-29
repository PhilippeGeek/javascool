/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
*******************************************************************************/

package org.javascool.tools;

// Used for URL formation
import java.net.URL;
import java.io.File;
import java.io.IOException;

// Used for URL read
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.net.URLEncoder;

// Used for URL write
import java.net.URLConnection;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.lang.System;

/** Lit/Ecrit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
 * @see <a href="StringFile.java.html">code source</a>
 * @serial exclude
 */
public class StringFile {
  // @factory
  private StringFile() {}

  /** Lit un contenu textuel local ou distant en tenant compte de l'encodage UTF-8.
   *
   * @param location Une URL (Universal Resource Location) de la forme: <table align="center">
   * <tr><td><tt>http:/<i>path-name</i></tt></td><td>pour aller chercher le contenu sur un site web</td></tr>
   * <tr><td><tt>http:/<i>path-name</i>?param_i=value_i&amp;..</tt></td><td>pour le récupérer sous forme de requête HTTP</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour le charger du système de fichier local ou en tant que ressource Java dans le CLASSPATH</td></tr>
   * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>pour le charger d'une archive
   *  <div>(exemple:<tt>jar:http://javascool.gforge.inria.fr/javascool.jar!/META-INF/MANIFEST.MF</tt>)</div></td></tr>
   * </table>
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static String load(String location) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(toUrl(location, true).openStream()), 10240);
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
   * @param location @optional<"stdout:"> Une URL (Universal Resource Location) de la forme: <table>
   * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>pour sauver sur un site FTP.</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour sauver dans le système de fichier local.</td></tr>
   * <tr><td><tt>mailto:<i>address</i>?subject=<i>subject</i></tt></td><td>pour envoyer un courriel avec le texte en contenu.</td></tr>
   * <tr><td><tt>stdout:/</tt></td><td>pour l'imprimer dans la console.</td></tr>
   * </table>
   *
   * @param string Le texte à sauver.
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static void save(String location, String string) {
    if(location.startsWith("stdout:")) {
      System.out.println("\n" + location + " " + string);
      return;
    }
    location = toUrl(location, false).toString();
    try {
      OutputStreamWriter writer = location.startsWith("file:") ? getFileWriter(location.substring(5)) : getUrlWriter(location);
      for(int i = 0; i < string.length(); i++)
        writer.write(string.charAt(i));
      writer.close();
    } catch(IOException e) { throw new RuntimeException(e + " when saving: " + location);
    }
  }
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
  private static OutputStreamWriter getFileWriter(String location) throws IOException {
    File file = new File(location), parent = file.getParentFile();
    if((parent != null) && (!parent.isDirectory()))
      parent.mkdirs();
    return new OutputStreamWriter(new FileOutputStream(location), "UTF-8");
  }

  /** Renvoie une URL normalisée, dans le cas du système de fichier local ou d'une ressource Java du CLASSPATH.
   * @param location L'URL à normaliser.
   * @param reading Précise si nous sommes en lecture (true) ou écriture (false)
   * @throws IllegalArgumentException Si l'URL est mal formée.
   */
  static URL toUrl(String location, boolean reading) {
    try {
      if(location.matches("(file|ftp|http|https|jar|mailto|stdout):.*"))
        return new URL(location);
      if (reading) {
	URL url = Thread.currentThread().getContextClassLoader().getResource(location);
	if(url != null)
	  return url;
      }
      File file = new File(location);
      if(file.exists())
        return new URL("file:" + file.getCanonicalPath());
      return new URL("file:" + location);
    } catch(IOException e) { 
      throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
    }
  }

}
