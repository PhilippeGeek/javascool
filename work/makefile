# vthierry's commands et tbd
### http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork
### http://code.google.com/p/javascool/issues/list

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

ant : 
	ant prun

nbe :
	netbeans --jdkhome $(JHOME) &

www :
	cd ../web ; rsync --rsh='ssh -C' -rl --delete-excluded --exclude .svn/ ../documents scm.gforge.inria.fr:/home/groups/javascool/htdocs/v4

#
# Questionnements:
#
# ProWeb: 
#   0. Creer html_get_contents
#
#   2. Une ligne <p>Cette «proglet» est une composant logiciel libre et ouvert dont documentation et source sont disponibles <l link="api/org/javascool/proglets/algoDeMaths/package-frame.html" text="ici"/></p>
#   1. Tester les liens !
# http://javascool.gforge.inria.fr/v4/index.php?page=api&api=org/javascool/macros/Macros.html
# http://javascool.gforge.inria.fr/v4/index.php?page=proglets&action=show&id=tortueLogo&helpFile=api/org/javascool/proglets/tortueLogo/Functions.html
# http://javascool.gforge.inria.fr/v4/index.php?page=proglets&action=show&id=dichotomie&helpFile=[[[dichotomie/]]]exercice.htm
#
# besoin de reduire les liens avec toto/.. voir dichotomie
## http://javascool.gforge.inria.fr/v4/index.php?page=api&api=org/javascool/builder/DialogFrame.html#startFrame%28%29
## http://javascool.gforge.inria.fr/v4/?page=proglets&action=show&id=abcdAlgos&helpFile=abcdAlgos/tutoriel.htm -> http://javascool.gforge.inria.fr/v4/widgets/icons/play.png
# bref y a un tortueLogo/ en trop !
# BROKEN  Anchor in http://javascool.gforge.inria.fr/v4//?page=api&api=org/javascool/core/JvsBeautifier.html -> http://javascool.gforge.inria.fr/v4//?page=api&api=org/javascool/core/http://download.oracle.com/javase/6/docs/api/java/lang/Object.html?is-external=true #wait()
# user for group javascool) Spurious link = ressources
php :
	xemacs ../web/pages/api/display.php ../web/pages/proglets/show.php ../web/includes/html_get_contents.php
#  - <div id="javadoc">
#  - name/../ -> / ; /./ -> /
# 
#
#
# ProProglets:
#   1. Ajouter les width et height pour les processing + les tailles dans ProgletEngine + test dans Builder
#   2. Virer tests sur processing ds Builder sur help.xml etc...
#   3. 4 x Ajouter les completion.xml dans les proglets
#   4. 4 x Mettre a plat les Functions.java
#   5. 4 x tester les programmes exemples
#
# Voir la finalisation des .exe ou .app ou la doc ecran
#


