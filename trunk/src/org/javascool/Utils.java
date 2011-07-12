/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
 *******************************************************************************/
package org.javascool;

// Used for URL formation
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;

// Used for URL read
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.StringBuilder;

// Used for URL write
import java.net.URLConnection;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.lang.System;

// Used to load icon
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

// Used for audio output
import java.applet.AudioClip;

// Used for string to name and file-name conversion
import java.util.HashMap;
import java.io.File;

// Used for the sax interface
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

// Used to report a throwable
import java.lang.reflect.InvocationTargetException;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/** This factory contains useful methods to interface javascool with the environment.
 * @see <a href="Utils.java.html">source code</a>
 * @serial exclude
 */
public class Utils {

    public static URLClassLoader classLoader = ((URLClassLoader) Thread.currentThread().getContextClassLoader());

    private Utils() {
    }
    private static final long serialVersionUID = 1L;
    static final String[] browsers = {"google-chrome", "firefox", "opera",
        "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla"};
    static final String errMsg = "Error attempting to launch web browser";

    /** Add a path to the ClassPath
     * Add a specified path to add to the context class loader
     * @param path The path to add
     */
    public static void addPathForClassLoader(String path) {
        URL[] urls = classLoader.getURLs();
        ArrayList<URL> newUrls = new ArrayList<URL>();
        newUrls.addAll(Arrays.asList(urls));
        try {
            newUrls.add(new File(path).toURI().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        URL[] newUrlsArray = new URL[newUrls.size()];
        for (int i = 0; i < newUrls.size(); i++) {
            newUrlsArray[i] = newUrls.get(i);
        }
        URLClassLoader newLoader = new URLClassLoader(newUrlsArray);
        classLoader = newLoader;
        Thread.currentThread().setContextClassLoader(newLoader);
    }

    public static void openURL(String url) {
        try {  //attempt to use Desktop library from JDK 1.6+
            Class<?> d = Class.forName("java.awt.Desktop");
            d.getDeclaredMethod("browse", new Class[]{java.net.URI.class}).invoke(
                    d.getDeclaredMethod("getDesktop").invoke(null),
                    new Object[]{java.net.URI.create(url)});
            //above code mimicks:  java.awt.Desktop.getDesktop().browse()
        } catch (Exception ignore) {  //library not available or failed
            String osName = System.getProperty("os.name");
            try {
                if (osName.startsWith("Mac OS")) {
                    Class.forName("com.apple.eio.FileManager").getDeclaredMethod(
                            "openURL", new Class[]{String.class}).invoke(null,
                            new Object[]{url});
                } else if (osName.startsWith("Windows")) {
                    Runtime.getRuntime().exec(
                            "rundll32 url.dll,FileProtocolHandler " + url);
                } else { //assume Unix or Linux
                    String browser = null;
                    for (String b : browsers) {
                        if (browser == null && Runtime.getRuntime().exec(new String[]{"which", b}).getInputStream().read() != -1) {
                            Runtime.getRuntime().exec(new String[]{browser = b, url});
                        }
                    }
                    if (browser == null) {
                        throw new Exception(Arrays.toString(browsers));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, errMsg + "\n" + e.toString());
            }
        }
    }

    /** Runs an operating-system command, waits until completion and returns the output.
     * @param command The operating systems command with its arguments separated by either tabulation ("\t" char) if any else space (" " char).
     * @param timeout Timeout in second or 0 if no timeout. Default is 10.
     * @return The command output (standard output and error output) including '\n'.
     *
     * @throws RuntimeException if an I/O exception occurs during command execution.
     * @throws IllegalStateException If the command returned status is non zero or if a time-out occurs.
     */
    public static String exec(String command, int timeout) {
        try {
            StringBuffer output = new StringBuffer();
            long time = timeout > 0 ? System.currentTimeMillis() + 1000 * timeout : 0;
            Process process = Runtime.getRuntime().exec(command.trim().split((command.indexOf('\t') == -1) ? " " : "\t"));
            InputStreamReader stdout = new InputStreamReader(process.getInputStream());
            InputStreamReader stderr = new InputStreamReader(process.getErrorStream());
            for (boolean waitfor = true; waitfor;) {
                waitfor = false;
                Thread.yield();
                while (stdout.ready()) {
                    waitfor = true;
                    output.append((char) stdout.read());
                }
                while (stderr.ready()) {
                    waitfor = true;
                    output.append((char) stderr.read());
                }
                if (!waitfor) {
                    try {
                        process.exitValue();
                    } catch (IllegalThreadStateException e1) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e2) {
                        }
                        waitfor = true;
                    }
                }
                if ((time > 0) && (System.currentTimeMillis() > time)) {
                    throw new IllegalStateException("Command {" + command + "} timeout (>" + timeout + "s) output=[" + output + "]\n");
                }
            }
            stdout.close();
            stderr.close();
            // Terminates the process
            process.destroy();
            try {
                process.waitFor();
            } catch (Exception e) {
            }
            Thread.yield();
            if (process.exitValue() != 0) {
                throw new IllegalStateException("Command {" + command + "} error #" + process.exitValue() + " output=[\n" + output + "\n]\n");
            }
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e + " when executing: " + command);
        }
    }
    /**/

    public static String exec(String command) {
        return exec(command, 10);
    }

    /** Converts a location to a well-formed URL.
     * @param location The location: either an URL or a local file-name or a ressource accessed by class code.
     *
     * @throws IllegalArgumentException If the URL is malformed.
     */
    public static URL toUrl(String location) {
        try {
            if (location.matches("(file|ftp|http|https|jar|mailto|stdout):.*")) {
                return new URL(location);
            }
            URL url = Thread.currentThread().getContextClassLoader().getResource(location);
            if (url != null) {
                return url;
            }
            File file = new File(location);
            if (file.exists()) {
                return new URL("file:" + file.getCanonicalPath());
            }
            return new URL("file:" + location);
        } catch (IOException e) {
            throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
        }
    }

    /** Loads an URL textual contents and returns it as a string.
     *
     * @param location A Universal Resource Location of the form: <table align="center">
     * <tr><td><tt>http:/<i>path-name</i></tt></td><td>to load from a HTTP location</td></tr>
     * <tr><td><tt>http:/<i>path-name</i>?param_i=value_i&amp;..</tt></td><td>to get a form answer</td></tr>
     * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>to load from a FTP site</td></tr>
     * <tr><td><tt>file:/<i>path-name</i></tt></td><td>to load from a file</td></tr>
     * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>to load from a JAR archive
     *  <div>(e.g.:<tt>jar:http://javascool.gforge.inria.fr/javascool.jar!/META-INF/MANIFEST.MF</tt>)</div></td></tr>
     * </table>
     *
     * @throws IllegalArgumentException If the URL is malformed.
     * @throws RuntimeException if an I/O exception has occurred.
     */
    public static String loadString(String location) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(toUrl(location).openStream()), 10240);
        StringBuilder buffer = new StringBuilder();
        char chars[] = new char[10240];
        while (true) {
            int l = reader.read(chars);
            if (l == -1) {
                break;
            }
            buffer.append(chars, 0, l);
        }
        return buffer.toString();
    }

    /** Saves a char-sequence in an URL textual contents.
     *
     * @param location @optional<"stdout:"> A Universal Resource Location of the form: <table>
     * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>to save onto a FTP site.</td></tr>
     * <tr><td><tt>file:/<i>path-name</i></tt></td><td>to save into a file.</td></tr>
     * <tr><td><tt>mailto:<i>address</i>?subject=<i>subject</i></tt></td><td>to send as an email in a readable form.</td></tr>
     * <tr><td><tt>stdout:/</tt></td><td>to print to the terminal standard output.</td></tr>
     * </table>
     *
     * @param string The string to save.
     *
     * @throws IllegalArgumentException If the URL is malformed.
     * @throws RuntimeException if an I/O exception has occurred.
     */
    public static void saveString(String location, String string) {
        if (location.startsWith("stdout:")) {
            System.out.println("\n" + location + " " + string);
            return;
        }
        location = toUrl(location).toString();
        try {
            OutputStreamWriter writer = location.startsWith("file:") ? getFileWriter(location.substring(5)) : getUrlWriter(location);
            for (int i = 0; i < string.length(); i++) {
                writer.write(string.charAt(i));
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e + " when saving: " + location);
        }
    }

    private static OutputStreamWriter getUrlWriter(String location) throws IOException {
        URL url = new URL(location);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        if (url.getProtocol().equals("mailto")) {
            int i = url.toString().indexOf("?subject=");
            if (i != -1) {
                writer.write("Subject: " + url.toString().substring(i + 9) + "\n");
            }
        }
        return writer;
    }

    private static OutputStreamWriter getFileWriter(String location) throws IOException {
        File file = new File(location), parent = file.getParentFile();
        if ((parent != null) && (!parent.isDirectory())) {
            parent.mkdirs();
        }
        return new OutputStreamWriter(new FileOutputStream(location), "UTF-8");
    }

    /** Returns an icon loaded from the applet context.
     * @param location The icon file name, in the context directory (directory on the server or on the client side or in the jar) or as a URL.
     * @return The related image icon or an empty icon if not loaded.
     *
     * @throws IllegalArgumentException If the URL is malformed.
     */
    public static ImageIcon getIcon(String location) {
        return new ImageIcon(toUrl(location));
    }

    /** Plays an audio clip (stop playing previous clip if the location is null).
     *
     * @param location A Universal Resource Location of the form: <table>
     * <tr><td><tt>http:/<i>path-name</i></tt></td><td>to load from a HTTP location</td></tr>
     * <tr><td><tt>ftp:/<i>path-name</i></tt></td><td>to load from a FTP site</td></tr>
     * <tr><td><tt>file:/<i>path-name</i></tt></td><td>to load from a file</td></tr>
     * <tr><td><tt>jar:/<i>jar-path-name</i>!/<i>jar-entry</i></tt></td><td>to load from a JAR archive</td></tr>
     * </table>
     *
     * @throws IOException if an I/O exception has occurred.
     */
    public static void play(String location) {
        try {
            if (clip != null) {
                clip.stop();
                clip = null;
            }
            if (location != null) {
                //clip = JsMain.newAudioClip(new URL(location));
                //clip.play();
            }
        } catch (Exception e) {
            throw new RuntimeException(e + " when playing: " + location);
        }
    }
    private static AudioClip clip;

    /** Converts a string to proper name.
     * <p>- A proper name is an identifier of lexical syntax: <tt>"[a-zA-Z_][a-zA-Z0-9_]*"</tt>.</p>
     * <p>- The construct <tt>toName(string) == string</tt> allows to check that the string is a proper name.</p>
     * @return The string itself if it is a proper name, else a new string: <ul>
     * <li>With punctuation replaced by <tt>'_'</tt></li>
     * <li>With some symbols replaced by their Latin name</li>
     * <li>With French accents letter replaced by the corresponding unaccentuated letter</li>
     * <li>With other spurious char replaced by the string <tt>null</tt></li>
     * <ul>
     */
    public static String toName(String string) {
        if (string == null) {
            return "null";
        }
        if (string.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return string;
        }
        if (string.length() == 0) {
            return "null";
        }
        String c_0 = string.substring(0, 1);
        String name = c_0.matches("[a-zA-Z_]") || string2name.containsKey(c_0) ? "" : "_";
        for (int i = 0; i < string.length(); i++) {
            String c_i = string.substring(i, i + 1);
            name += c_i.matches("[a-zA-Z0-9_]") ? c_i : string2name.get(c_i);
        }
        return name;
    }
    /** String2name conversion table.
     */
    private static HashMap<String, String> string2name;

    static {
        string2name = new HashMap<String, String>();
        string2name.put(" ", "_");
        string2name.put("!", "_");
        string2name.put("?", "_");
        string2name.put(",", "_");
        string2name.put(";", "_");
        string2name.put(".", "_");
        string2name.put("à", "a");
        string2name.put("â", "a");
        string2name.put("é", "e");
        string2name.put("ê", "e");
        string2name.put("è", "e");
        string2name.put("ë", "e");
        string2name.put("î", "i");
        string2name.put("ï", "i");
        string2name.put("ô", "o");
        string2name.put("ö", "o");
        string2name.put("ù", "u");
        string2name.put("ç", "c");
        string2name.put("+", "plus");
        string2name.put("-", "moins");
        string2name.put("&", "et");
        string2name.put("|", "ou");
    }

    /** Converts a file-name to a location with a proper file-name.
     * @param fileName The local fileName (not an URL) to be converted.
     * @param extension In false the extension is cancelled.
     * @return A <tt>file:</tt> location with a proper name and separator-char.
     * <p>- <tt>System.out.println</tt> messages in French to the user if the file-name has to be changed.</p>
     */
    public static String toFileName(String fileName, boolean extension) {
        File file = new File(fileName);
        String folder = file.getParent() == null ? System.getProperty("user.dir") : file.getParent();
        String name = file.getName().replaceAll("\\.[A-Za-z]+$", "");
        if (name.equals(file.getName())) {
            extension = false;
        }
        String ext = file.getName().replaceAll("^.*\\.([A-Za-z]+)$", "$1");
        String main;
        /*
        if(Jvs2Java.isReserved(name)) {
        main = "my_" + name;
        System.out.println("Attention: le nom \"" + name + "\" est interdit par Java,\n renommons le \"" + main + "\"");
        } else {
        main = toName(name);
        if(!main.equals(name))
        System.out.println("Attention: le nom \"" + name + "\" contient quelque caractère interdit par Java,\n renommons le \"" + main + "\"");
        }
         * 
         */
        main = "my_" + name;
        return "file:" + folder + File.separatorChar + main + (extension ? "." + ext : "");
    }

    /** Converts a XML string to another XML string using a XSL string.
     * <div>The <tt>saxon.jar</tt> must be in the path.</div>
     * @param xml The XML input string. If null simply cash the XSL.
     * @param xsl The XSL transformation string.
     * @return The tranformed string.
     *
     * @throws RuntimeException if an I/O exception has occurred.
     * @throws IllegalArgumentException if a syntax error has occurred.
     *
     */
    public static String xml2xml(String xml, String xsl) {
        // Compile the XSL tranformation
        try {
            if (!tranformers.containsKey(xsl)) {
                tranformers.put(xsl, tfactory.newTemplates(new StreamSource(new StringReader(xsl))).newTransformer());
            }
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e + " when compiling: " + xsl);
        }
        // Apply the transformation
        try {
            if (xml == null) {
                xml = "<null/>";
            }
            StringWriter writer = new StringWriter();
            tranformers.get(xsl).transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException e) {
            throw new IllegalArgumentException(e.getMessageAndLocation());
        }
    }
    // Cash mechanism
    private static TransformerFactory tfactory;
    private static HashMap<String, Transformer> tranformers = new HashMap<String, Transformer>();

    static {
        try {
            tfactory = TransformerFactory.newInstance();
            System.setProperty("javax.xml.parsers.SAXParserFactory", "com.icl.saxon.aelfred.SAXParserFactoryImpl");
            System.setProperty("javax.xml.transform.TransformerFactory", "com.icl.saxon.TransformerFactoryImpl");
            System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.icl.saxon.om.DocumentBuilderFactoryImpl");
        } catch (Throwable e) {
            System.err.println("Configuration error: " + e);
        }
    }

    /** Converts a HTML string to a XHTML string.
     * <p>- Accentuation is re-encoded in UTF-*, spurious constructs (comments, instructions, ..) are deleted and empty tags are terminated by '/>'.</p>
     * <p>- This is a fragible command in the sense that unexpected spurious or non-well formed HTML syntax may defeat it.</p>
     * @param htm The HTML string.
     * @return The XHTML converted string.
     */
    public static String htm2xml(String htm) {
        return htm. // Eliminate html accentuation
                replaceAll("&agrave;", "à").
                replaceAll("&acirc;", "â").
                replaceAll("&eacute;", "é").
                replaceAll("&egrave;", "è").
                replaceAll("&euml;", "ë").
                replaceAll("&ecirc;", "ê").
                replaceAll("&iuml;", "ï").
                replaceAll("&icirc;", "î").
                replaceAll("&ouml;", "ö").
                replaceAll("&ocirc;", "ô").
                replaceAll("&ugrave;", "ù").
                replaceAll("&ccedil;", "ç").
                // Eliminate spurious constructs
                replaceAll("<[!?][^>]*>", "").
                replaceAll("&nbsp;", " ").
                // Encapsulate non XML constructs
                replaceAll("(<(meta|img|hr|br|link)[^>/]*)/?>", "$1/>");
    }

    /** Reports a throwable with the related context.
     * @param error The error or exception to report.
     * @return A RuntimeException encapsulation this error or exception, if it has to be rethrown.
     */
    public static RuntimeException report(Throwable error) {
        if (error instanceof InvocationTargetException) {
            report(error.getCause());
        }
        System.out.println(error.toString());
        System.err.println(error.toString());
        for (int i = 0; i < 4 && i < error.getStackTrace().length; i++) {
            System.err.println(error.getStackTrace()[i]);
        }
        return error instanceof RuntimeException ? (RuntimeException) error : new RuntimeException(error);
    }
}