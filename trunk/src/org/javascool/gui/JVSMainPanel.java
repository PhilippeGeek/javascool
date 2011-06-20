/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author philien
 */
public class JVSMainPanel extends JPanel{
    
    private JVSToolBar toolbar=new JVSToolBar();
    
    public JVSMainPanel(){
        this.setVisible(true);
        this.setupViewLayout();
        this.setupToolBar();
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
    
    /** Get the toolbar
     * @return The toolbar
     */
    public JVSToolBar getToolBar(){
        return toolbar;
    }
    
}
