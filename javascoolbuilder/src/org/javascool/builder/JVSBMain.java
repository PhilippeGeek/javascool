/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
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

    public static String title = "Java's cool Builder";
    private String sketchbookUrlStr = "";
    private File sketchbook;
    private ArrayList<File> progletToInstall;
    static File tmpDir;
    static ProgressBar pb;
    static String baseJarFile;
    static String hdocXsltFile;
    static String saxonJarFile;
    static boolean quiet=false;
    
    public JVSBMain() throws Exception {
        this.sketchbookUrlStr = new File(ClassLoader.getSystemResource("org/javascool/builder").getPath().split("!")[0]).getParent().replace("file:", "").replace("%20", " ");
        this.sketchbook = new File(this.sketchbookUrlStr);

        if (quiet || Dialog.questionYN("Bienvenue dans le JVSBuilder", "Bienvenue dans Java's cool builder.\nVotre sketchbook est apparament \n" + this.sketchbook.toString() + "\nEst-ce correct ?") == JOptionPane.OK_OPTION) {
            if (!quiet) JVSBMain.pb = new ProgressBar();
            if (!quiet) JVSBMain.pb.update(5, "Indexation du sketchbook ...");
            this.progletToInstall = this.listProgletInDir(sketchbook);
            if (!quiet) JVSBMain.pb.update(7, "Suppression des répertoires temporaires qui traînent ...");
            this.removeTmp();
            if (!quiet) JVSBMain.pb.update(10, "Création du répertoire temporaire ...");
            this.setupTmp();
            if (!quiet) JVSBMain.pb.update(20, "Copie des librairies ...");
            this.copyJVSBase();
            if (!quiet) JVSBMain.pb.update(25, "Copie de Java's cool ...");
            this.runJarAnt("setup");
            int i = this.progletToInstall.toArray().length;
            for (File progletDir : this.progletToInstall) {
                if (!quiet) JVSBMain.pb.update((50 / (i)) + 25, "Compilation de " + progletDir.getName() + " ...");
                Proglet proglet = new Proglet(progletDir);
                ProgletBuild progletBuild = new ProgletBuild(proglet);
                progletBuild.build();
                i--;
            }
            if (!quiet) JVSBMain.pb.update(80, "Création du jar ...");
            this.runJarAnt("jar");
            if (!quiet) JVSBMain.pb.canBeClosed(Boolean.TRUE);
            if (!quiet) JVSBMain.pb.update(100, "Terminé");
        } else {
            Dialog.error("Arret du Builder", "Vous devez mettre le JVSBuilder \nà la racine de votre sketchbook");
            System.exit(0);
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

    /** Copy all need files to the tmp directory */
    private void copyJVSBase() throws FileNotFoundException, IOException {
        ProgletBuild.copyFileFromJar("org/javascool/base.jar", tmpDir.getPath() + File.separator + "base.jar");
        JVSBMain.baseJarFile = tmpDir.getPath() + File.separator + "base.jar";
        ProgletBuild.copyFileFromJar("org/javascool/builder/resources/hdoc2htm.xslt", tmpDir.getPath() + File.separator + "hdoc2htm.xslt");
        JVSBMain.hdocXsltFile = tmpDir.getPath() + File.separator + "hdoc2htm.xslt";
        ProgletBuild.copyFileFromJar("org/javascool/builder/resources/hml2htm.xslt", tmpDir.getPath() + File.separator + "hml2htm.xslt");
        ProgletBuild.copyFileFromJar("org/javascool/builder/resources/saxon.jar", tmpDir.getPath() + File.separator + "saxon.jar");
        JVSBMain.saxonJarFile = tmpDir.getPath() + File.separator + "saxon.jar";
        ProgletBuild.copyFileFromJar("org/javascool/builder/resources/build-jar.xml", tmpDir.getPath() + File.separator + "build.xml");
    }

    /** Run an ant target from build-jar.xml
     * It use to global tasks like uncompress Java's cool base or build the 
     * final jar
     * @param target The ant target to run
     * @throws FileNotFoundException An error when it copy the ant script
     * @throws IOException An error when it copy the ant script
     */
    private void runJarAnt(String target) throws FileNotFoundException, IOException {
        if (!new File(tmpDir.getPath() + File.separator + "build.xml").exists()) {
            this.copyJVSBase();
        }
        File buildFile = new File(tmpDir.getPath() + File.separator
                + "build.xml");
        Project p = new Project();
        p.setBaseDir(this.sketchbook);
        p.setUserProperty("ant.file", buildFile.getPath());
        p.setUserProperty("jvs.builderjar", this.sketchbook.getAbsolutePath()
                + File.separator + "javascool-builder.jar");
        p.setUserProperty("jvs.basejar", JVSBMain.baseJarFile);
        p.setUserProperty("jvs.tojar.tmp", "" + tmpDir.getName() + "/tojar");
        p.setUserProperty("jvs.tmp", "" + tmpDir.getName() + "");
        p.setUserProperty("jvs.newjar", this.sketchbook.getAbsolutePath()
                + File.separator + "javascool-personnel.jar");
        p.setUserProperty("jvs.jarname", "Java's Cool Sketchbook");
        p.init();
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        p.addReference("ant.projectHelper", helper);
        p.addReference("includeantruntime", false);
        helper.parse(p, buildFile);
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.out);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        p.executeTarget(target);
    }

    /** Create a tmp Directory */
    private void setupTmp() {
        String uuid = UUID.randomUUID().toString();
        tmpDir = new File(this.sketchbook.getPath() + File.separator + "tmp-" + uuid);
        tmpDir.mkdirs();
   //     tmpDir.deleteOnExit();
    }

    /** Removes the tmp directories that have been left by previous runs of jvsb 
     Doesn't work :( */
    private void removeTmp() {
   /*     File dir = new File(this.sketchbook.getPath());
        String[] children = dir.list();
        if (children == null) {
            return;
        } else {
            for (String child : children) {
                if (child.startsWith("tmp-")) {
                    (new File(child)).delete();
                }
            }
        }*/
    }

    /** Sleep the Builder for 200 msec */
    private void sleep() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            throw new RuntimeException("Programme arrêté !");
        }
    }

    /** Close all opened window */
    public static void close() {
        if (!quiet && JVSBMain.pb.isVisible()) {
            JVSBMain.pb.dispose();
        }/*
        try {
        if (!suppr(JVSBMain.tmpDir)) {
        Dialog.error("Error", "Le dossier temporaire n'a pas été supprimé automatiquement.\nIl se trouve à la racine de votre sketchbook.");
        }
        } catch (Exception e) {
        Utils.report(e);
        }*/
        System.exit(0);
    }

    public static Boolean suppr(File r) {
        File[] fileList = r.listFiles();
        Boolean s = true;
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                s = s && suppr(fileList[i]);
            }
            s = s && fileList[i].delete();
        }
        return s;
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
        if ((System.getProperty("java.version").charAt(2)) < '5') {
            final int n = JOptionPane.showConfirmDialog(
                    new JFrame(),
                    "<html>Vous n'avez pas une version suffisante de Java<br>"
                    + JVSBMain.title + " requière Java 1.5 ou plus.<br>"
                    + "Voulez vous être redirigé vers le site de téléchargements ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
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
        if (args.length>0 && args[0].equals("-q")) quiet=true;
        else if (args.length>0 && (args[0].equals("-h") || args[0].equals("--help"))) {
            System.out.println("JavaScoolBuilder - Builds a Java's Cool customized jar");
            System.out.println("Usage : java -jar javascool-builder.jar [-q]");
            System.out.println("Options : ");
            System.out.println("\t-q\tDisplays all output to the standard output and does not display dialogs");
            System.exit(1);
        } else if (args.length>0) {
            System.out.println("java -jar javascool-builder.jar --help for more info");
            System.exit(0);
        }
        
        JVSBMain jVSBMain = null;
        try {
            JVSBMain.setUpSystem();
            jVSBMain = new JVSBMain();
        } catch (Exception e) {
            Utils.report(e);
            System.err.println("Le programme a du s'arreter, voici la cause : \n" + e.getLocalizedMessage());
            if (!quiet) JVSBMain.pb.update(100, "Erreur !!");
            if (!quiet) JVSBMain.pb.canBeClosed(Boolean.TRUE);
            System.exit(0);
        }
    }
}
