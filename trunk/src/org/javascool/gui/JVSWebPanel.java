/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.javascool.JvsMain;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;

/** Make a web navigation Panel
 * Open url, website in a JPanel
 * @author Philippe VIENNE
 */
public class JVSWebPanel extends JPanel{
    
    private org.lobobrowser.html.gui.HtmlPanel webPanel;
    private JVSToolBar toolbar;
    private SimpleHtmlRendererContext render;
    
    public JVSWebPanel(){
        this.setupPanel();
    }
    
    private void setupPanel(){
        this.setLayout(new BorderLayout());
        this.setupWebPanel();
        this.setVisible(true);
    }
    
    private void setupWebPanel(){
        webPanel=new HtmlPanel();
        render=new SimpleHtmlRendererContext(webPanel,new SimpleUserAgentContext());
        System.err.println("file:"+JvsMain.class.getResource("package.html").getPath());
        try {
            webPanel.setHtml(org.javascool.Utils.loadString(JvsMain.class.getResource("packag.html").getPath()), "file:"+JvsMain.class.getResource("packag.html").getPath(), render );
            //render.navigate("http://www.google.fr");
            
        } catch (Exception ex) {
            Logger.getLogger(JVSWebPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.add(webPanel,BorderLayout.CENTER);
        this.setupToolbar();
    }
    
    private void setupToolbar(){
        toolbar=new JVSToolBar(true);
        toolbar.addTool("Précédent", "", new Runnable(){

            @Override
            public void run() {
            }
        });
        toolbar.addTool("Suivant", "", new Runnable(){

            @Override
            public void run() {
            }
        });
        this.add(toolbar,BorderLayout.NORTH);
    }
    
}
