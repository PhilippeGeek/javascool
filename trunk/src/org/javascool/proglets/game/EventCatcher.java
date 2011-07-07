/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author gmatheron
 */
public interface EventCatcher {
    public boolean isForMe(MouseState e);
    public boolean isForMe(MouseWheelState e);
    public boolean isForMe();
}
