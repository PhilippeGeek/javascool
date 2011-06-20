/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.javascool.JvsMain;

/**
 *
 * @author philien
 */
public class JVSMainPanel extends JPanel{
    
    private JVSToolBar toolbar=new JVSToolBar();
    private JVSTabs tabs=new JVSTabs();
    
    public JVSMainPanel(){
        this.setVisible(true);
        this.setupViewLayout();
        this.setupToolBar();
        this.setupMainPanel();
    }
    
    /** Setup the Border Layout for the JPanel */
    private void setupViewLayout(){
        BorderLayout layout=new BorderLayout();
        this.setLayout(layout);
    }
    
    /** Setup the ToolBar */
    private void setupToolBar(){
        // Add Buttons here
        this.add(toolbar, BorderLayout.NORTH);
    }
    
    /** Setup Main Panel */
    private void setupMainPanel(){
        //tabs.add("Test", JvsMain.logo16, new JPanel());
        tabs.add("Editeur", "", new org.javascool.editor.JVSEditor());
        this.add(tabs,BorderLayout.CENTER);
    }
    
    /** Get the toolbar
     * @return The toolbar
     */
    public JVSToolBar getToolBar(){
        return toolbar;
    }
    
}
