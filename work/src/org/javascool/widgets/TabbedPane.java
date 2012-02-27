package org.javascool.widgets;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.javascool.macros.Macros;

/** Propose un JTabbedPane avec des onglets pouvant posséder une croix. 
 * <p>Cette classe fonctionne comme un {@link JTabbedPane}; de plus un composant avec la propriété TAB_CLOSABLE possède une croix qui permet de le fermer.</p>
 */
class TabbedPane extends JTabbedPane {

  private static final long serialVersionUID = 1L;

  /** ClientProperty pour un composant fermable. */
  public static final String TAB_CLOSABLE = "tab_closable";

  // Vérifie si le composant possède une croix pour la fermeture.
  private static boolean isComponentClosable(Component component) {
    return component instanceof JComponent && ((JComponent) component).getClientProperty(TabbedPane.TAB_CLOSABLE) == Boolean.TRUE;
  }
  /** Indique que ce composant a une croix de fermeture.
   * @param Le composant à marquer.
   * @param Si true indique qu'il y a une croix de fermeture (valeur par défaut), sinon false.
   */
  public static void setComponentClosable(JComponent component, boolean closable) {
    component.putClientProperty(TabbedPane.TAB_CLOSABLE, closable ? Boolean.TRUE: Boolean.FALSE);
  }

  /** Ajoute un composant à ce panneau à onglet.
   * @param title Le nom du composant.
   * @param icon Le nom de l'icone de ce composant.
   * @param component Le composant à ajouter.
   * @param tooltip Un petit titre à afficher quand le curseur passe sur l'onglet du composant.
   * @param closable Si true indique qu'il y a une croix de fermeture (valeur par défaut), sinon false.
   * <p>Ceci ne fonctionne que si il s'agit d'un composant Swing.</p>
   */
  public void addTab(String title, String icon, Component component, String tooltip, boolean closable) {
    addTab(title, Macros.getIcon(icon), component, tooltip, closable);
  }
  /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  public void addTab(String title, Icon icon, Component component, String tooltip, boolean closable) {
    if (component instanceof JComponent)
      setComponentClosable((JComponent) component, closable);
    addTab(getTabbedTitle(component, title), icon, component, tooltip);
  }
  /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  public void addTab(String title, String icon, Component component, String tooltip) {
    addTab(title, icon, component, tooltip, true);
  }
 /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  @Override
  public void addTab(String title, Icon icon, Component component, String tooltip) {
    addTab(title, icon, component, tooltip, true);
  }
  /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  public void addTab(String title, String icon, Component component) {
    addTab(title, icon, component, null, true);
  }
  /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  @Override
  public void addTab(String title, Icon icon, Component component) {
    addTab(title, icon, component, null, true);
  }
  /**
   * @see #addTab(String, String, Component, String, boolean)
   */
  @Override
  public void addTab(String title, Component component) {
    addTab(title, (Icon) null, component, null, true);
  }

  // Renvoie un titre formaté pour tenir compte de l'affichage de la crois.
  private String getTabbedTitle(Component component, String title) {
    return title.trim() + (isComponentClosable(component) ? "    " : "");
  }
  // Gère les espaces ajoutés au titre pour afficher la croix
  @Override
  public String getTitleAt(int index) {
    return super.getTitleAt(index).trim();
  }
  @Override
  public void setTitleAt(int index, String title) {
    super.setTitleAt(index, getTabbedTitle(getComponentAt(index), title));
  }   
  @Override
  public int indexOfTab(String title) {
    return super.indexOfTab(title) != -1 ? super.indexOfTab(title) : super.indexOfTab(title + "    ");
  }

  // On ajoute la fonction de traçage des croix
  @Override
    public void paint(Graphics g) {
    super.paint(g);
    closeUI.paint(g);
  }
  // Creation d l'UI destiné à peindre les croix.
  private final TabCloseUI closeUI = new TabCloseUI(this);

  // Cette classe implémente la gestion des crois de fermeture des composants tabulés.
  private class TabCloseUI implements MouseListener, MouseMotionListener {
    private final TabbedPane tabbedPane;
    private int closeX = 0, closeY = 0, meX = 0, meY = 0;
    private int selectedTab = -1;		
    private final ImageIcon img = Macros.getIcon("org/javascool/widgets/icons/close.png");
    private final int width = 7, height = 7;
    private final Rectangle rectangle = new Rectangle(0, 0, width, height);

    public TabCloseUI(TabbedPane pane) {
      tabbedPane = pane;
      tabbedPane.addMouseMotionListener(this);
      tabbedPane.addMouseListener(this);
    }

    private boolean closeUnderMouse(int x, int y) {
      rectangle.x = closeX;
      rectangle.y = closeY;
      return rectangle.contains(x, y);
    }

    private void controlCursor() {
      if (tabbedPane.getTabCount() > 0)
	if (closeUnderMouse(meX, meY)) {
	  tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	} else {
	  tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
    }

    private void drawClose(Graphics g, int x, int y) {
      if (tabbedPane != null && tabbedPane.getTabCount() > 0) {
	final Graphics2D g2 = (Graphics2D) g;
	g2.drawImage(img.getImage(), x, y, x+width, y+height, 0, 0, img.getIconWidth(), img.getIconHeight(), null);
      }
    }

    @Override
      public void mouseClicked(MouseEvent me) {
    }

    @Override
      public void mouseDragged(MouseEvent me) {
    }

    @Override
      public void mouseEntered(MouseEvent me) {
    }

    @Override
      public void mouseExited(MouseEvent me) {
    }

    @Override
      public void mouseMoved(MouseEvent me) {
      meX = me.getX();
      meY = me.getY();
      if (mouseOverTab(meX, meY)) {
	controlCursor();
	tabbedPane.repaint();
      }
    }

    private boolean mouseOverTab(int x, int y) {
      final int tabCount = tabbedPane.getTabCount();
      for (int j = 0; j < tabCount; j++)
	if (tabbedPane.getBoundsAt(j).contains(meX, meY)
	    && TabbedPane.isComponentClosable(getComponentAt(j))) {
	  selectedTab = j;
	  closeX = tabbedPane.getBoundsAt(j).x
	    + tabbedPane.getBoundsAt(j).width - width - 6;
	  closeY = tabbedPane.getBoundsAt(j).y + 7;
	  return true;
	}
      return false;
    }

    @Override
      public void mousePressed(MouseEvent me) {
    }

    @Override
      public void mouseReleased(MouseEvent me) {
      if (closeUnderMouse(me.getX(), me.getY())) {
	if (0 <= selectedTab && TabbedPane.isComponentClosable(getComponentAt(selectedTab)))
	  tabbedPane.removeTabAt(selectedTab);
	selectedTab = tabbedPane.getSelectedIndex();
      }
    }
    
    public void paint(Graphics g) {
      int tabCount = tabbedPane.getTabCount();
      for (int j = 0; j < tabCount; j++)
	if (TabbedPane.isComponentClosable(getComponentAt(j))) {
	  final int x = tabbedPane.getBoundsAt(j).x
	    + tabbedPane.getBoundsAt(j).width - width - 6;
	  final int y = tabbedPane.getBoundsAt(j).y + 7;
	  if (tabCount > 1) {
	    setTitleAt(j, getTitleAt(j));
	    drawClose(g, x, y);
	  } else {
	    setTitleAt(j, getTitleAt(j));
	  }
	}
    }
  }
}
