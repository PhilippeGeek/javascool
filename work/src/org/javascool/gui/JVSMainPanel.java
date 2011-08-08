package org.javascool.gui;

import java.awt.BorderLayout;
import java.io.Console;
import java.io.InputStream;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.javascool.core.Engine;

/** The main panel for Java's cool
 * This class wich is very static contain all that we need to run Java's cool like save and open file command.
 * This class can only be called by JVSMain on instance otherwise it can throw very big errors
 * @author Philippe Vienne
 */
class JVSMainPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  /** The java's cool top tool bar. */
  private JVSToolBar toolbar = new JVSToolBar();
  /** The java's cool split pane. */
  private JVSSplitPane mainPane = new JVSSplitPane();
  /** This HashMap say if a file has to be saved */
  private HashMap<String, Boolean> haveToSave = new HashMap<String, Boolean>();
  private Boolean noFileEdited = true;

  /** Access to the unique instance of the JVSMainPanel object. */
  public static JVSMainPanel getInstance() {
    if(desktop == null)
      desktop = new JVSMainPanel();
    return desktop;
  }
  private static JVSMainPanel desktop = null;
  private JVSMainPanel() {
    setVisible(true);
    setupViewLayout();
    setupToolBar();
    setupMainPanel();
  }
  /** Setup the Border Layout for the JPanel */
  private void setupViewLayout() {
    BorderLayout layout = new BorderLayout();
    setLayout(layout);
  }
  /** Setup the ToolBar */
  private void setupToolBar() {
    // Add Buttons here
    add(toolbar, BorderLayout.NORTH);
  }
  /** Setup Main Panel */
  private void setupMainPanel() {
    add(new JVSStartPanel());
  }
  public void closeProglet() {
    if(closeAllFiles()) {
      removeAll();
      add(toolbar);
      add(new JVSStartPanel());
      revalidate();
    } else {}
  }
  /** Get the toolbar
   * @return The toolbar
   */
  public JVSToolBar getToolBar() {
    return toolbar;
  }
  /** Open a new file in the editor
   * @see JVSFileEditorTabs
   */
  public void newFile() {
    String fileId = getEditorTabs().openNewFile();
    haveToSave.put(fileId, false);
  }
  /** Compile file in the editor
   * @see JVSFileEditorTabs
   */
  public void compileFile() {
    getEditorTabs().getEditor(JVSFileEditorTabs.getCurrentCompiledFile()).removeLineSignals();
    getEditorTabs().saveCurrentFile();
    if(getEditorTabs().compileFile(getEditorTabs().getCurrentFileId()))
      toolbar.enableStartStopButton();
    else
      toolbar.disableStartStopButton();
  }
  /** Open a file
   * Start a file chooser and open selected file
   * @see JFileChooser
   * @see JVSFileEditorTabs
   */
  public void openFile() {
    final JFileChooser fc = new JFileChooser();
    int returnVal = fc.showOpenDialog(Desktop.getInstance().getFrame());
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      String path = fc.getSelectedFile().getAbsolutePath();
      if(noFileEdited)
        // closeFile();
        noFileEdited = false;
      String fileId = getEditorTabs().open(path);
      haveToSave.put(fileId, false);
    } else {}
  }
  /** Open a specified file
   * Start a file chooser and open selected file
   * @see JFileChooser
   * @see JVSFileEditorTabs
   */
  public void openFileFromJar(String fileName) {
    String file2 = "org/javascool/proglets/" + Engine.getInstance().getProglet().getName() + "/" + fileName;
    InputStream file = ClassLoader.getSystemClassLoader().getResourceAsStream(file2);
    System.out.println("load:" + file2);
    getEditorTabs().open(file);
    // TOTO error management
  }
  /** Save the current file
   * @see JVSFileEditorTabs
   * @see JVSFile
   */
  public void saveFile() {
    if(getEditorTabs().saveCurrentFile())
      haveToSave.put(getEditorTabs().getCurrentFileId(), false);
  }
  /** Close the current file
   * @see JVSFileEditorTabs
   */
  public void closeFile() {
    if(haveToSave.get(getEditorTabs().getCurrentFileId())) {
      if(saveFileIdBeforeClose(getEditorTabs().getCurrentFileId()) == 1)
        getEditorTabs().closeFile(getEditorTabs().getCurrentFileId());
    } else
      getEditorTabs().closeFile(getEditorTabs().getCurrentFileId());
    if(getEditorTabs().getOppenedFileCount() == 0)
      newFile();
  }
  /** Update haveToSave for a file
   * Set it to true
   * @param fileId The file id
   */
  public void mustSave(String fileId) {
    noFileEdited = false;
    haveToSave.put(fileId, true);
  }
  /** Update haveToSave for a file
   * Set it to true
   * @param fileId The file id
   */
  public void haveNotToSave(String fileId) {
    haveToSave.put(fileId, false);
  }
  public Boolean getHasToSave(String fileId) {
    return haveToSave.get(fileId);
  }
  /** Show a compile error for an human
   * Open a dialog with compile error explains and hightlight the error line
   * @param line The line error
   * @param explication Human explain for that error
   * @see Console
   */
  public void reportCompileError(int line, String explication) {
    org.javascool.widgets.Console.getInstance().clear();
    getWidgetTabs().showConsole();
    if(getEditorTabs().getEditor(JVSFileEditorTabs.getCurrentCompiledFile()) != null)
      getEditorTabs().getEditor(JVSFileEditorTabs.getCurrentCompiledFile()).signalLine(line);
  }
  /** Handle the close application task
   * Check if all files are saved and if the user want to close the application
   * @return True mean that app can be close and false that app can NOT be closed
   */
  public Boolean close() {
    String id = "";
    Boolean[] can_close = new Boolean[haveToSave.keySet().toArray().length];
    int i = 0;
    int j = 0;
    for(Object fileId : haveToSave.keySet().toArray())
      if(haveToSave.get((String) fileId))
        j++;
    // If user no have dialog to stop close, we create one
    if(j == 0) {
      final int n = JOptionPane.showConfirmDialog(
        Desktop.getInstance().getFrame(),
        "Voulez vous vraiment quitter Java's cool ?",
        "Confirmation",
        JOptionPane.YES_NO_OPTION);
      if(n == JOptionPane.YES_OPTION)
        return true;
      else
        return false;
    }
    j = 0;
    // Check save for each file
    for(Object fileId : haveToSave.keySet().toArray()) {
      id = (String) fileId;
      if(haveToSave.get(id)) {
        // File has to be saved
        // For number see saveFileIdBeforeClose() documentation about return
        switch(saveFileIdBeforeClose(id)) {
        case 1:
          can_close[i] = true;
          break;
        case 0:
          can_close[i] = false;
          break;
        case -1:
          return false;
        }
        j++;
      } else
        // If file has not to be saved it's good
        can_close[i] = true;
      if(can_close[i])
        // If we can close this file, we close the tab
        getEditorTabs().closeFile(id);
      i++;
    }
    // Check if a file is not save, if yes we can not close the application
    for(Boolean can_close_r : can_close)
      if(can_close_r == false)

        return false;
    // We return true if all is good
    return true;
  }
  /** Handle the close file task
   * Check if all files are saved and if the user want to continue
   * @return True meen that app can be close and false that app can NOT be closed
   */
  public Boolean closeAllFiles() {
    String id = "";
    Boolean[] can_close = new Boolean[haveToSave.keySet().toArray().length];
    int i = 0;
    int j = 0;
    for(Object fileId : haveToSave.keySet().toArray()) {
      if(haveToSave.get((String) fileId))
        j++;
      else
        getEditorTabs().closeFile((String) fileId);
    }
    // If user no have dialog to stop close, we create one
    if(j == 0) {
      final int n = JOptionPane.showConfirmDialog(
        Desktop.getInstance().getFrame(),
        "Voulez vous vraiment continuer ?",
        "Confirmation",
        JOptionPane.YES_NO_OPTION);
      if(n == JOptionPane.YES_OPTION)
        return true;
      else
        return false;
    }
    j = 0;
    // Check save for each file
    for(Object fileId : haveToSave.keySet().toArray()) {
      id = (String) fileId;
      if(haveToSave.get(id)) {
        // File has to be saved
        // For number see saveFileIdBeforeClose() documentation about return
        switch(saveFileIdBeforeClose(id)) {
        case 1:
          can_close[i] = true;
          break;
        case 0:
          can_close[i] = false;
          break;
        case -1:
          return false;
        }
        j++;
      } else
        // If file has not to be saved it's good
        can_close[i] = true;
      if(can_close[i])
        // If we can close this file, we close the tab
        getEditorTabs().closeFile(id);
      i++;
    }
    // Check if a file is not save, if yes we can not close the application
    for(Boolean can_close_r : can_close)
      if(can_close_r == false)

        return false;
    // We return true if all is good
    return true;
  }
  /** Ask to user to save a file before it close
   * @param fileId The file id
   * @return 1 meen that file is saved or that user not want to save the file. 0 meen that there was an error during the save of file. -1 meen that user want to stop all that happend (Cancel option).
   */
  public int saveFileIdBeforeClose(String fileId) {
    JVSFile file = getEditorTabs().getFile(fileId);
    int result = JOptionPane.showConfirmDialog(
      Desktop.getInstance().getFrame(),
      "Voulez vous enregistrer " + file.getName() + " avant de continuer ?");
    if(result == JOptionPane.YES_OPTION) {
      if(getEditorTabs().saveFile(fileId)) {
        haveToSave.put(fileId, false);
        return 1;
      } else
        return 0;
    } else if(result == JOptionPane.NO_OPTION)
      return 1;
    else {
      haveToSave.put(fileId, true);
      return -1;
    }
  }
  public void loadProglet(String name) {
    System.gc();
    Engine.getInstance().setProglet(name);
    if(getWidgetTabs().getProgletPanel() != null)
      getWidgetTabs().getProgletPanel().destroy();
    removeAll();
    revalidate();
    add(getToolBar(), BorderLayout.NORTH);
    add(getMainPane(), BorderLayout.CENTER);
    revalidate();
    getMainPane().revalidate();
    getMainPane().setDividerLocation(getWidth() / 2);
    getMainPane().revalidate();
    ((JVSTabs) getMainPane().getRightComponent()).removeAll();
    ((JVSTabs) getMainPane().getRightComponent()).add("Console", "", org.javascool.widgets.Console.getInstance());
    getWidgetTabs().setProglet();
    if(getWidgetTabs().getProgletPanel() != null)
      getWidgetTabs().getProgletPanel().init();
    newFile();
  }
  public void reportRuntimeBug(String ex) {
    StackTraceElement[] stack = Thread.currentThread().getStackTrace();
    int line = 0;
    for(StackTraceElement elem : stack) {
      if(elem.getFileName().startsWith("JvsToJavaTranslated"))
        line = elem.getLineNumber();
      else
        System.err.println(elem.getClassName());
    }
    if(getEditorTabs().getEditor(JVSFileEditorTabs.getCurrentCompiledFile()) != null)
      getEditorTabs().getEditor(JVSFileEditorTabs.getCurrentCompiledFile()).signalLine(line);
    Engine.getInstance().doStop();
    Dialog.error("Erreur du logiciel à la ligne " + line, ex);
  }
  public void reportApplicationBug(String ex) {
    Dialog.error("Erreur dans Java's Cool", ex);
  }
  /** Get the current istance of Editor Tabs
   * @see JVSFileEditorTabs
   * @return The JVSFileEditorTabs instance
   */
  public FileEditorTabs getEditorTabs() {
    return (FileEditorTabs) mainPane.getLeftComponent();
  }
  public JVSWidgetPanel getWidgetTabs() {
    return (JVSWidgetPanel) mainPane.getRightComponent();
  }
  public JVSSplitPane getMainPane() {
    return mainPane;
  }
  public static class Dialog {
    /** Show a success dialog */
    public static void success(String title, String message) {
      JOptionPane.showMessageDialog(Desktop.getInstance().getFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    /** Show an error dialog */
    public static void error(String title, String message) {
      JOptionPane.showMessageDialog(Desktop.getInstance().getFrame(), message, title, JOptionPane.ERROR_MESSAGE);
    }
  }
}
