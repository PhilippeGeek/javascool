import javax.swing.JApplet;

public class Panel extends JApplet {
	private static ProgletPanel panel=new ProgletPanel();
	
	/** Constructeur par défaut */
	public Panel() {
		// Affiche panel en tant que panel principal de cette applet
		setContentPane(panel);
	}
	
	/** Récupérer le ProgletPanel affiché */
	public static ProgletPanel getPanel() {
		return panel;
	}
	
	/** Juste pour montrer que cette méthode peut être implémentée */
	public void init() {
	}
}
