/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.Component;
import org.javascool.core.Engine;
import org.javascool.widgets.HtmlDisplay;

/**
 *
 * @author Philippe Vienne
 */
class JVSWidgetPanel extends JVSTabs {
  private static final long serialVersionUID = 1L;

  private String progletTabId;

  public JVSWidgetPanel() {
    super();
  }
  public void setProglet(String name) {
    Engine.Proglet proglet = Engine.getInstance().setProglet(name);
    if(proglet.getPane() != null)
      this.progletTabId = this.add("Proglet " + name, "", proglet.getPane());
    if(proglet.getHelp() != null)
      this.add("Aide de la proglet", "", new HtmlDisplay().setPage(proglet.getHelp()));
  }
  public void focusOnProgletPanel() {
    if(progletTabId != null)
      this.switchToTab(progletTabId);
  }
  public void showConsole() {
    this.setSelectedIndex(this.indexOfTab("Console"));
  }
  public void openWebTab(String url, String tabName) {
    HtmlDisplay memo = new HtmlDisplay();
    memo.setPage(url);
    this.add(tabName, "", memo);
    this.setTabComponentAt(this.indexOfTab(tabName), new TabPanel(this));
    this.setSelectedComponent(memo);
  }
}
