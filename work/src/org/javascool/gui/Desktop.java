/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.gui;

import java.awt.Frame;
import java.awt.Container;
import org.javascool.Core;
import org.javascool.widgets.HtmlDisplay;
import org.javascool.widgets.ToolBar;

// Used to define the frame
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;

/** Définit les functions d'interaction avec l'interface graphique de JavaScool.
 * @see <a href="Desktop.java.html">code source</a>
 * @serial exclude
 */
public class Desktop {
  // @static-instance

  /** Crée et/ou renvoie l'unique instance du desktop.
   * <p>Une application ne peut définir qu'un seul desktop.</p>
   */
  public static Desktop getInstance() {
    if(desktop == null)
      desktop = new Desktop();
    return desktop;
  }
  private static Desktop desktop = null;
  private Desktop() {}

  /** Renvoie la fenêtre racine de l'interface graphique. */
  public Frame getFrame() {
    if(frame == null)
      frame = showFrame(Core.title, Core.logo, JVSMainPanel.getInstance());
    return frame;
  }
  private JFrame frame;
  /** Ouvre une fenêtre principale pour lancer une application. 
   * @param title Le titre de la fenêtre.
   * @param icon L'icône de la fenêtre.
   * @param panel Le composant graphique à afficher.
   */
  static JFrame showFrame(String title, String icon, JComponent panel) {
    JFrame frame = new JFrame();
    frame.setTitle(title);
    ImageIcon image = org.javascool.tools.Macros.getIcon(icon);
    if(image != null)
      frame.setIconImage(image.getImage());
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
                              @Override
                              public void windowClosing(WindowEvent e) {
                                if(org.javascool.gui.Desktop.getInstance().close()) {
                                  e.getWindow().setVisible(false);
                                  e.getWindow().dispose();
                                  System.exit(0);
                                }
                              }
                            }
                            );
    frame.pack();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
    return frame;
  }
  /** Ouvre un fichier dans l'éditeur.
   * @param location L'URL (Universal Resource Location) du fichier.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de l'exécution.
   */
  public void addFile(String location) { throw new RuntimeException("Non implémenté");
  }
  /** Ouvre un document HTML dans l'interface.
   * @param location L'URL (Universal Resource Location) du fichier.
   * @param east Affiche dans le panneau de droite si vrai (valeur par défaut), sinon le panneau de gauche.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de l'exécution.
   */
  public void addTab(String location, boolean east) {
    addTab("Document", new HtmlDisplay().setPage(location), "", null, east, true);
  }
  /**
   * @see #addTab(String, boolean)
   */
  public void addTab(String location) {
    addTab(location, true);
  }
  /** Ajoute un composant graphique à l'interface.
   * @param label Nom du bouton. Chaque composant doit avoir un nom différent.
   * @param pane  Le composant à ajouter.
   * @param title Une description d'une ligne du composant.
   * @param icon  Icone descriptive du composant. Pas d'icone si la valeur est nulle ou le fichier inconnu.
   * @param east Affiche dans le panneau de droite si vrai (valeur par défaut), sinon le panneau de gauche.
   * @param show Rend le composant visible si vrai (valeur par défaut), sinon ne modifie pas l'onglet affiché.
   */
  public void addTab(String label, Container pane, String title, String icon, boolean east, boolean show) { throw new RuntimeException("Non implémenté");
  }
  /**
   * @see #addTab(String, Container, String, String, boolean, boolean)
   */
  public void addTab(String label, Container pane, String title, String icon, boolean east) {
    addTab(label, pane, title, icon, east, true);
  }
  /**
   * @see #addTab(String, Container, String, String, boolean, boolean)
   */
  public void addTab(String label, Container pane, String title, String icon) {
    addTab(label, pane, title, icon, true, true);
  }
  /**
   * @see #addTab(String, Container, String, String, boolean, boolean)
   */
  public void addTab(String label, Container pane, String title) {
    addTab(label, pane, title, null, true, true);
  }
  /**
   * @see #addTab(String, Container, String, String, boolean, boolean)
   */
  public void addTab(String label, Container pane) {
    addTab(label, pane, "", null, true, true);
  }
  /** Renvoie un accès à la barre d'outil de l'interface.
   * @return La barre d;outil.
   */
  public ToolBar getToolBar() { throw new RuntimeException("Non implémenté");
  }
  /** Demande la fermeture du desktop à la fin du programme.
   * @return La valeur true si le desktop peut être fermé sans dommage pour l'utilisateur, sinon la valeur fausse.
   */
  public boolean close() {
    return true;
  }
}

