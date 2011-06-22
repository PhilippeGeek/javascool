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
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.javascool.gui.JVSFileEditorTabs;

/**
 *
 * @author philien
 */
public class JVSEditor extends JPanel implements Editor{

    private RSyntaxTextArea TextPane;
    private RTextScrollPane scrollPane;

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

    /**
     * Creates the text area for this application.
     *
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
        //Ctrl-b to go backward one character
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);

        textArea.getInputMap().put(key,
                "save");
        textArea.getActionMap().put("save",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getEditorTabs().getMainPanel().saveFile();
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
    
    public JVSFileEditorTabs getEditorTabs(){
        try{
            return (JVSFileEditorTabs)getParent();
        } catch (Exception e){
            return new JVSFileEditorTabs();
        }
    }
}
