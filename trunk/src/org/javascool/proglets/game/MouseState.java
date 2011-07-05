/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;


/**
 * A MouseState is passed as a parameter to the user-defined callback functions
 * so it must stay as intuitive as possible
 */
public class MouseState extends State {

    private java.awt.event.MouseEvent m_evt;
    private int m_button;

    MouseState(java.awt.event.MouseEvent evt) {
        m_evt = evt;
    }

    MouseState(int button) {
        m_button = button;
    }
    /* 
     * TODO add covenience methods such as x and y. Maybe leave them public
     * in case the user prefers to use s.x rather than x.getX() ?
     */
}