/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.Component;
import javax.swing.JSplitPane;
import org.javascool.JvsMain;
import org.javascool.tools.Console;

/** The main Split Pane
 * This JSplitPane is used to place the FileEditor and the rights tabs
 * @author Philippe VIENNE
 */
public class JVSSplitPane extends JSplitPane{
    
    /** File editor tabs */
    private static JVSFileEditorTabs editor;
    /** The right tabs */
    private static JVSTabs tabs;
    
    /** Construct a new JVSSplitPane */
    public JVSSplitPane(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        JVSFileEditorTabs editorToSet=new JVSFileEditorTabs();
        JVSSplitPane.editor=editorToSet;
        JVSSplitPane.tabs=new JVSTabs();
        JVSSplitPane.tabs.add("Console", "", new Console());
        this.setLeftComponent(JVSSplitPane.editor);
        this.setRightComponent(JVSSplitPane.tabs);
        this.setVisible(true);
        this.validate();
        this.setDividerLocation(500);
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

    /** Get the Main Panel to have main functions */
    private static JVSMainPanel getJvsMainPanel() {
        return JvsMain.getJvsMainPanel();
    }
}
