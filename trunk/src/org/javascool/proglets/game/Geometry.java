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
public class Geometry extends LinkedEventGroup {
    private int m_w, m_h, m_x, m_y;
    
    public Geometry(int x, int y) {
        m_x=x; m_y=y;
    }
    public Geometry(int x, int y, int w, int h) {
        m_x=x; m_y=y; m_w=w; m_h=h;
    }
    public Geometry() {
        m_w=0; m_h=0; m_x=0; m_y=0;
    }
    public int getWidth() {
        return m_w;
    }
    public int getHeight() {
        return m_h;
    }
    public int getX() {
        return m_x;
    }
    public int getY() {
        return m_y;
    }
    public void setWidth(int w) {
        m_w=w;
    }
    public void setHeight(int h) {
        m_h=h;
    }
    public void setX(int x) {
        m_x=x;
    }
    public void setY(int y) {
        m_y=y;
    }
    public void scale(int w, int h) {
        m_w=w; m_h=h;
    }
    public void position(int x, int y) {
        m_x=x; m_y=y;
    }
    private static final Logger LOG = Logger.getLogger(Geometry.class.getName());

    @Override
    public boolean isForMe(MouseState e) {
        return (e.getX()>m_x && e.getX()<m_x+m_w && e.getY()>m_y && e.getY()<m_y+m_h);
    }

    @Override
    public boolean isForMe(MouseWheelState f) {
        MouseState e=new MouseState();
        return (e.getX()>m_x && e.getX()<m_x+m_w && e.getY()>m_y && e.getY()<m_y+m_h);
    }

    @Override
    public boolean isForMe() {
        MouseState e=new MouseState();
        return (e.getX()>m_x && e.getX()<m_x+m_w && e.getY()>m_y && e.getY()<m_y+m_h);
    }
}
