package org.javascool.widgets;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import org.javascool.widgets.ToolBar;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.Gutter;
import java.awt.Color;
import org.javascool.macros.Macros;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.javascool.tools.Pml;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.ShorthandCompletion;

/** Définit un panneau éditeur de texte qui intègre les fonctions de colorisation et de complétion automatique.
 * @author Philippe Vienne
 */
class TextEditor extends JPanel {

  private static final long serialVersionUID = 1L;

  // Barre de commande du panneau
  private ToolBar toolBar;
  // Panneau de l'éditeur
  private RSyntaxTextArea textArea;
  // Panneau de glissières
  private RTextScrollPane scrollPane;
  // Gestionnaire d'autocomplétion
  DefaultCompletionProvider completionsProvider;

  /** Construit un panneau d'édition. */
  public TextEditor() {
    setLayout(new BorderLayout());
    // Creation de la barre de commande
    {
      toolBar = new ToolBar();
      add(toolBar, BorderLayout.NORTH);
    } 
    // Creation de la zone d'édition
    {
      textArea = new RSyntaxTextArea(25, 70);
      textArea.setCaretPosition(0);
      textArea.requestFocusInWindow();
      textArea.setMarkOccurrences(true);
      textArea.setText("");
      // Raccourcis 
      {
	KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK);
	if (isMac())
	  key = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_MASK);
	KeyStroke copy_key = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK);
	if (isMac())
	  copy_key = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_MASK);
      }
      scrollPane = new RTextScrollPane(textArea, true);
      Gutter gutter = scrollPane.getGutter();
      gutter.setBorderColor(Color.BLUE);
      add(scrollPane, BorderLayout.CENTER);
    }
    // Définition du mécanisme de complétion
    {
      completionsProvider = new DefaultCompletionProvider();
      LanguageAwareCompletionProvider lacp = new LanguageAwareCompletionProvider(completionsProvider);
      AutoCompletion ac = new AutoCompletion(lacp) {
	  @Override
	    public void doCompletion() {
	    if (isAutoCompleteEnabled())
	      super.doCompletion();
	  }
	};
      ac.install(textArea);
      ac.setAutoCompleteSingleChoices(false);
      ac.setAutoActivationEnabled(true);
      ac.setAutoActivationDelay(1500);
      ac.setShowDescWindow(true);
    }
  }

  // Teste si le système est un mac
  private static boolean isMac() {
    return System.getProperty("os.name").toUpperCase().contains("MAC");
  }

  /** Définit la colorisation de cet éditeur. 
   * @param syntax Nom de la syntaxe : "Java", "Jvs", "None".
   * @return this.
   */
  public TextEditor setSyntax(String syntax) {
    if ("JVS".equals(syntax.toUpperCase()) || "JAVA".equals(syntax.toUpperCase()))
      textArea.setSyntaxEditingStyle(org.fife.ui.rsyntaxtextarea.SyntaxConstants.SYNTAX_STYLE_JAVA);
    return this;
  }

  /** Ajoute à cet éditeur les complétions définies dans le fichier. 
   * <p>Les complétions sont déclenchées par Ctrl+Space.</p>
   * @param completions Le nom du fichier contenant la définition des complétions.
   * @return this.
   */
  public TextEditor addCompletions(String completions) {
    // Lit le fichier d'autocomplétion
    Pml defs = new Pml().load(completions);
    // Pour chaque item . . 
    for(int i = 0; i < defs.getCount(); i++) {
      // .. recupère les infos de nom, titre, doc et code
      Pml def = defs.getChild(i);
      String name = def.getString("name"), title = def.getString("title"), code = null, doc = null;
      for(int j = 0; j < def.getCount(); j++) {
	if ("code".equals(def.getChild(j).getTag()) && def.getChild(j).getChild(0) != null && code == null)
	  code = def.getChild(j).getChild(0).getTag();
	if ("doc".equals(def.getChild(j).getTag()) && def.getChild(j).getChild(0) != null && doc == null)
	  doc = def.getChild(j).getChild(0).getTag();
      }
      // .. et ajoute la raccourci si il est bien défini
      if (name != null) {
	BasicCompletion bc = code == null ? new BasicCompletion(completionsProvider, name) : new ShorthandCompletion(completionsProvider, name, code);
	if (title != null)
	  bc.setShortDescription(title);
	if (doc != null)
	  bc.setSummary(doc);
	completionsProvider.addCompletion(bc);
      }
    }
    return this;
  }

  /** Remet à zéro toutes les complétions définies pour set éditeur. */
  void clearCompletions() {
    completionsProvider.clear();
  }

  /** Renvoie le texte actuellement édité. */
  public String getText() {
    return textArea.getText();
  }

  /** Initialise le texte à éditer.
   * @param text Le texte à éditer.
   */
  public void setText(String text) {
    textArea.setText(initialText = text);
  }

  /** Teste si le texte a été édité ou si il reste inchangé.
   * @return Renvoie la valeur true si getText() et la dernière valeur donnée à setText() ne sont pas les mêmes
   */
  public boolean isTextChanged() {
    return !getText().equals(initialText);
  }
  private String initialText = "";

  /** Marque une ligne du texte avec une icône.
   * @param line Numéro de la ligne du texte.
   * @param icon Nom de l'icône à utiliser (une icône indiquant une erreur par défaut).
   */
  public void signalLine(int line, String icon) {
    Gutter gutter = scrollPane.getGutter();
    gutter.setBookmarkingEnabled(true);
    try {
      textArea.setCaretPosition(textArea.getLineStartOffset(line - 1));
      scrollPane.getGutter().addLineTrackingIcon(line - 1, Macros.getIcon(icon == null ? "org/javascool/widgets/icons/error.png" : icon));
    } catch(Exception e) {}
  }
  /** 
   * @see #signalLine(int, String)
   */
  public void signalLine(int line) {
    signalLine(line, null);
  }
  /** Efface toutes les marques mises sur le code. */
  public void removeLineSignals() {
    scrollPane.getGutter().removeAllTrackingIcons();
  }

  /** Lanceur du mécanisme d'éditon.
   * @param usage <tt>java org.javascool.widgets.TextEditor</tt>
   */
  public static void main(String[] usage) {
    if(usage.length == 0)
      new MainFrame().reset("editor", 800, 600, new TextEditor().setSyntax("jvs").addCompletions("org/javascool/gui/completion-macros.xml"));
  }
}
