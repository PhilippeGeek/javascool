package org.javascool.gui;

import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.javascool.tools.Utils;

/** Create a tab structure
 * Java's cool Tab Structure is used to make easy JTabbedPane
 * @author Philippe VIENNE
 */
public class JVSTabs extends JTabbedPane {

    protected HashMap<String, JPanel> tabs = new HashMap<String, JPanel>();

    public JVSTabs() {
        super();
    }

    public String add(String name, String icon, JPanel panel) {
        return this.add(name,icon,panel,null);
    }
    
    public String add(String name, String icon, JPanel panel, String tooltip) {
        tabs.put(name, panel);
        if (!icon.equalsIgnoreCase("")) {
            ImageIcon logo = Utils.getIcon(icon);
            this.addTab(name, logo, panel,tooltip);
        }else{
            this.addTab(name, null, panel,tooltip);
        }
        this.revalidate();
        return name;
    }

    public JPanel getPanel(String name){
        return this.tabs.get(name);
    }
    
    public void del(String name) {
        this.removeTabAt(this.indexOfTab(name));
        tabs.remove(name);
    }

    public void switchToTab(String name) {
        this.setSelectedIndex(this.indexOfTab(name));
    }
}
