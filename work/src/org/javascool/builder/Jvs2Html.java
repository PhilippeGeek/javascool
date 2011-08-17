package org.javascool.builder;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.javasource.JavaSourceType;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.options.JavaSourceStyleEntry;
import de.java2html.util.RGB;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.javascool.tools.FileManager;

/** Convertit une portion de source Java ou Jvs en Html colorisé.
 * <p>Note: utilise une version patchée de <a href="http://www.java2html.de">java2html</a>, disponible dans <a href="http://code.google.com/p/javascool/source/browse/work/lib/jvs2html.jar">jvs2html.jar</a>, qui doit être dans le CLASSPATH.</p></p>
 *
 * @see <a href="Jvs2Html.java.html">code source</a>
 * @serial exclude
 */
public class Jvs2Html {
  // @factory
  private Jvs2Html() {}

  /** Convertit une portion de source Java ou JVs en Html colorisé..
   * @param code Le code Java ou Jvs.
   * @return Le code Html généré.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de l'éxecution.
   */
  public static String run(String code) {
    try {
      StringReader stringReader = new StringReader(code);
      JavaSource source = new JavaSourceParser().parse(stringReader);
      JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
      JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
      options.getStyleTable().put(JavaSourceType.KEYWORD, new JavaSourceStyleEntry(RGB.ORANGE, true, false));
      StringWriter writer = new StringWriter();
      converter.convert(source, options, writer);
      return writer.toString();
    } catch(IOException e) { throw new RuntimeException(e + " when converting: «" + code + "»");
    }
  }
  /** Lanceur de la conversion d'une portion de source Java ou Jvs en Html colorisé.
   * @param usage <tt>java org.javascool.tools.Jvs2Html input-file [output-file]</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length > 0)
      FileManager.save(usage.length > 1 ? usage[1] : "stdout:", run(FileManager.load(usage[0])));
  }
}
