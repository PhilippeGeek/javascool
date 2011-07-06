/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javascool.proglets.dico;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javascool.tools.Macros;

/**
 *
 * @author Philippe Vienne
 */
public class Functions {
    public static void help(){
        Macros.echo("ça marche");
    }
    public static Boolean compare(int a, int b){
        System.out.println("-- In the subThread");
        URL[] surls = ((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs();
        for(URL url:surls){
            System.out.println(url.toString());
        }
        return a>b;
    }
}
