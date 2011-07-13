/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2004.  All rights reserved. *
 *******************************************************************************/
package org.javascool.gui;

// Used to build the gui
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

// Used to manage links
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.net.URL;
import java.util.Vector;
import java.net.URLEncoder;
import java.net.URLDecoder;
import javax.swing.text.Document;

// Used to manage keystroke
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import org.javascool.Utils;

/** Defines a panel in which a HTML text is shown.
 *
 * @see <a href="HtmlDisplay.java.html">source code</a>
 * @serial exclude
 */
public class JVSHtmlDisplay extends JPanel {
    
    private static final long serialVersionUID = 1L;
    /** The Html Display pane. */
    private JEditorPane pane;
    /** The navigation buttons. */
    private JButton home, prev, next;

    public JVSHtmlDisplay(){
        // Builds the GUI
        setLayout(new BorderLayout());
        JToolBar bar = new JToolBar();
        bar.setOrientation(JToolBar.HORIZONTAL);
        bar.setBorderPainted(false);
        bar.setFloatable(false);
        try {
            bar.add(home = new JButton("Page initiale", Utils.getIcon(Class.forName("org.javascool.JvsMain").getResource("doc-files/icon16/refresh.png").toString())));
            bar.add(prev = new JButton("Page précédente", Utils.getIcon(Class.forName("org.javascool.JvsMain").getResource("doc-files/icon16/prev.png").toString())));
            bar.add(next = new JButton("Page suivante", Utils.getIcon(Class.forName("org.javascool.JvsMain").getResource("doc-files/icon16/next.png").toString())));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JVSHtmlDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        add(bar, BorderLayout.NORTH);
        pane = new JEditorPane();
        pane.setBackground(Color.WHITE);
        pane.setEditable(false);
        pane.setContentType("text/html");
        pane.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (e.getDescription().startsWith("jvs://")) {
                        /**
                         * Use this syntax for jvs:// links : 
                         * jvs://action1:param1:param2:param3,action2:param1:param2,action3
                         * Where actions can be 
                         *  - openjvs (1 param : the filename (must be in same proglet))
                         *  - openhtml (2 params : the html file to load (same as openjvs) in a new tab, and its name)
                         */
                        String[] actions=e.getDescription().substring(6).split(",");
                        String type="";     //The type of url (activity, proglet, webpage, etc.)
                        for (String action : actions) {
                            String[] params=action.split(":");
                            if (params[0].equals("openjvs")) {
                                if (params.length!=2) {
                                    JVSMainPanel.reportApplicationBug("Lien jvs mal formé");
                                }
                                else {
                                    JVSMainPanel.openFileFromJar(params[1]);
                                }
                            }
                            else if (params[0].equals("openhtml")) {
                                if (params.length!=3) {
                                    JVSMainPanel.reportApplicationBug("Lien jvs mal formé");
                                }
                                else {
                                    String file2="org/javascool/proglets/"+JVSMainPanel.getCurrentProglet().getPackageName()+"/"+params[1];
                                    JVSMainPanel.getWidgetTabs().openWebTab(file2, params[2]);
                                }
                            }
                        }
                    }
                    else {
                        load(e.getDescription());
                    }
                }
            }
        });
        JScrollPane spane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(spane, BorderLayout.CENTER);
        //<editor-fold defaultstate="collapsed" desc="Defines the backward/forward key-stroke and buttons">
        {
            AbstractAction doHome, doPrev, doNext;
            pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.CTRL_MASK), "home");
            pane.getActionMap().put("home", doHome = new AbstractAction("home") {
                
                private static final long serialVersionUID = 1L;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (urls.hasHome()) {
                        URL homeLink=urls.prev();
                        urls.empty();
                        urls.add(homeLink);
                        load(urls.home().toString(), false);
                    }
                    updateButtons();
                }
            });
            home.addActionListener(doHome);
            home.setEnabled(false);
            pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_MASK), "backward");
            pane.getActionMap().put("backward", doPrev = new AbstractAction("backward") {
                
                private static final long serialVersionUID = 1L;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (urls.hasPrev()) {;
                        load(urls.prev().toString(), false);
                    }
                    updateButtons();
                }
            });
            prev.addActionListener(doPrev);
            prev.setEnabled(false);
            pane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_MASK), "forward");
            pane.getActionMap().put("forward", doNext = new AbstractAction("forward") {
                
                private static final long serialVersionUID = 1L;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (urls.hasNext()) {
                        load(urls.next().toString(), false);
                    }
                    updateButtons();
                }
            });
            next.addActionListener(doNext);
            next.setEnabled(false);
        }
        //</editor-fold>
    }

    private void updateButtons() {
        home.setEnabled(urls.hasHome());
        prev.setEnabled(urls.hasPrev());
        next.setEnabled(urls.hasNext());
    }

    /** Sets the HTML text to show and return this.
     * - Called to show a HTML string.
     * @param text The HTML text location to show.
     * @return This, allowing to use the <tt>new HtmlDisplay().setText(..)</tt> construct.
     */
    public JVSHtmlDisplay reset(String text) {
        try {
            load(prefix + URLEncoder.encode(text, "UTF-8"), true);
        } catch (Exception e) {
        }
        return this;
    }
    static private final String prefix = "file://string/?value=:";

    /** Sets the HTML text to show and return this.
     * - Called when a link is clicked in the page.
     * @param location The HTML text location to show.
     * @return This, allowing to use the <tt>new HtmlDisplay().loads(..)</tt> construct.
     */
    public JVSHtmlDisplay load(String location) {
        return load(location, true);
    }

    private JVSHtmlDisplay load(String location, boolean stack) {
        try {
            URL url = urls.empty() ? Utils.toUrl(location) : new URL(urls.current(), location);
            if (stack) {
                urls.push(url);
            }
            updateButtons();
            if (urls.current().toString().startsWith(prefix)) {
                pane.setText(URLDecoder.decode(urls.current().toString().substring(prefix.length()), "UTF-8"));
            } else {
                pane.getDocument().putProperty(Document.StreamDescriptionProperty, null);
                pane.setPage(urls.current());
            }
        } catch (Exception e) {
            pane.setText("Upps : erreur de lien internet «" + e.toString() + "»");
        }
        return this;
    }

    /** Defines the URL backward/forward mechanism. */
    private class Stack extends ArrayList<URL> {

        private static final long serialVersionUID = 1L;
        /** Current index in the URL Vector. */
        private int current = -1;

        public Stack(){
            
        }
        
        /** Returns the current URL, if any. */
        public URL current() {
            return current < 0 ? null : get(current);
        }

        /** Checks if the statck is empty. */
        public boolean empty() {
            return size() == 0;
        }

        /** Pushs an URL in the stack. */
        public void push(URL url) {
            if(this.size()>0){
                this.current=this.size();
            }else{
                this.current=0;
            }
            add(url);
        }

        /** Checks if there is a home page. */
        public boolean hasHome() {
            return current >= 0;
        }

        /** Returns the home URL, if any. */
        public URL home() {
            if (hasHome()) {
                current = 0;
            }
            return current();
        }

        /** Checks if there is a previous page. */
        public boolean hasPrev() {
            return current > 0;
        }

        /** Returns the previous URL, if any. */
        public URL prev() {
            if (hasPrev()) {
                current--;
            }
            return current();
        }

        /** Checks if there is a next page. */
        public boolean hasNext() {
            return current < size() - 1;
        }

        /** Returns the next URL, if any. */
        public URL next() {
            if (hasNext()) {
                current++;
            }
            return current();
        }
    }
    /** The URL stack. */
    private Stack urls = new Stack();
}
