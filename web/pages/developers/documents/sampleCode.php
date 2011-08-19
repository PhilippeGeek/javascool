<?php
showBrowser(
        array(
    array("Java's Cool", "?"),
    array("Développeurs", "?page=developers"),
    array("Exemple de proglet", "")
        ), array(
    array("Proglet", "?page=developers&action=proglets"),
    array("XML", "?page=developers&action=doc-hml"),
    array("jvs", "?page=developers&action=doc-jvs"),
    array("API", "?page=api"),
    array("FAQ", "?page=developers&action=faq"),
    array("javascoolbuilder", "?page=developers&action=doc-javascoolbuilder")
        )
);
?>
<?php echo wiki_get_contents('JavaScool:DocCreationProgletExemple'); ?>