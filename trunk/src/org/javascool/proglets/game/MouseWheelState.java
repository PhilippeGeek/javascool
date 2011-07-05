package org.javascool.proglets.game;

import java.awt.event.MouseWheelEvent;

/**
 *
 * @author gmatheron
 */
class MouseWheelState extends State {
    private java.awt.event.MouseWheelEvent m_evt;
    public MouseWheelState(MouseWheelEvent e) {
        m_evt=e;
    }
    
    /* 
     * TODO add covenience methods such as x and y. Maybe leave them public
     * in case the user prefers to use s.x rather than x.getX() ?
     */
}
