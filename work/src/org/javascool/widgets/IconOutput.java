/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.widgets;

// Used to define the gui
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

// Used to manipulate the image
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

/** Panneau pour le tracé d'images pixeliques.
 *
 * @see <a href="IconOutput.java.html">source code</a>
 * @serial exclude
 */
public class IconOutput extends JPanel {
  private static final long serialVersionUID = 1L;
  // @bean
  public IconOutput() {
    setBackground(Color.GRAY);
    setPreferredSize(new Dimension(550, 550));
    reset(550, 550);
  }
  
  /** Routine interne de tracé, ne pas utiliser.
   *
   */
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    setBounds();
    g.setPaintMode();
    for(int j = 0; j < height; j++)
      for(int i = 0; i < width; i++) {
        g.setColor(image[i + j * width]);
        g.fillRect(i0 + i * dij, j0 + j * dij, dij, dij);
      }
  }
  private void setBounds() {
    int di = width > 0 ? getWidth() / width : 1, dj = height > 0 ? getHeight() / height : 1;
    dij = di < dj ? di : dj;
    i0 = (getWidth() - width * dij) / 2;
    j0 = (getHeight() - height * dij) / 2;
  }
  /**  Efface et initialize l'image.
   * @param width Taille horizontale de l'image.
   * @param height Taille verticale de l'image.
   * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
   */
  public IconOutput reset(int width, int height) {
    if(width * height > 550 * 550) throw new IllegalArgumentException("Image size too big !");
    if(width % 2 == 0)
      width--;
    if(height % 2 == 0)
      height--;
    image = new Color[(this.width = (width > 0 ? width : 1)) * (this.height = (height > 0 ? height : 1))];
    for(int ij = 0; ij < this.width * this.height; ij++)
      image[ij] = Color.WHITE;
    ;
    repaint(0, 0, getWidth(), getHeight());
    return this;
  }
  /** Initialize l'image à partir d'un fichier.
   * @param location L'URL (Universal Resource Location) de l'image.
   * @return Les dimensions de l'image.
   * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
   */
  public IconOutput reset(String location) throws IOException {
    BufferedImage img = ImageIO.read(new URL(location));
    if(img != null) {
      reset(img.getWidth(), img.getHeight());
      for(int j = 0, ij = 0; j < height; j++)
        for(int i = 0; i < width; i++, ij++)
          image[ij] = new Color(img.getRGB(i, j));
      return this;
    } else throw new IOException("Unable to load the image " + location);
  }
  /** Renvoie les dimensions de l'image. */
  public Dimension getDimension() {
    return new Dimension(width, height);
  }
  /** Définit la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @param  c Couleur: "black" (default), "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow".
   * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
   */
  public boolean set(int x, int y, String c) {
    return set(x, y, getColor(c));
  }
  /** Définit la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @param v L'intensité en niveau de gris du pixel de 0 (noir) à 255 (blanc).
   * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
   */
  public boolean set(int x, int y, int v) {
    v = v < 0 ? 0 : v > 255 ? 255 : v;
    return set(x, y, new Color(v, v, v));
  }
  private boolean set(int x, int y, Color c) {
    if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
      setBounds();
      int ij = x + y * width;
      image[ij] = c;
      repaint(i0 + x * dij, j0 + y * dij, dij, dij);
      return true;
    } else
      return false;
  }
  /** Renvoie la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @return L'intensite du pixel entre 0 et 255 ou -1 si le pixel n'est pas dans l'image.
   */
  public int getIntensity(int x, int y) {
    if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
      Color c = image[x + y * width];
      return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
    } else
      return -1;
  }
  /**  Renvoie la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @return La couleur du pixel ou "undefined" si le pixel n'est pas dans l'image.
   */
  public String getColor(int x, int y) {
    if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
      Color c = image[x + y * width];
      return colors.containsKey(c) ? colors.get(c) : c.toString();
    } else
      return "undefined";
  }
  private Color image[];
  private int width, height, i0, j0, dij;

  private static HashMap<Color, String> colors = new HashMap<Color, String>();
  private static Color getColor(String color) {
    try { return (Color) Class.forName("java.awt.Color").getField(color).get(null);
    } catch(Exception e) {
      return Color.BLACK;
    }
  }
  private static void putColors(String color) {
    colors.put(getColor(color), color);
  }
  static {
    putColors("black");
    putColors("blue");
    putColors("cyan");
    putColors("gray");
    putColors("green");
    putColors("magenta");
    putColors("orange");
    putColors("pink");
    putColors("red");
    putColors("white");
    putColors("yellow");
  }
}
