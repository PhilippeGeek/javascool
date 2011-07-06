/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;


/**
 * A MouseState is passed as a parameter to the user-defined callback functions
 * so it must stay as intuitive as possible
 */
public class MouseState extends State {

    private int m_x, m_y;
    private int m_button;

    MouseState(java.awt.event.MouseEvent evt) {
        m_x=evt.getX();
        m_y=evt.getY();
        m_button=evt.getButton();
    }
    
    MouseState() {
        m_x=(int) Functions.getSingleton().m_mousePosRelativeToPanelX;
        m_y=(int) Functions.getSingleton().m_mousePosRelativeToPanelY;
        m_button=-1;
    }
    
    public int getX() {
        return m_x;
    }
    
    public int getY() {
        return m_y;
    }
    
    /* 
     * TODO add covenience methods such as x and y. Maybe leave them public
     * in case the user prefers to use s.x rather than x.getX() ?
     */
    private static final Logger LOG = Logger.getLogger(MouseState.class.getName());
}