/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.core;

import java.applet.Applet;
import java.net.URL;

import java.util.ArrayList;
import org.javascool.tools.Macros;

import java.io.File;
import org.javascool.tools.StringFile;
import org.javascool.tools.Pml;

/** Définit les mécanismes de compilation, exécution, gestion de proglet JavaScool.
 * @see <a href="Engine.java.html">code source</a>
 * @serial exclude
 */
public class Engine {
  /** Tables des proglets. */
  private ArrayList<Proglet> proglets;
/** Proglet en fonctionnement. */
  Proglet currentProglet;

  // @static-instance

  /** Crée et/ou renvoie l'unique instance de l'engine.
   * <p>Une application ne peut définir qu'un seul engine.</p>
   */
  public static Engine getInstance() {
    if(engine == null)
      engine = new Engine();
    return engine;
  }
  private static Engine engine = null;
  private Engine() {
    proglets = new ArrayList<Proglet>();
    if(proglets.size() == 0) {
      // Définit une proglet "vide" pour lancer l'interface
      Proglet p = new Proglet();
      p.pml.set("name", "Interface");
      p.pml.set("icon-location", "org/javascool/widgets/icons/scripts.png");
      proglets.add(p);
    }
    currentProglet = proglets.get(0);
  }
  /** Mécanisme de compilation du fichier Jvs.
   * @param program Non du programme à compiler.
   * @return La valeur true si la compilation est ok, false sinon.
   */
  public boolean doCompile(String program) {
    Jvs2Java jvs2java = new Jvs2Java();
    jvs2java.setProgletTranslator(getProglet().getTranslator());
    if(getProglet().hasFunctions())
      jvs2java.setProgletPackageName("org.javascool." + getProglet().getName());
    String javaFile = System.getProperty("java.io.tmpdir") + File.separator + "JvsToJavaTranslated" + (++uid) + ".java";
    System.out.println(jvs2java.translate(program) + "\n------------------\n");
    StringFile.save(javaFile, jvs2java.translate(program));
    if(Java2Class.compile(javaFile)) {
      Java2Class.load(javaFile);
      return true;
    } else
      return false;
  }
  private int uid = 0;
  /** Mécanisme de lancement du programme compilé. */
  public void doRun() {
    Macros.message("doRun");
  }
  /** Mécanisme d'arrêt du programme compilé. */
  public void doStop() {
    Macros.message("doRun");
  }
  /** Mécanisme de chargement d'une proglet.
   * @param proglet Le nom de la proglet.
   * @return La proglet en fonctionnement.
   * @throws IllegalArgumentException Si la proglet n'existe pas.
   */
  public Proglet setProglet(String proglet) {
    for(Proglet p : getProglets())
      if(p.getName().equals(proglet))
        return p; throw new IllegalArgumentException("Proglet inconnue : " + proglet);
  }
  /** Renvoie la proglet en cours de fonction.
   * @return La proglet en fonctionnement.
   */
  public Proglet getProglet() {
    return currentProglet;
  }
  /** Renvoie toutes les proglets actuellement disponibles.
   * @return Un objet utilisable à travers la construction <tt>for(Proglet proglet: getProglets()) { .. / .. }</tt>.
   */
  public Iterable<Proglet> getProglets() {
    return proglets;
  }
  public class Proglet {
    /** Méta-données de la proglet. */
    Pml pml = new Pml();

    /** Définit une proglet à partir d'un répertoire donné.
     * @param location L'URL (Universal Resource Location) où se trouve la proglet.
     * @throws IllegalArgumentException Si l'URL est mal formée.
     */
    public void load(String location) {
      // Définit les méta-données de la proglet.
      pml.load(location + "proglet.pml");
      pml.set("location", location);
      try {
        pml.set("name", new File(new URL(location).getPath()).getName());
      } catch(Exception e) { throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
      }
      if(pml.isDefined("icon"))
        pml.set("icon-location", Macros.getResourceURL(pml.getString("icon"), location));
      if(!StringFile.exists(pml.getString("icon-location")))
        pml.set("icon-location", "org/javascool/widgets/icons/scripts.png");
      try {
        Class.forName("org.javascool.proglets." + pml.getString("name") + ".Functions");
        pml.set("has-functions", true);
      } catch(Throwable e) {
        pml.set("has-functions", false);
      }
      try {
        pml.set("java-pane", (Applet) Class.forName("org.javascool.proglets." + pml.getString("name") + ".Panel").newInstance());
      } catch(Throwable e) {
        System.err.println("Notice " + pml.getString("name") + " has no pane (" + e + ")");
      }
      try {
        pml.set("jvs-translator", (Translator) Class.forName("org.javascool.proglets." + pml.getString("name") + ".Panel").newInstance());
      } catch(Throwable e) {}
    }
    /** Renvoie le nom de la proglet.
     * @return Le nom de la proglet.
     */
    public String getName() {
      return pml.getString("name");
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
      return pml.getString("location") + "help.htm";
    }
    /** Indique si la proglet définit des fonctions statiques pour l'utilisateur.
     */
    public boolean hasFunctions() {
      return pml.getBoolean("has-functions");
    }
    /** Renvoie, si il existe, le panneau graphique de la proglet.
     * @return Le panneau graphique de la proglet si il existe, sinon null.
     */
    public Applet getPane() {
      return (Applet) pml.getObject("java-pane");
    }
    /** Renvoie, si il existe, le translateur de code de la proglet.
     * @return Le translateur de code de la proglet si il existe, sinon null.
     */
    public Translator getTranslator() {
      return (Translator) pml.getObject("jvs-translator");
    }
  }
}
