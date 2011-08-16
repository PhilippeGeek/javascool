/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
*******************************************************************************/
package org.javascool.proglets.syntheSons;

/** Définit une traduction d'un code Jvs en code Java standard. */
public class Translator extends org.javascool.core.Translator {
  @Override
  public String getImports() {
    return "";
  }
  @Override
  public String translate(String code) {
    // Translates the  @tone macro
    return code.replaceAll("@tone:(.*)\\s*;",
                           "org.javascool.proglets.syntheSons.Functions.tone = new org.javascool.proglets.syntheSons.SoundBit() { public double get(char c, double t) { return $1; } }; org.javascool.proglets.syntheSons.Functions.syntheSet(\"16 a\");");
  }
}
