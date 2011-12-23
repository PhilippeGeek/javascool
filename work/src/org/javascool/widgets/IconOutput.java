/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.widgets;

// Used to define the gui
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

// Used to manipulate the image
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import org.javascool.macros.Macros;
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
	int ij = i + j * width;
	if (0 <= ij && ij < image.length) {
	  g.setColor(image[ij]);
	  g.fillRect(i0 + i * dij, j0 + j * dij, dij, dij);
	}
      }
    Graphics2D g2d = (Graphics2D) g;
    paint2D(g2d);
  }
  /** Cette routine est appellée à chaque tracé et permet de définir un tracé spécifique au dessus de l'image affichée. 
   * - Pour utiliser cette foncctionnalité, il faut définir: <pre>
   * class MyIconInput extends IconInput {
   *   public void paint2D(Graphics2D g) {
   *     // Ici ajouter les g.drawLine g.fillOval g.drawRect g.fillRect souhaité.
   *   }
   * }</pre>
   * @param g2d L'environnement graphique 2D à utiliser pour peindre.
   */
  public void paint2D(Graphics2D g2d) {
  }
  private void setBounds() {
    int di = width > 0 && getWidth() >= width ? getWidth() / width : 1;
    int dj = height > 0 && getHeight() >= height ? getHeight() / height : 1;
    dij = di < dj ? di : dj;
    i0 = (getWidth() - width * dij) / 2;
    j0 = (getHeight() - height * dij) / 2;
  }
  /**  Efface et initialize l'image.
   * @param width Taille horizontale de l'image.
   * @param height Taille verticale de l'image.
   * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
   */
  public final IconOutput reset(int width, int height) {
    if(width > 550 || height > 550 || width * height > 550 * 550) throw new IllegalArgumentException("Image size too big !");
    if (width <= 0)
      width = 300;
    if (height <= 0)
      height = 300;
    if(width % 2 == 0)
      width++;
    if(height % 2 == 0)
      height++;
    image = new Color[(this.width = width) * (this.height = height)];
    for(int ij = 0; ij < this.width * this.height; ij++)
      image[ij] = Color.WHITE;
    repaint(0, 0, getWidth(), getHeight());
    return this;
  }
  /** Initialize l'image à partir d'un fichier.
   * @param location L'URL (Universal Resource Location) de l'image.
   * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
   */
  public IconOutput reset(String location) throws IOException {
    // Fait 2//3 essais sur l'URL si besoin
    for (int n = 0; n < 3; n++) {
      BufferedImage img = ImageIO.read(Macros.getResourceURL(location));
      if(img != null)
	return reset(img);
    }
    throw new IOException("Unable to load the image " + location);
  }
  /** Initialize l'image à partir d'une image en mémoire.
   * @param img L'image qui va initialiser le tracé.
   * @return Cet objet, permettant de définir la construction <tt>new IconOutput().reset(..)</tt>.
   */
  public IconOutput reset(BufferedImage img) throws IOException {
    reset(img.getWidth(), img.getHeight());
    for(int j = 0; j <  img.getHeight(); j++)
      for(int i = 0; i < img.getWidth(); i++)
	image[i + width * j] = new Color(img.getRGB(i, j));
    repaint(0, 0, getWidth(), getHeight());
    return this;
  }
  /** Renvoie les dimensions de l'image. */
  public Dimension getDimension() {
    return new Dimension(width, height);
  }
  /** Renvoie une image dans laquelle le contenu de l'affichage est copié.
   * @return Le contenu de l'affichage sous forme d'image.
   */
  public BufferedImage getImage() {
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for(int j = 0; j < img.getHeight(); j++)
      for(int i = 0; i < img.getWidth(); i++)
	img.setRGB(i, j, image[i + width * j].getRGB());
    return img;
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
  /** Définit la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @param c L'intensité en couleur du pixel.
   * @return La valeur true si le pixel est dans les limites de l'image, false sinon.
   */
  public boolean set(int x, int y, Color c) {
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
   * @return L'intensite du pixel entre 0 et 255 ou 0 si le pixel n'est pas dans l'image.
   */
  public int getIntensity(int x, int y) {
    if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
      Color c = image[x + y * width];
      return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
    } else
      return 0;
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
  /**  Renvoie la valeur d'un pixel.
   * @param x Abscisse du pixel, dans {0, width{.
   * @param y Ordonnée du pixel, dans {0, height{.
   * @return La couleur du pixel ou black si le pixel n'est pas dans l'image.
   */
  public Color getPixelColor(int x, int y) {
    if((0 <= x) && (x < width) && (0 <= y) && (y < height)) {
      Color c = image[x + y * width];
      return c;
    } else
      return Color.BLACK;
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
