/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.javascool.JVSFile;
import org.javascool.Utils;
import org.javascool.gui.JVSMainPanel;
import org.javascool.jvs.Jvs2Java;
import org.javascool.pml.Pml;

/**
 * Class Proglet
 * @author Philippe Vienne
 */
public class Proglet {

    private Pml conf;
    private JPanel panel=new JPanel();
    private String jvsFunctions = "";
    private String name="";

    public Proglet(File directory) throws Exception {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception(directory + " is not a proglet folder");
        }
        this.name=directory.getName();
        if (new File(directory.getPath() + File.separator + "Panel.class").exists()) {
            try {
                this.panel=Proglet.loadPanelFromClass(directory.getPath() + File.separator + "Panel.class");
            } catch (Throwable ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (new File(directory.getPath() + File.separator + "Panel.jvs").exists()) {
            // @todo Compile a jvs File to a JPanel
        }
        if (new File(directory.getPath() + File.separator + "functions.jvs").exists()) {
            this.setupJvsFunctions(new File(directory.getPath() + File.separator + "functions.jvs"));
        }
    }

    public Proglet(String progletName) {
        this.name = progletName;
        this.setupPanel();
        this.setupJvsFunctions();
    }

    private void setupPanel() {
        if (Proglet.classExists("org.javascool.proglets." + this.name + ".Panel")) {
            try {
                panel = (JPanel) Class.forName("org.javascool.proglets." + this.name + ".Panel").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setupJvsFunctions() {

        try {
            String jvsFile;
            jvsFile = Utils.loadString(Class.forName("org.javascool.JvsMain").getResource("proglets/" + name + "/functions.jvs").toURI().toString());
            jvsFile = jvsFile.replaceAll("\n", "").replaceAll("\t", "");
            // Before include check if JVS functions form the proglet are compilable
            Jvs2Java.reportError = false;
            if (!Jvs2Java.jvsCompile(jvsFile + "\nvoid main(){echo(\"This is a jvs lib\");}")) {
                JVSMainPanel.Dialog.error("Erreur", "Le fichier de fonctions JVS de la proglet \"" + name + "\" n'est pas bon");
            } else {
                this.jvsFunctions = jvsFile;
            }
            Jvs2Java.reportError = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupJvsFunctions(File functionFile) {
        JVSFile file=new JVSFile(functionFile.getAbsolutePath(),true);
        String jvsFile;
        jvsFile = file.getCode();
        jvsFile = jvsFile.replaceAll("\n", "").replaceAll("\t", "");
        // Before include check if JVS functions form the proglet are compilable
        Jvs2Java.reportError = false;
        if (!Jvs2Java.jvsCompile(jvsFile + "\nvoid main(){echo(\"This is a jvs lib\");}")) {
            JVSMainPanel.Dialog.error("Erreur", "Le fichier de fonctions JVS de la proglet \"" + name + "\" n'est pas bon");
        } else {
            this.jvsFunctions = jvsFile;
        }
        Jvs2Java.reportError = true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
    
    public String getName(){
        return "Proglet "+name;
    }

    public String getJvsFunctionsToInclude() {
        return this.jvsFunctions;
    }

    private static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }
    
    /** Dynamically loads a Java runnable class to be used during this session.
     * @param path The path to the java class to load. The java class is supposed to belong to the "default" package, i.e. not to belong to a package.
     * @return An instantiation of this Java class. If the object is a runnable, the current runnable is set.
     *
     * @throws RuntimeException if an I/O exception occurs during command execution.
     * @throws IllegalArgumentException If the Java class name is not valid.
     */
    private static JPanel loadPanelFromClass(String path) throws Throwable {
        try {
            File javaClass = new File(path);
            URL[] urls = new URL[]{new URL("file:" + javaClass.getParent() + File.separator)};
            Class< ?> j_class = new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.class", ""));
            Object o = j_class.newInstance();
            if (!(o instanceof JPanel)) {
                throw new Exception("La class à charger n'est pas un JPanel");
            }
            return (JPanel) o;
        } catch (Throwable e) {
            throw new RuntimeException("Erreur: impossible de charger la class, erreur : "+e.getMessage());
        }
    }
}
