package org.javascool.widgets;

import javax.swing.JToolBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.JButton;

/** Définit une barre d'outils avec intégration de la gestion des actions.
 *
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
    left = right = 0;
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
    return addTool(label, icon, action, left++);
  }
  /** Ajoute un bouton à une position précise de la barre d'outil
   * @see #addTool(String, String, Runnable)
   */
  private JButton addTool(String label, String icon, Runnable action, int where) {
    JButton button = icon == null ? new JButton(label) : new JButton(label, org.javascool.tools.Macros.getIcon(icon));
    button.addActionListener(new ActionListener() {
                               @Override
                               public void actionPerformed(ActionEvent e) {
                                 actions.get((AbstractButton) e.getSource()).run();
                               }
                             }
                             );
    add(button, where);
    if(buttons.containsKey(label)) throw new IllegalArgumentException("Chaque bouton/item/étiquette doit avoir un nom différent, mais le bouton «" + label + "» est en doublon");
    buttons.put(label, button);
    actions.put(button, action);
    revalidate();
    return button;
  }
  /** Ajoute un composant à la bare d'outils.
   * @param id Nom du composant (ce nom restera invisible). Chaque bouton/item/étiquette doit avoir un nom différent.
   * @param component Le composant à ajouter.
   */
  public void addTool(String id, JComponent component) {
    add(component, left++);
    if(buttons.containsKey(id)) throw new IllegalArgumentException("Chaque bouton/item/étiquette doit avoir un nom différent, mais le bouton «" + id + "» est en doublon");
    buttons.put(id, component);
    revalidate();
  }
  /** Efface un composant de la barre d'outils. */
  public void delTool(String label) {
    if(buttons.containsKey(label)) {
        JComponent c = buttons.get(label);
      remove(c);
      buttons.remove(label);
      if(c instanceof AbstractButton && actions.containsKey((AbstractButton) c))
        actions.remove((AbstractButton) c);
      setVisible(false);
      revalidate();
      setVisible(true);
      revalidate();
    }
  }
  /** Ajoute un composant à la droite de la barre d'outil.
   * @param title Nom du composant (ce nom restera invisible). Chaque bouton/item/étiquette doit avoir un nom différent.
   * @param action Action associée au bouton.
   * @return Le bouton ajouté.
   */
  public JButton addRightTool(String title, Runnable action) {
    if(right == 0)
      add(Box.createHorizontalGlue());
    return addTool(title, null, action, left + (++right));
  }
  private int left = 0, right = 0;
}
