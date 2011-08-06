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
import org.javascool.core.Engine;

/**
 *
 * @author Philippe Vienne
 */
class JVSStartPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public JVSStartPanel() {
    this.setupLayout();
    this.add(this.setupShortcutPanel());
  }
  private void setupLayout() {
    BorderLayout bl = new BorderLayout(10, 10);
    this.setLayout(bl);
  }
  private JPanel setupShortcutPanel() {
    JPanel vertical = new JPanel();
    vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
    vertical.add(Box.createVerticalGlue());
    JPanel shortcuts = new JPanel();
    // shortcuts.setLayout(new BorderLayout(10,10));
    shortcuts.add(Box.createHorizontalGlue());
    for(Engine.Proglet proglet : Engine.getInstance().getProglets()) {
      shortcuts.add(this.createShortcut(org.javascool.tools.Macros.getIcon(proglet.getIcon()), proglet.getName(), new Runnable() {
                                          @Override
                                          public void run() {
                                            JVSMainPanel.getInstance().loadProglet(Engine.getInstance().getProglet().getName());
                                          }
                                        }
                                        ));
    }
    shortcuts.add(Box.createHorizontalGlue());
    vertical.add(shortcuts);
    vertical.add(Box.createVerticalGlue());
    return vertical;
  }
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
