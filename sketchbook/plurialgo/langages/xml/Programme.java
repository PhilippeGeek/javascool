/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.xml;

import java.util.*;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Programme extends org.javascool.proglets.plurialgo.langages.modele.Programme {
	
	public Programme() {
	}
	
	public Programme(Programme progDer) {
		nom = progDer.nom;
		operations = progDer.operations;
		variables = progDer.variables;
		instructions = progDer.instructions;
		classes = progDer.classes;
		les_fichiers = progDer.les_fichiers;
		buf_error = progDer.buf_error; 
		buf_warning = progDer.buf_warning;
	}

	
	// ----------------------------
	// MAX_TAB
	// ----------------------------
	
	protected void traiterMaxTab() {
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=instructions.iterator(); iter.hasNext();) {
			Instruction instr = (Instruction) iter.next();
			traiterMaxTab(instr);
		}
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Operation> iter1=operations.iterator(); iter1.hasNext();) {
			Operation oper = (Operation) iter1.next();
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=oper.instructions.iterator(); iter.hasNext();) {
				Instruction instr = (Instruction) iter.next();
				traiterMaxTab(instr);
			}
		}
		for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Classe> iter2=classes.iterator(); iter2.hasNext();) {
			Classe cl = (Classe) iter2.next();
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Operation> iter1=cl.operations.iterator(); iter1.hasNext();) {
				Operation oper = (Operation) iter1.next();
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=oper.instructions.iterator(); iter.hasNext();) {
					Instruction instr = (Instruction) iter.next();
					traiterMaxTab(instr);
				}
			}
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Constructeur> iter1=cl.constructeurs.iterator(); iter1.hasNext();) {
				Constructeur constr = (Constructeur) iter1.next();
				for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Instruction> iter=constr.instructions.iterator(); iter.hasNext();) {
					Instruction instr = (Instruction) iter.next();
					traiterMaxTab(instr);
				}
			}
		}
	}
	
	private void traiterMaxTab(Instruction instr) {
		if (instr.isEcriture() || instr.isLecture()) {
			if (instr.arguments.size()==0) return;
			Argument arg1 = (Argument) instr.arguments.get(0);
			Argument arg2 = arg1;
			if (instr.arguments.size()>=2) {
				arg2 = (Argument) instr.arguments.get(1);
			}
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Argument> iter=instr.arguments.iterator(); iter.hasNext();) {
				Argument arg = (Argument) iter.next();
				traiterMaxTab(arg,arg1,arg2);
			}
		}
	}
	
	private void traiterMaxTab(Argument arg, Argument arg1, Argument arg2) {
		String mode1 = "MAX_TAB";
		String mode2 = "MAX_TAB";
		String mode3 = "MAX_TAB";
		if (arg.isTabSimple()) {
			if (arg1.isEntier()) {
				mode1 = arg1.nom;
			}
			arg.mode = mode1;
		}
		if (arg.isMatSimple()) {
			if (arg1.isEntier()) {
				mode1 = arg1.nom;
			}
			if (arg2.isEntier()) {
				mode2 = arg2.nom;
			}
			else {
				mode2 = mode1;
			}
			arg.mode = mode1 + "," + mode2;
		}
		if (arg.isClasse(this) || arg.isEnregistrement(this)) {
			arg.mode = null;
			Classe cl = (Classe) arg.getClasse(this);
			if (cl.proprietes.size()==0) return;
			Variable prop1 = (Variable) cl.proprietes.get(0);
			Variable prop2 = prop1;
			if (cl.proprietes.size()>=2) {
				prop2 = (Variable) cl.proprietes.get(1);
			}
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Variable> iter=cl.proprietes.iterator(); iter.hasNext();) {
				Variable prop = (Variable) iter.next();
				if (prop.isTabSimple()) {
					if (prop1.isEntier()) {
						mode1 = arg.nom + "." + prop1.nom;
					}
					arg.mode = mode1;
				}
				if (prop.isMatSimple()) {
					if (prop1.isEntier()) {
						mode1 = arg.nom + "." + prop1.nom;
					}
					if (prop2.isEntier()) {
						mode2 = arg.nom + "." + prop2.nom;
					}
					else {
						mode2 = mode1;
					}
					arg.mode = mode1 + "," + mode2;
				}
			}
		}
		if (arg.isTabClasse(this)) {
			if (arg1.isEntier()) {
				mode1 = arg1.nom;
			}
			arg.mode = mode1;
			Classe cl = (Classe) arg.getClasseOfTab(this);
			if (cl.proprietes.size()==0) return;
			Variable prop1 = (Variable) cl.proprietes.get(0);
			Variable prop2 = prop1;
			if (cl.proprietes.size()>=2) {
				prop2 = (Variable) cl.proprietes.get(1);
			}
			for (Iterator<org.javascool.proglets.plurialgo.langages.modele.Variable> iter=cl.proprietes.iterator(); iter.hasNext();) {
				Variable prop = (Variable) iter.next();
				if (prop.isTabSimple()) {
					if (prop1.isEntier()) {
						mode2 = arg.nom + "." + prop1.nom;
					}
					arg.mode = mode1 + "," + mode2;
				}
				if (prop.isMatSimple()) {
					if (prop1.isEntier()) {
						mode2 = arg.nom + "." + prop1.nom;
					}
					if (prop2.isEntier()) {
						mode3 = arg.nom + "." + prop1.nom;
					}
					else {
						mode3 = mode2;
					}
					arg.mode = mode1 + "," + mode2 + "," + mode3;
				}
			}
		}
	}
}
