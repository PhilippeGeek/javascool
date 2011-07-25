<?php 
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Lancement","")
        ),
        array(
            array("Licence","?page=home&action=licence")
        )
    );
?>

<!-- //TODO -->
<div class="display">
    <div class="label2">Windows</div>&nbsp;<br />
    <?php//TODO windaube screenshots?>
    <div class="label2">Linux</div>
	Pour lancer Java's Cool sans installation, cliquez sur le lien ci-dessus. Si le lancement échoue, vous devrez probablement installer Java.
	Sous linux, téléchargez le fichier .jar, puis suivez les instructions ci-dessous : 
    <table><tr><td><img src="images/screen1.png" alt="screenshot" style="width: 300px; height: auto;"/></td><td>
                <span class="label-arrow" /></td><td>
                <img src="images/screen2.png" alt="screenshot" style="width: 300px; height: auto;"/></td></tr></table>
    <div class="label2">Plus d'informations</div>
    <?php include('pages/run/lancement.php'); ?><br /><br /><br /><br />
    <div class="label2">Encore plus d'informations</div>
    <?php include('pages/run/document.php'); ?>
</div>
