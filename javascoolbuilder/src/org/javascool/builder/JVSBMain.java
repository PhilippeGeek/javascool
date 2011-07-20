/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.javascool.builder.gui.Dialog;
import org.javascool.builder.gui.ProgressBar;
import org.javascool.tools.Utils;

/**
 *
 * @author gmatheron
 */
public class JVSBMain {

    public static String title="Java's cool Builder";
    
    private String sketchbookUrlStr = "";
    private File sketchbook;
    private ArrayList<File> progletToInstall;
    
    private static ProgressBar pb;

    public JVSBMain() {
        this.sketchbookUrlStr = new File(ClassLoader.getSystemResource("org/javascool/builder").getPath().split("!")[0]).getParent().replace("file:", "").replace("%20", " ");
        this.sketchbookUrlStr = "C:\\Users\\Philippe Vienne\\Documents\\NetBeansProjects\\sketchbook";
        this.sketchbook = new File(this.sketchbookUrlStr);
        if (Dialog.questionYN("Bienvenu dans le JVSBuilder", "Bienvenu dans Java's cool builder.\nVotre sketchbook est apparament \n" + this.sketchbook.toString() + "\nEst-ce correct ?") == JOptionPane.OK_OPTION) {
            JVSBMain.pb=new ProgressBar();
            JVSBMain.pb.update(5, "Indexation du sketchbook ...");
            this.progletToInstall = JVSBMain.listProgletInDir(sketchbook);
            this.sleep();
            JVSBMain.pb.update(15, "Copie des librairies ...");
            this.copyJVSBase();
            this.sleep();
            JVSBMain.pb.update(25, "Création du répertoire temporaire ...");
            this.setupTmp();
            this.sleep();
            for (File progletDir : this.progletToInstall) {
                try {
                    String functionFile=org.javascool.tools.Utils.loadString(progletDir.getPath()+File.separator+"Functions.java");
                    functionFile.replaceAll("package [^;]*;", "");
                    functionFile="package org.javascool.proglets."+progletDir.getName()+";\n"+functionFile;
                    org.javascool.tools.Utils.saveString(progletDir.getPath()+File.separator+"Functions.java", functionFile);
                } catch (IOException ex) {
                    Logger.getLogger(JVSBMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                File buildFile = new File(progletDir.getAbsolutePath() + File.separator + "build.xml");
                Project p = new Project();
                p.setBaseDir(progletDir);
                p.setUserProperty("ant.file", buildFile.getAbsolutePath());
                p.setUserProperty("jvs.builderjar", "../javascool-builder.jar");
                p.setUserProperty("jvs.basejar", "../base.jar");
                p.setUserProperty("proglet.build", "../tmp/build");
                p.setUserProperty("proglet.src", "../tmp/"+progletDir.getName()+"-src");
                p.setUserProperty("jvs.tojar.tmp", "../tmp/tojar");
                p.setUserProperty("jvs.newjar", "../javascool-personel.jar");
                p.init();
                ProjectHelper helper = ProjectHelper.getProjectHelper();
                p.addReference("ant.projectHelper", helper);
                p.addReference("includeantruntime", false);
                helper.parse(p, buildFile);
                DefaultLogger consoleLogger = new DefaultLogger();
                consoleLogger.setErrorPrintStream(System.err);
                consoleLogger.setOutputPrintStream(System.out);
                consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
                p.addBuildListener(consoleLogger);
                p.executeTarget(p.getDefaultTarget());
            }
            JVSBMain.pb.dispose();
        } else {
            Dialog.error("Arret du Builder", "Vous devez mettre le JVSBuilder \nà la racine de votre sketchbook");
        }

    }

    /** List proglets in a directory
     * List all directory which contains a proglet and possible jars
     * Directories found will contain a file Proglet.pml
     * Jars found will be named javascool-proglet-<Proglet Name>.jar
     * @param directory The directory where we will find
     * @return All ellements as File in an ArrayList
     */
    private static ArrayList<File> listProgletInDir(File directory) {
        ArrayList<File> progletList = new ArrayList<File>();
        for (String entery : directory.list(new ProgletFileNameFilter())) {
            progletList.add(new File(directory.getPath() + File.separator + entery));
        }
        return progletList;
    }

    private void copyJVSBase() {
        try {
            File f2 = new File(this.sketchbookUrlStr+File.separator+"base.jar");
            InputStream in = ClassLoader.getSystemResourceAsStream("org/javascool/base.jar");

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);
            System.out.println("Coping ...");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("JVS base copied");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void setupTmp() {
        try {
            File f2 = new File(this.sketchbookUrlStr+File.separator+"tmp"+File.separator);
            System.out.println("Create tmp directory");
            f2.mkdirs();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /** Sleep the Builder for 1 sec */
    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Programme arrêté !");
        }
    }
    
    /** Close all opened window */
    public static void close(){
        if(JVSBMain.pb.isVisible()){
            JVSBMain.pb.dispose();
        }
    }
    
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
        //<editor-fold defaultstate="collapsed" desc="Check Java Version">
        if((System.getProperty("java.version").charAt(2))<'5'){
            final int n = JOptionPane.showConfirmDialog(
                    new JFrame(),
                    "<html>Vous n'avez pas une version suffisante de Java<br>"
                    + JVSBMain.title +" requière Java 1.5 ou plus.<br>"
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("---------------\n Start JVS Builder \n---------------");
        JVSBMain jVSBMain = null;
        try {
            JVSBMain.setUpSystem();
             jVSBMain=new JVSBMain();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getLocalizedMessage());
            e.printStackTrace(System.err);
            JVSBMain.close();
            Dialog.error("Erreur fatal", "Le programme a du s'arreter, voici la cause : \n"+e.getLocalizedMessage());
            System.exit(1);
        }
        System.out.println("---------------\n End JVS Builder \n---------------");
        System.exit(0);
    }
}
