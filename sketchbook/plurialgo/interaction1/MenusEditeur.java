/*******************************************************************************
*     patrick.raffinat@univ-pau.fr, Copyright (C) 2014.  All rights reserved.  *
*******************************************************************************/
package org.javascool.proglets.plurialgo.interaction1;

import javax.script.*;



/**
 * Cette classe ajoute des menus à l'éditeur Javascool
 */
public class MenusEditeur {

	static ScriptEngineManager manager = null;		 
    static ScriptEngine engine = null;

	
	static void doCarmetal(PanelInteraction pInter) {
		if (engine==null) {
			manager = new ScriptEngineManager();		 
			engine = manager.getEngineByName("js");
		}
	    String fonctionsJS = ""
	    		+ "function Input(message) {\n"
	            + "var reponse = javax.swing.JOptionPane.showInputDialog(null,message,\"\",javax.swing.JOptionPane.PLAIN_MESSAGE);\n"
	            + "if (reponse==null) throw Error('Exit');\n"
	            + "return reponse;\n"
	            + "}\n"
	    		+ "function Println(message) {\n"
	            + "var reponse = javax.swing.JOptionPane.showConfirmDialog(null,message,\"\",javax.swing.JOptionPane.OK_CANCEL_OPTION,javax.swing.JOptionPane.INFORMATION_MESSAGE);\n"
	            + "if (reponse>0) throw new Error('Exit');\n"
	            + "}\n"
	    		+ "function Print(message) {\n"
	            + "Print(message);\n"
	            + "}\n"
	            ;
		String code = pInter.pEdition.getText();
	    try {
	    	pInter.clearConsole(); pInter.writeConsole("");
	    	engine.eval(code+fonctionsJS);
	    }
	    catch (javax.script.ScriptException ex) {
	    	String msg = ex.getMessage();
	    	int i;
	    	i = msg.lastIndexOf("Error:");
	    	if (i>0) msg = msg.substring(i+6, msg.length());
	    	i = msg.lastIndexOf("Exception:");
	    	if (i>0) msg = msg.substring(i+10, msg.length());
	    	i = msg.indexOf("(<");
	    	if (i>0) msg = msg.substring(0, i);
	    	if (!msg.equals(" [object Error] ")) {
	    		pInter.writeConsole("erreur ligne " + ex.getLineNumber() + " : " + msg );
	    		pInter.writelnConsole();
	    	}
	    	//pInter.writeConsole("erreur complete :" + ex.getMessage() + "\n");
			try {
				int lig = ex.getLineNumber();
				javax.swing.JTextArea area = pInter.pEdition.editArea;
				int nb_lig = area.getLineCount();
				if (lig<=nb_lig) {
					int debut_lig = area.getLineStartOffset(lig-1);	// premiere ligne : ligne 0	
					area.setCaretPosition(debut_lig);
				}
			}
			catch(Exception ex1) {
				return;
			}
	    }
	}

}
