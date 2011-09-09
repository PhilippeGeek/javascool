package org.javascool.proglets.exploSonore;

/** Définit le traducteur de langage pour cette proglet.
 * 
 * @see <a href="Translator.java.html">code source</a>
 * @serial exclude
 */
public class Translator extends org.javascool.core.Translator {
  @Override
  public String translate(String code) {
    return code.replaceAll("\\s(playSignal|playRecord|playStop)\\(", " exploSonore.$1(");
  }
}
