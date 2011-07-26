package org.javascool.proglets.tortueLogo;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import org.javascool.tools.Utils;

public class Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    public Panel() {
      setPreferredSize(new Dimension(width, height));
      setBackground(new Color(10, 100, 10));
      // Adds the garden
      clear();
      // Adds the turtle
      turtle = new JLabel();
      turtle.setIcon(Utils.getIcon("org/javascool/proglets/tortueLogo/turtle.gif"));
      turtle.setBounds(width / 2, height / 2, 42, 35);
      add(turtle);
    }
    /** Internal routine: do not use. */
    @Override
    public void paint(Graphics g) {
      super.paint(g);
      for(int j = 0; j < height; j++)
        for(int i = 0; i < width; i++)
          if(garden[i + j * width] != null) {
            g.setColor(garden[i + j * width]);
            g.fillRect(i, j, 1, 1);
          }
    }
    private Color garden[];
    public JLabel turtle;
    static final int width = 512, height = 512;

    /** Clears the garden. */
    public final void clear() {
      garden = new Color[width * height];
    }
    /** Shows the turtle at a given location.
     * @param x Turtle horizontal position, not shown if &lt; 0.
     * @param y Turtle vertical position, not shown if &lt; 0.
     */
    public void show(int x, int y) {
      if((x < 0) || (y < 0))
        turtle.setVisible(false);
      else {
        turtle.setBounds(x, y, 42, 35);
        turtle.setVisible(true);
      }
    }
    /** Adds a trace value.
     * @param x Pixel abscissa, in [-1..1].
     * @param y Pixel Ordinate, in [-1..1].
     * @param c Color in {0, 9}.
     */
    public void add(int x, int y, Color c) {
      garden[x + y * width] = c;
    }
  }
