package org.javascool.widgets;

// Used to set Win look and feel
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;

import javax.swing.JFrame;
import java.applet.Applet;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import org.javascool.macros.Macros;

/** Définit une fenêtre principale pour lancer une application. */
public class MainFrame extends JFrame {
  private Component pane;
  // @bean
  public MainFrame() {}

  /** Compte des fenêtres ouvertes pour gérer le exit. */
  private static int frameCount = 0;

  /** Définit le look and feel de l'application. */
  static {
    try {
      for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        if("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
    } catch(Exception e1) {
      String os = System.getProperty("os.name");
      if(os.startsWith("Windows")) {
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception e2) {}
      } else {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e2) {
          System.err.println("Note: Utilisaton du thème Java (et non du système)");
        }
      }
    }
  }

  /** Construit et ouvre une fenêtre principale pour lancer une application.
   * @param title Le titre de la fenêtre.
   * @param icon L'icône de la fenêtre.
   * @param width Largeur de la fenêtre. Si 0 on prend tout l'écran.
   * @param height Hauteur de la fenêtre. Si 0 on prend tout l'écran.
   * @param pane Le composant graphique à afficher.
   * @return Cet objet, permettant de définir la construction <tt>new MainFrame().reset(..)</tt>.
   */
  public MainFrame reset(String title, String icon, int width, int height, Component pane) {
    setTitle(title);
    if(System.getProperty("os.name").toUpperCase().contains("MAC")) {
      try {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
      } catch(Exception e2) {}
    }
    ImageIcon image = Macros.getIcon(icon);
    if(image != null)
      setIconImage(image.getImage());
    add(this.pane = pane);
    if(pane instanceof Applet) {
      ((Applet) pane).init();
      ((Applet) pane).start();
    }
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                          if(isClosable()) {
                            if(MainFrame.this.pane instanceof Applet) {
                              ((Applet) MainFrame.this.pane).stop();
                              ((Applet) MainFrame.this.pane).destroy();
                            }
                            e.getWindow().setVisible(false);
                            e.getWindow().dispose();
                            frameCount--;
                            if(frameCount == 0)
                              System.exit(0);
                          }
                        }
                      }
                      );
    pack();
    if((width > 0) && (height > 0))
      setSize(width, height);
    else
      setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);
    if(firstFrame == null)
      firstFrame = this;
    frameCount++;
    return this;
  }
  /**
   * @see #reset(String, String, int, int, Component)
   */
  public MainFrame reset(String title, int width, int height, Component pane) {
    return reset(title, null, width, height, pane);
  }
  /**
   * @see #reset(String, String, int, int, Component)
   */
  public MainFrame reset(String title, String icon, Component pane) {
    return reset(title, icon, 0, 0, pane);
  }
  /**
   * @see #reset(String, String, int, int, Component)
   */
  public MainFrame reset(String title, Component pane) {
    return reset(title, null, 0, 0, pane);
  }
  /**
   * @see #reset(String, String, int, int, Component)
   */
  public MainFrame reset(int width, int height, Component pane) {
    return reset(pane.getClass().toString(), null, width, height, pane);
  }
  /**
   * @see #reset(String, String, int, int, Component)
   */
  public MainFrame reset(Component pane) {
    return reset(pane.getClass().toString(), null, 0, 0, pane);
  }
  /** Détermine si la fenêtre principale peut-être fermée.
   * @return La valeur true si la fenêtre principale peut-être fermée, sinon false.
   */
  public boolean isClosable() {
    return true;
  }
  /** Renvoie la frame principale ouverte.
   * @param La frame principale ouverte comme parent des dialogues modaux.
   */
  public static JFrame getFrame() {
    return firstFrame;
  }
  private static JFrame firstFrame = null;
}
