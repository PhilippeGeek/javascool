package org.javascool.widgets;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.javascool.macros.Macros;

/** Définit un bouton de start/stop avec affichage du temps de calcul.
 *
 * @author Philippe Vienne
 * @see <a href="StartStopButton.java.html">code source</a>
 * @serial exclude
 */
public abstract class StartStopButton extends JPanel {
  private static final long serialVersionUID = 1L;
  /** Le bouton de start/stop .*/
  private JButton startButton;
  /** L'affichage du temps d'exécution. */
  private JLabel execTime;

  // @bean
  public StartStopButton() {
    this.setOpaque(false);
    add(startButton = new JButton("Arrêter"));
    startButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                      new Thread(new Runnable() {
                                                   public void run() {
                                                     if(isStarting())
                                                       doStop();
                                                     else
                                                       doStart();
                                                   }
                                                 }
                                                 ).start();
                                    }
                                  }
                                  );
    add(execTime = new JLabel("  Temps d'exécution : 0 min 0 sec"));
    doStop();
  }
  /** Lancement du programme et du compteur. */
  private void doStart() {
    if(isRunning())
      stop();
    execTime.setText("  Temps d'exécution : 0 min 0 sec");
    startButton.setText("Arrêter");
    startButton.setIcon(Macros.getIcon("org/javascool/widgets/icons/stop.png"));
    revalidate();
    new Thread(new Runnable() {
                 @Override
                 public void run() {
                   for(int t = 0; isRunning(); t++) {
                     execTime.setText("  Temps d'exécution : " + t / 60 + " min " + t % 60 + " sec");
                     execTime.revalidate();
                     Macros.sleep(1000);
                   }
                   doStop();
                 }
               }
               ).start();
    start();
  }
  /** Indique que l'interface à affiché le lancement. */
  private boolean isStarting() {
    return "Arrêter".equals(startButton.getText());
  }
  /** Arrêt du programme et du compteur. */
  private void doStop() {
    if(isRunning())
      stop();
    startButton.setText("Exécuter");
    startButton.setIcon(Macros.getIcon("org/javascool/widgets/icons/play.png"));
    revalidate();
  }
  /** Cette méthode est appelée au lancement demandé par l'utilisateur. */
  abstract public void start();

  /** Cette méthode est appelée à l'arrêt demandé par l'utilisateur. */
  abstract public void stop();

  /** Cette méthode est appellée par le compteur pour déterminer si le programme est terminé.
   * <p>Par défaut l'indicateur est celui de l'appel à la méthode <tt>stop</tt>.</p>
   */
  public boolean isRunning() {
    return isStarting();
  }
}
