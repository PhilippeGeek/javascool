/*******************************************************************************
 * Thierry.Vieville@sophia.inria.fr, Copyright (C) 2010.  All rights reserved. *
 *******************************************************************************/

package org.javascool.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.javascool.macros.Macros;

/**
 * Exécute une commande du système d'exploitation.
 * 
 * @see <a href="Exec.java.html">code source</a>
 * @serial exclude
 */
public class Exec {
	// Lance une commande en arrière plan
	private static Process exec(String command) throws IOException {
		return Runtime.getRuntime().exec(
				command.trim().split(command.indexOf('\t') == -1 ? " " : "\t"));
	}

	// Fait une copie locale d'une jarre globale
	private static String getLocal(String location) throws IOException {
		final URL url = Macros.getResourceURL(location, true);
		final File file = File.createTempFile(new File(url.getFile()).getName()
				.replaceFirst("\\.jar$", "") + "-", ".jar");
		{
			final BufferedInputStream i = new BufferedInputStream(
					url.openStream(), 2048);
			final FileOutputStream o = new FileOutputStream(file);
			final byte data[] = new byte[2048];
			for (int c; (c = i.read(data, 0, 2048)) != -1;) {
				o.write(data, 0, c);
			}
			i.close();
			o.close();
		}
		return file.getCanonicalPath();
	}

	/**
	 * @see #run(String, int)
	 */
	public static String run(String command) {
		return Exec.run(command, 10);
	}

	/**
	 * Execute la commande et renvoie le résultat.
	 * 
	 * @param command
	 *            La commande avec ses arguments séparés par des tabulations
	 *            (caractère "\t") ou, sans cela, des espaces (caractère " ").
	 * @param timeout
	 *            Temporisation maximale avant la fin de la commande. Valeur par
	 *            défaut 10.
	 *            <p>
	 *            Si 0, l'attente est indéfinie.
	 *            </p>
	 *            <p>
	 *            Si -1, la commande est lancée en arrière plan et la fonction
	 *            revient tout de suite, sans résultat.
	 *            </p>
	 * @return Le résultat: ce que la commande écrit en sortie.
	 * @throws RuntimeException
	 *             Si une erreur d'entrée-sortie s'est produite lors de
	 *             l'exécution.
	 * @throws IllegalStateException
	 *             Si le statut de retour de la commande n'est pas 0 (donc a un
	 *             numéro d'erreur) ou si la temporisation est dépassée.
	 */
	public static String run(String command, int timeout) {
		try {
			final Process process = Exec.exec(command);
			if (timeout == -1)
				return "";
			final StringBuffer output = new StringBuffer();
			final long time = timeout > 0 ? System.currentTimeMillis() + 1000
					* timeout : 0;
			final InputStreamReader stdout = new InputStreamReader(
					process.getInputStream());
			final InputStreamReader stderr = new InputStreamReader(
					process.getErrorStream());
			for (boolean waitfor = true; waitfor;) {
				waitfor = false;
				Thread.yield();
				while (stdout.ready()) {
					waitfor = true;
					output.append((char) stdout.read());
				}
				while (stderr.ready()) {
					waitfor = true;
					output.append((char) stderr.read());
				}
				if (!waitfor) {
					try {
						process.exitValue();
					} catch (final IllegalThreadStateException e1) {
						try {
							Thread.sleep(100);
						} catch (final Exception e2) {
						}
						waitfor = true;
					}
				}
				if (time > 0 && System.currentTimeMillis() > time)
					throw new IllegalStateException("Command {" + command
							+ "} timeout (>" + timeout + "s) output=[" + output
							+ "]\n");
			}
			stdout.close();
			stderr.close();
			// Terminates the process
			process.destroy();
			try {
				process.waitFor();
			} catch (final Exception e) {
			}
			Thread.yield();
			if (process.exitValue() != 0)
				throw new IllegalStateException("Command {" + command
						+ "} error #" + process.exitValue() + " output=[\n"
						+ output + "\n]\n");
			return output.toString();
		} catch (final IOException e) {
			throw new RuntimeException(e + " when executing: " + command);
		}
	}

	/**
	 * Démarre un nouveau programme java en place de celui là.
	 * 
	 * @param jar
	 *            La jarre contenant le programme Java à lancé à la place de
	 *            celui là.
	 * @return La valeur true si le programme a pu se lancer, sinon la valeur
	 *         false.
	 */
	public static boolean start(String jar) {
		try {
			final String command = System.getProperty("java.home")
					+ File.separator + "bin" + File.separator + "java\t-jar\t"
					+ Exec.getLocal(jar);
			Exec.exec(command);
			return true;
		} catch (final IOException e) {
			System.err.println("Impossible de lancer le jar '" + jar + "' :"
					+ e);
			return false;
		}
	}

	// @factory
	private Exec() {
	}
}
