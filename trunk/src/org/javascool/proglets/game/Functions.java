/**
 * Functions.java
 * Part of Javascool proglet "game"
 * File creation : 20110704 at revision 48 by gmatheron (INRIA)
 * Last modification 20110704 by gmatheron (INRIA)
 * 
 * gmatheron : guillaume.matheron.06@gmail.com
 */



package org.javascool.proglets.game;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javascool.tools.Console;

/*
 * To use these event listeners, use this syntax : 
 * void toto(MouseState s) {
 *     ...
 * }
 * void main() {
 *     onClick("toto");
 * }
 */

/**
 * 
 * @author gmatheron
 */
public class Functions {
    /*
     * These arrays are designed to store the functions the user assigned a listener
     * A convenience type is used : CallbackFunction
     */
    private java.util.ArrayList<CallbackFunction> m_onClick;
    private java.util.ArrayList<CallbackFunction> m_onMouseEntered;
    private java.util.ArrayList<CallbackFunction> m_onMouseExited;
    private java.util.ArrayList<CallbackFunction> m_onMousePressed;
    private java.util.ArrayList<CallbackFunction> m_onMouseReleased;
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onClick(String s) {
        try {
            m_onClick.add(new CallbackFunction(Console.getProgram().getClass().getMethod(s, MouseState.class)));
        } catch (NoSuchMethodException ex) {
            //Maybe generate better errors ?
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseEntered(String s) {
        try {
            m_onMouseEntered.add(new CallbackFunction(Console.getProgram().getClass().getMethod(s, MouseState.class)));
        } catch (NoSuchMethodException ex) {
            //Maybe generate better errors ?
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseExited(String s) {
        try {
            m_onMouseExited.add(new CallbackFunction(Console.getProgram().getClass().getMethod(s, MouseState.class)));
        } catch (NoSuchMethodException ex) {
            //Maybe generate better errors ?
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMousePressed(String s) {
        try {
            m_onMousePressed.add(new CallbackFunction(Console.getProgram().getClass().getMethod(s, MouseState.class)));
        } catch (NoSuchMethodException ex) {
            //Maybe generate better errors ?
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseReleased(String s) {
        try {
            m_onMouseReleased.add(new CallbackFunction(Console.getProgram().getClass().getMethod(s, MouseState.class)));
        } catch (NoSuchMethodException ex) {
            //Maybe generate better errors ?
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //TODO
    /**
     * This method should be called at init (see bug report #005)
     * It creates the listeners
     */
    public void start() {
        /********* START ANONYMOUS CLASS ***************/
        new java.awt.event.MouseListener() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < m_onClick.size(); i++) {
                    m_onClick.get(i).call(new MouseState(evt));
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < m_onClick.size(); i++) {
                    m_onMouseEntered.get(i).call(new MouseState(evt));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < m_onClick.size(); i++) {
                    m_onMouseExited.get(i).call(new MouseState(evt));
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < m_onClick.size(); i++) {
                    m_onMousePressed.get(i).call(new MouseState(evt));
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < m_onClick.size(); i++) {
                    m_onMouseReleased.get(i).call(new MouseState(evt));
                }
            }
        };
        /********* END ANONYMOUS CLASS ***************/
    }

    /**
     * Convenience function to surround a java.lang.reflect.Method
     * This notably avoids having to create try/catch blocks each time a call
     * is performed (see Functions.start)
     * 
     * If this class is deleted, also delete the class State
     */
    class CallbackFunction {

        private java.lang.reflect.Method m_method;

        public CallbackFunction(java.lang.reflect.Method m) {
            m_method = m;
        }

        public void call(State s) {
            //Maybe generate better errors ?
            try {
                m_method.invoke(Console.getProgram(), s);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Superclass of all the states. This class is mainly used in CallbackFunction
     * so if CallbackFunction is deleted maybe State should be deleted too.
     * 
     * If an abstract (State) class can't be declared into a non-abstract class (Functions)
     * maybe we could switch it to an interface or leave it non-abstract (ugly)
     */
    abstract class State {
    }

    /**
     * A MouseState is passed as a parameter to the user-defined callback functions
     * so it must stay as intuitive as possible
     */
    class MouseState extends State {

        private java.awt.event.MouseEvent m_evt;

        MouseState(java.awt.event.MouseEvent evt) {
            m_evt = evt;
        }
        /* 
         * TODO add covenience methods such as x and y. Maybe leave them public
         * in case the user prefers to use s.x rather than x.getX() ?
         */
    }

    /**
     * TODO
     */
    class Sprite {

        private javax.swing.JPanel m_panel;

        public void call() {
        }
    }
}