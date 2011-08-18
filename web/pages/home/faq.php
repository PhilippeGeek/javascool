<?php
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Accueil","index.php"),
            array("FAQ","")
        ),
        array(
            array("Cadrage","?page=home&action=cadrage"),
            array("Manifeste","?page=home&action=manifest")
        )
    );
?>


<?php echo wiki_get_contents('JavaScool:Faq'); ?>