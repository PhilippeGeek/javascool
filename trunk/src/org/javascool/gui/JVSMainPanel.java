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
    
    public JVSMainPanel(){
        this.setVisible(true);
        this.setViewLayout();
    }
    
    private void setViewLayout(){
        BorderLayout layout=new BorderLayout();
        this.setLayout(layout);
    }
    
}
