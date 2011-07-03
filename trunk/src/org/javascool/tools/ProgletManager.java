/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
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

    private static HashMap<String, Proglet> proglets = new HashMap<String, Proglet>();

    public ProgletManager() {
        ProgletManager.proglets.put("test", new Proglet("test"));
        if(!JvsMain.getJvsConf().get("sketchbook").equals("")){
            try {
                this.installProgletDir(new File(JvsMain.getJvsConf().get("sketchbook")));
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
            if (ProgletManager.proglets.containsKey(dir)) {
                Dialog.error("Erreur lors du chargement", "Le proglet " + dir + " est déjà chargé");
            } else {
                try {
                    ProgletManager.proglets.put(dir, new Proglet(new File(directory.getPath() + File.separator + dir)));
                } catch (Exception e) {
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
                    if(progletsForHumans.get(key).equals(selectedValue)){
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
}
