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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import org.javascool.JvsMain;
import org.javascool.tools.Utils;
import org.javascool.widgets.Console;

/** The JVS top tool bar
 * @author Philippe VIENNE
 */
public class JVSToolBar extends JToolBar {

    /** HashMap for button list.
     * The map associate a String to a JButton
     */
    private HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    /** HashMap for action list.
     * The map associate a String to a Runnable
     */
    private HashMap<JButton, Runnable> actions = new HashMap<JButton, Runnable>();
    
    private JButton startButton;
    private JButton stopButton;
    private JLabel execTime;

    /** Create the JVSToolBar
     * 
     */
    public JVSToolBar() {
        super("Java's cool ToolBar");
        init();
    }

    protected JVSToolBar(Boolean escapeInit) {
        super("Java's cool ToolBar");
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
    private void init() {
        this.addTool("Nouvel activité", "org/javascool/doc-files/icon16/new.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.closeProglet();
            }
        });
        this.addTool("Nouveau fichier", "org/javascool/doc-files/icon16/new.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.newFile();
            }
        });
        this.addTool("Ouvrir un fichier", "org/javascool/doc-files/icon16/open.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.openFile();
            }
        });
        this.addTool("Sauver", "org/javascool/doc-files/icon16/save.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.saveFile();
            }
        });
        this.addTool("Fermer le fichier", "org/javascool/doc-files/icon16/remove.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.closeFile();
            }
        });

        this.addTool("Compiler", "org/javascool/doc-files/icon16/compile.png", new Runnable() {

            @Override
            public void run() {
                JVSMainPanel.compileFile();
            }
        });
        
        this.startButton=this.addTool("Executer", "org/javascool/doc-files/icon16/play.png", new Runnable(){

            @Override
            public void run() {
                Console.stopProgram();
                Console.startProgram();
            }
        
        });
        this.startButton.setVisible(false);
        
        this.stopButton=this.addTool("Arrêter", "org/javascool/doc-files/icon16/stop.png", new Runnable(){

            @Override
            public void run() {
                Console.stopProgram();
            }
        
        });
        this.stopButton.setVisible(false);
        
        this.execTime=new JLabel("  Temps d'execution : 0 min 0 sec");
        this.add(this.execTime);
        this.execTime.setVisible(false);
        
        this.add(Box.createHorizontalGlue());
        this.generateProgletMenu();
    }

    /** Adds a button to the toolbar.
     * @param label Button label.
     * @param icon Button icon. If null do not show icon.
     * @param action Button action.
     * @return The added button.
     */
    public final JButton addTool(String label, String icon, Runnable action) {
        String buttonId = "MenuButton-" + UUID.randomUUID().toString();
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
    
    /** Adds a button to the toolbar.
     * @param label Button label.
     * @param icon Button icon. If null do not show icon.
     * @param action Button action.
     * @return The added button.
     */
    private JButton addTool(String label, String icon, ActionListener action) {
        String buttonId = "MenuButton-" + UUID.randomUUID().toString();
        JButton button = icon == null ? new JButton(label) : new JButton(label, Utils.getIcon(icon));
        button.addActionListener(action);
        add(button);
        buttons.put(buttonId, button);
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
        Set<String> id = this.buttons.keySet();
        Collection<JButton> butToCheck = this.buttons.values();
        int i = 0;
        String findId = "";
        while (i < (id.size() - 1)) {
            JButton test = (JButton) butToCheck.toArray()[i];
            if (test.getText().equals(label)) {
                findId = test.getName();
                return findId;
            }
            i++;
        }
        return findId;
    }

    protected void resetButtonsMaps() {
        buttons.clear();
        actions.clear();
    }

    private void generateProgletMenu() {
        
        final JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem menuitem=new JMenuItem("Installer un sketchbook");
        menuitem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JVSMainPanel.getProgletManager().installNewSketchbook();
            }
            
        });
        jPopupMenu.add(menuitem);
        menuitem=new JMenuItem("Désinstaller le sketchbook");
        menuitem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JvsMain.getJvsConf().set("sketchbook", "");
            }
            
        });
        jPopupMenu.add(menuitem);
        jPopupMenu.addSeparator();
        menuitem=new JMenuItem("Changer de proglet");
        menuitem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JVSMainPanel.closeProglet();
            }
            
        });
        jPopupMenu.add(menuitem);
        this.addTool("Proglets", "", new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                jPopupMenu.show(((JButton)e.getSource()),
                       0, ((JButton)e.getSource()).getHeight());
            }
        });
        return;
    }
    
    public void activeStartButton(){
        this.startButton.setVisible(true);
        this.revalidate();
    }
    
    public void desactiveStartButton(){
        this.startButton.setVisible(false);
        this.revalidate();
    }
    
    public void activeStopButton(){
        this.stopButton.setVisible(true);
        this.revalidate();
    }
    
    public void desactiveStopButton(){
        this.stopButton.setVisible(false);
        this.revalidate();
    }
    
    public void activeExecTimer(){
        this.execTime.setVisible(true);
        this.revalidate();
    }
    
    public void desactiveExecTimer(){
        this.execTime.setVisible(false);
        this.revalidate();
    }
    
    public void updateTimer(int sec){
        this.execTime.setText("  Temps d'éxecution : "+sec/60+" min "+sec%60+" sec");
    }

    /** Get the Main Panel to have main functions */
    private static JVSMainPanel getJvsMainPanel() {
        return JvsMain.getJvsMainPanel();
    }
}
