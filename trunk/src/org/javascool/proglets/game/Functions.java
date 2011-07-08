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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javascool.tools.Console;
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
 * Main class for this proglet, it implements EventCatcher to allow it to catch
 * events : is you simply use
 * <code>onClick("exit");</code>
 * Functions will be used as EventCatcher (but it always return true and catches the event when triggered)
 * 
 * @author gmatheron
 */
public class Functions implements EventCatcher {
    /*
     * See getSingleton()
     */

    private static Functions m_singleton;

    /**
     * A singleton is used more for legacy than anything else. Everything could be
     * declared static but it probably would be a mess to refactor everything...
     * @return the singleton
     */
    public static Functions getSingleton() {
        return m_singleton;
    }
    /**
     * Stores the state of each mouse button
     */
    private boolean m_mouseDown[] = {false, false, false};
    /**
     * Stores position of the mouse wheel (in blocks) relative to its state at the
     * beginning of the user-defined program (to be checked)
     */
    private double m_mouseWheelPosition = 0;
    /* These arrays are designed to store the functions the user assigned a listener
     * A convenience type is used : EventListener
     */
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onClick;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseEntered;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseExited;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMousePressed;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseReleased;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseDown;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseUp;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseMoved;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseDragged;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseWheelUp;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseWheelDown;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onMouseWheelMoved;
    @SuppressWarnings("PublicField")
    public java.util.ArrayList<EventListener> m_onFrame;
    @SuppressWarnings("PublicField")
    public java.awt.event.MouseListener m_mouseListener;
    @SuppressWarnings("PublicField")
    public java.awt.event.MouseMotionListener m_mouseMotionListener;
    @SuppressWarnings("PublicField")
    public java.awt.event.MouseWheelListener m_mouseWheelListener;

    /**
     * Returns the mouse X position relative to the top-left corner of the
     * proglet panel
     * @return the mouse X position relative to the top-left corner of the
     * proglet panel
     */
    public static double mouseX() {
        return (double) m_singleton.m_mousePosRelativeToPanelX;
    }

    /**
     * Returns the mouse Y position relative to the top-left corner of the
     * proglet panel
     * @return the mouse Y position relative to the top-left corner of the
     * proglet panel
     */
    public static double mouseY() {
        return (double) m_singleton.m_mousePosRelativeToPanelY;
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onClick(String s) {
        m_singleton.m_onClick.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseEntered(String s) {
        m_singleton.m_onMouseEntered.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseExited(String s) {
        m_singleton.m_onMouseExited.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMousePressed(String s) {
        m_singleton.m_onMousePressed.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseReleased(String s) {
        m_singleton.m_onMouseReleased.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseDown(String s) {
        m_singleton.m_onMouseDown.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseUp(String s) {
        m_singleton.m_onMouseUp.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseMoved(String s) {
        m_singleton.m_onMouseMoved.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseState argument
     * @param s The function to callback
     */
    public static void onMouseDragged(String s) {
        m_singleton.m_onMouseDragged.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public static void onMouseWheelUp(String s) {
        m_singleton.m_onMouseWheelUp.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public static void onMouseWheelDown(String s) {
        m_singleton.m_onMouseWheelDown.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * with one MouseWheelState argument
     * @param s The function to callback
     */
    public static void onMouseWheelMoved(String s) {
        m_singleton.m_onMouseWheelMoved.add(new EventListener(s, m_singleton));
    }

    /**
     * Used to create a listener that will callback the specified function
     * @param s The function to callback
     */
    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    public static void onFrame(String s) {
        m_singleton.m_onFrame.add(new EventListener(s, m_singleton));
    }

    public static boolean classExtends(Class<?> sub, Class<?> superClass) {
        if (sub.getSuperclass() == superClass) {
            return true;
        } else if (sub.getSuperclass() == Object.class) {
            return false;
        } else {
            return classExtends(sub.getSuperclass(), superClass);
        }
    }

    /**
     * Calls the specified end-user-defined method, passing as a parameter the specified state.
     * If the function is undefined with one argument, it will be called with no arguments.
     * @param method The end-user-defined method to call
     * @param s The state to pass
     */
    private static void call(String method, Object s) {
        boolean found=false;
        try {
            for (int i = 0; i < Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m = Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    int params = m.getParameterTypes().length;
                    if (params == 0) {
                        m.invoke(Macros.getProgram());
                        found=true;
                    } else if (params == 1) {
                        if (m.getParameterTypes()[0] == s.getClass()) {
                             m.getParameterTypes()[0].cast(s);
                            m.invoke(Macros.getProgram(), m.getParameterTypes()[0].cast(s));
                            found=true;
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            System.out.println(ex.getCause());
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!found) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE,null,new Exception("Callback method "+method+" not found"));
        }
    }

    /**
     * Calls the specified end-user-defined method
     * @param method The end-user-defined method to call
     */
    private static void call(String method) {
        boolean found=false;
        try {
            for (int i = 0; i < Macros.getProgram().getClass().getMethods().length; i++) {
                java.lang.reflect.Method m = Macros.getProgram().getClass().getMethods()[i];
                if (m.getName().equals(method)) {
                    if (m.getParameterTypes().length == 0) {
                        m.invoke(Macros.getProgram()); found=true;
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!found) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE,null,new Exception("Callback method "+method+" not found"));
        }
    }

    /**
     * This method should be called after the program finishes running
     * It stops the timer and should delete the listeners (//TODO)
     */
    public static void stop() {
        m_clock.exitClean();
        Macros.getProgletPanel().removeMouseListener(m_singleton.m_mouseListener);
        Macros.getProgletPanel().removeMouseMotionListener(m_singleton.m_mouseMotionListener);
        Macros.getProgletPanel().removeMouseWheelListener(m_singleton.m_mouseWheelListener);
        
        m_singleton.m_onClick.removeAll(m_singleton.m_onClick);
        m_singleton.m_onMouseDown.removeAll(m_singleton.m_onMouseDown);
        m_singleton.m_onMouseDragged.removeAll(m_singleton.m_onMouseDragged);
        m_singleton.m_onMouseEntered.removeAll(m_singleton.m_onMouseEntered);
        m_singleton.m_onMouseExited.removeAll(m_singleton.m_onMouseExited);
        m_singleton.m_onMouseMoved.removeAll(m_singleton.m_onMouseMoved);
        m_singleton.m_onMousePressed.removeAll(m_singleton.m_onMousePressed);
        m_singleton.m_onMouseReleased.removeAll(m_singleton.m_onMouseReleased);
        m_singleton.m_onMouseUp.removeAll(m_singleton.m_onMouseUp);
        m_singleton.m_onFrame.removeAll(m_singleton.m_onFrame);
        m_singleton.m_onMouseWheelDown.removeAll(m_singleton.m_onMouseWheelDown);
        m_singleton.m_onMouseWheelUp.removeAll(m_singleton.m_onMouseWheelUp);
        m_singleton.m_onMouseWheelMoved.removeAll(m_singleton.m_onMouseWheelMoved);
        
        Panel p=(Panel)(Macros.getProgletPanel());
        p.stop();
    }
    /**
     * Stores a running clock that ticks at each frame and triggers frame-driven events
     */
    private static Clock m_clock;

    /**
     * This method is called during init
     * It creates the listeners, the clock and the singleton
     */
    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "CollectionWithoutInitialCapacity"})
    public static void start() {
        /* A singleton is used to be able to define non-static classes inside Functions
         * The functions the end-user can call are all static, but they refer
         * to non-static attributes using this singleton static attribute
         */
        m_singleton = new Functions();

        /* These arrays store the listeners that should be called when an event occurs
         */
        m_singleton.m_onClick = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseDown = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseDragged = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseEntered = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseExited = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseMoved = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMousePressed = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseReleased = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseUp = new java.util.ArrayList<EventListener>();
        m_singleton.m_onFrame = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseWheelDown = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseWheelUp = new java.util.ArrayList<EventListener>();
        m_singleton.m_onMouseWheelMoved = new java.util.ArrayList<EventListener>();

        /* The clock object will 'tick' each 1/30s. it will then call the callback
         * functions for onMouseDown, onMouseUp, etc if needed
         */
        m_clock = new Clock();
        m_clock.setFps(300);
        (new Thread(m_clock)).start();

        /* Define a few anonymous classes that will define the proglet's behavior
         * when an event is performed. Uually the proglet will call all the callback
         * functions for this event.
         * Possible optimization : only register a listener if an event is defined
         */
        //<editor-fold defaultstate="collapsed" desc="Anonymous classes">
        {
            /********* START ANONYMOUS CLASSES ***************/
            m_singleton.m_mouseListener = new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    callback(getSingleton().m_onClick);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    callback(getSingleton().m_onMouseEntered);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    callback(getSingleton().m_onMouseExited);
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    callback(getSingleton().m_onMousePressed);
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        m_singleton.m_mouseDown[0] = true;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON2) {
                        m_singleton.m_mouseDown[1] = true;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        m_singleton.m_mouseDown[2] = true;
                    }
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    callback(getSingleton().m_onMouseReleased);
                    if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                        m_singleton.m_mouseDown[0] = false;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON2) {
                        m_singleton.m_mouseDown[1] = false;
                    } else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        m_singleton.m_mouseDown[2] = false;
                    }
                }
            };
            Macros.getProgletPanel().addMouseListener(m_singleton.m_mouseListener);

            m_singleton.m_mouseMotionListener = new java.awt.event.MouseMotionListener() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    m_singleton.m_mousePosRelativeToPanelX = e.getX();
                    m_singleton.m_mousePosRelativeToPanelY = e.getY();
                    callback(getSingleton().m_onMouseDragged);
                    callback(getSingleton().m_onMouseMoved);
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    m_singleton.m_mousePosRelativeToPanelX = e.getX();
                    m_singleton.m_mousePosRelativeToPanelY = e.getY();
                    callback(getSingleton().m_onMouseMoved);
                }
            };
            Macros.getProgletPanel().addMouseMotionListener(m_singleton.m_mouseMotionListener);

            m_singleton.m_mouseWheelListener = new java.awt.event.MouseWheelListener() {

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    double copy = getSingleton().m_mouseWheelPosition;
                    m_singleton.m_mouseWheelPosition += e.getWheelRotation();
                    if (copy > getSingleton().m_mouseWheelPosition) {
                        callback(getSingleton().m_onMouseWheelDown);
                    } else {
                        callback(getSingleton().m_onMouseWheelUp);
                    }
                    callback(getSingleton().m_onMouseWheelMoved);
                }
            };
            Macros.getProgletPanel().addMouseWheelListener(m_singleton.m_mouseWheelListener);
            /********* END ANONYMOUS CLASSES ***************/
        }
        //</editor-fold>
    }

    /**
     * Calls each function of the specified array if the EventCatcher accepts it or is set to always
     * @param functions The functions to call
     */
    private static void callback(java.util.ArrayList<EventListener> functions) {
        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getObject().isForMe() || functions.get(i).getAlways()) {
                if (functions.get(i).getObject() == null) {
                    call(functions.get(i).getMethod());
                } else {
                    call(functions.get(i).getMethod(), functions.get(i).getObject());
                }
            }
        }
    }

    /**
     * A function directly assigned to the main panel will always be catched, so return true;
     * @return true
     */
    @Override
    public boolean isForMe() {
        return true;
    }

    /**
     * This class allows the proglet to trigger events regularly. Depending on the
     * selected framerate (see setFps(int)), a main routine will be executed at the given 
     * speed. At each loop, the clock will call the tick() method.
     */
    @SuppressWarnings("PublicInnerClass")
    public static class Clock implements Runnable {

        /**
         * Set to true when the clock needs to exit
         */
        private boolean m_exit = false;
        /**
         * Defines the framerate that the clock will try to achieve
         */
        private double m_fps = 30;

        /**
         * Default constructor, does nothing : see run()
         */
        Clock() {
        }

        /**
         * The clock is intended to be run as a thread, so it implements Runnable.
         * When ran, the clock will tick regularly. Be sure to call the setFps(double)
         * before running the clock, or it should (not tested) tick at 30 fps.
         * This method will break when m_exit is set to true (latency can be a bit more
         * that 1/m_fps seconds.
         */
        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            while (true) {
                if (!Console.isRunning()) {
                    break;
                }
                try {
                    Thread.sleep((int)(1000 / m_fps));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
                }
                tick();
                if (m_exit) {
                    break;
                }
            }
            stop();
        }

        /**
         * Sets the target framerate. For the moment (//TODO) the real framerate
         * will be inferior to the target because the proglet just waits for 
         * 1/m_fps seconds between each tick, so the duration of the tick is
         * not included. Please note that the execution of the callback function
         * is modal (it will block the clock's thread) so make sure to make
         * callback functions that return quickly.
         * @param fps 
         */
        public void setFps(float fps) {
            m_fps = fps;
        }

        /**
         * Triggers : 
         *     - The mouseDown event if any mouse button is pressed
         *     - The mouseUp event if no mouse button is pressed
         *     - The onFrame event
         * Forces the proglet panel to repaint
         */
        @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
        private void tick() {
            for (int j = 0; j < 3; j++) {
                if (getSingleton().m_mouseDown[j]) {
                    callback(getSingleton().m_onMouseDown);
                } else {
                    callback(getSingleton().m_onMouseUp);
                }
            }
            callback(getSingleton().m_onFrame);

            Macros.getProgletPanel().repaint();
        }

        public void exitClean() {
            m_exit = true;
        }
    }
    /**
     * Stores the cursor's position relative to the panel's topleft corner
     */
    @SuppressWarnings("PublicField")
    public float m_mousePosRelativeToPanelX;
    @SuppressWarnings("PublicField")
    public float m_mousePosRelativeToPanelY;
    private static final Logger LOG = Logger.getLogger(Functions.class.getName());
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
@SuppressWarnings("MultipleTopLevelClassesInFile")
class State {

    private static final Logger LOG = Logger.getLogger(State.class.getName());

    @SuppressWarnings("PublicConstructorInNonPublicClass")
    public State() {
    }
}