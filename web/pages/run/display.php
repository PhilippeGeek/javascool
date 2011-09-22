<?php 
    showBrowser(
        array(
            array("Java's Cool","index.php"),
            array("Lancement","")
        ),
        array(
            array("Licence","?page=run&action=licence"),
            array("Usage","?page=run&action=screenshot")
        )
    );
?>

<div class="display">

<p>Pour lancer Java's Cool, rien à installer, ormis Java1.6. <br/><table border="1"><tr><td>
Il suffit de le télécharger <b><a href="javascool-proglets.jar">ICI</a></b>, en choisissant l'option «ouvrir» ou «ouvrir avec Java».
</td></tr></table><br/></p>

<p>- Si le lancement échoue, vous devrez probablement <a href="http://www.java.com/fr/download">installer Java<a/> (version au moins 1.6).</p>
<p>- Ci dessous, nous donnons des indicatios pour vous aider si l'installation de votre ordinateur est singulière.</p>
<p>- Si votre système est trop ancien la <a href="http://javascool.gforge.inria.fr/v3">version 3</a> est encore disponible.</p>

<p>- En cas d'échec <a href="?page=contact">contactez-nous</a>.</p>

<p>Pour en savoir plus sur l'utilisation de l'interface se reporter à sa <a href="?page=run&action=screenshot">description picturale</a>.

<hr/><div class="label2"><a name="windows">Windows</a></div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir» ou «ouvrir avec Java»:</p>
<table border ="1" align="center"><tr><td><img src="images/screen3-1.png" alt="screenshot"/></td></tr></table>

<p>- Si l'option  «ouvrir ...» n'est pas présente, il suffit de téléchargé le le fichier <tt>javascool-proglets.jar</tt> sur le bureau puis de cliquer dessus pour le lancer:</p>
<table align="center"><tr><td>(1) télécharger sur le bureau</td><td></td><td>(2) cliquer pour le lancer et accepter le lancement</td></tr><tr>
<td valign="top"><table border ="1" align="center"><tr><td><img src="images/screen4-1.png" alt="screenshot"/></td></tr></table></td>
<td valign="top"><span class="label-arrow" /></td>
<td valign="top"><table border ="1" align="center"><tr><td><img src="images/screen56-1.png" alt="screenshot"/></td></tr></table></td>
</tr></table>

<hr/><div class="label2"><a name="macos">Mac OS</a></div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir avec Java».</p>
<table border ="1" align="center"><tr><td><img src="images/screen7.png" alt="screenshot"/></td></tr></table>

<p>- Si votre MAC OS X est un peu ancien, vous pouvez <a href="#Installation_avec_un_Mac_OS_X_un_peu_ancien">activer Java 1.6</a>.</p>

<p>- On peut aussi choisir installer Java's Cool comme une <a href="#Installation_comme_application_MacOS">application MacOS</a> (dossier <tt>.app</tt>).</p>

<hr/><div class="label2"><a name="linux">Linux</a></div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> <b><a href="javascool-proglets.jar">ici</a></b>, puis suivez les instructions ci-dessous :</p> 
<table border ="1" align="center"><tr><td><table><tr>
 <td><img src="images/screen1.png" alt="screenshot" style="width: 300px; height: auto;"/></td>
 <td><span class="label-arrow" /></td>
 <td><img src="images/screen2.png" alt="screenshot" style="width: 300px; height: auto;"/></td></tr>
</table></td></tr></table>
                
<p>- On peut aussi choisir <tt>Sauver sous</tt> pour ne pas télécharger le logiciel à chaque lancement puis <tt>Ouvrir avec . . <i>java</i></tt>.</p>

<p>- On peut aussi lancer à partir d'une ligne de commande <tt>java -jar javascool-proglets.jar</tt> une fois le logiciel téléchargé.</p>
    

<hr/><div class="label2">Plus d'informations</div>
    <?php echo wiki_get_contents('JavaScool:Lancement'); ?>
</div>
