/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.javascool.JVSFile;
import org.javascool.Utils;
import org.javascool.gui.JVSMainPanel;
import org.javascool.gui.JVSMainPanel.Dialog;
import org.javascool.jvs.Jvs2Java;
import org.javascool.pml.Pml;

/**
 * Class Proglet
 * @author Philippe Vienne
 */
public class Proglet {

    private Pml conf;
    private ArrayList<String> depClass;
    private JPanel panel = new JPanel();
    private File help;
    private Boolean jvsFunctions = false;
    private String packageName="org.javascool.proglets.tools";
    private String name = "";

    public Proglet(File directory) throws Exception {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception(directory + " is not a proglet folder");
        }
        this.name = directory.getName();
        if (new File(directory.getPath() + File.separator + "Panel.class").exists()) {
            try {
                this.panel = Proglet.loadPanelFromClass(directory.getPath() + File.separator + "Panel.class");
            } catch (Throwable ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (new File(directory.getPath() + File.separator + "Panel.jvs").exists()) {
            if (Jvs2Java.javaCompile(directory.getPath() + File.separator + "Panel.jvs").getDiagnostics().isEmpty()) {
                try {
                    this.panel = Proglet.loadPanelFromClass(directory.getPath() + File.separator + "Panel.class");
                } catch (Throwable ex) {
                    Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Dialog.error("Erreur de chargement", "La proglet " + name + " n'as pas pu être chargé car il y a eu une erreur de compilation dans le Panel");
            }
        } else if (new File(directory.getPath() + File.separator + "Panel.java").exists()) {
            if (Jvs2Java.javaCompile(directory.getPath() + File.separator + "Panel.java").getDiagnostics().isEmpty()) {
                try {
                    this.panel = Proglet.loadPanelFromClass(directory.getPath() + File.separator + "Panel.class");
                } catch (Throwable ex) {
                    Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Dialog.error("Erreur de chargement", "La proglet " + name + " n'as pas pu être chargé car il y a eu une erreur de compilation dans le Panel");
            }
        }
        if (new File(directory.getPath() + File.separator + "Help.html").exists()) {
            this.help=new File(directory.getPath() + File.separator + "Help.html");
        }
        if (new File(directory.getPath() + File.separator + "functions.jvs").exists()) {
            this.setupJvsFunctions(new File(directory.getPath() + File.separator + "functions.jvs"));
        }
    }

    public Proglet(String progletName) {
        this.name = progletName;
        this.packageName = "org.javascool.proglets."+progletName;
        this.conf=new Pml();
        this.setupPanel();
        this.setupJvsFunctions();
        try {
            try {
                this.conf.load(Class.forName("org.javascool.JvsMain").getResource("proglets/" + name + "/Proglet.pml").toURI().toString());
                this.help=new File(Class.forName("org.javascool.JvsMain").getResource("proglets/" + name + "/Help.html").toURI());
            } catch (URISyntaxException ex) {
                Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proglet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!this.conf.getString("name").equals("")){
            this.name=this.conf.getString("name");
        }
        if(!this.conf.getString("javaImport").equals("")){
            this.depClass=new ArrayList<String>();
            this.depClass.addAll(Arrays.asList(this.conf.getString("javaImport").split(",")));
        }
        if(!this.help.exists()){
            this.help=null;
        }
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
        if (Proglet.classExists("org.javascool.proglets." + this.name + ".Functions")) {
            this.jvsFunctions=true;
        }
    }

    private void setupJvsFunctions(File functionFile) {
        if (functionFile.exists()){
            Jvs2Java.javaCompile(functionFile.getPath());
            
        }
    }

    public String getHelpFileUrl(){
        return this.help.toURI().toString();
    }
    
    public ArrayList<String> getJavaDependance(){
        if(this.depClass==null){
            return new ArrayList<String>();
        }
        return this.depClass;
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
    
    public String getPackage(){
        return this.packageName;
    }

    public String getName() {
        return "Proglet " + name;
    }

    public Boolean getJvsFunctionsToInclude() {
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
            throw new RuntimeException("Erreur: impossible de charger la class, erreur : " + e.getMessage());
        }
    }
    
    private static void loadFromClass(String path) throws Throwable {
        try {
            File javaClass = new File(path);
            URL[] urls = new URL[]{new URL("file:" + javaClass.getParent() + File.separator)};
            Class< ?> j_class = new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.class", ""));
        } catch (Throwable e) {
            throw new RuntimeException("Erreur: impossible de charger la class, erreur : " + e.getMessage());
        }
    }
}
