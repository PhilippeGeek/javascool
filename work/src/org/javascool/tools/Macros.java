/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.tools;

import javax.swing.JOptionPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import java.util.Calendar;
import javax.swing.ImageIcon;

import java.net.URL;
import java.io.File;
import java.io.IOException;
import org.javascool.widgets.Console;

/** Cette factory contient des functions générales rendues visibles à l'utilisateur de proglets.
 * <p>Elle permet de définir des fonctions statiques qui seront utilisées pour faire des programmes élèves.</p>
 * <p>Elle permet aussi avoir quelques fonctions de base lors de la création de nouvelles proglets.</p>
 * @see <a href="Macros.java.html">code source</a>
 * @serial exclude
 */
public class Macros {
  // @factory
  private Macros() {}

  //
  // Macros d'entrées / sorties en lien avec la console
  //

  /** Affiche dans la console une chaîne de caractères ou la représentation textuelle d'un objet sur la console.
   * @param string La chaine ou l'objet à afficher sous sa représentation textuelle.
   */
  public static void echo(String string) {
    System.out.println(string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(int string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(double string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(boolean string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(Object string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void println(String string) {
    System.out.println(string);
  }
  /**
   * @see #echo(String)
   */
  public static void println(int i) {
    println("" + i);
  }
  /**
   * @see #echo(String)
   */
  public static void println(double d) {
    println("" + d);
  }
  /**
   * @see #echo(String)
   */
  public static void println(boolean b) {
    println("" + b);
  }
  /**
   * @see #echo(String)
   */
  public static void println(Object o) {
    println("" + o);
  }
  /** Affiche dans la console une chaîne de caractères ou la représentation textuelle d'un objet sur la console sans retour à la ligne.
   * @param string La chaine ou l'objet à afficher sous sa représentation textuelle.
   */
  public static void print(String string) {
    System.out.print(string);
    System.out.flush();
  }
  /**
   * @see #print(String)
   */
  public static void print(int i) {
    print("" + i);
  }
  /**
   * @see #print(String)
   */
  public static void print(double d) {
    print("" + d);
  }
  /**
   * @see #print(String)
   */
  public static void print(boolean b) {
    print("" + b);
  }
  /**
   * @see #print(String)
   */
  public static void print(Object o) {
    print("" + o);
  }
  /** Affiche un message dans une fenêtre présentée à l'utilisateur.
   * <p>Le message s'affiche sous une forme "copiable" pour que l'utilisateur puisse le copier/coller.</p>
   * @param text Le message à afficher.
   * @param html Mettre à true si le texte est en HTML, false sinon (valeur par défaut)
   */
  public static void message(String text, boolean html) {
    JEditorPane p = new JEditorPane();
    p.setEditable(false);
    if(html)
      p.setContentType("text/html");
    p.setText(text);
    p.setSize(800, 600);
    JOptionPane.showMessageDialog(
      org.javascool.gui.Desktop.getInstance().getFrame(),
      new JScrollPane(p),
      "Java's Cool",
      JOptionPane.PLAIN_MESSAGE);
  }
  /**
   * @see #message(String, boolean)
   */
  public static void message(String text) {
    message(text, false);
  }
  /** Lit une chaîne de caractère dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La chaîne lue.
   */
  public static String readString(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popString();
    String s = JOptionPane.showInputDialog(
      org.javascool.gui.Desktop.getInstance().getFrame(),
      question,
      "Java's cool",
      JOptionPane.PLAIN_MESSAGE);
    if((s != null) && (s.length() > 0))
      return s;
    else if((s == null) || (s.length() == 0))
      return "";
    else
      return Macros.readString(question);
  }
  /**
   * @see #readString(String)
   */
  public static String readString() {
    return Macros.readString("Entrez une chaîne :");
  }
  /** Lit un nombre entier dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static int readInteger(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popInteger();
    String s = Macros.readString(question);
    try {
      return Integer.decode(s);
    } catch(Exception e) {
      if(!question.endsWith(" (Merci d'entrer un nombre)"))
        question = question + " (Merci d'entrer un nombre)";
      if(s.equals(""))
        return 0;
      return readInteger(question);
    }
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInteger() {
    return readInteger("Entrez un nombre entier : ");
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInt(String question) {
    return readInteger(question);
  }
  /**
   * @see #readInteger(String)
   */
  public static int readInt() {
    return readInteger();
  }
  /** Lit un nombre décimal dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static double readDecimal(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popDecimal();
    String s = Macros.readString(question);
    try {
      return Double.parseDouble(s);
    } catch(Exception e) {
      if(!question.endsWith(" (Merci d'entrer un nombre)"))
        question = question + " (Merci d'entrer un nombre)";
      if(s.equals(""))
        return 0;
      return readDecimal(question);
    }
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDecimal() {
    return readDecimal("Entrez un nombre décimal : ");
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDouble(String question) {
    return readDecimal(question);
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readDouble() {
    return readDecimal();
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readFloat(String question) {
    return readDecimal(question);
  }
  /**
   * @see #readDecimal(String)
   */
  public static double readFloat() {
    return readDecimal();
  }
  /** Lit une valeur booléenne dans une fenêtre présentée à l'utilisateur.
   * @param question Une invite qui décrit la valeur à entrer (optionel).
   * @return La valeur lue.
   */
  public static boolean readBoolean(String question) {
    if(inputBuffer.isPopable())
      return inputBuffer.popBoolean();
    int r = JOptionPane.showConfirmDialog(
      org.javascool.gui.Desktop.getInstance().getFrame(),
      question,
      "Java's cool",
      JOptionPane.YES_NO_OPTION);
    switch(r) {
    case JOptionPane.OK_OPTION:
      return true;
    default:
      return false;
    }
  }
  /**
   * @see #readBoolean(String)
   */
  public static boolean readBoolean() {
    return readBoolean("Entrez une valeur booléenne (oui/non) : ");
  }
  /**
   * @see #readBoolean(String)
   */
  public static Boolean readBool(String question) {
    return readBoolean(question);
  }
  /**
   * @see #readBoolean(String)
   */
  public static Boolean readBool() {
    return readBoolean();
  }
  /** Efface tout ce qui est écrit dans la console. */
  public static void clear() {
    Console.getInstance().clear();
  }
  /** Sauve ce qui est présentement écrit dans la console dans un fichier au format HTML.
   * @param location La localisation (chemin du fichier ou localisation internet) où sauver le texte.
   */
  public static void saveConsoleOutput(String location) {
    org.javascool.tools.StringFile.save(location, Console.getInstance().getText());
  }
  /** Charge une chaine de caractère pour que son contenu serve d'entrée à la console.
   * @param string La chaine de caractère à ajouter.
   */
  public static void addConsoleInput(String string) {
    inputBuffer.add(string);
  }
  /** Charge le contenu d'un fichier pour que son contenu serve d'entrée à la console.
   * @param location La localisation (chemin du fichier ou localisation internet) d'où charger le texte.
   */
  public static void loadConsoleInput(String location) {
    addConsoleInput(org.javascool.tools.StringFile.load(location));
  }
  /** Définit une zone tampon qui permet de substituer un fichier aux lectures au clavier. */
  private static class InputBuffer {
    String inputs = new String();

    /** Ajoute une chaîne en substitution d'une lecture au clavier.
     * @param string Le texte à ajouter.
     */
    public void add(String string) {
      inputs += string.trim() + "\n";
    }
    /** Teste si il y une chaîne disponible.
     * @return La valeur true si il y une entrée disponible.
     */
    public boolean isPopable() {
      return inputs.length() > 0;
    }
    /** Récupére une chaîne en substitution d'une lecture au clavier.
     * @return Le texte suivant à considérer. Ou la chaîne vide si le tampon est vide.
     */
    public String popString() {
      org.javascool.tools.Macros.sleep(500);
      int i = inputs.indexOf("\n");
      if(i != -1) {
        String input = inputs.substring(0, i);
        inputs = inputs.substring(i + 1);
        return input;
      } else
        return "";
    }
    /**
     * @see #popString(String)
     */
    public int popInteger() {
      try {
        return Integer.decode(popString());
      } catch(Exception e) {
        return 0;
      }
    }
    /**
     * @see #popString(String)
     */
    public double popDecimal() {
      try {
        return Double.parseDouble(popString());
      } catch(Exception e) {
        return 0;
      }
    }
    /**
     * @see #popString(String)
     */
    public boolean popBoolean() {
      // Renvoie vrai si [t]rue [y]es [v]rai [o]ui 1
      return popString().toLowerCase().matches("[tyvo1].*");
    }
  }
  private static InputBuffer inputBuffer = new InputBuffer();

  //
  // Macros algorithmiques (maths, etc..)
  //

  /** Renvoie un nombre entier aléatoire uniformément distribué entre deux valeurs (maximum inclus).
   */
  public static int random(int min, int max) {
    return (int) Math.floor(min + (0.999 + max - min) * Math.random());
  }
  /** Renvoie true si deux chaînes de caratères sont égales, faux sinon.
   * @param string1 L'une des chaînes à comparer.
   * @param string2 L'autre des chaînes à comparer.
   */
  public static boolean equal(String string1, String string2) {
    return string1.equals(string2);
  }
  public final static int maxInteger = Integer.MAX_VALUE;

  /** Renvoie le temps actuel en milli-secondes.
   * @return Renvoie la différence, en millisecondes, entre le temps actuel et celui du 1 Janvier 2000, minuit, en utilisant le temps universel coordonné.
   */
  public static double now() {
    return System.currentTimeMillis() - offset;
  }
  private static long offset;
  static {
    Calendar ref = Calendar.getInstance();
    ref.set(2000, 0, 1, 0, 0, 0);
    offset = ref.getTimeInMillis();
  }

  /** Temporise une durée fixée.
   * Cela permet aussi de mettre à jour l'affichage.
   * @param delay Durée d'attente en milli-secondes.
   */
  public static void sleep(int delay) {
    try {
      if(delay > 0)
        Thread.sleep(delay);
      else
        Thread.sleep(0, 10000);
    } catch(Exception e) { throw new RuntimeException("Programme arrêté !");
    }
  }
  /** Vérifie une assertion et arrête le code si elle est fausse.
   * @param condition Si la condition n'est pas vérifiée, le code JavaScool va s'arrêter.
   * @param message Un message s'imprime sur la console pour signaler l'erreur.
   */
  public static void assertion(boolean condition, String message) {
    System.err.println("#" + condition + " : " + message);
    if(!condition) {
      System.out.println(message);
      // @todo Jvs2Java.run(false);
      sleep(500);
    }
  }
  //
  // Macros pour faciliter la programmation
  //

  /** Renvoie une icone stockée dans le JAR de l'application.
   * @param path Emplacement de l'icone, par exemple <tt>"org/javascool/widget/icons/play.png"</tt>
   * @return L'icone chargée ou null si elle n'existe pas.
   */
  public static ImageIcon getIcon(String path) {
    URL icon = Thread.currentThread().getContextClassLoader().getResource(path);
    if(icon == null)
      System.err.println("Warning : getIcon(" + path + ") not found");
    return icon == null ? null : new ImageIcon(icon);
  }
  /** Ouvre une URL (Universal Resource Location) dans un navigateur extérieur.
   * @param location L'URL à afficher.
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public static void openURL(String location) {
    try {
      java.awt.Desktop.getDesktop().browse(new java.net.URI(location));
    } catch(Exception e) { throw new RuntimeException(e + " when browwing: " + location);
    }
  }
  /** Renvoie une URL (Universal Resource Location) normalisée, dans le cas du système de fichier local ou d'une ressource.
   * <p>La fonction recherche l'existence du fichier:
   * (i) par rapport au répertoire de base qui est donné,
   * (ii) par rapport au dossier de travaul "user.dir",
   * (iii) par rapport à la racine des fichier "user.home",
   * (iv) dans les ressources du CLASSPATH.</p>
   * @param location L'URL à normaliser.
   * @param base     Un répertoire de réference pour la normalisation. Par défaut null.
   * @param reading  Précise si nous sommes en lecture (true) ou écriture (false). Par défaut en lecture.
   * @throws IllegalArgumentException Si l'URL est mal formée.
   */
  public static URL getResourceURL(String location, String base, boolean reading) {
    try {
      if(location.matches("(ftp|http|https|jar|mailto|stdout):.*"))
        return new URL(location).toURI().normalize().toURL();
      if(location.startsWith("file:"))
        location = location.substring(5);
      if(reading) {
        File file = base != null ? new File(base, location) : new File(location);
        if(file.exists())
          return new URL("file:" + file.getCanonicalPath());
        file = new File(System.getProperty("user.dir"), location);
        if(file.exists())
          return new URL("file:" + file.getCanonicalPath());
        file = new File(System.getProperty("user.home"), location);
        if(file.exists())
          return new URL("file:" + file.getCanonicalPath());
        System.err.println(">1" + location);
        URL url = Thread.currentThread().getContextClassLoader().getResource(location);
        System.err.println(">1" + url);
        if(url != null)
          return url;
      }
      return new URL("file:" + (base == null ? location : base + File.separatorChar + location));
    } catch(Exception e) { throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
    }
  }
  /**
   * @see #getResourceURL(String, String, boolean)
   */
  public static URL getResourceURL(String location, String base) {
    return getResourceURL(location, base, true);
  }
  /**
   * @see #getResourceURL(String, String, boolean)
   */
  public static URL getResourceURL(String location, boolean reading) {
    return getResourceURL(location, null, reading);
  }
  /**
   * @see #getResourceURL(String, String, boolean)
   */
  public static URL getResourceURL(String location) {
    return getResourceURL(location, null, true);
  }
}
