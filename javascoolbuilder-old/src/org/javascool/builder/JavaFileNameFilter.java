/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.builder;

import java.io.File;
import java.io.FilenameFilter;

/** Filter for Java Files
 *Implementation of FilenameFilter to only select Java Files
 */
public class JavaFileNameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith("java")) {
            return true;
        } else {
            return false;
        }
    }
}
