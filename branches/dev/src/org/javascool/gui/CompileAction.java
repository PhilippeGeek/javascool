package org.javascool.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.javascool.core.ProgletEngine;
import org.javascool.gui.editor.EditorKit;
import org.javascool.gui.editor.FileKit;
import org.javascool.gui.editor.JVSEditorsPane;
import org.javascool.widgets.Console;

/** Compile Action for all JVSFileReferance. */
public class CompileAction extends AbstractAction {

	private static final long serialVersionUID = -6432472821088070514L;
	
	private FileKit tabs;
	private boolean success;
	private EditorKit compiledEditor;
	
	public CompileAction(){
		//this.tabs=JVSEditorsPane.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.tabs=JVSPanel.getInstance().getEditorTabs();
			if(tabs.saveCurrentFile()==false){
				setSuccess(false);
				return;
			}
			JVSWidgetPanel.getInstance().focusOnConsolePanel();
			setCompiledEditor(tabs.getCurrentEditor());
			if (ProgletEngine.getInstance().doCompile(tabs.getCurrentEditor().getText())) {
			    Console.getInstance().clear();
			    System.out.println("Compilation réussie !");
			    setSuccess(true);
			} else {
			    setSuccess(false);
			}
			if(isSuccess()){
				JVSToolBar.getInstance().enableStartStopButton();
			} else {
				JVSToolBar.getInstance().disableStartStopButton();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public EditorKit getCompiledEditor() {
		return compiledEditor;
	}

	public void setCompiledEditor(EditorKit compiledEditor) {
		this.compiledEditor = compiledEditor;
	}

}
