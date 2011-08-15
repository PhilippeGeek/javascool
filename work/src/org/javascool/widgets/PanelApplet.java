/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.widgets;

// Used to encapsulate a proglet
import java.applet.Applet;
import javax.swing.JApplet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** Définit une applet qui encapsule un objet graphique. 
 * <p>Permet de wrapper un objet graphique dans une page HTML avec une construction de la forme
 * <div><tt>&lt;applet code="org.javascool.widgets.PanelApplet" archive="les-classes-java.jar" width="560" height="720"></tt></div>
 * <div><tt>&lt;param name="panel" value="nom-complet-qualifé-de-l-objet-graphique"/></tt></div>
 * <div><tt>&lt;param name="manual-start" value="true-ou-false"/></tt></div>
 * <div><tt>&lt;/applet></tt></div>
 * </p>
 * <ul>
 * <li>L'objet doit être un instance de <tt>java.awt.Component</tt> donc n'importe quel composant «swing» ou «awt».</li>
 * <li>Si l'objet possède des méthodes <tt>init<tt>, , <tt>destroy</tt>, <tt>start</tt>, <tt>stop</tt> elles sont invoquées par les méthodes correspondandes de l'applet.</li>
 * <li>Si l'option <tt>manual-start</tt> est activée les méthodes <tt>start</tt>, <tt>stop</tt> ne sont invoquées par l'applet mais par un bouton utilisateuer.</li>
 * </ul>
 * @see <a href="PanelApplet.java.html">source code</a>
 * @serial exclude
 */
public class PanelApplet extends JApplet {
  private static final long serialVersionUID = 1L;

  /** Definition programmatique des paramètres de l'applet.
   * @param panel Le nom de la classe Java de l'objet graphique à afficher.
   * @param manualStart Invocations manuelles si true des méthodes <tt>start/stop</tt> (par défaut), sinon elles sont invoquées au lancement. 
   * @return Cet objet, permettant de définir la construction <tt>new PanelApllet().reset(..)</tt>.
   */
  public PanelApplet reset(String panel, boolean manualStart) {
    this.panel = panel;
    this.manualStart = manualStart;
    return this;
  }
  /**
   * @see #reset(String, boolean)
   */
  public PanelApplet reset(String panel) {
    return reset(panel, true);
  }
  private String panel = null;
  private boolean manualStart = true;
  private Component pane = null;

  @Override
  public void init() {
    try {
      if (panel == null) {
	panel = getParameter("panel");
	manualStart = getParameter("manualStart") != null && getParameter("manualStart").toLowerCase().equals("true");
      }
    } catch(Exception e) {}
    try {
      getContentPane().add(pane = (Component) Class.forName(panel).newInstance(), BorderLayout.CENTER);
    } catch(Exception e) {
      getContentPane().add(new JLabel("Impossible d'instancier : "+ panel + "erreur: "+e));
    }
    if (manualStart) {
      getContentPane().add(new StartStopButton() {
	  public void start() {
	    invoke(pane, "start");
	  }
	  public void stop() {
	    invoke(pane, "stop");
	  }
	}, BorderLayout.NORTH);
    }
    invoke(pane, "init");
  }
  @Override
  public void destroy() {
    invoke(pane, "init");
  }
  @Override
  public void start() {
    if (!manualStart)
    invoke(pane, "start");
  }
  @Override
  public void stop() {
    if (!manualStart)
    invoke(pane, "stop");
  }

  /** Invoke une méthode sans argument sur un objet.
   * @param object L'objet sur lequel on invoque la méthode.
   * @param La méthode sans argument à invoquer, souvent : <tt>init</tt>, <tt>destroy</tt>, <tt>start</tt>, <tt>stop</tt> ou <tt>run</tt>.
   * @return La valeur true si la méthode est invocable, false sinon.
   * @throws RuntimeException si la méthode génère une exception lors de son appel.
   */
  public static boolean invoke(Object object, String method) {
    try {
      object.getClass().getDeclaredMethod(method).invoke(object);
    } catch(InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    }  catch(Throwable e) {
      return false;
    }
    return true;
  }
  /** Définit une fenêtre principale pour lancer une application. */
  public static class Frame extends JFrame {
    private Component pane;
    /** Construit et ouvre une fenêtre principale pour lancer une application.
     * @param title Le titre de la fenêtre.
     * @param icon L'icône de la fenêtre.
     * @param width Largeur de la fenêtre. Si 0 on prend tout l'écran.
     * @param hauteur Hauteur de la fenêtre. Si 0 on prend tout l'écran.
     * @param pane Le composant graphique à afficher.
     */
    public Frame(String title, String icon, int width, int height, Component pane) {
      setTitle(title);
      ImageIcon image = org.javascool.tools.Macros.getIcon(icon);
      if(image != null)
	setIconImage(image.getImage());
      add(this.pane = pane);
      if (pane instanceof Applet) {
	((Applet) pane).init();
	((Applet) pane).start();
      }
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
	  @Override
	    public void windowClosing(WindowEvent e) {
	    if (isClosable()) {
	      if (Frame.this.pane instanceof Applet) {
		((Applet) Frame.this.pane).stop();
		((Applet) Frame.this.pane).destroy();
	      }
	      e.getWindow().setVisible(false);
	      e.getWindow().dispose();
	    }
	  }
	});
      if (width > 0 && height > 0) {
	setSize(width, height);
      } else {
	setExtendedState(JFrame.MAXIMIZED_BOTH);
      }
      pack();
      setVisible(true);
    }
    /**
     * @see Frame(String, String, int, int, Component)
     */
    public Frame(String title, int width, int height, Component pane) {
      this(title, null, width, height, pane);
    }
    /**
     * @see Frame(String, String, int, int, Component)
     */
    public Frame(String title, String icon, Component pane) {
      this(title, icon, 0, 0, pane);
    }
    /**
     * @see Frame(String, String, int, int, Component)
     */
    public Frame(String title, Component pane) {
      this(title, null, 0, 0, pane);
    }
    /**
     * @see Frame(String, String, int, int, Component)
     */
    public Frame(int width, int height, Component pane) {
      this(pane.getClass().toString(), null, width, height, pane);
     }
    /**
     * @see Frame(String, String, int, int, Component)
     */
    public Frame(Component pane) {
      this(pane.getClass().toString(), null, 0, 0, pane);
     }
    /** Détermine si la fenêtre principale peut-être fermée.
     * @return La valeur true si la fenêtre principale peut-être fermée, sinon false.
     */
    public boolean isClosable() {
      return true;
    }
  }

  /** Lanceur dans une fenêtre principale d'une objet graphique.
   * @param usage <tt>java org.javascool.widgets.PanelApplet nom-complet-qualifé-de-l-objet-graphique</tt>.
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length > 0)
      new Frame(new PanelApplet().reset(usage[0]));
  }
}
