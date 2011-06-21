/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool;

import com.icl.saxon.exslt.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author philien
 */
public class JVSFile {

    private String text;
    private String name;
    private String path;
    private File file;

    public JVSFile() {
        this("");
    }

    public JVSFile(String text) {
        this(text, false);
    }

    public JVSFile(String url, Boolean fromurl) {
        if (!fromurl) {
            this.text = url;
            this.name = "New " + Date.time();
            this.path = "";
            try {
                this.file = File.createTempFile("JVS_TMPFILE_", ".jvs");
                this.file.deleteOnExit();
            } catch (IOException ex) {
                Logger.getLogger(JVSFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            File file_to_open = new File(url);
            this.name = file_to_open.getName();
            this.path = file_to_open.getAbsolutePath();
            this.text = file_to_open.toString();
            this.file = file_to_open;
        }
    }

    public Boolean isTmp() {
        return (this.name.startsWith("New") && this.file.getName().startsWith("JVS_TMPFILE_"));
    }

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

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
}