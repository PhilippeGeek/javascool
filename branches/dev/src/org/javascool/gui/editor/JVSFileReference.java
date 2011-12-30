package org.javascool.gui.editor;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import org.javascool.tools.FileManager;

public class JVSFileReference {

	public static final String SOURCE_EXTENTION = ".jvs";
	public static final String DEFAULT_SOURCE = "void main(){\n\t\n}";
	/** The referenced file */
	private File file;
	/** Say if file is temporary */
	private Boolean tmp;
	/** It's the content of file */
	private String content;
	/** Original content.
	 *  Used to compare with the content string to check 
	 *  difference and deduce if we have to save
	 */
	private String originalContent;
	/** It's the name of the file */
	private String name;
	
	public JVSFileReference(){
		this.tmp=true;
		this.content=DEFAULT_SOURCE;
		this.originalContent=this.content;
		this.file=null;
		this.name="";
	}
	
	public JVSFileReference(File file){
		this();
		setFile(file);
	}
	
	public String getContent() {
		return content;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean hasToSave(){
		return (!content.equals(originalContent));
	}
	
	public Boolean isTmp(){
		return this.tmp;
	}
	
	public Boolean save(){
		if(file==null||isTmp()){
			throw new IllegalStateException("Can not save an tmp file" +
					", please give a location");
		}
		try {
			FileManager.save(file.getAbsolutePath(), this.getContent(), false,
					true);
			originalContent=this.getContent();
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setFile(File file) {
		if(!file.exists()||file.isDirectory()){
			throw new IllegalArgumentException("File "+file+" doesn't" +
					" exist or is a dirctory");
		}
		this.file=file;
		this.setContent(FileManager.load(file.getAbsolutePath(), true));
		this.setName(this.file.getName());
		this.originalContent=this.getContent();
		this.tmp=false;
	}
	
	private void setName(String name){
		setName(name,false);
	}
	
	private void setName(String name, boolean rename) {
		this.name = name;
		if(file!=null&&rename){
			File newFile=new File(file.getParentFile(),name);
			file.renameTo(newFile);
			setFile(file);
		}
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof JVSFileReference){
			if(((JVSFileReference) o).isTmp()||this.isTmp()){
				return false;
			}
			if(((JVSFileReference) o).getFile().getAbsolutePath().equals(this.getFile().getAbsolutePath())){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private ArrayList<ActionListener> als;
	
	public void addChangeListener(ActionListener al){
		if(!als.contains(al))
			als.add(al);
	}
	
	public void removeChangeListener(ActionListener al){
		if(als.contains(al))
			als.remove(al);
	}
}
