/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.javascool.gui.JVSMainPanel;
import org.javascool.gui.JVSToolBar;

/**
 * Class Console
 * @author Philippe Vienne
 */
public class Console extends JPanel {
    private static boolean running;
    /** The current JVS Program */
    private static Runnable program;
    /** The current Thread */
    private static Thread runThread;
    /** The timer Thread */
    private static Thread timeThread;
    /** The new console */
    private static JTextArea outputPane = Console.getConsoleTextPane();
    /** ScrollPane for the console */
    private static JScrollPane scrolledOutputPane;
    /** ToolBar for the console */
    private static JVSConsoleToolBar toolbar;
    // Runnables for buttons
    private static Runnable stop = new Runnable() {

        @Override
        public void run() {
            Console.stopProgram();
            //Console.toolbar.programCompiled();
        }
    };
    private static Runnable run = new Runnable() {

        @Override
        public void run() {
            Console.startProgram();
        }
    };

    private static class JVSConsoleToolBar extends JVSToolBar {

        public JLabel timeRunning = new JLabel();

        public JVSConsoleToolBar() {
            super(true);
            init();
            this.setFloatable(false);
        }

        @Override
        public void reset() {
            setVisible(false);
            revalidate();
            this.removeAll();
            this.resetButtonsMaps();
            this.addTool("Effacer", "org/javascool/doc-files/icon16/erase.png", new Runnable() {

                @Override
                public void run() {
                    Console.clear();
                }
            });
            this.addSeparator();
            setVisible(true);
            revalidate();
        }

        private void init() {
            this.reset();
            this.add(new JLabel("Aucun programe à executer"));
        }

        public void programNotCompiled() {
            this.reset();
            this.add(new JLabel("Aucun programme à executer"));
            this.revalidate();
        }

        public void programCompiled() {
            this.reset();
            JVSMainPanel.getToolBar().activeStartButton();
            JVSMainPanel.getToolBar().activeExecTimer();
            this.revalidate();
        }

        public void programRunning() {
            //JVSMainPanel.getToolBar().desactiveStartButton();
            JVSMainPanel.getToolBar().activeStopButton();
            this.revalidate();
        }

        public void afterRunning() {
            JVSMainPanel.getToolBar().desactiveStopButton();
            JVSMainPanel.getToolBar().activeStartButton();
            this.revalidate();
        }

        public void updateTimeRunning(int sec) {
            JVSMainPanel.getToolBar().updateTimer(sec);
        }

        public void resetTimeRunning() {
            JVSMainPanel.getToolBar().updateTimer(0);
        }
    }

    /** Construct a new Panel with the console */
    public Console() {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        scrolledOutputPane = new JScrollPane(outputPane);
        this.add(Console.scrolledOutputPane, BorderLayout.CENTER);
        this.add(Console.getConsoleToolBar(), BorderLayout.NORTH);
        Console.redirectSystemStreams();
        this.setVisible(true);
    }

    /** Generate the ConsoleTextPane
     * @return The TextArea
     */
    private static JTextArea getConsoleTextPane() {
        JTextArea outPane = new JTextArea();
        outPane.setEditable(false);
        float[] bg = Color.RGBtoHSB(200, 200, 200, null);
        outPane.setBackground(Color.getHSBColor(bg[0], bg[1], bg[2]));
        return outPane;
    }

    /** Generate the ConsoleToolbar
     * @return The TextArea
     */
    private static JVSToolBar getConsoleToolBar() {
        toolbar = new JVSConsoleToolBar();
        return toolbar;
    }

    /** Clear the console */
    public static void clear() {
        outputPane.setText("");
    }

    /** Start the current program */
    public static void startProgram() {
        Console.stopProgram();
        if(JVSMainPanel.getCurrentProglet().hasPanel()){
            JVSMainPanel.getWidgetTabs().focusOnProgletPanel();
        }
        Console.running=true;
        Console.toolbar.updateTimeRunning(0);
        
        Console.clear();
        
        Console.timeThread=new Thread(new Runnable() {

            @Override
            public void run() {
                Console.run(true);
                for (int t = 0; Console.running; t++) {
                    Console.toolbar.updateTimeRunning(t);
                    Macros.sleep(1000);
                }
            }
        });
        Console.timeThread.start();
        Console.toolbar.programRunning();
    }

    /** Stop the current program */
    public static void stopProgram() {
        Console.running=false;
        Console.run(false);
        if (Console.timeThread != null) {
            try {
                Console.timeThread.interrupt();
            } catch (Exception e) {
                System.err.println("Erreur : "+e.getMessage());
            }
            Console.timeThread = null;
        }
        Console.timeThread=null;
        Console.runThread=null;
        Console.toolbar.afterRunning();
    }

    /** Runs/Stops the program.
     * @param start If true starts the proglet pupil's program, if defined. If false stops the proglet pupil's program.
     */
    private static void run(boolean start) {
        if (Console.runThread != null) {
            try {
                Console.runThread.interrupt();
            } catch (Exception e) {
                System.err.println("Erreur : "+e.getMessage());
            }
            Console.runThread = null;
        }
        if (start) {
            if (Console.program != null) {
                (Console.runThread = new Thread(new Runnable() {
                    
                    @Override
                    public void run() {

                        try {
                            Console.program.run();
                            Console.runThread = null;
                            Console.toolbar.afterRunning();
                        } catch (Throwable e) {
                            if (!"Programme arrêté !".equals(e.getMessage())) {
                                System.err.println(e.getMessage());
                                e.printStackTrace(System.err);
                            }
                            Console.runThread = null;
                            Console.toolbar.afterRunning();
                        }

                    }
                })).start();
            } else {
                System.err.println("Undefined runnable");
            }
        }
    }

    /** Update console function
     * @param text The text to add
     */
    private static void updateTextPane(final String text) {
        Console.outputPane.append(text);
    }

    /** Redirect the console outPut */
    private static void redirectSystemStreams() {
        OutputStream out = new OutputStream() {

            @Override
            public void write(final int b) throws IOException {
                Console.updateTextPane(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                Console.updateTextPane(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
    }

    /** Get current compiled program
     * @return the program
     */
    public static Runnable getProgram() {
        return program;
    }

    /** Say if a program is running */
    public static Boolean isRunning() {
        return running;
    }

    /** Set a new program
     * @param aProgram the program to set
     */
    public static void setProgram(Runnable aProgram) {
        program = aProgram;
        Console.toolbar.programCompiled();
    }

    /** Get the current Thread
     * @return the runThread
     */
    public static Thread getRunThread() {
        return runThread;
    }
}
