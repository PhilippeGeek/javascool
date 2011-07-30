/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.javascool.builder.gui.Dialog;
import org.javascool.jvs.Jvs2Java;
import org.javascool.pml.Pml;
import org.javascool.tools.Utils;

/**
 *
 * @author Philippe Vienne
 */
public class ProgletBuild {

    /** The proglet object */
    Proglet proglet;
    /** The proglet directory */
    File progletDir;
    /** The doc directory */
    File docDir;
    /** The tmp src directory */
    File srcDir;
    /** The tmp build directory */
    File buildDir;
    /** The directory toJar
     * It will contain all file as they will be in the jar
     */
    File toJarDir;

    /** Build the proglet
     * Make all we need, compile and copy to the jar folder the Proglet
     * @param progletToCompile The verified proglet
     * @throws FileNotFoundException
     * @throws IOException  
     */
    public ProgletBuild(org.javascool.builder.Proglet progletToCompile) throws FileNotFoundException, IOException {
        proglet = progletToCompile;
        progletDir = proglet.getProgletDir();
        docDir = new File(JVSBMain.tmpDir.getPath() + File.separator + proglet.getPackageName() + "-doc");
        srcDir = new File(JVSBMain.tmpDir.getPath() + File.separator + proglet.getPackageName() + "-src");
        buildDir = new File(JVSBMain.tmpDir.getPath() + File.separator + proglet.getPackageName() + "-build");
        toJarDir = new File(JVSBMain.tmpDir.getPath() + File.separator + "tojar");
        // Create all dirs
        docDir.mkdirs();
        srcDir.mkdirs();
        buildDir.mkdirs();

    }

    private static String readFileAsString(String filePath)
            throws java.io.IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    /** Build the proglet
     * This function compile the proglet.<br/>
     * <p>
     * Firstly, it replace all packages indication in java files<br/>
     * After, it edit doc file to make a syntax color on code balise<br/>
     * Finaly, it call the ant script which call Xslt Saxon, build java files and
     * copy all files to the to jar directory.<br/>
     * </p>
     * <p>Before call this function, you have to check if the Proglet is good.
     * If no, don't call this function.</p>
     * @todo Manage compilation bugs
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void build() throws FileNotFoundException, IOException {
        String[] javaFiles = null;
        try {
            javaFiles = progletDir.list(new JavaFileNameFilter());
        } catch (Exception e) {
            Utils.report(e);
        }
        if (!this.proglet.hasHelp()) {
            System.err.println("No help file for proglet : " + proglet.getName());
            Dialog.error("Erreur", "Pas de fichier d'aide pour " + proglet.getName() + ", la proglet ne sera pas construite.");
            return;
        }
        if (this.proglet.getConf().getString("title").isEmpty() || this.proglet.getConf().getString("author").isEmpty()) {
            System.err.println("Error in configuration file for proglet : " + proglet.getName());
            Dialog.error("Erreur", "Le fichier de configuration de " + proglet.getName() + " ne respecte pas les spécifications, la proglet ne sera pas construite.");
            return;
        }
        System.out.println("Setting up java files ...");
        for (String name : javaFiles) {
            String functionFile = new String(org.javascool.tools.Utils.loadString(progletDir.getPath() + File.separator + name).getBytes(), "UTF-8");
            functionFile = functionFile.replaceAll("package [^;]*;[\n]*", "");
            functionFile = "package org.javascool.proglets." + progletDir.getName() + ";\n" + functionFile;
            org.javascool.tools.Utils.saveString(progletDir.getPath() + File.separator + name, functionFile);
        }

        new Pml().load(progletDir.getPath() + File.separator + "proglet.pml").save(progletDir.getPath() + File.separator + "proglet.php", "php");

        File demo = new File(progletDir.getPath() + File.separator + "demo.jvs");
        if (demo.exists()) {
            String demoC = readFileAsString(demo.getAbsolutePath());
            String demoT = Jvs2Java.translate(demoC);
            File demoO=new File(progletDir.getPath()+File.separator + "demo.jvsc");
            BufferedWriter demoS=new BufferedWriter(new FileWriter(demoO));
            demoS.write(demoT);
            demoS.close();
        }
        /*    (new File(progletDir.getPath() + File.separator + "proglet.php")).deleteOnExit();
        (new File(progletDir.getPath() + File.separator + "doc")).deleteOnExit();*/
        String[] docFiles = progletDir.list(new DocumentationFileNameFilter());
        System.out.println("Setting up docs ...");
        (new File(progletDir.getPath() + File.separator + "doc")).mkdir();
        for (String name : docFiles) {
            System.out.println("Setting up doc file : " + name);
            // Mais le xslt est lancé avant ça ou après ? Il vaudrait mieux qu'il soit lancé avant, sinon il râle sur le code que tu génères
            // ici !
            String docFile = new String(org.javascool.tools.Utils.loadString(progletDir.getPath() + File.separator + name).getBytes(), "UTF-8");
            docFile = Utils.htm2xml(docFile);   //FIXME Ah ben non alors, c'est l'inverse ! On veut convertir du xml en htm !
            //EDIT ok j'ai compris, mais le nom de la fonction n'est vraiment pas très explicite...
            String[] splitCodeStart = docFile.split("<code>");
            docFile = "";
            for (String codeToSplit : splitCodeStart) {

                if (codeToSplit.split("</code>").length == 1) {
                    docFile = docFile + codeToSplit;
                } else {
                    String code = codeToSplit.split("</code>", 2)[0];
                    docFile = docFile + "<div class=\"code\"><code>" + org.javascool.builder.doc.Formater.format(code) + "</code></div>" + codeToSplit.split("</code>", 2)[1];
                }

            }
            org.javascool.tools.Utils.saveString(progletDir.getPath() + File.separator + "doc" + File.separator + name, Utils.htm2xml(docFile));
        }
        ProgletBuild.copyFileFromJar("org/javascool/builder/resources/build-proglet.xml", progletDir.getPath() + File.separator + "build.xml");
        File buildFile = new File(progletDir.getAbsolutePath() + File.separator + "build.xml");
        Project p = new Project();
        p.setBaseDir(progletDir);
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());
        p.setUserProperty("jvs.builderjar", "../javascool-builder.jar");
        p.setUserProperty("jvs.basejar", JVSBMain.baseJarFile);
        p.setUserProperty("jvs.hdocxslt", JVSBMain.hdocXsltFile);
        p.setUserProperty("jvs.saxon", JVSBMain.saxonJarFile);
        p.setUserProperty("proglet.build", buildDir.getPath());
        p.setUserProperty("proglet.name", proglet.getPackageName());
        p.setUserProperty("proglet.doc", docDir.getPath());
        p.setUserProperty("proglet.src", srcDir.getPath());
        p.setUserProperty("jvs.tojar.tmp", toJarDir.getPath());
        p.setUserProperty("jvs.newjar", "../javascool-personel.jar");
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
        System.out.println("Calling ant to build the proglet : ");
        p.executeTarget(p.getDefaultTarget());
        System.out.println("Done");
        buildFile.delete();
        /*   (new File(progletDir.getPath() + File.separator + "proglet.php")).delete();
        (new File(progletDir.getPath() + File.separator + "doc")).delete();*/
    }

    /** Copy a file from the jar
     * Copy a jar resource to the destination. Build all directories of the
     * destination to don't have error.
     * @param fileInJar
     * @param dest 
     * @throws FileNotFoundException If the dest file can not be found
     * @throws IOException If we can't copy the file to dest
     */
    static void copyFileFromJar(String fileInJar, String dest)
            throws FileNotFoundException, IOException {

        File f2 = new File(dest);
        InputStream in = ClassLoader.getSystemResourceAsStream(fileInJar);

        // Create directories for dest File (f2)
        f2.getParentFile().mkdirs();
        f2.deleteOnExit();

        //For Overwrite the file.
        OutputStream out = new FileOutputStream(f2);
        System.err.println("Coping " + fileInJar + " ...");
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        System.err.println("" + fileInJar + " copied");
    }
}
