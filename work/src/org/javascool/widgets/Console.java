/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/

package org.javascool.widgets;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.javascool.widgets.ToolBar;

// redirectSystemStreams
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

/** Définit une zone d'affichage qui permet de recevoir les messages de la console.
 * @author Philippe Vienne
 * @see <a href="Console.java.html">code source</a>
 * @serial exclude
 */
public class Console extends JPanel {
  private static final long serialVersionUID = 1L;

  /** Zone d'affichage */
  private JTextArea outputPane;
  /** Barre de menu */
  private ToolBar toolbar;
  /** Zone d'affichage du statut. */
  private JLabel status;

  // @static-instance 
  private Console() {
    BorderLayout layout = new BorderLayout();
    this.setLayout(layout);
    // Construit la zone d'affichage
    outputPane = new JTextArea();
    outputPane.setEditable(false);
    float[] bg = Color.RGBtoHSB(200, 200, 200, null);
    outputPane.setBackground(Color.getHSBColor(bg[0], bg[1], bg[2]));
    JScrollPane scrolledOutputPane = new JScrollPane(outputPane);
    this.add(scrolledOutputPane, BorderLayout.CENTER);
    // Construit la zone des bouttons
    toolbar = new ToolBar();
    toolbar.addTool("Effacer", "org/javascool/doc-files/icon16/erase.png", new Runnable() {
	
	@Override
	  public void run() {
	  clear();
	}
      });
    toolbar.addSeparator();
    toolbar.addTool("status", status = new JLabel("                                         "));
    this.add(toolbar, BorderLayout.NORTH);
    // Finalise l'objet
    redirectSystemStreams();
    this.setVisible(true);
  }
  /** Redirige le System.out vers cet affichage */
  private void redirectSystemStreams() {
    OutputStream out = new OutputStream() {
	
	@Override
	  public void write(int b) throws IOException {
	  print(String.valueOf((char) b));
	}
	
	@Override
	  public void write(byte[] b, int off, int len) throws IOException {
	  print(new String(b, off, len));
	}
	
	@Override
	  public void write(byte[] b) throws IOException {
	  write(b, 0, b.length);
	}
      };
    System.setOut(new PrintStream(out, true));
  }

  /** Efface le contenu de la console. */
  public void clear() {
    outputPane.setText("");
  }
 
  /** Affiche une information dans la barre de statut.
   * @param text Texte à afficher.
   */
  public void show(String text) {
    status.setText(text);
  }    

  /** Affiche du texte dans la console.
   * @param text Le texte à afficher.
   */
  public void print(String text) {
    outputPane.append(text);
  }

  /** Renvoie le contenu actuel de la console.
   * @return Ce qui affiché dans la console.
   */
  public String getText() {
    return outputPane.getText();
  }

  /** Crée et/ou renvoie l'unique instance de console.
   * <p>Une application ne peut définir qu'une seule console.</p>
   */
  public static Console getInstance() {
    if (console == null) console = new Console();
    return console;
  }
  private static Console console = null;
}
