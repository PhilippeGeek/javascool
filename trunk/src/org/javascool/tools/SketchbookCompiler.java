/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public SketchbookCompiler() throws IOException {
        
        // List files in the path
        try {
            if (ClassLoader.getSystemClassLoader().getResource("org/javascool").toString().split("!").length > 1) {
                this.jvsPath = ClassLoader.getSystemClassLoader().getResource("org").toString().replaceAll("file:", "").replaceAll("jar:", "").replaceAll("%20", " ").split("!")[0];
                this.isInJar = true;
                this.files = this.listFilesInJar(this.jvsPath);
            } else {
                this.jvsPath = ClassLoader.getSystemClassLoader().getResource("").toURI().getPath();
                this.isInJar = false;
                this.files = this.listFilesInPath(this.jvsPath);
            }
        } catch (URISyntaxException uRISyntaxException) {
            System.err.println(uRISyntaxException.getMessage());
        }
        
        // Create a tmp directory
        File tmpDir=this.getTmpDir();
        tmpDir.mkdirs();
        
        
        // These are the files to include in the ZIP file
        String[] filenames = new String[]{"build.xml", "logos/logo.png"};

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            String outFilename = "outfile.zip";
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));

            // Compress the files
            for (int i = 0; i < filenames.length; i++) {
                FileInputStream in = new FileInputStream(filenames[i]);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(filenames[i]));

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
            System.out.println(e);
        }
    }

    private ArrayList<String> listFilesInPath(String path) {
        ArrayList<String> pathFiles = new ArrayList<String>();
        File directory = new File(path);
        for (File file : directory.listFiles()) {
            pathFiles.add(file.getAbsolutePath());
            if (file.isDirectory()) {
                pathFiles.addAll(this.listFilesInPath(file.getAbsolutePath()));
            }
        }
        return pathFiles;
    }

    private ArrayList<String> listFilesInJar(String jarPath) {
        ArrayList<String> jarFiles = new ArrayList<String>();
        try {
            System.out.println("jar : "+jarPath);
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
        return new File(System.getProperty("user.dir")+"/.javascool/tmp/");
    }

    public static void main(String[] args) {
        try {
            new SketchbookCompiler();
        } catch (IOException ex) {
            Logger.getLogger(SketchbookCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
