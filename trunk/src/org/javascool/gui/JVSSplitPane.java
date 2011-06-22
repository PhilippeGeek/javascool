/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author philien
 */
public class JVSSplitPane extends JSplitPane implements JVSGuiObject{
    
    private Component left_pane;
    private Component right_pane;
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
        JVSFileEditorTabs editor=new JVSFileEditorTabs();
        left_pane=editor;
        this.setLeftComponent(editor);
        this.setRightComponent(right);
        this.setVisible(true);
    }
    
    /** Get the left component */
    @Override
    public Component getLeftComponent(){
        return left_pane;
    }
    
    /** Get the right component */
    @Override
    public Component getRightComponent(){
        return right_pane;
    }

    @Override
    public JVSMainPanel getMainPanel() {
        return ((JVSMainPanel)this.getParent());
    }
}
