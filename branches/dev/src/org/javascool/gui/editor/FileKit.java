package org.javascool.gui.editor;

import java.util.ArrayList;

/** Ce Kit définit les fonctions de base du système d'onglet.
 * 
 */
public interface FileKit {

	public abstract EditorKit getEditorForFile(FileReference file);

	public abstract ArrayList<FileReference> getOpenedFiles();

	public abstract boolean openFile(FileReference file);

	public abstract boolean saveAllFiles();

	public abstract boolean isAllFilesSaved();

	public abstract boolean closeCurrentFile();

	public abstract boolean saveCurrentFile();

	public abstract boolean saveAsCurrentFile();
	
	public abstract boolean saveFileAtIndex(int index);

	public abstract EditorKit getCurrentEditor();

}