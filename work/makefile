# vthierry's ongoing tasks
#
# * Pb avec enVoiture reste vide, pete tout, exemple pas valide ! (repartir d'une JRE propre pour installer opengl)
# * Basculement site: virer /home/groups/javascool/htdocs/{index|sketchbook|javascool.jar|sources.zip|index.php} et ln -s v4/index.php .

ant :
	ant prun

tst : 
	ant javascool-proglets.jar ; /usr/java/jre1.6.0_11/bin/java -jar dist/javascool-proglets.jar

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &
