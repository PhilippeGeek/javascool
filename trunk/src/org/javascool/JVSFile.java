/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author philien
 */
public class JVSFile {

    /** The text content of file */
    private String text;
    /** The name of the file */
    private String name;
    /** The path to the file */
    private String path;
    /** The file instance */
    private File file;

    /** Open a new empty file */
    public JVSFile() {
        this("");
    }

    /** Open a new file from a text
     * @param text The text of new file
     */
    public JVSFile(String text) {
        this(text, false);
    }

    /** Open a file from an url
     * Don't forget to put fromurl to true
     * @param url The url of file
     * @param fromurl  True for open from an url
     */
    public JVSFile(String url, Boolean fromurl) {
        if (!fromurl) {
            this.text = url;
            this.name = "Nouveau fichier";
            this.path = "";
            try {
                this.file = File.createTempFile("JVS_TMPFILE_", ".jvs");
                this.file.deleteOnExit();
                this.path=this.file.getAbsolutePath();
            } catch (IOException ex) {
                Logger.getLogger(JVSFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            File file_to_open = new File(url);
            this.name = file_to_open.getName();
            this.path = file_to_open.getAbsolutePath();
            try {
                this.text = JVSFile.readFileAsString(this.path);
            } catch (IOException ex) {
                this.text="";
            }
            this.file = file_to_open;
        }
    }

    /** Check if file is in tempory memory */
    public Boolean isTmp() {
        return (this.file.getName().startsWith("JVS_TMPFILE_"));
    }

    /** Save file */
    public Boolean save() {
        try {
            FileWriter fstream = new FileWriter(this.getPath());
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(this.getText());
            out.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    /** Get the content of file
     * @return the text
     */
    public String getText() {
        return text;
    }

    /** Set the text
     * !! WARNING !! It no write the text to the file, it just save it into the object use save() insted.
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /** Get the file name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /** Set the file name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Get the path to file
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /** Set a new path for the file
     * Use save() to write the file into the new path
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /** Get the file Instance in memory
     * @return the file
     */
    public File getFile() {
        return file;
    }
    
    /** Read a file */
    public static String readFileAsString(String filePath) throws java.io.IOException {
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
        f.read(buffer);
        return new String(buffer);
    }
}