/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool;

import org.javascool.tools.Utils;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.javascool.gui.JVSMainPanel;
import org.javascool.pml.Pml;
import org.javascool.tools.Macros;

/** The Start Class for Java's cool
 * It will contain all engines to start and setup Java's cool as a stand alone program, an applet or an sub-panel in an app.
 * @author Philippe Vienne
 */
public class JVSMain {
    
    public static final String title = "Java's Cool 3.3";
    public static final String logo = "org/javascool/doc-files/logo.png";
    public static final String logo32 = "org/javascool/doc-files/icon32/logo.png";
    public static final String logo16 = "org/javascool/doc-files/icon16/logo.png";
    
    private static JFrame jvsMainFrame;
    private static JVSConf jvsConf=new JVSConf();
    private static JVSMainPanel jvsMainPanel;

    /** Setup the system to run Java's cool
     * Set look and feel
     */
    static void setUpSystem() {
        //<editor-fold defaultstate="collapsed" desc="Style setup">
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            String os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                try {
                    // We are on windows
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception ex) {
                }
            } else {
                try {
                    // We are on an *nix's system or a mac
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    // Error
                    System.err.println(e.getMessage());
                    System.err.println("Note: Utilisaton du thème Java (et non du système)");
                }
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Mac setup">
        if (JVSMain.isMac()) {
            try {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", JVSMain.title);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                JVSMain.reportBug(ex);
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Check Java Version">
        if((System.getProperty("java.version").charAt(2))<'5'){
            final int n = JOptionPane.showConfirmDialog(
                    new JFrame(),
                    "<html>Vous n'avez pas une version suffisante de Java<br>"
                    + JVSMain.title +" requière Java 1.5 ou plus.<br>"
                    + "Voulez vous être redirigé vers le site de téléchargements ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
            if (n == JOptionPane.YES_OPTION) {
                Utils.openURL("http://www.java.com/getjava");
            }
            System.exit(-1);
        }
        //</editor-fold>
    }

    /**
     * Create the Main Frame for this app
     */
    private static void setUpJVSMainFrame() {
        jvsMainFrame = new JFrame();
        jvsMainPanel = new JVSMainPanel();
        jvsMainFrame.add(jvsMainPanel);
        jvsMainFrame.setTitle(title);
        jvsMainFrame.setIconImage(Utils.getIcon(JVSMain.logo).getImage());
        jvsMainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jvsMainFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (JVSMainPanel.close()) {
                    jvsMainFrame.setVisible(false);
                    jvsMainFrame.dispose();
                    System.exit(0);
                }
            }
        });
        jvsMainFrame.pack();
        jvsMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /** Get the jvsMainFrame */
    public static JFrame getJvsMainFrame() {
        return JVSMain.jvsMainFrame;
    }

    /** Get the jvsMainPanel instance */
    public static JVSMainPanel getJvsMainPanel() {
        return JVSMain.jvsMainPanel;
    }
    
    /** Get the JVSConf instance */
    public static JVSConf getJvsConf() {
        return JVSMain.jvsConf;
    }
    
    public static JVSMainPanel getJvs(){
        return JVSMain.jvsMainPanel;
    }

    /** Test if we are on Mac */
    public static Boolean isMac() {
        return false;
    }
    
    /** Function used to report a bug */
    public static void reportBug(Exception e){
        System.err.println("Bug : "+e.getMessage()+" Source : "+e.getCause().toString());
    }

    /** Used to run a JavaScool 3.3 as a standalone program.
     * <p>- Starts a JavaScool "activity" which result is to be stored in a "file-name".</p>
     * @param usage <tt>java org.javascool.JVSMain [activity [file-name]]</tt><ul>
     * <li><tt>activity</tt> specifies the activity to be done (its index or name).</li>
     * <li><tt>file-name</tt> specifies the file used for the activity.</li>
     * </ul>
     */
    public static void main(String[] usage) {
        try {
            System.err.println("" + title + " is starting ...");
            org.javascool.JVSMain.setUpSystem();
            JVSMain.setUpJVSMainFrame();
            JVSMain.getJvsMainFrame().setVisible(true);
            JVSMain.getJvsMainPanel().revalidate();
            JVSMain.getJvsMainFrame().pack();
            JVSMain.getJvsMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        } catch (Exception e) {
            System.err.println("Erreur Java : \n"+e.getMessage()+"\nCall stack: \n");
            e.printStackTrace(System.err);
        }
    }
}
