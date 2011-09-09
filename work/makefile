# vthierry's ongoing tasks
#
# Proglets: tester contruction et programme exemples 
# * pn enVoiture reste vide
# * voir à ajouter javascool-base.jar au jar de processing pour avoir la compilation sans ./src
# * verifer que processing met bien le .class dans org/**/proglets
# * traiter le problème par une regex dans Translator poru avoir une soluce et documenter

dft : tst2

ant : 
	ant prun

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &
	firefox http://code.google.com/p/javascool/issues/list

tst1 :
	ant javascool-proglets.jar
	/usr/java/jre1.6.0_11/bin/java -jar dist/javascool-proglets.jar

tst2: 
	firefox ../sketchbook/enVoiture/applet/index.html 

################################################################################################################

# Basculement site: virer /home/groups/javascool/htdocs/{index|sketchbook|javascool.jar|sources.zip|index.php} et ln -s v4/index.php .
