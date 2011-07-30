package org.javascool.widget;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Définit une bouton de start/stop avec affichage du temps de calcul.
 * @author Philippe Vienne
 * @see <a href="StartStopButton.java.html">code source</a>
 * @serial exclude
 */
public class StartStopButton extends JPanel {
  private static final long serialVersionUID = 1L;
 
  /** Le boutton de start/stop .*/
  private JButton startButton;
  /** L'affichage du temps d'exécution. */
  private JLabel execTime;
  /** L'état du start/stop. */
  private boolean started = false;

  // @bean 
  public StartStopButton() {
    add(startButton = new JButton());
    add(execTime = new JLabel("  Temps d'éxecution : 0 min 0 sec"));
    doStop();
  }
  
  private void doStart() {
    if (started) 
      doStrop();
    started = true;
    startButton.setText("Arrêter");
    startButton.setIcon(org.javascool.tools.ConfUtils.getIcon("org/javascool/widget/icons/stop.png"));
    new Thread(new Runnable() {
	
	@Override
	  public void run() {
	  for (int t = 0; started; t++) {
	    execTime.setText("  Temps d'éxecution : "+t/60+" min "+t%60+" sec");
	    try { Thread.sleep(1000); } catch (Exception e) { }
	  }
	}
      }).start();
    start();
  }
  private void doStop() {
    if (started) {
      stop();
      started = false;
      startButton.setText("Exécuter");
      startButton.setIcon(org.javascool.tools.ConfUtils.getIcon("org/javascool/widget/icons/play.png"));
    }
  }

  /** Cette méthode est appelée au lancement demandé par l'utilisateur. */
  public void start() {}
  /** Cette méthode est appelée à l'arrêt demandé par l'utilisateur. */
  public void stop() {}
}
  
