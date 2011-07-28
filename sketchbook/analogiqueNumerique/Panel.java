import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JApplet;

public class Panel extends JApplet {
	ProgletPanel panel;
	
	public void init() {
		this.panel=new ProgletPanel();
		Functions.panel=new ProgletPanel();
		setContentPane();
	}
}
