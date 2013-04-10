---
layout: default
title: Développer autour de Java'sCool 5
---

## Le fichier *proglet.json* et son icône

Ce fichier en syntaxe [json](http://www.json.org) donne la description de la proglet, le nom de son icône et les auteurs, sous la forme:

	{
	 "title"  : "Exemple de «proglet» (1 à 2 lignes de description)",
	 "author" : "Guillaume Matheron <guillaume.quest@gmail.com>", 
	 "icon"   : "icon.png"
	}

* Les trois champs sont obligatoires, et le champ *author* doit faire apparaître un email de contact.

* Le fichier image *icon* est celui du logo de la proglet. Elle peut avoir n'importe quel nom mais :
  * Il est quasi-indispensable que cette image soit au format png transparent. Sans cela il est très difficile de l'intégrer sur le site sans affecter son design.
  * La taille maximale et conseillée de cette image est de 128x128 pour respecter le design. 
  * Le site [iconfinder](http://www.iconfinder.com) est un excellent outil pour trouver des icônes. 




