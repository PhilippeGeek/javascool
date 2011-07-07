/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;


/**
 * A MouseState is passed as a parameter to the user-defined callback functions
 * so it must stay as intuitive as possible
 * @author gmatheron
 */
public class MouseState extends State {
    /**
     * The mouse's position relative to the panel
     */
    private int m_x, m_y;
    /**
     * The mouse buttons pressed
     */
    private int m_button;

    /**
     * Creates a new MouseState based on a MouseEvent
     * @param evt The MouseEvent from which to construct the MouseState
     */
    MouseState(java.awt.event.MouseEvent evt) {
        m_x=evt.getX();
        m_y=evt.getY();
        m_button=evt.getButton();
    }
    
    /**
     * Creates a new MouseState based on the current cursor's position
     */
    MouseState() {
        m_x=(int) Functions.getSingleton().m_mousePosRelativeToPanelX;
        m_y=(int) Functions.getSingleton().m_mousePosRelativeToPanelY;
        m_button=-1;
    }
    
    /**
     * Gets the X position of the cursor defined by this MouseState
     * @return the X position of the cursor defined by this MouseState
     */
    public int getX() {
        return m_x;
    }
    
    /**
     * Gets the Y position of the cursor defined by this MouseState
     * @return the Y position of the cursor defined by this MouseState
     */
    public int getY() {
        return m_y;
    }
    
    private static final Logger LOG = Logger.getLogger(MouseState.class.getName());
}