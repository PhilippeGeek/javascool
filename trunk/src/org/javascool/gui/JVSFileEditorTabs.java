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
    
    public void openNewFile(){
        this.openFile(new JVSFile("class Toto{\n"
                + "\tvoid main(){\n"
                + "\n"
                + "\t}\n"
                + "}"));
    }
    
    private String openFile(JVSFile file){
        String fileId=UUID.randomUUID().toString();
        JVSEditor editor=new JVSEditor();
        editor.setText(file.getText());
        this.editors.put(fileId, editor);
        this.files.put(fileId, file);
        this.add(file.getName(), "", editors.get(fileId));
        this.fileIds.put(file.getName(), fileId);
        return fileId;
    }
    
    public JVSFile getFile(String id) throws Exception{
        if(this.files.containsKey(id)){
            return this.files.get(id);
        } else {
            throw new Exception("File "+id+" is not editing");
        }
    }
    
    public JVSEditor getEditor(String fileId) throws Exception{
        if(this.editors.containsKey(fileId)){
            return this.editors.get(fileId);
        } else {
            throw new Exception("File "+fileId+" is not editing");
        }
    }
    
}
