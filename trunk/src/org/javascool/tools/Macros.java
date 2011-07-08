/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved. *
 *******************************************************************************/
package org.javascool.tools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.javascool.JvsMain;
import org.javascool.Utils;
import org.javascool.gui.JVSMainPanel;

/** Contain useful function for help student to program
 * @serial exclude
 */
public class Macros {

    private Macros() {
    }

    /** Set accesible in JVS the JVSMainPanel
     * @see JVSMainPanel
     * @return 
     */
    public static JVSMainPanel getJVS() {
        return org.javascool.gui.JVSMainPanel.getThisInStatic();
    }

    /** Show a String on the console
     * @param string The string to show
     */
    public static void echo(String string) {
        System.out.println(string);
    }

    public static void echo(int string) {
        echo("" + string);
    }

    public static void echo(double string) {
        echo("" + string);
    }

    public static void echo(boolean string) {
        echo("" + string);
    }

    public static < A, B> void echo(java.util.Map<A, B> map) {
        echo("" + map);
    }

    public static < A> void echo(java.util.List<A> list) {
        echo("" + list);
    }

    public static < A> void echo(java.util.Set<A> set) {
        echo("" + set);
    }

    /** Renvoie un nombre entier aléatoire uniformément distribué entre deux valeurs (maximum inclus).
     */
    public static int random(int min, int max) {
        return (int) Math.floor(min + (0.999 + max - min) * Math.random());
    }

    /** Renvoie true si deux chaines de caratères sont égales, faux sinon.
     * @param string1 L'une des chaines à comparer.
     * @param string2 L'autre des chaines à comparer.
     */
    public static boolean equal(String string1, String string2) {
        return string1.equals(string2);
    }
    public final static int maxInteger = Integer.MAX_VALUE;

    /** Renvoie le temps actuel en milli-secondes.
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

    /** Temporise une durée fixée.
     * Cela permet aussi de mettre à jour l'affichage.
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
            throw new RuntimeException("Programme arrêté !");
        }
    }

    /** Vérifie une assertion et arrête le code si elle est fausse.
     * @param condition Si la condition n'est pas vérifiée, le code JavaScool va s'arrêter.
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

    /** Prompt a string
     * Prompt a string to the user with the question "Entrez une chaîne"
     * @return The result string
     */
    public static String readString() {
        return Macros.readString("Entrez une chaîne :");
    }

    /** Prompt a string
     * Prompt a string with a specific question
     * @param question The question to ask
     * @return The user answer
     */
    public static String readString(String question) {
        String s = (String) JOptionPane.showInputDialog(
                JvsMain.getJvsMainFrame(),
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

    /** Prompt a number to the user */
    public static int readInteger() {
        return readInteger("Entrez un nombre : ");
    }

    /** Prompt a number to the user 
     * @param question The question to ask
     * @return The integer value
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

    public static Boolean readBoolean(){
        return readBoolean("Voulez vous continuer ?");
    }
    
    public static Boolean readBoolean(String question) {
        int r = JOptionPane.showConfirmDialog(
                JvsMain.getJvsMainFrame(),
                question,
                "Java's cool",
                JOptionPane.YES_NO_OPTION);
        switch(r){
            case JOptionPane.OK_OPTION:
                return true;
            default:
                return false;
        }
    }

    public static JPanel getProgletPanel() {
        return JVSMainPanel.getWidgetTabs().getProgletPanel();
    }

    public static Object getProgram() {
        return org.javascool.tools.Console.getProgram().getClass().cast(org.javascool.tools.Console.getProgram());
    }
    
    public static JVSMainPanel getJvs(){
        return JVSMainPanel.getThisInStatic();
    }

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
}