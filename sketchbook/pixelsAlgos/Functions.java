/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.pixelsAlgos;
import static org.javascool.macros.Macros.*;

// Used to define the gui
import java.awt.Dimension;

/** Définit les fonctions de la proglet qui de manipuler les pixels d'une image.
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
  /** Initialise l'image.
   * - La taille de l'image ne doit pas être trop importante (pas plus de 500^2).
   * @param width Demi largeur de l'image de taille {-width, width}.
   * @param height Demi hauteur de l'image de taille {-height, height}.
   */
  static public void smileyReset(int width, int height) {
    getPane().reset(2 * width + 1, 2 * height + 1);
  }
  static private int width, height;

  /** Charge l'image.
   * - La taille de l'image ne doit pas être trop importante (pas plus de 500^2).
   * @param image Nom de l'URL où se trouve l'image
   */
  static public void smileyLoad(String image) {
    try {
      getPane().reset(image);
      Dimension dim = getPane().getDimension();
      width = (dim.width - 1) / 2;
      height = (dim.height - 1) / 2;
    } catch(Exception e) {
      smileyReset(200, 200);
      System.out.println("Impossible de charger " + image);
    }
  }
  /** Gets the witdh. */
  static public int smileyWidth() {
    return width;
  }
  /** Gets the height. */
  static public int smileyHeight() {
    return height;
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}.
   * @param color Couleur: "black" (default), "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow".
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites d el'image.
   */
  static public boolean smileySet(int x, int y, String color) {
    return getPane().set(x + width, y + height, color);
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}.
   * @param valeur Une valeur entre 0 et 255 (0 pour noir, 1 pour blanc).
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites d el'image.
   */
  static public boolean smileySet(int x, int y, int valeur) {
    return getPane().set(x + width, y + height, valeur);
  }
  /** Lit la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre {-height, height}.
   * @return Une valeur entre 0 et 255 (0 pour noir, 1 pour blanc);
   */
  static public int smileyGet(int x, int y) {
    return getPane().getIntensity(x + width, y + height);
  }
}

