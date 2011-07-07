package org.javascool.proglets.game;

import java.awt.event.MouseWheelEvent;
import java.util.logging.Logger;

/**
 * A MouseState is passed as a parameter to the user-defined callback functions
 * so it must stay as intuitive as possible
 * @author gmatheron
 */
public class MouseWheelState extends State {
    /**
     * The current position of the mouse wheel (in blocks since the beginning of the program (to be checked //TODO)
     */
    private int m_pos;
    /**
     * The difference between the actual position of the mouse wheel and its position
     * during the last frame. This value can be positive or negative.
     */
    private int m_delta;
    
    /**
     * Creates a new MouseXheelState based on a MouseWheelEvent and the actual
     * position of the MouseWheel.
     * @param e A MouseWheelEvent from which to read the MouseWheelState
     * @param position The actual position of the MouseWheel
     */
    public MouseWheelState(MouseWheelEvent e, int position) {
        m_delta=e.getWheelRotation();
        m_pos=position;
    }
    
    /**
     * Returns The actual position of the MouseWheel
     * @return The actual position of the MouseWheel
     */
    public int getPosition() {
        return m_pos;
    }
    
    /**
     * Returns the difference between the actual position of the mouse wheel and its position
     * during the last frame. This value can be positive or negative.
     * @return The difference between the actual position of the mouse wheel and its position
     * during the last frame.
     */
    public int getMove() {
        return m_delta;
    }
    
    private static final Logger LOG = Logger.getLogger(MouseWheelState.class.getName());
}
