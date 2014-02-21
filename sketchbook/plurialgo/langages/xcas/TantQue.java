/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xcas;

import java.util.Iterator;

import org.javascool.proglets.plurialgo.divers.Divers;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class TantQue extends org.javascool.proglets.plurialgo.langages.modele.TantQue {

	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		Divers.ecrire(buf, "tantque " + this.getCondition() + " faire", indent);
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			Instruction instr = (Instruction)obj ;
			instr.ecrire(prog, buf, indent+1);
		}
		Divers.ecrire(buf, "ftantque;", indent);
	}
}
