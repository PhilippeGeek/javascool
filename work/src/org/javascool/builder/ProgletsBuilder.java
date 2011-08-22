/*********************************************************************************
 * Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
 * Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
 *********************************************************************************/
package org.javascool.builder;

import org.javascool.tools.FileManager;
import org.javascool.tools.Xml2Xml;
import org.javascool.tools.Pml;
import org.javascool.core.Java2Class;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import org.javascool.Core;

/** Cette factory contient les mécanismes de construction d'un application Java's Cool avec des proglets.
 *
 * @see <a href="ProgletsBuilder.java.html">code source</a>
 * @serial exclude
 */
public class ProgletsBuilder {
    // @factory

    private ProgletsBuilder() {
    }

    /** Teste si cette version de Java'sCool a la capacité de créer des jar.  */
    public static boolean canBuildProglets() {
        try {
            Class.forName("com.icl.saxon.TransformerFactoryImpl");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /** Renvoie les proglets à construire. */
    public static String[] getProglets() {
        ArrayList<String> proglets = new ArrayList<String>();
        for (String dir : FileManager.list(System.getProperty("user.dir"))) {
            if (FileManager.exists(dir + File.separator + "proglet.pml")) {
                proglets.add(dir);
            }
        }
        return proglets.toArray(new String[proglets.size()]);
    }

    /** Construit une nouvelle archive avec les proglets proposées.
     * @param proglets Les proglets sélectionnées. Par défaut toutes les proglets disponibles.
     * @param targetDir Le répertoire cible dans lequel la construction se fait. Si null utilise un répertoire temporaire.
     * @return La valeur true si la construction est sans erreur, false sinon.
     */
    public static boolean build(String[] proglets, String targetDir) {
        if (!canBuildProglets()) {
            throw new IllegalArgumentException("Mauvaise configuration du builder, il faut utiliser le bon jar !");
        }
        try {
            // Définition de la jarre cible.
            String targetJar = System.getProperty("user.dir") + File.separator + "javascool-proglets.jar";
            new File(targetJar).delete();
            System.out.println("Scan des proglets à partir du répertoire: " + System.getProperty("user.dir"));
            // Installation du répertoire de travail.
            File buildDir;
            String tmpDir, jarDir, progletsDir;
            // Création d'un répertoire cible.
            if (targetDir == null) {
                buildDir = FileManager.createTempDir("build");
            } else {
                buildDir = new File(targetDir);
                buildDir.mkdirs();
            }
            // Création du répertoire de travail.
            {
                tmpDir = buildDir + File.separator + "tmp";
                new File(tmpDir).mkdirs();
            }
            // Création du répertoire cible
            {
                jarDir = buildDir + File.separator + "jar";
                progletsDir = jarDir + File.separator + "org" + File.separator + "javascool" + File.separator + "proglets";
                new File(progletsDir).mkdirs();
            }
            DialogFrame.setUpdate("Installation 1/2", 10);
            // Expansion des classes javascool et des proglets existantes dans les jars
            {
                String javascoolJar = Core.javascoolJar();
                JarManager.jarExtract(javascoolJar, jarDir, "org/javascool");
                JarManager.jarExtract(javascoolJar, jarDir, "org/fife");
                // @todo REMETTRE AVEC /// JASCCOOL Repaire unsafe command
// for(String jar : FileManager.list(System.getProperty("user.dir"), ".*\\.jar"))
// if(!jar.matches(".*/javascool-proglets.jar"))
// JarManager.jarExtract(jar, jarDir, "org/javascool/proglets");
            }
            DialogFrame.setUpdate("Installation 2/2", 20);
            Integer level = 20;
            // Construction des proglets
            for (String proglet : proglets) {
                String name = new File(proglet).getName(), progletDir = progletsDir + File.separator + name;
                Pml pml = new Pml().load(proglet + File.separator + "proglet.pml");
                System.out.println("Compilation de " + name + " ...");
                if (pml.getBoolean("processing")) {
                    System.out.println("==>proglet processing (non pris en charge ici: à suivre !)");
                } else {
                    DialogFrame.setUpdate("Construction de " + name + " 1/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
                    // Copie de tous les fichiers
                    {
                        new File(progletDir).mkdirs();
                        JarManager.copyFiles(proglet, progletDir);
                    }
                    DialogFrame.setUpdate("Construction de " + name + " 2/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
                    // Vérification des spécifications
                    {
                        boolean error = false;
                        if (!name.matches("[a-z][a-zA-Z][a-zA-Z][a-zA-Z]+")) {
                            System.out.println("Le nom de la proglet «" + name + "» est bizarre il ne doit contenir que des lettres faire au moins quatre caractères et démarrer par une minuscule");
                            error = true;
                        }
                        if (!FileManager.exists(progletDir + File.separator + "help.xml")) {
                            System.out.println("Pas de fichier d'aide pour " + name + ", la proglet ne sera pas construite.");
                            error = true;
                        }
                        if (!pml.isDefined("author")) {
                            System.out.println("Le champ «author» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
                            error = true;
                        }
                        if (!pml.isDefined("title")) {
                            System.out.println("Le champ «title» n'est pas défini dans " + name + "/proglet.pml, la proglet ne sera pas construite.");
                            error = true;
                        }
                        pml.save(progletDir + File.separator + "proglet.php");
                        if (error) {
                            throw new IllegalArgumentException("La proglet ne respecte pas les spécifications");
                        }
                    }
                    // Traduction Hml -> Htm des docs
                    {
                        for (String doc : FileManager.list(progletDir, ".*\\.xml")) // @todo ici il faut remplacer le xslt par un fichier du tmp !!
                        {
                            System.out.println(FileManager.load(doc));
                            FileManager.save(doc.replaceFirst("\\.xml", "\\.htm"),
                                    Xml2Xml.run(FileManager.load(doc),
                                    "../work/src/org/javascool/builder/hdoc2htm.xslt"));
                        }
                        // buildDir con !! // .. . jarDir+ "/org/javascool/builder/hdoc2htm.xslt"));
                    }
                    DialogFrame.setUpdate("Construction de " + name + " 3/4", level += (10 / proglets.length == 0 ? 1 : 10 / proglets.length));
                    if (pml.getBoolean("processing")) {
                        throw new IllegalStateException("Upps le builder est pas encore implémenté pour le processing");
                    } // @todo Tester que nous avons les tailles explicites
                    // @todo Ne pas compiler mais deployer les jars dans la cible
                    else {
                        // Extraction des extensions nécessaires à cette proglet
                        for (String jar : FileManager.list(progletDir, ".*\\.jar")) {
                            JarManager.jarExtract(jar, jarDir);
                        }
                        // Création d'une page de lancement de l'applet // @todo à valider avec Guillaume
                        FileManager.save(progletDir + File.separator + "index.html",
                                "<html><head><title>" + name + "</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body>\n"
                                + "  <center><h4>" + name + " (<a target='_blank' href='help.htm'>documentation utilisateur</a> <a target='_blank' href='api/index.html'>documentation Java</a>)</h4></center>\n"
                                + "  <applet width='560' height='720' code='org.javascool.widgets.PanelApplet' archive='../javasccool-progets><param name='pane' value='org.javascool.proglets." + name + "'/><pre>Impossible de lancer " + name + ": Java n'est pas installé ou mal configuré</pre></applet>\n"
                                + "</body></html>\n");
                        // Lancement de la compilation dans le répertoire
                        {
                            String[] javaFiles = FileManager.list(progletDir, ".*\\.java");
                            if (javaFiles.length > 0) {
                                javac(javaFiles);
                            }
                        }
                        // Creation de la javadoc si on est dans le svn
                        if (new File(".svn").exists()) { // @ todo à consolider
                            //String apiDir = buildDir + File.separator + "jar" + File.separator + name;
                            File apiDir=new File(".."+File.separator+"work"+File.separator+"dist"+File.separator+"proglets-doc"+File.separator+name);
                            apiDir.mkdirs();
                            javadoc(progletDir, apiDir.getAbsolutePath());
                        }
                    }
                    DialogFrame.setUpdate("Construction de " + name + " 4/4", level += (10 / proglets.length));
                }
            }
            DialogFrame.setUpdate("Finalisation 1/2", 90);
            // Elimination de la proglet sampleCode 
            {
                JarManager.rmDir(new File(progletsDir + File.separator + "sampleCode"));
            }
            // Création de l'archive et du manifest
            {
                String version = "Java'sCool v4 on \"" + new Date() + "\" Revision #" + Core.revision;
                Pml manifest = new Pml().set("Main-Class", "org.javascool.Core").
                        set("Manifest-version", version).
                        set("Created-By", "inria.fr (javascool.gforge.inria.fr) ©INRIA: CeCILL V2 + CreativeCommons BY-NC-ND V2").
                        set("Implementation-URL", "http://javascool.gforge.inria.fr").
                        set("Implementation-Vendor", "fuscia-accueil@inria.fr, ou=javascool.gforge.inria.fr, o=inria.fr, c=fr").
                        set("Implementation-Version", version).
                        save(tmpDir + "/manifest.jmf");
                JarManager.jarCreate(targetJar, tmpDir + "/manifest.jmf", jarDir);
                DialogFrame.setUpdate("Finalisation 2/2", 100);
            }
            if (targetDir == null) {
                JarManager.rmDir(buildDir);
            } else {
                JarManager.rmDir(new File(tmpDir));
            }
            System.out.println("Construction achevée avec succès: «" + targetJar + "» a été créé");
            System.out.println("\tIl faut lancer «" + targetJar + "» pour tester/utiliser les proglets.");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur inopinée lors de la construction (" + e.getMessage() + "): corriger l'erreur et relancer la construction");
            return false;
        }
    }

    /**
     * @see #build(String[], String)
     */
    public static boolean build(String[] proglets) {
        return build(proglets, null);
    }

    /**
     * @see #build(String[], String)
     */
    public static boolean build(String targetDir) {
        return build(getProglets(), targetDir);
    }

    /**
     * @see #build(String[], String)
     */
    public static boolean build() {
        return build(getProglets(), null);
    }

    /** Lance la compilation java sur un groupe de fichiers. */
    private static void javac(String[] javaFiles) {
        if (!Java2Class.compile(javaFiles, true)) {
            throw new IllegalArgumentException("Erreur de compilation java");
        }
    }

    /** Construction de javadoc avec sources en java2html. */
    private static void javadoc(String srcDir, String apiDir) throws IOException {
        String files[] = FileManager.list(srcDir, ".*\\.java$");
        if (files.length > 0) {
            {
                // Construit l'appel à javadoc
                String argv = "-quiet\t-classpath\t" + Core.javascoolJar() + "\t-d\t" + apiDir
                        + "\t-link\thttp://download.oracle.com/javase/6/docs/api\t-link\thttp://javadoc.fifesoft.com/rsyntaxtextarea"
                        + "\t-public\t-author\t-windowtitle\tJava's Cool v4\t-doctitle\tJava's Cool v4\t-version\t-nodeprecated\t-nohelp\t-nonavbar\t-notree\t-charset\tUTF-8";
                for (String f : files) {
                    argv += "\t" + f;
                }
                // Lance javadoc
                try {
                    // System.err.println("javadoc\t"+argv);
                    // @todo Pb avec appel direct : à solder
                    //System.err.println(Exec.run("javadoc\t" + argv));
                    com.sun.tools.javadoc.Main.execute(argv.split("\t"));
                } catch (Throwable e) {
                    throw new IOException(e);
                }
            }
            // Construit les sources en HTML à partir de java2html
            {
                File htmlDir = new File(apiDir + files[0].replaceFirst(".*" + (File.separator.equals("\\") ? "\\\\" : File.separator) + "org", File.separator + "org")).getParentFile();

                // Crée les sources à htmléiser
                for (String f : files) {
                    JarManager.copyFiles(f, apiDir+ File.separator + f.replaceFirst(".*" + (File.separator.equals("\\") ? "\\\\" : File.separator) + "org", "org") + ".java");
                }
                // Lance java2html
                de.java2html.Java2HtmlApplication.main(("-srcdir\t" + apiDir).split("\t"));
                // Nettoie les sources à htmléiser
                for (String f : files) {
                    new File(apiDir+ File.separator + f.replaceFirst(".*" + (File.separator.equals("\\") ? "\\\\" : File.separator) + "org", "org") + ".java").delete();
                }
            }
        }
        /* Un patch pour la mise au point !
         *  if (srcDir.matches(".*sampleCode.*"))
         *  org.javascool.macros.Macros.openURL("file://"+apiDir+"/index.html");
         */
    }
}
