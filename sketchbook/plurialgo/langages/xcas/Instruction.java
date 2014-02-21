/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xcas;

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
		else if (isAppel(prog)) {
			ecrireAppel(prog, buf, indent); 
		}
		else if (isSi()) {
			int nb_si = interpreterSi();
			if (isSelon()) {
				String var = this.getVariableSelon();
				Divers.ecrire(buf, "switch (" + var + ") {", indent);
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Si> iter=sis.iterator(); iter.hasNext();) {
					Si si = (Si) iter.next();
					si.ecrire(prog, buf, indent + 1);
				}
				Divers.ecrire(buf, "}", indent);
			}
			else {	// si classique
				int nb_fsi = nb_si;
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Si> iter=sis.iterator(); iter.hasNext();) {
					Si si = (Si) iter.next();
					si.ecrire(prog, buf, indent);
					indent = indent+1;
					if (si.isSinon()) {
						nb_fsi = nb_si-1;
						indent = indent-1;
					}
				}
				if (nb_fsi > 0) {
					for (int i = nb_fsi; i>=1; i--) {
						indent = indent-1;
						Divers.ecrire(buf, "fsi;", indent);
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
	
	private void ecrireAppel(Programme prog, StringBuffer buf, int indent)  {
		Argument retour = (Argument) getRetour();
		Divers.indenter(buf, indent);
		if (isAppelFonction(prog)) {
			retour.ecrire(prog, buf);
			Divers.ecrire(buf, " := ");
			Divers.ecrire(buf, nom);
			Divers.ecrire(buf, "(");
			int nbparam=0;
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				if (arg.isOut()) continue;
				nbparam=nbparam+1;
				if (nbparam>1) Divers.ecrire(buf, ", ");
				arg.ecrire(prog, buf);
			}
			Divers.ecrire(buf, ");");
		}
		else if (isAppelProcedure(prog)) {
			StringBuffer gauche = new StringBuffer();
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				if (arg.isOut()) {	// procedure transformee en fonction
					if (gauche.length()>0) gauche.append(",");
					gauche.append(arg.nom);
				}
			}
			if (gauche.length()>0) {
				Divers.ecrire(buf,gauche.toString() + ":=");
			}
			Divers.ecrire(buf, nom);
			Divers.ecrire(buf, "(");
			int nbparam=0;
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				if (arg.isOut()) continue;
				nbparam=nbparam+1;
				if (nbparam>1) Divers.ecrire(buf, ", ");
				arg.ecrire(prog, buf);
			}
			Divers.ecrire(buf, ");");
		}
	}
	
	public void ecrire(org.javascool.proglets.plurialgo.langages.modele.Programme prog, StringBuffer buf, int indent) {
		this.ecrire((org.javascool.proglets.plurialgo.langages.xcas.Programme)prog, buf, indent);
	}
	
}
