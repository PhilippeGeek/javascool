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

<p>Pour lancer Java's Cool, rien à installer, cliquez sur un des liens ci-dessus.</p>
<p>Si le lancement échoue, vous devrez probablement <a href="http://www.java.com/fr/download">installer Java<a/> (version au moins 1.6).</p>
<p>En cas d'échec <a href="?page=contact">contactez-nous</a>.</p>

<p>Pour en savoir plus sur l'utilisation de l'interface se reporter à sa <a href="?page=run&action=screenshot">description picturale</a>.

<div class="label2">Windows</div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir» ou «ouvrir avec Java»:</p>
<table border ="1" align="center"><tr><td><img src="images/screen3-1.png" alt="screenshot"/></td></tr></table>

<p>Si l'option  «ouvrir ...» n'est pas présente, il suffit de téléchargé le le fichier <tt>javascool-proglets.jar</tt> sur le bureau puis de cliquer dessus pour le lancer:</p>
<table align="center"><tr><td>(1) télécharger sur le bureau</td><td></td><td>(2) cliquer pour le lancer</td><td></td><td>(3) accepter le lancement</td></tr><tr>
<td valign="top"><img src="images/screen4-1.png" alt="screenshot"/></td>
 <td><span class="label-arrow" /></td>
<td valign="top"><img src="images/screen5-1.png" alt="screenshot"/></td>
 <td><span class="label-arrow" /></td>
<td valign="top"><img src="images/screen6-1.png" alt="screenshot"/></td>
</tr></table>

<div class="label2">Mac OS</div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> exécutable <b><a href="javascool-proglets.jar">ici</a></b>, en choisissant l'option «ouvrir avec Java».</p>

<p>- On peut aussi choisir installer Java's Cool comme une <a href="#Installation_comme_application_MacOS">application MacOS</a> (dossier <tt>.app</tt>).</p>

<div class="label2">Linux</div>

<p>Téléchargez Java's Cool au format <tt>JAR</tt> <b><a href="javascool-proglets.jar">ici</a></b>, puis suivez les instructions ci-dessous :</p> 
<table><tr>
 <td><img src="images/screen1.png" alt="screenshot" style="width: 300px; height: auto;"/></td>
 <td><span class="label-arrow" /></td>
 <td><img src="images/screen2.png" alt="screenshot" style="width: 300px; height: auto;"/></td></tr>
</table
                
<p>-On peut aussi choisir <tt>Sauver sous</tt> pour ne pas télécharger le logiciel à chaque lancement puis <tt>Ouvrir avec . . <i>java</i></tt>.</p>

<p>-On peut aussi lancer à partir d'une ligne de commande <tt>java -jar javascool-proglets.jar</tt> une fois le logiciel téléchargé.</p>
    

<div class="label2">Plus d'informations</div>
    <?php echo wiki_get_contents('JavaScool:Lancement'); ?>
</div>
