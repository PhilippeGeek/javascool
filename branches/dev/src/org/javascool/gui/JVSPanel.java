package org.javascool.gui;

import java.awt.BorderLayout;
import java.io.Console;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.javascool.core.ProgletEngine;
import org.javascool.gui.editor.CompileAction;
import org.javascool.gui.editor.EditorTabs;
import org.javascool.gui.editor.JVSFileReferance;
import org.javascool.gui.editor.JVSFileTabs;
import org.javascool.tools.UserConfig;

/**
 * The main panel for Java's cool This class wich is very static contain all
 * that we need to run Java's cool like save and open file command. This class
 * can only be called by JVSPanel on instance otherwise it can throw very big
 * errors
 * 
 * @author Philippe Vienne
 */
class JVSPanel extends JPanel {

	private static final long serialVersionUID = -913356947524067017L;

	/** Access to the unique instance of the JVSPanel object. */
	public static JVSPanel getInstance() {
		if (desktop == null) {
			desktop = new JVSPanel();
		}
		return desktop;
	}

	private static JVSPanel desktop = null;

	private JVSPanel() {
		setVisible(true);
		setLayout(new BorderLayout());
		add(JVSStartPanel.getInstance());
		this.revalidate();
	}

	public EditorTabs getEditorTabs() {
		if(JVSCenterPanel.getInstance().getLeftComponent() instanceof EditorTabs)
			return (EditorTabs) JVSCenterPanel.getInstance().getLeftComponent();
		throw new IllegalStateException("Left component is not an EditorTabs");
	}

	public void closeProglet() {
		if (closeAllFiles()) {
			this.removeAll();
			this.setOpaque(true);
			this.repaint();
			this.validate();
			this.repaint();
			add(JVSStartPanel.getInstance());
			this.repaint();
			this.revalidate();
			this.repaint();
			if (ProgletEngine.getInstance().getProglet() != null)
				ProgletEngine.getInstance().getProglet().stop();
		}
	}

	/**
	 * Open a new file in the editor
	 * 
	 * @see JVSFileTabs
	 */
	public void newFile() {
		getEditorTabs().openFile(new JVSFileReferance());
	}

	private CompileAction ca = new CompileAction();

	/**
	 * Compile file in the editor
	 * 
	 * @see JVSFileTabs
	 */
	public void compileFile() {
		ca.actionPerformed(null);
	}

	/**
	 * Open a file Start a file chooser and open selected file
	 * 
	 * @see JFileChooser
	 * @see JVSFileTabs
	 */
	public void openFile() {
		JFileChooser fc = new JFileChooser();
		if (UserConfig.getInstance("javascool").getProperty("dir") != null)
			fc.setCurrentDirectory(new File(UserConfig.getInstance("javascool")
					.getProperty("dir")));
		else if (System.getProperty("os.name").toLowerCase().contains("nix")
				|| System.getProperty("os.name").toLowerCase().contains("nux"))
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		else if (System.getProperty("home.dir") != null)
			fc.setCurrentDirectory(new File(System.getProperty("home.dir")));
		if (fc.showOpenDialog(Desktop.getInstance().getFrame()) == JFileChooser.APPROVE_OPTION) {
			if (!fc.getSelectedFile().exists()) {
				Dialog.error("Erreur", "Le fichier indiqué n'existe pas !!!");
				return;
			}
			UserConfig.getInstance("javascool").setProperty("dir",
					fc.getSelectedFile().getParentFile().getAbsolutePath());
			JVSFileReferance jvsfr = new JVSFileReferance(fc.getSelectedFile());
			getEditorTabs().openFile(jvsfr);
		}
	}

	/**
	 * Save the current file
	 * 
	 * @see JVSFileTabs
	 * @see JVSFile
	 */
	public boolean saveFile() {
		if (getEditorTabs().saveCurrentFile()) {
			return true;
		}
		return false;
	}

	/**
	 * Show a compile error for an human Open a dialog with compile error
	 * explains and hightlight the error line
	 * 
	 * @param line
	 *            The line error
	 * @param explication
	 *            Human explain for that error
	 * @see Console
	 */
	public void reportCompileError(int line, String explication) {
		org.javascool.widgets.Console.getInstance().clear();
		JVSWidgetPanel.getInstance().focusOnConsolePanel();
		if (ca.getCompiledEditor() != null) {
			ca.getCompiledEditor().signalLine(line);
		}
	}

	/**
	 * Handle the close application task Check if all files are saved and if the
	 * user want to close the application
	 * 
	 * @return True mean that app can be close and false that app can NOT be
	 *         closed
	 */
	public boolean close() {
		return closeAllFiles("Voulez vous vraiment quitter Java's cool ?");
	}

	public boolean closeAllFiles() {
		return closeAllFiles("Voulez vous vraiment continuer ?");
	}

	public boolean closeAllFiles(String messageIfAllFilesAreSaved) {
		if (getEditorTabs().isAllFilesSaved()) {
			int n = JOptionPane.showConfirmDialog(Desktop.getInstance()
					.getFrame(), messageIfAllFilesAreSaved, "Confirmation",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				return true;
			else
				return false;
		}
		return getEditorTabs().saveAllFiles();
	}

	/**
	 * Charge une nouvelle proglet dans l'interface utilisateur.
	 * 
	 * @param name
	 *            Le nom de code de la proglet (ex:abcdAlgos)
	 * @see org.javascool.core.ProgletEngine
	 */
	public void loadProglet(String name) {
		System.gc();
		this.removeAll();
		try {
			JVSToolBar.getInstance().disableDemoButton();
			this.revalidate();
			this.add(JVSToolBar.getInstance(), BorderLayout.NORTH);
			this.add(JVSCenterPanel.getInstance(), BorderLayout.CENTER);
			this.revalidate();
			JVSCenterPanel.getInstance().revalidate();
			JVSCenterPanel.getInstance().setDividerLocation(getWidth() / 2);
			JVSCenterPanel.getInstance().revalidate();
			JVSWidgetPanel.getInstance().setProglet(name);
			if (ProgletEngine.getInstance().getProglet().hasDemo()) {
				JVSToolBar.getInstance().enableDemoButton();
			} else {
				JVSToolBar.getInstance().disableDemoButton();
			}
			this.newFile();
		} catch (Exception e) {
			throw new RuntimeException("Unable to load proglet " + name, e);
		}
	}

	public void reportRuntimeBug(String ex) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		int line = 0;
		for (StackTraceElement elem : stack) {
			if (elem.getFileName().startsWith("JvsToJavaTranslated")) {
				line = elem.getLineNumber();
			} else {
				System.err.println(elem.getClassName());
			}
		}
		if (ca.getCompiledEditor() != null) {
			ca.getCompiledEditor().signalLine(line);
		}
		ProgletEngine.getInstance().doStop();
		Dialog.error("Erreur du logiciel à la ligne " + line, ex);
	}

	public void reportApplicationBug(String ex) {
		Dialog.error("Erreur dans Java's Cool", ex);
	}

	public static class Dialog {

		/** Show a success dialog */
		public static void success(String title, String message) {
			JOptionPane.showMessageDialog(Desktop.getInstance().getFrame(),
					message, title, JOptionPane.INFORMATION_MESSAGE);
		}

		/** Show an error dialog */
		public static void error(String title, String message) {
			JOptionPane.showMessageDialog(Desktop.getInstance().getFrame(),
					message, title, JOptionPane.ERROR_MESSAGE);
		}
	}
}
