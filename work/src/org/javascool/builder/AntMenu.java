package org.javascool.builder;

import java.io.FileInputStream;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.javascool.macros.Stdin;
import org.javascool.widgets.Console;
import org.javascool.widgets.MainFrame;
import org.javascool.widgets.ToolBar;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/** Ouvre un menu à partir d'un fichier ant pour piloter ces commandes. */
public class AntMenu {
  /** Lit le fichier ant et renvoie les cibles. */
  private void reset(String file) {
    AntHandler handler = new AntHandler(Console.getInstance().getToolBar());
    try {
      SAXParserFactory.newInstance().newSAXParser().parse(new FileInputStream(file), handler);
    } catch(Exception e) {
      System.err.println("Impossible de lire les cibles du fichier : "+file+" ("+e+")");
    }
    Console.getInstance().getToolBar().addTool("QUIT", new Runnable() {
	@Override
	  public void run() {
	  System.exit(0);
	}});
    new MainFrame().reset("Ant tasks", Console.getInstance());
  }
  private static class AntExec implements Runnable {
    public AntExec(String target) {
      this.target = target;
    }
    @Override
      public void run() {
      System.out.println("\nStarting "+target+" . .\n");
      new Thread(new Runnable() {
	  @Override
	    public void run() {
	    if ("svn".equals(target)) {
	      String input = Stdin.readString("commit message") ;
	      System.out.println(Exec.run("sh\t-c\techo "+input+" | ant "+target, 0));
	    } else
	      System.out.println(Exec.run("ant\t"+target, 0));
	  }}).start();
    }
    private String target;
  }
  private static class AntHandler extends DefaultHandler {
    public AntHandler(ToolBar toolbar) {
      this.toolbar = toolbar;
    }
    @Override
      public void startElement(String uri, String urn, String name, Attributes params) {
      if ("target".equals(name) && !"menu".equals(params.getValue("name"))) {
	name = params.getValue("name"); while(name.length() < 16) name += " ";
	toolbar.addTool(name, "org/javascool/widgets/icons/refresh.png", new AntExec(params.getValue("name")));
      }
    }
    private ToolBar toolbar;
  }
  /** Lanceur du menu.
   * @param usage <tt>java org.javascool.builder.AntMenu [build.xml]</tt>
   */
  public static void main(String[] usage) {
    new AntMenu().reset(usage.length > 0 ? usage[0] : "build.xml");
  }
}

