/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Philippe Vienne
 */
public class Dialog {

    public static void error(String title, String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void info(String title, String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(String title, String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                message,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    public static int questionYN(String title, String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                message,
                title,
                JOptionPane.YES_NO_OPTION);
    }

    public static int questionYNC(String title, String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static String prompt(String title, String message) {
        return JOptionPane.showInputDialog(new JFrame(),
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
