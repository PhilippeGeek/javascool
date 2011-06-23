/**************************************************************
 * Philippe VIENNE, Copyright (C) 2011.  All rights reserved. *
 **************************************************************/
package org.javascool.jvs;

// used for the translation
import java.io.File;

// Used to register the proglets
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Used to interface with the j5/j6 compiler

// Used to load class
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.javascool.tools.Console;

/** This factory defines how a Jvs code is translated into a Java code and compiled.
 * The goal of the Jvs syntax is to ease the syntax when starting to program in an imperative language, like Java.
 * <p>- This factory calls the java compiler as in the jdk5 (and earlier) case, but still works with jdk6. It is designed to be used in standalone mode.</p>
 * @see <a href="Jvs2Java.java.html">source code</a>
 * @serial exclude
 */
public class Jvs2Java {

    private Jvs2Java() {
    }

    /** Translates a Jvs code source.
     * @param path The file path to translate: A <tt>.jvs</tt> file is read and the corresponding <tt>.java</tt> file is written.
     * @param proglet The proglet to use. Default is to make all proglets usable.
     *
     * @throws RuntimeException if an I/O exception occurs during file translation.
     * @throws IllegalArgumentException If the Java class name is not valid.
     */
    public static String translate(String jvsCode) {
        String text = jvsCode;
        String javaCode = "";
        // Here we check and correct a missing "void main()"
        if (!text.replaceAll("[ \n\r\t]+", " ").matches(".*void[ ]+main[ ]*\\([ ]*\\).*")) {
            if (text.replaceAll("[ \n\r\t]+", " ").matches(".*main[ ]*\\([ ]*\\).*")) {
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
            // Copies the user's code
            for (String line : lines) {
                if (line.matches("^\\s*(import|package)[^;]*;\\s*$")) {
                    head.append(line);
                    body.append("//").append(line).append("\n");
                    if (line.matches("^\\s*package[^;]*;\\s*$")) {
                        System.out.println("Attention: on ne peut normallement pas définir de package Java en JavaScool\n le programme risque de ne pas s'exécuter correctement");
                    }
                } else {
                    body.append(translateOnce(line)).append("\n");
                }
            }
            // Imports proglet's static methods
            head.append("import static java.lang.Math.*;");
            head.append("import java.util.List;");
            head.append("import java.util.ArrayList;");
            head.append("import java.util.Map;");
            head.append("import java.io.*;");
            head.append("import java.util.HashMap;");
            //head.append("import static org.javascool.Macros.*;");
            //head.append("import proglet.paintbrush.*;");
            // Declares the proglet's core as a Runnable in the Applet
            head.append("public class JvsToJavaTranslated").append(Jvs2Java.uid).append(" implements Runnable {");
            head.append("  private static final long serialVersionUID = ").append(uid).append("L;");
            head.append("  public void run() { main(); new File(System.getProperty(\"java.io.tmpdir\")+\"").append(File.separator).append("JvsToJavaTranslated").append(Jvs2Java.uid).append(".class\").delete(); }");
            body.append("}\n");
        }
        return (head.toString() + body.toString());
    }

    private static String translateOnce(String line) {
        // Translates the while statement with sleep
        line = line.replaceAll("(while.*\\{)", "$1 sleep(0);");
        // Translates the Synthe proglet @tone macro
        line = line.replaceFirst("@tone:(.*)\\s*;",
                "proglet.synthesons.SoundDisplay.tone = new org.javascool.SoundBit() { public double get(char c, double t) { return $1; } }; proglet.synthesons.SoundDisplay.syntheSet(\"16 a\");");
        return "    " + line;
    }
    
    // Counter used to increment the serialVersionUID in order to reload the different versions of the class
    private static int uid = 1;
    
    public static DiagnosticCollector<JavaFileObject> compile(String pathToJavaFile){
        
        // We init tools
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // The compiler tool
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>(); // The diagnostic colector
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, Locale.FRENCH, null); // The file manager
        
        // Setup the file
        List<File> sourceFileList = new ArrayList<File>();
        sourceFileList.add(new File(pathToJavaFile));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
        
        // Create the task
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, compilationUnits);
        
        // Now we compile
        task.call();
        
        // Close file manager
        try {
            fileManager.close();
        } catch (IOException ex) {
            Logger.getLogger(Jvs2Java.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return diagnostics;
    }

    /** Compiles a Jvs code source.
     * <div>The jdk <tt>tool.jar</tt> must be in the path.</div>
     * @param path The file path to compile.
     * @return An empty string if the compilation succeeds, else the error message's text.
     *
     * @throws RuntimeException if an I/O exception occurs during command execution.
     */
    public static String compileAndRun(String jvsCode) {
        
        // Create an temp file
        File tmpJavaFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "JvsToJavaTranslated" + Jvs2Java.uid + ".java");
        String tmpPath = new File(tmpJavaFile.getParent()).getAbsolutePath();
        String tmpJFilePath = tmpJavaFile.getAbsolutePath();
        
        // We convert the JVS file and write the Java code ito the temp file
        try {
            Utils.saveString(Jvs2Java.translate(jvsCode), tmpJFilePath);
        } catch (IOException ex) {
            Logger.getLogger(Jvs2Java.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /////////////////////////
        // Compile tmpJavaFile //
        /////////////////////////
        
        DiagnosticCollector<JavaFileObject> diagnostics = Jvs2Java.compile(tmpJFilePath);
        
        // Delete the tmp Java source file
        tmpJavaFile.delete();
        
        // Show errors
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.println("Erreur à la ligne "+diagnostic.getLineNumber()+". Message de java : "+diagnostic.getMessage(Locale.FRENCH)+". Type : "+diagnostic.getKind());
            if(diagnostic.getKind().equals(Diagnostic.Kind.ERROR)){
                System.err.println("Erreur fatal lors de la compilation, arrêt de la compilation.");
                uid++;
                return "";
            }

        }
        
        ///////////////
        //    Run    //
        ///////////////
        
        try {
            Runnable program = Jvs2Java.load(tmpJavaFile.getAbsolutePath());
            Console.clear();
            program.run();
        } catch (Throwable e) {
            // If any error during the execution time
            System.err.println("Erreur fatal lors de l'éxecution, arrêt du programme."+e);
            uid++;
            return "";
        }
        uid++;
        return "";
    }

    /** Dynamically loads a Java runnable class to be used during this session.
     * @param path The path to the java class to load. The java class is supposed to belong to the "default" package, i.e. not to belong to a package.
     * @return An instantiation of this Java class. If the object is a runnable, the current runnable is set.
     *
     * @throws RuntimeException if an I/O exception occurs during command execution.
     * @throws IllegalArgumentException If the Java class name is not valid.
     */
    public static Runnable load(String path) throws Throwable {
        try {
            File javaClass=new File(path);
            URL[] urls = new URL[]{ /* new URL(getJavaScoolJar()), */new URL("file:" + javaClass.getParent() + File.separator)};
            Class< ?> j_class = new URLClassLoader(urls).loadClass(javaClass.getName().replaceAll("\\.java",""));
            Object o = j_class.newInstance();
            if (!(o instanceof Runnable)) {
                throw new Exception("La class à charger n'est pas un runnable");
            }
            return (Runnable) o;
        } catch (Throwable e) {
            throw new RuntimeException("Erreur: impossible de charger la class");
        }
    }
    
    public static Runnable runnable = null;

    /** Translates Jvs files to Java files.
     * @param usage <tt>java org.javascool.Jvs2Java [-reformat] [-compile] input-file</tt>
     * <p><tt>-reformat</tt> : Reformat the Jvs code</p>
     * <p><tt>-compile</tt> : Compile the java classes</p>
     */
    public static void main(String[] usage) {
        try {
            Jvs2Java.compile("/home/philien/test/Hello.java");
        } catch (Throwable ex) {
            Logger.getLogger(Jvs2Java.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}