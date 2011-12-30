package org.javascool.gui.editor;

import java.util.ArrayList;

public interface EditorTabs {

	public abstract Editor getEditorForFile(JVSFileReferance file);

	public abstract ArrayList<JVSFileReferance> getOpenedFiles();

	public abstract boolean openFile(JVSFileReferance file);

	public abstract boolean saveAllFiles();

	public abstract boolean isAllFilesSaved();

	public abstract boolean saveCurrentFile();

	public abstract boolean saveFileAtIndex(int index);

	public abstract Editor getCurrentEditor();

}