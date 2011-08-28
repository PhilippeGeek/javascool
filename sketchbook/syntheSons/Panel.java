/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/

package org.javascool.proglets.syntheSons;
import static org.javascool.proglets.syntheSons.Functions.*;

/** Defines the GUI of this proglet.
 *
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends SoundBitPanel {
  private static final long serialVersionUID = 1L;
  public SoundBit sound = new NotesSoundBit() {
    @Override
    public double get(char channel, double time) {
      return Functions.tone == null ? Math.sin(2 * Math.PI * time) : Functions.tone.get(channel, time);
    }
  };
  // @bean
  public Panel() {
    reset(sound, 'l');
    sound.reset("16 A");
  }
  /** Démo de la proglet. */
  public void start() {
    test(new SoundBit() {
           @Override
           public double get(char c, double t) {
             return sns(t);
           }
         }
         );
    test(new SoundBit() {
           @Override
           public double get(char c, double t) {
             return 0.5 * sqr(t);
           }
         }
         );
    test(new SoundBit() {
           @Override
           public double get(char c, double t) {
             return 0.8 * tri(t) + 0.2 * noi(t);
           }
         }
         );
    test(new SoundBit() {
           @Override
           public double get(char c, double t) {
             return 0.3 * sqr(t / 2) * sns(t) + 0.3 * sns(2 * t) + 0.3 * tri(3 * t);
           }
         }
         );
    setNotes("e5 e5b e5 e5b e5 e5b e5 b d5 c5 4 a | 1 h c e a 4 b | 1 h e g g# 4 a");
    play();
  }
  private void test(SoundBit sound) {
    Functions.tone = sound;
    setNotes("16 a");
    play();
  }
}
