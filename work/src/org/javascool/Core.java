/*******************************************************************************
*           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
*******************************************************************************/

package org.javascool;

import javax.swing.UIManager;
import org.javascool.macros.Macros;
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.io.UnsupportedEncodingException;
import org.javascool.gui.Desktop;
import org.javascool.core.ProgletEngine;
import org.javascool.tools.ErrorCatcher;

/** Lanceur de l'application "apprenant" qui permet de manipuler des «proglets».  *
 *
 * @see <a href="Core.java.html">source code</a>
 * @serial exclude
 */
public class Core {
  /** Aide de JVS */
  public static final String help = "org/javascool/macros/memo-macros.htm";
  /** Mets en place le système d'alerte en cas d'erreur non gérée. */
  static void setUncaughtExceptionAlert() {
    ErrorCatcher.setUncaughtExceptionAlert("<h1>Détection d'une anomalie liée à Java:</h1>\n" +
                                           "Il y a un problème de compatibilité avec votre système, nous allons vous aider:<ul>\n" +
                                           "  <li>Copier/Coller tous les éléments de cette fenêtre et</li>\n" +
                                           "  <li>Envoyez les par mail à <b>javascool@googlegroups.com</b> avec toute information utile.</li>" +
                                           " </ul>",
                                           About.revision);
  }
  /** Retrouve le chemin du jar courant.
   * @return Le chemin du jar
   * @throws RuntimeException lorsque l'application n'a pas été démarré depuis un jar
   */
  public static String javascoolJar() {
    if(javascoolJar != null) {
      return javascoolJar;
    }
    String url = Macros.getResourceURL("org/javascool/Core.class").toString().replaceFirst("jar:file:([^!]*)!.*", "$1");
    //- System.err.println("Notice: javascool url is " + url);
    if(url.endsWith(".jar")) {
      try {
        String jar = URLDecoder.decode(url, "UTF-8");
        if(new File(jar).exists()) {
          return javascoolJar = jar;
        }
        // Ici on essaye tous les encodages possibles pour essayer de détecter javascool
        {
          jar = URLDecoder.decode(url, Charset.defaultCharset().name());
          if(new File(jar).exists()) {
            javascoolJarEnc = Charset.defaultCharset().name();
            return jar;
          }
          for(String enc : Charset.availableCharsets().keySet()) {
            jar = URLDecoder.decode(url, enc);
            if(new File(jar).exists()) {
              javascoolJarEnc = enc;
              System.err.println("Notice: javascool file " + jar + " correct decoding as " + enc);
              return javascoolJar = jar;
            } else {
              System.err.println("Notice: javascool file " + jar + " wrong decoding as " + enc);
            }
          } throw new RuntimeException("Il y a un bug d'encoding sur cette plate forme");
        }
      } catch(UnsupportedEncodingException ex) { throw new RuntimeException("Spurious defaultCharset: this is a caveat");
      }
    } else { return "";
    }
    // throw new RuntimeException("Java's cool n'a pas été démarré depuis un Jar");
  }
  public static String javascoolJarEnc() {
    javascoolJar();
    return javascoolJarEnc;
  }
  private static String javascoolJar = null, javascoolJarEnc = null;

  /** Lanceur de l'application.
   * @param usage <tt>java -jar javascool.jar [proglet]</tt>
   */
  public static void main(String[] usage) {
    if((usage.length > 0) && (usage[0].equals("-h") || usage[0].equals("-help") || usage[0].equals("--help"))) {
      System.out.println("Java's Cool Core - lance l'interface pour travailler avec les proglets");
      System.out.println("Usage : java -jar javascool.jar");
      System.exit(0);
    }
    // Empeche de pouvoir renommer itempestivement des folder, attention un problème sous Mac avec la jvs7
    if (!System.getProperty("os.name").toLowerCase().startsWith("mac"))
      UIManager.put("FileChooser.readOnly", Boolean.TRUE);
    System.err.println("" + About.title + " is starting ...");
    ErrorCatcher.checkJavaVersion(6);
    setUncaughtExceptionAlert();
    // Lance le pannneau général ou une proglet directement
    Desktop.getInstance().getFrame();
    if(usage.length == 1) {
      Desktop.getInstance().openProglet(usage[0], true);
    } else if (ProgletEngine.getInstance().getProgletCount() == 1) {
      for(ProgletEngine.Proglet proglet: ProgletEngine.getInstance().getProglets()) {
	String name = proglet.getName();
	System.err.println("Ouverture de la proglet «"+name+"»");
	Desktop.getInstance().openProglet(name, true);
	break;
      }
    }
  }
}
