<?php
  // array("ancien-lien" => "page-php", ..)
// A rajouter: indirecy\tion pour chaque proglet, page exomath/index pages /progkeet.
$v3_redirections = array(
"api/(.*)" => "?page=api", // puisque toutes les classes ont changé
"Accueil" => "?page=home",
"Menu" => "?page=home",
"Manifest" => "?page=home&action=manifest",
"Faq" => "?page=home&action=faq",
"Contacts" => "?page=contact",
"Contact" => "?page=contact",
"contact" => "?page=contact",
"Crédits" => "?page=contact&action=credits",
"Lancement" => "?page=run",
"Telechargement" => "?page=run",
"Licence"  => "?page=run",
"Developpement" => "?page=developers",
"Ressources" => "?page=ressources
"Activites" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:Activites", 
"Ailleurs" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:Ailleurs", 
"AutresInitiatives" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:AutresInitiatives", 
"Cadrage" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:Cadrage",
"Pepites" => "?page=resources&action=link-pepite",
"Perpites" => "?page=resources&action=link-pepite",
"doc%3A%2Fdocuments%2Fnos-ressources%2Fpepites.html" => "?page=resources&action=link-pepite",
"Revues" => "?page=resources&action=link-revues",
"doc%3A%2Fdocuments%2Fnos-ressources%2Frevues.html" => "?page=resources&action=link-revues",
"doc%3A%2Fdocuments%2Fquelques-t-p-e%2Findex.html" => "?page=resources&action=link-revues", 
"TPE-Quizz" => "?page=resources&action=link-quizz", 
"doc%3A%2Fdocuments%2Fspeed-dating-09%2Findex.html" => "?page=resources&action=link-quizz",
"TPE-Sujets" => "?page=resources&action=link-idees-tpe",
"TPE-Accueil" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:TPE-Accueil",
"TPE-Demos" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:TPE-Demos",
"TPE-Exemples" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:TPE-Exemples",
"TPE-Interventions" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:TPE-Interventions",
"TPE-Methode" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:TPE-Methode",
"doc%3Adocuments%2Fquelques-t-p-e%2Faffiche_finale_inria.pdf" => "doc/documents/quelques-t-p-e/affiche_finale_inria.pdf",
"doc%3Adocuments%2Fquelques-t-p-e%2Fquelques-t-p-e.pdf" => "doc/documents/quelques-t-p-e/quelques-t-p-e.pdf",
"doc%3Asketchbook(.*)" => "http://wiki.inria.fr/sciencinfolycee/JavaScool:ProgletsProcessing", // rebonde tous les sous-liens
);
?>
