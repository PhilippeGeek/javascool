package org.javascool.builder;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javascool.tools.FileManager;
import org.javascool.tools.Xml2Xml;

/** Calculette de conversion de HTML en HML. */
public class Htm2Hml extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9069757673723961389L;
	private JTextArea htm, hml;

	public Htm2Hml() {
		setLayout(new BorderLayout());
		final JToolBar b = new JToolBar();
		b.setFloatable(false);
		b.add(new JLabel(
				"Traduction de [X]HTML en HML (coller le HTML à gauche et copié le HML à droite)"));
		b.add(Box.createHorizontalGlue());
		b.add(new JButton("[Traduire]") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 410907766230668800L;

			{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						translate();
					}
				});
			}
		});
		add(b, BorderLayout.NORTH);
		final JPanel c = new JPanel();
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
			hml.setText(Xml2Xml.run(hml.getText(),
					FileManager.load("org/javascool/builder/htm2hml.xslt")));
		} catch (final Exception e) {
			System.out.println("Impossible de traduire le HTML en HML: " + e);
		}
	}
}
