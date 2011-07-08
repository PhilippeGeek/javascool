/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import org.javascool.JvsMain;
import org.javascool.Utils;
import org.javascool.gui.JVSMainPanel;
import org.javascool.gui.JVSMainPanel.Dialog;
import org.javascool.pml.Pml;

/** The proglets manager class
 * This class list and store all opened proglets
 * @author Philippe Vienne
 */
public class ProgletManager {

    private static String defaultProglet;
    private static HashMap<String, Proglet> proglets = new HashMap<String, Proglet>();

    /**
     * @return the defaultProglet
     */
    public static String getDefaultProglet() {
        return defaultProglet;
    }

    /**
     * @param aDefaultProglet the defaultProglet to set
     */
    public static void setDefaultProglet(String aDefaultProglet) {
        defaultProglet = aDefaultProglet;
    }

    public ProgletManager() {
        try {
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
            if (ex.getMessage().equals("URI is not hierarchical")) {
                try {
                    System.err.println("We are in a jar");
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
                    Logger.getLogger(ProgletManager.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } else {
                Logger.getLogger(ProgletManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //ProgletManager.proglets.put("game", new Proglet("game"));
        if (!JvsMain.getJvsConf().get("sketchbook").equals("")) {
            try {
                //System.err.println("Load sketchbook ...");
                //this.installProgletDir(new File(JvsMain.getJvsConf().get("sketchbook")));
            } catch (Exception ex) {
                Logger.getLogger(ProgletManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Proglet getProglet(String name) {
        return ProgletManager.proglets.get(name);
    }

    public void installNewProglet() {
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
        switch (fc.showDialog(JvsMain.getJvsMainFrame(), "Installer le scketchbook")) {
            case JFileChooser.APPROVE_OPTION:
                try {
                    this.installProgletDir(fc.getSelectedFile());
                } catch (Exception ex) {
                    Logger.getLogger(ProgletManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Help");
        }
    }

    private void installProgletDir(File directory) throws Exception {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new Exception(directory + " is not a proglet folder");
        }
        JvsMain.getJvsConf().set("sketchbook", directory.getPath());
        for (String dir : directory.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (dir.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }
        })) {
            System.err.println("Chargement de " + directory + File.separator + dir);
            if (ProgletManager.proglets.containsKey(dir)) {
                Dialog.error("Erreur lors du chargement", "Le proglet " + dir + " est déjà chargé");
            } else {
                try {
                    ProgletManager.proglets.put(dir, new Proglet(new File(directory.getPath() + File.separator + dir)));
                } catch (Exception e) {
                    System.err.println("Erreur " + e.getMessage() + " lors du chargement de " + dir);
                }
            }
        }
    }

    public void changeProglet() {
        // Create the frame
        String title = "Changer de proglet";
        final JFrame frame = new JFrame(title);
        frame.setIconImage(Utils.getIcon("org/javascool/logo.png").getImage());
        JButton valid = new JButton("Changer");
        final HashMap<String, String> progletsForHumans = new HashMap<String, String>();
        for (String key : ProgletManager.proglets.keySet()) {
            progletsForHumans.put(key, ProgletManager.proglets.get(key).getName());
        }
        final JList list = new JList(progletsForHumans.values().toArray());
        JScrollPane scrollingList = new JScrollPane(list);
        frame.getContentPane().add(scrollingList, BorderLayout.CENTER);
        frame.getContentPane().add(valid, BorderLayout.SOUTH);
        valid.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedValue = list.getSelectedValue();
                for (String key : progletsForHumans.keySet()) {
                    if (progletsForHumans.get(key).equals(selectedValue)) {
                        JVSMainPanel.loadProglet(key);
                    }
                }
                frame.dispose();
            }
        });
        int width = 300;
        int height = 300;
        int x = (JvsMain.getJvsMainFrame().getX()) + (JvsMain.getJvsMainFrame().getWidth() - width) / 2;
        int y = (JvsMain.getJvsMainFrame().getY()) + (JvsMain.getJvsMainFrame().getHeight() - height) / 2;
        frame.setBounds(x, y, width, height);
        frame.setVisible(true);
    }

    private static List<String> listProgletInTheJar(String path) throws IOException {
        Macros.echo("Searching in " + path);
        List<String> classFiles = new ArrayList<String>();
        final String[] parts = path.split("!");
        if (parts.length == 2) {
            try {
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
            } catch (Exception ex) {
                Macros.echo("Erreur : " + ex.getMessage());
            }
        }
        return classFiles;
    }
}
