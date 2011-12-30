package org.javascool.gui.editor;

import java.util.ArrayList;

public interface EditorTabs {

	public abstract Editor getEditorForFile(JVSFileReference file);

	public abstract ArrayList<JVSFileReference> getOpenedFiles();

	public abstract boolean openFile(JVSFileReference file);

	public abstract boolean saveAllFiles();

	public abstract boolean isAllFilesSaved();

	public abstract boolean saveCurrentFile();

	public abstract boolean saveFileAtIndex(int index);

	public abstract Editor getCurrentEditor();

}