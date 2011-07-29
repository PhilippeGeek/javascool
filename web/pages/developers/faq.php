<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("FAQ du développeur","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-xml"),
            array("javascoolbuilder","?page=developers&action=doc-javascoolbuilder"),
            array("API","?page=api"),
            array("Exemple de proglet","?page=developers&action=sampleCode"),
            array("jvs","?page=developers&action=doc-jvs")
        )
    );
?>

<h1>FAQ du développeur</h1>

<h2 id="#usgh">Je n'arrive pas à utiliser mes fonctions définies dans Functions depuis Java's Cool</h2>
<p>Vérifiez que la classe Functions et publique et que toutes les méthodes la composant sont
publiques et statiques</p>

<h2 id="#fsfgs">Ais-je accès à des fonctions de facilité pour écrire dans la console, demander un nombre à l'utilisateur, etc. ?</h2>
<p>Oui, toutes les fonctions de Macros sont accessibles à la fois depuis Java's Cool et depuis le code des classes de la proglet.
    Cependant, pour les utiliser, vous devrez ajouter la ligne : </p>
    <pre>
import org.javascool.tools.Macros;
    </pre> <p>au début de votre programme.</p>
    <p>Les appels à ces fonctions doivent être précédés du préfixe </p><pre>Macros.</pre>
    <p>Ainsi, pour appeler la fonction </p><pre>readString()</pre><p> on utilise la syntaxe : </p><pre>Macros.readString()</pre>
<p>Noter cependant que l'élève peut appeler ces fonctions directement depuis Java's Cool sans import et sans préfixe : </p>
<pre>readString()</pre>