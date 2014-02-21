/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xcas;

import org.javascool.proglets.plurialgo.divers.*;

/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Parametre extends org.javascool.proglets.plurialgo.langages.modele.Parametre {
	
	public Parametre() {
	}
	
	public void ecrire(Programme prog, StringBuffer buf) {
		if (this.isOut()) return;
		Divers.ecrire(buf, nom);
	}
	
}
