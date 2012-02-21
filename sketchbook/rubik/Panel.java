package org.javascool.proglets.rubik;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel {

  private static final long serialVersionUID = 5559872489901631705L;

  private static void add(Box panel, String title, ActionListener action) {
    JButton button = new JButton(title);
    button.addActionListener(action);
    panel.add(button);
  }

  public Panel() {
    super(new BorderLayout());
    setOpaque(false);
  }

  private boolean inited;

  @Override
  protected void paintComponent(Graphics graphics) {
    if (inited)
      return;
    init();
    revalidate();
    repaint();
    inited = true;
  }

  public void init() {
    super.add(RubikAnimator.getCube(50));
    Box box = Box.createHorizontalBox();
    add(box, "\u2191", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.bringToFront(Face.BOTTOM, true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    box.add(Box.createHorizontalGlue());
    add(box, "\u2193", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.bringToFront(Face.TOP, true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    box.add(Box.createHorizontalGlue());
    add(box, "\u2190", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.bringToFront(Face.RIGHT, true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    box.add(Box.createHorizontalGlue());
    add(box, "\u2192", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.bringToFront(Face.LEFT, true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    box.add(Box.createHorizontalGlue());
    add(box, "\u21BA", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.antiRotate(true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    box.add(Box.createHorizontalGlue());
    add(box, "\u21BB", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          RubikAnimator.rotate(true);
        } catch (InterruptedException e1) {
          throw new AssertionError(e1);
        }
      }
    });
    add(box, BorderLayout.NORTH);
  }

}
