package org.javascool.gui.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.fife.ui.autocomplete.AutoCompletion;

public class CompletionSwitchAction extends AbstractAction {

	private static final long serialVersionUID = 4365053052695732060L;
	private static String ACTIVATION_NAME="Activer la complétion";
	private static String DESACTIVATION_NAME="Déctiver la complétion";
	
	private AutoCompletion ac;
	
	public CompletionSwitchAction(AutoCompletion ac){
		super();
		putValue(NAME, ACTIVATION_NAME);
		this.ac=ac;
		this.ac.setAutoCompleteEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.ac.isAutoCompleteEnabled()){
			putValue(NAME, ACTIVATION_NAME);
			ac.setAutoCompleteEnabled(false);
		} else {
			putValue(NAME, DESACTIVATION_NAME);
			ac.setAutoCompleteEnabled(true);
		}
	}

}
