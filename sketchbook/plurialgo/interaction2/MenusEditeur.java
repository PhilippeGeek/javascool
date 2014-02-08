/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction2;

import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.javascool.gui.EditorWrapper;
import org.javascool.proglets.plurialgo.divers.Divers;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurAlgobox;
import org.javascool.tools.FileManager;
import org.javascool.tools.UserConfig;


/**
 * Cette classe ajoute des menus à l'éditeur Javascool
 */
public class MenusEditeur {
	
	// ---------------------------------------------
	// Ajout des styles et des menus
	// ---------------------------------------------

	public static void  modifierStyles(PanelInteraction pInter) {
		RSyntaxTextArea editArea = (RSyntaxTextArea)EditorWrapper.getRTextArea();
		try {
			editArea.setCodeFoldingEnabled(true);
			if (pInter.isJavascript()) {
				editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
			}
			else if (pInter.isPython()) {
				editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
			}
			else if (pInter.isVb()) {
				editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
			}
			else if (pInter.isPhp()) {
				editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);
			}
		} catch (Exception e) {
			editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		}
	}
	
	public static void addPopupMenu(PanelInteraction pInter) {
		final PanelInteraction inter = pInter;
		JPopupMenu popup = EditorWrapper.getRTextArea().getPopupMenu();
		if (pInter.isJavascool()) {
			popup.addSeparator();
			JMenuItem menu;
			menu = new JMenuItem("Compiler+"); 
			menu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CompilateurJVS.doCompile(inter,false);
				}
			});
			popup.add(menu);
		}
		else if (pInter.isJavascript()) {
			if(java.awt.Desktop.isDesktopSupported()){
				if (java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
					popup.addSeparator();
					JMenuItem menu;
					menu = new JMenuItem("Web"); 
					menu.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							doJavascript(inter);
						}
					});
					popup.add(menu);
				}
			}
		}
		else if (pInter.isPython()) {
			popup.addSeparator();
			JMenuItem menu;
			menu = new JMenuItem("Python"); 
			menu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doPython(inter);
				}
			});
//			popup.add(menu);
//			menu = new JMenuItem("Python (via commande DOS)"); 
//			menu.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					doPythonCommande(inter);
//				}
//			});
			popup.add(menu);
		}
		else if (pInter.isAlgobox()) {
			popup.addSeparator();
			JMenuItem menu;
			menu = new JMenuItem("Algobox"); 
			menu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doAlgobox(inter);
				}
			});
			popup.add(menu);
			menu = new JMenuItem("Affectation"); 
			menu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int indent = Divers.getIndent(EditorWrapper.getRTextArea());
					String tabus = tabuler(indent);
					String txt = tabus + "var PREND_LA_VALEUR ..."; 
					Divers.inserer(EditorWrapper.getRTextArea(), txt);
				}
			});
			popup.add(menu);
		}
	}
	
	// ---------------------------------------------
	// Utilitaires
	// ---------------------------------------------

	private static String saveFile(String code, String nom) {
		String nomComplet;
		try {
			File buildDir = UserConfig.createTempDir("javac");
			nomComplet = buildDir + File.separator + nom;
			FileManager.save(nomComplet, code);
	        // Si il y a un problème avec le répertoire temporaire on se rabat sur le répertoire local
		} catch(Exception e1) {
			try {
				nomComplet = new File(nom).getAbsolutePath();
				System.err.println("Sauvegarde locale du fichier : " + nomComplet);
				FileManager.save(nomComplet, code);
	          // Sinon on signale le problème à l'utilisateur
			} catch(Exception e2) {
				System.out.println("Attention ! le répertoire '" + System.getProperty("user.dir") + "' ne peut etre utilisé pour sauver des fichiers, \n il faut re-lancer javascool dans un répertoire de travail approprié.");
				return null;
	        }
		}
		return nomComplet;
	}
	
	private static boolean openFileDesktop(String nomComplet) {
		if(java.awt.Desktop.isDesktopSupported()){
			if (java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)){
				java.awt.Desktop dt = java.awt.Desktop.getDesktop();
				try {
					File f = new File(nomComplet);
					dt.open(f);
					return true;
				} catch (Exception ex) {
					System.err.println("\n"+"exception:"+ex.getMessage()+"\n");
					JOptionPane.showMessageDialog(null, ex.getMessage());
					return false;
				} 
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}		
	}
	
	private static boolean browseFileDesktop(String nomComplet) {
		if(java.awt.Desktop.isDesktopSupported()){
			if (java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)){
				java.awt.Desktop dt = java.awt.Desktop.getDesktop();
				try {
					File f = new File(nomComplet);
					dt.browse(f.toURI());
					return true;
				} catch (Exception ex) {
					//System.err.println("\n"+"exception:"+ex.getMessage()+"\n");
					return false;
				} 
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}	
	
	private static String tabuler(int indent) {
		String txt_indent = "\n";
		for(int i=0; i<indent; i++) {
		txt_indent = txt_indent + "\t";
		}
		return txt_indent;
	}    
	
	// ---------------------------------------------
	// Les commandes des menus
	// ---------------------------------------------
	
	public static boolean doJavascript(PanelInteraction pInter) {
		String code = pInter.getText();
		String nom = "test"+ ".html";
		String nomComplet = saveFile(code, nom);
		if (nomComplet==null) return false;
		// execution
		return browseFileDesktop(nomComplet);
	}

	static boolean doPythonCommande(PanelInteraction pInter) {
		String code = pInter.getText();
		String msg_fin = "Taper sur la touche ENTREE";
		if (!code.contains(msg_fin)) {
			code = code + "\n" + "raw_input(\"" + msg_fin + "\")" + "\n";
		}
		String nom = "test"+ ".py";
		String nomComplet = saveFile(code, nom);
		if (nomComplet==null) return false;
		// execution
		String OS = System.getProperty("os.name").toLowerCase();		
		if (OS.startsWith("win")) {
			try {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("cmd.exe /c start python " + nomComplet);
				return true;
			} catch (Exception ex) {
				pInter.writeConsole("\n"+"exception:"+ex.getMessage()+"\n");
				return false;
			} 
		}
		else {
			return false;
		}

	}
	
	public static boolean doPython(PanelInteraction pInter) {
		String code = pInter.getText();
		String nom = "test"+ ".py";
		String nomComplet = saveFile(code, nom);
		if (nomComplet==null) return false;
		// execution
		return openFileDesktop(nomComplet);
	}
	
	public static boolean doAlgobox(PanelInteraction pInter) {
		String code = pInter.getText();
		int i = code.indexOf("<?xml");
		if (i>=0) {
			code = code.substring(i);
		}
		else {
			AnalyseurAlgobox analyseur = new AnalyseurAlgobox(code);
			code = analyseur.exportAlgobox();
		}
		//pInter.writeConsole("["+code+"]");
		String nom = "test"+ ".alg";
		String nomComplet = saveFile(code, nom);
		if (nomComplet==null) return false;
		// execution
		return openFileDesktop(nomComplet);
	}
	
}
