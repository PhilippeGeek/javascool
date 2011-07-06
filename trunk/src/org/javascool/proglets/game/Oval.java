/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.Graphics;
import java.util.logging.Logger;
import org.javascool.tools.Macros;

/**
 *
 * @author gmatheron
 */
public class Oval extends Geometry implements Drawable {
    private boolean m_solid;
    
    public Oval(int x, int y, int w, int h) {
        super(x,y,w,h);
        m_solid=true;
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    public Oval(int x, int y, int w, int h, boolean solid) {
        super(x,y,w,h);
        ((Panel)Macros.getProgletPanel()).addItem(this);
        m_solid=solid;
    }
    
    @Override
    public void draw(Graphics g) {
        if (isM_solid()) {
            g.fillOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        }
        else {
            g.drawOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        }
    }
    private static final Logger LOG = Logger.getLogger(Oval.class.getName());

    /**
     * @return the m_solid
     */
    public boolean isM_solid() {
        return m_solid;
    }

    /**
     * @param m_solid the m_solid to set
     */
    public void setM_solid(boolean m_solid) {
        this.m_solid = m_solid;
    }
}
