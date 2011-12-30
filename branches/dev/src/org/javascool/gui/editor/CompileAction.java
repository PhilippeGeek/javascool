package org.javascool.gui.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.javascool.core.ProgletEngine;
import org.javascool.gui.JVSToolBar;
import org.javascool.gui.JVSWidgetPanel;
import org.javascool.widgets.Console;

public class CompileAction extends AbstractAction {

	private static final long serialVersionUID = -6432472821088070514L;
	
	private EditorTabs tabs;
	private boolean success;
	private Editor compiledEditor;
	
	public CompileAction(){
		this.tabs=JVSFileTabs.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
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

	public Editor getCompiledEditor() {
		return compiledEditor;
	}

	public void setCompiledEditor(Editor compiledEditor) {
		this.compiledEditor = compiledEditor;
	}

}
