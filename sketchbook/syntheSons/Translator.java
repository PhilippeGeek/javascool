/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/
package org.javascool.proglets.syntheSons;

/** Defines a Jvs code to Java standard code translation for this proglet.
 * @see <a href="Translator.java.html">code source</a>
 * @serial exclude
 */
public class Translator extends org.javascool.core.Translator {
  @Override
  public String translate(String code) {
    // Translates the  @tone macro
    return code.replaceAll("@tone:(.*)\\s*;",
                           "org.javascool.proglets.syntheSons.Functions.tone = new org.javascool.proglets.syntheSons.SoundBit() { public double get(char c, double t) { return $1; } }; org.javascool.proglets.syntheSons.Functions.setNotes(\"16 a\");");
  }
}
