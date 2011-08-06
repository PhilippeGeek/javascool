/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.applet.Applet;
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
  public void setProglet() {
    if(Engine.getInstance().getProglet().getPane() != null)
      this.progletTabId = this.add("Proglet " + Engine.getInstance().getProglet().getName(), "", Engine.getInstance().getProglet().getPane());
    if(Engine.getInstance().getProglet().getHelp() != null)
      this.add("Aide de la proglet", "", new HtmlDisplay().setPage(Engine.getInstance().getProglet().getHelp()));
  }
  public Applet getProgletPanel() {
    return Engine.getInstance().getProglet().getPane();
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
