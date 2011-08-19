<?php
// Renvoie le texte sur http://wiki.inria.fr/sciencinfolycee
function wiki_get_contents($name) {
   return wiki_get_contents_load($name);
}
// Lecture du wiki`a travers un cache local
function wiki_get_contents_cache_load($name) {
  $name = rawurlencode($name);
  // Utilisation d'un cache local
  $cache = '.http.cache';
  if (!file_exists($cache)) 
    mkdir($cache, 0777);
  if (!file_exists($cache.'/'.$name)) {
    file_put_contents($cache.'/'.$name, wiki_get_contents_load($name));
    chmod($cache.'/'.$name, 0666);
  }
  return file_get_contents($cache.'/'.$name);
}    

// Lecture du wiki et mise en forme des liens
function wiki_get_contents_load($name) {
  // Recuperation de la page sur le wiki
  $text = file_get_contents('http://wiki.inria.fr/sciencinfolycee/'.$name.'?printable=yes&action=render');  
  // Remplace tous les liens entre pages par des pages vues du site
  $redirections = array(
		 "JavaScool:Accueil" => "?page=home",
		 "JavaScool:Actualité" => "?page=home",
		 "JavaScool:Manifeste" => "?page=home&action=manifest",
		 "JavaScool:Faq" => "?page=home&action=faq",
		 "JavaScool:Lancement" => "?page=run",
		 "JavaScool:Ressources" => "?page=resources", 
		 "JavaScool:Développement" => "?page=developers", 
		 /* A ajouter ensuite:
		  ./pages/developers/documents/doc-jvs.php:'JavaScool:DocLiensJvs'
		  ./pages/developers/documents/sampleCode.php:'JavaScool:DocCreationProgletExemple'
		  ./pages/developers/documents/proglets.php:'JavaScool:DocCreationProglet'
		  ./pages/developers/documents/doc-javascoolbuilder.php:'JavaScool:DocJavaScoolBuilder'
		  ./pages/developers/documents/faq.php:'JavaScool:FaqDéveloppement'
		  ./pages/developers/documents/doc-hml.php:    'JavaScool:DocFormatHml'
		  ./pages/developers/documents/specs-javascoolbuilder.php:'JavaScool:SpecJavaScoolBuilder'
		 */
		 );
  foreach($redirections as $wiki => $php) 
    $text = ereg_replace("href=\"http://wiki.inria.fr/sciencinfolycee/$wiki\"", "href=\"$php\"", $text);
  // Remplace tous les liens wikis locaux pas des liens distants
  $text = ereg_replace('src="/wikis/sciencinfolycee', 'src="http://wiki.inria.fr/wikis/sciencinfolycee', $text);
  // Elimine la table de méta-donnée
  $text = ereg_replace('<table class="wikitable">.*</table>', '', $text);
  // Détecte les erreurs
  if (ereg("<title>(Erreur|Connexion nécessaire)", $text))
    $text = "Erreur: la page wiki $name est en erreur.\n";
  return $text;
}

?>