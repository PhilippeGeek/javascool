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
import javax.swing.JOptionPane;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.javascool.builder.gui.Dialog;

/**
 *
 * @author gmatheron
 */
public class JVSBMain {

    private String sketchbookUrlStr = "";
    private File sketchbook;
    private ArrayList<File> progletToInstall;

    public JVSBMain() {
        this.sketchbookUrlStr = new File(ClassLoader.getSystemResource("org/javascool/builder").getPath().split("!")[0]).getParent().replace("file:", "").replace("%20", " ");
        this.sketchbookUrlStr = "C:\\Users\\Philippe Vienne\\Documents\\NetBeansProjects\\jvsBuilder\\sketchbook";
        this.sketchbook = new File(this.sketchbookUrlStr);
        if (Dialog.questionYN("Bienvenu dans le JVSBuilder", "Bienvenu dans Java's cool builder.\nVotre sketchbook est apparament \n" + this.sketchbook.toString() + "\nEst-ce correct ?") == JOptionPane.OK_OPTION) {
            this.progletToInstall = JVSBMain.listProgletInDir(sketchbook);
            this.copyJVSBase();
            this.setupTmp();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("---------------\n Start JVS Builder \n---------------");
        try {
            JVSBMain jVSBMain = new JVSBMain();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getLocalizedMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
        System.out.println("---------------\n End JVS Builder \n---------------");
        System.exit(0);
    }
}
