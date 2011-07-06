/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.javascool.tools.Macros;

/**
 *
 * @author gmatheron
 */
public class Sprite extends Geometry implements Drawable {
    private BufferedImage m_image;
    
    public Sprite(float x, float y, float w, float h) {
        super(x,y,w,h);
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    public void load(String fileName) {
        try {
            m_image=ImageIO.read(new File(fileName));
        }
        catch (IOException e) {
            org.javascool.JvsMain.reportBug(e); //TODO
        }
    }
    
    @Override
    public void draw(Graphics g) {
        if (m_image!=null) {
            g.drawImage(m_image, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), null);
        }
    }
    private static final Logger LOG = Logger.getLogger(Sprite.class.getName());
}
