/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import javax.swing.*;


/**
 * Cette classe permet de tester l'application dans une applette
*/
public class AppletteTest extends JApplet {
	private static final long serialVersionUID = 1L;

	PanelInteraction panelInteraction;
	
	/* langages disponibles (par ordre alphabetique) :
	 * 	- algobox
	 *  - carmetal (javascript rhino)
	 *	- java
	 *	- javascool
	 *	- javascript
	 *	- larp
	 *	- php
	 *	- python
	 *	- r
	 *	- visual basic
	 *	- xcas 
	 */
	
	private static String[][] mesLangages = {
		// {nom, package, debut_tableau}
		{"Javascool", "javascool", "0"},
		{"Algobox", "algobox", "1"},
		{"Larp", "larp", "1"},
		{"Python", "python", "0"},
		{"Xcas", "xcas", "0"},
		{"Javascript", "javascript", "0"},
		{"Java", "java", "0"},
		{"Php", "php", "0"},
		{"Visual Basic", "vb", "1"},
		{"CarMetal", "carmetal", "0"},
		{"R", "R", "1"}
	};
	
	public void init() {	
		panelInteraction = new PanelInteraction(mesLangages,true); 
		this.setContentPane(panelInteraction);
		//setPreferredSize(new java.awt.Dimension(680, 480));	
	}

}
