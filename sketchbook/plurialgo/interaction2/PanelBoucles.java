/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;

import org.javascool.gui.EditorWrapper;
import org.javascool.proglets.plurialgo.divers.Divers;
import org.javascool.proglets.plurialgo.langages.modele.Instruction;
import org.javascool.proglets.plurialgo.langages.modele.Pour;
import org.javascool.proglets.plurialgo.langages.modele.Programme;
import org.javascool.proglets.plurialgo.langages.modele.TantQue;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurAlgobox;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurJavascool;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurLarp;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurPython;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurVb;
import org.javascool.proglets.plurialgo.langages.xml.AnalyseurXcas;
import org.javascool.proglets.plurialgo.langages.xml.Argument;
import org.javascool.proglets.plurialgo.langages.xml.Intermediaire;
import org.javascool.proglets.plurialgo.langages.xml.ProgrammeVectorise;
import org.javascool.proglets.plurialgo.langages.xml.iAnalyseur;



/**
 * Cette classe correspond à l'onglet Boucles de l'interface graphique.
*/
@SuppressWarnings("unchecked")	// car les JList doivent être paramétrées avec Java7
public class PanelBoucles extends JPanel implements ActionListener {
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
	private JButton vectoriserButton, bouclerButton, insererButton, effacerButton, aideButton;

	public PanelBoucles (PanelInteraction pInter) {
		super(new BorderLayout());
		this.pInter = pInter;
		initVect();
		this.add(pVect);
        setVisible(true);
	}
	
	private void initVect() {
		JPanel p;
		pVect = new JPanel();
		pVect.setLayout( new FlowLayout() );
		this.add(pVect,"West");
		Box vbox = Box.createVerticalBox();
		pVect.add(vbox); 
		vbox.add(Box.createVerticalStrut(20));
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
		//p.add( new JLabel(" de 1 a ") );
		p.add( new JLabel(" de ") );
		pourDebutField = new JTextField(4);
		pourDebutField.setText("1");
		p.add(pourDebutField);
		p.add( new JLabel(" a ") );
		pourFinField = new JTextField(4);
		pourFinField.setText("n");
		p.add(pourFinField);
		p.add( new JLabel(" pas ") );
		pourPasField = new JTextField(4);
		pourPasField.setText("1");
		p.add(pourPasField);
		vbox.add(p);
		// sommation
		p = new JPanel(); 
		sommeCheck = new JCheckBox(); p.add(sommeCheck);
		p.add( new JLabel("sommation : ") );
		sommeModeField = new JTextField(21);
		sommeModeField.setText("som:increment");
		p.add(sommeModeField);
		vbox.add(p);
		// comptage
		p = new JPanel(); 
		compterCheck = new JCheckBox();	p.add(compterCheck);
		p.add( new JLabel("comptage : ") );
		compterModeField = new JTextField(21);
		compterModeField.setText("effectif:condition");
		p.add(compterModeField);
		vbox.add(p);
		// minimum
		p = new JPanel(); 
		miniCheck = new JCheckBox(); p.add(miniCheck);
		p.add( new JLabel("minimum : ") );
		miniModeField = new JTextField(21);
		miniModeField.setText("mini:expression");
		p.add(miniModeField);
		vbox.add(p);
		// maximum
		p = new JPanel(); 
		maxiCheck = new JCheckBox(); p.add(maxiCheck);
		p.add( new JLabel("maximum : ") );
		maxiModeField = new JTextField(21);
		maxiModeField.setText("maxi:expression");
		p.add(maxiModeField);
		vbox.add(p);
		// chercher
		p = new JPanel(); 
		chercherCheck = new JCheckBox(); p.add(chercherCheck);
		p.add( new JLabel("chercher (un) : ") );
		chercherModeField = new JTextField(21);
		chercherModeField.setText("condition");
		p.add(chercherModeField);
		vbox.add(p);
		// bouton
		p = new JPanel(); 
		bouclerButton = new JButton("Creer"); p.add(bouclerButton);
		bouclerButton.addActionListener(this);
		bouclerButton.setActionCommand("boucler");
		bouclerButton.setVisible(true);
		insererButton = new JButton("Inserer"); p.add(insererButton);
		insererButton.addActionListener(this);
		insererButton.setActionCommand("inserer");
		insererButton.setVisible(true);
		effacerButton = new JButton("Effacer"); p.add(effacerButton);
		effacerButton.addActionListener(this);
		effacerButton.setActionCommand("effacer");
		effacerButton.setVisible(true);
		vectoriserButton = new JButton("Transformer (1-n)"); p.add(vectoriserButton);
		vectoriserButton.addActionListener(this);
		vectoriserButton.setActionCommand("vectoriser");
		vectoriserButton.setVisible(true);
		aideButton = new JButton("?"); p.add(aideButton);
		aideButton.addActionListener(this);
		aideButton.setActionCommand("aide");
		aideButton.setVisible(true);
		vbox.add(p);
		// ajout de dimension
		p = new JPanel(); 
		p.add( new JLabel("vectorisation : ") );
		vectModeField = new JTextField(21);
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
			else if (e.getSource() == this.aideButton || ("aide".equals(cmd))) {
				pInter.selectPanel(pInter.pHtml);
				pInter.pHtml.consulter("boutonsBoucles.html");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// ---------------------------------------------
	// Vectorisation
	// ---------------------------------------------
	
	private void vectoriser() {
		Intermediaire inter = null;
		iAnalyseur analyseur = null;
		pInter.clearConsole();
		// analyse du programme initial
		if (pInter.isVb()) {
			analyseur = new AnalyseurVb(pInter.getText(), false, false);
		}
		else if (pInter.isJavascool()) {
			analyseur = new AnalyseurJavascool(pInter.getText(), false, false);
		}
		else if (pInter.isAlgobox()) {
			analyseur = new AnalyseurAlgobox(pInter.getText(), false, false);
		}
		else if (pInter.isLarp()) {
			inter = pInter.creerIntermediaireLarp("vectoriser");
			analyseur = new AnalyseurLarp(pInter.getText(), false, false, inter);
		}
		else if (pInter.isPython()) {
			inter = pInter.creerIntermediaireLarp("vectoriser");
			analyseur = new AnalyseurPython(pInter.getText(), false, false, inter);
		}
		else if (pInter.isXcas()) {
			inter = pInter.creerIntermediaireLarp("vectoriser");
			analyseur = new AnalyseurXcas(pInter.getText(), false, false, inter);
		}
		else {
			pInter.clearConsole();
			pInter.writeConsole("---------- Transformation impossible ----------\n");
			pInter.writeConsole("l'algorithme initial ne semble pas etre du Javascool, du Larp, du Python, de l'Algobox, du Xcas ou du Visual Basic\n");
			pInter.writeConsole("--> reessayez en selectionnant une portion de l'algorithme\n");
			return;
		}
		// vectorisation
		pInter.messageWarning(analyseur.getProgramme());
		org.javascool.proglets.plurialgo.langages.xml.Programme prog = analyseur.getProgramme();
		vectoriser(prog);
		ProgrammeVectorise progVect = new ProgrammeVectorise(prog);
		// ajout du resultat dans l' onglet Complements et dans l'editeur
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
		int indent = Divers.getIndent(EditorWrapper.getRTextArea());
		for (Iterator<Instruction> iter=prog.instructions.iterator(); iter.hasNext();) {
			Instruction instr = iter.next();
			if (instr.isLecture()) continue;
			if (instr.isEcriture()) continue;
			instr.ecrire(prog, buf, indent);
		}
		if (buf.length()>0 ) {
			prog.postTraitement(buf);
			Divers.inserer(EditorWrapper.getRTextArea(), buf.toString());
		}
	}
	
	boolean vectoriserSelection() {
		pInter.clearConsole();
		// recherche zone de sélection
		JTextArea editArea = EditorWrapper.getRTextArea();
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
			if (instr.isTantQue()) {
				TantQue tq = instr.tantques.get(0);
				if (tq==null) continue;
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
	// Pour l'onglet Html
	// ---------------------------------------------
	
	void setBoucle(String boucle, String var, String debut, String fin, String pas) {
		boucleList.setSelectedValue(boucle, false);
		pourVarField.setText(var);
		pourDebutField.setText(debut);
		pourFinField.setText(fin);
		pourPasField.setText(pas);
	}
	
	void setSomme(boolean cocher, String mode) {
		sommeCheck.setSelected(cocher);
		sommeModeField.setText(mode);
	}
	
	void setCompter(boolean cocher, String mode) {
		compterCheck.setSelected(cocher);
		compterModeField.setText(mode);
	}
	
	void setMinimum(boolean cocher, String mode) {
		miniCheck.setSelected(cocher);
		miniModeField.setText(mode);
	}
	
	void setMaximum(boolean cocher, String mode) {
		maxiCheck.setSelected(cocher);
		maxiModeField.setText(mode);
	}
	
	void setChercher(boolean cocher, String mode) {
		chercherCheck.setSelected(cocher);
		chercherModeField.setText(mode);
	}
	
	void effacer() {
		setBoucle("pour", "k", "1", "n", "1");
		setSomme(false, "som:increment");
		setCompter(false, "effectif:condition");
		setMaximum(false, "maxi:condition");
		setMinimum(false, "mini:condition");
		setChercher(false, "condition");
	}
}
