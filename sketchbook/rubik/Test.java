package org.javascool.proglets.rubik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import javax.swing.JFrame;


public class Test {

  public static void main(String[] args) throws IOException, InterruptedException {
    JFrame frame = new JFrame("Rubik's");
    frame.add(new Panel());

    frame.setSize(500, 500);
    frame.setFocusable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    for(;;) {
      String s=in.readLine();
      try {
        Method m = Functions.class.getMethod(s);
        m.invoke(null);
      } catch (NoSuchMethodException e) {
        System.err.println("No such action");
      } catch (Exception e) {
        throw new AssertionError(e);
      } 
    }

  }
}
