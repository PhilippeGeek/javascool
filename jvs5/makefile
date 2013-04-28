########################################################################################################################################################
#
# @TODO :
#
# - Corriger PB windows avec ordi + valider javaStart
#
# @THEN
# - Voir pb de librairies toxi processing 2.0--bits 64
# - Comprendre usage de --export
# - Deployer un linkcheck sur les liens
# - Automatiser les productions de librairies pour gérer les contenus processing 
# - Voir jar jbed jad http://www.developpez.net/forums/d922916/java/general-java/java-mobiles/android/android-jar
# - Reorganiser jvs5 sous github javascool/{documents,sketchbook,processing,javascool-core/{makefile,bin,src,lib}} et miroir retour sur jvs4
# - Creer les rebonds de v4 -> v5 selon l'usage qui se profile
#
#
########################################################################################################################################################

dft : tst

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Installation du web github

GIT_HOME=./javascool.github.com

DIST=$(GIT_HOME)/wproglets

########################################################################################################################################################

sos : 
	cp ../work/lib/javascool.bat ../work/dist/javascool-proglets.jar ~/Desktop

# Tests en cours 

tstProglet = algoDeMaths

tst  : 
	rm -rf $(DIST)/javascool-proglet-$(tstProglet)-html
	$(MAKE) cpv $(DIST)/javascool-core.jar $(DIST)/javascool-proglet-$(tstProglet)-html
	echo "Jvs2Jar=/user/vthierry/home/Work/culsci/ongoing/jvs/jvs5/javascool.github.com/wproglets/javascool-proglet-algoDeMaths-html/JeuSimon.jvs" > $(HOME)/.javascool/webcommand.mf
#	firefox $(DIST)/javascool-proglet-$(tstProglet)-html/index.html
	java -jar $(DIST)/javascool-proglet-$(tstProglet)-html/javascool-proglet-$(tstProglet).jar

########################################################################################################################################################

# Lancement de l'environnement processing

prc :
	/usr/java/processing-2.0b8/processing

########################################################################################################################################################

# Publie les fichiers du site web

www : clean cnv jar $(patsubst sketchbook/%,$(DIST)/javascool-proglet-%-html,$(wildcard sketchbook/*))
#	$(MAKE) -C $(GIT_HOME) push
	ls $(DIST)/javascool-proglet-*-html/compilation.false.log.txt

# Construit les proglets

$(DIST)/javascool-proglet-%-html : sketchbook/%
	echo build $*
	java -jar $(DIST)/javascool-core.jar $@ $^

# Construit la jarre de javascool 

SRC = $(wildcard lib4/*.jar) $(shell find src4+5 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm' -o -name '*.zip')

jar : cpv $(DIST)/javascool-core.jar $(DIST)/javascool-core-api 

$(DIST)/javascool-core.jar : $(SRC)
	echo javac javascool-core
	sh ./bin/java2jar.sh -static -main=org.javascool.core.MainWrapper $@ $^

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
	rm -rf tmp-*
	svn status

cleanall : clean
	rm -rf $(DIST)/* 

# Test des différences entre jvs4 et jvs5

diff : 
	sh bin/diffv4v5.sh

########################################################################################################################################################
