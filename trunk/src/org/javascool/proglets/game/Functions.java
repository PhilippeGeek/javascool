/**
 * Functions.java
 * Part of Javascool proglet "game"
 * File creation : 20110704 at revision 48 by gmatheron (INRIA)
 * Last modification 20110704 by gmatheron (INRIA)
 * 
 * gmatheron : guillaume.matheron.06@gmail.com
 */
package org.javascool.proglets.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javascool.tools.Macros;

/* To use these event listeners, use this syntax : 
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

    private static Functions m_singleton;

    /**
     * @return the m_singleton
     */
    public static Functions getM_singleton() {
        return m_singleton;
    }

    /**
     * @param aM_singleton the m_singleton to set
     */
    public static void setM_singleton(Functions aM_singleton) {
        m_singleton = aM_singleton;
    }

    /**
     * @return the m_clock
     */
    public static Clock getM_clock() {
        return m_clock;
    }

    /**
     * @param aM_clock the m_clock to set
     */
    public static void setM_clock(Clock aM_clock) {
        m_clock = aM_clock;
    }

    /**
     * @return the LOG
     */
    public static Logger getLOG() {
        return LOG;
    }

    /**
     * @param aLOG the LOG to set
     */
    public static void setLOG(Logger aLOG) {
        LOG = aLOG;
    }
    private boolean m_mouseDown[] = {false, false, false};
    private int m_mouseWheelPosition = 0;
    /* These arrays are designed to store the functions the user assigned a listener
     * A convenience type is used : CallbackFunction
     */
    private java.util.ArrayList<String> m_onClick;
    private java.util.ArrayList<String> m_onMouseEntered;
    private java.util.ArrayList<String> m_onMouseExited;
    private java.util.ArrayList<String> m_onMousePressed;
    private java.util.ArrayList<String> m_onMouseReleased;
    private java.util.ArrayList<String> m_onMouseDown;
    private java.util.ArrayList<String> m_onMouseUp;
    private java.util.ArrayList<String> m_onMouseMoved;
    private java.util.ArrayList<String> m_onMouseDragged;
    private java.util.ArrayList<String> m_onMouseWheelUp;
    private java.util.ArrayList<String> m_onMouseWheelDown;
    private java.util.ArrayList<String> m_onMouseWheelMoved;
    private java.util.ArrayList<String> m_onFrame;
    private java.awt.event.MouseListener m_mouseListener;
    private java.awt.event.MouseMotionListener m_mouseMotionListener;
    private java.awt.event.MouseWheelListener m_mouseWheelListener;

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onClick(String s) {
        getM_singleton().getM_onClick().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseEntered(String s) {
        getM_singleton().getM_onMouseEntered().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseExited(String s) {
        getM_singleton().getM_onMouseExited().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMousePressed(String s) {
        getM_singleton().getM_onMousePressed().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseReleased(String s) {
        getM_singleton().getM_onMouseReleased().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseDown(String s) {
        getM_singleton().getM_onMouseDown().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseUp(String s) {
        getM_singleton().getM_onMouseUp().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseMoved(String s) {
        getM_singleton().getM_onMouseMoved().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseDragged(String s) {
        getM_singleton().getM_onMouseDragged().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseWheelUp(String s) {
        getM_singleton().getM_onMouseWheelUp().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseWheelDown(String s) {
        getM_singleton().getM_onMouseWheelDown().add(s);
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseWheelMoved(String s) {
        getM_singleton().getM_onMouseWheelMoved().add(s);
    }
    
    /**
     * Used to create a listener that will callback the specified function
     * @param s The function to callback
     */
    public static void onFrame(String s) {
        getM_singleton().getM_onFrame().add(s);
    }

    /**
     * Calls the specified end-user-defined method, passing as a parameter the specified state.
     * If the function is undefined with one argument, it will be called with no arguments.
     * @param method The end-user-defined method to call
     * @param s The state to pass
     */
    private static void call(String method, State s) {
        try {
            for (int i=0; i<Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m=Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    int params=m.getParameterTypes().length;
                    if (params==0) m.invoke(Macros.getProgram());
                    else if (params==1) {
                        if (m.getParameterTypes()[0].getSuperclass().getSimpleName().equals("State")) {
                            m.invoke(Macros.getProgram(), m.getParameterTypes()[0].cast(s));
                        }
                        else {
                            org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" takes one parameter but it must extend State to be valid"));
                        }
                    }
                    else
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" must take zero or one parameter"));
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
    private static void call(String method) {
        try {
            for (int i=0; i<Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m=Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    int params=m.getParameterTypes().length;
                    if (params==0) m.invoke(Macros.getProgram());
                    else if (params==1) {
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" cannot take a parameter with this listener"));
                    }
                    else
                        org.javascool.JvsMain.reportBug(new NoSuchMethodException("The specified method "+method+" must take zero parameters"));
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

    /** //TODO
     * This method should be called after the program finishes running
     * It stops the timer and should delete the listeners (//TODO)
     */
    public static void stop() {
        /*
        m_clock.exitClean();
        Macros.getProgletPanel().removeMouseListener(m_singleton.m_mouseListener);
        Macros.getProgletPanel().removeMouseMotionListener(m_singleton.m_mouseMotionListener);
        Macros.getProgletPanel().removeMouseWheelListener(m_singleton.m_mouseWheelListener);
        (Panel)(Macros.getProgletPanel()).stop();*/
    }
    
    private static Clock m_clock;

    /**
     * This method is called during init
     * It creates the listeners, the clock and the singleton
     */
    public static void start() {
        /* A singleton is used to be able to define non-static classes inside Functions
         * The functions the end-user can call are all static, but they refer
         * to non-static attributes using this singleton static attribute
         */
        setM_singleton(new Functions());

        /* These arrays store the listeners that should be called when an event occurs
         */
        m_singleton.setM_onClick(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseDown(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseDragged(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseEntered(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseExited(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseMoved(new java.util.ArrayList<String>());
        m_singleton.setM_onMousePressed(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseReleased(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseUp(new java.util.ArrayList<String>());
        m_singleton.setM_onFrame(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseWheelDown(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseWheelUp(new java.util.ArrayList<String>());
        m_singleton.setM_onMouseWheelMoved(new java.util.ArrayList<String>());

        /* The clock object will 'tick' each 1/30s. it will then call the callback
         * functions for onMouseDown, onMouseUp, etc if needed
         */
        setM_clock(new Clock());
        getM_clock().setFps(30);
        (new Thread(getM_clock())).start();

        /* Define a few anonymous classes that will define the proglet's behavior
         * when an event is performed. Uually the proglet will call all the callback
         * functions for this event.
         * Possible optimization : only register a listener if an event is defined
         */
        {
            /********* START ANONYMOUS CLASSES ***************/
            m_singleton.setM_mouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    callback(getM_singleton().getM_onClick(), new MouseState(evt));
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    callback(getM_singleton().getM_onMouseEntered(), new MouseState(evt));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    callback(getM_singleton().getM_onMouseExited(), new MouseState(evt));
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    callback(getM_singleton().getM_onMousePressed(), new MouseState(evt));
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        m_singleton.getM_mouseDown()[0] = true;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON2) {
                        m_singleton.getM_mouseDown()[1] = true;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        m_singleton.getM_mouseDown()[2] = true;
                    }
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    callback(getM_singleton().getM_onMouseReleased(), new MouseState(evt));
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        m_singleton.getM_mouseDown()[0] = false;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON2) {
                        m_singleton.getM_mouseDown()[1] = false;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        m_singleton.getM_mouseDown()[2] = false;
                    }
                }
            });
            Macros.getProgletPanel().addMouseListener(getM_singleton().getM_mouseListener());

            m_singleton.setM_mouseMotionListener(new java.awt.event.MouseMotionListener() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    m_singleton.setM_mousePosRelativeToPanelX(e.getX());
                    m_singleton.setM_mousePosRelativeToPanelY(e.getY());
                    callback(getM_singleton().getM_onMouseDragged(), new MouseState(e));
                    callback(getM_singleton().getM_onMouseMoved(), new MouseState(e));
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    m_singleton.setM_mousePosRelativeToPanelX(e.getX());
                    m_singleton.setM_mousePosRelativeToPanelY(e.getY());
                    callback(getM_singleton().getM_onMouseMoved(), new MouseState(e));
                }
            });
            Macros.getProgletPanel().addMouseMotionListener(getM_singleton().getM_mouseMotionListener());

            m_singleton.setM_mouseWheelListener(new java.awt.event.MouseWheelListener() {

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int copy = getM_singleton().getM_mouseWheelPosition();
                    m_singleton.setM_mouseWheelPosition(m_singleton.getM_mouseWheelPosition() + e.getWheelRotation());
                    if (copy > getM_singleton().getM_mouseWheelPosition()) {
                        callback(getM_singleton().getM_onMouseWheelDown(), new MouseWheelState(e, getM_singleton().getM_mouseWheelPosition()));
                    } else {
                        callback(getM_singleton().getM_onMouseWheelUp(), new MouseWheelState(e, getM_singleton().getM_mouseWheelPosition()));
                    }
                    callback(getM_singleton().getM_onMouseWheelMoved(), new MouseWheelState(e, getM_singleton().getM_mouseWheelPosition()));
                }
            });
            Macros.getProgletPanel().addMouseWheelListener(getM_singleton().getM_mouseWheelListener());
        }
        /********* END ANONYMOUS CLASSES ***************/
    }

    private static void callback(java.util.ArrayList<String> functions, State s) {
        for (int i = 0; i < functions.size(); i++) {
            call(functions.get(i), s);
        }
    }
    
    private static void callback(java.util.ArrayList<String> functions) {
        for (int i=0; i<functions.size(); i++) {
            call(functions.get(i));
        }
    }

    /**
     * @return the m_mouseDown
     */
    public boolean m_mouseDown[] getM_mouseDown() {
        return m_mouseDown;
    }

    /**
     * @param m_mouseDown the m_mouseDown to set
     */
    public void setM_mouseDown(boolean m_mouseDown[] m_mouseDown) {
        this.m_mouseDown = m_mouseDown;
    }

    /**
     * @return the m_mouseWheelPosition
     */
    public int getM_mouseWheelPosition() {
        return m_mouseWheelPosition;
    }

    /**
     * @param m_mouseWheelPosition the m_mouseWheelPosition to set
     */
    public void setM_mouseWheelPosition(int m_mouseWheelPosition) {
        this.m_mouseWheelPosition = m_mouseWheelPosition;
    }

    /**
     * @return the m_onClick
     */
    public java.util.ArrayList<String> getM_onClick() {
        return m_onClick;
    }

    /**
     * @param m_onClick the m_onClick to set
     */
    public void setM_onClick(java.util.ArrayList<String> m_onClick) {
        this.m_onClick = m_onClick;
    }

    /**
     * @return the m_onMouseEntered
     */
    public java.util.ArrayList<String> getM_onMouseEntered() {
        return m_onMouseEntered;
    }

    /**
     * @param m_onMouseEntered the m_onMouseEntered to set
     */
    public void setM_onMouseEntered(java.util.ArrayList<String> m_onMouseEntered) {
        this.m_onMouseEntered = m_onMouseEntered;
    }

    /**
     * @return the m_onMouseExited
     */
    public java.util.ArrayList<String> getM_onMouseExited() {
        return m_onMouseExited;
    }

    /**
     * @param m_onMouseExited the m_onMouseExited to set
     */
    public void setM_onMouseExited(java.util.ArrayList<String> m_onMouseExited) {
        this.m_onMouseExited = m_onMouseExited;
    }

    /**
     * @return the m_onMousePressed
     */
    public java.util.ArrayList<String> getM_onMousePressed() {
        return m_onMousePressed;
    }

    /**
     * @param m_onMousePressed the m_onMousePressed to set
     */
    public void setM_onMousePressed(java.util.ArrayList<String> m_onMousePressed) {
        this.m_onMousePressed = m_onMousePressed;
    }

    /**
     * @return the m_onMouseReleased
     */
    public java.util.ArrayList<String> getM_onMouseReleased() {
        return m_onMouseReleased;
    }

    /**
     * @param m_onMouseReleased the m_onMouseReleased to set
     */
    public void setM_onMouseReleased(java.util.ArrayList<String> m_onMouseReleased) {
        this.m_onMouseReleased = m_onMouseReleased;
    }

    /**
     * @return the m_onMouseDown
     */
    public java.util.ArrayList<String> getM_onMouseDown() {
        return m_onMouseDown;
    }

    /**
     * @param m_onMouseDown the m_onMouseDown to set
     */
    public void setM_onMouseDown(java.util.ArrayList<String> m_onMouseDown) {
        this.m_onMouseDown = m_onMouseDown;
    }

    /**
     * @return the m_onMouseUp
     */
    public java.util.ArrayList<String> getM_onMouseUp() {
        return m_onMouseUp;
    }

    /**
     * @param m_onMouseUp the m_onMouseUp to set
     */
    public void setM_onMouseUp(java.util.ArrayList<String> m_onMouseUp) {
        this.m_onMouseUp = m_onMouseUp;
    }

    /**
     * @return the m_onMouseMoved
     */
    public java.util.ArrayList<String> getM_onMouseMoved() {
        return m_onMouseMoved;
    }

    /**
     * @param m_onMouseMoved the m_onMouseMoved to set
     */
    public void setM_onMouseMoved(java.util.ArrayList<String> m_onMouseMoved) {
        this.m_onMouseMoved = m_onMouseMoved;
    }

    /**
     * @return the m_onMouseDragged
     */
    public java.util.ArrayList<String> getM_onMouseDragged() {
        return m_onMouseDragged;
    }

    /**
     * @param m_onMouseDragged the m_onMouseDragged to set
     */
    public void setM_onMouseDragged(java.util.ArrayList<String> m_onMouseDragged) {
        this.m_onMouseDragged = m_onMouseDragged;
    }

    /**
     * @return the m_onMouseWheelUp
     */
    public java.util.ArrayList<String> getM_onMouseWheelUp() {
        return m_onMouseWheelUp;
    }

    /**
     * @param m_onMouseWheelUp the m_onMouseWheelUp to set
     */
    public void setM_onMouseWheelUp(java.util.ArrayList<String> m_onMouseWheelUp) {
        this.m_onMouseWheelUp = m_onMouseWheelUp;
    }

    /**
     * @return the m_onMouseWheelDown
     */
    public java.util.ArrayList<String> getM_onMouseWheelDown() {
        return m_onMouseWheelDown;
    }

    /**
     * @param m_onMouseWheelDown the m_onMouseWheelDown to set
     */
    public void setM_onMouseWheelDown(java.util.ArrayList<String> m_onMouseWheelDown) {
        this.m_onMouseWheelDown = m_onMouseWheelDown;
    }

    /**
     * @return the m_onMouseWheelMoved
     */
    public java.util.ArrayList<String> getM_onMouseWheelMoved() {
        return m_onMouseWheelMoved;
    }

    /**
     * @param m_onMouseWheelMoved the m_onMouseWheelMoved to set
     */
    public void setM_onMouseWheelMoved(java.util.ArrayList<String> m_onMouseWheelMoved) {
        this.m_onMouseWheelMoved = m_onMouseWheelMoved;
    }

    /**
     * @return the m_onFrame
     */
    public java.util.ArrayList<String> getM_onFrame() {
        return m_onFrame;
    }

    /**
     * @param m_onFrame the m_onFrame to set
     */
    public void setM_onFrame(java.util.ArrayList<String> m_onFrame) {
        this.m_onFrame = m_onFrame;
    }

    /**
     * @return the m_mouseListener
     */
    public java.awt.event.MouseListener getM_mouseListener() {
        return m_mouseListener;
    }

    /**
     * @param m_mouseListener the m_mouseListener to set
     */
    public void setM_mouseListener(java.awt.event.MouseListener m_mouseListener) {
        this.m_mouseListener = m_mouseListener;
    }

    /**
     * @return the m_mouseMotionListener
     */
    public java.awt.event.MouseMotionListener getM_mouseMotionListener() {
        return m_mouseMotionListener;
    }

    /**
     * @param m_mouseMotionListener the m_mouseMotionListener to set
     */
    public void setM_mouseMotionListener(java.awt.event.MouseMotionListener m_mouseMotionListener) {
        this.m_mouseMotionListener = m_mouseMotionListener;
    }

    /**
     * @return the m_mouseWheelListener
     */
    public java.awt.event.MouseWheelListener getM_mouseWheelListener() {
        return m_mouseWheelListener;
    }

    /**
     * @param m_mouseWheelListener the m_mouseWheelListener to set
     */
    public void setM_mouseWheelListener(java.awt.event.MouseWheelListener m_mouseWheelListener) {
        this.m_mouseWheelListener = m_mouseWheelListener;
    }

    /**
     * @return the m_mousePosRelativeToPanelX
     */
    public float getM_mousePosRelativeToPanelX() {
        return m_mousePosRelativeToPanelX;
    }

    /**
     * @param m_mousePosRelativeToPanelX the m_mousePosRelativeToPanelX to set
     */
    public void setM_mousePosRelativeToPanelX(float m_mousePosRelativeToPanelX) {
        this.m_mousePosRelativeToPanelX = m_mousePosRelativeToPanelX;
    }

    /**
     * @return the m_mousePosRelativeToPanelY
     */
    public float getM_mousePosRelativeToPanelY() {
        return m_mousePosRelativeToPanelY;
    }

    /**
     * @param m_mousePosRelativeToPanelY the m_mousePosRelativeToPanelY to set
     */
    public void setM_mousePosRelativeToPanelY(float m_mousePosRelativeToPanelY) {
        this.m_mousePosRelativeToPanelY = m_mousePosRelativeToPanelY;
    }

    /**
     * TODO
     */
    class Sprite {
        private javax.swing.JPanel m_panel;
        Sprite() {
            
        }
    }

    public static class Clock implements Runnable {

        private boolean m_exit = false;
        private int m_fps=30;

        Clock() {
            tt=new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            };
        }
        
        @Override
        public void run() {
            tick();
        }

        public void setFps(int fps) {
            m_fps = fps;
        }
        
        private TimerTask tt;
        
        private void tick() {
            for (int j = 0; j < 3; j++) {
                if (getM_singleton().getM_mouseDown()[j]) {
                    for (int i = 0; i < getM_singleton().getM_onMouseDown().size(); i++) {
                        call(getM_singleton().getM_onMouseDown().get(i), new MouseState());
                    }
                } else {
                    for (int i = 0; i < getM_singleton().getM_onMouseUp().size(); i++) {
                        call(getM_singleton().getM_onMouseUp().get(i), new MouseState());
                    }
                }
            }
            callback(getM_singleton().getM_onFrame());
            
            Macros.getProgletPanel().repaint();
            
            Timer t=new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            },(1000/m_fps));
        }

        public void exitClean() {
            m_exit = true;
            System.out.println("exiting clock...");
        }
    }
    
    private float m_mousePosRelativeToPanelX;
    private float m_mousePosRelativeToPanelY;
    private static Logger LOG = Logger.getLogger(Functions.class.getName());
}

/**
 * Superclass of all the states.
 * 
 * If an abstract (State) class can't be declared into a non-abstract class (Functions)
 * maybe we could switch it to an interface or leave it non-abstract (ugly)
 * 
 * PLEASE NOTE THAT ALL THE SUBCLASSES OF STATE MUST BE FINAL : ALL THE STATES MUST DIRECTLY
 * EXTEND THIS CLASS. See 'call' for details
 */
abstract class State {
}