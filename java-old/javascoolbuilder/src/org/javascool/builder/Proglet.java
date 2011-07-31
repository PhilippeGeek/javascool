/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 * INRIA, Copyright (C) 2011.  All rights reserved.           *
 **************************************************************/
package org.javascool.builder;

import java.io.File;
import javax.swing.ImageIcon;
import org.javascool.tools.Utils;
import org.javascool.pml.Pml;

/** Define a proglet for compilation
 * This class check if a proglet can be compiled and with what. This will load
 * configuration file and check all files into the Proglet Folder
 */
public final class Proglet {

    /** The proglet configuration */
    private Pml conf;
    /** The proglet path */
    private String path;
    /** Icon of the proglet */
    private ImageIcon icon;
    /** Say if we have a panel to show */
    private Boolean hasPanel = false;
    /** Say if the proglet has got functions */
    private Boolean jvsFunctions = false;
    /** Human name for the proglet */
    private String name = "";
    /** Proglet's package name */
    private String packageName = "ingredients";
    /** Say if the proglet has got an help */
    private Boolean hasHelp;
    /** The proglet directory */
    private File progletDir;

    /** Setup a proglet from a folder to parse it before compile
     * The constructor load all proglets files and check if it's possible to run
     * the proglet
     * @param progletFolder The proglet folder
     * @throws Exception If the proglet is corrumpted
     */
    public Proglet(File progletFolder) throws Exception {
        this.setProgletDir(progletFolder);
        this.packageName = progletFolder.getName();
        this.conf = new Pml();
        this.path = progletFolder.getPath();

        if (new File(path + File.separator + "proglet.pml").exists()) {
            this.conf.reset(Utils.loadString(new File(path + File.separator + "proglet.pml").getPath()));
            if (!this.conf.getString("name").equals("")) {
                this.name = this.conf.getString("name");
            }
            if (!this.conf.getString("logo").equals("")) {
                String logo = this.conf.getString("logo");
                if (new File(path + File.separator + logo).exists()) {
                    this.icon = Utils.getIcon(path + File.separator + logo);
                }
            }
            if (this.icon == null) {
                this.icon = null;
            }
        } else {
            throw new Exception("No configuration file for " + packageName);
        }

        if (new File(path + File.separator + "Panel.java").exists()) {
            hasPanel = true;
        } else {
            hasPanel = false;
        }
        
        if (new File(path + File.separator + "help.xml").exists()) {
            hasHelp = true;
        } else {
            hasHelp = false;
        }

        // Install the proglet functions
        if (new File(path + File.separator + "Functions.java").exists()) {
            this.jvsFunctions = true;
        } else {
            this.jvsFunctions = false;
        }
    }

    /** Say if we have a JPanel Widget
     * @return True if proglet has got a Widget
     */
    public Boolean hasPanel() {
        return this.hasPanel;
    }
    
    /** Say if we have an help
     * @return True if proglet has got an help
     */
    public Boolean hasHelp() {
        return this.hasHelp;
    }

    /** Get the package name for this proglet
     * Ex : "game"
     * @return The package string
     */
    public String getPackageName() {
        return this.packageName;
    }

    /** Get the human name of this proglet
     * @return The proglet name
     */
    public String getName() {
        return this.name;
    }
    
    /** Get PML conf of Proglet
     * @return the Pml Object
     * @see Pml
     */
    public Pml getConf(){
        return this.conf;
    }

    /** Say if we have to include Function class on compile
     * @return true if we have a class Functions
     */
    public Boolean haveJvsFunctions() {
        return this.jvsFunctions;
    }

    /** Get the proglet's icon
     * @return The proglet icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * @return the progletDir
     */
    public File getProgletDir() {
        return progletDir;
    }

    /**
     * @param progletDir the progletDir to set
     */
    public void setProgletDir(File progletDir) {
        this.progletDir = progletDir;
    }
}
