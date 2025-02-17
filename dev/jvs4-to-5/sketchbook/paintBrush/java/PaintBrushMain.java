/*******************************************************************************
* David.Pichardie@inria.fr, Copyright (C) 2011.           All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.paintBrush;

import javax.swing.*;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import org.javascool.macros.Macros;

class PaintBrushMain {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
                                 @Override
                                 public void run() {
                                   createAndShowGUI();
                                 }
                               }
                               );
  }
  private static void createAndShowGUI() {
    JFrame f = new JFrame("Paint");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new MainPanel());
    f.pack();
    f.setVisible(true);
  }
}

class Point implements Comparable<Point> {
  int x;
  int y;
  Point(int _x, int _y) {
    x = _x;
    y = _y;
  }
  @Override
  public int compareTo(Point o) {
    Point p = o; // (Point) o;
    int cmp1 = p.x - x;
    if(cmp1 != 0) {
      return cmp1;
    } else {
      return p.y - y;
    }
  }
  public boolean isClosed(Point p) {
    return Math.abs(p.x - x) <= 1 && Math.abs(p.y - y) <= 1;
  }
}

enum Mode { DRAW, RECTANGLE, ERASE, FILL, LINE }

class MainPanel extends JPanel implements ActionListener {
  private static final long serialVersionUID = 1L;

  private static String buttonVersion1String = "Mode démo";
  private static String buttonVersion2String = "Mode proglet";
  private static String button1String = "Tracé";
  private static String button2String = "Rectangle";
  private static String button3String = "Gomme";
  private static String button4String = "Remplir";
  private static String button5String = "Ligne";
  private static String buttonRotateString = "Rotation gauche";
  private static String showCodeBoxString = "hexa code";
  private static String buttonClearString = "Effacer tout";
  MyPanel myPanel;
  ColorPanel cPanel;

  public MainPanel() {
    super(new BorderLayout());

    JRadioButton buttonVersion1 = new JRadioButton(buttonVersion1String);
    buttonVersion1.setActionCommand(buttonVersion1String);
    buttonVersion1.setSelected(true);

    JRadioButton buttonVersion2 = new JRadioButton(buttonVersion2String);
    buttonVersion2.setActionCommand(buttonVersion2String);

    JRadioButton button1 = new JRadioButton(button1String);
    button1.setActionCommand(button1String);
    button1.setSelected(true);

    JRadioButton button2 = new JRadioButton(button2String);
    button2.setActionCommand(button2String);

    JRadioButton button3 = new JRadioButton(button3String);
    button3.setActionCommand(button3String);

    JRadioButton button4 = new JRadioButton(button4String);
    button4.setActionCommand(button4String);

    JRadioButton button5 = new JRadioButton(button5String);
    button5.setActionCommand(button5String);

    JCheckBox showCodeBox = new JCheckBox(showCodeBoxString);
    showCodeBox.setSelected(false);

    ButtonGroup group = new ButtonGroup();
    group.add(button1);
    group.add(button2);
    group.add(button3);
    group.add(button4);
    group.add(button5);

    JPanel radioPanel = new JPanel(new GridLayout(0, 1));
    radioPanel.add(button1);
    radioPanel.add(button2);
    radioPanel.add(button3);
    radioPanel.add(button4);
    radioPanel.add(button5);

    radioPanel.add(showCodeBox);

    JButton buttonRotate = new JButton(buttonRotateString);
    buttonRotate.setActionCommand(buttonRotateString);
    radioPanel.add(buttonRotate);

    JButton buttonClear = new JButton(buttonClearString);
    buttonClear.setActionCommand(buttonClearString);
    radioPanel.add(buttonClear);

    ButtonGroup groupVersion = new ButtonGroup();
    groupVersion.add(buttonVersion1);
    groupVersion.add(buttonVersion2);

    // JPanel radioVersionPanel = new JPanel(new GridLayout(1, 0));
    radioPanel.add(buttonVersion1);
    radioPanel.add(buttonVersion2);

    cPanel = new ColorPanel();
    PaintBrushImage image = new PaintBrushImage(MyPanel.width, MyPanel.height);
    myPanel = new MyPanel(cPanel, image);
    myPanel.updateMode(Mode.DRAW);

    add(cPanel, BorderLayout.SOUTH);
    add(radioPanel, BorderLayout.WEST);
    add(myPanel, BorderLayout.CENTER);
    // add(radioVersionPanel, BorderLayout.NORTH);
    // Je n'arrive pas a faire un bon affichage de la version ascii. Le placement swing c'est compliqu...
    // add(asciiPanel, BorderLayout.EAST);

    buttonVersion1.addActionListener(this);
    buttonVersion2.addActionListener(this);
    button1.addActionListener(this);
    button2.addActionListener(this);
    button3.addActionListener(this);
    button4.addActionListener(this);
    button5.addActionListener(this);
    buttonRotate.addActionListener(this);
    showCodeBox.addActionListener(this);
    buttonClear.addActionListener(this);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    if(action.equals(button1String)) {
      myPanel.updateMode(Mode.DRAW);
    } else if(action.equals(button2String)) {
      myPanel.updateMode(Mode.RECTANGLE);
    } else if(action.equals(button3String)) {
      myPanel.updateMode(Mode.ERASE);
    } else if(action.equals(button4String)) {
      myPanel.updateMode(Mode.FILL);
    } else if(action.equals(button5String)) {
      myPanel.updateMode(Mode.LINE);
    } else if(action.equals(buttonVersion1String)) {
      MyPanel.manipImage = MyPanel.demoManipImage;
    } else if(action.equals(buttonVersion2String)) {
      MyPanel.manipImage = MyPanel.progletManipImage;
    } else if(action.equals(showCodeBoxString)) {
      MyPanel.showCode = !MyPanel.showCode;
      myPanel.repaint();
      cPanel.repaint();
    } else if(action.equals(buttonRotateString)) {
      MyPanel.manipImage.rotationGauche();
      myPanel.repaint();
    } else if(action.equals(buttonClearString)) {
      myPanel.clear();
    }
  }
}

enum ColorPaint {
  BLACK(0, 0.f, 0.f, 0.f),
  GRAY(1, 128.f, 128.f, 128.f),
  MAROON(2, 128.f, 0.f, 0.f),
  RED(3, 255.f, 0.f, 0.f),
  GREEN(4, 0.f, 128.f, 0.f),
  LIME(5, 0.f, 255.f, 0.f),
  OLIVE(6, 128.f, 128.f, 0.f),
  YELLOW(7, 255.f, 255.f, 0.f),
  NAVY(8, 0.f, 0.f, 128.f),
  BLUE(9, 0.f, 0.f, 255.f),
  PURPLE(10, 128.f, 0.f, 128.f),
  FUCHSIA(11, 255.f, 0.f, 255.f),
  TEAL(12, 0.f, 128.f, 128.f),
  AQUA(13, 0.f, 255.f, 255.f),
  SILVER(14, 192.f, 192.f, 192.f),
  WHITE(15, 255.f, 255.f, 255.f);

  private int index;
  private Color color;
  static public ColorPaint[] colors;

  static {
    colors = new ColorPaint[16];
    for(ColorPaint c : ColorPaint.values())
      colors[c.getIndex()] = c;
  }

  ColorPaint(int idx, float r, float g, float b) {
    index = idx;
    color = new Color(r / 255, g / 255, b / 255, 1.f);
  }
  int getIndex() {
    return index;
  }
  Color getColor() {
    return color;
  }
  ColorPaint getTextColor() {
    switch(this) {
    case WHITE:
    case AQUA:
    case LIME:
    case YELLOW:
    case SILVER:
      return BLACK;
    default:
      return WHITE;
    }
  }
}

class ColorPanel extends JPanel implements MouseMotionListener {
  private static final long serialVersionUID = 1L;

  public ColorPaint current = ColorPaint.BLACK;
  static private int square = 16;

  public ColorPanel() {
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    addMouseMotionListener(this);
    addMouseListener(new MouseAdapter() {
                       @Override
                       public void mousePressed(MouseEvent e) {
                         int x = e.getX();
                         int y = e.getY();
                         if((y < 2 + 2 * square) && (x > 1 + (square + 2) * 2) && (x <= (square + 2) * (2 + 7) + 1 + square)) {
                           int idx = 2 * ((x - 1) / (square + 2) - 2) + (y - 1) / (square + 2);
                           // System.out.println("x = "+x+" y = "+y+" idx = "+idx);
                           current = ColorPaint.colors[idx];
                           repaint();
                         }
                       }
                     }
                     );
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(current.getColor());
    g.fillRect(1, 1, 2 + 2 * square, 2 + 2 * square);
    for(ColorPaint c : ColorPaint.values()) {
      int i = c.getIndex();
      int x = (square + 2) * (2 + i / 2) + 1;
      int y = (square + 2) * (i % 2) + 1;
      g.setColor(c.getColor());
      g.fillRect(x, y, square, square);
      if(MyPanel.showCode) {
        g.setColor(c.getTextColor().getColor());
        g.drawString(PaintBrushImage.byteToString(i), x + square / 3 - 2, y + 2 * square / 3 + 2);
      }
    }
  }
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(2 + (square + 2) * 2, 2 + (square + 2) * 10);
  }
  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    if(e.getX() <= 1 + (square + 2) * 2) {
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    } else {
      setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
  }
}

class MyPanel extends JPanel implements MouseMotionListener {
  private static final long serialVersionUID = 1L;

  // Mécanisme de switch entre demo et proglet mode.
  static public PaintBrushManipImage demoManipImage = new ManipImageFinal(), progletManipImage = new ManipImageVide(), manipImage = demoManipImage;

  static final boolean showBase = false;
  static final int square = 16;
  static final int height = 32;
  static final int width = 32;
  static final int totalheight = square * height;
  static boolean showCode = false;
  private static Cursor paint_cursor;
  private static Cursor eraser_cursor;
  private ColorPanel cPanel;
  private Mode mode;
  public PaintBrushImage image;
  private Point start_point_rectangle;
  private Point end_point_rectangle;
  private Point previous_point;
  private Point line_start;
  private Point line_end;
  private static JPanel me;

  int sanitizeX(int x) {
    int max_x = PaintBrushImage.maxX() * square;
    return (x <= 0) ? 0 : (x >= max_x) ? max_x - 1 : x;
  }
  int sanitizeY(int y) {
    int max_y = PaintBrushImage.maxY() * square;
    return (y <= 0) ? 0 : (y >= max_y) ? max_y - 1 : y;
  }
  int inverseY(int y) {
    return totalheight - y;
  }
  public void updateMode(Mode m) {
    mode = m;
    Cursor c;
    switch(mode) {
    case DRAW:
      c = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
      break;
    case FILL:
      c = paint_cursor;
      break;
    case ERASE:
      c = eraser_cursor;
      break;
    case LINE:
    case RECTANGLE:
      c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
      break;
    default:
      c = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }
    setCursor(c);
  }
  @Override
  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = inverseY(e.getY());
    switch(mode) {
    case DRAW:
      addOtherSquare(sanitizeX(x), sanitizeY(y));
      break;
    case ERASE:
      removeOtherSquare(sanitizeX(x), sanitizeY(y));
      break;
    case LINE:
      showLine(sanitizeX(x), sanitizeY(y));
      break;
    case RECTANGLE:
      showRect(sanitizeX(x), sanitizeY(y));
      break;
    default:
      break;
    }
  }
  @Override
  public void mouseMoved(MouseEvent evt) {}

  public static void warning(String msg) {
    JOptionPane.showMessageDialog(me,
                                  msg,
                                  "Erreur",
                                  JOptionPane.WARNING_MESSAGE);
  }
  public MyPanel(ColorPanel _cPanel, PaintBrushImage _image) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    java.awt.Image image1 = Macros.getIcon("org/javascool/proglets/paintBrush/cursor_paint.png").getImage();
    paint_cursor = toolkit.createCustomCursor(image1, new java.awt.Point(3, 24), "Fill");
    java.awt.Image image2 = Macros.getIcon("org/javascool/proglets/paintBrush/cursor_eraser.png").getImage();
    eraser_cursor = toolkit.createCustomCursor(image2, new java.awt.Point(2, 14), "Erase");

    cPanel = _cPanel;
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.WHITE);

    addMouseListener(new MouseAdapter() {
                       @Override
                       public void mousePressed(MouseEvent e) {
                         int x = e.getX();
                         int y = inverseY(e.getY());
                         switch(mode) {
                         case DRAW:
                           addSquare(x, y);
                           break;
                         case FILL:
                           fill(x, y);
                           break;
                         case ERASE:
                           removeSquare(x, y);
                           break;
                         case RECTANGLE:
                           startRect(x, y);
                           break;
                         case LINE:
                           startLine(x, y);
                           break;
                         }
                       }
                     }
                     );

    addMouseListener(new MouseAdapter() {
                       @Override
                       public void mouseReleased(MouseEvent e) {
                         int x = e.getX();
                         int y = inverseY(e.getY());
                         switch(mode) {
                         case RECTANGLE:
                           endRect(sanitizeX(x), sanitizeY(y));
                           break;
                         case LINE:
                           endLine(sanitizeX(x), sanitizeY(y));
                           break;
                         case ERASE:
                         case DRAW:
                           addSquareEnd();
                           break;
                         default:
                           break;
                         }
                       }
                     }
                     );

    addMouseMotionListener(this);

// addMouseMotionListener(new MouseAdapter() {
// public void mouseDragged(MouseEvent e) {
// switch (mode) {
// case DRAW : addOtherSquare(sanitizeX(x),sanitizeY(y)); break;
// case ERASE : removeOtherSquare(sanitizeX(x),sanitizeY(e.getY())); break;
// case RECTANGLE : showRect(sanitizeX(x),sanitizeY(e.getY())); break;
// default : break;
// }
// }
// });

    image = _image;
    me = this;
  }
  void drawPoint(Graphics g, Point p) {
    try {
      int col_int = PaintBrushImage.getPixel(p.x, p.y);
      if (0 <= col_int && col_int < ColorPaint.colors.length) {
	ColorPaint col = ColorPaint.colors[col_int];
	g.setColor(col.getColor());
	g.fillRect(square * p.x, inverseY(square * p.y) - square, square, square);
      }
    } catch(ArrayIndexOutOfBoundsException e) {}
    // Patch pour éviter l ejet d'une exception
  }
  void repaintPoint(Point p) {
    repaint(square * p.x, square * p.y, square + 1, square + 1);
  }
  void clear() {
    image.clear();
    repaint();
  }
  private void addSquare(int x, int y) {
    Point p = new Point(x / square, y / square);
    manipImage.affichePoint(p.x, p.y, cPanel.current.getIndex());
    previous_point = p;
    repaint();
  }
  private void addSquareEnd() {
    previous_point = null;
    repaint();
  }
  private void addOtherSquare(int x, int y) {
    Point p = new Point(x / square, y / square);
    manipImage.affichePoint(p.x, p.y, cPanel.current.getIndex());
    if(!p.isClosed(previous_point)) {
      fillHole(previous_point.x, previous_point.y, p.x, p.y);
    }
    previous_point = p;
    repaint();
  }
  // Bresenham's line algorithm
  private void fillHole(int x0, int y0, int x1, int y1) {
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);
    int sx = (x0 < x1) ? 1 : -1;
    int sy = (y0 < y1) ? 1 : -1;
    int err = dx - dy;
    while(true) {
      manipImage.affichePoint(x0, y0, cPanel.current.getIndex());
      if((x0 == x1) && (y0 == y1)) {
        return;
      }
      int e2 = 2 * err;
      if(e2 > -dy) {
        err = err - dy;
        x0 = x0 + sx;
      }
      if(e2 < dx) {
        err = err + dx;
        y0 = y0 + sy;
      }
    }
  }
  // Bresenham's line algorithm
  private void eraseHole(int x0, int y0, int x1, int y1) {
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);
    int sx = (x0 < x1) ? 1 : -1;
    int sy = (y0 < y1) ? 1 : -1;
    int err = dx - dy;
    while(true) {
      manipImage.supprimePoint(x0, y0);
      if((x0 == x1) && (y0 == y1)) {
        return;
      }
      int e2 = 2 * err;
      if(e2 > -dy) {
        err = err - dy;
        x0 = x0 + sx;
      }
      if(e2 < dx) {
        err = err + dx;
        y0 = y0 + sy;
      }
    }
  }
  private void removeSquare(int x, int y) {
    Point p = new Point(x / square, y / square);
    manipImage.supprimePoint(p.x, p.y);
    previous_point = p;
    repaint();
  }
  private void removeOtherSquare(int x, int y) {
    Point p = new Point(x / square, y / square);
    manipImage.supprimePoint(p.x, p.y);
    if(!p.isClosed(previous_point)) {
      eraseHole(previous_point.x, previous_point.y, p.x, p.y);
    }
    previous_point = p;
    repaint();
  }
  private void fill(int x, int y) {
    manipImage.remplir(x / square, y / square, cPanel.current.getIndex());
    repaint();
  }
  private void startRect(int x, int y) {
    start_point_rectangle = new Point(x / square, y / square);
    end_point_rectangle = null;
  }
  private void showRect(int x, int y) {
    end_point_rectangle = new Point(x / square, y / square);
    repaint();
  }
  private void endRect(int x, int y) {
    manipImage.afficheRectangle(start_point_rectangle.x, start_point_rectangle.y, x / square, y / square, cPanel.current.getIndex());
    end_point_rectangle = null;
    repaint();
  }
  private void startLine(int x, int y) {
    line_end = line_start = new Point(x / square, y / square);
    repaint();
  }
  private void showLine(int x, int y) {
    line_end = new Point(x / square, y / square);
    repaint();
  }
  private void endLine(int x, int y) {
    manipImage.afficheLigne(line_start.x, line_start.y, x / square, y / square, cPanel.current.getIndex());
    line_end = line_start = null;
    repaint();
  }
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width * square, height * square);
  }
  static Color lighter(Color color, int alpha) {
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();

    return new Color(red, green, blue, alpha);
  }
  void lighterSquare(Graphics g, int x, int y) {
    ColorPaint cp = ColorPaint.colors[PaintBrushImage.getPixel(x, y)];
    if(cp.getIndex() != 15) {
      Color c = cp.getColor();
      g.setColor(Color.WHITE);
      g.fillRect(square * x, inverseY(square * y) - square, square, square);
      g.setColor(lighter(c, 128));
    } else {
      g.setColor(Color.LIGHT_GRAY);
    }
    g.fillRect(square * x, inverseY(square * y) - square, square, square);
    if(showBase) {
      g.setColor(Color.BLACK);
      g.fillRect(square * x - 1, inverseY(square * y) - 1, 3, 3);
    }
  }
  // Bresenham's line algorithm
  private void draw_line(Graphics g, Point p0, Point p1) {
    int x0 = p0.x;
    int y0 = p0.y;
    int x1 = p1.x;
    int y1 = p1.y;
    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);
    int sx = (x0 < x1) ? 1 : -1;
    int sy = (y0 < y1) ? 1 : -1;
    int err = dx - dy;
    while(true) {
      lighterSquare(g, x0, y0);
      if((x0 == x1) && (y0 == y1)) {
        return;
      }
      int e2 = 2 * err;
      if(e2 > -dy) {
        err = err - dy;
        x0 = x0 + sx;
      }
      if(e2 < dx) {
        err = err + dx;
        y0 = y0 + sy;
      }
    }
  }
  private void draw_rect(Graphics g, Point p1, Point p2) {
    int xmin = Math.min(p1.x, p2.x);
    int xmax = Math.max(p1.x, p2.x);
    int ymin = Math.min(p1.y, p2.y);
    int ymax = Math.max(p1.y, p2.y);
    for(int i = xmin; i <= xmax; i++) {
      lighterSquare(g, i, ymin);
      lighterSquare(g, i, ymax);
    }
    for(int j = ymin + 1; j < ymax; j++) {
      lighterSquare(g, xmin, j);
      lighterSquare(g, xmax, j);
    }
// g.setColor(Color.BLACK);
// g.drawRect(square*xmin+square/2+1,square*ymin+square/2+1,square*(xmax-xmin),square*(ymax-ymin));
  }
  private void draw_point(Graphics g, Point p) {
    if(mode == Mode.ERASE) {
      for(int i = p.x - 1; i <= p.x + 1 && i < PaintBrushImage.maxX(); i++)
        for(int j = p.y - 1; j <= p.y + 1 && j < PaintBrushImage.maxY(); j++)
          if((i >= 0) && (j >= 0)) {
            lighterSquare(g, i, j);
          }
    } else {
      lighterSquare(g, p.x, p.y);
    }
  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // g.drawString("text",10,20);
    for(Point p : PaintBrushImage.points)
      drawPoint(g, p);
    if(previous_point != null) {
      draw_point(g, previous_point);
    }
    if(end_point_rectangle != null) {
      draw_rect(g, start_point_rectangle, end_point_rectangle);
    }
    if((line_start != null) && (line_end != null)) {
      draw_line(g, line_start, line_end);
    }
    if(previous_point != null) {
      draw_point(g, previous_point);
    }
    if(MyPanel.showCode) {
      image.ascii(g);
    }
  }
}

