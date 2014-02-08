/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.algobox;

import java.util.*;

import org.javascool.proglets.plurialgo.divers.*;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Instruction extends org.javascool.proglets.plurialgo.langages.modele.Instruction {
	
	public Instruction() {
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent) {
		if (isLectureStandard()) {
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				arg.ecrire(prog, buf, indent, this);
			}
		}
		else if (isEcritureStandard()) {
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				arg.ecrire(prog, buf, indent, this);
			}
		}
		else if (isSi()) {
			int nb_si = interpreterSi();
			if (isSelon()) {
			}
			else {	// si classique
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Si> iter=sis.iterator(); iter.hasNext();) {
					Si si = (Si) iter.next();
					si.ecrire(prog, buf, indent);
					if (si.isSi()) {
						indent = indent+1;
					}
					else {
						indent = indent+2;
					}
				}
				if (nb_si > 1) {
					indent = indent-1;
					for (int i = nb_si; i>=2; i--) {
						Divers.ecrire(buf, "FIN_SINON", indent);
						indent = indent-2;
					}
				}
			}
		}
		else if (isPour()) {
			interpreterPour();	
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Pour> iter=pours.iterator(); iter.hasNext();) {
				Pour pour = (Pour) iter.next();
				pour.ecrire(prog, buf, indent);
			}
		}
		else if (isTantQue()) {
			interpreterTantQue();	
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.TantQue> iter=tantques.iterator(); iter.hasNext();) {
				TantQue tq = (TantQue) iter.next();
				tq.ecrire(prog, buf, indent);
			}
		}
		else if (isAffectation()) {
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Affectation> iter=affectations.iterator(); iter.hasNext();) {
				Affectation aff = (Affectation) iter.next();
				aff.ecrire(prog, buf, indent);
			}
		}
		else if (isCommentaire()) {
			prog.commenter(buf, nom.substring(2), indent);
		}
		else if (isPrimitive()) {
			Divers.ecrire(buf, "// " + this.getPrimitive(), indent);
		}
		else {
			// Divers.ecrire(buf, nom, indent);
		}
	}
	
	public void ecrire(org.javascool.proglets.plurialgo.langages.modele.Programme prog, StringBuffer buf, int indent) {
		this.ecrire((org.javascool.proglets.plurialgo.langages.algobox.Programme)prog, buf, indent);
	}
	
}
