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
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.javascool.Utils;

/**
 *
 * @author Philippe Vienne
 */
public class JVSStartPanel extends JPanel{
    
    private HashMap proglets=new HashMap();
    
    public JVSStartPanel(){
        this.setupLayout();
        this.add(this.setupShortcutPanel());
    }
    
    private void setupLayout(){
        BorderLayout bl=new BorderLayout(10,10);
        this.setLayout(bl);
    }
    private JPanel setupShortcutPanel(){
        JPanel vertical=new JPanel();
        vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
        vertical.add(Box.createVerticalGlue());
        JPanel shortcuts=new JPanel();
        //shortcuts.setLayout(new BorderLayout(10,10));
        shortcuts.add(Box.createHorizontalGlue());
        shortcuts.add(this.createShortcut("scripts", "Ingrédients des algotrithmes",new Runnable(){

            @Override
            public void run() {
                JVSMainPanel.loadProglet("ingredients");
            }
        
        }));
        //shortcuts.add(this.createShortcut("maths", "Mathématiques"));
        shortcuts.add(this.createShortcut("games", "Créer un jeu",new Runnable(){

            @Override
            public void run() {
                JVSMainPanel.loadProglet("game");
            }
        
        })); 
        shortcuts.add(Box.createHorizontalGlue());
        vertical.add(shortcuts);
        vertical.add(Box.createVerticalGlue());
        return vertical;
    }
    private JPanel createShortcut(String icon,String title,final Runnable start){
        JPanel panel=new JPanel();
        //panel.setLayout(new BorderLayout(10,10));
        JButton label=new JButton(title,Utils.getIcon("org/javascool/doc-files/icons/"+icon+".png"));
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        panel.add(label);
        label.addMouseListener(new MouseListener(){

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
