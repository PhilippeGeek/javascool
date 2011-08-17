/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.core;

import java.applet.Applet;
import java.awt.Component;

import java.util.ArrayList;
import org.javascool.tools.Macros;

import java.io.File;
import java.io.IOException;
import org.javascool.tools.FileManager;
import org.javascool.tools.Pml;
import org.javascool.tools.Invoke;

/** Définit les mécanismes de compilation, exécution, gestion de proglet.
 *
 * @see <a href="ProgletEngine.java.html">code source</a>
 * @serial exclude
 */
public class ProgletEngine {
  /** Tables des proglets. */
  private ArrayList<Proglet> proglets;

  // @static-instance

  /** Crée et/ou renvoie l'unique instance de l'engine.
   * <p>Une application ne peut définir qu'un seul engine.</p>
   */
  public static ProgletEngine getInstance() {
    if(engine == null)
      engine = new ProgletEngine();
    return engine;
  }
  private static ProgletEngine engine = null;
  private ProgletEngine() {
    // Détection des proglets présentes dans le jar
    {
      proglets = new ArrayList<Proglet>();
      String javascoolJar = Macros.getResourceURL("org/javascool/core/ProgletEngine.class").toString().replaceFirst("jar:([^!]*)!.*", "$1");
      for(String dir : FileManager.list(javascoolJar, "org.javascool.proglets.[^\\.]+"))
        proglets.add(new Proglet().load(dir.replaceFirst("jar:[^!]*!", "")));
    }
    // Définit une proglet "vide" pour lancer l'interface
    if(proglets.isEmpty()) {
      Proglet p = new Proglet();
      p.pml.set("name", "Interface");
      p.pml.set("icon-location", "org/javascool/widgets/icons/scripts.png");
      proglets.add(p);
    }
  }
  //
  // [1] Mécanisme de compilation/exécution
  //

  /** Mécanisme de compilation du fichier Jvs.
   * @param program Nom du programme à compiler.
   * @return La valeur true si la compilation est ok, false sinon.
   */
  public boolean doCompile(String program) {
    doStop();
    // Traduction Jvs -> Java puis Java -> Class et chargement de la classe si succès
    Jvs2Java jvs2java = new Jvs2Java();
    if(getProglet() != null) {
      jvs2java.setProgletTranslator(getProglet().getTranslator());
      jvs2java.setProgletPackageName(getProglet().hasFunctions() ? "org.javascool.proglets." + getProglet().getName() : null);
    }
    String javaCode = jvs2java.translate(program);
    // Creation d'un répertoire temporaire
    File buildDir = FileManager.createTempDir("javac");
    String javaFile = buildDir + File.separator + jvs2java.getClassName() + ".java";
    FileManager.save(javaFile, javaCode);
    if(Java2Class.compile(javaFile)) {
      runnable = Java2Class.load(javaFile);
      return true;
    } else {
      runnable = null;
      return false;
    }
  }
  /** Mécanisme de lancement du programme compilé. */
  public void doRun() {
    doStop();
    // Lancement du runnable dans un thread
    if(runnable != null) {
      (thread = new Thread(new Runnable() {
                             @Override
                             public void run() {
                               try {
                                 runnable.run();
                                 thread = null;
                               } catch(Throwable e) {
                                 System.out.println("Erreur à l'exécution: " + e);
                               }
                             }
                           }
                           )).start();
    }
  }
  /** Mécanisme d'arrêt du programme compilé. */
  public void doStop() {
    if(thread != null) {
      thread.interrupt();
      thread = null;
    }
  }
  /** Renvoie true si le programme est en cours. */
  public boolean isRunning() {
    return thread != null;
  }
  private Thread thread = null;
  private Runnable runnable = null;

  //
  // Mécanisme de chargement d'une proglet
  //

  /** Mécanisme de chargement d'une proglet.
   * @param proglet Le nom de la proglet.
   * @return La proglet en fonctionnement ou null si la proglet n'existe pas.
   */
  public Proglet setProglet(String proglet) {
    if(currentProglet != null && currentProglet.getPane() != null)
            Invoke.run(currentProglet.getPane(), "destroy");
    for(Proglet p : getProglets())
      if(p.getName().equals(proglet))
        currentProglet = p;
    if(currentProglet != null && currentProglet.getPane() != null)
            Invoke.run(currentProglet.getPane(), "init");
    return currentProglet;
  }
  /** Renvoie la proglet courante.
   * * @return la proglet courante ou null sinon.
   */
  public Proglet getProglet() {
    return currentProglet;
  }
  private Proglet currentProglet = null;

  /** Renvoie toutes les proglets actuellement disponibles.
   * @return Un objet utilisable à travers la construction <tt>for(Proglet proglet: getProglets()) { .. / .. }</tt>.
   */
  public Iterable<Proglet> getProglets() {
    return proglets;
  }
  public class Proglet {
    /** Méta-données de la proglet. */
    public Pml pml = new Pml();

    /** Définit une proglet à partir d'un répertoire donné.
     * @param location L'URL (Universal Resource Location) où se trouve la proglet.
     * @throws IllegalArgumentException Si l'URL est mal formée.
     * @return Cet objet, permettant de définir la construction <tt>new Proglet().load(..)</tt>.
     */
    public Proglet load(String location) {
      // Définit les méta-données de la proglet.
      pml.load(location + "proglet.pml");
      pml.set("location", location);
      try {
        pml.set("name", new File(location).getName());
      } catch(Exception e) { throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
      }
      if(pml.isDefined("icon") &&
         FileManager.exists(Macros.getResourceURL(location + pml.getString("icon"))))
        pml.set("icon-location", location + pml.getString("icon"));
      else
        pml.set("icon-location", "org/javascool/widgets/icons/scripts.png");
      try {
        Class.forName("org.javascool.proglets." + pml.getString("name") + ".Functions");
        pml.set("has-functions", true);
      } catch(Throwable e) {
        pml.set("has-functions", false);
      }
      pml.set("help-location", pml.getString("location") + "help.htm");
      try {
        pml.set("java-pane", (Component) Class.forName("org.javascool.proglets." + pml.getString("name") + ".Panel").newInstance());
      } catch(Throwable e) {}
      try {
        pml.set("jvs-translator", (Translator) Class.forName("org.javascool.proglets." + pml.getString("name") + ".Translator").newInstance());
      } catch(Throwable e) {}
      return this;
    }
    @Override
    public String toString() {
      return pml.toString();
    }
    /** Renvoie le nom de la proglet.
     * @return Le nom de la proglet.
     */
    public String getName() {
      return pml.getString("name");
    }
    /** Renvoie le titre de la proglet.
     * @return Le titre de la proglet.
     */
    public String getTitle() {
      return pml.getString("title");
    }
    /** Renvoie l'icone de la proglet.
     * @return Le nom de l'URL de l'icone de la proglet, ou l'icone par defaut sinon.
     */
    public String getIcon() {
      return pml.getString("icon-location");
    }
    /** Renvoie la documentation de la proglet.
     * @return L'URL de la documentation de la proglet.
     */
    public String getHelp() {
      return pml.getString("help-location");
    }
    /** Indique si la proglet définit des fonctions statiques pour l'utilisateur.
     */
    public boolean hasFunctions() {
      return pml.getBoolean("has-functions");
    }
    /** Renvoie, si il existe, le panneau graphique de la proglet.
     * @return Le panneau graphique de la proglet si il existe, sinon null.
     */
    public Component getPane() {
      return (Component) pml.getObject("java-pane");
    }
    /** Renvoie, si il existe, le translateur de code de la proglet.
     * @return Le translateur de code de la proglet si il existe, sinon null.
     */
    public Translator getTranslator() {
      return (Translator) pml.getObject("jvs-translator");
    }    
    /** Indique si la proglet a une démo pour l'utilisateur.
     */
    public boolean hasDemo() {
      return getPane() != null && Invoke.run(getPane(), "start", false);
    }
    /** Lance la démo de la proglet.
     * @return La valeur true si la méthode est invocable, false sinon.
     * @throws RuntimeException si la méthode génère une exception lors de son appel.
     */
    public boolean doDemo() {
        return getPane() != null && Invoke.run(getPane(), "start");
    }
}
}
