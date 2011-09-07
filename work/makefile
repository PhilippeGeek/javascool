# vthierry's ongoing tasks
#
# Proglets: tester contruction et programme exemples 
# * retarder la construction de la proglet à l'ouverture et non au load pour alleger le jar
# * tester les proglets en popup 
# * pb : javax/media/opengl/glu/GLUtessellatorCallback enVoiture
# * voir à ajouter javascool-base.jar au jar de processing pour avoir la compilation sans ./src
# * verifer que processing met bien le .class dans org/**/proglets
# * traiter le problème par une regex dans Translator poru avoir une soluce et documenter

dft : tst1

ant : 
	ant prun

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &
	firefox http://code.google.com/p/javascool/issues/list

tst1 :
	ant javascool-proglets.jar
	/usr/java/jre1.6.0_27/bin/java -jar dist/javascool-proglets.jar

tst2 :
	javac -cp ../sketchbook/.build/jar ../sketchbook/grapheEtChemins/src/grapheEtCheminsFunctions.java

################################################################################################################

# Basculement site: virer /home/groups/javascool/htdocs/{index|sketchbook|javascool.jar|sources.zip|index.php} et ln -s v4/index.php .

# Problèmes à traite rpour la RC2: verifier que les ressources sont bien chargees et bon noms de fichier 
# - Problème proglet (lecture du fichier wav) son avec java.lang.NullPointerException stack-trace = «ddf.minim.javasound.JSMinim.getAudioInputStream(JSMinim.java:604)ddf.minim.javasound.JSMinim.getAudioRecordingStream(JSMinim.java:200)ddf.minim.Minim.loadFile(Minim.java:357)ddf.minim.Minim.loadFile(Minim.java:341)exploSonore$record.setRecord(exploSonore.java:446)»
# - Problème proglet enVoiture avec la fenêtre qui apparait pas
