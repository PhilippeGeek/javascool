package org.javascool.widgets;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.javascool.macros.Macros;

/**
 * Crée un JTabbedPane avec des onglets pouvant posséder une croix. Cette classe
 * a fonctionne comme un {@link JTabbedPane} mais lorsque un JComponent avec une
 * propriété TAB_CLOSABLE à true alors il a une croix. Pour éviter que de
 * mauvaises propriétés soit assigné, une fonction static fait en sorte que le
 * JComponent arborde une croix. Afin de vérifier si un onglet peut être fermer,
 * il vous faut faire un "overide" de la fonction isTabClosable.
 */
public class TabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 2304963236664505495L;

	/** ClientProperty pour un composant fermable. */
	public static final String TAB_CLOSABLE = "tab_closable";

	/**
	 * Vérifie si le composant possède une croix pour la fermeture.
	 * 
	 * @param component
	 *            Le composant à vérifier
	 * @return True si il faut afficher la croix, false sinon.
	 */
	private static boolean isComponentClosable(Component component) {
		if (component instanceof JComponent)
			if (((JComponent) component)
					.getClientProperty(TabbedPane.TAB_CLOSABLE) == Boolean.TRUE)
				return true;
		return false;
	}

	/**
	 * Paramètre un composant pour qu'il puisse avoir une croix de fermeture
	 * 
	 * @param component
	 *            Le composant à configurer
	 */
	public static void setComponentClosable(JComponent component) {
		component.putClientProperty(TabbedPane.TAB_CLOSABLE, Boolean.TRUE);
	}

	/** L'UI destiné à peindre les croix */
	private final TabCloseUI closeUI = new TabCloseUI(this);

	/**
	 * Add a tab with an Applet
	 * 
	 * @param name
	 *            The tab name
	 * @param icon
	 *            The link to the icon, can be an empty String
	 * @param panel
	 *            The Applet to show into the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, Component panel) {
		if (!icon.equalsIgnoreCase("")) {
			final ImageIcon logo = Macros.getIcon(icon);
			this.addTab(name, logo, panel);
		} else {
			this.addTab(name, null, panel);
		}
		revalidate();
		return name;
	}

	/**
	 * Add a tab with a JPanel
	 * 
	 * @param name
	 *            The tab name
	 * @param icon
	 *            The link to the icon, can be an empty String
	 * @param panel
	 *            The JPanel to show into the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, JPanel panel) {
		return this.add(name, icon, panel, null);
	}

	/**
	 * Add a tab with a JPanel
	 * 
	 * @param name
	 *            The tab name
	 * @param icon
	 *            The link to the icon, can be an empty String
	 * @param panel
	 *            The JPanel to show into the tab
	 * @param tooltip
	 *            An tooltip for the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, JPanel panel, String tooltip) {
		if (!icon.equalsIgnoreCase("")) {
			final ImageIcon logo = Macros.getIcon(icon);
			this.addTab(name, logo, panel, tooltip);
		} else {
			this.addTab(name, null, panel, tooltip);
		}
		revalidate();
		return name;
	}

	/**
	 * @see JTabbedPane
	 */
	@Override
	public void addTab(String title, Component component) {
		if (TabbedPane.isComponentClosable(component)) {
			super.addTab(title + "    ", component);
		} else {
			super.addTab(title, component);
		}
	}

	/**
	 * Delete a tab
	 * 
	 * @param name
	 *            The tab id
	 */
	public void del(String name) {
		removeTabAt(this.indexOfTab(name));
	}

	/**
	 * Get a JPanel
	 * 
	 * @param name
	 *            The id of JPanel
	 * @return The JPanel
	 */
	public JPanel getPanel(String name) {
		if (getComponentAt(indexOfTab(name)) instanceof JPanel)
			return (JPanel) getComponentAt(indexOfTab(name));
		return null;
	}

	/** Retourne le nom du composant sans les espaces n�cessaire � la croix. */
	public String getTabTitleAt(int index) {
		return super.getTitleAt(index).trim();
	}

	/**
	 * Say if a tab can be closed. By default this function return true but it
	 * can be override. This is in add of {@link ClosableComponent}.isClosable()
	 */
	public boolean isTabClosable(int tabIndex) {
		return true;
	}

	/**
	 * Ajout de la fonction de tracage des croix dans paint.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		closeUI.paint(g);
	}

	/**
	 * @see JTabbedPane
	 */
	@Override
	public void setTitleAt(int index, String title) {
		if (TabbedPane.isComponentClosable(getComponentAt(index))
				&& getTabCount() > 1) {
			title = title + "    ";
		}
		super.setTitleAt(index, title);
	}

	/**
	 * Switch to a tab
	 * 
	 * @param name
	 *            The id of the tab
	 */
	public void switchToTab(String name) {
		setSelectedIndex(this.indexOfTab(name));
	}

	private class TabCloseUI implements MouseListener, MouseMotionListener {
		private final TabbedPane tabbedPane;
		private int closeX = 2, closeY = 2, meX = 0, meY = 0;
		private int selectedTab;
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
				drawColored(g2, isUnderMouse(x, y) ? Color.RED : Color.WHITE,
						x, y);
			}
		}

		private void drawColored(Graphics2D g2, Color color, int x, int y) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(Color.BLACK);
			g2.drawLine(x, y, x + width, y + height);
			g2.drawLine(x + width, y, x, y + height);
			g2.setColor(color);
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(x, y, x + width, y + height);
			g2.drawLine(x + width, y, x, y + height);

		}

		private boolean isUnderMouse(int x, int y) {
			if (Math.abs(x - meX) < width && Math.abs(y - meY) < height)
				return true;
			return false;
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
				final boolean isToCloseTab = isTabClosable(selectedTab);
				if (isToCloseTab && selectedTab > -1) {
					tabbedPane.removeTabAt(selectedTab);
				}
				selectedTab = tabbedPane.getSelectedIndex();
			}
		}

		public void paint(Graphics g) {

			final int tabCount = tabbedPane.getTabCount();
			for (int j = 0; j < tabCount; j++)
				if (TabbedPane.isComponentClosable(getComponentAt(j))) {
					final int x = tabbedPane.getBoundsAt(j).x
							+ tabbedPane.getBoundsAt(j).width - width - 6;
					final int y = tabbedPane.getBoundsAt(j).y + 7;
					if (tabCount > 1) {
						setTitleAt(j, getTabTitleAt(j));
						drawClose(g, x, y);
					} else {
						setTitleAt(j, getTabTitleAt(j));
					}
				}
		}

	}

}
