package org.javascool.widgets;

import java.io.File;
import javax.swing.JFileChooser;
import org.javascool.gui.Desktop;
import org.javascool.tools.FileManager;
import org.javascool.tools.UserConfig;

/** Définit un panneau éditeur d'un fichier texte qui intègre les fonctions de colorisation et de complétion automatique.
 * @author Philippe Vienne
 */
class TextFileEditor extends TextEditor {

  private static final long serialVersionUID = 1L;

  private String location = null;

  /** Charge le texte à partir d'un fichier local.
   * <p>Lance un dialogue avec l'utilisateur pour choisir le fichier.</p>
   */
  public void load() {
    JFileChooser fc = new JFileChooser();
    String dir = getWorkingDir();
    if (dir != null) 
      fc.setCurrentDirectory(new File(dir));
    int returnVal = fc.showOpenDialog(Desktop.getInstance().getFrame());
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      setWorkingDir(fc.getSelectedFile().getParentFile().getAbsolutePath());
      load(fc.getSelectedFile().getAbsolutePath());
    }
  }
  // Récupère le répertoire par défaut de javascool
  private static String getWorkingDir() {
    try {
      if (UserConfig.getInstance("javascool").getProperty("dir") != null) {
	return UserConfig.getInstance("javascool").getProperty("dir");
      } else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux")) {
	return System.getProperty("user.dir");
      } else if (System.getProperty("home.dir") != null) {
	return System.getProperty("home.dir");
      } else
	return null;
    } catch(Exception e) {
      System.err.println("Notice: échec de la mise en place du répertoire par défaut: "+ e);
      return null;
    }
  }
  // Met à jour le répertoire par défaut de javascool
  private static void setWorkingDir(String dir) {
    try {
      UserConfig.getInstance("javascool").setProperty("dir", dir);
    } catch(Exception e) {}
  }

  /** Charge le texte à partir d'une location.
   *
   * @param location Une URL (Universal Resource Location) de la forme: <div id="load-format"><table align="center">
   * <tr><td><tt>http:/<i>path-name</i></tt></td><td>pour aller chercher le contenu sur un site web</td></tr>
   * <tr><td><tt>http:/<i>path-name</i>?param_i=value_i&amp;..</tt></td><td>pour le récupérer sous forme de requête HTTP</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour le charger du système de fichier local ou en tant que ressource Java dans le CLASSPATH</td></tr>
   * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>pour le charger d'une archive
   *  <div>(exemple:<tt>jar:http://javascool.gforge.inria.fr/javascool.jar!/META-INF/MANIFEST.MF</tt>)</div></td></tr>
   * </table></div>
   * @param utf8 Si la valeur est vraie, force l'encodage en UTF-8 à la lecture. Par défaut (false) utilise l'encodage local.
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public void load(String location, boolean utf8) {
    setText(FileManager.load(this.location = location, utf8));
  }
  /**
   * @see #load(String, boolean)
   */
  public void load(String location) {
    load(location, false);
  }

  /** Sauve le texte dans un fichier local.
   * <p>Lance un dialogue avec l'utilisateur pour choisir le fichier.</p>
   * @param ext Si non null, l'extension du fichier est forcée à la valeur ".ext".
   */
  public void saveAs(String ext) {
    JFileChooser fc = new JFileChooser();
    String dir = getWorkingDir();
    if (dir != null) 
      fc.setCurrentDirectory(new File(dir));
    fc.setApproveButtonText("Enregistrer");
    fc.setDialogTitle("Enregistrer");
    int returnVal = fc.showOpenDialog(this.getParent());
    if (returnVal == JFileChooser.APPROVE_OPTION) { 
      setWorkingDir(fc.getSelectedFile().getParentFile().getAbsolutePath());
      save(fc.getSelectedFile().getAbsolutePath(), ext);
    }
  }
  /** 
   * @see #saveAs(String)
   */
  public void saveAs() {
    saveAs(null);
  }

  /** Sauve le texte à la dernière location choisie, si le texte a été modifié.
   */
  public void save() {
    if (isTextModified())
      save(location);
  }

  /** Sauve le texte dans une location.
   *
   * @param location Une URL (Universal Resource Location) de la forme: <div id="save-format"><table>
   * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>pour sauver sur un site FTP.</td></tr>
   * <tr><td><tt>file:/<i>path-name</i></tt></td><td>pour sauver dans le système de fichier local (le <tt>file:</tt> est optionnel).</td></tr>
   * <tr><td><tt>mailto:<i>address</i>?subject=<i>subject</i></tt></td><td>pour envoyer un courriel avec le texte en contenu.</td></tr>
   * <tr><td><tt>stdout:/</tt></td><td>pour l'imprimer dans la console.</td></tr>
   * </table></div>
   * @param ext Si non null, l'extension du fichier est forcée à la valeur ".ext".
   * @param backup Si true, dans le cas d'un fichier, crée une sauvegarde d'un fichier existant. Par défaut false.
   * * <p>Le fichier sauvegardé est doté d'un suffixe numérique unique.</p>
   * @param utf8 Si la valeur est vraie, force l'encodage en UTF-8 à la lecture. Par défaut (false) utilise l'encodage local.
   *
   * @throws IllegalArgumentException Si l'URL est mal formée.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
   */
  public void save(String location, String ext, boolean backup, boolean utf8) {
    if (ext != null)
      location = location.replaceFirst("\\.[^.]*$", "") + "." + ext;
    FileManager.save(this.location = location, getText(), backup, utf8);
  }
  /*
   * @see #save(String, String, boolean, boolean)
   */
  public void save(String location, String ext) {
    save(location, ext, false, false);
  }
  /**
   * @see #save(String, String, boolean, boolean)
   */
  public void save(String location) {
    save(location, null, false, false);
  }
  

  /** Lanceur du mécanisme d'éditon.
   * @param usage <tt>java org.javascool.widgets.TextEditor</tt>
   */
  public static void main(String[] usage) {
    if(usage.length == 0)
      new MainFrame().reset("editor", 800, 600, new TextFileEditor().setSyntax("jvs")
			    .addCompletions("org/javascool/macros/completion-langage.xml")
			    .addCompletions("org/javascool/macros/completion-stdout.xml")
			    .addCompletions("org/javascool/macros/completion-stdin.xml")
			    .addCompletions("org/javascool/macros/completion-macros.xml"));
  }
}
