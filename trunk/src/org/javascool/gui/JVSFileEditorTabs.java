/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.gui;

import java.util.HashMap;
import java.util.UUID;
import org.javascool.JVSFile;
import org.javascool.editor.JVSEditor;

/**
 *
 * @author philien
 */
public class JVSFileEditorTabs extends JVSTabs{
    
    private HashMap<String,JVSEditor> editors=new HashMap<String,JVSEditor>();
    private HashMap<String,JVSFile> files=new HashMap<String,JVSFile>();
    private HashMap<String,String> fileIds=new HashMap<String,String>();
    
    public JVSFileEditorTabs(){
        super(); 
    }
    
    /** Open a new empty Java's cool file in tmp
     * @return The file's tempory id in editor tabs
     */
    public String openNewFile(){
        return this.openFile(new JVSFile("class Toto{\n"
                + "\tvoid main(){\n"
                + "\n"
                + "\t}\n"
                + "}"));
    }
    
    /** Open a new empty Java's cool file in tmp
     * @param url The url to the file (used by File())
     * @return The file's tempory id in editor tabs
     */
    public String open(String url){
        return this.openFile(new JVSFile(url,true));
    }
    
    /** Open a file
     * Open a file from the instance and open its tab
     * @param file The opened file
     * @return The file's tempory id in editor tabs
     */
    private String openFile(JVSFile file){
        if(!this.getFileId(file.getName()).equals("")){
            this.setSelectedIndex(this.getTabId(this.getFileId(file.getName())));
            return this.getFileId(file.getName());
        }
        String fileId=UUID.randomUUID().toString();
        JVSEditor editor=new JVSEditor();
        editor.setText(file.getText());
        this.editors.put(fileId, editor);
        this.files.put(fileId, file);
        this.add(file.getName(), "", editors.get(fileId));
        this.fileIds.put(file.getName(), fileId);
        this.setSelectedIndex(this.getTabId(fileId));
        return fileId;
    }
    
    public int getTabId(String fileId){
        if(this.fileIds.containsValue(fileId)){
            return this.indexOfComponent(this.editors.get(fileId));
        }
        return -1;
    }
    
    public String getFileId(String tabName){
        if(this.fileIds.containsKey(tabName)){
            return this.fileIds.get(tabName);
        } else {
            return "";
        }
    }
    
    public JVSFile getFile(String id){
        if(this.files.containsKey(id)){
            return this.files.get(id);
        } else {
            return new JVSFile();
        }
    }
    
    public JVSEditor getEditor(String fileId){
        if(this.editors.containsKey(fileId)){
            return this.editors.get(fileId);
        } else {
            return new JVSEditor();
        }
    }
    
}
