/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import org.javascool.core.ProgletEngine;
import org.javascool.macros.Macros;

/** Ecran d'accueil de Java's cool
 * Il présente toutes les activités présentes dans le jar sous la forme d'un
 * panneau d'icones avec le nom des proglets respectives.
 * @see org.javascool.core.ProgletEngine
 */
class JVSStartPanel extends JScrollPane {

    private static final long serialVersionUID = 1L;
    private static JVSStartPanel jvssp;

    public static JVSStartPanel getInstance() {
        if (jvssp == null) {
            jvssp = new JVSStartPanel(JVSStartPanel.shortcutPanel());
        }
        return jvssp;
    }

    private JVSStartPanel(JPanel panel) {
        super(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ToolTipManager.sharedInstance().setInitialDelay(75);
    }

    /** Dessine le JPanel en listant les proglets
     * @see ProgletEngine
     * @return Le JPanel dessiné
     */
    private static JPanel shortcutPanel() {
        //JPanel vertical = new JPanel();
        //vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
        //vertical.add(Box.createVerticalGlue());
        JPanel shortcuts = new JPanel();
        shortcuts.setLayout(new GridLayout(0, 4));
        //JScrollPane jsp = new JScrollPane(shortcuts);
        //shortcuts.add(Box.createHorizontalGlue());
        for (ProgletEngine.Proglet proglet : ProgletEngine.getInstance().getProglets()) {
            shortcuts.add(JVSStartPanel.createShortcut(Macros.getIcon(proglet.getIcon()), proglet.getName(), proglet.getTitle(), new ProgletLoader(proglet.getName())));
        }
        //shortcuts.add(Box.createHorizontalGlue());
        //vertical.add(jsp);
        //vertical.add(Box.createVerticalGlue());
        return shortcuts;
    }

    /** Cette classe permet de lançer une Proglet */
    private static
            class ProgletLoader implements Runnable {

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
    private static JPanel createShortcut(ImageIcon icon, String name, String title, final Runnable start) {
        JPanel panel = new JPanel();
        
        JButton label = new JButton(name, icon);
        label.setToolTipText(title);
        label.setPreferredSize(new Dimension(160, 160));
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(label);
        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                start.run();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        return panel;
    }
}