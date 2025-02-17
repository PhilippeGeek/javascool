/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2013.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.langages.javascript;


import java.util.*;
import org.javascool.proglets.plurialgo.divers.*;
import org.javascool.proglets.plurialgo.langages.modele.*;


/**
 * Cette classe hérite de la classe homonyme du modèle.
*/
public class Argument extends ModeleArgument {
	
	public Argument() {
	}
	
	public Argument(String nom, String type, String mode) {
		this.nom = nom;
		this.type = type;
		this.mode = mode;
	}
	
	public void ecrire(Programme prog, StringBuffer buf, int indent, Instruction pere) {
		if (pere==null) return;
		if (pere.isLectureStandard()) {
			String msg = prog.quote(nom+" : ");
			this.lireStandard(prog, buf, indent, msg);
		}
		else if (pere.isEcritureStandard()) {
			String msg = prog.quote(nom+" : ");
			this.ecrireStandard(prog, buf, indent, msg);
		}
	}
	
	private String transformerMsg(String msg) {
		if (msg==null) return "";
		String txt = msg;
		txt=Divers.remplacer(txt, "[", "[\" + (");
		txt=Divers.remplacer(txt, "]", ") + \"]");
		return txt;
	}
	
// -------------------------------
// lecture d'arguments (standard)
// -------------------------------
	
	private void lireStandard(Programme prog, StringBuffer buf, int indent, String msg) { 
		if ( isSimple() ) {
			lireSimpleStandard(prog,buf,indent,msg);
		}
		if ( isTabSimple()) {
			lireTabStandard(prog,buf,indent,msg);
		}
		if ( isTabClasse(prog)) {
			lireTabClasseStandard(prog, buf, indent, msg);
		}
		if ( isMatSimple() ) {
			lireMatStandard(prog,buf,indent,msg);
		}
		if ( isEnregistrement(prog) || isClasse(prog) ) {
			lireClasseStandard(prog, buf, indent, msg);
		}
	}
	
	private void lireSimpleStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if (this.isTexte()) {
			Divers.ecrire(buf, this.nom + " = prompt( " + transformerMsg(msg) + " ); ", indent); 
		}
		else {
			Divers.ecrire(buf, this.nom + " = eval(prompt( " + transformerMsg(msg) + " )); ", indent); 
		}
	}
	
	private void lireTabStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		this.addVariable(new Variable("i1","ENTIER"));
		Divers.ecrire(buf, "for(i1=0; i1<" + prog.getDim(1, this) + "; i1++) {", indent);
		Argument arg = new Argument(this.nom+"[i1]", this.getTypeOfTab(), null);
		String msg1 =  prog.quote(arg.nom + " : ");
		arg.lireStandard(prog, buf, indent+1, msg1);
		Divers.ecrire(buf, "}", indent);
	}
	
	private void lireMatStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		this.addVariable(new Variable("i1","ENTIER"));
		this.addVariable(new Variable("j1","ENTIER"));
		Divers.ecrire(buf, "for(i1=0; i1<" + prog.getDim(1, this) + "; i1++) {", indent);
		Divers.ecrire(buf, "for(j1=0; j1<" + prog.getDim(2, this) + "; j1++) {", indent);
		Argument arg = new Argument(this.nom+"[i1][j1]", this.getTypeOfMat(), null);
		String msg1 =  prog.quote(arg.nom + " : ");
		arg.lireStandard(prog, buf, indent+2, msg1);
		Divers.ecrire(buf, "}", indent+1);
		Divers.ecrire(buf, "}", indent);
	}
	
	private void lireClasseStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		Classe cl = (Classe) getClasse(prog);
		for(Iterator<ModeleVariable> iter=cl.proprietes.iterator(); iter.hasNext(); ) {
			Variable prop = (Variable) iter.next();
			if (prop.isOut()) continue;
			Argument arg = new Argument(this.nom+"."+prop.nom, prop.type, this.mode);
			arg.parent = this.parent;
			String msg1 =  prog.quote(arg.nom + " : ");
			arg.lireStandard(prog, buf, indent, msg1);
		}
	}
	
	private void lireTabClasseStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		Classe cl = (Classe) getClasseOfTab(prog);
		this.addVariable(new Variable("ii","ENTIER"));
		Divers.ecrire(buf, "for(ii=0; ii<" + prog.getDim(1, this) + "; ii++) {", indent);
		for(Iterator<ModeleVariable> iter=cl.proprietes.iterator(); iter.hasNext(); ) {
			Variable prop = (Variable) iter.next();
			Argument arg = new Argument(this.nom+"[ii]"+"."+prop.nom, prop.type, oteDim(1));
			arg.parent = this.parent;
			if (prop.isOut()) continue;
			//msg1=prog.quote(prop.nom + " : ");
			String msg1 =  prog.quote(arg.nom + " : ");
			arg.lireStandard(prog, buf, indent+1, msg1);
		}
		Divers.ecrire(buf, "}", indent);
	}
	
//	 --------------------------------
//	 écriture d'arguments	(standard)
//	 --------------------------------
	
	private void ecrireStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if ( isSimple() ) {
			ecrireSimpleStandard(prog,buf,indent,msg);
		}
		if ( isTabSimple()) {
			ecrireTabStandard(prog,buf,indent,msg);
		}
		if ( isTabClasse(prog)) {
			ecrireTabClasseStandard(prog, buf, indent, msg);
		}
		if ( isMatSimple() ) {
			ecrireMatStandard(prog,buf,indent,msg);
		}
		if ( isEnregistrement(prog) || isClasse(prog) ) {
			ecrireClasseStandard(prog, buf, indent, msg);
		}
		if ( isExpression() ) {
			ecrireExpression(prog,buf,indent);
		}
	}
	
	private void ecrireExpression(Programme prog, StringBuffer buf, int indent) {
		Divers.indenter(buf, indent);
		Divers.ecrire(buf, "document.writeln(" + this.nom + "); ");
	}
	
	private void ecrireSimpleStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		Divers.indenter(buf, indent);
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + transformerMsg(msg) + "); ");
		Divers.ecrire(buf, "document.writeln(" + this.nom + "); ");
		if (msg!=null && !(prog.quote("\\t").equals(msg))) Divers.ecrire(buf, "document.writeln(" + prog.quote("<br/>") + "); "); 
	}
	
	private void ecrireTabStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + msg + ", " + prog.quote("<br/>") + "); ", indent);
		// this.addVariable(new Variable("i1","ENTIER"));
		Divers.ecrire(buf, "for(i1=0; i1<" + prog.getDim(1, this) + "; i1++) {", indent);
		String msg1 = prog.quote("\\t");
		Argument arg = new Argument(this.nom+"[i1]", this.getTypeOfTab(), null);
		arg.ecrireStandard(prog, buf, indent+1, msg1);
		Divers.ecrire(buf, "}", indent);
		Divers.ecrire(buf, "document.writeln(" + prog.quote("<br/>") + "); ", indent); 
	}
	
	private void ecrireMatStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + msg + ", " + prog.quote("<br/>") + "); ", indent);
		// this.addVariable(new Variable("i1","ENTIER"));
		Divers.ecrire(buf, "for(i1=0; i1<" + prog.getDim(1, this) + "; i1++) {", indent);
		Divers.ecrire(buf, "for(j1=0; j1<" + prog.getDim(2, this) + "; j1++) {", indent+1);
		String msg1 = prog.quote("\\t");
		Argument arg = new Argument(this.nom+"[i1][j1]", this.getTypeOfMat(), null);
		arg.ecrireStandard(prog, buf, indent+2, msg1);
		Divers.ecrire(buf, "}", indent+1);
		Divers.ecrire(buf, "document.writeln(" + prog.quote("<br/>") + "); ", indent+1); 
		Divers.ecrire(buf, "}", indent);
		Divers.ecrire(buf, "document.writeln(" + prog.quote("<br/>") + "); ", indent); 
	}
	
	private void ecrireClasseStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + msg + ", " + prog.quote("<br/>") + "); ", indent);
		Classe cl = (Classe) getClasse(prog);
		for(Iterator<ModeleVariable> iter=cl.proprietes.iterator(); iter.hasNext(); ) {
			Variable prop = (Variable) iter.next();
			if (prop.isIn()) continue;
			Argument arg = new Argument(this.nom+"."+prop.nom, prop.type, this.mode);
			String msg1 = prog.quote(arg.nom + " : ");
			// String msg1 = prog.quote("'" + arg.nom + " : " + "'");
			arg.ecrireStandard(prog, buf, indent, msg1);
		}
	}
	
	private void ecrireTabClasseStandard(Programme prog, StringBuffer buf, int indent, String msg) {
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + msg + ", " + prog.quote("<br/>") + "); ", indent);
		Classe cl = (Classe) getClasseOfTab(prog);
		// this.addVariable(new Variable("ii","ENTIER"));
		Divers.ecrire(buf, "for(ii=0; ii<" + prog.getDim(1, this) + "; ii++) {", indent);
		String msg1 = prog.quote("rang ") + " , ii , " + prog.quote(" de " + this.nom + " : ");
		if (msg1!=null) Divers.ecrire(buf, "document.writeln(" + msg1 + ", " + prog.quote("<br/>") + "); ", indent+1);
		for(Iterator<ModeleVariable> iter=cl.proprietes.iterator(); iter.hasNext(); ) {
			Variable prop = (Variable) iter.next();
			if (prop.isIn()) continue;
			Argument arg = new Argument(this.nom+"[ii]"+"."+prop.nom, prop.type, oteDim(1));
			msg1=prog.quote(prop.nom + " : ");
			arg.ecrireStandard(prog, buf, indent+1, msg1);
		}
		Divers.ecrire(buf, "}", indent);
		if (msg!=null) Divers.ecrire(buf, "document.writeln(" + prog.quote("<br/>") + "); ", indent);
	}
	
}
