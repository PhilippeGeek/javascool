/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.javascool.tools.Console;

/**
 *
 * @author philien
 */
public class JVSSplitPane extends JSplitPane implements JVSGuiObject{
    
    private static JVSFileEditorTabs editor;
    private static JVSTabs tabs;
    private int split;
    
    /** Construct a new JVSSplitPane */
    public JVSSplitPane(){
        this(new JPanel(),new JPanel());
    }
    
    /** Construct a new JVSSplitPane
     * Construct and set left and right component
     * @param left The left component
     * @param right The right component
     */
    public JVSSplitPane(Component left,Component right){
        super(JSplitPane.HORIZONTAL_SPLIT);
        JVSFileEditorTabs editorToSet=new JVSFileEditorTabs();
        JVSSplitPane.editor=editorToSet;
        JVSSplitPane.tabs=new JVSTabs();
        JVSSplitPane.tabs.add("Console", "", new Console());
        this.setLeftComponent(JVSSplitPane.editor);
        this.setRightComponent(JVSSplitPane.tabs);
        this.setVisible(true);
    }
    
    /** Get the left component */
    @Override
    public Component getLeftComponent(){
        return JVSSplitPane.editor;
    }
    
    /** Get the right component */
    @Override
    public Component getRightComponent(){
        return JVSSplitPane.tabs;
    }

    @Override
    public JVSMainPanel getMainPanel() {
        return ((JVSMainPanel)this.getParent());
    }
}
