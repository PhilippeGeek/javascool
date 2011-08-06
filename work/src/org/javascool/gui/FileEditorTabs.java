package org.javascool.gui;

import java.io.InputStream;
import org.javascool.gui.JVSFile;

/** The FileEditorTabs Interface
 * This interface define functions to have a FileEditorTabs
 * @author Philippe Vienne
 */
interface FileEditorTabs {
  /** Open a new empty Java's cool file in tmp
   * @return The file's tempory id in editor tabs
   */
  public String openNewFile();

  /** Open a new empty Java's cool file in tmp
   * @param url The url to the file (used by File())
   * @return The file's tempory id in editor tabs
   */
  public String open(String url);

  /** Open a new empty Java's cool file in tmp
   * @param stream The url to the file (used by File())
   * @return The file's tempory id in editor tabs
   */
  public String open(InputStream stream);

  /** Close an opened file
   * @param fileId The file ID
   */
  public void closeFile(String fileId);

  /** Get the Current file ID
   * @return An file id
   */
  public String getCurrentFileId();

  /** Get the count of openned files
   * @return An integer with the number of oppened file
   */
  public int getOppenedFileCount();

  /** Save the current file */
  public Boolean saveCurrentFile();

  /** Check if the current file is in tempory memory
   * @return True if is tempory
   */
  public Boolean currentFileIsTmp();

  /** Compile a file
   * This function have to set program to the console
   * @param fileId The id of the file to javaCompile
   * @return True on success, false in case of error. Can return true if file is not openned
   */
  public Boolean compileFile(String fileId);

  /** Save a file
   * @param fileId The id of the file to save
   * @return True on success, false in case of error. Can return true if file is not openned
   */
  public Boolean saveFile(String fileId);

  /** Prompt where we can save
   * after prompt it will call saveFile(fileId)
   * @param fileId The id of the file to save
   * @return See saveFile()
   */
  public Boolean saveFilePromptWhere(String fileId);

  /** Get the tabId for an fileId */
  public int getTabId(String fileId);

  /** Change the tab name
   * @param fileId The id of the file wich we change the title
   * @param newTitle The new title
   * @return The success
   */
  public Boolean editTabName(String fileId, String newTitle);

  /** Get the fileId from a TabName
   * @param tabName The tab Name
   * @return The file Id
   */
  public String getFileId(String tabName);

  /** Get a file from its ID */
  public JVSFile getFile(String id);

  /** Get the editor for an ID */
  public JVSEditor getEditor(String fileId);
}
