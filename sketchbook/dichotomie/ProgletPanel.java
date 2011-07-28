import org.javascool.tools.Macros;
import org.javascool.tools.Utils;

// Used to define the gui
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.Dimension;

// Used to define an icon/label
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;

// Used to define a button
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;


// This defines the panel to display
public class ProgletPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public ProgletPanel() {
    super(new BorderLayout());
    setBackground(Color.WHITE);
    // Adds the background icon
    JLayeredPane book = new JLayeredPane();
    book.setPreferredSize(new Dimension(540, 350));
    add(book);
    JLabel icon = new JLabel();
    icon.setBounds(10, 0, 540, 350);
    icon.setIcon(Utils.getIcon("org/javascool/proglets/dichotomie/dicho_background.png"));
    System.err.println("icon");
    book.add(icon, new Integer(1), 0);
    // Adds the label and flag
    name = new JLabel();
    name.setBounds(90, 50, 150, 100);
    book.add(name, new Integer(2), 0);
    flag = new JLabel();
    flag.setBounds(340, 100, 200, 100);
    book.add(flag, new Integer(2), 1);
    // Adds the prev/next buttons and page count label
    JPanel tail = new JPanel();
    add(tail, BorderLayout.SOUTH);
    JButton prev = new JButton("<-");
    tail.add(prev);
    prev.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent e) {
                               show(--current);
                             }
                           }
                           );
    JButton next = new JButton("->");
    tail.add(next);
    next.addActionListener(new ActionListener() {
                             public void actionPerformed(ActionEvent e) {
                               show(++current);
                             }
                           }
                           );
    tail.add(new JLabel("       "));
    num = new JLabel();
    tail.add(num);
    show(63);
  }
  /** Affiche une page.
   * @param page L'index de la page de 0 à getSize() exclu.
   */
  public void show(int page) {
    if(page < 0)
      page = 0;
    if(page >= Functions.dichoLength())
      page = Functions.dichoLength() - 1;
    current = page;
    num.setText("" + page);
    name.setText("<html><h2>" + Functions.dicho[page][0] + "</h2></html>");
    flag.setIcon(Utils.getIcon("org/javascool/proglets/dichotomie/" + Functions.dicho[page][1]));
    Macros.sleep(150);
  }
  private JLabel name, flag, num;
  private int current;
}
