//@deprecated voir org.javascool.tools.UserConfig

package org.javascool;

import org.javascool.tools.Utils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philippe Vienne
 */
public class JVSConf {

    private static File workspace = new File(JVSConf.getWorkspace());
    private static File configurationFile = new File(JVSConf.getWorkspace() + "configuration.jvsconf");
    private static HashMap<String, String> conf = new HashMap<String, String>();

    public JVSConf() {
        if (!configurationFile.exists()) {
            Utils.saveString(configurationFile.getAbsolutePath(), "version:" + JVSMain.title);
        }
        this.loadConf();
    }

    public String get(String key) {
        if (conf.containsKey(key)) {
            return conf.get(key);
        } else {
            return "";
        }
    }

    public void set(String key, String value) {
        conf.put(key, value);
        this.writeFile();
    }

    private void loadConf() {
        String[] confFile = null;
        try {
            confFile = Utils.loadString(configurationFile.getAbsolutePath()).split("\n");
        } catch (IOException ex) {
            Logger.getLogger(JVSConf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String confLine : confFile) {
            if (confLine.split(":", 2).length == 2) {
                conf.put(confLine.split(":", 2)[0], confLine.split(":", 2)[1]);
            }
        }
    }

    private void writeFile() {
        String confFile = "";
        for (String key : conf.keySet()) {
            confFile += key + ":" + conf.get(key) + "\n";
        }
        Utils.saveString(JVSConf.configurationFile.getAbsolutePath(), confFile);
    }

    private static String getWorkspace() {
        String OS = System.getProperty("os.name").toUpperCase();
        if (OS.contains("WIN")) {
            return System.getenv("APPDATA") + "\\javascool\\";
        } else if (OS.contains("MAC")) {
            return System.getProperty("user.home") + "/Library/Application "
                    + "Support" + "/javascool/";
        } else if (OS.contains("NUX")) {
            return System.getProperty("user.home") + "/.javascool/";
        }
        return System.getProperty("user.dir");
    }
}
