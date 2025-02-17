/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xml;

import org.javascool.proglets.plurialgo.langages.modele.*;

/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class XmlArgument extends ModeleArgument {
	
	public XmlArgument() {
	}
	
	public XmlArgument(String nom, String type, String mode) {
		this.nom = nom;
		this.type = type;
		this.mode = mode;
	}
}
