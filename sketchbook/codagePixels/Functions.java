/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.codagePixels;
import static org.javascool.macros.Macros.*;

// Used to define the gui
import java.awt.Dimension;

/** Définit les fonctions de la proglet qui permettent de manipuler les pixels d'une image.
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
  static public void reset(int width, int height) {
    Functions.width = width;
    Functions.height = height;
    getPane().reset(2 * width + 1, 2 * height + 1);
  }
  static private int width, height;

  /** Charge l'image.
   * - La taille de l'image ne doit pas être trop importante (pas plus de 500^2).
   * @param image Nom de l'URL où se trouve l'image
   */
  static public void load(String image) {
    try {
      getPane().reset(image);
      Dimension dim = getPane().getDimension();
      Functions.width = (dim.width - 1) / 2;
      Functions.height = (dim.height - 1) / 2;
    } catch(Exception e) {
      reset(200, 200);
      System.out.println("Impossible de charger " + image);
    }
  }
  /** Renvoie la demi-largeur de l'image. */
  static public int getWidth() {
    return width;
  }
  /** Renvoie la demi-hauteur de l'image. */
  static public int getHeight() {
    return height;
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}.
   * @param color Couleur: "black" (default), "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow".
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites d el'image.
   */
  static public boolean setPixel(int x, int y, String color) {
    return getPane().set(x + width, y + height, color);
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}.
   * @param valeur Une valeur entre 0 et 255 (0 pour noir, 255 pour blanc).
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites d el'image.
   */
  static public boolean setPixel(int x, int y, int valeur) {
    return getPane().set(x + width, y + height, valeur);
  }
  /** Lit la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}.
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre {-height, height}.
   * @return Une valeur entre 0 et 255 (0 pour noir, 255 pour blanc);
   */
  static public int getPixel(int x, int y) {
    return getPane().getIntensity(x + width, y + height);
  }
}

