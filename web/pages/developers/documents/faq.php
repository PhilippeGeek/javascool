<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("FAQ du développeur","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-hml"),
            array("javascoolbuilder","?page=developers&action=doc-javascoolbuilder"),
            array("API","?page=api"),
            array("Exemple de proglet","?page=developers&action=sampleCode"),
            array("jvs","?page=developers&action=doc-jvs")
        )
    );
?>
<?php echo wiki_get_contents('JavaScool:FaqDéveloppement'); ?>
