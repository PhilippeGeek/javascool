/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import org.javascool.tools.Macros;

/**
 *
 * @author gmatheron
 */
public class Oval extends Geometry implements Drawable {
    public Oval(int x, int y, int w, int h) {
        super(x,y,w,h);
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
    }
}
