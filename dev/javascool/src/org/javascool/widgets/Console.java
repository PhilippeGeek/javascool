/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/

package org.javascool.widgets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;

import org.javascool.tools.FileManager;

/**
 * Définit une zone d'affichage qui permet de recevoir les messages de la
 * console.
 * 
 * @author Philippe Vienne
 * @see <a href="Console.java.html">code source</a>
 * @serial exclude
 */
public class Console extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Crée et/ou renvoie l'unique instance de console.
	 * <p>
	 * Une application ne peut définir qu'une seule console.
	 * </p>
	 */
	public static Console getInstance() {
		if (Console.console == null) {
			Console.console = new Console();
		}
		return Console.console;
	}

	/** Renvoie true si la console a déjà été instanciée, false sinon. */
	public static boolean isInstanced() {
		return Console.console != null;
	}

	/** Zone d'affichage */
	private final JTextArea outputPane;

	// @static-instance

	/** Barre de menu */
	private final ToolBar toolbar;
	/** Zone d'affichage du statut. */
	private JLabel status;
	private static Console console = null;

	private Console() {
		final BorderLayout layout = new BorderLayout();
		setLayout(layout);
		// Construit la zone d'affichage
		outputPane = new JTextArea();
		outputPane.setEditable(false);
		final float[] bg = Color.RGBtoHSB(200, 200, 200, null);
		outputPane.setBackground(Color.getHSBColor(bg[0], bg[1], bg[2]));
		final JScrollPane scrolledOutputPane = new JScrollPane(outputPane);
		this.add(scrolledOutputPane, BorderLayout.CENTER);
		// Construit la zone des bouttons
		toolbar = new ToolBar();
		toolbar.addTool("Effacer", "org/javascool/widgets/icons/erase.png",
				new Runnable() {
					@Override
					public void run() {
						clear();
					}
				});
		toolbar.addSeparator();
		toolbar.addTool("status", status = new JLabel(
				"                                         "));
		this.add(toolbar, BorderLayout.NORTH);
		// Finalise l'objet
		redirectSystemStreams();
		setVisible(true);
	}

	/** Efface le contenu de la console. */
	public void clear() {
		outputPane.setText("");
	}

	/**
	 * Renvoie le contenu actuel de la console.
	 * 
	 * @return Ce qui affiché dans la console.
	 */
	public String getText() {
		return outputPane.getText();
	}

	/**
	 * Renvoie la barre de menu de la console pour ajouter des éléments.
	 * 
	 * @return La barre de menu de la console.
	 */
	public ToolBar getToolBar() {
		return toolbar;
	}

	/**
	 * Affiche du texte dans la console.
	 * 
	 * @param text
	 *            Le texte à afficher.
	 */
	public void print(String text) {
		outputPane.append(text);
	}

	/** Redirige le System.out vers cet affichage */
	private void redirectSystemStreams() {
		final OutputStream oldOut = System.out;
		final OutputStream out = new OutputStream() {
			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
				oldOut.write(b);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				print(new String(b, off, len));
				oldOut.write(b, off, len);
			}

			@Override
			public void write(int b) throws IOException {
				print(String.valueOf((char) b));
				oldOut.write(b);
			}
		};
		System.setOut(new PrintStream(out, true));
	}

	/**
	 * Sauve ce qui est présentement écrit dans la console dans un fichierL.
	 * 
	 * @param location
	 *            La localisation (chemin du fichier ou localisation internet)
	 *            où sauver le texte.
	 */
	public void saveConsoleOutput(String location) {
		FileManager.save(location, getText());
	}

	/**
	 * Affiche une information dans la barre de statut.
	 * 
	 * @param text
	 *            Texte à afficher.
	 */
	public void show(String text) {
		status.setText(text);
	}
}
