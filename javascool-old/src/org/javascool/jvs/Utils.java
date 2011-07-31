//@deprecated voir org.javascool.tools.StringFile

package org.javascool.jvs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for JVS Compilation
 * @author philien
 */
public class Utils {

    /**
     * Save a String into a file
     * @param what
     * @param where
     * @return True on succes, false on failure.
     * @throws IOException 
     */
    public static Boolean saveString(String what, String where) throws IOException {
        FileWriter out = new FileWriter(new File(where));
        try {
            BufferedWriter ecrire = new BufferedWriter(new FileWriter(where, true));
            ecrire.write(what);//ou les données que tu dois recup quand tu clique sur le button en question 
            ecrire.close();
            return true;
        } catch (Exception e) {
            System.err.println(out);
            System.err.println(e.toString());
            return false;
        }
    }

    public static String loadString(String string) {
        return Utils.readFile(string);
    }

    private static String readFile(String filename) {
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
            String toReturn="";
            for(String lineToPut:records){
                toReturn=toReturn+lineToPut+"\n";
            }
            return toReturn;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            return null;
        }
    }

    public static Throwable report(Throwable e) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String toName(String jclass) {
        return jclass;
    }
}
