/*********************************************************************************
 * Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
 * Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
 *********************************************************************************/
package org.javascool.macros;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import java.util.Calendar;
import javax.swing.ImageIcon;

import java.net.URL;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import org.javascool.core.ProgletEngine;
import org.javascool.widgets.Dialog;
import javax.swing.SwingUtilities;
import org.javascool.widgets.MainFrame;
import org.javascool.widgets.PanelApplet;

/** Cette factory contient des fonctions générales rendues visibles à l'utilisateur de proglets.
 * <p>Elle permet de définir des fonctions plus facile d'utilisation que les appels Java usuels.</p>
 * <p>Elle permet aussi avoir quelques fonctions de base lors de la création de nouvelles proglets.</p>
 *
 * @see <a href="Macros.java.html">code source</a>
 * @serial exclude
 */
public class Macros {
    // @factory

    private Macros() {
    }

    /** Renvoie un nombre entier aléatoire uniformément distribué entre deux valeurs (maximum inclus).
     */
    public static int random(int min, int max) {
        return (int) Math.floor(min + (0.999 + max - min) * Math.random());
    }

    /** Renvoie true si deux chaînes de caratères sont égales, faux sinon.
     * @param string1 L'une des chaînes à comparer.
     * @param string2 L'autre des chaînes à comparer.
     */
    public static boolean equal(String string1, String string2) {
        return string1.equals(string2);
    }

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
            org.javascool.core.ProgletEngine.getInstance().doStop();
            Macros.sleep(500);
        }
    }

    /** Affiche un message dans une fenêtre présentée à l'utilisateur.
     * <p>Le message s'affiche sous une forme "copiable" pour que l'utilisateur puisse le copier/coller.</p>
     * @param text Le message à afficher.
     * @param html Mettre à true si le texte est en HTML, false sinon (valeur par défaut)
     */
    public static void message(String text, boolean html) {
        JEditorPane p = new JEditorPane();
        p.setEditable(false);
        p.setOpaque(false);
        if (html)
            p.setContentType("text/html; charset=utf-8");
        p.setText(text);
        p.setBackground(new java.awt.Color(200, 200, 200, 0));
        messageDialog = new Dialog();
        messageDialog.setTitle("Java's Cool message");
	messageDialog.setMinimumSize(new Dimension(300, 100));
        messageDialog.add(p);
        messageDialog.add(new JButton("OK") {
        {
           addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        messageDialog.close();
                    }
                });
            }
        }, BorderLayout.SOUTH);
        messageDialog.open(!SwingUtilities.isEventDispatchThread());
    }
    private static Dialog messageDialog;

    /**
     * @see #message(String, boolean)
     */
    public static void message(String text) {
        message(text, false);
    }

    /** Renvoie une icone stockée dans le JAR de l'application.
     * @param path Emplacement de l'icone, par exemple <tt>"org/javascool/widget/icons/play.png"</tt>
     * @return L'icone chargée ou null si elle n'existe pas.
     */
    public static ImageIcon getIcon(String path) {
      URL icon = getResourceURL(path); // Thread.currentThread().getContextClassLoader().getResource(path);
        if (icon == null) {
            System.err.println("Warning : getIcon(" + path + ") not found");
        }
        return icon == null ? null : new ImageIcon(icon);
    }

    /** Ouvre une URL (Universal Resource Location) dans un navigateur extérieur.
     * @param location L'URL à afficher.
     *
     * @throws IllegalArgumentException Si l'URL est mal formée.
     * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite.
     */
    public static void openURL(String location) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(location));
        } catch (Throwable th) {
            try {
                String url = location;
                String os = System.getProperty("os.name").toLowerCase();
                Runtime rt = Runtime.getRuntime();
                try {
                    if (os.indexOf("win") >= 0) {
                        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                    } else if (os.indexOf("mac") >= 0) {
                        rt.exec("open " + url);
                    } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                        // Do a best guess on unix until we get a platform independent way
                        // Build a list of browsers to try, in this order.
                        String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                            "netscape", "opera", "links", "lynx"};
                        // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                        StringBuilder cmd = new StringBuilder();
                        for (int i = 0; i < browsers.length; i++) {
                            cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
                        }
                        rt.exec(new String[]{"sh", "-c", cmd.toString()});
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    return;
                }
                return;
            } catch (Exception e) {
                throw new RuntimeException(e + " when browwing: " + location);
            }
        }
    }

    /** Renvoie une URL (Universal Resource Location) normalisée, dans le cas du système de fichier local ou d'une ressource.
     * <p>La fonction recherche l'existence du fichier:
     * (i) par rapport au répertoire de base qui est donné,
     * (ii) par rapport au dossier de travaul "user.dir",
     * (iii) par rapport à la racine des fichier "user.home",
     * (iv) dans les ressources du CLASSPATH.</p>
     * @param location L'URL à normaliser.
     * @param base     Un répertoire de réference pour la normalisation. Par défaut null.
     * @param reading  Précise si nous sommes en lecture (true) ou écriture (false). Par défaut en lecture.
     * @throws IllegalArgumentException Si l'URL est mal formée.
     */
    public static URL getResourceURL(String location, String base, boolean reading) {
        if (base != null) {
            location = base + "/" + location;
        }
        try {
            // @patch : ceci blinde un bug sur les URL jar
            if (location.matches("jar:[^!]*!.*")) {
                String res = location.replaceFirst("[^!]*!/", "");
                URL url = Thread.currentThread().getContextClassLoader().getResource(res);
                if (url != null) {
                    return url;
                } else {
                    throw new IllegalArgumentException("Unable to find " + res + " from " + location + " as a classpath resource");
                }
            }
            if (location.matches("(ftp|http|https|jar|mailto|stdout):.*")) {
                return new URL(location).toURI().normalize().toURL();
            }
            if (location.startsWith("file:")) {
                location = location.substring(5);
            }
            if (reading) {
                File file = new File(location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                file = new File(System.getProperty("user.dir"), location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                file = new File(System.getProperty("user.home"), location);
                if (file.exists()) {
                    return new URL("file:" + file.getCanonicalPath());
                }
                URL url = Thread.currentThread().getContextClassLoader().getResource(location);
                if (url != null) {
                    return url;
                }
            }
            return new URL("file:" + location);
        } catch (IOException e) {
            throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e + " : " + location + " is a malformed URL");
        }
    }

    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location, String base) {
        return getResourceURL(location, base, true);
    }

    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location, boolean reading) {
        return getResourceURL(location, null, reading);
    }

    /**
     * @see #getResourceURL(String, String, boolean)
     */
    public static URL getResourceURL(String location) {
        return getResourceURL(location, null, true);
    }

    /** Renvoie le panneau graphique de la proglet courante.
     * @return Le panneau graphique de la proglet courante ou null si il n'est pas défini.
     */
    public static < T extends Component> T getProgletPane() {
        Component c = null;
        try {
            c = ProgletEngine.getInstance().getProglet().getProgletPane();
        } catch (Throwable e) {
            c = PanelApplet.getPane();
        }
        return (T) c;
    }
}
