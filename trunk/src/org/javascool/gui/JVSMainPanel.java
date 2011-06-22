/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.javascool.JVSFile;

/**
 *
 * @author philien
 */
public final class JVSMainPanel extends JPanel {

    private JVSToolBar toolbar = new JVSToolBar();
    private JVSSplitPane mainPane = new JVSSplitPane(new JVSFileEditorTabs(), new JVSFileEditorTabs());
    private HashMap<String, Boolean> haveToSave = new HashMap<String, Boolean>();

    public JVSMainPanel() {
        this.setVisible(true);
        this.setupViewLayout();
        this.setupToolBar();
        this.setupMainPanel();
        this.addBindings();
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
        this.add(mainPane, BorderLayout.CENTER);
    }

    //Add a couple of emacs key bindings for navigation.
    protected void addBindings() {

        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK);
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK);
        KeyStroke ctrlQ = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK);
        KeyStroke ctrlI = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
        KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK);

        // Set save handler for ctrl+S
        this.getInputMap().put(ctrlS,
                "save");
        this.getActionMap().put("save",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });
        
        // Set openFile handler for ctrl+O
        this.getInputMap().put(ctrlO,
                "open");
        this.getActionMap().put("open",
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openFile();
                    }
                });
    }

    /** Get the toolbar
     * @return The toolbar
     */
    public JVSToolBar getToolBar() {
        return toolbar;
    }

    public void newFile() {
        String fileId = this.getEditorTabs().openNewFile();
        this.haveToSave.put(fileId, true);
    }

    public void openFile() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            String fileId = this.getEditorTabs().open(path);
            this.haveToSave.put(fileId, false);
        } else {
        }
    }

    public void saveFile() {
        if(this.getEditorTabs().saveCurrentFile()){
            this.haveToSave.put(this.getEditorTabs().getCurrentFileId(), false);
        }
    }

    public void mustSave(String fileId) {
        this.haveToSave.put(fileId, true);
    }

    public Boolean close() {
        String id = "";
        Boolean[] can_close = new Boolean[this.haveToSave.keySet().toArray().length];
        int i = 0;
        for (Object fileId : this.haveToSave.keySet().toArray()) {
            id = (String) fileId;
            if (this.haveToSave.get(id)) {
                switch (this.saveFileIdBeforeClose(id)) {
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
                this.getEditorTabs().closeFile(id);
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

    private int saveFileIdBeforeClose(String fileId) {
        final JFileChooser fc = new JFileChooser();
        JVSFile file = this.getEditorTabs().getFile(fileId);
        int result = JOptionPane.showConfirmDialog(this.getParent(),
                "Voulez vous enregistrer " + file.getName() + " avant de quitter ?");
        if (result == JOptionPane.YES_OPTION) {
            if (this.getEditorTabs().saveFile(fileId)) {
                this.haveToSave.put(fileId, false);
                return 1;
            } else {
                return 0;
            }
        } else if (result == JOptionPane.NO_OPTION) {
            return 1;
        } else {
            this.haveToSave.put(fileId, true);
            return -1;
        }
    }

    private JVSFileEditorTabs getEditorTabs() {
        return ((JVSFileEditorTabs) this.mainPane.getLeftComponent());
    }
}
