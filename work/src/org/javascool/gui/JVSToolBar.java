/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.Box;
import org.javascool.core.Engine;

import org.javascool.widgets.ToolBar;
import javax.swing.JButton;
import org.javascool.widgets.StartStopButton;
import org.javascool.builder.Builder;

/** The JVS top tool bar
 * @author Philippe VIENNE
 */
class JVSToolBar extends ToolBar {
  private static final long serialVersionUID = 1L;

  /** Boutons de l'interface. */
  private JButton compileButton;
  private StartStopButton runButton;

  // @bean
  public JVSToolBar() {
    setName("Java's cool ToolBar");
    init();
  }
  protected JVSToolBar(Boolean escapeInit) {
    setName("Java's cool ToolBar");
  }
  /** Initialize the toolBar with default button and setUps */
  private void init() {
    addTool("Nouvelle activité", "org/javascool/widgets/icons/new.png", new Runnable() {
              @Override
              public void run() {
                JVSMainPanel.getInstance().closeProglet();
              }
            }
            );
    addTool("Nouveau fichier", "org/javascool/widgets/icons/new.png", new Runnable() {
              @Override
              public void run() {
                JVSMainPanel.getInstance().newFile();
              }
            }
            );
    addTool("Ouvrir un fichier", "org/javascool/widgets/icons/open.png", new Runnable() {
              @Override
              public void run() {
                JVSMainPanel.getInstance().openFile();
              }
            }
            );
    addTool("Sauver", "org/javascool/widgets/icons/save.png", new Runnable() {
              @Override
              public void run() {
                JVSMainPanel.getInstance().saveFile();
              }
            }
            );
    addTool("Fermer le fichier", "org/javascool/widgets/icons/remove.png", new Runnable() {
              @Override
              public void run() {
                JVSMainPanel.getInstance().closeFile();
              }
            }
            );

    compileButton = addTool("Compiler", "org/javascool/widgets/icons/compile.png", new Runnable() {
                              @Override
                              public void run() {
                                JVSMainPanel.getInstance().compileFile();
                              }
                            }
                            );

    addTool("Executer", runButton = new StartStopButton() {
  private static final long serialVersionUID = 1L;
              @Override
              public void start() {
                Engine.getInstance().doRun();
              }
              @Override
              public void stop() {
                Engine.getInstance().doStop();
              }
              @Override
              public boolean isRunning() {
                return Engine.getInstance().isRunning();
              }
            }
            );

    runButton.setVisible(false);
    // Crée le menu de construction de proglets si pertinent
    if(Builder.hasProglets())
      pbutton = addRightTool("Proglet Builder", new Runnable() {
                               @Override
                               public void run() {
                                 org.javascool.builder.DialogFrame.startProgletMenu(pbutton);
                               }
                             }
                             );
  }
  // @ inner-class-variable
  private JButton pbutton;
  public void enableCompileButton() {
    compileButton.setVisible(true);
    revalidate();
  }
  public void disableCompileButton() {
    compileButton.setVisible(false);
    revalidate();
  }
  public void enableStartStopButton() {
    runButton.setVisible(true);
    revalidate();
  }
  public void disableStartStopButton() {
    runButton.setVisible(false);
    revalidate();
  }
}
