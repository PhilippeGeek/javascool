/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import javax.swing.JPanel;
import org.javascool.tools.Proglet;

/**
 *
 * @author Philippe Vienne
 */
public class JVSWidgetPanel extends JVSTabs{
    
    private String progletTabId;
    
    public JVSWidgetPanel(){
        super();
    }
    
    public void setProglet(Proglet proglet){
        this.progletTabId=this.add(proglet.getName(),"", proglet.getPanel());
    }
    
    public JPanel getProgletPanel(){
        return this.getPanel(progletTabId);
    }
    
}
