/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Philippe Vienne
 */
public class ProgletFileNameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.startsWith("javascool-proglet-") && name.endsWith(".jar")) {
            return false;
        } else if (new File(dir.getPath()+File.separator+name).isDirectory()) {
            File subDir=new File(dir.getPath()+File.separator+name);
            if(new File(subDir.getPath()+File.separator+"proglet.pml").exists()){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
