/**************************************************************
* Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
**************************************************************/
package org.javascool.core;

/** Implémente le mécanisme de base de traduction d'un code Jvs en code Java standard.
 * <p>Les erreurs de traduction sont affichées dans la console.</p>
 *
 * @see <a href="Jvs2Java.java.html">source code</a>
 * @serial exclude
 */
public class Jvs2Java extends Translator {
  // @bean
  public Jvs2Java() {}

  /** Définit un mécanisme spécifique de traduction en plus du mécanisme standard.
   * @param progletTranslator Le mécanisme de traduction spécifique d'une proglet donnée.
   * @return Cet objet, permettant de définir la construction <tt>Jvs2Java translator = new Jvs2Java().setProgletTranslator(..)</tt>.
   */
  public Jvs2Java setProgletTranslator(Translator progletTranslator) {
    // @bean-parameter(Translator, progletTranslator, w);
    this.progletTranslator = progletTranslator;
    return this;
  }
  private Translator progletTranslator = null;

  /** Définit le nom complet du package de la proglet pour ce mécanismes de traduction.
   * @param progletPackageName Le nom complet du package de la proglet.
   * @return Cet objet, permettant de définir la construction <tt>Jvs2Java translator = new Jvs2Java().setProgletPackageName(..)</tt>.
   */
  public Jvs2Java setProgletPackageName(String progletPackageName) {
    // @bean-parameter(String, progletPackageName, w);
    this.progletPackageName = progletPackageName;
    return this;
  }
  private String progletPackageName = null;

  @Override
  public String translate(String jvsCode) {
    String text = jvsCode.replace((char) 160, ' ');
    // Ici on ajoute
    if(!text.replaceAll("[ \n\r\t]+", " ").matches(".*void[ ]+main[ ]*\\([ ]*\\).*")) {
      if(text.replaceAll("[ \n\r\t]+", " ").matches(".*main[ ]*\\([ ]*\\).*")) {
        System.out.println("Attention: il faut mettre \"void\" devant \"main()\" pour que le programme puisque se compiler");
        text = text.replaceFirst("main[ ]*\\([ ]*\\)", "void main()");
      } else {
        System.out.println("Attention: il faut un block \"void main()\" pour que le programme puisque se compiler");
        text = "\nvoid main() {\n" + text + "\n}\n";
      }
    }
    String[] lines = text.split("\n");
    StringBuilder head = new StringBuilder();
    StringBuilder body = new StringBuilder();
    // Here is the translation loop
    {
      int i = 1;
      // Copies the user's code
      for(String line : lines) {
        if(line.matches("^\\s*(import|package)[^;]*;\\s*$")) {
          head.append(line);
          body.append("//").append(line).append("\n");
          if(line.matches("^\\s*package[^;]*;\\s*$"))
            System.out.println("Attention: on ne peut normalement pas définir de package Java en JavaScool\n le programme risque de ne pas s'exécuter correctement");
        } else
          body.append(translateOnce(line, i)).append("\n");
        i++;
      }
      // Imports proglet's static methods
      head.append("import static java.lang.Math.*;");
      head.append("import static org.javascool.tools.Macros.*;");
      if(progletPackageName != null)
        head.append("import static ").append(progletPackageName).append(".Functions.*;");
      if(progletTranslator != null)
        head.append(progletTranslator.getImports());
      // Declares the proglet's core as a Runnable in the Applet
      uid++;
      head.append("public class JvsToJavaTranslated").append(uid).append(" implements Runnable{");
      head.append("  private static final long serialVersionUID = ").append(uid).append("L;");
      head.append("  public void run() {");
      head.append("   try{ main(); } catch(Throwable e) { ");
      head.append("     System.out.println(\"\\n-------------------\\nErreur lors de l'exécution de la proglet\\n\"+e+\"\\n-------------------\\n\");}");
      head.append("}");
    }
    // Ici il y a le grospatch pour jeux2D
    String finalBody = body.toString().replaceAll("((^|\n)([ \t]*)(?!((public|private|protected)([ \t]+)))([A-Za-z0-9_]+)([ ]+)([A-Za-z0-9_]+)\\(([^()]*)\\)([ ]*)\\{([ \t]*)(\n|$))", "public $1").replaceAll("(^|[\n\t ])for[\n\t ]*\\(([A-Za-z0-9_.]+)[\n\t ]+([A-Za-z0-9_.]+)[\n\t ]+in[\n\t ]+([A-Za-z0-9_.]+)[\n\t ]*\\)[\n\t ]*\\{", "for (int tmpsystemi=0; tmpsystemi<$4.size(); tmpsystemi++) {$2 $3=($2)($4.get(tmpsystemi));");
    return head.toString() + finalBody + "}";
  }
  /** Translate a jvs line to a java line.
   * Translate with replace
   * @param line The text line to translate.
   * @param lineNumber The line numbr to translate.
   * @return The java translated text.
   */
  private String translateOnce(String line, int lineNumber) {
    // Translates the while statement with sleep
    line = line.replaceAll("(while.*\\{)", "$1 sleep(1);");
    line = line.replaceAll("(.*[^a-zA-Z0-9_])([a-zA-Z0-9_]+[ \t=]*\\.getProperty[ \t=]*\\()[ \t=]*([a-zA-Z0-9_]+)[ \t=]*,([^)]*\\))(.*)", "$1(($3)$2$4)$5");
    line = line.replaceAll("\\(int\\)", "(Integer)");
    line = line.replaceAll("\\(double\\)", "(Double)");
    line = line.replaceAll("([A-Za-z0-9_\\-]+)::([A-Za-z0-9_\\-]+)", "org.javascool.proglets.$1.Functions.$2");
    return "    " + line;
  }
  /** Renvoie le nom de la dernière classe Java générée lors de la traductions. */
  public String getClassName() {
    return "JvsToJavaTranslated" + uid;
  }
  // Counter used to increment the serialVersionUID in order to reload the different versions of the class
  private static int uid = 0;

  /** Lanceur de la conversion Jvs en Java.
   * @param usage <tt>java org.javascool.core.Jvs2Java input-file [output-file]</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length > 0)
      org.javascool.tools.StringFile.save(usage.length > 1 ? usage[1] : "stdout:", new Jvs2Java().translate(org.javascool.tools.StringFile.load(usage[0])));
  }
}
