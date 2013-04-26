package org.javascool.proglets.plurialgo;

import raffinat.inout.config.Config;
import raffinat.inout.interaction1.PanelInteraction;

/**
 * Definit le panneau graphique de la proglet
 * 
 * @see <a href="Panel.java.html">code source</a>
 * @serial exclude
 */
public class Panel extends PanelInteraction {
	private static final long serialVersionUID = 1L;

	// @bean
	public Panel() {
		super();
	}

	public void initConfig() {
		Config.setEnglish(false);
		Config.urlDoc = "/org/javascool/proglets/plurialgo/aideJVS/";
		// modifications de l'interface graphique
		String[] langages = { "javascool", "vb", "larp", "python", "javascript", 
				"php", "cplus", "java" };
		Config.langList = langages;
	}

}
