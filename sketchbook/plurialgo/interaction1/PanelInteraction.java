/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import java.awt.*;
import javax.swing.*;

import org.javascool.proglets.plurialgo.divers.Divers;
import org.javascool.proglets.plurialgo.langages.modele.*;
import org.javascool.proglets.plurialgo.langages.xml.*;


/**
 * Cette classe correspond au conteneur de l'interface graphique, à intégrer
 * ensuite dans une fenêtre swing, une applette ou l'environnement Javascool.
*/
public class PanelInteraction extends JPanel {	
	private static final long serialVersionUID = 1L;
	
	PanelConsole pConsole;
	JTabbedPane onglets;
	PanelPrincipal pPrincipal;
	PanelBoucles pBoucles;
	PanelSi pSi;
	PanelHtml pHtml;
	PanelXml pXml;
	PanelEdition pEdition;

	public static String urlDoc = null;
	private static String[][] langages = null;
	public static String[] langagesNoms = null;

	public PanelInteraction(String [][] mesLangages, boolean documentationInterne) {
		super(new BorderLayout());
		// documentation
		// documentation
		if (documentationInterne) {
			urlDoc = "/org/javascool/proglets/plurialgo/";
		} else {
			urlDoc = "http://web.univ-pau.fr/~raffinat/plurialgo/jvs/aideJVS/";
		}
		// langages
		langages = mesLangages;
		int n = langages.length;
		langagesNoms = new String[n];
		for(int i=0; i<n; i=i+1) {
			langagesNoms[i] = new String(langages[i][0]);
		}
		// onglets
		this.initOnglets();
	}

	public void initOnglets() {
		// nord
		pConsole = new PanelConsole(null);
		this.add(pConsole,"North");
		// centre : editeur + onglets
		onglets = new JTabbedPane(JTabbedPane.TOP);
		onglets.setBackground(null);
		pPrincipal = new PanelPrincipal(this);	onglets.add("Principal", pPrincipal);
		pSi = new PanelSi(this);  onglets.add("Si", pSi);
		pBoucles = new PanelBoucles(this); onglets.add("Boucles", pBoucles);
		pHtml = new PanelHtml(this); onglets.add("Documentation", pHtml);
		pXml = new PanelXml(this);  //onglets.add("Xml", pXml);
		pEdition = new PanelEdition(this); 
		JSplitPane splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pEdition, onglets);
		splitPane.setDividerLocation(300);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane,"Center");
	}
	
	public String[] getLangagesNoms() {
		return langagesNoms;
	}
	
	public String getLangagePackage(String nom_lang) {
		for(int i=0; i<langagesNoms.length; i=i+1) {
			if (nom_lang.equals(langagesNoms[i])) return langages[i][1];
		}
		return nom_lang;
	}
		
	public void selectPanel(Component pn) {
		if (pn==pBoucles) {
			onglets.setSelectedComponent(pn);
		}
		else if (pn==pPrincipal) {
			onglets.setSelectedComponent(pn);
		}
		else if (pn==pHtml) {
			onglets.setSelectedComponent(pn);
		}
		else if (pn==pSi) {
			onglets.setSelectedComponent(pn);
		}
	}

	// ---------------------------------------------
	// Console
	// ---------------------------------------------
	
	public void clearConsole() {
		pConsole.clearConsole();
	}	
	
	public void writeConsole(String txt) {
		pConsole.writeConsole(txt);
	}	
	
	public void writelnConsole() {
		pConsole.writelnConsole();
	}
	
	public boolean messageWarning(ModeleProgramme prog) {
		if (prog.buf_warning.length()>0) {
			this.clearConsole();
			this.writeConsole("---------- Avertissement ----------\n");
			this.writeConsole(prog.buf_warning.toString());
			return true;
		}
		return false;
	}
	
	public boolean messageErreur(ModeleProgramme prog) {
		if (prog.buf_error.length()>0) {
			this.clearConsole();
			this.writeConsole("---------- Erreur ----------\n");
			this.writeConsole(prog.buf_error.toString());
			return true;
		}
		return false;
	}
	
	// ---------------------------------------------
	// Xml
	// ---------------------------------------------

	public void setXml(String txt) {
		pXml.setText(txt);
	}
	
	public String getXml() {
		return pXml.getText();
	}

	public boolean add_xml(ModeleProgramme prog) {
		StringBuffer buf_xml = prog.getXmlBuffer();
		if (this.messageErreur(prog)) {
			return false;
		}
		else if (this.messageWarning(prog)) {
			Divers.remplacerSpeciaux(buf_xml);
			pXml.setText(buf_xml.toString());
			return true;
		}
		else {
			Divers.remplacerSpeciaux(buf_xml);
			pXml.setText(buf_xml.toString());
			return true;
		}
	}
	
	public ModeleProgramme getProgramme(String txt_xml, String nom_lang) {
		//return BindingCastor.getProgramme(txt_algo, nom_lang);
		return ModeleProgramme.getProgramme(txt_xml, nom_lang);
	}	

	public void traduireXml() {
		String lang = pPrincipal.getNomLangage();
		String txt = pXml.getText();
		int debut_tableau = 1;
		for(int i=0; i<langagesNoms.length; i=i+1) {
			if (lang.equals(langages[i][1])) {
				if (langages[i][2].equals("0")) debut_tableau=0;
				break;
			};
		}
		if (debut_tableau == 0) {
			StringBuffer buf = new StringBuffer(pXml.getText());
			Divers.remplacer(buf, "]", "-1]");
			Divers.remplacer(buf, "+1-1]", "]");
			pXml.setText(buf.toString());
		}
		traduireXml(pPrincipal.getNomLangage());
		if (debut_tableau == 0) {
			pXml.setText(txt);
		}
	}
	
	private boolean traduireXml(String nom_lang) {
		// récupération du programme
		ModeleProgramme prog = getProgramme(pXml.getText(),nom_lang); 
		if (this.messageErreur(prog)) {
			System.out.println("erreur:"+prog.getXmlBuffer().toString());
			return false;
		}
		prog.ecrire2();
		// edition du programme dans l'onglet Edition
		this.add_editeur(prog);
		this.selectPanel(this.pEdition);
		return true;
	}
	
	// ---------------------------------------------
	// Editeur
	// ---------------------------------------------

	public JTextArea getTextArea() {
		return this.pEdition.getTextArea();
	}
	
	public String getText() {
		return this.pEdition.getText();
	}	
	
	public void setText(String txt) {
		this.pEdition.setText(txt);
	}	
		
	public void add_editeur(ModeleProgramme prog) {
		this.pEdition.add_editeur(prog);
	}	
	
	public boolean isJavascool() {
		String txt=getText();
		if (txt.contains("void ") && txt.contains(" main()")) return true;
		if (txt.contains("void ") && txt.contains(" main( ")) return true;
		return false;
	}	
	
	public boolean isVb() {
		String txt=getText().toLowerCase();
		if (txt.contains("sub ")) return true;
		if (txt.contains("end function")) return true;
		if (txt.trim().length()==0) return true;
		return false;
	}	
	
	public boolean isLarp() {
		String txt=getText().toLowerCase();
		if (txt.contains("debut_algorithme")) return false;		// Algobox
		if ((txt.contains("debut")||txt.contains("début"))&&txt.contains("fin")) return true;
		if (txt.contains("entrer")&&txt.contains("retourner")) return true;
		return false;
	}	
	
	public boolean isAlgobox() {
		String txt=getText().toLowerCase();
		if (txt.contains("debut_algorithme")&&txt.contains("fin_algorithme")) return true;
		return false;
	}	
	
	public boolean isPython() {
		String txt=getText().toLowerCase();
		if (txt.contains("void ")) return false;	// Java, Javascool
		if (txt.contains("<html>")) return false;	// Javascript
		if (txt.contains("<?php")) return false;	// Php
		if (txt.contains("};")) return false;	// Xcas, Javascool...
		if (txt.contains("library(")) return false;	// R		
		if (isXcas()) return false;	
		if (isVb()) return false;	
		if (isLarp()) return false;	
		if (isAlgobox()) return false;	
		if (isCarmetal()) return false;	
		return true;
	}	
	
	public boolean isXcas() {
		String txt=getText().toLowerCase();
		if (txt.contains("void ")) return false;	// Java, Javascool
		if (txt.contains("<html>")) return false;	// Javascript
		if (txt.contains("<?php")) return false;	// Php
		if (txt.contains("library(")) return false;	// R
		if (txt.contains("fsi;")) return true;
		if (txt.contains("fspour;")) return true;
		if (txt.contains("ftantque;")) return true;			
		if (txt.contains("}:")) return true;
		if (isVb()) return false;	
		if (isLarp()) return false;	
		if (isAlgobox()) return false;		
		if (txt.contains(":=")) return true;		
		if (txt.contains("saisir(") && txt.contains(";")) return true;
		return false;
	}	
	
	public boolean isJavascript() {
		String txt=getText().toLowerCase();
		if (!txt.contains("<html>")) return false;	
		if (txt.contains("<?php")) return false;	
		return true;
	}	
	
	public boolean isPhp() {
		String txt=getText().toLowerCase();
		if (txt.contains("<?php")) return true;	
		return false;
	}	
	
	public boolean isR() {
		String txt=getText().toLowerCase();
		if (txt.contains("library(")) return true;	
		return false;
	}	
	
	public boolean isCarmetal() {
		String txt=getText();
		if (txt.contains("library(")) return false;	
		if (txt.contains("Println(")) return true;	
		if (txt.contains("Input(")) return true;
		return false;
	}	
		
	
	// ---------------------------------------------	
	// méthodes de création d'intermediaire	
	// ---------------------------------------------
	
	public Intermediaire creerIntermediaire() {
		Intermediaire inter = new Intermediaire();
		PanelPrincipal fen = this.pPrincipal;
		inter.nom_algo = fen.algoField.getText();
		if (inter.nom_algo.equalsIgnoreCase("patrick")) inter.nom_algo = "raffinat";
		inter.nom_langage = fen.getNomLangage();
		inter.donnees = fen.donneesField.getText();
		inter.resultats = fen.resultatsField.getText();
		inter.reels = fen.reelsField.getText();
		inter.entiers = fen.entiersField.getText();
		inter.textes = fen.textesField.getText();
		inter.booleens = fen.booleensField.getText();
		inter.tab_reels = fen.tab_reelsField.getText();
		inter.tab_entiers = fen.tab_entiersField.getText();
		inter.tab_textes = fen.tab_textesField.getText();
		inter.tab_booleens = fen.tab_booleensField.getText();
		inter.mat_reels = fen.mat_reelsField.getText();
		inter.mat_entiers = fen.mat_entiersField.getText();
		inter.mat_textes = fen.mat_textesField.getText();
		inter.mat_booleens = fen.mat_booleensField.getText();
		inter.niv_saisie = fen.niv_saisieList.getSelectedValue().toString(); 
		inter.niv_calcul = fen.niv_calculList.getSelectedValue().toString(); 
		inter.niv_affichage = fen.niv_affichageList.getSelectedValue().toString(); 
		inter.niv_groupement = fen.niv_groupementList.getSelectedValue().toString(); 
		inter.nom_groupe = fen.groupeField.getText();
		return inter;
	}
	
	public Intermediaire creerIntermediaireLarp(String option) {
		Intermediaire inter = this.creerIntermediaire();
		if ("traduire".equals(option)) {
			inter.donnees="";
			inter.resultats="";
			inter.niv_saisie="elementaire";
			inter.niv_affichage="elementaire";
			inter.niv_calcul="elementaire";
			inter.niv_groupement="elementaire";			
		}
		else if ("reformuler".equals(option)) {
			inter.niv_saisie="elementaire";
			inter.niv_affichage="elementaire";
			inter.niv_calcul="elementaire";
			inter.niv_groupement="elementaire";
		}
		else if ("vectoriser".equals(option)) {
			inter.donnees="";
			inter.resultats="";
			inter.niv_saisie="elementaire";
			inter.niv_affichage="elementaire";
			inter.niv_calcul="elementaire";
			inter.niv_groupement="elementaire";				
		}
		return inter;
	}

}
