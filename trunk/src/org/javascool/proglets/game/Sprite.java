/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.javascool.tools.Macros;
import static org.javascool.gui.JVSMainPanel.*;

/**
 *
 * @author gmatheron
 */
public class Sprite extends Geometry implements Drawable {
    BufferedImage m_image;
    
    Sprite(float x, float y, float w, float h) {
        super(x,y,w,h);
        ((Panel)Macros.getProgletPanel()).addItem(this);
    }
    
    void load(String fileName) {
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
}
