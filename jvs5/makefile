########################################################################################################################################################
#
# @TODO :
#
#. voir jvs avec fichiers pister les load
#. tester wrapper ds mzkefile .. err et ss err
#
# Commencer a calculer les rebonds vers v5/*
# Valider le mécanisme de webservice en lancement d'un programme local
#
# Voir comment se gère les proglets processing et bien valider les liens vers le site fuscia
## cp $d/applet/{*.java,{name}.java} $d ; rm -rf applet
## /usr/java/processing-2.0b8/processing-java --sketch=sketchbook/cryptageRSA --output=tmp --force --build
## voir pb clean of jvs5/{libraries,tools,modes,tmp}
## voir a creer Panel extends .. { pour eviter le pb des non package
## http://code.google.com/p/processing/issues/detail?id=142
#
# @DEPLOY
#
# Vider javascool sous github et y basculer {documents,sketchbook,javascool-core} en un ou en trois ?
# Valider le www en ligne
## faire git add wproglets quand stable
# Verifier en ligne les nv liens des proglet's docs.
# Reduire le site v4 à accueil + lancement + rebonds vers v5
#
# @THEN
# - Voir jar jbed jad http://www.developpez.net/forums/d922916/java/general-java/java-mobiles/android/android-jar
#
#
########################################################################################################################################################

dft : tst1

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Installation du web github

GIT_HOME=./javascool.github.com

DIST=$(GIT_HOME)/wproglets

########################################################################################################################################################

# Test en cours 

tstP = cryptageRSA

tst1  : cleanall cpv $(DIST)/javascool-core.jar $(DIST)/javascool-proglet-$(tstP)-html
	firefox $(DIST)/javascool-proglet-$(tstP)-html/index.html
#	java -jar $(DIST)/javascool-proglet-$(tstP)-html/javascool-proglet-$(tstP).jar
#	java -jar $(DIST)/javascool-proglet-algoDeMaths-html/JeuSimon.jvs.jar 

tst2 : cleanall cpv $(DIST)/javascool-core.jar
	java -cp $(DIST)/javascool-core.jar org.javascool.core.Proglet2Html

prc :
	/usr/java/processing-2.0b8/processing

########################################################################################################################################################

# Publie les fichiers du site web

www : clean cnv jar $(patsubst sketchbook/%,$(DIST)/javascool-proglet-%-html,$(wildcard sketchbook/*))
	for d in $(DIST)/javascool-proglet-*-html ; do ls $$d/compilation.*.log.txt ; done
#	$(MAKE) -C $(GIT_HOME) push

# Construit les proglets

$(DIST)/javascool-proglet-%-html : sketchbook/%
	echo build $*
	java -jar $(DIST)/javascool-core.jar $@ $^

# Construit la jarre de javascool 

SRC = $(wildcard lib4/*.jar) $(shell find src4+5 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm' -o -name '*.zip')

jar : cpv $(DIST)/javascool-core.jar $(DIST)/javascool-core-api 

$(DIST)/javascool-core.jar : $(SRC)
	echo javac javascool-core
	sh ./bin/java2jar.sh -static -main=org.javascool.core.Proglet2Html $@ $^

$(DIST)/javascool-core-api : $(SRC)
	echo javadoc javascool-core
	java -jar $(DIST)/javascool-core.jar javascool-core src4+5 $@

# Effectue la copie des src/lib de jvs4 vers jvs5

cpv :
	sh ./bin/srclib4to5.sh

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh

# Nettoie les répertoires générés

clean :
	rm -rf tmp-* libraries modes tools
	svn status
#	cd $(GIT_HOME) ; git status @todo

cleanall : clean
	rm -rf $(DIST)/* 

# Test des différences entre jvs4 et jvs5

diff : 
	sh bin/diffv4v5.sh

########################################################################################################################################################
