<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("Description de javascoolbuilder","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-xml"),
            array("API","?page=api"),
            array("Exemple de proglet","?page=developers&action=sampleCode"),
            array("FAQ","?page=developers&action=faq"),
            array("jvs","?page=developers&action=doc-jvs")
        )
    );
?>

<p>Java's Cool est con&ccedil;u de fa&ccedil;on &agrave; &ecirc;tre un syst&egrave;me flexible. Il permet notamment à l'enseignant de
    créer sa version personalisée de Java's Cool. Java's Cool est divisé en deux parties : le coeur (core) ou noyau et les proglets.</p>
<p>Une proglet est un ensemble de composants Java, HTML, images et sons réunis dans un dossier (voir les
    <?php showLink('?page=developers&action=proglet','Spécifications d\'une proglet','internal'); ?>).</p>
<p>Javascoolbuilder est un programme écrit en Java permettant de générer une version de Java's Cool réunissant plusieurs proglets.
    Toutes les proglets devant être compilées avec Java's Cool sont réunies dans un dossier qui peut avoir n'importe quel nom : c'est le
    <tt>sketchbool</tt></p>
<p>Le sketchbook contient un dossier par proglet. Il peut aussi contenir des versions de javascool. Dans ce cas, les proglets contenues
    dans ces versions seront aussi ajoutées à la nouvelle version. Typiquement, chaque nouvelle proglet Java's Cool est disponible
    <?php showLink('?page=proglets','sur le site de Java\' Cool','internal'); ?> au format .jar. Il s'agit en fait d'un Java's Cool
    ne contenant qu'une seule proglet. Pour créer une version de Java's Cool réunissant trois proglets du site, il suffit de créer un 
    dossier sketchbook, de télécharger les trois .jar des trois proglets depuis le site et de lancer javascoolbuilder. Le fichier
    produit sera une version de Java's Cool réunissant les trois proglets. Il est également à noter que les jar sources peuvent contenir
    plusieurs proglets. Ainsi, le jar produit peut être ré-utilisé pour créer une nouvelle version de Java's Cool.
</p>
<div class="spec"><p>Ainsi le dossier sketchbook peut contenir : <ul>
        <li>des fichiers .jar contenant chacun une version de Java's Cool contenant une ou plusieurs proglets;</li>
        <li>des dossiers contenant une proglet chacun. Le nom du dossier correspond au nom de la proglet (voir les
    <?php showLink('?page=developers&action=proglet','Spécifications d\'une proglet','internal'); ?>).</li>
    </ul>
</p>
</div>
<p>Pour lancer javascoolbuilder, <?php showLink('?page=run&action=javascoolbuilder','téléchargez-le','internal'); ?> au format jar, placez
    le jar dans votre dossier sketchbook, et lancez le jar comme vous lanceriez Java's Cool (voir les 
    <?php showLink('?page=run','instructions sur comment lancer un jar','internal'); ?>).
</p>

<p>Si vous recevez des erreurs, elles s'afficheront dans le fenêtre de javascoolbuilder. Si vous avez besoin d'aide pour résoudre vos
    erreurs, n'hésitez pas à nous <?php showLink('?page=contact','contacter','internal'); ?></p>