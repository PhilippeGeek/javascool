/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.javascool.core.ProgletEngine;
import org.javascool.macros.Macros;

/** Ecran d'accueil de Java's cool
 * Il présente toutes les activités présentes dans le jar sous la forme d'un
 * panneau d'icones avec le nom des proglets respectives.
 * @see org.javascool.core.ProgletEngine
 */
class JVSStartPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private static JVSStartPanel jvssp;

  public static JVSStartPanel getInstance() {
    if(jvssp == null)
      jvssp = new JVSStartPanel();
    return jvssp;
  }
  private JVSStartPanel() {
    this.setupLayout();
    this.add(this.setupShortcutPanel());
  }
  /** Configure le Layout du JPanel
   * @see BorderLayout
   */
  private void setupLayout() {
    BorderLayout bl = new BorderLayout(10, 10);
    this.setLayout(bl);
  }
  /** Dessine le JPanel en listant les proglets
   * @see ProgletEngine
   * @return Le JPanel dessiné
   */
  private JPanel setupShortcutPanel() {
    JPanel vertical = new JPanel();
    vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
    vertical.add(Box.createVerticalGlue());
    JPanel shortcuts = new JPanel();
    shortcuts.add(Box.createHorizontalGlue());
    for(ProgletEngine.Proglet proglet : ProgletEngine.getInstance().getProglets())
      shortcuts.add(this.createShortcut(Macros.getIcon(proglet.getIcon()), proglet.getName(), new ProgletLoader(proglet.getName())));
    shortcuts.add(Box.createHorizontalGlue());
    vertical.add(shortcuts);
    vertical.add(Box.createVerticalGlue());
    return vertical;
  }
  /** Cette classe permet de lançer une Proglet */
  private class ProgletLoader implements Runnable {
    private String proglet;

    ProgletLoader(String proglet) {
      this.proglet = proglet;
    }
    @Override
    public void run() {
      JVSPanel.getInstance().loadProglet(proglet);
    }
  }

  /** Créer un pannel avec un bouton capâble de lançer la Proglet */
  private JPanel createShortcut(ImageIcon icon, String title, final Runnable start) {
    JPanel panel = new JPanel();
    JButton label = new JButton(title, icon);
    label.setVerticalTextPosition(JLabel.BOTTOM);
    label.setHorizontalTextPosition(JLabel.CENTER);
    panel.add(label);
    label.addMouseListener(new MouseListener() {
                             @Override
                             public void mouseClicked(MouseEvent e) {
                               start.run();
                             }

                             @Override
                             public void mousePressed(MouseEvent e) {}

                             @Override
                             public void mouseReleased(MouseEvent e) {}

                             @Override
                             public void mouseEntered(MouseEvent e) {}

                             @Override
                             public void mouseExited(MouseEvent e) {}
                           }
                           );
    return panel;
  }
}
