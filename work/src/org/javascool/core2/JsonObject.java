package org.javascool.core2;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/** Implements an integrated JSON object interface. 
 * <p>Use the <a href="http://www.json.org/java">json.jar</a> implementation of <a href="http://www.json.org">json</a>.</p>
 * <p>The present object model allows to simplify the interface with Json structures with: <ul>
 * <li>One language extension : multi-lines strings starting with "\`\n" (a «\`» at the end of a line) and ending with "\n\`" (a «\`» at the beginning of a line).</li>
 * <li>A restriction, regarding the use of arrays (enclosed between «[» and «]») as either a list of string or alist of object, only.</li>
 * </ul></p>
 * With this restriction, validity of the syntax (i.e. correctnes of the structure with respect to a given format), 
 * is easy to checked in a programmatic way, and the string values are automatically pre-formated.
 */
class JsonObject {

  /** Creates an empty JSONObject. */
  public JsonObject() {
    this(new JSONObject());
  }

  /** Encapsulates a JSONObject. */
  private JsonObject(JSONObject object) {
    this.object = object;
  }
  private JSONObject object;

  /** Returns an object parsed from a string.
   * @param text The textual specification of the Json object, i.e. a t-uples starting with a '{'.
   * <p>In this context, arrays are either t-uples array or string arrays, not heterogenous list of items.</p>
   * @throw IllegalArgumentException If the textual specification structure is not well-formed (lexical errors).
   * @return The parsed object.
   */
  static public JsonObject parseObject(String text) {
    try {
      text = strings2array(text);
      return new JsonObject(new JSONObject(text));
    } catch(JSONException e) {
      throw new IllegalArgumentException("JSON structure lexical error : "+e.getMessage()+" of:\n"+withLineCount(text));
    }
  }

  /** Returns an object array parsed from a string.
   * @param text The textual specification of the Json object, i.e. an array starting with a '['.
   * @throw IllegalArgumentException If the textual specification structure is not well-formed (lexical errors).
   * @return The parsed object.
   */
  static public JsonObject[] parseObjectArray(String text) {
    try {
      text = strings2array(text);
      return getObjectArray(new JSONArray(text));
    } catch(JSONException e) {
      throw new IllegalArgumentException("JSON structure lexical error : "+e.getMessage()+" of:\n"+withLineCount(text));
    }
  }

  /** Checks the object syntax and returns an illegal-argument exception if any error.
   * <p>The syntax is defined by an array of t-uples defining the object key: its "name", "type" and, if the field is required, a "mandatory" : "true" boolean.</p>
   * <p>The following types are defined: <ul>
   *   <li>"object" : if the field is a t-uples object, default value being an empty object.</p>
   *   <li>"object-array" : if the field is an array of t-uples object, default value being an empty array.</p>
   *   <li>"string" : if the field is a string given as a string, a string array or a multi-line string enclosed between <tt>`\n</tt> .. <tt>\n`</tt>, default value being the empty string.</li>
   *   <li>"string-array" : if the field is a string array, default value being an empty array.</li>
   *   <li>"boolean" : if the field is a boolean value, default value being <tt>false</tt>.</li>
   *   <li>"number" : if the field is a numerical value, default value being <tt>0</tt>.</li>
   *   <li>[ " ../.. ] : a list of names, if the value is to beb taken in an enumeration, default value being "", the empty string.</li>
   * </ul>
   * <p>The syntax of the syntax token is thus :<pre>[ 
   * { "name" : "name", "type" : "string", "mandatory" : "true" },
   * { "name" : "type", "type" : [ "object", "object-array", "string", "string-array", "boolean", "number" ], "mandatory" : "true" },
   * { "name" : "mandatory", "type" : "boolean" }
   *]</pre>
   * @param syntax A textual description of the syntaax.
   * @throw IllegalArgumentException If the textual specification structure is not valid (syntax errors).
   * @return This object, allowing construct like <tt>parseObject(text).check(syntax);</tt>.
   */
  public JsonObject check(JsonObject[] syntax) {
    // Checks the syntax of the syntax
    if (syntax != syntaxSyntax) {
      for (JsonObject syntaxItem : syntax)
	syntaxItem.check(syntaxSyntax);
    }
    // Checks that an object has no spurious key-name outside the given keys array.
    {
      // Creates a key-set
      java.util.Set<String> definedKeys = new java.util.HashSet<String>();
      for (JsonObject syntaxItem : syntax)
	definedKeys.add(syntaxItem.getString("name"));
      // Interates on existing keys
      for(java.util.Iterator k = object.keys(); k.hasNext();) {
	String key = k.next().toString();
	check(definedKeys.contains(key), "spurious key '"+key+"' found in object "+toString());
      }
    }
    // Checks that all mandatory keys are defined
    {
      for (JsonObject syntaxItem : syntax)
	check(!syntaxItem.has("mandatory") || object.has(syntaxItem.getString("name")),
	      "the mandatory key '"+syntaxItem.getString("name")+"' is undefined in object "+toString());
    }
    // Checks for type correctness
    {
      for (JsonObject syntaxItem : syntax) {
	String name = syntaxItem.getString("name");
	if (object.has(name)) {
	  String type = syntaxItem.getString("type").replaceAll("\\s\"", "").replaceAll(",", "|").replaceAll("\\[", "(").replaceAll("]", ")");
	  String value = object.optString(name);
	  boolean ok = true;
	  check(
		"object".equals(type) ? object.optJSONObject(name) != null :
		"object-array".equals(type) ? object.optJSONArray(name) != null && isObjectArray(object.optJSONArray(name)) :
		"string".equals(type) ? true :
		"string-array".equals(type) ? object.optJSONArray(name) != null && isStringArray(object.optJSONArray(name)) :
		"boolean".equals(type) ? value.toLowerCase().matches("^(true|false)$") :
		"number".equals(type) ? value.toLowerCase().matches("[+-]?[0-9]+\\.[0-9]*(e[+-]?[0-9]+)?") :
		value.matches(type),
		"the key '"+name+"' is not of type "+type+" in "+toString());
	}
      }
    }
    return this;
  }
  private static final JsonObject[] syntaxSyntax = JsonObject.parseObjectArray("["+ 
    "{ \"name\" : \"name\", \"type\" : \"string\", \"mandatory\" : \"true\" },"+
    "{ \"name\" : \"type\", \"type\" : \"string\", \"mandatory\" : \"true\" },"+
    "{ \"name\" : \"mandatory\", \"type\" : \"boolean\" }" +
    "]");

  /** Checks if a given key is defined. */
  public boolean has(String key) {
    return object.has(key);
  }
  
  /** Returns this object keys as an array. */
  public String[] keys() {
    String names[] = new String[object.length()];
    int i = 0;
    for(java.util.Iterator k = object.keys(); k.hasNext();)
      names[i++] = k.next().toString();
    return names;
  }

  /** Returns the Json object associated to a key, or an empty object if undefined. */
  public JsonObject getObject(String key) {
    JSONObject value = object.optJSONObject(key);
    return value != null ? new JsonObject(value) : new JsonObject();
  }

  /** Returns the Json object array associated to a key, or an empty array or empty object if undefined. */
  public JsonObject[] getObjectArray(String key) {
    return getObjectArray(object.optJSONArray(key));
  }
  /** Tests if objects is an object array. */
  private static boolean isObjectArray(JSONArray objects) {
    for(int i = 0; i < objects.length(); i++) 
      if(objects.optJSONObject(i) == null)
	return false;
    return true;
  }
  /** Converts a JSONArray to an object array. */
  private static JsonObject[] getObjectArray(JSONArray objects) {
    if (objects != null) {
      JsonObject[] values = new JsonObject[objects.length()];
      for(int i = 0; i < objects.length(); i++) 
	values[i] = new JsonObject(objects.optJSONObject(i));
      return values;
    } else
      return new JsonObject[0];
  }

  /** Returns a string value associated to a key, either one string or an array of strings concatenated or the defaul-value if defined, else the empty string. */
  public String getString(String key, String defaultValue) {
    String value = "";
    if (object.has(key)) {
      JSONArray values = object.optJSONArray(key);
      if (values != null) 
	for (int i = 0; i < values.length(); i++) 
	  value += values.optString(i) + "\n";
      else
	value = object.optString(key);
    }
    return "".equals(value) ? defaultValue : value;
  }
  /**
   * @see #getString(String, String)
   */
  public String getString(String key) {
    return getString(key, "");
  }

  /** Returns a string array associated to a key with either one string or an array of strings as value. */
  public String[] getStringArray(String key) {
    return getStringArray(object.optJSONArray(key));
  }
 /** Tests if objects is a string array. */
  private static boolean isStringArray(JSONArray objects) {
    for(int i = 0; i < objects.length(); i++) {
      try { objects.getString(i); } catch(Exception e) { return false; }
    }
    return true;
  }
  /** Converts a JSONArray to a string array. */
  private static String[] getStringArray(JSONArray values) {
    if (values != null) {
      String v[] = new String[values.length()];
      for (int i = 0; i < values.length(); i++) 
	v[i] = values.optString(i);
      return v;
    } else
      return new String[0];
  }
  
  /** Returns the boolean value associated with a key (true or false value, case insensitive), or the defaultValue is undefined, else false. */
  public boolean getBoolean(String key, boolean defaultValue) {
    return object.optBoolean(key, defaultValue);
  }
  /**
   * @see etBoolean(String, boolean)
   */
  public boolean getBoolean(String key) {
    return getBoolean(key, false);
  }  

  /** Returns the numerical value associated with a key, or the defaultValue if undefined or the value is not a number. */
  public double getDouble(String key, double defaultValue) {
    return object.optDouble(key, defaultValue);
  }

  /** Returns the numerical value associated with a key, or the defaultValue if undefined or the value is not a number. */
  public int getInt(String key, int defaultValue) {
    return object.optInt(key, defaultValue);
  }

  /** Sets one parameter value. 
   * @param key The parameter key.
   * @param value The parameter value, or null to delete it. The value can be a boolean, numeric value, String of String array, JsonObject or JsonObject array.
   * @return This object, allowing construct like <tt>new JsonObject().put(key, value).</tt>. 
   * @throw IllegalArgumentException If the value JSON syntax is wrong.
   */
  public JsonObject put(String key, Object value) {
    try {
      if (value == null) {
	object.remove(key);
      } else if (value instanceof JsonObject[]) {
	JSONArray array = new JSONArray();
	for(JsonObject v : (JsonObject[]) value)
	  array.put(((JsonObject) v).object);
	object.put(key, array);
      } else if (value instanceof JsonObject) {
	object.put(key, ((JsonObject) value).object);
      } else if (value instanceof String[]) {
	JSONArray array = new JSONArray();
	for(String v : (String[]) value)
	  array.put(v);
	object.put(key, array);
      } else {
	object.put(key, value);
      }
    } catch(JSONException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this;
  }
  /**
   * @see #put(java.lang.String, java.lang.Object);
   */
  public JsonObject put(String key, boolean value) {
    try {
      object.put(key, value);
    } catch(JSONException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this;
  }
  /**
   * @see #put(java.lang.String, java.lang.Object);
   */
  public JsonObject put(String key, int value) {
    try {
      object.put(key, value);
    } catch(JSONException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this;
  }
  /**
   * @see #put(java.lang.String, java.lang.Object);
   */
  public JsonObject put(String key, long value) {
    try {
      object.put(key, value);
    } catch(JSONException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this;
  }
  /**
   * @see #put(java.lang.String, java.lang.Object);
   */
  public JsonObject put(String key, double value) {
    try {
      object.put(key, value);
    } catch(JSONException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this;
  }

  /** Returns this object a space normalized string. */
  public String toString() {
    return object.toString().replaceAll("\\s+", " ");
  }

  /** Converts a multi-line strings enclosed with `` to a standard json array. */
  private static String strings2array(String text) {
    for(int i0 = 0; (i0 = text.indexOf("`\n", i0)) != -1;) {
      int i1 = text.indexOf("\n`", i0);
      check(i1 != -1, "unterminated multi-line strings : a \"\n`\" is missing after a \"`\n\"");
      String text0 = text.substring(0, i0), lines[] = text.substring(i0+2, i1 >= i0+2 ? i1 : i0+2).split("\n"), text1 = text.substring(i1+2);
      text = text0 + "[\n";
      for(String line : lines)
	text += "  \"" + line.replaceAll("\\\\", "\\\\\\\\").replaceAll("([\"/])", "\\\\$1") + "\",\n";
      i0 = text.length() + 2;
      text += " \"\"]\n" + text1;
    }
    return text;
  }

  /** Returns the text with lines prefixed with line count. */
  private static String withLineCount(String text) { 
    String lines[] = text.split("\n"), listing = "";
    for(int i = 0; i < lines.length; i++) {
      String n = ""+i; while(n.length() < 4) n = " " +n;
      listing += n+" : "+lines[i]+"\n";
    }
    return listing;
  }

  /** Checks a condition to be true regarding the object specification and throw an illegal-argumentexception if unverified. */
  public static void check(boolean condition, String errorMessage) {
    if (!condition) throw new IllegalArgumentException(errorMessage);
  }
}
