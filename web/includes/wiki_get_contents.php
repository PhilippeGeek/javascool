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

// Redirections des pages du wiki
$wiki_get_contents_redirections = array(
					"JavaScool:Accueil" => "?page=home",
					"JavaScool:Actualité" => "?page=home",
					"JavaScool:Manifeste" => "?page=home&action=manifest",
					"JavaScool:Faq" => "?page=home&action=faq",
					"JavaScool:Ailleurs" => "?page=home&action=faq-ailleurs",
					"JavaScool:AutresInitiatives" => "?page=home&action=faq-autres",
					"JavaScool:Cadrage" => "?page=home&action=faq-cadrage",
					"JavaScool:InfoAuLycee" => "?page=home&action=faq-infolycee",
					"JavaScool:Lancement" => "?page=run",
					"JavaScool:Licence" => "?page=run&action=licence",
					"JavaScool:Screenshot" => "?page=run&action=screenshot",
					"JavaScool:Cr%C3%A9dits" => "?page=contact&action=credits",
					"JavaScool:Proglet" => "?page=proglets",
					"JavaScool:Développement" => "?page=developers", 
					"JavaScool:DocCreationProglet" => "?page=developers&action=spec-proglets",
					"JavaScool:DocCreationProgletExemple" => "?page=developers&action=doc-proglets",
					"JavaScool:ProgletProcessing" => "?page=developers&action=doc-processing",
					"JavaScool:DocFormatHml" => "?page=developers&action=doc-hml",
					"JavaScool:DocumentsHml" => "?page=developers&action=spec-hml",
					"JavaScool:EditorCompletion" => "?page=developers&action=doc-completion",
					"JavaScool:DocJavaScoolBuilder" => "?page=developers&action=doc-javascoolbuilder",
					"JavaScool:SpecJavaScoolBuilder" => "?page=developers&action=spec-javascoolbuilder",
					"JavaScool:FaqD%C3%A9veloppement" => "?page=developers&action=faq-developers",
					"JavaScool:Ressources" => "?page=resources");

// Lecture du wiki et mise en forme des liens
function wiki_get_contents_load($name) {
  global $wiki_get_contents_redirections;
  // Recuperation de la page sur le wiki
  $text = file_get_contents('http://wiki.inria.fr/sciencinfolycee/'.$name.'?printable=yes&action=render');  
  // Remplace tous les liens entre pages par des pages vues du site
  foreach($wiki_get_contents_redirections as $wiki => $php) 
    $text = ereg_replace("href=\"http://wiki.inria.fr/sciencinfolycee/$wiki\"", "class=\"internal\" href=\"$php\"", $text);
  // Remplace tous les liens wikis locaux pas des liens distants
  $text = ereg_replace('src="/wikis/sciencinfolycee', 'src="http://wiki.inria.fr/wikis/sciencinfolycee', $text);
  // Qualifie proprement les liens internes issus du wiki
  $text = ereg_replace("href=\"http://javascool.gforge.inria.fr/([^\"]*)\" *class=\"external text\"", "href=\"\\1\" class=\"internal\"", $text);
  // Elimine la table de méta-donnée
  $text = ereg_replace('<table class="wikitable">.*</table>', '', $text);
  // Détecte les erreurs
  if (ereg("<title>(Erreur|Connexion nécessaire)", $text))
    $text = "Erreur: la page wiki $name est en erreur.\n";
  return $text;
}

?>
