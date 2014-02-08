/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.proglets.plurialgo.divers.Divers;
import org.javascool.proglets.plurialgo.langages.modele.Instruction;
import org.javascool.proglets.plurialgo.langages.modele.Pour;
import org.javascool.proglets.plurialgo.langages.modele.Programme;
import org.javascool.proglets.plurialgo.langages.modele.TantQue;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurAlgobox;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurJavascool;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurLarp;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurVb;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurPython;
import org.javascool.proglets.plurialgo.langages.xml.Argument;
import org.javascool.proglets.plurialgo.langages.xml.Intermediaire;
import org.javascool.proglets.plurialgo.langages.xml.ProgrammeDerive;
import org.javascool.proglets.plurialgo.langages.xml.ProgrammeVectorise;
import org.javascool.proglets.plurialgo.langages.xml.iAnalyseur;



/**
 * Cette classe correspond à l'onglet Résultats de l'interface graphique.
 * 
 * <p>
 * L'éditeur syntaxique est réalisé à partir de la librairie
 * <a href="http://fifesoft.com/rsyntaxtextarea/" target="_blank">rsyntaxtextarea</a>.
 * </p>
*/
@SuppressWarnings("unchecked")	// car les JList doivent être paramétrées avec Java7
public class PanelProgrammes extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private PanelInteraction pInter;	
	
	private JPanel pVect;	
	private JList boucleList;
	private JTextField pourVarField, pourFinField, vectModeField;
	private JTextField pourDebutField, pourPasField;
	private JTextField sommeModeField; private JCheckBox sommeCheck;
	private JTextField compterModeField; private JCheckBox compterCheck;
	private JTextField miniModeField; private JCheckBox miniCheck;
	private JTextField maxiModeField; private JCheckBox maxiCheck;
	private JTextField chercherModeField; private JCheckBox chercherCheck;
	private JButton vectoriserButton, bouclerButton, insererButton;
	
	private JPanel pEdit;
	RSyntaxTextArea editArea;	
	private JList fichList;
	private Map<String,StringBuffer> les_fichiers;
	private String le_fichier; 
	private JPopupMenu popup;	
	
	private JSplitPane splitPane;

	public PanelProgrammes (PanelInteraction pInter) {
		super(new BorderLayout());
		this.pInter = pInter;
		initEdition();
		initPopupMenus();
		initVect();
		splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pVect, pEdit);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane);
        setVisible(true);
	}
	
	private void initEdition() {
		// editeur de texte
        editArea = new RSyntaxTextArea(20,20);
		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
		editArea.setCodeFoldingEnabled(true);
		editArea.setAntiAliasingEnabled(true);
		RTextScrollPane paneScrollPane = new RTextScrollPane(editArea);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editArea.setVisible(true);
		// selecteur de fichiers
		les_fichiers = new TreeMap<String,StringBuffer>();
		le_fichier = null;
		fichList=new JList();
		fichList.addListSelectionListener(this);
		String [] choix ={"exemple.jvs"};
		fichList.setListData(choix); 
		les_fichiers.put(choix[0], new StringBuffer());
		le_fichier = choix[0];
		fichList.setVisible(false);
		// positionnement des composants
		pEdit = new JPanel(new BorderLayout());
		pEdit.add(paneScrollPane,"Center");
		pEdit.add(fichList,"East");
		pEdit.setVisible(true);
	}
	
	private void initPopupMenus() {
		popup = editArea.getPopupMenu();
	    popup.addSeparator();
		JMenuItem menu;
		menu = new JMenuItem("Si"); menu.addActionListener(this); menu.setActionCommand("si");
		popup.add(menu);
		menu = new JMenuItem("Pour"); menu.addActionListener(this); menu.setActionCommand("pour");
		popup.add(menu);
		menu = new JMenuItem("Tantque"); menu.addActionListener(this); menu.setActionCommand("tantque");
		popup.add(menu);
	}
	
	private void initVect() {
		JPanel p;
		pVect = new JPanel();
		pVect.setLayout( new FlowLayout() );
		this.add(pVect,"West");
		Box vbox = Box.createVerticalBox();
		pVect.add(vbox); 
		vbox.add(Box.createVerticalStrut(20));
		// titre
		p = new JPanel(); 
		p.add( new JLabel("Transformation 1-n") );
		vbox.add(p);
		vbox.add(Box.createVerticalStrut(10)); 
		// pour
		p = new JPanel(); 
		boucleList = new JList();
		String [] choix_boucle ={"pour","tantque"};
		boucleList.setListData(choix_boucle); 
		boucleList.setSelectedIndex(0);
		p.add(boucleList);
		p.add( new JLabel(" : ") );
		pourVarField = new JTextField(4);
		pourVarField.setText("k");
		p.add(pourVarField);
		//p.add( new JLabel(" de 1 à ") );
		p.add( new JLabel(" de ") );
		pourDebutField = new JTextField(2);
		pourDebutField.setText("1");
		p.add(pourDebutField);
		p.add( new JLabel(" à ") );
		pourFinField = new JTextField(6);
		pourFinField.setText("n");
		p.add(pourFinField);
		p.add( new JLabel(" pas ") );
		pourPasField = new JTextField(2);
		pourPasField.setText("1");
		p.add(pourPasField);
		vbox.add(p);
		// sommation
		p = new JPanel(); 
		sommeCheck = new JCheckBox(); p.add(sommeCheck);
		p.add( new JLabel("sommation : ") );
		sommeModeField = new JTextField(15);
		sommeModeField.setText("somme:increment");
		p.add(sommeModeField);
		vbox.add(p);
		// comptage
		p = new JPanel(); 
		compterCheck = new JCheckBox();	p.add(compterCheck);
		p.add( new JLabel("comptage : ") );
		compterModeField = new JTextField(20);
		compterModeField.setText("effectif:condition");
		p.add(compterModeField);
		vbox.add(p);
		// minimum
		p = new JPanel(); 
		miniCheck = new JCheckBox(); p.add(miniCheck);
		p.add( new JLabel("minimum : ") );
		miniModeField = new JTextField(15);
		miniModeField.setText("mini:expression");
		p.add(miniModeField);
		vbox.add(p);
		// maximum
		p = new JPanel(); 
		maxiCheck = new JCheckBox(); p.add(maxiCheck);
		p.add( new JLabel("maximum : ") );
		maxiModeField = new JTextField(15);
		maxiModeField.setText("maxi:expression");
		p.add(maxiModeField);
		vbox.add(p);
		// chercher
		p = new JPanel(); 
		chercherCheck = new JCheckBox(); p.add(chercherCheck);
		p.add( new JLabel("chercher (un) : ") );
		chercherModeField = new JTextField(15);
		chercherModeField.setText("condition");
		p.add(chercherModeField);
		vbox.add(p);
		// bouton
		p = new JPanel(); 
		vectoriserButton = new JButton("Transformer (1-n)"); p.add(vectoriserButton);
		vectoriserButton.addActionListener(this);
		vectoriserButton.setActionCommand("vectoriser");
		vectoriserButton.setVisible(true);
		bouclerButton = new JButton("Creer"); p.add(bouclerButton);
		bouclerButton.addActionListener(this);
		bouclerButton.setActionCommand("boucler");
		bouclerButton.setVisible(true);
		insererButton = new JButton("Inserer"); p.add(insererButton);
		insererButton.addActionListener(this);
		insererButton.setActionCommand("inserer");
		insererButton.setVisible(true);
		vbox.add(p);
		// ajout de dimension
		p = new JPanel(); 
		p.add( new JLabel("vectorisation : ") );
		vectModeField = new JTextField(15);
		vectModeField.setText("");
		p.add(vectModeField);
		vbox.add(p);
	}
		
	public void actionPerformed(ActionEvent e) {
        try {
			String cmd = e.getActionCommand();
			if (e.getSource() == this.vectoriserButton || ("vectoriser".equals(cmd))) {	
				if (this.vectoriserSelection()) return;
				this.vectoriser();	
			}
			else if (e.getSource() == this.bouclerButton || ("boucler".equals(cmd))) {	
				this.boucler();	
			}
			else if (e.getSource() == this.insererButton || ("inserer".equals(cmd))) {	
				this.inserer();	
			}
			else if ("traduire".equals(cmd)) {	
				this.traduire();	
			}
			else if ("reformuler".equals(cmd)) {	
				this.reformuler();	
			}
			// menu Instructions
			else if ("si".equals(cmd)) {
				this.addSi();	
			}
			else if ("pour".equals(cmd)) {
				this.addPour();	
			}
			else if ("tantque".equals(cmd)) {
				this.addTantQue();	
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void valueChanged(javax.swing.event.ListSelectionEvent e) {
		try {
			if (e.getSource()==this.fichList) {
				this.updateEditeur();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// ---------------------------------------------
	// Pour les autres Panels
	// ---------------------------------------------
	
	public String getText() {
		//return org.javascool.gui.EditorWrapper.getText();
		return this.editArea.getText();
	}	
	
	public void setText(String txt) {
		//org.javascool.gui.EditorWrapper.setText(txt);
		this.editArea.setText(txt);
	}	
	
	public void add_editeur(org.javascool.proglets.plurialgo.langages.modele.Programme prog) {
		les_fichiers.clear(); le_fichier=null;	 
		les_fichiers.putAll(prog.les_fichiers);
		fichList.setListData(prog.les_fichiers.keySet().toArray());
		fichList.setSelectedIndex(0); 
		if (prog.les_fichiers.size()>1) {
			fichList.setVisible(true);
		}
		else {
			fichList.setVisible(false);
		}
	}
	
	// ---------------------------------------------
	// Mise à jour éditeur
	// ---------------------------------------------
	
	private void updateEditeur(){
		if (this.fichList.getSelectedValue()!=null) {
			if (le_fichier!=null) {
				les_fichiers.put(le_fichier, new StringBuffer(editArea.getText()));
			}
			le_fichier = this.fichList.getSelectedValue().toString();
			colorier(le_fichier);
			pInter.pEdition.setText(les_fichiers.get(le_fichier).toString());
		}
	}
	
    public void colorier(String le_fichier) {
    	if (le_fichier.endsWith(".html")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".larp") || le_fichier.endsWith(".txt") || le_fichier.equals("larp")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
    	}
    	else if (le_fichier.endsWith(".bas") || le_fichier.equals("vb")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".jvs") || le_fichier.equals("javascool")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".scala") || le_fichier.equals("scala")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".java")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".cpp")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
    	}
    	else if (le_fichier.endsWith(".adb") || le_fichier.endsWith(".ads")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DELPHI);
    	}
    	else if (le_fichier.endsWith(".pm") || le_fichier.endsWith(".pl")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL);
    	}
    	else if (le_fichier.endsWith(".scm")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP);
    	}
    	else if (le_fichier.endsWith(".py")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
    	}
    	else if (le_fichier.endsWith(".R")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".php")) {
    		//editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
    	}
    	else if (le_fichier.endsWith(".xml")) {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
    	}
    	else {
    		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
    	}
    }
	
	// ---------------------------------------------
	// utilitaires pour vectorisation, reformulation et traduction
	// ---------------------------------------------
	
	public boolean isJavascool() {
		String txt=getText();
		if (txt.contains("void ") && txt.contains(" main()")) return true;
		if (txt.contains("void ") && txt.contains(" main( )")) return true;
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
		if (isVb()) return false;	
		if (isLarp()) return false;	
		if (isAlgobox()) return false;	
		return true;
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
		
	// ---------------------------------------------
	// Vectorisation
	// ---------------------------------------------
	
	void vectoriser() {
		Intermediaire inter = null;
		iAnalyseur analyseur = null;
		pInter.clearConsole();
		// analyse du programme initial
		if (isVb()) {
			analyseur = new AnalyseurVb(editArea.getText(), false, false);
		}
		else if (isJavascool()) {
			analyseur = new AnalyseurJavascool(editArea.getText(), false, false);
		}
		else if (isAlgobox()) {
			analyseur = new AnalyseurAlgobox(editArea.getText(), false, false);
		}
		else if (isLarp()) {
			inter = pInter.creerIntermediaireLarp("vectoriser");
			analyseur = new AnalyseurLarp(editArea.getText(), false, false, inter);
		}
		else if (isPython()) {
			inter = pInter.creerIntermediaireLarp("vectoriser");
			analyseur = new AnalyseurPython(editArea.getText(), false, false, inter);
		}
		else {
			pInter.clearConsole();
			pInter.writeConsole("---------- Transformation impossible ----------\n");
			pInter.writeConsole("l'algorithme initial ne semble pas etre du Javascool, du Larp, du Python ou de l'Algobox\n");
			pInter.writeConsole("--> reessayez en selectionnant une portion de l'algorithme\n");
			return;
		}
		// vectorisation
		pInter.messageWarning(analyseur.getProgramme());
		org.javascool.proglets.plurialgo.langages.xml.Programme prog = analyseur.getProgramme();
		vectoriser(prog);
		ProgrammeVectorise progVect = new ProgrammeVectorise(prog);
		// ajout du resultat dans les onglets Complements et Resultats
		pInter.add_xml(new org.javascool.proglets.plurialgo.langages.xml.Programme(progVect));
		pInter.traduireXml();
	}
	
	private void vectoriser(org.javascool.proglets.plurialgo.langages.xml.Programme prog) {
		prog.options.add( new Argument("vectorisation", null, vectModeField.getText()) );
		prog.options.add( new Argument("pour_var", null, pourVarField.getText()) );
		prog.options.add( new Argument("pour_fin", null, pourFinField.getText()) );
		prog.options.add( new Argument("pour_debut", null, pourDebutField.getText()) );
		prog.options.add( new Argument("pour_pas", null, pourPasField.getText()) );
		if ("tantque".equals(boucleList.getSelectedValue())) {
			prog.options.add( new Argument("tantque", null, "tantque") );
		}
		if (sommeCheck.isSelected()) {
			prog.options.add( new Argument("sommation", null, sommeModeField.getText()) );
		}
		if (compterCheck.isSelected()) {
			prog.options.add( new Argument("comptage", null, compterModeField.getText()) );
		}
		if (miniCheck.isSelected()) {
			prog.options.add( new Argument("minimum", null, miniModeField.getText()) );
		}
		if (maxiCheck.isSelected()) {
			prog.options.add( new Argument("maximum", null, maxiModeField.getText()) );
		}
		if (chercherCheck.isSelected()) {
			prog.options.add( new Argument("recherche", null, chercherModeField.getText()) );
		}
	}	
	
	private void boucler() {
		pInter.clearConsole();
		// vectorisation
		org.javascool.proglets.plurialgo.langages.xml.Programme prog;
		prog = new org.javascool.proglets.plurialgo.langages.xml.Programme();
		prog.nom = pInter.pPrincipal.getNomAlgo();
		vectoriser(prog);
		ProgrammeVectorise progVect = new ProgrammeVectorise(prog);
		// ajout du resultat dans les onglets Complements et Resultats
		pInter.add_xml(new org.javascool.proglets.plurialgo.langages.xml.Programme(progVect));
		pInter.traduireXml();
	}
	
	private void inserer() {
		pInter.clearConsole();
		// vectorisation
		org.javascool.proglets.plurialgo.langages.xml.Programme prog_xml;
		prog_xml = new org.javascool.proglets.plurialgo.langages.xml.Programme();
		prog_xml.nom = pInter.pPrincipal.getNomAlgo();
		vectoriser(prog_xml);
		ProgrammeVectorise progVect = new ProgrammeVectorise(prog_xml);
		// conversion du programme en Xml
		prog_xml = new org.javascool.proglets.plurialgo.langages.xml.Programme(progVect);
		pInter.add_xml(prog_xml);
		// conversion du programme dans le langage courant
		String lang = pInter.pPrincipal.getNomLangage();
		String txt = pInter.pXml.getText();
		Programme prog = Programme.getProgramme(txt,lang); 
		// ajout de la boucle
		StringBuffer buf = new StringBuffer();
		int indent = Divers.getIndent(pInter.pEdition.editArea);
		for (Iterator<Instruction> iter=prog.instructions.iterator(); iter.hasNext();) {
			Instruction instr = iter.next();
			if (instr.isLecture()) continue;
			if (instr.isEcriture()) continue;
			instr.ecrire(prog, buf, indent);
		}
		if (buf.length()>0 ) {
			prog.postTraitement(buf);
			Divers.inserer(pInter.pEdition.editArea, buf.toString());
		}
	}
	
	boolean vectoriserSelection() {
		pInter.clearConsole();
		// recherche zone de sélection
		int i_start = editArea.getSelectionStart();
		int i_end = editArea.getSelectionEnd();
		int indent = 0;
		if (i_end - i_start<5) return false;	// trop petit (donc sélection involontaire ?)
		String txt_select = "";
		try {
			int lig_start = editArea.getLineOfOffset(i_start);
			i_start = editArea.getLineStartOffset(lig_start);
			int lig_end = editArea.getLineOfOffset(i_end);
			i_end = editArea.getLineEndOffset(lig_end) - 1;
			txt_select = editArea.getText(i_start, i_end-i_start);
			while (txt_select.substring(indent, indent+1).equals("\t")) {
				indent = indent+1;
			}
			txt_select = "\t" + Divers.remplacer(txt_select, "\n", "\n\t");
			editArea.select(i_start, i_end);			
		}
		catch(Exception ex) {
			return false;
		}
		// vectorisation
		org.javascool.proglets.plurialgo.langages.xml.Programme prog_xml;
		prog_xml = new org.javascool.proglets.plurialgo.langages.xml.Programme();
		prog_xml.nom = pInter.pPrincipal.getNomAlgo();
		vectoriser(prog_xml);
		ProgrammeVectorise progVect = new ProgrammeVectorise(prog_xml);
		// ajout du commentaire transformer1n dans le Pour le programme vectorisé
		for (Iterator<Instruction> iter=progVect.instructions.iterator(); iter.hasNext();) {
			Instruction instr = iter.next();
			if (instr.isPour()) {
				Pour pour = instr.pours.get(0);
				if (pour==null) continue;
				if (pour.instructions.size()==1) {
					if (pour.instructions.get(0).isCommentaire()) {
						pour.instructions.remove(0);
					}
				}
				pour.instructions.add(0, new Instruction("//transformer1n"));
				break;
			}
			if (instr.isPour()) {
				TantQue tq = instr.tantques.get(0);
				if (tq==null) continue;
				tq.instructions.add(0, new Instruction("//transformer1n"));
				if (tq.instructions.size()==1) {
					if (tq.instructions.get(0).isCommentaire()) {
						tq.instructions.remove(0);
					}
				}
				tq.instructions.add(0, new Instruction("//transformer1n"));
				break;
			}
		}
		// conversion du programme en Xml
		prog_xml = new org.javascool.proglets.plurialgo.langages.xml.Programme(progVect);
		pInter.add_xml(prog_xml);
		// conversion du programme dans le langage courant
		String lang = pInter.pPrincipal.getNomLangage();
		String txt = pInter.pXml.getText();
		Programme prog = Programme.getProgramme(txt,lang); 
		// texte de la boucle (avec transformation1n)
		StringBuffer buf = new StringBuffer();
		for (Iterator<Instruction> iter=prog.instructions.iterator(); iter.hasNext();) {
			Instruction instr = iter.next();
			if (instr.isLecture()) continue;
			if (instr.isEcriture()) continue;
			instr.ecrire(prog, buf, indent);
		}
		// on remplace la ligne contenant transformation1n par txt_select
		int k = buf.indexOf("transformer1n");
		if (k<0) return false;
		int k_debut = buf.substring(0, k).lastIndexOf("\n")+1;
		int k_fin = buf.indexOf("\n", k);
		buf.delete(k_debut, k_fin);
		buf.insert(k_debut, txt_select);
		prog.postTraitement(buf);
		buf.delete(0, 1);
		editArea.replaceRange(buf.toString(), i_start, i_end);
		return true;
	}

	// ---------------------------------------------
	// Traduction
	// ---------------------------------------------
	
	public void traduire() {
		Intermediaire inter = null;
		iAnalyseur analyseur = null;
		pInter.clearConsole();
		// analyse du programme initial
		if (isVb()) {
			analyseur = new AnalyseurVb(editArea.getText(), false, false);
		}
		else if (isJavascool()) {
			analyseur = new AnalyseurJavascool(editArea.getText(), false, false);
		}
		else if (isAlgobox()) {
			analyseur = new AnalyseurAlgobox(editArea.getText(), false, false);
		}
		else if (isLarp()) {
			inter = pInter.creerIntermediaireLarp("traduire");
			analyseur = new AnalyseurLarp(editArea.getText(), false, false, inter);
		}
		else if (isPython()) {
			inter = pInter.creerIntermediaireLarp("traduire");
			analyseur = new AnalyseurPython(editArea.getText(), false, false, inter);
		}
		else {
			pInter.clearConsole();
			pInter.writeConsole("---------- Traduction impossible ----------\n");
			pInter.writeConsole("l'algorithme initial ne semble pas etre du Javascool, du Larp, du Python ou de l'Algobox");
			return;
		}
		// ajout du resultat dans les onglets Complements et Resultats
		pInter.messageWarning(analyseur.getProgramme());
		pInter.pXml.setText(analyseur.getXml().toString());
		pInter.traduireXml();
	}			
		
	// ---------------------------------------------
	// Reformulation
	// ---------------------------------------------
	
	public void reformuler() {
		Intermediaire inter = null;
		iAnalyseur analyseur = null;
		pInter.clearConsole();
		// analyse du programme initial
		if (isVb()) {
			analyseur = new AnalyseurVb(editArea.getText(), true, true);
		}
		else if (isJavascool()) {
			analyseur = new AnalyseurJavascool(editArea.getText(), true, true);
		}
		else if (isAlgobox()) {
			analyseur = new AnalyseurAlgobox(editArea.getText(), true, true);
		}
		else if (isLarp()) {
			inter = pInter.creerIntermediaireLarp("reformuler");
			analyseur = new AnalyseurLarp(editArea.getText(), true, true, inter);
		}
		else if (isPython()) {
			inter = pInter.creerIntermediaireLarp("reformuler");
			analyseur = new AnalyseurPython(editArea.getText(), true, true, inter);
		}
		else {
			pInter.clearConsole();
			pInter.writeConsole("---------- Reformulation impossible ----------\n");
			pInter.writeConsole("l'algorithme initial ne semble pas etre du Javascool, du Larp, du Python ou de l'Algobox");
			return;	
		}
		// construction du programme dérivé
		pInter.messageWarning(analyseur.getProgramme());
		inter = pInter.creerIntermediaire();
		ProgrammeDerive progDer = new ProgrammeDerive(analyseur.getProgramme(), inter);
		// ajout du resultat dans les onglets Complements et Resultats
		pInter.add_xml(new org.javascool.proglets.plurialgo.langages.xml.Programme(progDer));
		pInter.pPrincipal.algoField.setText(progDer.nom);
		pInter.traduireXml();
	}		
		
	// ---------------------------------------------
	// Commandes algorithmiques : Si, Pour, Tantque
	// ---------------------------------------------
			
		private String tabuler(int indent) {
			String txt_indent = "\n";
			for(int i=0; i<indent; i++) {
			txt_indent = txt_indent + "\t";
			}
			return txt_indent;
		}    
		
		private void insertText(String txt) {
	    	int start = editArea.getSelectionStart();
	    	editArea.setSelectionEnd(start);
	    	editArea.replaceSelection(txt);
		}  

		private void addSi() {
			int indent = Divers.getIndent(this.editArea);
			String tabus = tabuler(indent);
			String txt = ""; 
			if (isVb()) {
				txt += tabus + "if (condition) then";
				txt += tabus + "\tinstructions";
				txt += tabus + "elseif (condition) Then";
				txt += tabus + "\tinstructions";
				txt += tabus + "else";
				txt += tabus + "\tinstructions";
				txt += tabus + "end if";
				this.insertText(txt);
			}
			else if (isJavascool()) {
				txt += tabus + "if (condition) {";
				txt += tabus + "\tinstructions";
				txt += tabus + "}";
				txt += tabus + "elseif (condition) {";
				txt += tabus + "\tinstructions";
				txt += tabus + "}";
				txt += tabus + "else {";
				txt += tabus + "\tinstructions";
				txt += tabus + "}";
				this.insertText(txt);
			}
			else if (isLarp()) {
				txt += tabus + "SI (condition) ALORS";
				txt += tabus + "\tinstructions";
				txt += tabus + "SINON SI (condition) ALORS";
				txt += tabus + "\tinstructions";
				txt += tabus + "SINON";
				txt += tabus + "\tinstructions";
				txt += tabus + "FINSI";
				this.insertText(txt);
			}
		}
		
		private void addPour() {
			int indent = Divers.getIndent(this.editArea);
			String tabus = tabuler(indent); 
			String txt = ""; 
			if (isVb()) {
				txt += tabus + "for variable=debut to fin";
				txt += tabus + "\tinstructions";
				txt += tabus + "next variable";
				this.insertText(txt);
			}
			else if (isJavascool()) {
				txt += tabus + "while (condition) {";
				txt += tabus + "\tinstructions";
				txt += tabus + "}";
				this.insertText(txt); 
			}
			else if (isLarp()) {
				txt += tabus + "POUR variable=debut JUSQU'A fin FAIRE";
				txt += tabus + "\tinstructions";
				txt += tabus + "FINPOUR";
				this.insertText(txt);
			}
		}
		
		private void addTantQue() {
			int indent = Divers.getIndent(this.editArea);
			String tabus = tabuler(indent);
			String txt = ""; 
			if (isVb()) {
				txt += tabus + "while (condition)";
				txt += tabus + "\tinstructions";
				txt += tabus + "wend";
				this.insertText(txt); 
			}
			else if (isJavascool()) {
				txt += tabus + "while (condition) {";
				txt += tabus + "\tinstructions";
				txt += tabus + "}";
				this.insertText(txt); 
			}
			else if (isLarp()) {
				txt += tabus + "TANTQUE (condition) ";
				txt += tabus + "\tinstructions";
				txt += tabus + "FINTANTQUE";
				this.insertText(txt); 
			}
		}			
	
}
