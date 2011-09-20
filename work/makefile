# vthierry's ongoing tasks
#
# * Installer java 7 et voir si proglets arrivent 
# * C'est la merde sur 
# * Sur certaines plateformes eepc les proglets ne sont pas trouvees !!!!
# * Pb avec enVoiture reste vide, pete tout, exemple pas valide ! (repartir d'une JRE propre pour installer opengl)

ant :
	ant prun

now0 :
	cd ../web ; ant publish-javascool-proglets

tst : 
	ant javascool-proglets.jar ; /usr/java/jre1.6.0_11/bin/java -jar dist/javascool-proglets.jar

nbe :
	netbeans --jdkhome $(JHOME) &

www : 
	firefox http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork &
