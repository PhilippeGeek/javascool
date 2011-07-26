package org.javascool.proglets.jeux2D;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.applet.Applet;
import java.awt.Color;
import java.util.logging.Logger;
import javax.swing.JApplet;
import org.javascool.proglets.game.GamePanel;

/**
 *
 * @author gmatheron
 */
public class Panel extends JApplet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Panel.class.getName());
    private GamePanel m_g;
    
    public Panel() {
        m_g=new GamePanel();
        setContentPane(m_g);
    }
    
    public GamePanel getGamePanel() {
        return m_g;
    }
}
