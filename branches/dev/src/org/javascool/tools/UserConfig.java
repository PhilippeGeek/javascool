/*******************************************************************************
 *           Philippe.Vienne, Copyright (C) 2011.  All rights reserved.         *
 *******************************************************************************/
package org.javascool.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Permet de stocker des informations dans un fichier de configuration de
 * l'utilisateur.
 * 
 * @author Philippe Vienne
 * @see <a href="UserConfig.java.html">code source</a>
 * @serial exclude
 */
public class UserConfig {
	// @static-instance

	/** Nom de l'application. */
	private final String applicationName;
	/** Nom du fichier de configuration. */
	private final String configFile = "configuration.xml";
	/** Table des propriétés de l'application. */
	private Properties properties = null;
	private static String theApplicationName = null;

	private static UserConfig userConfig = null;

	/**
	 * Crée et/ou renvoie l'unique instance de l'objet.
	 * <p>
	 * Une application ne peut définir qu'un seul objet de configuration.
	 * </p>
	 */
	public static UserConfig getInstance(String applicationName) {
		if (UserConfig.userConfig == null)
			return UserConfig.userConfig = new UserConfig(
					UserConfig.theApplicationName = applicationName);
		else if (UserConfig.theApplicationName.equals(applicationName))
			return UserConfig.userConfig;
		else
			throw new IllegalArgumentException(
					"Appel incohérent à la configuration de l'application avec deux noms différents "
							+ UserConfig.theApplicationName
							+ " puis "
							+ applicationName);
	}

	private UserConfig(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Renvoie le répertoire standard où stocker les données d'une application.
	 */
	public String getApplicationFolder() {
		final String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN"))
			return System.getenv("APPDATA") + "\\" + applicationName + "\\";
		else if (OS.contains("MAC"))
			return System.getProperty("user.home")
					+ "/Library/Application Support/" + applicationName + "/";
		else if (OS.contains("NUX"))
			return System.getProperty("user.home") + "/." + applicationName
					+ "/";
		else
			throw new IllegalStateException(
					"Impossible de définir un répertoire de configuration pour l'application "
							+ applicationName
							+ " sous le système d'exploitation «" + OS + "»");
	}

	/** Renvoie la liste de propriétés liées à cette application. */
	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.loadFromXML(new FileInputStream(
						getApplicationFolder() + configFile));
			} catch (final Exception e) {
				System.err
						.println("Dysfonctionnement lors la lecture du fichier de configuration"
								+ e);
			}
		}
		return properties;
	}

	/**
	 * @see #getProperty(String, String)
	 */
	public String getProperty(String name) {
		return getProperty(name, null);
	}

	/**
	 * Lit une propriété liée à cette application.
	 * 
	 * @param name
	 *            Nom de la propriété.
	 * @param value
	 *            Valeur par défaut.
	 * @return La valeur de la propriété, si elle définie, sinon null.
	 */
	public String getProperty(String name, String value) {
		return getProperties().getProperty(name, value);
	}

	/**
	 * Ecrit une propriété liée à cette application.
	 * 
	 * @param name
	 *            Nom de la propriété.
	 * @param value
	 *            Valeur de la propriété.
	 * @return Cet objet, permettant de définir la construction
	 *         <tt>getInstance(..).setProperty(..)</tt>.
	 * @throws RuntimeException
	 *             Si une erreur d'entrée-sortie s'est produite lors de la
	 *             sauvergarde de la propriété.
	 */
	public UserConfig setProperty(String name, String value) {
		getProperties().setProperty(name, value);
		try {
			new File(getApplicationFolder()).mkdirs();
			properties.storeToXML(new FileOutputStream(getApplicationFolder()
					+ configFile), "JavaS'Cool user configuration");
		} catch (final Exception e) {
			throw new RuntimeException(
					"Erreur de la sauvegarde de la configuration de l'application "
							+ applicationName + " dans le fichier "
							+ getApplicationFolder() + configFile);
		}
		return this;
	}
}
