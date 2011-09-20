package org.javascool.proglets.commSerie;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Définit un panneau graphique permettant de piloter une interface série.
 * <p><img src="http://javascool.gforge.inria.fr/documents/sketchbook/commSerie/screenshot.png" alt="screenshot"/></p>
 * @see <a href="SerialInterfacePanel.java.html">source code</a>
 * @serial exclude
 */
public class SerialInterfacePanel extends JPanel {
  private static final long serialVersionUID = 1L;

  /** Construit un panneau de contôle pour l'interface série donné.
   * @param serialInterface Interface série à piloter. Si null, crée une interface série.
   */
  public SerialInterfacePanel(SerialInterface serialInterface) {
    serial = serialInterface == null ? new SerialInterface() : serialInterface;
    setBorder(BorderFactory.createTitledBorder("Interface de contrôle d'un port série"));
    setLayout(new BorderLayout());
    add(new JPanel() {
	private static final long serialVersionUID = 1L;
	{
	  add(new JComboBox(SerialInterface.getPortNames()) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Nom du port"));
		setPreferredSize(new Dimension(150, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      serial.setName((String) ((JComboBox) e.getSource()).getSelectedItem());
		    }});
	      }});
	  add(new JComboBox(new Integer[] {19200, 9600, 4800, 2400, 1200, 600, 300}) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Débit en bauds"));
		setPreferredSize(new Dimension(150, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      serial.setRate((Integer) ((JComboBox) e.getSource()).getSelectedItem());
		    }});
	      }});
	  add(new JComboBox(new String[] {"aucun", "pair", "impair"}) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Bit de parité"));
		setPreferredSize(new Dimension(150, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      String v = (String) ((JComboBox) e.getSource()).getSelectedItem();
		      serial.setParity("pair".equals(v) ? 'E' : "impair".equals(v) ? 'O' : 'N');
		    }});
	      }});
	  add(new JComboBox(new Integer[] {8, 7}) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Taille du mot"));
		setPreferredSize(new Dimension(150, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      serial.setSize((Integer) ((JComboBox) e.getSource()).getSelectedItem());
		    }});
	      }});
	  add(new JComboBox(new Double[] {1.0, 1.5, 2.0}) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Bits de stop"));
		setPreferredSize(new Dimension(150, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      serial.setStop((Double) ((JComboBox) e.getSource()).getSelectedItem());
		    }});
	      }});
	}}, BorderLayout.NORTH);
    add(new JPanel() { 
	private static final long serialVersionUID = 1L;
	{
	  add(new JButton("Ouvrir") {
	      private static final long serialVersionUID = 1L;
	      {
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      JButton b = (JButton) e.getSource();
		      if("Ouvrir".equals(b.getText())) {
			b.setText("Fermer");
			System.out.println("Opening serial interface : "+serial);
			serial.open();
		      } else {
			b.setText("Ouvrir");
			serial.close();
		      }
		    }});
	      }});
	  add(write = new JTextField(24) {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Envoyer un octet (héxa):"));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      try {
			int b = Integer.parseInt(((JTextField) e.getSource()).getText(), 16);
			serial.write(b);
			System.out.println("Sending : '0x"+b+"'");
		      } catch(NumberFormatException u) {
			write.setText("Illegal format !");
		      }
		    }});
	      }});
	  add(new JPanel() {
	      private static final long serialVersionUID = 1L;
	      {
		setBorder(BorderFactory.createTitledBorder("Lire un octet (héxa):"));
		add(new JButton("Lire :") {
		    private static final long serialVersionUID = 1L;
		    {
		      addActionListener(new ActionListener() {
			  private static final long serialVersionUID = 1L;
			  @Override
			    public void actionPerformed(ActionEvent e) {
			    int v = serial.read();
			    read.setText(v == -1 ? "rien à lire" : "'0x"+Integer.toHexString(v)+"'");
			  }});
		    }});
		add(read = new JTextField(12) {  
		    private static final long serialVersionUID = 1L;
		    {
		      setEditable(false);
		    }
		  });
	      }});
	}}, BorderLayout.CENTER);
    // Permet d'afficher les messages de la console dans l'interface.  
    if (!org.javascool.widgets.Console.isInstanced()) {
      JPanel c = org.javascool.widgets.Console.getInstance();
      c.setPreferredSize(new Dimension(800, 200));
      add(c, BorderLayout.SOUTH);
    }  
  }
  /** 
   * @see #SerialInterfacePanel(SerialInterface)
   */
  public SerialInterfacePanel() {
    this(null);
  }
  private SerialInterface serial;
  private JTextField write, read;

  /** Renvoie la liste des des noms de ports séries disponibles ce qui teste l'installation des librairies. 
   * @param usage <tt>java -cp javascool-proglets.jar org.javascool.proglets.commSerie.SerialInterfacePanel</tt>
   */  
  public static void main(String[] usage) {
    new org.javascool.widgets.MainFrame().reset("Interface série", 800, 400, new SerialInterfacePanel());
  }
}



