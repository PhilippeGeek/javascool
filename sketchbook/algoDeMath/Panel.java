package org.javascool.proglets.algoDeMath;
import static org.javascool.tools.Macros.*;
import static org.javascool.proglets.algoDeMath.Functions.*;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import org.javascool.widgets.CurveOutput;
import org.javascool.widgets.NumberInput;

public class Panel extends JPanel {
  private static final long serialVersionUID = 1L;

  // @bean
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
  /** Tracé de courbes. */
  public CurveOutput scope;
  /** Entrées de valeurs numériques. */
  public NumberInput inputX, inputY;
}
