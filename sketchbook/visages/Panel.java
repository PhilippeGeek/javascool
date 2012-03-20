package org.javascool.proglets.visages;

import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Color;

import org.javascool.widgets.NumberInput;
import org.javascool.macros.Macros;

/** Définit une proglet javascool qui permet d'implémenter un programme de reconnaissance de visage.
 *
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;

  // @bean
  public Panel() {
    super(new BorderLayout());
    JLayeredPane pane = new JLayeredPane();
    pane.setPreferredSize(new Dimension(500, 700));
    pane.setAutoscrolls(true);
    pane.setBackground(Color.yellow);
    for (int n=1;n<=7;n++){
      String nom = "visage"+n;
      JLabel fig = new JLabel(nom);
      fig.setIcon(Macros.getIcon("org/javascool/proglets/visages/visage"+n+".jpg"));
      fig.setBounds(100*(n-1), 0, 100, 80);
	
      pane.add(fig, new Integer(0), 0);}
		
		
    JLabel fig = new JLabel("Visage moyen");
    fig.setIcon(Macros.getIcon("org/javascool/proglets/visages/image_moyenne.jpg"));
    fig.setBounds(250, 105, 180, 80);
			
    pane.add(fig, new Integer(1), 0);
		
		
    JLabel fig1 = new JLabel("Visages normalisés");
    fig1.setBounds(0, 180, 180, 20);
    pane.add(fig1, new Integer(1), 0);
    for (int n=0;n<7;n++){

      JLabel fig2 = new JLabel();
      fig2.setIcon(Macros.getIcon("org/javascool/proglets/visages/image_normalisee"+n+".jpg"));
      fig2.setBounds(100*n, 200, 100, 80);
      pane.add(fig2, new Integer(2), 0);}
		 
    JLabel fig4 = new JLabel("Visages propres");
    fig4.setBounds(0, 300, 180, 20);
    pane.add(fig4, new Integer(1), 0);		 
		 
		 
    for (int n=0;n<3;n++){
      JLabel fig3 = new JLabel();
      fig3.setIcon(Macros.getIcon("org/javascool/proglets/visages/visage_propre"+n+".jpg"));
      fig3.setBounds(100*n, 320, 100, 80);
					
      pane.add(fig3, new Integer(2), 0);}
    JLabel fig5 = new JLabel("Visages reconstruits");
    fig5.setBounds(0, 420, 180, 20);
    pane.add(fig5, new Integer(1), 0);		 
			 		 
			 		 
    for (int n=0;n<7;n++){
      JLabel fig6 = new JLabel();
      fig6.setIcon(Macros.getIcon("org/javascool/proglets/visages/image_reconstruite"+n+".jpg"));
      fig6.setBounds(100*n, 440, 100, 80);
      pane.add(fig6, new Integer(1), 0);}	 
    JLabel fig7 = new JLabel("Les visages suivants seront -ils reconnus ?");
    fig7.setBounds(0, 540, 400, 20);
    pane.add(fig7, new Integer(1), 0);				  
			 
    JLabel fig9 = new JLabel();
    fig9.setIcon(Macros.getIcon("org/javascool/proglets/visages/visage31.jpg"));
    fig9.setBounds(100, 560, 100, 80);
    pane.add(fig9, new Integer(1), 0);
    JLabel fig8 = new JLabel();
    fig8.setIcon(Macros.getIcon("org/javascool/proglets/visages/visage8.jpg"));
    fig8.setBounds(200, 560, 100, 80);
    pane.add(fig8, new Integer(1), 0);
		
    add(pane, BorderLayout.SOUTH);
  }
	
}
	
