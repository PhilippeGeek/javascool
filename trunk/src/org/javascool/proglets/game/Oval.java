/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.Graphics;
import java.util.logging.Logger;
import org.javascool.tools.Macros;

/**
 * This class allows the end-user to draw and manipulate an oval
 * @author gmatheron
 */
public class Oval extends Geometry implements Drawable {
    /**
     * True if the oval should be filled, false otherwise
     */
    private boolean m_solid;
    
    /**
     * Creates and draws a non-filled oval
     * @param x The X position of the topleft corner of the rectangle that fits the oval
     * @param y The Y position of the topleft corner of the rectangle that fits the oval
     * @param w The width of the topleft corner of the rectangle that fits the oval
     * @param h The height of the topleft corner of the rectangle that fits the oval
     */
    public Oval(int x, int y, int w, int h) {
        super(x,y,w,h);
        m_solid=true;
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    /**
     * Creates and draws an oval
     * @param x The X position of the topleft corner of the rectangle that fits the oval
     * @param y The Y position of the topleft corner of the rectangle that fits the oval
     * @param w The width of the topleft corner of the rectangle that fits the oval
     * @param h The height of the topleft corner of the rectangle that fits the oval
     * @param solid True if the oval should be filled, false otherwise
     */
    public Oval(int x, int y, int w, int h, boolean solid) {
        super(x,y,w,h);
        ((Panel)Macros.getProgletPanel()).addItem(this);
        m_solid=solid;
    }
    
    /**
     * Draws the oval to a Graphics buffer.
     * @param g The Graphics buffer on which to draw the oval
     */
    @Override
    public void draw(Graphics g) {
        if (m_solid) {
            g.fillOval(getX(), getY(), getWidth(), getHeight());
        }
        else {
            g.drawOval(getX(), getY(), getWidth(), getHeight());
        }
    }
    
    private static final Logger LOG = Logger.getLogger(Oval.class.getName());
}
