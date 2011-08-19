<?php 
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Lancement","")
        ),
        array(
            array("Licence","?page=run&action=licence")
        )
    );
?>
<div align="center">
    <big>Il suffit  de cliquer <big><big><a href="http://javascool.gforge.inria.fr/v4/javascool.jar">ICI</a></big></big> et de choisir <tt>Ouvrir avec . . <i>java</i></tt>.</big>
</div>

<div class="display">
    <div class="label2">Windows</div>&nbsp;<br />
    //@TODO windaube screenshots
    <div class="label2">Mac OS</div>&nbsp;<br />
    //@TODO macosxx screenshots
    
    
    <div class="label2">Linux</div>
	<p>Pour lancer Java's Cool sans installation, cliquez sur le lien ci-dessus. Si le lancement échoue, vous devrez probablement installer Java.
	Sous linux, téléchargez le fichier .jar, puis suivez les instructions ci-dessous : 
    <table><tr><td><img src="index/images/screen1.png" alt="screenshot" style="width: 300px; height: auto;"/></td><td>
                <span class="label-arrow" /></td><td>
                <img src="index/images/screen2.png" alt="screenshot" style="width: 300px; height: auto;"/></td></tr></table></p>
                
<p>-On peut aussi choisir <tt>Sauver sous</tt> pour ne pas télécharger le logiciel à chaque lancement puis <tt>Ouvrir avec . . <i>java</i></tt>.</p>
<p>-On peut aussi lancer à partir d'une ligne de commande <tt>java -jar javascool.jar</tt> une fois le logiciel téléchargé.</p>
    

<div class="label2">Plus d'informations</div>
    <?php echo wiki_get_contents('JavaScool:Lancement'); ?>

</div>
