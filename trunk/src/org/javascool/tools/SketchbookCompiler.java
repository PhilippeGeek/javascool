//TODO
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Philippe Vienne
 */
public class SketchbookCompiler {

    private ArrayList<String> files = new ArrayList<String>();
    private String jvsPath = "";
    private Boolean isInJar = true;

    /**
    Remove a directory and all of its contents.
    
    The results of executing File.delete() on a File object
    that represents a directory seems to be platform
    dependent. This method removes the directory
    and all of its contents.
    
    @return true if the complete directory was removed, false if it could not be.
    If false is returned then some of the files in the directory may have been removed.
    
     */
    public static boolean removeDirectory(File directory) {
        if (directory == null) {
            return false;
        }
        if (!directory.exists()) {
            return true;
        }
        if (!directory.isDirectory()) {
            return false;
        }

        String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);
                if (entry.isDirectory()) {
                    if (!removeDirectory(entry)) {
                        return false;
                    }
                } else {
                    if (!entry.delete()) {
                        return false;
                    }
                }
            }
        }

        return directory.delete();
    }

    //TODO javadoc
    private static void copyfile(String srFile, String dtFile) {
        try {
            File f2 = new File(dtFile);
            InputStream in = ClassLoader.getSystemResourceAsStream(srFile);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory. Error on file " + srFile);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public SketchbookCompiler() throws IOException {
        // List files in the path
        /*
         * All files to copy are put in an arraylist as strings : this.files
         */
        try {
            if (ClassLoader.getSystemClassLoader().getResource("org/javascool").toString().split("!").length > 1) {
                this.jvsPath = ClassLoader.getSystemClassLoader().getResource("org/javascool/").toString().replaceAll("file:", "").replaceAll("jar:", "").replaceAll("%20", " ").split("!")[0];
                this.isInJar = true;
                this.files = this.listFilesInJar(this.jvsPath);
            } else {
                this.jvsPath = ClassLoader.getSystemClassLoader().getResource("").toURI().getPath();
                System.out.println("Path : " + this.jvsPath);
                this.isInJar = false;
                this.files = this.listFilesInPath(this.jvsPath);
            }
        } catch (URISyntaxException uRISyntaxException) {
            System.err.println(uRISyntaxException.getMessage());
        }

        // Create a tmp directory
        File tmpDir = this.getTmpDir();
        System.out.println(tmpDir);
        tmpDir.mkdirs();


        // These are the files to include in the ZIP file with names
        // such as org/javascool/a.class
        ArrayList<String> filenamesJar = new ArrayList<String>();   //Name in jar

        for (String file : this.files) {
            if (file.endsWith("/")) {
                File dir = new File(tmpDir+File.separator+file);
                dir.mkdirs();
            } else if (!file.contains("META-INF") && this.isInJar) {
                // FIXME : copyresource(file, tmpDir+"/"+file);
                filenamesJar.add(file);
            } else if (!this.isInJar) {
                System.out.println(file + " > " + tmpDir+File.separator+file);
                if (new File(file).isDirectory()) {
                    File dir = new File(tmpDir+File.separator+file);
                    dir.mkdirs();
                } else if (!file.contains("META-INF")) {
                    copyfile(file, tmpDir+File.separator+file);
                    filenamesJar.add(file);
                }
            }
            System.out.println("Copied " + file);
        }
        filenamesJar.add("META-INF/MANIFEST.MF");
        String manifest = new String();
        manifest += "Manifest-Version: 1.0\n";
        manifest += "Created-By: INRIA\n";
        manifest += "Main-Class: org.javascool.JvsMain\n";
        FileWriter fstream = new FileWriter(tmpDir + "/META-INF/MANIFEST.MF");
        BufferedWriter outs = new BufferedWriter(fstream);
        outs.write(manifest);
        outs.close();

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            String outFilename = "outfile.jar";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));

            // Compress the files
            for (int i = 0; i < filenamesJar.size(); i++) {
                FileInputStream in = new FileInputStream(tmpDir+"/"+filenamesJar.get(i));

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(filenamesJar.get(i)));   //FIXME

                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        System.out.println("Jar creation completed");
    }

    private ArrayList<String> listFilesInPath(String path) {
        ArrayList<String> pathFiles = new ArrayList<String>();
        File directory = new File(path);
        for (File file : directory.listFiles()) {
            pathFiles.add(file.getPath());
            if (file.isDirectory()) {
                pathFiles.addAll(this.listFilesInPath(file.getAbsolutePath()));
            }
        }
        return pathFiles;
    }

    private ArrayList<String> listFilesInJar(String jarPath) {
        ArrayList<String> jarFiles = new ArrayList<String>();
        try {
            System.out.println("jar : " + jarPath);
            JarFile jarFile = new JarFile(jarPath);
            final Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                final String entryName = entry.getName();
                jarFiles.add(entryName);
            }
        } catch (IOException ex) {
            System.err.println("Unable to access to the jar");
        }
        return jarFiles;
    }

    private File getTmpDir() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            return new File(System.getenv("APPDATA") + "\\javascool\\tmp\\");
        } else if (OS.contains("MAC")) {
            return new File(System.getProperty("user.home") + "/Library/Application "
                    + "Support" + "/javascool/tmp/");
        } else if (OS.contains("NUX")) {
            return new File(System.getProperty("user.home") + "/.javascool/tmp/");
        }
        return new File(System.getProperty("user.dir") + "/.javascool/tmp/");
    }

    public static void main(String[] args) {
        try {
            new SketchbookCompiler();
        } catch (IOException ex) {
            Logger.getLogger(SketchbookCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
