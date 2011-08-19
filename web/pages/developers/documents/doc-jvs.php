<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("Spécification des liens jvs","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-hml"),
            array("API","?page=api"),
            array("Exemple de proglet","?page=developers&action=sampleCode"),
            array("FAQ","?page=developers&action=faq"),
            array("javascoolbuilder","?page=developers&action=doc-javascoolbuilder")
        )
    );
?>
<?php echo wiki_get_contents('JavaScool:DocLiensJvs');