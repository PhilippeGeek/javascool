/*******************************************************************************
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
*******************************************************************************/

package org.javascool.builder;

import java.io.IOException;
import java.io.InputStreamReader;

/** Exécute une commande du système d'exploitation.
 * <p>Attend jusqu'à sa complétion et renvoie ce que la commande écrit en sortie.</p>
 *
 * @see <a href="Exec.java.html">code source</a>
 * @serial exclude
 */
public class Exec {
  // @factory
  private Exec() {}

  /** Execute la commande et renvoie le résultat.
   * @param command La commande avec ses arguments séparés par des tabulations (caractère "\t") ou, sans cela, des espaces (caractère " ").
   * @param timeout Temporisation maximale avant la fin de la commande. Si 0, l'attente est indéfinie. Valeur par défaut 10.
   * @return Le résultat: ce que la commande écrit en sortie.
   * @throws RuntimeException Si une erreur d'entrée-sortie s'est produite lors de l'exécution.
   * @throws IllegalStateException Si le statut de retour de la commande n'est pas 0 (donc a un numéro d'erreur) ou si la temporisation est dépassée.
   */
  public static String run(String command, int timeout) {
    try {
      StringBuffer output = new StringBuffer();
      long time = timeout > 0 ? System.currentTimeMillis() + 1000 * timeout : 0;
      Process process = Runtime.getRuntime().exec(command.trim().split((command.indexOf('\t') == -1) ? " " : "\t"));
      InputStreamReader stdout = new InputStreamReader(process.getInputStream());
      InputStreamReader stderr = new InputStreamReader(process.getErrorStream());
      for(boolean waitfor = true; waitfor;) {
        waitfor = false;
        Thread.yield();
        while(stdout.ready()) {
          waitfor = true;
          output.append((char) stdout.read());
        }
        while(stderr.ready()) {
          waitfor = true;
          output.append((char) stderr.read());
        }
        if(!waitfor) {
          try {
            process.exitValue();
          } catch(IllegalThreadStateException e1) {
            try {
              Thread.sleep(100);
            } catch(Exception e2) {}
            waitfor = true;
          }
        }
        if((time > 0) && (System.currentTimeMillis() > time)) throw new IllegalStateException("Command {" + command + "} timeout (>" + timeout + "s) output=[" + output + "]\n");
      }
      stdout.close();
      stderr.close();
      // Terminates the process
      process.destroy();
      try {
        process.waitFor();
      } catch(Exception e) {}
      Thread.yield();
      if(process.exitValue() != 0) throw new IllegalStateException("Command {" + command + "} error #" + process.exitValue() + " output=[\n" + output + "\n]\n");
      return output.toString();
    } catch(IOException e) { throw new RuntimeException(e + " when executing: " + command);
    }
  }
  /**
   * @see #run(String, int)
   */
  public static String run(String command) {
    return run(command, 10);
  }
}
