/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.codagePixels;
import static org.javascool.macros.Macros.*;

// Used to define the gui
import java.awt.Dimension;
import java.awt.Color;

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
   * @param width Demi largeur de l'image de taille {-width, width}, si centrée (sinon largeur de l'image)
   * @param height Demi hauteur de l'image de taille {-height, height}, si centrée (sinon hauteur de l'image).
   * @param centered Si l'image est centrée, la valeur vaut true; si l'image n'est pas centrée la valeur vaut false.
   */
  static public void reset(int width, int height, boolean centered) {
    Functions.width = width;
    Functions.height = height;
    Functions.centered = centered;
    if (centered) {
      getPane().reset(2 * width + 1, 2 * height + 1);
    } else {
      getPane().reset(width, height);
    }
  }  
  /**									
   * @see reset(int, int, boolean)
   */
  static public void reset(int width, int height) {
    reset(width, height, true);
  }
  static private int width = 0, height = 0;
  static private boolean centered = true;

  /** Charge l'image.
   * - La taille de l'image ne doit pas être trop importante (pas plus de 500^2).
   * @param image Nom de l'URL où se trouve l'image
   * @param centered Si l'image est centrée, la valeur vaut true; si l'image n'est pas centrée la valeur vaut false.
   */
  static public void load(String image, boolean centered) {
    try {
      getPane().reset(image);
      Dimension dim = getPane().getDimension(); 
      Functions.centered = centered;
      if (centered) {
	Functions.width = (dim.width - 1) / 2;
	Functions.height = (dim.height - 1) / 2;
      } else {
	Functions.width = dim.width;
	Functions.height = dim.height;
      }
    } catch(Exception e) {
      reset(200, 200);
      System.err.println(e);
      System.out.println("Impossible de charger " + image);
    }
  }
  /*
   * @see load(String, boolean)
   *
   */
  static public void load(String image) {
    load(image, true);
  }
  /** Renvoie la demi-largeur de l'image, si elle est centrée (sinon la largeur de l'image). */
  static public int getWidth() {
    return width;
  }
  /** Renvoie la demi-hauteur de l'image, si elle est centrée (sinon la hauteur de l'image). */
  static public int getHeight() {
    return height;
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}, si l'image est centrée (sinon valeur entre {0, width{).
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}, si l'image est centrée (sinon valeur entre {0, height{).
   * @param color Couleur: "black" (default), "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow".
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites d el'image.
   */
  static public boolean setPixel(int x, int y, String color) { 
    if (centered) {
      return getPane().set(x + width, height - y, color);
    } else {
      return getPane().set(x, y, color);
    }
  }
  /** Change la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}, si l'image est centrée (sinon valeur entre {0, width{).
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}, si l'image est centrée (sinon valeur entre {0, height{).
   * @param valeur Une valeur entre 0 et 255 (0 pour noir, 255 pour blanc).
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites de l'image.
   */
  static public boolean setPixel(int x, int y, int valeur) {
   if (centered) {
     return getPane().set(x + width, height - y, valeur);
    } else {
      return getPane().set(x, y, valeur);
    }
  }
  /** Change la valeur couleur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}, si l'image est centrée (sinon valeur entre {0, width{).
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre  {-height, height}, si l'image est centrée (sinon valeur entre {0, height{).
   * @param red Une valeur pour le rouge entre 0 et 255.
   * @param green Une valeur pour le vert entre 0 et 255.
   * @param blue Une valeur pour le blue entre 0 et 255.
   * @return Renvoie true si le pixel est dans l'image, false si il est en dehors des limites de l'image.
   */
  static public boolean setPixel(int x, int y, int red, int green, int blue) {
   if (centered) {
     return getPane().set(x + width, height - y, new Color(red, green, blue));
    } else {
     return getPane().set(x, y, new Color(red, green, blue));
    }
  }
  /** Lit la valeur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}, si l'image est centrée (sinon valeur entre {0, width{).
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre {-height, height}, si l'image est centrée (sinon valeur entre {0, height{).
   * @return Une valeur entre 0 et 255 (0 pour noir, 255 pour blanc); Renvoie 0 pour les pixels extérieurs à l'image.
   */
  static public int getPixel(int x, int y) { 
    if (centered) {
      return getPane().getIntensity(x + width, height - y);
    } else {
      return getPane().getIntensity(x, y);
    }
  }  /** Lit la valeur couleur d'un pixel de l'image.
   * @param x Abcisse de l'image, comptée à partir du milieu, valeur entre {-width, width}, si l'image est centrée (sinon valeur entre {0, width{).
   * @param y Ordonnée de l'image, comptée à partir du milieu, valeur entre {-height, height}, si l'image est centrée (sinon valeur entre {0, height{).
   * @return Une valeur entre 0 et 255 (0 pour noir, 255 pour blanc); Renvoie noir pour les pixels extérieurs à l'image.
   */
  static public Color getPixelColor(int x, int y) { 
    if (centered) {
      return getPane().getPixelColor(x + width, height - y);
    } else {
      return getPane().getPixelColor(x, y);
    }
  }
}

