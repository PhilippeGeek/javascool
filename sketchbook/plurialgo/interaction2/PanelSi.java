/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction2;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import org.javascool.proglets.plurialgo.langages.xml.ProgrammeSi;


/**
 * Cette classe correspond à l'onglet Si de l'interface graphique.
*/
@SuppressWarnings("unchecked")	// car les JList doivent être paramétrées avec Java7
public class PanelSi extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static int nb_max = 10;
	JCheckBox t_check[];
	PanelBrancheSi t_branche[];

	PanelInteraction pInter;
	JButton creerButton;
	
	public PanelSi (PanelInteraction pInter) {
		this.setLayout( new FlowLayout(FlowLayout.LEFT) );
		this.pInter = pInter; 
		initBox ();
	}

	private void initBox() {
		Box vBox = Box.createVerticalBox();
		// panel Boutons 
		Box hbox = Box.createHorizontalBox();
		creerButton = new JButton("Creer"); creerButton.addActionListener(this);
		creerButton.setActionCommand("creer");
		hbox.add(Box.createHorizontalStrut(5));
        hbox.add(creerButton); hbox.add(Box.createHorizontalStrut(5));
        vBox.add(hbox);
        // création des alternatives
		t_check = new JCheckBox[nb_max];
		t_branche = new PanelBrancheSi[nb_max];
		for(int i=0; i<nb_max; i=i+1) {
			t_check[i]=new JCheckBox();
			t_check[i].addActionListener(this);
			t_branche[i]=new PanelBrancheSi();
			t_branche[i].setVisible(false);
			JPanel p = new JPanel();
			p.setLayout( new FlowLayout(FlowLayout.LEFT) );
			p.add(t_check[i]);
			p.add(t_branche[i]);
			vBox.add(p);
		}
		for(int i=0; i<2; i=i+1) {
			t_check[i].setSelected(true);
			t_branche[i].setVisible(true);
		}
        // fin
		this.add(vBox);
		this.setBackground(Color.white);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			for(int i=0; i<nb_max; i=i+1) {
				if (e.getSource()==t_check[i]) {
					if (t_check[i].isSelected()) {
						for(int i1=0; i1<=i; i1=i1+1) {
							t_check[i1].setSelected(true);
							t_branche[i1].setVisible(true);
						}
						for(int i1=i+1; i1<nb_max; i1=i1+1) {
							t_check[i1].setSelected(false);
							t_branche[i1].setVisible(false);
						}
					}
					else {
						for(int i1=0; i1<i; i1=i1+1) {
							t_check[i1].setSelected(true);
							t_branche[i1].setVisible(true);
						}
						for(int i1=i; i1<nb_max; i1=i1+1) {
							t_check[i1].setSelected(false);
							t_branche[i1].setVisible(false);
						}
						
					}
				}
			}
			if (e.getSource() == this.creerButton) {	
				this.nouveau();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// ---------------------------------------------
	// Pour les autres Panels
	// ---------------------------------------------
	
	private void nouveau() {
		org.javascool.proglets.plurialgo.langages.xml.Programme prog_xml;
		ProgrammeSi prog_si = new ProgrammeSi();
		for(int i=0; i<nb_max; i=i+1) {
			if (t_check[i].isSelected()) {
				prog_si.ajouterBranche(t_branche[i].getNiveau(), t_branche[i].getCondition());
			}
		}
		prog_xml = new org.javascool.proglets.plurialgo.langages.xml.Programme(prog_si);
		pInter.add_xml(prog_xml);
		pInter.traduireXml();
	}

}
