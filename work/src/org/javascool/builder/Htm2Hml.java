package org.javascool.builder;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import org.javascool.tools.FileManager;
import org.javascool.tools.Xml2Xml;

/** Calculette de conversion de HTML en HML. */
public class Htm2Hml extends JPanel {
  private JTextArea htm, hml;
  public Htm2Hml() {
    setLayout(new BorderLayout());
    JToolBar b = new JToolBar();
    b.setFloatable(false);
    b.add(new JLabel("Traduction de [X]HTML en HML (coller le HTML à gauche et copié le HML à droite)"));
    b.add(Box.createHorizontalGlue());
    b.add(new JButton("[Traduire]") {
	{
	  addActionListener(new ActionListener() {
	      private static final long serialVersionUID = 1L;
	      @Override
		public void actionPerformed(ActionEvent e) {
		translate();
	      }});
	}});
    add(b, BorderLayout.NORTH);
    JPanel c = new JPanel();
    c.add(new JScrollPane(htm = new JTextArea(40, 64)));
    c.add(new JScrollPane(hml = new JTextArea(40, 64) {
	private static final long serialVersionUID = 1L;
	{
	  setBackground(new Color(200, 200, 200));
	  setEditable(false);
	}
      }));
    add(c, BorderLayout.CENTER);
    // Just to test
    htm.setText("<div>\n<div><img src='ok.png'></div>\n<hr>\n</div>");
  }
  private void translate() {
    hml.setText(Xml2Xml.html2xhtml(htm.getText()));
    try {
      hml.setText(Xml2Xml.run(hml.getText(), FileManager.load("org/javascool/builder/htm2hml.xslt")));
    } catch(Exception e) {
      System.out.println("Impossible de traduire le HTML en HML: "+e);
    }
  }
}
