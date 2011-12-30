package org.javascool.gui.editor;
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

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.javascool.macros.Macros;

/** Create a JTabbedPane with closable Tabs
 * Add components which implements ClosableComponent and they will have a cross to be closed.
 */
public class JVSTabs extends JTabbedPane{
 
	private static final long serialVersionUID = 2304963236664505495L;

	public static final String TAB_CLOSABLE="tab_closable";

	private TabCloseUI closeUI = new TabCloseUI(this);

	private static boolean isComponentClosable(Component component){
		if(component instanceof JComponent)
			if(((JComponent) component).getClientProperty(TAB_CLOSABLE)==Boolean.TRUE)
				return true;
		return false;
	}

	public static void setComponentClosable(JComponent component){
		component.putClientProperty(TAB_CLOSABLE,Boolean.TRUE);
	}

	public void paint(Graphics g){
		super.paint(g);
		closeUI.paint(g);
	}

	public void addTab(String title, Component component) {
		if(isComponentClosable(component))
			super.addTab(title+"    ", component);
		else
			super.addTab(title, component);
	}

	public void setTitleAt(int index, String title){
		if(isComponentClosable(getComponentAt(index))&&getTabCount()>1)
			title=title+"    ";
		super.setTitleAt(index, title);
	}


	public String getTabTitleAt(int index) {
		return super.getTitleAt(index).trim();
	}

	private class TabCloseUI implements MouseListener, MouseMotionListener {
		private JVSTabs  tabbedPane;
		private int closeX = 2 ,closeY = 2, meX = 0, meY = 0;
		private int selectedTab;
		private final int  width = 7, height = 7;
		private Rectangle rectangle = new Rectangle(0,0,width, height);
		public TabCloseUI(JVSTabs pane) {

			tabbedPane = pane;
			tabbedPane.addMouseMotionListener(this);
			tabbedPane.addMouseListener(this);
		}
		public void mouseEntered(MouseEvent me) {}
		public void mouseExited(MouseEvent me) {}
		public void mousePressed(MouseEvent me) {}
		public void mouseClicked(MouseEvent me) {}
		public void mouseDragged(MouseEvent me) {}



		public void mouseReleased(MouseEvent me) {
			if(closeUnderMouse(me.getX(), me.getY())){
				boolean isToCloseTab = isTabClosable(selectedTab);
				if (isToCloseTab && selectedTab > -1){			
					tabbedPane.removeTabAt(selectedTab);
				}
				selectedTab = tabbedPane.getSelectedIndex();
			}
		}

		public void mouseMoved(MouseEvent me) {	
			meX = me.getX();
			meY = me.getY();			
			if(mouseOverTab(meX, meY)){
				controlCursor();
				tabbedPane.repaint();
			}
		}

		private void controlCursor() {
			if(tabbedPane.getTabCount()>0)
				if(closeUnderMouse(meX, meY)){
					tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				else{
					tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}	
		}

		private boolean closeUnderMouse(int x, int y) {		
			rectangle.x = closeX;
			rectangle.y = closeY;
			return rectangle.contains(x,y);
		}

		public void paint(Graphics g) {

			int tabCount = tabbedPane.getTabCount();
			for(int j = 0; j < tabCount; j++)
				if(isComponentClosable(getComponentAt(j))){			
					int x = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width -width-6;
					int y = tabbedPane.getBoundsAt(j).y +7;
					if(tabCount>1){
						setTitleAt(j, getTabTitleAt(j));
						drawClose(g,x,y);
					} else {
						setTitleAt(j, getTabTitleAt(j));
					}
				}
		}

		private void drawClose(Graphics g, int x, int y) {
			if(tabbedPane != null && tabbedPane.getTabCount() > 0){
				Graphics2D g2 = (Graphics2D)g;				
				drawColored(g2, isUnderMouse(x,y)? Color.RED : Color.WHITE, x, y);
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
			if(Math.abs(x-meX)<width && Math.abs(y-meY)<height )
				return  true;		
			return  false;
		}

		private boolean mouseOverTab(int x, int y) {
			int tabCount = tabbedPane.getTabCount();
			for(int j = 0; j < tabCount; j++)
				if(tabbedPane.getBoundsAt(j).contains(meX, meY)&&(isComponentClosable(getComponentAt(j)))){
					selectedTab = j;
					closeX = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width -width-6;
					closeY = tabbedPane.getBoundsAt(j).y +7;					
					return true;
				}
			return false;
		}

	}
	
	/** Add a tab with a JPanel
	 * @param name The tab name
	 * @param icon The link to the icon, can be an empty String
	 * @param panel The JPanel to show into the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, JPanel panel) {
		return this.add(name, icon, panel, null);
	}
	/** Add a tab with an Applet
	 * @param name The tab name
	 * @param icon The link to the icon, can be an empty String
	 * @param panel The Applet to show into the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, Component panel) {
		if(!icon.equalsIgnoreCase("")) {
			ImageIcon logo = Macros.getIcon(icon);
			this.addTab(name, logo, panel);
		} else
			this.addTab(name, null, panel);
		this.revalidate();
		return name;
	}
	/** Add a tab with a JPanel
	 * @param name The tab name
	 * @param icon The link to the icon, can be an empty String
	 * @param panel The JPanel to show into the tab
	 * @param tooltip An tooltip for the tab
	 * @return The new id of your tab
	 */
	public String add(String name, String icon, JPanel panel, String tooltip) {
		if(!icon.equalsIgnoreCase("")) {
			ImageIcon logo = Macros.getIcon(icon);
			this.addTab(name, logo, panel, tooltip);
		} else
			this.addTab(name, null, panel, tooltip);
		this.revalidate();
		return name;
	}
	/** Get a JPanel
	 * @param name The id of JPanel
	 * @return The JPanel
	 */
	public JPanel getPanel(String name) {
		if(getComponentAt(indexOfTab(name)) instanceof JPanel)
			return (JPanel) getComponentAt(indexOfTab(name));
		return null;
	}
	/** Delete a tab
	 * @param name The tab id
	 */
	public void del(String name) {
		this.removeTabAt(this.indexOfTab(name));
	}
	/** Switch to a tab
	 * @param name The id of the tab
	 */
	public void switchToTab(String name) {
		this.setSelectedIndex(this.indexOfTab(name));
	}

	/** Say if a tab can be closed.
	 * By default this function return true but it can be override.
	 * This is in add of {@link ClosableComponent}.isClosable()
	 */
	public boolean isTabClosable(int tabIndex) {
		return true;
	}


}
