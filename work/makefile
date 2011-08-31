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
# - qui du javascool-framework.jar ?
# - ok pour http://wiki.inria.fr/sciencinfolycee/JavaScool:About ?
#
# ProWeb:
#   1. produire le web et tester les liens !
#
# ProProglets:
#   1. Ajouter les width et height pour les processing + les tailles dans ProgletEngine + test dans Builder
#   2. Virer tests sur processing ds Builder sur help.xml etc...
#   3. 4 x Ajouter les completion.xml dans les proglets
#   4. 4 x Mettre a plat les Functions.java
#   5. 4 x tester les programmes exemples
#
# JavaProg
#   1. Tester les codes de javaProg
#
