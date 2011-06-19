/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.javascool.gui.JVSMainPanel;

/** The Start Class for Java's cool
 * It will contain all engines to start and setup Java's cool as a stand alone program, an applet or an sub-panel in an app.
 * @author Philippe Vienne
 */
public class JvsMain {

    public static String title = "Java's Cool 3.3 Dev";

    
    /** Setup the system to run Java's cool
     * Set look and feel
     * @todo Setup here the execution path or just check it
     */
    static void setUpSystem(){
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            try {
                // We are on windows
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
            }
        } else {
            try {
                // We set menu for Mac
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
            } catch (Exception e) {
            }
            try {
                // We are on an *nix's system or a mac
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Error
                System.err.println(e.getMessage());
                System.err.println("Note: Utilisaton du thème Java (et non du système)");
            }
        }
    }
    
    static JFrame getJVSMainJFrame(){
        JFrame main=new JFrame();
        main.add(new JVSMainPanel());
        main.setAlwaysOnTop(true);
        main.setTitle(title);
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return main;
    }

    /** Used to run a JavaScool 3.3 as a standalone program.
     * <p>- Starts a JavaScool "activity" which result is to be stored in a "file-name".</p>
     * @param usage <tt>java org.javascool.Main [activity [file-name]]</tt><ul>
     * <li><tt>activity</tt> specifies the activity to be done (its index or name).</li>
     * <li><tt>file-name</tt> specifies the file used for the activity.</li>
     * </ul>
     */
    public static void main(String[] usage) {
        System.out.println("---------------------\n" + title + "\n---------------------\nstarting..");
        org.javascool.JvsMain.setUpSystem();
        JFrame main=getJVSMainJFrame();
        main.setVisible(true);
    }
}
