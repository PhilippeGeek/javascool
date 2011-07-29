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
<h2>Je n'arrive pas à utiliser mes fonctions définies dans Functions depuis Java's Cool</h2>
Vérifiez que la classe Functions et publique et que toutes les méthodes la composant sont
publiques et statiques</h2>