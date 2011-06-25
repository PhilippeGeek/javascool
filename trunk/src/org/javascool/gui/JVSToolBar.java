/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.javascool.JvsMain;
import org.javascool.Utils;

/** The JVS top tool bar
 * @author Philippe VIENNE
 */
public class JVSToolBar extends JToolBar{

    /** HashMap for button list.
     * The map associate a String to a JButton
     */
    private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    /** HashMap for action list.
     * The map associate a String to a Runnable
     */
    private HashMap<JButton, Runnable> actions = new HashMap<JButton, Runnable>();

    /** Create the JVSToolBar
     * 
     */
    public JVSToolBar() {
        super("Java's cool ToolBar");
        this.init();
    }

    /** Reset all the toolbar */
    public void reset() {
        setVisible(false);
        revalidate();
        this.removeAll();
        buttons.clear();
        actions.clear();
        this.init();
        setVisible(true);
        revalidate();
    }
    
    /** Initialize the toolBar with default button and setUps */
    private void init(){
        this.addTool("Nouveau fichier", "org/javascool/doc-files/icon16/new.png", new Runnable(){
            @Override
            public void run() {
                JVSMainPanel.newFile();
            }
        });
        this.addTool("Ouvrir un fichier", "org/javascool/doc-files/icon16/open.png", new Runnable(){
            @Override
            public void run() {
                JVSMainPanel.openFile();
            }
        });
        this.addTool("Sauver", "org/javascool/doc-files/icon16/save.png", new Runnable(){
            @Override
            public void run() {
                JVSMainPanel.saveFile();
            }
        });
        this.addTool("Fermer le fichier", "org/javascool/doc-files/icon16/remove.png", new Runnable(){
            @Override
            public void run() {
                JVSMainPanel.closeFile();
            }
        });
        this.addTool("Compiler", "org/javascool/doc-files/icon16/compile.png", new Runnable(){
            @Override
            public void run() {
                JVSFileEditorTabs.compileFile(JVSMainPanel.getEditorTabs().getCurrentFileId());
            }
        });
    }

    /** Adds a button to the toolbar.
     * @param label Button label.
     * @param icon Button icon. If null do not show icon.
     * @param action Button action.
     * @return The added button.
     */
    public final JButton addTool(String label, String icon, Runnable action) {
        String buttonId="MenuButton-"+UUID.randomUUID().toString();
        JButton button = icon == null ? new JButton(label) : new JButton(label, Utils.getIcon(icon));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actions.get((JButton) e.getSource()).run();
            }
        });
        add(button);
        buttons.put(buttonId, button);
        actions.put(button, action);
        revalidate();
        button.setName(buttonId);
        return button;
    }

    /** Removes a button from the tool bar. */
    public void delTool(String buttonId) {
        if (buttons.containsKey(buttonId)) {
            remove(buttons.get(buttonId));
            actions.remove(buttons.get(buttonId));
            buttons.remove(buttonId);
            setVisible(false);
            revalidate();
            setVisible(true);
            revalidate();
        }
    }
    
    /** Get a button id
     * Search the buttonId by the text label
     * @param label The text label on the button
     * @return The button ID
     */
    public String getButtonId(String label) {
        Set<String> id=this.buttons.keySet();
        Collection<JButton> butToCheck=this.buttons.values();
        int i=0;
        String findId="";
        while(i<(id.size()-1)){
            JButton test=(JButton) butToCheck.toArray()[i];
            if(test.getText().equals(label)){
                findId=test.getName();
                return findId;
            }
            i++;
        }
        return findId;
    }

    /** Get the Main Panel to have main functions */
    private static JVSMainPanel getJvsMainPanel() {
        return JvsMain.getJvsMainPanel();
    }
}
