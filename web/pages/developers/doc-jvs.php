<?php
    showBrowser(
        array(
            array("Java's Cool","?"),
            array("Développeurs","?page=developers"),
            array("Spécification des liens jvs","")
        ), array(
            array("Proglet","?page=developers&action=proglets"),
            array("XML","?page=developers&action=doc-xml"),
            array("javascoolbuilder","?page=developers&action=doc-javascoolbuilder")
        )
    );
?>

<div class="label2">Où peut-on utiliser des liens jvs:// ?</div>
<p>Dans tous les fichier affichés dans le panneau de droite de Java's Cool, c'est-à-dire dans le fichier help.xml et dans tous les fichiers
auxquels il faut référence.</p>

<div class="label2">Quand sont-ils utiles ?</div>
<p>Les liens jvs:// sont utiles lorsque l'aide propose à l'utilisateur d'ouvrir un nouveau fichier de code, par exemple un code d'exemple
    ou un exercice. Le lien jvs:// permet : </p><ul>
    <li>D'ouvrir un fichier .jvs dans l'éditeur</li>
    <li>D'ouvrir simultanément une page web dans le navigateur intégré de Java's Cool</li>
</ul><p>Ceci permet par exemple d'afficher l'énoncé de l'exercice dans la page, ou encore la documentation de l'exemple. La page web doit être
spécifiée au format .htm mais il est préférable de la rédiger en .xml 
(<?php showLink('?page=developers&action=doc-xml',"spécifications des fichiers xml de Java's Cool",'internal'); ?>) : le fichier .xml sera
converti en .htm lors de l'exécution de <?php showLink('?page=developers&action=doc-javascoolbuilder','javascoolbuilder','internal'); ?>.
Cependant le lien jvs doit toujours référer à un fichier .html, même si ce dernier n'es créé que lors du passage dans javascoolbuilder.</p>

<div class="label2">Quelle est leur syntaxe ?</div>
<p>Voici un exemple de lien jvs : </p>
<tt>&lt;a href="jvs://openhtml:Orbiter.html:Orbiter,openjvs:orbiter.jvs"&gt;Ouvrir l'exemple&lt;/a&gt;</tt>
<p>Ce lien ouvre le fichier html "Orbiter.html" dans un nouvel onglet appellé "Orbiter", puis le fichier orbiter.jvs dans l'éditeur.</p>
<div class="spec"><p>Le format d'un lien jvs est le suivant :</p>
<tt>jvs://openhtml:FICHIER_HTML.HTML:NOM_DE_L_ONGLET,openjvs:FICHIER_JVS</tt><p>
Les deux parties séparées par une virgule sont optionnelles : on peut utiliser les liens suivants pour n'ouvrir qu'un fichier ou qu'un page
web respectivement : </p>
<tt>jvs://openhtml:FICHIER_HTML.HTML:NOM_DE_L_ONGLET</tt><br />
<tt>jvs://openjvs:FICHIER_JVS</tt>
<p>Les fichier jvs et html doivent être dans le dossier de la proglet.</p></div>