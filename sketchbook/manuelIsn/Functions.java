// ----------------------------------------------------------------------
// Isn.java
// ----------------------------------------------------------------------
// Julien Cervelle et Gilles Dowek, version javascool.

package org.javascool.proglets.manuelIsn;
import static org.javascool.macros.Macros.*;

// io
import java.util.Scanner;
import java.io.OutputStreamWriter;
// graphisme
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
// tracé de texte
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;

/** Définit les fonctions de la proglet qui permettent de faire les exercices Isn.
 *
 * @see <a href="Functions.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  // @factory
  private Functions() {}
  /** Renvoie l'instance de la proglet pour accéder à ses éléments. */
  private static Panel getPane() {
    return getProgletPane();
  }
  
  // ----------------------------------------------------------------------
  // Strings
  // ----------------------------------------------------------------------

  /** Renvoie vrai si les deux chaînes sont égales, faux sinon. */
  public static boolean stringEqual(String s1, String s2) {
    return s1.equals(s2);
  }  
  /** Renvoie vrai si la chaîne s1 précède dans l'ordre alphabétique la chaîne s2 ou si les chaînes sont égales, faux sinon. */
  public static boolean stringAlph(String s1, String s2) {
    return s1.compareTo(s2) <= 0;
  }
  /** Renvoie le n-ième caractère de la chaîne. */
  public static String stringNth(String s, int n) {
    return s.substring(n, n + 1);
  }
  /** Renvoie la longueur de la chaîne. */
  public static int stringLength(String s) {
    return s.length();
  }
  /** Renvoie le caractère de code ASCII n. */
  public static String asciiString(int n) {
    byte[] b;
    b = new byte[1];
    b[0] = (byte) n;
    return new String(b);
  }
  /** Renvoie le code ASCII du 1er caractère de la chaîne. */
  public static int stringCode(String s) {
    return (int) (s.charAt(0));
  }
  
  // ----------------------------------------------------------------------
  // io
  // ----------------------------------------------------------------------

  private static final Object readMonitor = new Object();

  private static Scanner scanner(java.io.Reader in) {
    Scanner scanner = new Scanner(in);
    scanner.useLocale(java.util.Locale.US);
    return scanner;
  }
  private static final Object writeMonitor = new Object();

  private static volatile java.io.PrintStream output = System.out;

  /** Ouvre un fichier en lecture. */
  public static Scanner openIn(String name) {
    try {
      java.io.FileInputStream fis = new java.io.FileInputStream(name);
      Scanner scanner = scanner(new java.io.InputStreamReader(fis));
      return scanner;
    } catch(java.io.FileNotFoundException e) {
      return scanner(new java.io.InputStreamReader(System.in));
    }
  }
  /** Ferme un fichier en lecture. */
  public static void closeIn(Scanner s) {
    s.close();
  }
  /** Lit un entier à partir d'un fichier. */
  public static int readIntFromFile(Scanner s) {
    synchronized (readMonitor) {
      return s.nextInt();
    }
  }
  /** Lit un nombre décimal à partir d'un fichier. */
  public static double readDoubleFromChar(Scanner s) {
    synchronized (readMonitor) { 
      return s.nextDouble();
    }
  }

  private static final java.util.regex.Pattern DOT = java.util.regex.Pattern.compile(".", java.util.regex.Pattern.DOTALL);

  /** Lit un caratère à partir d'un fichier. */
  public static String readCharacterFromFile(Scanner s) {
    synchronized (readMonitor) {
      return String.valueOf(s.findWithinHorizon(DOT, 1).charAt(0));
    }
  }
  /** Lit un chaîne à partir d'un fichier. */
  public static String readStringFromFile(Scanner s) {
    String r, eoln;
    r = "";
    eoln = System.getProperty("line.separator");
    do {
      r = r + readCharacterFromFile(s);
    } while(!r.endsWith(eoln));
    return r.substring(0, r.length() - eoln.length());
  }
  /** Ouvre un fichier en écriture. */
  public static OutputStreamWriter openOut(String name) {
    try {
      java.io.FileOutputStream fos = new java.io.FileOutputStream(name);
      java.io.OutputStreamWriter out = new java.io.OutputStreamWriter(fos, "UTF-8");
      return out;
    } catch(java.io.FileNotFoundException e) {
      return new java.io.OutputStreamWriter(System.out);
    } catch(java.io.UnsupportedEncodingException e) {
      return new java.io.OutputStreamWriter(System.out);
    }
  }
  /** Ferme un fichier en écriture. */
  public static void closeOut(OutputStreamWriter s) {
    try {
      s.close();
    } catch(java.io.IOException e) {}
  }
  /** Ecrit un passage à la ligne dans un fichier. */
  public static void printlnToFile(OutputStreamWriter s) {
    try { s.write(System.getProperty("line.separator"));
    } catch(java.io.IOException e) {}
  }
  /** Ecrit un passage une chaîne dans un fichier. */
  public static void printToFile(OutputStreamWriter s, String n) {
    try { s.write(n);
    } catch(java.io.IOException e) {}
  }
  /** Ecrit un passage une chaîne et un passage à la ligne dans un fichier. */
  public static void printlnToFile(OutputStreamWriter s, String n) {
    printToFile(s, n);
    printlnToFile(s);
  }
  /** Ecrit un passage un entier dans un fichier. */
  public static void printToFile(OutputStreamWriter s, int n) {
    try {
      s.write(String.valueOf(n));
    } catch(java.io.IOException e) {}
  }
  /** Ecrit un passage un entier et un passage à la ligne dans un fichier. */
  public static void printlnToFile(OutputStreamWriter s, int n) {
    printToFile(s, n);
    printlnToFile(s);
  }
  /** Ecrit un passage un nombre décimal dans un fichier. */
  public static void printToFile(OutputStreamWriter s, double n) {
    try {
      s.write(String.valueOf(n));
    } catch(java.io.IOException e) {}
  }
  /** Ecrit un passage un nombre décimal et un passage à la ligne dans un fichier. */
  public static void printlnToFile(OutputStreamWriter s, double n) {
    printToFile(s, n);
    printlnToFile(s);
  }

  // ----------------------------------------------------------------------
  // graphisme
  // ----------------------------------------------------------------------

  /** Initialise le graphique avec un tracé de tailles (w, h).
   * - Dans ce contexte le titre "s" et la position (x, y) sont sans importance, on pourra donc utiliser : <tt>initDrawing("", 0, 0, w, h);</tt>
   */
  public static void initDrawing(String s, int x, int y, int w, int h) { 
    initDrawing(w, h);
  }
  /**
   * @see #initDrawing(String, int, int, int, int)
   */
  public static void initDrawing(int w, int h) {    
    getPane().reset(w, h);
    org.javascool.gui.Desktop.getInstance().focusOnProgletPanel();
  }
  /** Peint un pixel en (x, y) de couleur RGB = (c1, c2, c3). */
  public static void drawPixel(double x, double y, int c1, int c2, int c3) {
    getPane().add(new Rectangle2D.Double(x, y, 0, 0), new Color(c1, c2, c3), Color.WHITE);
  }
  /** Trace un rectangle de coin supérieur gauche (x, y), de tailles (a, b) et de couleur RGB = (c1, c2, c3). */
  public static void drawRect(double x, double y, double a, double b, int c1, int c2, int c3) {
    getPane().add(new Rectangle2D.Double(x, y, a, b), new Color(c1, c2, c3), Color.WHITE);
  }
  /** Remplit un rectangle de coin supérieur gauche (x, y), de tailles (a, b) avec la couleur RGB = (c1, c2, c3). */
  public static void paintRect(double x, double y, double a, double b, int c1, int c2, int c3) {
    getPane().add(new Rectangle2D.Double(x, y, a, b), new Color(c1, c2, c3), new Color(c1, c2, c3));
  }
  /** Trace un segment de droite de (x1, y1) à (x2, y2) et de couleur RGB = (c1, c2, c3). */
  public static void drawLine(double x1, double y1, double x2, double y2, int c1, int c2, int c3) {
    getPane().add(new Line2D.Double(x1, y1, x2, y2), new Color(c1, c2, c3), Color.WHITE);
  }
  /** Trace un cercle de centre (cx, cy) de rayon r et de couleur RGB = (c1, c2, c3). */
  public static void drawCircle(double cx, double cy, double r, int c1, int c2, int c3) {
    getPane().add(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r), new Color(c1, c2, c3), null);
  }
  /** Remplit un cercle de centre (cx, cy) de rayon r avec la couleur RGB = (c1, c2, c3). */
  public static void paintCircle(double cx, double cy, double r, int c1, int c2, int c3) {
    getPane().add(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r), new Color(c1, c2, c3), new Color(c1, c2, c3));
  }
  /** Écrit un texte au point (x, y) avec une fonte de taille size et la couleur RGB = (c1, c2, c3). */
  public static Rectangle2D drawText(String text, double x, double y, int size, int c1, int c2, int c3) {
    FontRenderContext ctx = new FontRenderContext(null, true, true);
    Font font = new Font(Font.SANS_SERIF, Font.PLAIN,size);
    GlyphVector vector = font.createGlyphVector(ctx, text);
    Color color = new Color(c1, c2, c3);
    Shape shape = vector.getOutline();
    Area area = new Area(shape);
    area = area.createTransformedArea(AffineTransform.getTranslateInstance(x, y));
    getPane().add(area, color, color);
    return area.getBounds2D();
  }
}
