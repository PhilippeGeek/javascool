/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xcas;

import java.util.Iterator;

import org.javascool.proglets.plurialgo.divers.Divers;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Pour extends org.javascool.proglets.plurialgo.langages.modele.Pour {
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		if (isTantQue()) {
			if (pas.startsWith("-")) {
				Divers.ecrire(buf, var + ":=" + debut + ";", indent) ;
				Divers.ecrire(buf, "tantque (" + var + ">=" + fin + ") ", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, var + ":=" + var + pas+ ";", indent+1) ;
				Divers.ecrire(buf, "ftantque", indent);
			}
			else {
				Divers.ecrire(buf, var + ":=" + debut + "; ", indent) ;
				Divers.ecrire(buf, "tantque (" + var + "<=" + fin + ") FAIRE", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, var + "=" + var + "+" + pas+ ";", indent+1) ;
				Divers.ecrire(buf, "ftantque;", indent);
			}
		}
		else {
				Divers.ecrire(buf, "pour " + var + " de " + debut  
						+ " jusque " + fin 
						+ " pas " + pas + " faire ", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
					Object obj = iter.next();
					Instruction instr = (Instruction)obj ;
					instr.ecrire(prog, buf, indent+1);
				}
				Divers.ecrire(buf, "fpour;", indent);
		}
	}
	
}
