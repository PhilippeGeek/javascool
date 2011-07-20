/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.proglet;

import org.javascool.proglet.Proglet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.javascool.JVSMain;
import org.javascool.tools.Utils;

/** The proglets manager class
 * This class list and store all opened proglets
 * @author Philippe Vienne
 */
public class ProgletManager {

    private static String defaultProglet;
    private static HashMap<String, Proglet> proglets = new HashMap<String, Proglet>();

    /** Get the default proglet
     * @return the defaultProglet
     */
    public static String getDefaultProglet() {
        return defaultProglet;
    }

    /** Set a new default proglet
     * @param aDefaultProglet the defaultProglet to set
     */
    public static void setDefaultProglet(String aDefaultProglet) {
        defaultProglet = aDefaultProglet;
    }

    /** Start and construct the proglet manager */
    public ProgletManager() {
        try {
            // We are NOT in a jar
            String proglet;
            File progletDir = new File(Thread.currentThread().getContextClassLoader().getResource("org/javascool/proglets").toURI());
            for (File dir : progletDir.listFiles()) {
                if (dir.isDirectory()) {
                    proglet = dir.getName();
                    try {
                        ProgletManager.proglets.put(proglet, new Proglet(proglet));
                    } catch (Exception ex) {
                        System.err.println("Auto-destruct proglet " + proglet + ", error during load. Exception : " + ex.getMessage());
                        if (ex.getMessage().startsWith("No configuration file")) {
                            ProgletManager.proglets.remove(proglet);
                        } else {
                            ProgletManager.proglets.remove(proglet);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            // We are IN a JAR
            if (ex.getMessage().equals("URI is not hierarchical")) {
                try {
                    for (String proglet : ProgletManager.listProgletInTheJar(Utils.classLoader.getResource("org/javascool/proglets").toString().replaceAll("file:", "").replaceAll("jar:", "").replaceAll("%20", " "))) {
                        proglet = proglet.split("/")[proglet.split("/").length - 1];
                        try {
                            ProgletManager.proglets.put(proglet, new Proglet(proglet));
                        } catch (Exception exep) {
                            System.err.println("Auto-destruct proglet " + proglet + ", error during load. Exception : " + exep.getMessage());
                            if (ex.getMessage().startsWith("No configuration file")) {
                                ProgletManager.proglets.remove(proglet);
                            } else {
                                ProgletManager.proglets.remove(proglet);
                            }
                        }
                    }
                } catch (Exception ex1) {
                }
            } else {
            }
        }
        // TODO : Remake the sketchbook
    }

    /** Get a proglet
     * @param name The name of the proglet
     * @return The proglet Object
     */
    public Proglet getProglet(String name) {
        return ProgletManager.proglets.get(name);
    }
    
    /** Get all proglets
     * @return Proglets as a collection
     */
    public Collection<Proglet> getProglets() {
        return ProgletManager.proglets.values();
    }

    /** Set up a new sketchbook */
    public void installNewSketchbook() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Dossiers";
            }
        });
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        switch (fc.showDialog(JVSMain.getJvsMainFrame(), "Installer le "
                + "scketchbook")) {
            case JFileChooser.APPROVE_OPTION:
                try {
                    this.installProgletDir(fc.getSelectedFile());
                } catch (Exception ex) {
                    Logger.getLogger(ProgletManager.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Help");
        }
    }

    /** Install a new Sketchbook directory
     * @param directory The directory to install
     * @throws Exception If this directory cannot be set as Scketchbook
     */
    private void installProgletDir(File directory) throws Exception {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception(directory + " is not a proglet folder");
        }
        JVSMain.getJvsConf().set("sketchbook", directory.getPath());
    }

    /** List all proglets in the current jar */
    private static List<String> listProgletInTheJar(String path) throws IOException {
        List<String> classFiles = new ArrayList<String>();
        final String[] parts = path.split("!");
        if (parts.length == 2) {
            String jarFilename = parts[0];
            String relativePath = parts[1].replace(File.separatorChar, '/').substring(1);
            JarFile jarFile = new JarFile(jarFilename);
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String entryName = entry.getName();
                if (entryName.startsWith(relativePath) && entry.isDirectory() && !entryName.equals(relativePath + "/")) {
                    classFiles.add(entryName);
                }
            }
        }
        return classFiles;
    }
}
