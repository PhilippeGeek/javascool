<?php
showBrowser(
        array(
    array("Java's Cool", "?"),
    array("Développeurs", "?page=developers"),
    array("Spécifications de javascoolbuilder", "")
        ), array(
    array("Proglet", "?page=developers&action=proglets"),
    array("XML", "?page=developers&action=doc-xml"),
    array("API", "?page=api"),
    array("Exemple de proglet", "?page=developers&action=sampleCode"),
    array("FAQ", "?page=developers&action=faq"),
    array("jvs", "?page=developers&action=doc-jvs")
        )
);
?>

<p>JavaScoolBuilder effectue les actions suivantes : </p>
<ul><li>Créer un jar contenant les fichiers .java de la proglet compilés. Les fichiers Panel.java et Functions.java sont optionnels, il
        est donc possible qu'aucune source java ne soit présente</li>
    <li>Créer un dossier src dans le jar final (dans le dossier de la proglet) contenant les sources java. Ce dossier doit être présent
        même si aucune source java n'est trouvée</li>
    <li>Convertir tous les fichiers .xml du dossier de la proglet en .htm et les mettre dans le jar directement dans le dossier de la proglet :
        ces fichiers ne doivent pas être insérés dans le sketchbook.</li>
    <li>Aucun fichier ne doit être inséré ou modifié dans le sketchbook</li>
    <li>Tous les dossiers temporaires doivent être supprimés à la fin du javascoolbuilder, ou au pire au lancement suivant de
        javascoolbuilder</li>
    <li>Le fichier proglet.pml doit être présent et converti en fichier .php dans le dossier de la proglet dans le jar</li>
    <li>Le fichier proglet.pml doit aussi être copié dans le dossier de la proglet dans le jar, ainsi que tous les fichiers non-java
        et non-xml du sketchbool.</li>
    <li>Le dossier de la proglet dans le sketchbook ne doit pas contenir de sous-dossiers</li>
</ul>
<p>Autrement dit :</p><ul>
    <li>Les fichiers requis sont : <ul>
            <li>proglet.pml</li>
            <li>help.xml</li>
        </ul>
    </li>
    <li>Les fichiers devant être copiés tel quel dans le dossier de la proglet dans le jar sont : 
        <ul>
            <li>proglet.pml</li>
            <li>Tous les fichiers qui ne vérifient aucune des regex preg suivantes :<ul>
                    <li>#^.+\.xml$#</li>
                    <li>#^.+\.java$#</li>
                </ul>
            </li>
        </ul>
    </li>
    <li>Les fichiers devant être copiés dans un sous-dossier 'src' du dossier de la proglet dans le jar sont :
        <ul>
            <li>Tous les fichiers qui vérifient une des regex preg suivantes : <ul>
                    <li>#^.+\.xml$#</li>
                    <li>#^.+\.java$#</li>
                </ul>
            </li>
        </ul>
    </li>
    <li>Les fichiers devant être générés puis copiés dans le dossier de la proglet dans le jar sont : 
        <ul>
            <li>proglet.pml -> proglet.php</li>
            <li>*.xml -> *.htm</li>
            <li>*.java -> *.class</li>
        </ul>
    </li>
</ul>

<p>Voici donc un exemple de génération de proglet : </p>
<p>Liste des fichiers du dossier maProglet du sketchbook : </p>
<ul>
    <li>proglet.pml</li>
    <li>Panel.java</li>
    <li>Functions.java</li>
    <li>Oval.java</li>
    <li>image1.png</li>
    <li>icon.gif</li>
    <li>help.xml</li>
    <li>exercice.xml</li>
    <li>exemple.jvs</li>
    <li>exemple.htm</li>
    <li>exemple2.jvs</li>
    <li>exemple2.xml</li>
</ul>
<p>Liste des fichiers du dossier maProglet du jar : </p>
<ul>
    <li>proglet.pml</li>
    <li>proglet.php</li>
    <li>Panel.class</li>
    <li>Functions.class</li>
    <li>Oval.class</li>
    <li>image1.png</li>
    <li>icon.gif</li>
    <li>exercice.htm</li>
    <li>exemple.jvs</li>
    <li>exemple.htm</li>
    <li>exemple2.jvs</li>
    <li>exemple2.htm</li>
    <li>src/
        <ul>
            <li>Panel.java</li>
            <li>Functions.java</li>
            <li>Oval.java</li>
            <li>help.xml</li>
            <li>exercice.xml</li>
            <li>exemple2.xml</li>
        </ul>
    </li>
</ul>
<p>Après l'exécution de javascoolbuilder, le dossier maproglet du sketchbook reste inchangé</p>