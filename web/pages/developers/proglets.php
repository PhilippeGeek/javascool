<?php showBrowser(array(array("Java's Cool","index.php"),array("D&eacute;veloppeurs","?page=developers"),array("Proglets",""))); ?>

<pre>
 - plugin javascool
 - donne accàs aux élèves des fonctions personalisés 
 - afficher des résultats dans un panel graphique
 - proposer de la documentation qui correspond à l'activité
</pre>

<hr>

<div class="label2">Spécification d'une «proglet»</div> <ul>

<li> Tous les fichiers nécessaires au fonctionnement de la proglet sont placés dans un <em>dossier</em>.</li>

<br>

<li> Le nom du dossier correspond à l'<em>identifiant</em> de la proglet.

<div class="spec">
    L'identifiant (<tt>id</tt>) de chaque proglet doit être unique.
    Regarder sur <?php showLink("?page=proglets", "la table des proglets", "internal");?> les noms existants.
</div>
<div class="spec">
   Le nom doit avoir au plus seize lettres minuscules ou majuscules; pas de chiffre ou de symbole.
   Il doit être de la forme «<tt>algoDeMaths</tt>» pour «algo de maths» c'est à dire, sans espace, commencer par une minucule et les autres mots par une majuscule.
</div>
On respecte ici la convention de nommage des packages Java. </li>

<br>

<li>Les fichiers standards de la proglet: <ul>
  <li><tt>proglet.pml</tt> : le descripteur de la proglet.</li>
  <li><tt>help.xml</tt> : le fichier de documentation de la proglet.</li>
  <li><tt>Functions.java</tt> : qui définit les fonctions proposées à l'élève (<em>optionnel</em>).</li>
  <li><tt>Panel.java</tt> : qui implémente l'applet graphique de la proglet (<em>optionnel</em>).</li>
</ul> 

<div class="spec"><tt>proglet.pml</tt> est un fichier texte de trois lignes données sous la forme:
   <br>&nbsp; title = "Description en 1 à 2 lignes de la proglet"
   <br>&nbsp; author = "Prenom Nom &lt;email@serveur>, Prenom Nom &lt;email@serveur>, .."
   <br>&nbsp; icon = "Le nom de fichier de l'image qui décrit la proglet (<em>optionnel</em>)"
</div>

<div class="spec"><tt>help.xml</tt> est un fichier en XML dont les balises sont <?php showLink("?page=developers&action=doc-xml", "définies ici", "internal");?>.
<br>Il contient des liens vers les autres pages, il peut inclure une vidéo externe au site, qui montre le fonctionnement de la proglet, etc..
</div>

<div class="spec"><tt>Functions.java</tt> est une classe publique sans parent, qui contient uniquement les fonctions et constantes statiques et publiques, destinées à l'utilisateur. Elle est documentée soit en anglais, soit en français, à destination de l'élève. Tous les identificateurs utilisés dans cette classes deviennent des mots réservés. Il ne faut rien mettre d'autre dans cette classe.
</div>

<div class="spec"><tt>Panel.java</tt> est une classe publique qui a <tt>java.awt.Applet</tt> comme parent. Par conséquent: <ul>
  <li>La méthode <tt>init()</tt>, définie dans cette classe, qui permet de construire l'applet, initialiser les objets graphiques, gestionnaires d'événements, etc..</li>
  <li>La méthode <tt>destroy()</tt>, appelée à la fermeture de la proglet, doit détruire ce que <tt>init()</tt> et l'élève ont fait.</li>
  <li>La méthode <tt>start()</tt>, optionelle, n'est appelée que pour lancer une <em>démonstration</em> du fonctionnement de la proglet. La méthode <tt>stop()</tt> est appelée à l'arrêt de la démosntration.</li>
</ul>
</div>




</li></ul>