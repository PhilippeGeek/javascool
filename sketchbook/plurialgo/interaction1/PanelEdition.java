/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.proglets.plurialgo.langages.modele.*;



/**
 * Cette classe correspond à l'onglet Résultats de l'interface graphique.
 * 
 * <p>
 * L'éditeur syntaxique est réalisé à partir de la librairie
 * <a href="http://fifesoft.com/rsyntaxtextarea/" target="_blank">rsyntaxtextarea</a>.
 * </p>
*/
@SuppressWarnings("unchecked")	// car les JList doivent être paramétrées avec Java7
public class PanelEdition extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private PanelInteraction pInter;	
	
	private JPanel pEdit;
	RSyntaxTextArea editArea;	
	private JList fichList;
	private Map<String,StringBuffer> les_fichiers;
	private String le_fichier; 

	public PanelEdition (PanelInteraction pInter) {
		super(new BorderLayout());
		this.pInter = pInter;
		initEdition();
		initPopupMenus();
		this.add(pEdit);
        setVisible(true);
	}
	
	private void initEdition() {
		// editeur de texte
        editArea = new RSyntaxTextArea(20,20);
		editArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		//editArea.setCodeFoldingEnabled(true);
		//editArea.setAntiAliasingEnabled(true);
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
		pEdit.add(fichList,"West");
		pEdit.setVisible(true);
	}
	
	private void initPopupMenus() {
		JPopupMenu popup = editArea.getPopupMenu();
	    popup.addSeparator();
		JMenuItem menu;
		menu = new JMenuItem("Executer"); menu.addActionListener(this); menu.setActionCommand("executer");
		//popup.add(menu);
	}
		
	public void actionPerformed(ActionEvent e) {
        try {
    		String cmd = e.getActionCommand();
        	if ("executer".equals(cmd)) {
        		MenusEditeur.doCarmetal(pInter);
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
	
	public JTextArea getTextArea() {
		return this.editArea;
	}	
		
	public String getText() {
		return this.editArea.getText();
	}	
	
	public void setText(String txt) {
		this.editArea.setText(txt);
	}	
	
	public void add_editeur(ModeleProgramme prog) {
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
			pInter.pEdition.setText(les_fichiers.get(le_fichier).toString());
		}
	}
		
	
}
