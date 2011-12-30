/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.gui.editor.CompletionSwitchAction;
import org.javascool.gui.editor.FormatCodeAction;
import org.javascool.gui.editor.JVSAutoCompletionProvider;
import org.javascool.widgets.ToolBar;

/** Define a JVSEditor
 * Use JVSEditor to edit jvs files, it can be used as a panel
 * @author Philippe VIENNE
 */
class JVSEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	/** The editor */
	private RSyntaxTextArea textPane;
	/** The scroll pane */
	private RTextScrollPane scrollPane;
	/** The ToolBar */
	private ToolBar toolBar;
	/** The Completion Provider */
	private JVSAutoCompletionProvider jacp;

	/** Create a new JVSEditor
	 * Common setup
	 */
	public JVSEditor() {
		super(new BorderLayout());
		textPane = createTextArea();

		jacp=new JVSAutoCompletionProvider(textPane);
		jacp.setShowDescWindow(true);

		scrollPane=new RTextScrollPane(textPane);
		scrollPane.getGutter().setBorderColor(Color.BLACK);

		add(scrollPane);
		
		toolBar=new ToolBar();
		toolBar.add(new FormatCodeAction(textPane));
		toolBar.add(new CompletionSwitchAction(jacp));
		
		add(toolBar,BorderLayout.NORTH);
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
		textArea.getActionMap().put("save",
				new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JVSPanel.getInstance().saveFile();
			}
		});
		return textArea;
	}

	/** Tests if on mac. */
	private static boolean isMac() {
		return System.getProperty("os.name").toUpperCase().contains("MAC");
	}

	/** Get text into the TextArea
	 * @return The code
	 */
	public String getText() {
		return textPane.getText();
	}

	/** Set the text
	 * @param text The text to write on screen
	 */
	public void setText(String text) {
		textPane.setText(text);
	}

	/** Get the RSyntaxTextArea */
	public RSyntaxTextArea getRTextArea() {
		return textPane;
	}

	/** Retourne le RTextScrollPane de l'éditeur */
	public RTextScrollPane getScrollPane() {
		return scrollPane;
	}

	public void removeLineSignals() {
		getScrollPane().getGutter().removeAllTrackingIcons();
	}

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

	
}
