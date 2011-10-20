# vthierry's ongoing tasks
#
# * Sur certaines plateformes eepc les proglets ne sont pas trouvees !!!!
# * Pb avec enVoiture reste vide, pete tout, exemple pas valide ! (repartir d'une JRE SUN propre pour installer opengl)

#export JRE_HOME=/usr/java/default
#export JRE_HOME=/usr/java/jre1.6.0_27
export JRE_HOME=/usr/java/jdk1.7.0_02/jre

tst : 
	ant javascool-proglets.jar ; $(JRE_HOME)/bin/java -jar dist/javascool-proglets.jar

ant :
	ant prun

sos :
	cd ../web ; ant publish-javascool-proglets

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &

usb :
	ant javascool-proglets.jar javascool-builder.jar
	cd dist ; cp javascool-proglets.jar javascool-builder.jar /media/javascool 
