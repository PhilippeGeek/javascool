package org.javascool.proglets.algoDeMath;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ProgletPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public ProgletPanel() {
      super(new BorderLayout());
      add(scope = new CurveOutput(), BorderLayout.CENTER);
      JPanel input = new JPanel(new BorderLayout());
      input.add(inputX = new NumberInput("X"), BorderLayout.NORTH);
      input.add(inputY = new NumberInput("Y"), BorderLayout.SOUTH);
      Runnable run1 = new Runnable() {
        public void run() {
          inputX.setValue(scope.getReticuleX());
          inputY.setValue(scope.getReticuleY());
        }
      };
      Runnable run2 = new Runnable() {
        public void run() {
          scope.setReticule(inputX.getValue(), inputY.getValue());
        }
      };
      scope.setRunnable(run1);
      inputX.setRunnable(run2);
      inputY.setRunnable(run2);
      add(input, BorderLayout.SOUTH);
      reset(1, 1);
    }
    public CurveOutput scope;
    public NumberInput inputX, inputY;

    /** Resets the scope display and set scales.
     * @param Xscale Horizontal scale.
     * @param Yscale Vertical scale.
     */
    public void reset(double Xscale, double Yscale) {
      inputX.setScale(-Xscale, Xscale, 0.001);
      inputY.setScale(-Yscale, Yscale, 0.001);
      scope.reset(0, 0, Xscale, Yscale);
    }
    /** Resets the scope display and set scales.
     * @param Xmin Horizontal scale.
     * @param Xmin Horizontal scale.
     * @param Ymin Vertical scale.
     * @param Ymax Vertical scale.
     */
    public void reset(double Xmin, double Xmax, double Ymin, double Ymax) {
      inputX.setScale(Xmin, Xmax, 0.001);
      inputY.setScale(Ymin, Ymax, 0.001);
      scope.reset((Xmin + Xmax) / 2, (Ymin + Ymax) / 2, (Xmax - Xmin) / 2, (Ymax - Ymin) / 2);
    }
  }
