# vthierry's ongoing tasks

export JAVA_HOME=/usr/java/jdk1.6.0_22
#export JAVA_HOME=/usr/java/jdk1.7.0_02

export PATH := $(JAVA_HOME)/bin:$(PATH)

#export JRE_HOME=/usr/java/jre1.6.0_27
export JRE_HOME=/usr/java/jre1.7.0_01

tst : 
	ant javascool-proglets.jar ; $(JRE_HOME)/bin/java -jar dist/javascool-proglets.jar

ant :
	ant prun

nbe :
	netbeans --jdkhome $(JAVA_HOME) &

sos :
	cd ../web ; ant publish-jar

jbl :
	ant javascool-builder.jar ; cd ../sketchbook ; java -jar ../work/dist/javascool-builder.jar

php : 
	cd ../web ; ant local

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &

usb :
	ant javascool-proglets.jar javascool-builder.jar
	cd dist ; cp javascool-proglets.jar javascool-builder.jar /media/javascool 
