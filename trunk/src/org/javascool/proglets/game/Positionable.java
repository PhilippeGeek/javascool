/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.util.logging.Logger;

/**
 *
 * @author gmatheron
 */
public class Positionable {
    private float m_x, m_y;
    public Positionable () {
        m_x=0; m_y=0;
    }
    public Positionable (float x, float y) {
        m_x=x; m_y=y;
    }
    public float getX() {
        return m_x;
    }
    public float getY() {
        return m_y;
    }
    public void setX(float x) {
        m_x=x;
    }
    public void setY(float y) {
        m_y=y;
    }
    public void position(float x, float y) {
        m_x=x; m_y=y;
    }
    private static final Logger LOG = Logger.getLogger(Positionable.class.getName());
}
