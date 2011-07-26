/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 * INRIA, Copyright (C) 2011.  All rights reserved.           *
 **************************************************************/
package org.javascool.proglet;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.javascool.tools.Utils;
import org.javascool.gui.JVSHtmlDisplay;
import org.javascool.pml.Pml;

/**
 * Class Proglet
 * @author Philippe Vienne
 */
public class Proglet {

    /** The proglet configuration */
    private Pml conf;
    /** Class to add before compilation */
    private ArrayList<String> depClass;
    /** The proglet's widget panel */
    private Applet panel = new Applet();
    /** Icon of the proglet */
    private ImageIcon icon;
    /** Say if we have a panel to show */
    private Boolean hasPanel = false;
    /** The help panel of the proglet */
    private JVSHtmlDisplay help;
    /** Say if the proglet has got functions 
     * True signifie that we will include Function class at compilation in static
     */
    private Boolean jvsFunctions = false;
    /** Observers code wich run before and after run */
    private String[] observer = {"", ""};
    /** Full proglet's package name */
    private String fullPackageName = "org.javascool.proglets.tools";
    /** Human name for the proglet */
    private String name = "";
    /** Proglet's package name */
    private String packageName = "ingredients";

    /** Setup a proglet by its name
     * The constructor load all proglets files and check if it's possible to run
     * the proglet
     * @param progletName The proglet package name in org.javascool.proglets.*
     * @throws Exception If the proglet is corrumpted
     */
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
        if (ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" + 
                packageName + "/proglet.pml") != null) {
            this.conf.reset(Proglet.convertStreamToString(
                    ClassLoader.getSystemResourceAsStream("org/javascool/progle"
                    + "ts/" + packageName + "/proglet.pml")));
            if (!this.conf.getString("name").isEmpty()) {
                this.name = this.conf.getString("name");
            }
            if (!this.conf.getString("javaImport").isEmpty()) {
                this.depClass = new ArrayList<String>();
                this.depClass.addAll(Arrays.asList(this.conf.getString("javaImp"
                        + "ort").split(",")));
            }
            if (this.conf.getString("default").equals("true")) {
                ProgletManager.setDefaultProglet(this.packageName);
            }
            if (!this.conf.getString("javaCallBefore").isEmpty()) {
                this.observer[0] = this.conf.getString("javaCallBefore");
            }
            if (!this.conf.getString("javaCallAfter").isEmpty()) {
                this.observer[1] = this.conf.getString("javaCallAfter");
            }
            if (!this.conf.getString("logo").isEmpty()) {
                String logo = this.conf.getString("logo");
                if (ClassLoader.getSystemResourceAsStream("org/javascool/progle"
                        + "ts/" + packageName + "/"+logo) != null) {
                    this.icon = Utils.getIcon("org/javascool/proglets/"
                            +this.packageName+"/"+logo);
                }
            }
            if(this.icon==null){
                this.icon = Utils.getIcon("org/javascool/doc-files/icons/script"
                        + "s.png");
            }
        } else {
            throw new Exception("No configuration file for " + packageName);
        }

        // Install the panel
        if (Proglet.classExists("org.javascool.proglets." + this.packageName 
                + ".Panel")) {
            try {
                System.err.println("Load panel for proglet " + packageName);
                panel = (Applet) Class.forName("org.javascool.proglets." 
                        + this.packageName + ".Panel").newInstance();
                hasPanel = true;
            } catch (Exception ex) {
                throw ex;
            }
        } else {
            System.err.println("No panel for proglet " + packageName);
            this.panel = null;
        }

        // Install the proglet functions
        if (Proglet.classExists("org.javascool.proglets." + this.packageName 
                + ".Functions")) {
            System.err.println("Load functions for proglet " + packageName);
            this.jvsFunctions = true;
        } else {
            System.err.println("No functions for proglet " + packageName);
            this.jvsFunctions = false;
        }

        // Install the help file
        if (ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" 
                + packageName + "/help.html") != null) {
            System.err.println("Load help for proglet " + packageName);
            this.help = new JVSHtmlDisplay();
            this.help.load(Utils.toUrl("org/javascool/proglets/" + packageName 
                    + "/help.html").toString());
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
    private static String convertStreamToString(InputStream is) 
            throws IOException {
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

    /** Say if we have a JPanel Widget
     * @return True if proglet has got a Widget
     */
    public Boolean hasPanel(){
        return this.hasPanel;
    }
    
    /** Get Help Widget
     * @return The widget or null
     */
    public JPanel getHelpWidget() {
        return this.help;
    }

    /** Get all java dependance of this proglet 
     * @return Dependance as an ArrayList
     */ 
    public ArrayList<String> getJavaDependance() {
        if (this.depClass == null) {
            return new ArrayList<String>();
        }
        return this.depClass;
    }

    /** Get java code to include before
     * Used by Jvs2Java
     * @return The code as a String
     */
    public String getJavaCodeToIncludeBefore() {
        return this.observer[0];
    }

    /** Get java code to include after
     * Used by Jvs2Java
     * @return The code as a String
     */
    public String getJavaCodeToIncludeAfter() {
        return this.observer[1];
    }

    /** Get the Widget Panel
     * @return The widget panel or null, the panel is an Applet
     */
    public Applet getPanel() {
        try {
            this.panel.init();
        } catch (Exception e) {
        }
        return this.panel;
    }

    /** Get the full package name for this proglet
     * Ex : "org.javascool.proglets.game"
     * @return The package String
     */
    public String getFullPackageName() {
        return this.fullPackageName;
    }

    /** Get the package name for this proglet
     * Ex : "game"
     * @return The package string
     */
    public String getPackageName() {
        return this.packageName;
    }

    /** Get the human name of this proglet */
    public String getName() {
        return this.name;
    }

    /** Say if we have to include Function class on compile */
    public Boolean getJvsFunctionsToInclude() {
        return this.jvsFunctions;
    }

    /** Say if a class exist in the current class path */
    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    /** Get the proglet's icon
     * @return the icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /** Set a new icon to the proglet
     * @param icon the icon to set
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
