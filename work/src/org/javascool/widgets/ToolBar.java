package org.javascool.widgets;

import javax.swing.JToolBar;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/** Définit une barre d'outils avec intégration de la gestion des actions.
 * @author Philippe Vienne
 * @see <a href="ToolBar.java.html">code source</a>
 * @serial exclude
 */
public class ToolBar extends JToolBar {
  private static final long serialVersionUID = 1L;
  // @bean 
  public ToolBar() {}

  /** Table des boutons indexés par leurs noms. */
  private HashMap<String, JComponent> buttons = new HashMap<String, JComponent>();
  /** Table des actions associées au bouton. */
  private HashMap<AbstractButton, Runnable> actions = new HashMap<AbstractButton, Runnable>();
    
  /** Initialize la barre de boutons et efface tous les élements. */
  public void reset() {
    setVisible(false);
    revalidate();
    removeAll();
    buttons.clear();
    actions.clear();
    setVisible(true);
    revalidate();
  }

  /** Ajoute un bouton à la barre d'outils.
   * @param label Nom du bouton. Chaque bouton/item/étiquette doit avoir un nom différent.
   * @param icon  Icone du bouton. Si null le bouton est montré sans icone.
   * @param action Action associée au bouton.
   * @return Le bouton ajouté.
   */
  public final JButton addTool(String label, String icon, Runnable action) {
    JButton button = icon == null ? new JButton(label) : new JButton(label, org.javascool.widgets.Macros.getIcon(icon));
    button.addActionListener(new ActionListener() {
	
	@Override
	  public void actionPerformed(ActionEvent e) {
	  actions.get((AbstractButton) e.getSource()).run();
	}
      });
    add(button);
    buttons.put(label, button);
    actions.put(button, action);
    revalidate();
    button.setName(label);
    return button;
  }

  /** Ajoute un composant à la bare d'outils.
   * @param id Nom du composant (ce nom restera invisible). Chaque bouton/item/étiquette doit avoir un nom différent.
   * @param component Le composant à ajouter.
   */
  public void addTool(String id, JComponent component) {   
    add(component);
    buttons.put(id, component);
    revalidate();
  }

  /** Efface un composant de la barre d'outils. */
  public void delTool(String label) {
    if (buttons.containsKey(label)) {
      remove(buttons.get(label));
      buttons.remove(label);
      if (actions.containsKey(buttons.get(label)))
	actions.remove(buttons.get(label));
      setVisible(false);
      revalidate();
      setVisible(true);
      revalidate();
    }
  }

  /** Définit menu à droite de la barre d'outil.
   * <p>Un unique menu peut être ajouté, le redéfinir efface tout le menu précédent.
   * @param title Titre de la barre d'outil. 
   * @return Le menu ajouté.
   */
  public JPopupMenu setRightMenu(String title) {        
    if (jPopupMenu != null)
      remove(jPopupMenu);
    jPopupMenu = new JPopupMenu();
    add(Box.createHorizontalGlue());
    add(jPopupMenu);
    return jPopupMenu;
  }
  private JPopupMenu jPopupMenu = null;
  
  /** Ajoute une action au menu de droite.
   * @param title Nom du bouton. Chaque bouton/item/étiquette doit avoir un nom différent.
   * @param action Action associée au bouton.
   * @return Le bouton ajouté.
   */
  public JMenuItem addRightTool(String title, Runnable action) {        
    JMenuItem menuitem = new JMenuItem("Installer un sketchbook");
    menuitem.addActionListener(new ActionListener() {
	@Override
	  public void actionPerformed(ActionEvent e) {
	  actions.get((AbstractButton) e.getSource()).run();
	}
      });
    jPopupMenu.add(menuitem);
    return menuitem;
  }

  /** Ajoute un séparateur au menu de droite. */
  public void addRightMenuSeparator() {
    jPopupMenu.addSeparator();
  }    
}
