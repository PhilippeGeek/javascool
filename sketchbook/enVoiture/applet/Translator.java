package org.javascool.proglets.enVoiture;

/** Définit le traducteur de langage pour cette proglet.
 * 
 * @see <a href="Translator.java.html">code source</a>
 * @serial exclude
 */
public class Translator extends org.javascool.core.Translator {
  @Override
  public String translate(String code) {
    return code.replaceAll("\\s(addSpot|getClosestSpot|removeSpot|addRoad|removeRoad|isRoad)\\(", " enVoiture.$1(");
  }
}
