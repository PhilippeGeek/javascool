/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
 *******************************************************************************/
package org.javascool.pml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import java.util.Vector;
import javax.xml.transform.TransformerException;

/** Définit la syntaxe PML (Programmatic Markup Language) et son DOM (Data Object Model) Java.
 *
 * <p> Un contenu PML (pour «Programmatic Métadata Logicalstructure») et une structure-logique minimale (Parametric Minimal Logical-structure)
 * qui permet de définir les paramètres d'un objet numérique (algorithme, web-service) ou d'interfacer entre des applications. 
 * C'est une forme minimale de structure à-la XML.</p>
 * 
 * <p> Ses paramètres sont <ul>
 *   <li>Son <i>tag</i>, c'est à dire le nom qui définit son type.</li>
 *   <li>Des <i>attributs</i> c'est à dire des valeurs indexes par un nom.</li>
 *   <li>Des <i>éléments</i> c'est à dire des valeurs indexees par un entier non-négatif <tt>>= 0</tt>.</li>
 * </ul> Chaque valeur étant elle même un PML ou une chaîne de caractères. Rien de plus.</p>
 *
 * <p>La syntaxe est de la forme:
 * <div style="margin-left: 40px"><tt>"{tag name = value .. element .. }"</tt></div> où <ul>
 * <li>Les PML sont encapsules avec des accolades <tt>{</tt> .. <tt>}</tt>.</li>
 * <li>Les String avec des espaces, <tt>{</tt> ou <tt>}</tt> sont encapsulés avec des double quotes <tt>'"'</tt> (en utilisant <tt>'\"'</tt> pour y échapper).</li>
 * </ul>Cette syntaxe est minimale, proche des langages à accolades (<tt>C/C++, PHP, Java</tt>), facile à lire ou éditer et surtout complètement standar.</p>
 *
 * <p>L'analyse syntaxique de PML est <i>tolérante</i> au sens où une valeur est toujours obtenue sans générer d'erreur de syntaxe, 
 * en utilisant des valeurs par défaut: <ul>
 *  <li> La construction <tt>"name = value .. "</tt> sans accolade sera vue comme une liste de valeur de tag <tt>null</tt>,</li>
 *  <li> Un attribut sans valeur recevra la valeur <tt>true</tt>,</li>
 * </ul> etc..</p>
 *
 * @see <a href="Pml.java.html">source code</a>
 * @serial exclude
 */
public class Pml {
  // @bean
  private static final long serialVersionUID = 1L;
  public Pml() {  }

  private HashMap<String, Pml> data = new HashMap<String, Pml>();

  /** Initialise le PML en la lisant dans une chaîne de caractères.
   * @param value La chaîne de syntaxe <tt>"{tag name = value .. element .. }"</tt>.
   * @return Cet objet, permettant de définir la construction <tt>Pml pml= new Pml().reset(..)</tt>.
   */
  public Pml reset(String value) {
    // Initializes the Pml
    data = new HashMap<String, Pml>();
    tag = "";
    parent = null;
    count = -1;
    // Parses the string
    new PmlReader().read(value, this);
    return this;
  }

  /** Initialise le PML en en recopiant le PML en entrée.
   * @param pml Le PML à copier.
   */
  public Pml reset(Pml pml) {
    // Initializes the Pml
    data = new HashMap<String, Pml>();
    tag = "";
    parent = null;
    count = -1;
    if (pml != null) {
      setTag(pml.getTag());
      for (String name : pml.attributes()) {
	set(name, pml.getChild(name));
      }
      for (int i = 0; i < pml.getCount(); i++) {
	set(i, pml.getChild(i));
      }
    }
    return this;
  }

  /** Definit l'analyseur lexical qui lit la chaîne mot à mot en normlisant les espaces et en titant le caractère '"'. */
  protected static class TokenReader {

    /** Definit un élément lexical. */
    private static class token {

      String string;
      int line;

      token(String s, int i0, int i1, int l) {
	string = s.substring(i0, i1);
	line = l;
      }

      @Override
	public String toString() {
	return "#" + line + " \"" + string + "\" ";
      }
    }
    Vector<token> tokens;
    int itoken;

    /** Initialise le lecteur. */
    public TokenReader reset(String string) {
      // Initializes the buffer
      tokens = new Vector<token>();
      itoken = 0;
      // Split the string into tokens
      {
	char[] chars = string.toCharArray();
	for (int ichar = 0, ln = 0; ichar < chars.length;) {
	  // Skips spaces
	  while (ichar < chars.length && Character.isWhitespace(chars[ichar])) {
	    if (chars[ichar] == '\n') {
	      ln++;
	    }
	    ichar++;
	  }
	  if (ichar < chars.length) {
	    int ichar0 = ichar;
	    // Detects a quoted string taking "{" "}" and \" constructs into account
	    if (chars[ichar0] == '"') {
	      while (ichar < chars.length && (ichar == ichar0 || chars[ichar] != '"' || chars[ichar - 1] == '\\')) {
		ichar++;
	      }
	      ichar++;
	      int ichar1;
	      if ((ichar == ichar0 + 3) && ((chars[ichar0 + 1] == '{') || (chars[ichar0 + 1] == '}'))) {
		ichar1 = ichar;
	      } else {
		ichar0++;
		ichar1 = ichar - 1;
	      }
	      tokens.add(new token(string, ichar0, ichar1, ln));
	      // Detects a name
	    } else if (Character.isLetter(chars[ichar0]) || (chars[ichar0] == '_')) {
	      while (ichar < chars.length && (Character.isLetterOrDigit(chars[ichar]) || chars[ichar0] == '_')) {
		ichar++;
	      }
	      tokens.add(new token(string, ichar0, ichar, ln));
	      // Detects a number
	    } else if (Character.isDigit(chars[ichar0]) || (chars[ichar0] == '.')) {
	      while (ichar < chars.length && Character.isDigit(chars[ichar])) {
		ichar++;
	      }
	      if ((ichar < chars.length) && (chars[ichar] == '.')) {
		ichar++;
		while (ichar < chars.length && Character.isDigit(chars[ichar])) {
		  ichar++;
		}
	      }
	      if ((ichar < chars.length) && ((chars[ichar] == 'E') || (chars[ichar] == 'e'))) {
		ichar++;
		if ((ichar < chars.length) && ((chars[ichar] == '+') || (chars[ichar] == '-'))) {
		  ichar++;
		}
		while (ichar < chars.length && Character.isDigit(chars[ichar])) {
		  ichar++;
		}
	      }
	      tokens.add(new token(string, ichar0, ichar, ln));
	      // Detects operators and punctuation
	    } else if (isOperator(chars[ichar0])) {
	      while (ichar < chars.length && isOperator(chars[ichar])) {
		ichar++;
	      }
	      tokens.add(new token(string, ichar0, ichar, ln));
	    } else {
	      tokens.add(new token(string, ichar0, ++ichar, ln));
	    }
	  }
	}
      }
      itoken = 0;
      return this;
    }

    private static boolean isOperator(char c) {
      switch (c) {
      case '+':
      case '-':
      case '*':
      case '/':
      case '%':
      case '&':
      case '|':
      case '^':
      case '=':
      case '!':
      case '<':
      case '>':
      case '.':
      case ':':
	return true;
      default:
	return false;
      }
    }

    /** Renvoie un des éléments.
     * @param next Si 0 renvoie l'élément courant. Si 1 renvoie l'élément à suivre, etc..
     * @return L'élément ou '}' à l afin du fichier.
     */
    public String getToken(int next) {
      String current = itoken + next < tokens.size() ? tokens.get(itoken + next).string : "}";
      return current;
    }

    /** Teste si il y reste des éléments. */
    public boolean isNext() {
      return itoken < tokens.size();
    }

    /** Avance à un élément suivant. 
     * @param next Si 1 avance d'un élément, etc..
     */
    public void next(int next) {
      itoken += next;
    }
  
    /** Renvoie la fin de la chaîne. */
    public String trailer() {
      String t = "";
      while (itoken < tokens.size()) {
	t += " " + tokens.get(itoken++).string;
      }
      return t.trim();
    }

    /** Teste une condition de syntaxe. */
    public void check(boolean ok, String message) {
      if (!ok) {
	System.out.println("Erreur de syntaxe \"" + message + "\", ligne " + (itoken < tokens.size() ? "" + tokens.get(itoken).line + " vers \"" + getToken(0) + "\"" : "finale"));
      }
    }

    @Override
      public String toString() {
      String s = "[";
      for (int i = 0; i < tokens.size(); i++) {
	s += (i == itoken ? " ! " : " ") + "\"" + tokens.get(i).string + "\"#" + tokens.get(i).line;
      }
      return s + " ]";
    }
  }

  /** Définit un lecteur de PML. */
  private static class PmlReader extends TokenReader {

    /** Lit la chaîne et en affecte les valeurs du PML. */
    public void read(String string, Pml pml) {
      reset(string);
      // Parses the string
      parse(pml);
      // Detects the trailer if any
      String trailer = trailer();
      if (trailer.length() > 0) {
	Pml p = new Pml();
	p.setTag(trailer);
	pml.set("string_trailer", p);
      }
    }

    /** Effectue l'analyse syntaxique récursive. */
    private Pml parse(Pml pml) {
      String b = getToken(0);
      // Parses a { } Pml construct
      if ("{".equals(b)) {
	next(1);
	for (boolean start = true; true; start = false) {
	  String t = getToken(0);
	  if ("}".equals(t)) {
	    next(1);
	    break;
	    // Adds an element
	  } else if ("{".equals(t)) {
	    Pml p = new Pml();
	    parse(p);
	    pml.add(p);
	    // Adds an attribute
	  } else if ("=".equals(getToken(1))) {
	    next(2);
	    if ("}".equals(getToken(0))) {
	      pml.set(t, "true");
	    } else {
	      Pml p = new Pml();
	      parse(p);
	      pml.set(t, p);
	    }
	    // Adds an attribute tag
	  } else if (start) {
	    next(1);
	    pml.setTag(t);
	    // Adds an atomic element
	  } else {
	    Pml p = new Pml();
	    parse(p);
	    pml.add(p);
	  }
	}
	// Considers the Pml as a list of name=value
      } else if ("=".equals(getToken(1))) {
	while("=".equals(getToken(1))) {
	  String t = getToken(0);
	  next(2);
	  if ("}".equals(getToken(0))) {
	    pml.set(t, "true");
	  } else {
	    Pml p = new Pml();
	    parse(p);
	    pml.set(t, p);
	  }
	}
	// Considers the Pml as a simple string
      } else {
	pml.setTag(b);
	next(1);
      }
      return pml;
    }
  }


  /** Retourne le PML sous forme de chaîne de caractères.
   * @param inline Si true retourne une chaîne 1D de longueur minimale. Si faux retourne une chaîne 2D formattée.
   * @return La chaîne qui représente le PML.
   */
  public String toString(boolean inline) {
    return inline ?  new PlainWriter().toString(this, 0) : new PlainWriter().toString(this, 180);
  }

  @Override
    public final String toString() {
    return toString(true);
  }

  /** Définit le convertisseur de PML en chaîne de caractères. */
  private static class PlainWriter {

    private StringBuffer string;
    int width, l;

    /** Convertit le PML en chaîne.
     * @param pml Le PML à convertir.
     * @param width si width == 0 retourne une chaîne 1D de longueur minimale, sinon retourne une chaîne 2D de la largeur donnée.
     * @return La chaîne générée.
     */
    public String toString(Pml pml, int width) {
      if (pml == null) {
	return "null";
      }
      // Initializes the variables
      string = new StringBuffer();
      if (width == 0) {
	write1d(pml);
      } else {
	this.width = width;
	l = 0;
	write2d(pml, 0, 0);
      }
      return string.toString();
    }

    private void write1d(Pml pml) {
      if (pml == null) {
	string.append(" {null} ");
	return;
      }
      string.append("{").append(quote(pml.getTag()));
      for (String name : pml.attributes()) {
	string.append(" ").append(quote(name)).append("=");
	write1d(pml.getChild(name));
      }
      for (int n = 0; n < pml.getCount(); n++) {
	string.append(" ");
	write1d(pml.getChild(n));
      }
      string.append("}");
    }
    private boolean write2d(Pml pml, int n, int tab) {
      if (pml == null) {
	string.append(" {null} ");
	return false;
      }
      if (pml.getSize() == 0) {
	boolean ln = n >= 0 && (n == 0 || (pml.getParent() != null && pml.getParent().getChild(n - 1) != null && pml.getParent().getChild(n - 1).getSize() > 0));
	writeln(ln, tab);
	write(quote(pml.getTag()), tab);
	return ln;
      } else {
	boolean ln = pml.getTag().length() > 1 || "p".equals(pml.getTag());
	writeln(n >= 0 && ln, tab);
	write("{" + quote(pml.getTag()), tab);
	ln = false;
	for (String name : pml.attributes()) {
	  write(" " + quote(name) + " =", tab);
	  ln |= write2d(pml.getChild(name), -1, tab + 1);
	}
	for (int i = 0; i < pml.getCount(); i++) {
	  ln |= write2d(pml.getChild(i), i, tab + 2);
	}
	writeln(ln, tab);
	write("}", tab);
	return ln;
      }
    }
    private void writeln(boolean ln, int tab) {
      if (ln) {
	string.append("\n");
	for (int t = 0; t < tab; t++) {
	  string.append(" ");
	}
	l = tab;
      } else {
	string.append(" ");
      }
    }
    private void write(String word, int tab) {
      if (l + word.length() > width) {
	writeln(false, tab + 1);
      }
      string.append(word);
      l += word.length();
    }

    /** Retourne la chaîne en tenant compte des "{" "}" et \". */
    private static String quote(String string) {
      return 
	string == null ? "null" : 
	string.matches("[a-zA-Z_][a-zA-Z0-9_]*") || "\"{\"".equals(string) || "\"}\"".equals(string) ? string :
	"\"" + string.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"") + "\"";
    }
  }

  /** Renvoie le type de ce PML.
   * @return The tag définit lors de l'initialisation, sinon le nom de la classe du PML.
   */
  public final String getTag() {
    return tag;
  }

  protected final Pml setTag(String value) {
    tag = value;
    return this;
  }
  private String tag = getClass().getName();

  /** Renvoie le parent du PML si défini.
   * @return Si ce PML est un sous-partie d'un PML renvoie son parent, sinon renvoie null.
   */
  public final Pml getParent() {
    return parent;
  }

  private void setParent(Pml value) {
    if (parent == null) {
      parent = value;
    }
  }
  private Pml parent = null;

  /** Teste si un paramètre de ce PML est défini.
   * <p>Cet appel est formellement équivalent à <tt>getChild(name) != null</tt></p>
   * @param name Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @return True si le paramètre est défini, false sinon.
   */
  public final boolean isDefined(String name) {
    return data.containsKey(name);
  }
 // @variant
  public final boolean isDefined(int index) {
    return isDefined(Integer.toString(index));
  }

  /** Renvoie la valeur d'un paramètre de ce PML.
   * @param name Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @return La valeur du paramètre, ou null si indéfini.
   */
  public Pml getChild(String name) {
    return data.get(name);
  }
  // @variant
  public final Pml getChild(int index) {
    return getChild(Integer.toString(index));
  }

  /** Renvoie la valeur d'un paramètre de ce PML en tant que chaîne.
   * @param name  Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @param value La valeur par défaut, sinon "".
   * @return La valeur de ce paramètre, si défini, sinon sa valeur par défaut.
   */
  public final String getString(String name, String value) {
    if( data.get(name)==null){
      return "";
    }
    String v = data.get(name).toString();
    if(v.startsWith("{\"")){
      v=v.substring(2,v.length()-2);
    } else {
      v=v.substring(1,v.length()-1);
    }
    return v != null ? v : value != null ? value : "";
  }
  // @variant
  public final String getString(int index, String value) {
    return getString(Integer.toString(index), value);
  }
  // @variant
  public final String getString(String name) {
    return getString(name, null);
  }
  // @variant
  public final String getString(int index) {
    return getString(index, null);
  }

  /** Renvoie la valeur d'un paramètre de ce PML en tant que décimal.
   * @param name  Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @param value La valeur par défaut, sinon "0".
   * @return La valeur de ce paramètre, si défini, sinon sa valeur par défaut.
   */
  public final double getDecimal(String name, double value) {
    try {
      return Double.parseDouble(getString(name, "0"));
    } catch (NumberFormatException e) {
      return value;
    }
  }
  // @variant
  public final double getDecimal(int index, double value) {
    return getDecimal(Integer.toString(index), value);
  }
  // @variant
  public final double getDecimal(String name) {
    return getDecimal(name, 0);
  }
  // @variant
  public final double getDecimal(int index) {
    return getDecimal(index, 0);
  }

  /** Renvoie la valeur d'un paramètre de ce PML en tant qu'entier.
   * @param name  Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @param value La valeur par défaut, sinon "0".
   * @return La valeur de ce paramètre, si défini, sinon sa valeur par défaut.
   */
  public final int getInteger(String name, int value) {
    try {
      return Integer.parseInt(getString(name, "0"));
    } catch (NumberFormatException e) {
      return value;
    }
  }
  // @variant
  public final int getInteger(int index, int value) {
    return getInteger(Integer.toString(index), value);
  }
  // @variant
  public final int getInteger(String name) {
    return getInteger(name, 0);
  }
  // @variant
  public final int getInteger(int index) {
    return getInteger(index, 0);
  }

  /** Définit la valeur d'un paramètre de ce PML.
   * @param name  Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @param value La valeur du paramètre (en tant que PML, entier, décimal ou entier).
   * @return Cet objet, permettant de définir la construction <tt>Pml pml= new Pml().reset(..)</tt>.
   */
  public Pml set(String name, Pml value) {
    // Deletes the attribute value
    if (value == null) {
      try {
	// Shifts removed elements to avoid "null wholes"
	int i = Integer.parseInt(name), l = getCount() - 1;
	if ((0 <= i) && (i <= l)) {
	  for (int j = i; j < l; j++) {
	    data.put(Integer.toString(j), data.get(Integer.toString(j + 1)));
	  }
	  data.remove(Integer.toString(l));
	} else {
	  data.remove(name);
	}
      } catch (NumberFormatException e) {
	data.remove(name);
      }
      // Adds the parameter value
    } else {
      data.put(name, value);
      value.setParent(this);
    }
    count = -1;
    return this;
  }
  // @variant
  public final Pml set(int index, Pml value) {
    return set(Integer.toString(index), value);
  }
  // @variant
  public final Pml set(String name, String value) {
    Pml v = new Pml();
    v.reset(value);
    return set(name, v);
  }
  // @variant
  public final Pml set(int index, String value) {
    return set(Integer.toString(index), value);
  }
  // @variant
  public final Pml set(String name, double value) {
    return set(name, Double.toString(value));
  }
  // @variant
  public final Pml set(int index, double value) {
    return set(Integer.toString(index), value);
  }
  // @variant
  public final Pml set(String name, int value) {
    return set(name, Integer.toString(value));
  }
  // @variant
  public final Pml set(int index, int value) {
    return set(Integer.toString(index), value);
  }

  /** Elimine la valeur d'un paramètre de ce PML.
   * <p>Cet appel est formellement équivalent à <tt>set(name, null);</tt></p>
   * @param name  Le nom de l'attribut ou l'index de l'élément (sous forme de chaîne ou d'entier).
   * @return Cet objet, permettant de définir la construction <tt>Pml pml= new Pml().reset(..)</tt>.
   */
  public Pml del(String name) {
    return set(name, (Pml) null);
  }
  // @variant
  public final Pml del(int index) {
    return set(Integer.toString(index), (Pml) null);
  }

  /** Ajoute un élément à ce PML.
   * <p>Cet appel est formellement équivalent à <tt>set(getCount(), value);</tt></p>
   * @param value La valeur du paramètre (en tant que PML, entier, décimal ou entier).
   * @return Cet objet, permettant de définir la construction <tt>Pml pml= new Pml().reset(..)</tt>.
   */
  public final Pml add(Pml value) {
    int c = getCount();
    set(c, value);
    count = ++c;
    return this;
  }
  // @variant
  public final Pml add(String value) {
    Pml v = new Pml();
    v.reset(value);
    return add(v);
  }
  // @variant
  public final Pml add(double value) {
    return add(Double.toString(value));
  }
  // @variant
  public final Pml add(int value) {
    return add(Integer.toString(value));
  }

  /** Renvoie le nombre d'éléments de ce PML.
   * @return Le nombre d'éléments (indépendamment des attributs), les éléments null étant éliminés
   */
  public int getCount() {
    if (count < 0) {
      count = 0;
      for (String key : data.keySet()) {
	if (isIndex(key)) {
	  count = Math.max(Integer.parseInt(key) + 1, count);
	}
      }
    }
    return count;
  }
  private int count = -1;

  /** Renvoie le nombre de paramètres de ce PML.
   * @return Le nombre d'attributs et d'éléments. Si 0, ce PML correspond uniquement à une chaîne: son tag.
   */
  public int getSize() {
    return data.size();
  }

  /** Définir un itérateur sur les attributs de ce PML.
   * <p>- Les attributes sont énumérés avec une construction de la forme: <tt>for(String name : pml.attributes()) { Pml value = pml.getChild(name); .. }</tt>.</p>
   * <p>- Les éléments sont énumérés avec une construction de la forme:  <tt>for(int n = 0; n &lt; pml.getCount(); n++) { Pml value = pml.getChild(n); .. }</tt>.</p>
   */
  public final Iterable<String> attributes() {
    return new Iterable<String>() {

      @Override
	public Iterator<String> iterator() {
	return new Iterator<String>() {

	  Iterator<String> keys = data.keySet().iterator();
	  String key;

	  {
	    nextKey();
	  }

	  private void nextKey() {
	    for (key = null; keys.hasNext() && isIndex(key = keys.next()); key = null) {
	    }
	  }

	  @Override
	    public String next() {
	    String value = key;
	    nextKey();
	    return value;
	  }

	  @Override
	    public boolean hasNext() {
	    return key != null;
	  }

	  @Override
	    public void remove() {
	    throw new UnsupportedOperationException();
	  }
	};
      }
    };
  }
  // @return true if the name is an index

  private static boolean isIndex(String name) {
    return index.matcher(name).matches();
  }
  static Pattern index = Pattern.compile("[0-9]+");
}
