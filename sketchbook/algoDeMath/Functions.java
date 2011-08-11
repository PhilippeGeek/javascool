package org.javascool.proglets.algoDeMath;
public class Functions {
  //
  // This defines the javascool interface
  //

  /** Initialise le tracé.
   * @param X Echelle maximale horizontale, l'abscisse sera tracée dans [-X, X], par défaut [-1, 1].
   * @param Y Echelle maximale verticale, l'ordonnée sera tracée dans [-Y, Y], par défaut [-1, 1].
   *//*
   *  public static void scopeReset(double X, double Y) {
   *  org.javascool.JsMain.getMain().getFrame().showTab("Tracé");
   *  panel.reset(X, Y);
   *  }
   *  /** Initialise le tracé.
   * @param Xmin Echelle minimale horizontale, l'abscisse sera tracée dans [-Xmin, Xmax], par défaut [-1, 1].
   * @param Xmax Echelle maximale horizontale, l'abscisse sera tracée dans [-Xmin, Xmax], par défaut [-1, 1].
   * @param Ymin Echelle minimale verticale, l'abscisse sera tracée dans [-Ymin, Ymay], par défaut [-1, 1].
   * @param Ymax Echelle maximale verticale, l'abscisse sera tracée dans [-Ymin, Ymax], par défaut [-1, 1].
   *//*
   *  public static void scopeReset(double Xmin, double Xmax, double Ymin, double Ymax) {
   *  panel.reset(Xmin, Xmax, Ymin, Ymax);
   *  }
   *  /**//*public static void scopeReset() {
   *  panel.reset(1, 1);
   *  }
   *  /** Change la valeur d'un point du tracé.
   * @param x Abcisse de la courbe, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée de la courbe, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   *//*
   *  public static void scopeSet(double x, double y, int c) {
   *  panel.scope.add(x, y, c);
   *  }
   *  /** Ajoute un chaîne de caratère au tracé.
   * <p><tt>scopeAdd</tt> est une abréviation pour <tt>scopeAddString</tt>.</p>
   * @param x Abcisse du coin inférieur gauche de la chaîne, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée du coin inférieur gauche de la chaîne, dans [-Y, Y], par défaut [-1, 1].
   * @param s Valeur de la chaîne de caratère.
   * @param c Couleur de la chaîne de caratère: 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   *//*
   *  public static void scopeAddString(double x, double y, String s, int c) {
   *  panel.scope.add(x, y, s, c);
   *  }
   *  /**//*public static void scopeAddString(double x, double y, String s) {
   *  scopeAddString(x, y, s, 0);
   *  }
   *  /**//*public static void scopeAdd(double x, double y, String s, int c) {
   *  scopeAddString(x, y, s, c);
   *  }
   *  /**//*public static void scopeAdd(double x, double y, String s) {
   *  scopeAddString(x, y, s);
   *  }
   *  /** Trace un rectangle.
   * @param xmin Abcisse inférieure gauche, dans [-X, X], par défaut [-1, 1].
   * @param ymin Ordonnée inférieure gauche, dans [-Y, Y], par défaut [-1, 1].
   * @param xmax Abcisse supérieure droite, dans [-X, X], par défaut [-1, 1].
   * @param ymax Ordonnée supérieure droite, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   *//*
   *  public static void scopeAddRectangle(double xmin, double ymin, double xmax, double ymax, int c) {
   *  scopeAddLine(xmin, ymin, xmax, ymin, c);
   *  scopeAddLine(xmax, ymin, xmax, ymax, c);
   *  scopeAddLine(xmax, ymax, xmin, ymax, c);
   *  scopeAddLine(xmin, ymax, xmin, ymin, c);
   *  }
   *  /**//*public static void scopeAddRectangle(double xmin, double ymin, double xmax, double ymax) {
   *  scopeAddRectangle(xmin, ymin, xmax, ymax, 0);
   *  }
   *  /** Trace une ligne.
   * @param x1 Abcisse du 1er point, dans [-X, X], par défaut [-1, 1].
   * @param y1 Ordonnée du 1er point, dans [-Y, Y], par défaut [-1, 1].
   * @param x2 Abcisse du 2eme point, dans [-X, X], par défaut [-1, 1].
   * @param y2 Ordonnée du 2eme point, dans [-Y, Y], par défaut [-1, 1].
   * @param c Numéro de la courbe: 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   *//*
   *  public static void scopeAddLine(double x1, double y1, double x2, double y2, int c) {
   *  panel.scope.add(x1, y1, x2, y2, c);
   *  }
   *  /**//*public static void scopeAddLine(double x1, double y1, double x2, double y2) {
   *  scopeAddLine(x1, y1, x2, y2, 0);
   *  }
   *  /** Trace un  cercle.
   * @param x Abcisse du centre, dans [-X, X], par défaut [-1, 1].
   * @param y Ordonnée du centre, dans [-Y, Y], par défaut [-1, 1].
   * @param r Rayon du cercle.
   * @param c Numéro de la courbe: 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   *//*
   *  public static void scopeAddCircle(double x, double y, double r, int c) {
   *  panel.scope.add(x, y, r, c);
   *  }
   *  /**//*public static void scopeAddCircle(double x, double y, double r) {
   *  scopeAddCircle(x, y, r, 0);
   *  }
   *  /** Renvoie la valeur horizontale du réticule. *//*
   *  public static double scopeX() {
   *  return panel.inputX.getValue();
   *  }
   *  /** Renvoie la valeur verticale du réticule. *//*
   *  public static double scopeY() {
   *  return panel.inputY.getValue();
   *  }*/
}
