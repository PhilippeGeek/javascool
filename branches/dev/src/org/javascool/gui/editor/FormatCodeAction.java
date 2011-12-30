package org.javascool.gui.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

import org.javascool.core.JvsBeautifier;

public class FormatCodeAction extends AbstractAction{

	private static final long serialVersionUID = -1107044277364712002L;
	
	private JTextArea textArea;

	public FormatCodeAction(JTextArea jta){
		super();
		this.putValue(NAME, "Formater le code");
		textArea=jta;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		textArea.setText(JvsBeautifier.run(textArea.getText()));
	}

}
