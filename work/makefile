
# vthierry's ongoing tasks, . . 

dft : www
#	ant javascool-proglets.jar

export JAVA_HOME=/usr/java/jdk1.6.0_25
#export JAVA_HOME=/usr/java/jdk1.7.0_02

export PATH := $(JAVA_HOME)/bin:$(PATH)

export JRE_HOME=/usr/java/jre1.7.0_05
#export JRE_HOME=$(JDK_HOME)/jre

www :
	cd .. ; ant web

mig :
	ant javascool-proglets.jar
	./lib/jvs4tojvs5.sh

tst : 
	ant javascool-proglets.jar
	$(JRE_HOME)/bin/java -jar dist/javascool-proglets.jar

ant :
	ant prun

nbe :
	/usr/java/netbeans-7.0.1/bin/netbeans --jdkhome $(JAVA_HOME) &

sos :
	ant javascool-proglets.jar ; cd ../web ; ant publish-jar

jbl :
	ant javascool-builder.jar ; cd ../sketchbook ; java -jar ../work/dist/javascool-builder.jar

jpr : 
	ant javascool-proglets.jar ; javac -cp dist/javascool-proglets.jar Main.java ; java -cp .:dist/javascool-proglets.jar Main

pro :
	ant processing

pgl :
	cd ../web ; ant build
	$(JRE_HOME)/bin/java -jar ../web/proglets/codagePixels/javascool-proglet-codagePixels.jar 

prg : 
	ant javascool-base.jar ; java -cp dist/javascool-base.jar org.javascool.gui2.TextFilesEditor;

php : 
	cd ../web ; ant local

usb :
	ant javascool-proglets.jar javascool-builder.jar
	cd dist ; cp javascool-proglets.jar javascool-builder.jar /media/javascool 

###################################

