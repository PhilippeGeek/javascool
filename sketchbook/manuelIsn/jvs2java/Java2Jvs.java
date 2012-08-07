import org.javascool.tools.FileManager;

/** Définit le mécanisme de traduction d'un code Java utilisant la classe Isn en Jvs.
 * La classe <a href="https://who.rocq.inria.fr/Gilles.Dowek/Isn">
 * @see <a href="Java2Jvs.java.html">code source</a>
 * @serial exclude
 */
public class Java2Jvs {
  // @factory
  private Java2Jvs() {}

  /** Traduit un texte Java en Jvs.
   * @param javaText Le texte Java
   * @return Le texte Jvs.
   */
  public static String translate(String javaText) {
    // Desencapsule la construction "class * {"
    int i0 = javaText.indexOf("public")
    javaText = javaText.replace("", "void main()");
    javaText = javaText.replace("public static void main(String[] args)", "void main()");
    return javaText;
  }

  /** Lanceur de la conversion Java en Jvs.
   * @param usage <tt>java Java2Jvs input-file [output-file]</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length > 0) {
      FileManager.save(usage.length > 1 ? usage[1] : "stdout:", translate(FileManager.load(usage[0])));
    }
  }
}

  
