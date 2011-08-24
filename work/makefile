# vthierry's commands et tbd
### http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork
# (TV0) finir de faire marcher les applets
# (TV1) Ajouter la doc des mémos à partir de du texte de DL 
## creer memo.htm memo.xml

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

ant :
	ant prun
#	ant webproglets; appletviewer dist/proglets/jar/org/javascool/proglets/tortueLogo/applet-tag.htm 
#	ant javascool-proglets.jar ; java -jar dist/proglets/javascool-proglet-sampleCode.jar 

svn :
	cd .. ; ant svn

nbe :
	netbeans --jdkhome $(JHOME) &

pro :
	ant processing

# // @todo a ajouter a ant

src/org/javascool/builder/javascool.key : makefile
	keytool -genkey -alias javascool -validity 3000 -dname cn='fuscia-accueil@inria.fr, ou=javascool.gforge.inria.fr, o=inria.fr, c=fr' -storepass javascool -keypass mer,d,azof -keystore src/org/javascool/builder/javascool.key
