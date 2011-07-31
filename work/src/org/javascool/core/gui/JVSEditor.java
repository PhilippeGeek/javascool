/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.JVSMain;
import org.javascool.gui.JVSFileEditorTabs;
import org.javascool.gui.JVSMainPanel;

/** Define a JVSEditor
 * Use JVSEditor to edit jvs files, it can be used as a panel
 * @author Philippe VIENNE
 */
public class JVSEditor extends JPanel {
 private static final long serialVersionUID = 1L;
    /** The editor */
    private RSyntaxTextArea TextPane;
    /** The scroll pane */
    private RTextScrollPane scrollPane;

    /** Create a new JVSEditor
     * Common setup
     */
    public JVSEditor() {
        this.setLayout(new BorderLayout());
        TextPane = this.createTextArea();
        TextPane.setSyntaxEditingStyle(org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_JAVA);
        scrollPane = new RTextScrollPane(TextPane, true);
        Gutter gutter = scrollPane.getGutter();
        gutter.setBorderColor(Color.BLUE);
        
        this.add(scrollPane,BorderLayout.CENTER);
        this.setVisible(true);
    }

    /** TextArea initialization
     * Creates the text area for this application.
     * @return The text area.
     */
    private RSyntaxTextArea createTextArea() {
        RSyntaxTextArea textArea = new RSyntaxTextArea(25, 70);
        textArea.setCaretPosition(0);
        //textArea.addHyperlinkListener(this);
        textArea.requestFocusInWindow();
        textArea.setMarkOccurrences(true);
        textArea.setTextAntiAliasHint("VALUE_TEXT_ANTIALIAS_ON");
        textArea.setText("");
        
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
        if(JVSMain.isMac()){
            key=KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.META_MASK);
        }
        KeyStroke copy_key = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK);
        if(JVSMain.isMac()){
            copy_key=KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.META_MASK);
        }

        textArea.getInputMap().put(key,
                "save");
        textArea.getActionMap().put("save",
                new AbstractAction() {
 private static final long serialVersionUID = 1L;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JVSMainPanel.saveFile();
                    }
                });
        
        textArea.getInputMap().put(copy_key,
                "copy");
        textArea.getActionMap().put("copy",
                new AbstractAction() {
 private static final long serialVersionUID = 1L;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getRTextArea().copyAsRtf();
                    }
                });
        
        return textArea;
    }

    /** Get text into the TextArea
     * @return The code
     */
    @Override
    public String getText() {
        return TextPane.getText();
    }

    /** Set the text
     * @param text The text to write on screen
     */
    @Override
    public void setText(String text) {
        TextPane.setText(text);
    }
    
    /** Get the RSyntaxTextArea */
    public RSyntaxTextArea getRTextArea(){
        return TextPane;
    }
    
    public RTextScrollPane getScrollPane() {
        return scrollPane;
    }
    
    public void removeLineSignals() {
        getScrollPane().getGutter().removeAllTrackingIcons();
    }
    
    public void signalLine(int line){
        Gutter gutter = getScrollPane().getGutter();
        gutter.setBookmarkingEnabled(true);
        ImageIcon icon=null;
        BufferedImage img;
        try {
            img = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/javascool/doc-files/icon16/error.png"));
            icon=new ImageIcon(img);
        } catch (IOException ex1) {
        }
        try {
            getRTextArea().setCaretPosition(getRTextArea().getLineStartOffset(line-1));
            getScrollPane().getGutter().addLineTrackingIcon(line-1, icon);
        } catch (BadLocationException ex) {
            Logger.getLogger(JVSEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
