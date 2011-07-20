/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.javascool.JVSMain;
import org.javascool.tools.Utils;

/** Open the About Windows
 * @author Philippe Vienne
 */
public class JVSAboutFrame extends JDialog {

    /** Open a new aboutFrame */
    public JVSAboutFrame() {
        super(new JFrame(), "About "+JVSMain.title, true);
        
        this.setIconImage(Utils.getIcon(JVSMain.logo).getImage());
        this.setLayout(new BorderLayout());
        getContentPane().add(new JLabel("<html>"+
                JVSMain.title+" est un logiciel conçut en colaboration avec : <br/><center>"
                + "Philippe Vienne<br/>"
                + "Guillaume Matheron<br/>"
                + "Thierry Viéville<br/>"
                + "L'INRIA<br/>"
                + "Cécille Picard<br/>"
                + "</center><br/>"
                + "Il est distribué sous les conditions de la licence CeCILL<br/>"
                + "(ou selon votre choix GNU GPL V3)"
                +"</html>"
                ),BorderLayout.CENTER);
        JPanel space=new JPanel();
        space.setSize(20, 10);
        JPanel space2=new JPanel();
        space2.setSize(20, 10);
        JPanel space3=new JPanel();
        space3.setSize(20, 10);
        this.add(space3,BorderLayout.NORTH);
        this.add(space2,BorderLayout.EAST);
        this.add(space,BorderLayout.WEST);

        JPanel p2 = new JPanel();
        JButton ok = new JButton("Ok");
        p2.add(ok);
        getContentPane().add(p2, BorderLayout.SOUTH);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
            }
        });

        //setSize(250, 150);
        pack();
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
}
