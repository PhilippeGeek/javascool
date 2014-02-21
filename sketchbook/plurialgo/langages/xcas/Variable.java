/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xcas;

import org.javascool.proglets.plurialgo.divers.Divers;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Variable extends org.javascool.proglets.plurialgo.langages.modele.Variable {
	
	public Variable() {
	}
	
	public Variable(String nom, String type) {
		this.nom = nom;
		this.type = type;
		this.mode = null;
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		Divers.ecrire(buf,this.nom);
		if (this.isTabSimple()) {
			Divers.ecrire(buf, ":=[]");
		}
		if (this.isMatSimple()) {
			Divers.ecrire(buf, ":=matrix(5,5)");
		}
	}

}
