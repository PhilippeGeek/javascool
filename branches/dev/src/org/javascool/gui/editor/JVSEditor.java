/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.gui.Desktop;
import org.javascool.tools.FileManager;
import org.javascool.widgets.MainFrame;
import org.javascool.widgets.ToolBar;

/** Define a JVSEditor
 * Use JVSEditor to edit jvs files, it can be used as a panel
 * @author Philippe VIENNE
 */
public class JVSEditor extends JPanel implements Editor, ClosableComponent {

	private static final long serialVersionUID = 1L;
	/** Tests if on MacIntosh. */
	private static boolean isMac() {
		return System.getProperty("os.name").toUpperCase().contains("MAC");
	}
	/** The editor */
	private RSyntaxTextArea textPane;
	/** The scroll pane */
	private RTextScrollPane scrollPane;
	/** The ToolBar */
	private ToolBar toolBar;
	/** The Completion Provider */
	private JVSAutoCompletionProvider jacp;
	/** Opened file */
	private JVSFileReferance file;

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#getFile()
	 */
	@Override
	public JVSFileReferance getFile() {
		return file;
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#getName()
	 */
	@Override
	public String getName(){
		if(file==null)
			return "Nouveau Fichier";
		if(file.isTmp())
			return "Nouveau Fichier";
		else
			return file.getName();
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#setFile(org.javascool.gui.editor.JVSFileReferance)
	 */
	@Override
	public void setFile(JVSFileReferance file) {
		this.file = file;
		textPane.setText(file.getContent());
		firePropertyChange("name", null, getName());
	}

	/** Create a new JVSEditor
	 * Common setup
	 */
	public JVSEditor(JVSFileReferance file) {
		super(new BorderLayout());
		this.file=file;
		textPane = createTextArea();

		jacp=new JVSAutoCompletionProvider(textPane);
		jacp.setShowDescWindow(true);

		scrollPane=new RTextScrollPane(textPane);
		scrollPane.getGutter().setBorderColor(Color.BLACK);

		add(scrollPane);

		toolBar=new ToolBar();
		toolBar.add(new FormatCodeAction(textPane));
		//toolBar.add(new CompletionSwitchAction(jacp));

		add(toolBar,BorderLayout.NORTH);
		
		setText(file.getContent());
	}

	/** TextArea initialization
	 * Creates the text area for this application.
	 * @return The text area.
	 */
	private RSyntaxTextArea createTextArea() {
		RSyntaxTextArea textArea = new RSyntaxTextArea();
		textArea.setCaretPosition(0);
		textArea.requestFocusInWindow();
		textArea.setMarkOccurrences(true);
		textArea.setAntiAliasingEnabled(true);
		textArea.setText("");
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
		if (isMac()) {
			key = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.META_MASK);
		}
		KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
		if (isMac()) {
			KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.META_MASK);
		}
		textArea.getInputMap().put(key,"save");
		textArea.getActionMap().put("save", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				file.setContent(getText());
			}
			public void insertUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {}
		});
		return textArea;
	}

	/** Get the RSyntaxTextArea */
	public RSyntaxTextArea getRTextArea() {
		return textPane;
	}

	/** Retourne le RTextScrollPane de l'éditeur */
	public RTextScrollPane getScrollPane() {
		return scrollPane;
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#getText()
	 */
	@Override
	public String getText() {
		return textPane.getText();
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#hasToSave()
	 */
	@Override
	public Boolean hasToSave(){
		return file.hasToSave();
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#removeLineSignals()
	 */
	@Override
	public void removeLineSignals() {
		getScrollPane().getGutter().removeAllTrackingIcons();
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#save()
	 */
	@Override
	public Boolean save() {
		if(file.isTmp()){
			JFileChooser jfc=new JFileChooser();
			jfc.setApproveButtonText("Sauvegarder");
			if(jfc.showSaveDialog(MainFrame.getFrame())==JFileChooser.APPROVE_OPTION){
				if(!jfc.getSelectedFile().getName().endsWith(JVSFileReferance.SOURCE_EXTENTION)){
					jfc.setSelectedFile(new File(jfc.getSelectedFile().getParentFile(),jfc.getSelectedFile().getName()+JVSFileReferance.SOURCE_EXTENTION));
				}
				if(jfc.getSelectedFile().exists()){
					if(JOptionPane.showConfirmDialog(MainFrame.getFrame(), 
							"Êtes vous sûr de vouloir effacer ce fichier ?", 
							"Confirmation",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE)
							!= JOptionPane.OK_OPTION)
						return save();
				}
				FileManager.save(jfc.getSelectedFile().getAbsolutePath(), "", false, true);
				file.setFile(jfc.getSelectedFile());
				return save();
			} else {
				return false;
			}
		} else {
			file.setContent(getText());
			setFile(file);
			return file.save();
		}
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#saveBeforeClose()
	 */
	@Override
	public boolean saveBeforeClose() {
		if(!hasToSave())
			return true;
		int result = JOptionPane.showConfirmDialog(
				Desktop.getInstance().getFrame(),
				"Voulez vous enregistrer " + getName() + " avant de continuer ?");
		if (result == JOptionPane.YES_OPTION) {
			return save();
		} else if (result == JOptionPane.NO_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		textPane.setText(text);
	}

	/* (non-Javadoc)
	 * @see org.javascool.gui.editor.Editor#signalLine(int)
	 */
	@Override
	public void signalLine(int line) {
		Gutter gutter = getScrollPane().getGutter();
		gutter.setBookmarkingEnabled(true);
		ImageIcon icon = null;
		BufferedImage img;
		try {
			img = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/javascool/widgets/icons/error.png"));
			icon = new ImageIcon(img);
		} catch (IOException ex1) {
			System.err.println("Dysfonctionnement innatendu ici "+ex1);
		}
		try {
			getRTextArea().setCaretPosition(getRTextArea().getLineStartOffset(line - 1));
			getScrollPane().getGutter().addLineTrackingIcon(line - 1, icon);
		} catch (BadLocationException ex) {
			System.err.println("Dysfonctionnement innatendu ici "+ex);
		}
	}

	@Override
	public boolean isClosable() {
		if(getParent() instanceof JVSFileTabs){
			JVSFileTabs jtp=(JVSFileTabs)getParent();
			if(jtp.getTabCount()<2){
				return false;
			}
		}
		return true;
	}

	@Override
	public String getFullName() {
		return getName();
	}


}
