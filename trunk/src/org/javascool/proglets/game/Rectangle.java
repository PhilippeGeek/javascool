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
public class Rectangle extends Geometry implements Drawable {
    private boolean m_solid;
    
    public Rectangle(float x, float y, float w, float h, boolean solid) {
        super(x,y,w,h);
        m_solid=solid;
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    public Rectangle(float x, float y, float w, float h) {
        super(x,y,w,h);
        m_solid=true;
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    @Override
    public void draw(Graphics g) {
        if (m_solid) {
            g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        }
        else {
            g.drawRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        }
    }
    private static final Logger LOG = Logger.getLogger(Rectangle.class.getName());
}
