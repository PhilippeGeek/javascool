package org.javascool.proglets.tortueLogo;
import java.awt.Color;
import org.javascool.tools.Macros;

/** Définit une proglet javascool qui permet de simuler la tortue logo.
 * @see <a href="about-proglet.htm">Description</a>
 * @see <a href="the-proglet.htm">La proglet</a>
 * @see <a href="http://fr.wikipedia.org/wiki/Logo_(langage)#Primitives_graphiques">La rÃƒÆ’Ã‚Â©fÃƒÆ’Ã‚Â©rence du langage logo</a>
 * @see <a href="TortueLogo.java.html">code source</a>
 * @serial exclude
 */
public class Functions {
  private static final long serialVersionUID = 1L;
  //
  // This defines the tests on the panel
  //

  /**/ public static void test() {
    clear_all();
    pen_up();
    int t = 0;
    while(t < 9000) {
      set_color((t / 1000) % 10);
      set_position(256 + 250 * Math.cos(0.0015 * t), 256 + 250 * Math.sin(0.0045 * t));
      pen_down();
      t = t + 1;
    }
  }
  //
  // This defines the javascool interface
  //

  // Updates the turtle position and draw if required
  private static void update(int x, int y) {
    if(x < 0)
      x = 0;
    if(x > 511)
      x = 511;
    if(y < 0)
      y = 0;
    if(y > 511)
      y = 511;
    if(pen)
      draw(Functions.x, x, Functions.y, y);
    Functions.x = x;
    Functions.y = y;
    getTortueLogo().show(x, y);
    Macros.sleep(3);
  }
  private static void draw(int x1, int x2, int y1, int y2) {
    if(Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
      if(x1 < x2)
        draw_x(x1, x2, y1, y2);
      else if(x1 > x2)
        draw_x(x2, x1, y2, y1);
    } else {
      if(y1 < y2)
        draw_y(x1, x2, y1, y2);
      else if(y1 > y2)
        draw_y(x2, x1, y2, y1);
    }
  }
  private static void draw_x(int x1, int x2, int y1, int y2) {
    for(int x = x1; x <= x2; x++)
      getTortueLogo().add(x, y1 + ((y2 - y1) * (x - x1)) / (x2 - x1), pen_color);
  }
  private static void draw_y(int x1, int x2, int y1, int y2) {
    for(int y = y1; y <= y2; y++)
      getTortueLogo().add(x1 + ((x2 - x1) * (y - y1)) / (y2 - y1), y, pen_color);
  }
  private static int x = 0, y = 0;
  private static double a = 0;
  private static Color pen_color = Color.BLACK;
  private static boolean pen = true;

  /** Efface toutes traces du carrÃƒÆ’Ã‚Â© de salade de taille (512, 512). */
  public static void clear_all() {
    getTortueLogo().clear();
  }
  /** Retour au milieu du carrÃƒÆ’Ã‚Â© de salade, au point (256, 256). */
  public static void home() {
    update(getTortueLogo().width / 2, getTortueLogo().height / 2);
  }
  /** La tortue avance de n pas. */
  public static void forward(double n) {
    set_position(x + n * Math.cos(a), y + n * Math.sin(a));
  }
  /** La tortue recule de n pas. */
  public static void backward(double n) {
    forward(-n);
  }
  /** La tortue tourne de n degrÃƒÆ’Ã‚Â©s d'angle vers la gauche. */
  public static void leftward(double n) {
    a += Math.PI / 180.0 * n;
  }
  /** La tortue tourne de n degrÃƒÆ’Ã‚Â©s d'angle vers la droite. */
  public static void rightward(double n) {
    leftward(-n);
  }
  /** Fixe la position absolue de la tortue dans le carrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â© de salade. */
  public static void set_position(double x, double y) {
    update((int) x, (int) y);
  }
  /** Fixe le cap de la tortue de maniere absolue, selon l'angle de a degrÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â©s. */
  public static void set_heading(double a) {
    Functions.a = Math.PI / 180.0 * a;
  }
  /** La tortue ne laisse pas de trace. */
  public static void pen_up() {
    pen = false;
  }
  /** La tortue laisse sa trace (par défaut). */
  public static void pen_down() {
    pen = true;
  }
  /** Change la couleur du fond, n est un entier positif entre 0 et 9.
   * @param n : 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void set_background(int n) {
    getTortueLogo().setBackground(colors[n < 0 || n > 9 ? 0 : n]);
  }
  /** Change la couleur du crayon, n est un entier positif entre 0 et 9.
   * @param n : 0 (noir), 1 (brun), 2 (rouge), 3 (orange), 4 (jaune), 5 (vert), 6 (bleu), 7 (violet), 8 (gris), 9 (blanc).
   */
  public static void set_color(int n) {
    pen_color = colors[n < 0 || n > 9 ? 0 : n];
  }
  private static Color colors[] = { Color.BLACK, new Color(150, 75, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.GRAY, Color.WHITE };

  private static Panel getTortueLogo() {
    return (Panel) Macros.getProgletPanel();
  }
}
