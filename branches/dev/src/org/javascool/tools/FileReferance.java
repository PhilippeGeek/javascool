package org.javascool.tools;

import java.io.File;
import java.io.IOException;

public class FileReferance {

	/** Préfix des fichiers temporaires. */
	public static String tmpPrefix="jvsTmpFile-";
	/** Suffix des extentions des fichiers sources. */
	public static String srcSuffix=".jvs";

	/** Le fichier où la référance pointe.*/
	private File file;

	/** Contruit une nouvelle référance à partir d'un fichier.
	 * Si la référance est nulle, la référance va pointer vers un fichier temporaire.
	 * @param file Le fichier à référancer
	 */
	public FileReferance(File file){
		if(file==null){
			try {
				file=File.createTempFile(tmpPrefix, srcSuffix);
			} catch (IOException e) {
				throw new RuntimeException("Could not create tmp file",e);
			}
		}
		setFile(file);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		if(!file.exists()){
			throw new IllegalArgumentException("File "+file+" doesn't exist");
		}
		this.file = file;
	}

	public boolean isTmpSourceFile(){
		if(this.file.getName().startsWith(tmpPrefix)&&isSrcFile())
			return true;
		else
			return false;
	}

	public boolean isSrcFile() {
		if(file.getName().endsWith(srcSuffix)&&file.isFile())
			return true;
		else
			return false;
	}
	
	public String getName(){
		return file.getName();
	}
	
	public String getPath(){
		return file.getPath();
	}

}
