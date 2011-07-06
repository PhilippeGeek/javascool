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
public class Geometry extends Positionable {
    private float m_w, m_h;
    public Geometry(float x, float y) {
        super(x,y);
    }
    public Geometry(float x, float y, float w, float h) {
        super(x,y); m_w=w; m_h=h;
    }
    public Geometry() {
        m_w=0; m_h=0;
    }
    public float getWidth() {
        return m_w;
    }
    public float getHeight() {
        return m_h;
    }
    public void setWidth(float w) {
        m_w=w;
    }
    public void setHeight(float h) {
        m_h=h;
    }
    public void scale(float w, float h) {
        m_w=w; m_h=h;
    }
    private static final Logger LOG = Logger.getLogger(Geometry.class.getName());
}
