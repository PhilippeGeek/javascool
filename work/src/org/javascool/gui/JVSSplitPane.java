/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.Component;
import javax.swing.JSplitPane;
import org.javascool.widgets.Console;

/** The main Split Pane
 * This JSplitPane is used to place the FileEditor and the rights tabs
 * @author Philippe VIENNE
 */
class JVSSplitPane extends JSplitPane {
  private static final long serialVersionUID = 1L;

  /** File editor tabs */
  private static JVSFileEditorTabs editor;
  /** The right tabs */
  private static JVSTabs tabs;

  /** Construct a new JVSSplitPane */
  public JVSSplitPane() {
    super(JSplitPane.HORIZONTAL_SPLIT);
    JVSFileEditorTabs editorToSet = new JVSFileEditorTabs();
    JVSSplitPane.editor = editorToSet;
    JVSSplitPane.tabs = new JVSWidgetPanel();
    JVSSplitPane.tabs.add("Console", "", Console.getInstance());
    this.setLeftComponent(JVSSplitPane.editor);
    this.setRightComponent(JVSSplitPane.tabs);
    this.setVisible(true);
    this.validate();
  }
  /** Get the left component */
  @Override
  public Component getLeftComponent() {
    return JVSSplitPane.editor;
  }
  /** Get the right component */
  @Override
  public Component getRightComponent() {
    return JVSSplitPane.tabs;
  }
}
