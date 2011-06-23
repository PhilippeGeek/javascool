/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Class Console
 * @author Philippe Vienne
 */
public class Console extends JPanel {
    
    public static JTextArea outputPane=Console.getConsoleTextPane();

    public Console() {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(Console.outputPane,BorderLayout.CENTER);
        Console.redirectSystemStreams();
        this.setVisible(true);
    }

    private static JTextArea getConsoleTextPane() {
        JTextArea outPane = new JTextArea();
        outPane.setEditable(false);
        float[] bg = Color.RGBtoHSB(200, 200, 200, null);
        outPane.setBackground(Color.getHSBColor(bg[0], bg[1], bg[2]));
        outPane.setToolTipText("La console");
        return outPane;
    }
    
    public static void clear(){
        outputPane.setText("");
    }

    private static void updateTextPane(final String text) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Console.outputPane.setText(Console.outputPane.getText()+text);
            }
        });
    }

    private static void redirectSystemStreams() {
        OutputStream out = new OutputStream() {

            @Override
            public void write(final int b) throws IOException {
                Console.updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                Console.updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}
