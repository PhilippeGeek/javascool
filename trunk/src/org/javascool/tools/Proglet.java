/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import org.javascool.JVSFile;
import org.javascool.Utils;
import org.javascool.gui.JVSHtmlDisplay;
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
    private Boolean hasPanel = false;
    private JVSHtmlDisplay help;
    private Boolean jvsFunctions = false;
    private Boolean packagedInJvs = true;
    private String[] observer = {"", ""};
    private String fullPackageName = "org.javascool.proglets.tools";
    private String name = "";
    private String packageName = "ingredients";

    public Proglet(String progletName) throws Exception {
        // Set the name
        this.name = progletName;
        // Set the package name
        this.packageName = progletName;
        // It's an official package, so it's in org.javascool.proglets
        this.fullPackageName = "org.javascool.proglets." + progletName;
        // We will store configuration
        this.conf = new Pml();

        // Read Configuration
        if (ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" + packageName + "/Proglet.pml") != null) {
            this.conf.reset(Proglet.convertStreamToString(ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" + packageName + "/Proglet.pml")));
            if (!this.conf.getString("name").equals("")) {
                this.name = this.conf.getString("name");
            }
            if (!this.conf.getString("javaImport").equals("")) {
                this.depClass = new ArrayList<String>();
                this.depClass.addAll(Arrays.asList(this.conf.getString("javaImport").split(",")));
            }
            if (this.conf.getString("default").equals("true")) {
                ProgletManager.setDefaultProglet(this.packageName);
            }
            if (!this.conf.getString("javaCallBefore").equals("")) {
                this.observer[0] = this.conf.getString("javaCallBefore");
            }
            if (!this.conf.getString("javaCallAfter").equals("")) {
                this.observer[1] = this.conf.getString("javaCallAfter");
            }
        } else {
            throw new Exception("No configuration file for " + packageName);
        }

        // Install the panel
        if (Proglet.classExists("org.javascool.proglets." + this.packageName + ".Panel")) {
            try {
                System.err.println("Load panel for proglet " + packageName);
                panel = (JPanel) Class.forName("org.javascool.proglets." + this.packageName + ".Panel").newInstance();
                hasPanel = true;
            } catch (Exception ex) {
                throw ex;
            }
        } else {
            System.err.println("No panel for proglet " + packageName);
            this.panel = null;
        }

        // Install the proglet functions
        if (Proglet.classExists("org.javascool.proglets." + this.packageName + ".Functions")) {
            System.err.println("Load functions for proglet " + packageName);
            this.jvsFunctions = true;
        } else {
            System.err.println("No functions for proglet " + packageName);
            this.jvsFunctions = false;
        }

        // Install the help file
        if (ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" + packageName + "/Help.html") != null) {
            System.err.println("Load help for proglet " + packageName);
            this.help = new JVSHtmlDisplay();
            this.help.load(Utils.toUrl("org/javascool/proglets/" + packageName + "/Help.html").toString());
        } else {
            System.err.println("No help for proglet " + packageName);
            this.help = null;
        }

    }

    /** Convert a Stream to a String
     * To convert the InputStream to String we use the
     * Reader.read(char[] buffer) method. We iterate until the
     * Reader return -1 which means there's no more data to
     * read. We use the StringWriter class to produce the string.
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];

            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    private void setupJvsFunctions(File functionFile) {
        if (functionFile.exists()) {
            Jvs2Java.javaCompile(functionFile.getPath());
        }
    }

    public Boolean hasPanel(){
        return this.hasPanel;
    }
    
    public JPanel getHelpFileUrl() {
        return this.help;
    }

    public ArrayList<String> getJavaDependance() {
        if (this.depClass == null) {
            return new ArrayList<String>();
        }
        return this.depClass;
    }

    public String getJavaCodeToIncludeBefore() {
        return this.observer[0];
    }

    public String getJavaCodeToIncludeAfter() {
        return this.observer[1];
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public String getFullPackageName() {
        return this.fullPackageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getJvsFunctionsToInclude() {
        return this.jvsFunctions;
    }

    public static boolean classExists(String className) {
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
            Macros.echo(javaClass.getName().replaceAll("\\.class", ""));
            Class< ?> j_class;
            if (path.endsWith("org" + File.separator + "javascool" + File.separator + "proglets" + File.separator + javaClass.getParentFile().getName() + File.separator + "Panel.class")) {
                j_class = Utils.classLoader.loadClass("org.javascool.proglets." + javaClass.getParentFile().getName() + ".Panel");
            } else {
                j_class = new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.class", ""));
            }
            Object o = j_class.newInstance();
            if (!(o instanceof JPanel)) {
                throw new Exception("La class à charger n'est pas un JPanel");
            }
            return (JPanel) o;
        } catch (Throwable e) {
            throw new RuntimeException("Erreur: impossible de charger la class" + path + ", erreur : " + e.getMessage());
        }
    }

    private static Class<?> loadFromClass(String path) throws Throwable {
        try {
            File javaClass = new File(path);
            URL[] urls = new URL[]{new URL("file:" + javaClass.getParent() + File.separator)};
            return new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.class", ""));
        } catch (Throwable e) {
            throw new RuntimeException("Erreur: impossible de charger la class, erreur : " + e.getMessage());
        }
    }
}
