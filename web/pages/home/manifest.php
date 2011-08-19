<?php
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Accueil","index.php"),
            array("Manifeste","")
        ),
        array(
            array("FAQ","?page=home&action=faq")
        )
    );
?>


<?php echo wiki_get_contents('JavaScool:Manifeste'); ?>