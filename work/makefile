# vthierry's ongoing tasks
#
# * Sur certaines plateformes eepc les proglets ne sont pas trouvees !!!!
# * Pb avec enVoiture reste vide, pete tout, exemple pas valide ! (repartir d'une JRE propre pour installer opengl)

#export JAVA_HOME=/usr/java/default

ant :
	ant prun

tst : 
	ant javascool-proglets.jar ; /usr/java/jdk1.6.0_27/jre/bin/java -jar dist/javascool-proglets.jar

sos :
	cd ../web ; ant publish-javascool-proglets

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &

usb :
	ant javascool-proglets.jar javascool-builder.jar
	cd dist ; cp javascool-proglets.jar javascool-builder.jar /media/javascool 
