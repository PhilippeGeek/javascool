<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-xml"),
            array("javascoolbuilder","?page=developers&action=doc-javascoolbuilder"),
            array("API","?page=api"),
            array("jvs","?page=developers&action=doc-jvs")
        )
    );
?>

<p>Java's Cool est d&eacute;velopp&eacute; par des membres de l'Institut National de Recherche en Informatique et Automatique (INRIA) &agrave; Sophia Antipolis (06).</p>

<?php include('document.php'); ?>