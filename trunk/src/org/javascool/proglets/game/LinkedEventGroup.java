/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.javascool.tools.Macros;

/**
 *
 * @author gmatheron
 */
abstract public class LinkedEventGroup implements EventCatcher {
    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "CollectionWithoutInitialCapacity"})
    public LinkedEventGroup() {
    }
    
    private static final Logger LOG = Logger.getLogger(LinkedEventGroup.class.getName());
    
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onClick(String s) {
        Functions.getSingleton().m_onClick.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseEntered(String s) {
        Functions.getSingleton().m_onMouseEntered.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseExited(String s) {
        Functions.getSingleton().m_onMouseExited.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMousePressed(String s) {
        Functions.getSingleton().m_onMousePressed.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseReleased(String s) {
        Functions.getSingleton().m_onMouseReleased.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseDown(String s) {
        Functions.getSingleton().m_onMouseDown.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseUp(String s) {
        Functions.getSingleton().m_onMouseUp.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseMoved(String s) {
        Functions.getSingleton().m_onMouseMoved.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseDragged(String s) {
        Functions.getSingleton().m_onMouseDragged.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseWheelUp(String s) {
        Functions.getSingleton().m_onMouseWheelUp.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseWheelDown(String s) {
        Functions.getSingleton().m_onMouseWheelDown.add(new EventListener(s,this));
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public void onMouseWheelMoved(String s) {
        Functions.getSingleton().m_onMouseWheelMoved.add(new EventListener(s,this));
    }
    
    protected static void callback(java.util.ArrayList<String> functions, State s) {
        for (int i = 0; i < functions.size(); i++) {
            call(functions.get(i), s);
        }
    }
    
    protected static void callback(java.util.ArrayList<String> functions) {
        for (int i=0; i<functions.size(); i++) {
            call(functions.get(i));
        }
    }
    
    
    /**
     * Calls the specified end-user-defined method, passing as a parameter the specified state.
     * If the function is undefined with one argument, it will be called with no arguments.
     * @param method The end-user-defined method to call
     * @param s The state to pass
     */
    protected static void call(String method, State s) {
        try {
            for (int i=0; i<Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m=Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    int params=m.getParameterTypes().length;
                    if (params==0) {
                        m.invoke(Macros.getProgram());
                    }
                    else if (params==1) {
                        if (m.getParameterTypes()[0].getSuperclass().getSimpleName().equals("State")) {
                            m.invoke(Macros.getProgram(), m.getParameterTypes()[0].cast(s));
                        }
                        else {
                            org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" takes one parameter but it must extend State to be valid"));
                        }
                    }
                    else {
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" must take zero or one parameter"));
                    }
                }
            }
            //TODO tell if method not found
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
    
    /**
     * Calls the specified end-user-defined method
     * @param method The end-user-defined method to call
     */
    protected static void call(String method) {
        try {
            for (int i=0; i<Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m=Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    int params=m.getParameterTypes().length;
                    if (params==0) {
                        m.invoke(Macros.getProgram());
                    }
                    else if (params==1) {
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" cannot take a parameter with this listener"));
                    }
                    else {
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" must take zero parameters"));
                    }
                }
            }
            //TODO tell if method not found
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
