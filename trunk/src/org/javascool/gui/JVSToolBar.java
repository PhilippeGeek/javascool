/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import org.javascool.JvsMain;
import org.javascool.Utils;

/**
 *
 * @author philien
 */
public class JVSToolBar extends JToolBar {

    /** HashMap for button list.
     * The map associate a String to a JButton
     */
    private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    /** HashMap for action list.
     * The map associate a String to a Runnable
     */
    private HashMap<JButton, Runnable> actions = new HashMap<JButton, Runnable>();

    /** Create the JVSToolBar
     * 
     */
    public JVSToolBar() {
        super("Java's cool ToolBar");
        JLabel soft = new JLabel(Utils.getIcon(org.javascool.JvsMain.logo32));
        soft.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new JVSAboutFrame();
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
        this.add(soft);
    }

    /** Reset all the toolbar */
    public void reset() {
        setVisible(false);
        revalidate();
        this.removeAll();
        this.add(new JLabel(Utils.getIcon(org.javascool.JvsMain.logo32)));
        buttons.clear();
        actions.clear();
        setVisible(true);
        revalidate();
    }

    /** Adds a button to the toolbar.
     * @param label Button label.
     * @param icon Button icon. If null do not show icon.
     * @param action Button action.
     * @return The added button.
     */
    public final JButton addTool(String label, String icon, Runnable action) {
        delTool(label);
        JButton button = icon == null ? new JButton(label) : new JButton(label, Utils.getIcon(icon));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actions.get((JButton) e.getSource()).run();
            }
        });
        add(button);
        buttons.put(label, button);
        actions.put(button, action);
        revalidate();
        return button;
    }

    /** Removes a button from the tool bar. */
    public void delTool(String label) {
        if (buttons.containsKey(label)) {
            remove(buttons.get(label));
            actions.remove(buttons.get(label));
            buttons.remove(label);
            setVisible(false);
            revalidate();
            setVisible(true);
            revalidate();
        }
    }
}
