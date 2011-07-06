/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import javax.swing.JPanel;

/** Editor interface
 * This interface define functions that will be in all editors
 * It must extends from a JPanel to include it in a tab
 * The editor is included in a FileEditorTabs
 * @see FileEditorTabs
 * @author Philippe Vienne
 */
public interface Editor{
    
    /** Set the code in the editor
     * @param text The text to set
     */
    public void setText(String text);
    /** Get the code in the editor
     * @return A string which contain the code
     */
    public String getText();
    
}
