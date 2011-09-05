# vthierry's ongoing tasks
#
# ProProglets: tester contruction, completion et programme exemples 
# Basculement site: virer /home/groups/javascool/htdocs/{index|sketchbook|javascool.jar|sources.zip|index.php} et ln -s v4/index.php .
#
# Refs: http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork & http://code.google.com/p/javascool/issues/list

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

dft : tst1

ant : 
	ant prun

nbe :
	netbeans --jdkhome $(JHOME) &

tst1 :
	ant javascool-proglets.jar
	/usr/java/jre1.6.0_27/bin/java -jar dist/javascool-proglets.jar

tst2 :
	javac -cp ../sketchbook/.build/jar ../sketchbook/grapheEtChemins/src/grapheEtCheminsFunctions.java


#> throwable = java.lang.NullPointerException
#> stack-trace = «ddf.minim.javasound.JSMinim.getAudioInputStream(JSMinim.java:604)ddf.minim.javasound.JSMinim.getAudioRecordingStream(JSMinim.java:200)ddf.minim.Minim.loadFile(Minim.java:357)ddf.minim.Minim.loadFile(Minim.java:341)exploSonore$record.setRecord(exploSonore.java:446)»
