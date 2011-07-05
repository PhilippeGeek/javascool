package org.javascool.tests;

import org.javascool.pml.Pml;

/** Read/write test of Pml data structures. */
public class PmlTst {
  /** Runs the test.
     * * Usage: <tt>java org.javascool.test.PmlTst</tt>
     * @param argv Not used.
     */
  public static void main(String argv[]) {
    tst("{proglet {name {\"Jeux\"}} {author {\"Guillaume Matheron\"}} {package{\"org.javascool.proglet.game\"}}}", "xml");
  }
  // Read and then write in normalized format
  private static void tst(String string, String format) {
    System.out.println("in>"+string);
    Pml pml = new Pml().reset(string, format);
    System.out.println("out>"+pml);
  }
}
