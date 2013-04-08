package org.javascool.core;

import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import org.javascool.tools.FileManager;
import java.io.InputStreamReader;

/** Définit le mécanisme de compilation en ligne d'une proglet dans sa version jvs5.
 * - Attention il faut que la proglet ait été convertie en jvs5 (conversion des docs XML en HTML, du fichier de méta-donnée en .json).
 * @see <a href="Proglet2Html.java.html">code source</a>
 * @serial exclude
 */
public class Proglet2Html {
  // @factory
  private Proglet2Html() {}

  /** Compile sous forme de répertoire web une proglet donnée.
   * <p>Les erreurs de compilation ou de construction s'affichent dans la console.</p>
   * @param htmlDir Le répertoire cible de la construction.
   * @param progletDir Le répertoire où se trouvent les fichiers de la proglet.
   * @return La valeur true en cas de succès, false si il y a des erreurs de compilation.
   *
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de la compilation ou construction.
   */
  public static boolean buildHtml(String htmlDir, String progletDir) {
    try {
      JSONObject params = Proglet2Jar.getProgletParameters(progletDir);
      // Creation d'un répertoire vierge
      JarManager.rmDir(new File(htmlDir));
      new File(htmlDir).mkdirs();
      // Expansion des fichiers de base du site
      addBootstrap(htmlDir);
      FileManager.save(htmlDir + File.separator + "index.html", "<script>location.replace('help.html');</script>");
      // Copy des fichiers de la proglet
      for (String file : FileManager.list(progletDir)) {
	if (new File(file).isFile() && !(file.endsWith(".jar") || file.endsWith(".zip"))) {
	  if (file.endsWith(".html")) {
	    FileManager.save(htmlDir + File.separator + new File(file).getName(), encapsulates(FileManager.load(file), params));
	  } else if (file.endsWith(".java")) {
	    JarManager.copyFile(file, htmlDir + File.separator + "api" +  
				File.separator + "org" + File.separator + "javascool" + File.separator + "proglets" + File.separator + params.getString("name") + 
				File.separator + new File(file).getName());
	  } else {
	    if (file.endsWith(".jvs"))
	      FileManager.save(htmlDir + File.separator + new File(file).getName() + ".html",
			       encapsulates("<pre class=\"prettyprint linenums\">\n" + FileManager.load(file) + "\n</pre>", params));
	    JarManager.copyFile(file, htmlDir + File.separator + new File(file).getName());
	  }
	}
      }
      // Génération des fichiers liés aux sources java
      {
	String progletJar = htmlDir + File.separator + "javascool-proglet-"+params.getString("name")+".jar";
	Proglet2Jar.buildJar(progletJar, progletDir);
	for (String file : FileManager.list(progletDir))
	  if (file.endsWith(".jvs"))
	    javaStart("-cp "+progletJar+" org.javascool.core.Jvs2Jar "+params.getString("name")+" "+file+" "+file+".jar", 60);
	javadoc(params.getString("name"), System.getProperty("java.class.path"), htmlDir + File.separator + "api", htmlDir + File.separator + "api");
      }
      return true;
    } catch (Throwable e) {
      System.err.println(e);
      e.printStackTrace();
      return false;
    }
  }

  // Encapsulate le corps d'une page html
  private static String encapsulates(String body, JSONObject params) throws Exception {
    return (progletHtmlHeader + body + progletHtmlTrailer)
      .replaceAll("@name", params.getString("name"))
      .replaceAll("@title", params.getString("title"))
      .replaceAll("@icon", params.getString("icon"))
      .replaceAll("@author", params.getString("author"))
      .replaceAll("@email", params.getString("email"))
      .replaceAll("@base-url", ".")
      .replaceAll("href=\"http://newtab\\?", "href=\"")
      .replaceAll("< *a +href=\"http://editor\\?([^\\.]*).jvs\"", "(<a href=\"$1.jvs.jar\">@run</a>) <a href=\"$1.jvs.html\"");
  }

  // Genere la doc liée aux source java
  private static void javadoc(String name, String classPath, String srcDir, String apiDir) throws IOException {
    // Mise en place des dossiers source et cible
    apiDir = new File(apiDir).getCanonicalPath();
    srcDir = new File(srcDir).getCanonicalPath();
    if (!apiDir.equals(srcDir))
      JarManager.rmDir(new File(apiDir));
    new File(apiDir).mkdirs();
    String files[] = FileManager.list(srcDir, ".*\\.java$", 5);
    // Lancement du logiciel javadoc avec les bonnes options
    if (files.length > 0) {
      String argv = "-quiet\t-classpath\t" + classPath + "\t-d\t" + apiDir +
	"\t-link\thttp://download.oracle.com/javase/6/docs/api" + "\t-windowtitle\tJava's Cool "
	+ name + "\t-doctitle\tJava's Cool " + name +
	"\t-public\t-author\t-version\t-nodeprecated\t-nohelp\t-nonavbar\t-notree\t-charset\tutf-8";
      for (String f : files)
	argv += "\t" + f;
      try {
	com.sun.tools.javadoc.Main.execute(argv.split("\t"));
      } catch (Throwable e) {
	throw new IllegalStateException("Erreur à la création de la javadoc du répertoire \"" + srcDir + "\", «" + e + "»");
      }
      // Copie et encapsulation des sources pour accéder au code
      for (String file : files)
	FileManager.save(apiDir + File.separator + file.substring(srcDir.length()) + ".html",
			 "<pre class=\"prettyprint linenums\">\n" + FileManager.load(file) + "\n</pre>");
      
      // Manipulation des fichiers html générés pour lisser le style
      for (String file : FileManager.list(apiDir, ".*\\.html", 5)) {
	String text = FileManager.load(file)
	  .replaceFirst("(?s)<!DOCTYPE.*<body[^>]*>", "")
	  .replaceFirst("</body>\\s*</html>", "")
	  .replaceAll("class=\"(blockList|inheritance)\"", "class=\"unstyled\"")
	  .replaceAll("<table[^>]*>", "<table class=\"table table-striped table-bordered\">")
	  .replaceAll("<(/?)code>", "<$1pre>");
	text = (javadocHtmlHeader + text + progletHtmlTrailer)
	  .replaceAll("@name", name).replaceAll(
						"@base-url",
						file.substring(apiDir.length() + 1)
						.replaceAll("[^/]+", "..")
						.substring(1));
	FileManager.save(file, text);
      }
      if (new File(apiDir + File.separator + "overview-summary.html").exists())
	JarManager.copyFile(apiDir + File.separator + "overview-summary.html", apiDir + File.separator + "index.html");
      addBootstrap(apiDir);
    }
  }

  /** Exécute dans un autre process une commande java.
   * <p>Les sorties du process java sont renvoyés en mémoire au stdin/stderr de la présente exécution.</p>
   * @param command Les arguments de la ligne de commande de l'exécutable java.
   * @param timeout La durée maximale d'exécution de la commande.
   * @return Le status de l'exécutable java, 0 si pas d'erreur.
   * @throws IllegalStateException en cas de time-out.
   */
  public static boolean javaStart(String command, int timeout) {
    try {
      // @todo regarder sur windows si il y a le bin ou un exec à la place
      String javaCommand = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java"; 
      return exec(javaCommand+(command.indexOf('\t') == -1 ? " " : "\t")+command, timeout) == 0;
    } catch(Exception e) {
      System.err.println("Impossible de lancer la command '$java " + command + "' : " + e);
      return false;
    }
  }
  // Lance une commande avec echo des stdout/stderr
  private static int exec(String command, int timeout) throws IOException  {
    Process process = Runtime.getRuntime().exec(command.trim().split((command.indexOf('\t') == -1) ? " " : "\t"));
    InputStreamReader stdout = new InputStreamReader(process.getInputStream());
    InputStreamReader stderr = new InputStreamReader(process.getErrorStream());
    long now = System.currentTimeMillis();
    while(true) {
      try { Thread.sleep(300); } catch(Exception e) { }
      while(stdout.ready())
	System.out.print((char) stdout.read());
      while(stderr.ready())
	System.err.print((char) stderr.read());
      try {
	return process.exitValue();
      } catch(Exception e) { }
      if (timeout > 0 && System.currentTimeMillis() > now + timeout * 1000)
	throw new IllegalStateException("Timeout T > "+timeout+"s when running '"+command+"'");
    }
  }
  private static void addBootstrap(String apiDir) throws IOException {
    JarManager.copyResource("org/javascool/core/proglet-css.zip", apiDir + File.separator + "proglet-css.zip");
    JarManager.jarExtract(apiDir + File.separator + "proglet-css.zip", apiDir);
    new File(apiDir + File.separator + "proglet-css.zip").delete();
  }
  
  private final static String progletHtmlHeader0 = 
    "<!DOCTYPE html>\n" +
    "<html lang=\"fr\">\n" +
    "<head>\n" +
    "  <title>Java'sCool «@name»</title>\n" +
    "  <meta charset=\"UTF-8\">\n" +
    "  <style type=\"text/css\">\n" +
    "     #main { margin-top: 80px;  }\n" +
    "  </style>\n" +
    "  <link href=\"@base-url/assets/pygments.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/bootstrap/css/bootstrap-responsive.css\" rel=\"stylesheet\">\n" +
    "  <link href=\"@base-url/assets/google-code-prettify/prettify.css\" type=\"text/css\" rel=\"stylesheet\"/>\n" +
    "  <script type=\"text/javascript\" src=\"@base-url/assets/google-code-prettify/prettify.js\"></script>\n" +
    "</head><body style=\"background-color:#FFFFFF;\" onload=\"prettyPrint()\">\n" +
    "\n";

  private final static String progletHtmlNavBar = 
    "<div class=\"navbar navbar-fixed-top\">\n" +
    "  <div class=\"navbar-inner\">\n" +
    "    <div class=\"container\">\n" +
    "      <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "      </a>\n" +
    "      <a class=\"brand\" href=\"@base-url/help.html\" style=\"margin: 0; margin-right: 30px; margin-top: 3px; padding:0;\">\n" +
    "        <img alt=\"logo\" src=\"@base-url/@icon\" style=\"height: 35px; padding: 0; margin: 0;\"/>&nbsp;<b>Java'sCool «@name»</b>\n" +
    "      </a>\n" +
    "      <div class=\"nav-collapse\">\n" +
    "        <ul class=\"nav\">\n" +
    "          <li><a href=\"@base-url/help.html\">Documentation</a></li>\n" +
    "          <li><a href=\"@base-url/javascool-proglet-@name.jar\">Démonstration</a></li>\n" +
    "          <li><a href=\"@base-url/api/org/javascool/proglets/@name/package-summary.html\">Implémentation</a></li>\n" +
    "          <li><a href=\"mailto:@email?subject=À propos de Java'sCool «@name»\">Contact</a></li>\n" +
    "        </ul>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div id=\"main\"><b><img align=\"left\" src=\"@icon\" alt=\"icon\"/>&nbsp;Java's Cool proglet «@name»</b><br/><div align=\"center\">@title</div><div align=\"right\"><i>@author</i></div></div>\n" +
    "<div class=\"container\">\n";

  private static final String progletHtmlHeader  = progletHtmlHeader0 + progletHtmlNavBar;

  private final static String javadocHtmlNavBar = 
    "<div class=\"navbar navbar-fixed-top\">\n" +
    "  <div class=\"navbar-inner\">\n" +
    "    <div class=\"container\">\n" +
    "      <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "        <span class=\"icon-bar\"></span>\n" +
    "      </a>\n" +
    "      <a class=\"brand\" href=\"@base-url/help.html\" style=\"margin: 0; margin-right: 30px; margin-top: 3px; padding:0;\">\n" +
    "        &nbsp;<b>Java'sCool «@name»</b>\n" +
    "      </a>\n" +
    "      <div class=\"nav-collapse\">\n" +
    "        <ul class=\"nav\">\n" +
    "          <li></li>\n" +
    "        </ul>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "</div>\n" +
    "<div id=\"main\"></div>\n" +
    "<div class=\"container\">\n";

  private static final String javadocHtmlHeader  = progletHtmlHeader0 + javadocHtmlNavBar;

  private final static String progletHtmlTrailer = 
    "</div>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/jquery.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-transition.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-alert.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-modal.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-dropdown.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-scrollspy.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-tab.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-tooltip.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-popover.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-button.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-collapse.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-carousel.js\"></script>\n" +
    "<script src=\"@base-url/assets/bootstrap/js/bootstrap-typeahead.js\"></script>\n" +
    "</body></html>\n";
  
  /** Lanceur de la construction de la proglet.
   * @param usage <tt>java org.javascool.core2.Proglet2Html (jarFile progletDir|javadocName srcDir apiDir)</tt>
   */
  public static void main(String[] usage) {
    // @main
    if(usage.length == 2) {
      buildHtml(usage[0], usage[1]);
    } else if(usage.length == 3) {
      try {
	javadoc(usage[0], System.getProperty("java.class.path"), usage[1], usage[2]);
      } catch(IOException e) {
	throw new RuntimeException(e.toString());
      }
    }
  }
}

