/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
 *******************************************************************************/
package org.javascool.tools;

import java.applet.Applet;
import org.javascool.widgets.Console;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.javascool.JVSMain;
import org.javascool.gui.JVSFileEditorTabs;
import org.javascool.gui.JVSMainPanel;

/** Contain useful function for help student to program
 * This can also be used by proglet-makers if they import org.javascool.tools.Maxros
 * and prefix method calls with 'Method.'
 * @serial exclude
 */
public class Macros {

    /**
     * Internal use only
     * @param filePath The path in which to look
     * @param fileName The filename to open
     * @return and InputStream or null
     */
    private static InputStream tryFile(String filePath, String fileName) {
        File inJvsDir = new File(filePath + "/" + fileName);
        if (inJvsDir.exists()) {
            try {
                return (new FileInputStream(filePath + "/" + fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Macros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Tries to find the specified file. If found, the file is opened and a stream is returned
     * @param fileName The file to look for
     * @return A stream pointing to the specified file. Remember to close it after use !
     */
    public static InputStream getResource(String fileName) {
        /* 
         * Try .jvs dir
         */
        InputStream ret;

        String path = JVSMainPanel.getEditorTabs().getFile(JVSFileEditorTabs.getCurrentCompiledFile()).getPath().replaceAll(File.separator, "/");
        path = path.replaceAll("^(.*)/[^/]+$", "$1");
        ret = tryFile(path, fileName);
        if (ret != null) {
            return ret;
        }

        ret = tryFile(System.getProperty("user.dir"), fileName);
        if (ret != null) {
            return ret;
        }

        ret = tryFile(System.getProperty("user.home"), fileName);
        if (ret != null) {
            return ret;
        }

        ret = ClassLoader.getSystemResourceAsStream("org/javascool/proglets/" + JVSMainPanel.getCurrentProglet().getPackageName() + "/" + fileName);
        if (ret != null) {
            return ret;
        }

        return null;
    }

    /**
     * //REVIEW
     * Looks to me like a duplicate of getResource. Maybe make a clever merge ?
     * Maybe getUserResource should become getFileResource and look in the same places as getResource except it only 
     * looks for files and does not output an InputStream but a File ?
     * See issue #45
     * @param location The file to look for
     * @return A file if found, otherwise null
     */
    public static File getUserResource(String location) {
        try {
            Utils.addPathForClassLoader(JVSMainPanel.getEditorTabs().getFile(JVSMainPanel.getEditorTabs().getCurrentFileId()).getFile().getParentFile().toURI().toString());
            return new File(Utils.classLoader.findResource(location).toURI());
        } catch (URISyntaxException ex) {
            System.out.println("erreur");
            //Logger.getLogger(Macros.class.getName()).log(Level.SEVERE, null, ex);
            return new File(".");
        }
    }

    /**
     * Private default constructor
     */
    private Macros() {
    }

    /** 
     * Returns the singleton for JVSMainPanel. Not really useful except for setup
     * as JVSMainPanel has nearly only static members.
     * @see JVSMainPanel
     * @return The JVSMainPanel singleton
     */
    public static JVSMainPanel getJvs() {
        return org.javascool.gui.JVSMainPanel.getThisInStatic();
    }

    /**
     * Outputs a String onto the console
     * @param string The string to output
     */
    public static void echo(String string) {
        System.out.println(string);
    }

    /**
     * Outputs an int onto the console
     * @param i The int to output
     */
    public static void echo(int i) {
        echo("" + i);
    }

    /**
     * Outputs a double onto the console
     * @param d The string to output
     */
    public static void echo(double d) {
        echo("" + d);
    }

    /**
     * Outputs a String onto the console
     * @param b The boolean to output
     */
    public static void echo(boolean b) {
        echo("" + b);
    }

    /**
     * Outputs a map to the console
     * @param <A> The map template
     * @param <B> The map template
     * @param map The map to output
     */
    public static < A, B> void echo(java.util.Map<A, B> map) {
        echo("" + map);
    }

    /**
     * Outputs a nicely formatted list onto the console
     * @param <A> The list's template
     * @param list The list to output
     */
    public static < A> void echo(java.util.List<A> list) {
        echo("" + list);
    }

    /**
     * Outputs a set to the console
     * @param <A> The set's template
     * @param set The set to output
     */
    public static < A> void echo(java.util.Set<A> set) {
        echo("" + set);
    }

    /**
     * @see echo(String)
     */
    public static void println(String string) {
        System.out.println(string);
    }

    /**
     * @see echo(int)
     */
    public static void println(int i) {
        println("" + i);
    }

    /**
     * @see echo(double)
     */
    public static void println(double d) {
        println("" + d);
    }

    /**
     * @see echo(boolean)
     */
    public static void println(boolean b) {
        println("" + b);
    }

    /**
     * @see echo(java.util.Map)
     */
    public static < A, B> void println(java.util.Map<A, B> map) {
        println("" + map);
    }

    /**
     * @see echo(java.util.List)
     */
    public static < A> void println(java.util.List<A> list) {
        println("" + list);
    }

    /**
     * @see echo(java.util.Set)
     */
    public static < A> void println(java.util.Set<A> set) {
        println("" + set);
    }

    /** 
     * Renvoie un nombre entier aléatoire uniformément distribué entre deux valeurs (maximum inclus).
     * @param min Borne inférieure du générateur de nombres aléatoires (excluse)
     * @param max Borne supérieure (incluse)
     * @return Le nombre entier tiré au hasard.
     * @see randomInteger(int,int)
     */
    public static int random(int min, int max) {
        return randomInteger(min, max);
    }

    /** 
     * Renvoie un nombre entier aléatoire uniformément distribué entre deux valeurs (maximum inclus).
     * @param min Borne inférieure du générateur de nombres aléatoires (excluse)
     * @param max Borne supérieure (incluse)
     * @return Le nombre entier tiré au hasard.
     * @see random(int,int)
     */
    public static int randomInteger(int min, int max) {
        return (int) Math.floor(min + (0.99999 + max - min) * Math.random());
    }

    /** 
     * Renvoie un nombre décimal aléatoire uniformément distribué entre deux valeurs (maximum inclus).
     * @param min Borne inférieure du générateur de nombres aléatoires (excluse)
     * @param max Borne supérieure (incluse)
     * @return Le nombre décimal tiré au hasard.
     * @see random(int,int)
     */
    public static double randomDouble(double min, double max) {
        return (Math.random() * (max - min) + min);
    }

    /** 
     * Renvoie true si deux chaines de caratères sont égales, faux sinon.
     * @param string1 L'une des chaines à comparer.
     * @param string2 L'autre des chaines à comparer.
     * @return  Vrai si les chaînes sont égales, sinon faux
     */
    public static boolean equal(String string1, String string2) {
        return string1.equals(string2);
    }
    /**
     * Correspond à l'entier maximal pouvant être stocké dans une variable de type int
     */
    public final static int maxInteger = Integer.MAX_VALUE;

    /** 
     * Renvoie le temps actuel en milli-secondes.
     * @return Renvoie la différence, en millisecondes, entre le temps actuel et celui du 1 Janvier 2000, minuit, en utilisant le temps universel coordonné.
     */
    public static double now() {
        return System.currentTimeMillis() - offset;
    }
    private static long offset;

    static {
        Calendar ref = Calendar.getInstance();
        ref.set(2000, 0, 1, 0, 0, 0);
        offset = ref.getTimeInMillis();
    }

    /** 
     * Attend une durée fixée.
     * Cela permet de mettre en pause l'exécution du programme
     * @param delay Durée d'attente en milli-secondes.
     */
    public static void sleep(int delay) {
        try {
            if (delay > 0) {
                Thread.sleep(delay);
            } else {
                Thread.sleep(0, 10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Programme arrêté !");
        }
    }

    /** 
     * Vérifie une assertion et arrête le code si elle est fausse.
     * @param condition Si la condition n'est pas vérifiée, le programme va s'arrêter.
     * @param message Un message s'imprime sur la console pour signaler l'erreur.
     */
    public static void assertion(boolean condition, String message) {
        System.err.println("#" + condition + " : " + message);
        if (!condition) {
            System.out.println(message);
            Console.stopProgram();
            sleep(500);
        }
    }

    /** 
     * Prompt a string
     * Prompt a string to the user with the question "Entrez une chaîne"
     * @return The result string
     */
    public static String readString() {
        return Macros.readString("Entrez une chaîne :");
    }

    /** 
     * Prompt a string
     * Prompt a string with a specific question
     * @param question The question to ask
     * @return The user answer
     */
    public static String readString(String question) {
        String s = JOptionPane.showInputDialog(
                JVSMain.getJvsMainFrame(),
                question,
                "Java's cool",
                JOptionPane.PLAIN_MESSAGE);
        if ((s != null) && (s.length() > 0)) {
            return s;
        } else if (s == null || s.length() == 0) {
            return "";
        } else {
            return Macros.readString(question);
        }
    }

    /**
     * @see readInteger()
     */
    public static int readInt() {
        return readInteger();
    }

    /**
     * @see readInteger(String)
     */
    public static int readInt(String question) {
        return readInteger(question);
    }

    /** 
     * Asks the user for an integer. If the value entered is invalid, il will be
     * prompted again until it is a valid integer.
     * @return the chosen integer
     * @see readinteger(String)
     */
    public static int readInteger() {
        return readInteger("Entrez un nombre entier : ");
    }

    /** 
     * Asks the user for an integer. If the value entered is invalid, il will be
     * prompted again until it is a valid integer.
     * @param question The question to ask
     * @return The integer value
     * @see readInteger()
     */
    public static int readInteger(String question) {
        String s = Macros.readString(question);
        try {
            return Integer.decode(s);
        } catch (Exception e) {
            if (!question.endsWith(" (Merci d'entrer un nombre)")) {
                question = question + " (Merci d'entrer un nombre)";
            }
            if (s.equals("")) {
                return 0;
            }
            return readInteger(question);
        }
    }

    /**
     * @see readBoolean()
     */
    public static Boolean readBool() {
        return readBoolean();
    }

    /**
     * @see readBoolean(String)
     */
    public static Boolean readBool(String question) {
        return readBoolean(question);
    }

    /**
     * Prompts the user for a boolean
     * @see readBoolean(String)
     * @return The chose boolean
     */
    public static Boolean readBoolean() {
        return readBoolean("Voulez vous continuer ?");
    }

    /**
     * Prompts the user for a boolean
     * @param question The prompt to use
     * @return The chose boolean
     * @see readBoolean()
     */
    public static Boolean readBoolean(String question) {
        int r = JOptionPane.showConfirmDialog(
                JVSMain.getJvsMainFrame(),
                question,
                "Java's cool",
                JOptionPane.YES_NO_OPTION);
        switch (r) {
            case JOptionPane.OK_OPTION:
                return true;
            default:
                return false;
        }
    }

    /**
     * Shows a dialog with a message
     * @param text The message to show
     */
    public static void message(String text) {
        int r = JOptionPane.showConfirmDialog(
                JVSMain.getJvsMainFrame(),
                text,
                "Java's cool",
                JOptionPane.YES_OPTION);
    }

    /**
     * Returns the proglet's Panel class instance (which is an applet according to the Proglet's specs).
     * If you want to access the proglet's content pane, create a getter in your Panel class.
     * See issue #43.
     * @return The proglet pane
     */
    public static Applet getProgletPanel() {
        /*
         * This condition was made when trying to make jvs work inside an applet : if JVSMainPanel
         * is not managing the proglet, get the progletPanel directly from Console.
         * author = Guillaume Matheron <guillaumematheron06@gmail.com>
         */
        if (Console.progletPanel == null) {
            return JVSMainPanel.getWidgetTabs().getProgletPanel();
        } else {
            return Console.progletPanel;
        }
    }

    /**
     * Returns the current program the student is making. Actually, each line of code
     * typed inside JVS' editor is inside a class with a random name beginning with
     * JvsToJavaTranslated. Getting the instance of this class allows, for instance, 
     * to use java's reflex API to list user-defined functions.
     * @return The end-user's program
     */
    public static Object getProgram() {
        return org.javascool.widgets.Console.getProgram().getClass().cast(org.javascool.widgets.Console.getProgram());
    }
    private static final Logger LOG = Logger.getLogger(Macros.class.getName());
}
