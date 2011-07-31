/*******************************************************************************
*           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
*******************************************************************************/

package org.javascool;

// Used to define the frame
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Used to set Win look and feel
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;

import org.javascool.tools.ErrorCatcher;

/** Lanceur de l'application "apprenant" qui permet de manipuler des «proglets». */
public class Core {
  /** Titre de l'application. */
  public static final String title = "Java's Cool 3.3";  
  /** Numéro de révision de l'application. 
   * Il est obtenu par la commande Unix <tt>svn info | grep Revision | sed 's/.*: //'</tt>
   */
  static final int revision = 362;
  /** Logo pour affichage. */
  public static final String logo = "org/javascool/doc-files/logo.png";

  /** Définit le look and feel de l'applicatiom. */
  static void setUpSystem() {
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	if ("Nimbus".equals(info.getName())) {
	  UIManager.setLookAndFeel(info.getClassName());
	  break;
	}
      }
    } catch (Exception e1) {
      String os = System.getProperty("os.name");
      if(os.startsWith("Windows")) {
	try {
	  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	} catch(Exception e2) {}
      } else {
	try { 
	  System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
	} catch(Exception e2) {}
	try { 
	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch(Exception e2) {
	  System.err.println("Note: Utilisaton du thème Java (et non du système)");
	}
      }
    }
  }
  
  /** Impose que la version de Java soit au moins 1.6. */
  static void checkJavaVersion() {
    if (new Integer(System.getProperty("java.version").replaceFirst("..(.).*", "\1")) < 6) {
      if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
								  new JFrame(),
								  "<html>Vous n'avez pas une version suffisante de Java<br>"
								  + title +" requière Java 1.6 ou plus.<br>"
								  + "Voulez vous être redirigé vers le site de téléchargements ?",
								  "Confirmation",
								  JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)) {
	org.javascool.widgets.Macros.openURL("http://www.java.com/getjava");
	System.exit(-1);
      }
    }
  } 
  
  /** Mets en place le système d'alerte en cas d'erreur non gérée. */
  static void setUncaughtExceptionAlert() {
    ErrorCatcher.setUncaughtExceptionAlert("Détection d'une erreur Java au sein du logiciel "+title,
					  "Il y a un problème de compatibilité avec votre système, nous allons vous aider:\n"+
					  " - Copier/Coller tous les éléments de cette fenêtre et\n"+
					   " - Envoyez les nous par mail à fuscia-accueil@inria.fr avec toute information utile",
					   revision);
  }

  /** Ouvre une fenêtre principale pour lancer l'application. */
  static void openWindow(String title, String icon, JPanel panel) {
    JFrame frame =  new JFrame();
    frame.setTitle(title);
    frame.setIconImage(org.javascool.widgets.Macros.getIcon(icon).getImage());
    if (panel != null)
      frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
	@Override
	  public void windowClosing(WindowEvent e) {
	  if (org.javascool.core.Desktop.getInstance().close()) {
	    e.getWindow().setVisible(false);
	    e.getWindow().dispose();
	    System.exit(0);
	  }
	}
      });
    frame.pack();
    frame.setVisible(true);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  /** Lanceur de l'application.
   * @param usage <tt>java org.javascool.Core</tt>
   */
  public static void main(String[] usage) {
    System.err.println("" + title + " is starting ...");
    System.exit(0);
    setUncaughtExceptionAlert();
    setUpSystem();
    checkJavaVersion();
    openWindow(title, logo, null);
  }
}
