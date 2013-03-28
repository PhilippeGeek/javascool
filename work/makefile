
# vthierry's ongoing tasks, . . .

dft : tst

export JAVA_HOME=/usr/java/jdk1.6.0_25
#export JAVA_HOME=/usr/java/jdk1.7.0_07

export PATH := $(JAVA_HOME)/bin:$(PATH)

export JRE_HOME=/usr/java/jre1.7.0_07
#export JRE_HOME=$(JDK_HOME)/jre

www :
	cd .. ; ant web

jvs :
	ant javascool-proglets.jar

tst : 
	ant clean
	ant javascool-proglets.jar
	$(JRE_HOME)/bin/java -jar dist/javascool-proglets.jar

mig :
#	ant javascool-proglets.jar
	./lib/jvs4tojvs5.sh

#################################################

ant :
	ant prun

jdoc :
	ant javadoc ; firefox ./dist/javadoc/overview-summary.html

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

