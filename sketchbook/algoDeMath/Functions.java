package org.javascool.proglets.algoDeMath;
import static org.javascool.macros.Macros.*;

public class Functions {
  private static final long serialVersionUID = 1L;
  private static Panel getPane() {
    return getProgletPane();
  }
  /** Initialise le tracé.
   * @param Xscale Echelle maximale horizontale, l'abscisse sera tracée dans [-X, X], par défaut [-1, 1].
   * @param Yscale Echelle maximale verticale, l'ordonnée sera tracée dans [-Y, Y], par défaut [-1, 1].
   */
  public static void scopeReset(double Xscale, double Yscale) {
    getPane().inputX.setScale(-Xscale, Xscale, 0.001);
    getPane().inputY.setScale(-Yscale, Yscale, 0.001);
    getPane().scope.reset(0, 0, Xscale, Yscale);
  }
  /** Initialise le tracé.
   * @param Xmin Echelle minimale horizontale, l'abscisse sera tracée dans [-Xmin, Xmax], par défaut [-1, 1].
   * @param Xmax Echelle maximale horizontale, l'abscisse sera tracée dans [-Xmin, Xmax], par défaut [-1, 1].
   * @param Ymin Echelle minimale verticale, l'abscisse sera tracée dans [-Ymin, Ymay], par défaut [-1, 1].
   * @param Ymax Echelle maximale verticale, l'abscisse sera tracée dans [-Ymin, Ymax], par défaut [-1, 1].
   */
  public static void scopeReset(double Xmin, double Xmax, double Ymin, double Ymax) {
    getPane().inputX.setScale(Xmin, Xmax, 0.001);
    getPane().inputY.setScale(Ymin, Ymax, 0.001);
    getPane().scope.reset((Xmin + Xmax) / 2, (Ymin + Ymax) / 2, (Xmax - Xmin) / 2, (Ymax - Ymin) / 2);
  }
  /*
   * @see #scopeReset(double, double, double, double)
   */
  public static void scopeReset() {
    scopeReset(1, 1);
  }
  /** Change la valeur d'un point du tracé.
   * @param x Abcisse de la courbe, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée de la courbe, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir, défaut), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void scopeSet(double x, double y, int c) {
    getPane().scope.add(x, y, c);
  }
  /*
   * @see #scopeSet(double, double, int)
   */
  public static void scopeSet(double x, double y) {
    scopeSet(x, y, 0);
  }
  /** Ajoute un chaîne de caratère au tracé.
   * <p><tt>scopeAdd</tt> est une abréviation pour <tt>scopeAddString</tt>.</p>
   * @param x Abcisse du coin inférieur gauche de la chaîne, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée du coin inférieur gauche de la chaîne, dans [-Y, Y], par défaut [-1, 1].
   * @param s Valeur de la chaîne de caractères.
   * @param c Couleur de la chaîne de caractères: 0 (noir, défaut), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void scopeAddString(double x, double y, String s, int c) {
    getPane().scope.add(x, y, s, c);
  }
  /*
   * @see #scopeAddString(double, double, double, String, int)
   */
  public static void scopeAddString(double x, double y, String s) {
    scopeAddString(x, y, s, 0);
  }
  /** Trace un rectangle.
   * @param xmin Abcisse inférieure gauche, dans [-X, X], par défaut [-1, 1].
   * @param ymin Ordonnée inférieure gauche, dans [-Y, Y], par défaut [-1, 1].
   * @param xmax Abcisse supérieure droite, dans [-X, X], par défaut [-1, 1].
   * @param ymax Ordonnée supérieure droite, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir, défaut), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void scopeAddRectangle(double xmin, double ymin, double xmax, double ymax, int c) {
    scopeAddLine(xmin, ymin, xmax, ymin, c);
    scopeAddLine(xmax, ymin, xmax, ymax, c);
    scopeAddLine(xmax, ymax, xmin, ymax, c);
    scopeAddLine(xmin, ymax, xmin, ymin, c);
  }
  /*
   * @see #scopeAddRectangle(double, double, double, double, int)
   */
  public static void scopeAddRectangle(double xmin, double ymin, double xmax, double ymax) {
    scopeAddRectangle(xmin, ymin, xmax, ymax, 0);
  }
  /** Trace une ligne.
   * @param x1 Abcisse du 1er point, dans [-X, X], par défaut [-1, 1].
   * @param y1 Ordonnée du 1er point, dans [-Y, Y], par défaut [-1, 1].
   * @param x2 Abcisse du 2eme point, dans [-X, X], par défaut [-1, 1].
   * @param y2 Ordonnée du 2eme point, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir, défaut), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void scopeAddLine(double x1, double y1, double x2, double y2, int c) {
    getPane().scope.add(x1, y1, x2, y2, c);
  }
  /*
   * @see #scopeAddLine(double, double, double, double, int)
   */
  public static void scopeAddLine(double x1, double y1, double x2, double y2) {
    scopeAddLine(x1, y1, x2, y2, 0);
  }
  /** Trace un  cercle.
   * @param x Abcisse du centre, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée du centre, dans [-Y, Y], par défaut [-1, 1].
   * @param r Rayon du cercle.
   * @param c Numéro de la courbe: 0 (noir, défaut), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void scopeAddCircle(double x, double y, double r, int c) {
    getPane().scope.add(x, y, r, c);
  }
  /*
   * @see #scopeAddCircle(double, double, double, int)
   */
  public static void scopeAddCircle(double x, double y, double r) {
    scopeAddCircle(x, y, r, 0);
  }
  /** Renvoie la valeur horizontale du réticule. */
  public static double scopeX() {
    return getPane().inputX.getValue();
  }
  /** Renvoie la valeur verticale du réticule. */
  public static double scopeY() {
    return getPane().inputY.getValue();
  }
}
