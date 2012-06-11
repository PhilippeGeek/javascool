/*======================================================================
 * Translator.java			Lionel Avon	2012-06-09
 *							2012-06-10
 *
 * Fichier constituant la définition de la « proglet polyominos »
 * selon les spécifications de JavaScool
 *
 * D'après des exemples trouvés sur http://javascool.gforge.inria.fr
 *======================================================================
 */

package org.javascool.proglets.polyominos;

/*
 * Définit la traduction d'un code Jvs en code Java
 * pour manipuler la proglet « polyominos »
 * À DÉTRUIRE SI NON UTILISÉ
 *
 * @see <a href="Translator.java.html">code source</a>
 * @serial exclude
 */
public class Translator extends org.javascool.core.Translator {
    @Override
	public String getImports() {
	return "import org.dnsalias.avon.polyominos.* ;";
    }
    
    @Override
	public String translate(String code) {
	return code;
    }
}
