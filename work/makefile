# vthierry's commands et tbd
### http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork
# (TV0) finir de faire marcher les applets
# (TV0) Pb dans le Core avec ces versions build.xml
# (TV1) Ajouter la doc des mémos à partir de du texte de DL creer memo.htm memo.xml
# (TVx) maj du wiki/issues

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

run : #ant
	#-killall.sh firefox 
	seamonkey ./jpgl/tortueLogo/applet-tag.htm

ant :
#	ant prun
	ant webproglets 
#	ant javascool-proglets.jar ; java -jar dist/proglets/javascool-proglet-sampleCode.jar 

svn :
	cd .. ; ant svn

nbe :
	netbeans --jdkhome $(JHOME) &

pro :
	ant processing

# // @todo a ajouter a ant
