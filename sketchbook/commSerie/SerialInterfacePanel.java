package org.javascool.proglets.commSerie;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

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
		setPreferredSize(new Dimension(120, 70));
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
		setBorder(BorderFactory.createTitledBorder("Débit en b./s."));
		setPreferredSize(new Dimension(120, 70));
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
		setPreferredSize(new Dimension(120, 70));
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
		setPreferredSize(new Dimension(120, 70));
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
		setPreferredSize(new Dimension(120, 70));
		addActionListener(new ActionListener() {
		    private static final long serialVersionUID = 1L;
		    @Override
		      public void actionPerformed(ActionEvent e) {
		      serial.setStop((Double) ((JComboBox) e.getSource()).getSelectedItem());
		    }});
	      }});
	}}, BorderLayout.NORTH);
    add(new JButton() {
	private static final long serialVersionUID = 1L;
	private static final String open = "OUVRIR le port", close = "FERMER le port";
	{
	  setText(open);
	  addActionListener(new ActionListener() {
	      private static final long serialVersionUID = 1L;
	      @Override
		public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(open.equals(b.getText())) {
		  b.setText(close);
		  System.out.println("Opening serial interface : "+serial);
		  serial.open();
		} else {
		  b.setText(open);
		  serial.close();
		}
	      }});
	}}, BorderLayout.WEST);
    add(new Box(BoxLayout.X_AXIS) { 
	private static final long serialVersionUID = 1L;
	{
	  add(new Box(BoxLayout.Y_AXIS) { 
	      private static final long serialVersionUID = 1L;
	      {
		add(writeChar = new JTextField(12) {
		    private static final long serialVersionUID = 1L;
		    {
		      setBorder(BorderFactory.createTitledBorder("Envoyer un caractère :"));
		      addKeyListener(new KeyListener() {
			private static final long serialVersionUID = 1L;
			@Override
			  public void keyPressed(KeyEvent e) { }
			  public void keyReleased(KeyEvent e) { }
			  public void keyTyped(KeyEvent e) {
			    char c = e.getKeyChar();
			    writeHexa.setText(writeHexa.getText()+" 0x"+Integer.toString((int) c, 16));
			    serial.write(c);
			  }
			});
		    }});
		add(writeHexa = new JTextField(12) {
		    private static final long serialVersionUID = 1L;
		    {
		      setBorder(BorderFactory.createTitledBorder("Code ASCII du caractère :"));
		      setEditable(false);
		    }
		  });
		add(new JButton("Effacer") {
		    private static final long serialVersionUID = 1L;
		    {
		      addActionListener(new ActionListener() {
			  private static final long serialVersionUID = 1L;
			  @Override
			  public void actionPerformed(ActionEvent e) {
			    writeChar.setText("");
			    writeHexa.setText("");
			  }
			});
		    }});
	      }});
	  add(new Box(BoxLayout.Y_AXIS) { 
	      private static final long serialVersionUID = 1L;
	      {
		add(readChar = new JTextField(12) {
		    private static final long serialVersionUID = 1L;
		    {
		      setBorder(BorderFactory.createTitledBorder("Caractère reçu :"));
		      setEditable(false);
		    }});
		add(readHexa = new JTextField(12) {
		    private static final long serialVersionUID = 1L;
		    {
		      setBorder(BorderFactory.createTitledBorder("Code ASCII du caractère :"));
		      setEditable(false);
		    }});
		add(new JButton("Effacer") {
		    private static final long serialVersionUID = 1L;
		    {
		      addActionListener(new ActionListener() {
			  private static final long serialVersionUID = 1L;
			  @Override
			  public void actionPerformed(ActionEvent e) {
			    readChar.setText("");
			    readHexa.setText("");
			  }
			});
		    }});
	      }});
	}}, BorderLayout.CENTER);
    serial.setRunnable(new Runnable() { public void run() {
      char c = (char) serial.read();
      readChar.setText(readChar.getText()+" "+c);
      readHexa.setText(readHexa.getText()+" 0x"+Integer.toString(c, 16));
    }});
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
  private JTextField writeChar, writeHexa, readChar, readHexa;

  /** Renvoie l'interface série, pour pouvoir accéder à ses fonctions. */
  public SerialInterface getSerialInterface() {
    return serial;
  }

  /** Renvoie la liste des des noms de ports séries disponibles ce qui teste l'installation des librairies. 
   * @param usage <tt>java -cp javascool-proglets.jar org.javascool.proglets.commSerie.SerialInterfacePanel</tt>
   */  
  public static void main(String[] usage) {
    new org.javascool.widgets.MainFrame().reset("Interface série", 800, 600, new SerialInterfacePanel());
  }
}



