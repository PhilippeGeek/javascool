/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.javascool.JVSFile;

/**
 *
 * @author philien
 */
public class JVSMainPanel extends JPanel {

    private JVSToolBar toolbar = new JVSToolBar();
    private JVSFileEditorTabs editorTabs = new JVSFileEditorTabs();
    private HashMap<String, Boolean> haveToSave = new HashMap<String, Boolean>();

    public JVSMainPanel() {
        this.setVisible(true);
        this.setupViewLayout();
        this.setupToolBar();
        this.setupMainPanel();
    }

    /** Setup the Border Layout for the JPanel */
    private void setupViewLayout() {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
    }

    /** Setup the ToolBar */
    private void setupToolBar() {
        // Add Buttons here
        this.add(toolbar, BorderLayout.NORTH);
    }

    /** Setup Main Panel */
    private void setupMainPanel() {
        //tabs.add("Test", JvsMain.logo16, new JPanel());
        this.newFile();
        this.add(editorTabs, BorderLayout.CENTER);
    }

    /** Get the toolbar
     * @return The toolbar
     */
    public JVSToolBar getToolBar() {
        return toolbar;
    }

    public void newFile() {
        String fileId = this.editorTabs.openNewFile();
        this.haveToSave.put(fileId, true);
    }

    public void openFile() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            String fileId = this.editorTabs.open(path);
            this.haveToSave.put(fileId, true);
        } else {
        }
    }

    public Boolean close() {
        String id = "";
        Boolean[] can_close = new Boolean[this.haveToSave.keySet().toArray().length];
        int i = 0;
        for (Object fileId : this.haveToSave.keySet().toArray()) {
            id = (String) fileId;
            if (this.haveToSave.get(id)) {
                switch (this.saveFileId(id)) {
                    case 1:
                        can_close[i] = true;
                        break;
                    case 0:
                        can_close[i] = false;
                        break;
                    case -1:
                        return false;
                }
            } else {
                can_close[i] = true;
            }
            if (can_close[i]) {
                this.editorTabs.closeFile(id);
            }
            i++;
        }
        for (Boolean can_close_r : can_close) {
            if (can_close_r == false) {
                return false;
            }
        }
        return true;
    }

    private int saveFileId(String fileId) {
        final JFileChooser fc = new JFileChooser();
        JVSFile file = this.editorTabs.getFile(fileId);
        int result = JOptionPane.showConfirmDialog(this.getParent(),
                "Voulez vous enregistrer " + file.getName() + " avant de quitter ?");
        if (result == JOptionPane.YES_OPTION) {
            if (file.isTmp()) {
                int returnVal = fc.showOpenDialog(this.getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".jvs")) {
                        path = path + ".jvs";
                    }
                    file.setPath(path);
                    file.setName(fc.getSelectedFile().getName());
                    if (file.save()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else if (file.save()) {
                    return 1;
            } else {
                return 0;
            }
        } else if (result == JOptionPane.NO_OPTION) {
            return 1;
        } else {
            return -1;
        }
    }
}
