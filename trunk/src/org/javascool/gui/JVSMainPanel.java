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

    private static JVSToolBar toolbar = new JVSToolBar();
    private static JVSSplitPane mainPane = new JVSSplitPane(new JVSFileEditorTabs(), new JVSFileEditorTabs());
    private static HashMap<String, Boolean> haveToSave = new HashMap<String, Boolean>();

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
        JVSMainPanel.newFile();
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
    public static JVSToolBar getToolBar() {
        return toolbar;
    }

    public static void newFile() {
        String fileId = JVSMainPanel.getEditorTabs().openNewFile();
        JVSMainPanel.haveToSave.put(fileId, false);
    }

    public static void openFile() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(JVSMainPanel.getEditorTabs().getMainPanel().getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            String fileId = JVSMainPanel.getEditorTabs().open(path);
            JVSMainPanel.haveToSave.put(fileId, false);
        } else {
        }
    }

    public static void saveFile() {
        if (JVSMainPanel.getEditorTabs().saveCurrentFile()) {
            JVSMainPanel.haveToSave.put(JVSMainPanel.getEditorTabs().getCurrentFileId(), false);
        }
    }

    public static void closeFile() {
        if (JVSMainPanel.haveToSave.get(getEditorTabs().getCurrentFileId())) {
            if (saveFileIdBeforeClose(getEditorTabs().getCurrentFileId()) == 1) {
                getEditorTabs().closeFile(getEditorTabs().getCurrentFileId());
            }
        } else {
            getEditorTabs().closeFile(getEditorTabs().getCurrentFileId());
        }
        if (JVSMainPanel.getEditorTabs().tabs.entrySet().toArray().length==0) {
            JVSMainPanel.newFile();
        }
    }

    public static void mustSave(String fileId) {
        JVSMainPanel.haveToSave.put(fileId, true);
    }

    public static void haveNotToSave(String fileId) {
        JVSMainPanel.haveToSave.put(fileId, false);
    }

    public static Boolean close() {
        String id = "";
        Boolean[] can_close = new Boolean[JVSMainPanel.haveToSave.keySet().toArray().length];
        int i = 0;
        int j = 0;
        for (Object fileId : JVSMainPanel.haveToSave.keySet().toArray()) {
            id = (String) fileId;
            if (JVSMainPanel.haveToSave.get(id)) {
                switch (JVSMainPanel.saveFileIdBeforeClose(id)) {
                    case 1:
                        can_close[i] = true;
                        break;
                    case 0:
                        can_close[i] = false;
                        break;
                    case -1:
                        return false;
                }
                j++;
            } else {
                can_close[i] = true;
            }
            if (can_close[i]) {
                JVSMainPanel.getEditorTabs().closeFile(id);
            }
            i++;
        }
        for (Boolean can_close_r : can_close) {
            if (can_close_r == false) {

                return false;
            }
        }
        if (j == 0) {
            final int n = JOptionPane.showConfirmDialog(
                    getEditorTabs().getMainPanel().getParent(),
                    "Voulez vous vraiment quitter Java's cool ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                return true;
            } else {
                JVSMainPanel.newFile();
                return false;
            }
        }
        return true;
    }

    private static int saveFileIdBeforeClose(String fileId) {
        JVSFile file = JVSMainPanel.getEditorTabs().getFile(fileId);
        int result = JOptionPane.showConfirmDialog(JVSMainPanel.getEditorTabs().getMainPanel(),
                "Voulez vous enregistrer " + file.getName() + " avant de quitter ?");
        if (result == JOptionPane.YES_OPTION) {
            if (JVSMainPanel.getEditorTabs().saveFile(fileId)) {
                JVSMainPanel.haveToSave.put(fileId, false);
                return 1;
            } else {
                return 0;
            }
        } else if (result == JOptionPane.NO_OPTION) {
            return 1;
        } else {
            JVSMainPanel.haveToSave.put(fileId, true);
            return -1;
        }
    }

    public static JVSFileEditorTabs getEditorTabs() {
        return ((JVSFileEditorTabs) JVSMainPanel.mainPane.getLeftComponent());
    }
}
