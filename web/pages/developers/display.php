<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","")
        ),
	array(
	    array("Faire une proglet","?page=developers&action=doc-proglets"),
	    array("Comment ça marche","?page=developers&action=spec-proglets")
	)
    );
?>

<?php echo wiki_get_contents('JavaScool:Développement'); ?>
