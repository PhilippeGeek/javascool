package org.javascool.proglets.game;

import java.awt.event.MouseWheelEvent;
import java.util.logging.Logger;

/**
 *
 * @author gmatheron
 */
public class MouseWheelState extends State {
    private int m_pos;
    private int m_delta;
    
    public MouseWheelState(MouseWheelEvent e, int position) {
        m_delta=e.getWheelRotation();
        m_pos=position;
    }
    
    public int getPosition() {
        return m_pos;
    }
    
    public int getMove() {
        return m_delta;
    }
    private static final Logger LOG = Logger.getLogger(MouseWheelState.class.getName());
}
