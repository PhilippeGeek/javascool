/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.File;
import java.io.FilenameFilter;

/** Filter for Java's cool Doc Files
 * Implementation of FilenameFilter to only select XML Files wich can be a
 * documentation of java's cool
 */
public class DocumentationFileNameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith("xml") && !name.equals("build.xml")) {
            return true;
        } else {
            return false;
        }
    }
}
