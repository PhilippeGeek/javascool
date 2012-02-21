package org.javascool.proglets.rubik;

import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.media.j3d.Behavior;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.media.j3d.WakeupOnElapsedTime;

class ActionBehavior extends Behavior {

  private final RubikInterpolator rubikInterpolator;

  private final ComponentEvent wakeupEvent;

  private final BlockingQueue<Move> actions;
  // private final ConcurrentLinkedQueue<Move> actionList = new
  // ConcurrentLinkedQueue<Move>();
  private final ArrayBlockingQueue<Move> actionList = new ArrayBlockingQueue<Move>(
      1);
  // private Action action;
  private final Alpha alpha = new Alpha(500);
  private final WakeupCriterion defaultWakeupCriterion;
  private final WakeupCriterion passiveWakeupCriterion;

  private int prevAlphaCountValue;

  private final Canvas3D canvas;

  ActionBehavior(RubikInterpolator rubikInterpolator, int fps, Canvas3D canvas,
      BlockingQueue<Move> actions) {
    if (fps == 0)
      defaultWakeupCriterion = new WakeupOnElapsedFrames(0);
    else
      defaultWakeupCriterion = new WakeupOnElapsedTime(1000 / fps);

    this.actions = actions;
    this.rubikInterpolator = rubikInterpolator;
    this.canvas = canvas;
    this.wakeupEvent = new ComponentEvent(canvas,
        ComponentEvent.COMPONENT_MOVED);
    this.passiveWakeupCriterion = new WakeupOnAWTEvent(wakeupEvent.getID());
  }

  @Override
  public void initialize() {
    launchWaitThread();
    wakeupOn(defaultWakeupCriterion);
  }

  private void launchWaitThread() {
    Runnable r = new Runnable() {

      @Override
      public void run() {
        try {
          for (;;) {
            if (actions.peek() == null)
              synchronized (alpha) {
                alpha.setLength(2000);
              }
            Move a = actions.take();
            actionList.put(a);
            EventQueue.invokeAndWait(new Runnable() {

              @Override
              public void run() {
                canvas.dispatchEvent(wakeupEvent);
              }
            });
          }
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        } catch (InvocationTargetException e) {
          throw new AssertionError(e);
        }

      }
    };
    Thread t = new Thread(r);
    t.setDaemon(true);
    t.start();
  }

  void pause() {
    synchronized (alpha) {
      alpha.pause();
    }
  }

  float resume() {
    synchronized (alpha) {
      if (alpha.isPaused()) {
        alpha.reset();
        prevAlphaCountValue = 0;
      }
      return alpha.status();
    }
  }

  @Override
  public void processStimulus(@SuppressWarnings("rawtypes") Enumeration criteria) {
    if (actionList.peek() == null) {
      pause();
      wakeupOn(passiveWakeupCriterion);
      return;
    }

    float status;
    synchronized (alpha) {
      int length = 2000 / (actions.size() + 1);
      if (length < alpha.getLength())
        alpha.setLength(length);
      status = resume();
    }
    int loopCount = Alpha.count(status);
    // if (value != prevAlphaValue) {
    if (loopCount > prevAlphaCountValue) {
      Move action = actionList.remove();
      action.end(rubikInterpolator);
      loopCount--;
      while (loopCount > prevAlphaCountValue) {
        Move newAction = actionList.poll();
        if (newAction != null) {
          newAction.end(rubikInterpolator);
        } else {
          break;
        }
        loopCount--;
      }
      Move newAction = actionList.peek();
      if (newAction != null) {
        newAction.step(rubikInterpolator, Alpha.value(status));
        wakeupOn(defaultWakeupCriterion);
      } else {
        pause();
        wakeupOn(passiveWakeupCriterion);
      }
    } else {
      Move action = actionList.element();
      if (action != null) {
        action.step(rubikInterpolator, Alpha.value(status));
        wakeupOn(defaultWakeupCriterion);
      } else {
        pause();
        wakeupOn(passiveWakeupCriterion);
      }
    }
    prevAlphaCountValue = Alpha.count(status);
    // }
  }

  private final Transform3D[][][] transforms = new Transform3D[2][2][2];
  {
    for (int x = 0; x <= 1; x++)
      for (int y = 0; y <= 1; y++)
        for (int z = 0; z <= 1; z++) {
          transforms[x][y][z] = new Transform3D();
        }
  }

}
