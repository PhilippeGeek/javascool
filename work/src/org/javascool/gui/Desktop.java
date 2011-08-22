/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.gui;

import java.awt.Component;
import javax.swing.JFrame;
import org.javascool.Core;
import org.javascool.widgets.HtmlDisplay;
import org.javascool.widgets.MainFrame;

// Used to define the frame

/** Définit les functions d'interaction avec l'interface graphique de JavaScool.
 *
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
  public JFrame getFrame() {
    if(frame == null)
      frame = (new MainFrame() {
                 @Override
                 public boolean isClosable() {
                   return org.javascool.gui.Desktop.getInstance().isClosable();
                 }
               }
               ).reset(Core.title, Core.logo, JVSPanel.getInstance());
    return frame;
  }
  private MainFrame frame;

  /** Ouvre un fichier dans l'éditeur.
   * @param location L'URL (Universal Resource Location) du fichier.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de l'exécution.
   */
  public void addFile(String location) {
    // @todo a implementer
    throw new RuntimeException("Non implémenté");
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
  public void addTab(String label, Component pane, String title, String icon, boolean east, boolean show) {
    // @todo a implementer
    throw new RuntimeException("Non implémenté");
  }
  /**
   * @see #addTab(String, Component, String, String, boolean, boolean)
   */
  public void addTab(String label, Component pane, String title, String icon, boolean east) {
    addTab(label, pane, title, icon, east, true);
  }
  /**
   * @see #addTab(String, Component, String, String, boolean, boolean)
   */
  public void addTab(String label, Component pane, String title, String icon) {
    addTab(label, pane, title, icon, true, true);
  }
  /**
   * @see #addTab(String, Component, String, String, boolean, boolean)
   */
  public void addTab(String label, Component pane, String title) {
    addTab(label, pane, title, null, true, true);
  }
  /**
   * @see #addTab(String, Component, String, String, boolean, boolean)
   */
  public void addTab(String label, Component pane) {
    addTab(label, pane, "", null, true, true);
  }
  /** Demande la fermeture du desktop à la fin du programme.
   * @return La valeur true si le desktop peut être fermé sans dommage pour l'utilisateur, sinon la valeur fausse.
   */
  public boolean isClosable() {
    return JVSPanel.getInstance().close();
  }
}

