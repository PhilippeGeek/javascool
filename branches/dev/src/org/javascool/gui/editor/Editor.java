package org.javascool.gui.editor;

public interface Editor {

	public abstract JVSFileReferance getFile();

	public abstract String getName();

	public abstract void setFile(JVSFileReferance file);

	/** Get text into the TextArea
	 * @return The code
	 */
	public abstract String getText();

	public abstract Boolean hasToSave();

	public abstract void removeLineSignals();

	public abstract Boolean save();

	/** Ask to user to save a file before it close
	 * @param fileId The file id
	 * @return 1 mean that file is saved or that user not want to save the file. 0 meen that there was an error during the save of file. -1 meen that user want to stop all that happend (Cancel option).
	 */
	public abstract boolean saveBeforeClose();

	/** Set the text
	 * @param text The text to write on screen
	 */
	public abstract void setText(String text);

	public abstract void signalLine(int line);

}