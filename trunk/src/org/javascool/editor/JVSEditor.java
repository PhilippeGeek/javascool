/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author philien
 */
public class JVSEditor extends JPanel {

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
//try {
//SyntaxScheme scheme = SyntaxScheme.load(textArea.getFont(), new java.io.FileInputStream("C:/temp/eclipse.xml"));
//textArea.setSyntaxScheme(scheme);
//} catch (Exception e) { e.printStackTrace(); }
        return textArea;
    }
}
