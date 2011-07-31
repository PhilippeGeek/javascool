<?php
showBrowser(
        array(
    array("Java's Cool", "?"),
    array("Développeurs", "?page=developers"),
    array("Exemple de proglet", "")
        ), array(
    array("Proglet", "?page=developers&action=proglets"),
    array("XML", "?page=developers&action=doc-xml"),
    array("jvs", "?page=developers&action=doc-jvs"),
    array("API", "?page=api"),
    array("FAQ", "?page=developers&action=faq"),
    array("javascoolbuilder", "?page=developers&action=doc-javascoolbuilder")
        )
);
?>

<p>Cette page a pour but de vous expliquer comment créer une proglet concrètement. Elle présente une proglet d'exemple, 
    affichant simplement un bouton et un label, et permettant à l'utilisateur d'interagir avec ces derniers. Le code est
    commenté et détaillé, et vous pouvez télécharger : </p>
<p><ul><li><?php showLink('proglets/sources-sampleCode.zip', 'Les sources au format zip', 'file'); ?></li>
    <li><?php showLink('proglets/javascool-proglet-sampleCode.jar', 'L\'exécutable au format jar', 'file'); ?></li></ul>
</p>

<h1>proglet.pml</h1>
<p>Ce fichier est le coeur de la proglet : il définit la description de la proglet, le nom de son ic&ocirc;ne
    et les auteurs</p>
<pre>{proglet
title="Exemple de proglet"
author="Guillaume Matheron <guillaumematheron06@gmail.com>"
icon="sample.png"
}</pre>

<h1>sample.png</h1>
<p>Cette image est le logo de la proglet. Elle peut avoir n'importe quel nom mais
    celui-ci doit être mentionné dans le fichier proglet.pml</p>
<p>Note : il est quasi-indispensable que cette image soit au format png transparent.
    Sans cela il sera très difficile dee l'intégrer sur le site sans affecter son design</p>
<img src="proglets/sampleCode/sample.png" alt="Logo de la proglet" />

<h1>Panel.java</h1>
<p>C'est le fichier central de la proglet, en quelque sorte (ou au moins de son code Java). Il contient
    la description de la zone de Javascool réservée à la proglet (le 'panel') et le code à exécuter
    automatiquement au lancement de la proglet (initialiser des variables, créer des listeners,
    lancer des tâches de fond etc.)</p>
<p>La classe Panel doit hériter de la classe JApplet. On créera donc une autre class, ProgletPanel, qui
    contiendra tous les éléments graphiques de la proglet. On se contentera donc dans la classe Panel
    d'implémenter la méthode init() de la classe JApplet et d'instancier la class ProgletPanel.</p>
<p>Notes : </p><ul><li>Le nom de la classe Proglet est fixé par les spécifications, mais celui de la classe ProgeltPanel
        ne l'est pas. En fait, cette classe peut très bien ne pas exister ou être définie comme classe privée dans
        la classe Panel.</li>
    <li>La classe Panel doit hériter de la classe java.applet.Applet mais il est en général plus simple
        de la faire hériter de javax.swing.JApplet. Ceci est autorisé car javax.swing.JApplet hérite
        elle-même de java.applet.Applet.</li>
    <li>On utilise ici la fonction Macros.readString(). Plus d'informations dans la 
    <?php showLink('?page=developers&action=faq#fsfgs','FAQ','internal'); ?>
    <li>Consultez les <?php showLink('?page=developers&action=proglets', 'spécifications d\'une proglet', 'internal'); ?></li>
</ul>
<p>Code source du fichier Panel.java</p>
<pre>import javax.swing.JApplet;
import org.javascool.tools.Macros;

public class Panel extends JApplet {
	private static ProgletPanel panel=new ProgletPanel();
	
	/** Constructeur par défaut */
	public Panel() {
		// Affiche panel en tant que panel principal de cette applet
		setContentPane(panel);
	}
	
	/** Récupérer le ProgletPanel affiché */
	public static ProgletPanel getPanel() {
		return panel;
	}
	
	/** Juste pour montrer que cette méthode peut être implémentée */
	public void init() {
        // On demande un texte à l'utilisateur et on l'affiche en label
		panel.setText(Macros.readString());
	}
}</pre>

<h1>ProgletPanel.java</h1>
<p>Ce fichier définit le panel qui sera affiché dans l'applet. La classe ProgletPanel doit donc
    étendre la classe java.awt.Panel.</p>
<p>Lors de sa construction elle crée un label et l'affiche en haut du panel. Elle définit aussi
    une méthode permettant de changer le texte du label, pour respecter l'encapsulation.</p>
<pre>import java.awt.Panel;
import java.awt.Label;
import java.awt.BorderLayout;
import java.lang.String;

public class ProgletPanel extends Panel {
	/** Correspond au label affiché dans le panel de la proglet */
	private Label label;
	
	/** Construit un nouveau panel */
	public ProgletPanel() {
		// On crée un label
		label=new Label("");
		// Et on l'ajoute au panel
		add(label,BorderLayout.NORTH);
	}
	
	/** Change le texte affiché dans le label */
	public void setText(String text) {
		label.setText(text);
	}
}</pre>

<h1>Functions.java</h1>
<p>Ce fichier définit les fonctions directement accessibles depuis Java's Cool, et qui
    permettent donc d'interagir avec la proglet</p>
<p>Dans cet exemple, nous définirons une méthode getText(String) qui permettra de changer
    le texte affiché dans le panel de la proglet. Toutes les méthodes de Fonctions
    doivent être statiques pour être accessibles depuis Java's Cool.</p>
<pre>import java.lang.String;

public class Functions {
	/** Permet de changer le texte affiché sur le panel de la proglet */
	public static void setText(String text) {
		Panel.getPanel().setText(text);
	}
}</pre>

<h1>help.xml</h1>
<p>Ce fichier définit l'aide à afficher à l'utilisateur. Le fichier est écrit en xml, 
    vous pouvez trouver de l'aide quand à comment rédiger ce fichier dans les
    <?php showLink('?page=developers&action=doc-xml', 'spécifications des fichiers d\'aide', 'internal'); ?>.
    Il est possible, de créer plusieurs fichiers d'aide, mais ils seront tous convertis
    au format htm lors de la compilation de la proglet. Pour lier les fichiers entre eux, vous
    devez donc y référer en tant que fichiers htm.</p>
<p>Note : le nom du fichier help.xml est fixé par les spécifications d'une proglet</p>
<pre><l link="exercice.htm" text="Exercice" /></pre><?php //TODO complete help      ?>

<h1>exercice.xml</h1>
<p>Ce fichier est un fichier xml : il sera converti en htm et permet de créer une
    seconde page d'aide. Toutes les pages d'aide peuvent être liées les unes aux autres</p>
<pre><l link="help.htm" text="Aide" /></pre><?php //TODO complete help      ?>
