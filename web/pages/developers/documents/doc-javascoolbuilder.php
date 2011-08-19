<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("Description de javascoolbuilder","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-hml"),
            array("API","?page=api"),
            array("Exemple de proglet","?page=developers&action=sampleCode"),
            array("FAQ","?page=developers&action=faq"),
            array("jvs","?page=developers&action=doc-jvs")
        )
    );
?>

<?php echo wiki_get_contents('JavaScool:DocJavaScoolBuilder'); ?>