package org.javascool.gui;

import org.fife.ui.rtextarea.RTextArea;

public class EditorWrapper {
	
	private static JVSFileTabs tabs = JVSFileTabs.getInstance();
	
	public static String getText() {
		String fileId = tabs.getCurrentFileId(); 
		return tabs.getEditor(fileId).getText();
	}
	
	public static void setText(String txt) {
		String fileId = tabs.getCurrentFileId(); 
		tabs.getEditor(fileId).setText(txt);
	}
	
	public RTextArea getRTextArea() {
		String fileId = tabs.getCurrentFileId(); 
		return tabs.getEditor(fileId).getRTextArea();
	}
	
}
