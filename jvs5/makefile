########################################################################################################################################################
#
# @TODO :
#
# Voir le javaStart sous windows 
# Voir le pb de la console jsv2jar + gerer la recup de stdout sans graphique aussi pour stocket un pg dans chaque repertoire
# Voir comment se gère les proglets processing et bien valider les liens vers le site fuscia
## /usr/java/processing-2.0b8/processing-java --sketch=sketchbook/cryptageRSA --output=tmp --force --build
## http://code.google.com/p/processing/issues/detail?id=142
# Voir pour la proglet java3D
# Valider le make www de ici et le git (avec le wget de http://javascool.gforge.inria.fr/v5 dans javascool.github.com/wproglets)
# Verifier en ligne les nv liens des proglet's docs.
#
# @THEN
# - Recuperer l'éditeur HTML5 de Philippe est en faire un produit autonome qui se connecte aux web services
# - Voir au déploiement par jnlp des jars utilisés
# - Voir jar jbed jad http://www.developpez.net/forums/d922916/java/general-java/java-mobiles/android/android-jar
#
#
########################################################################################################################################################

dft : cnv

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Installation du web github

GIT_HOME=./javascool.github.com

DIST=$(GIT_HOME)/wproglets

########################################################################################################################################################

# Test en cours 

prc :
	/usr/java/processing-2.0b8/processing

tstP = algoDeMaths

tst1  : cleanall cpv $(DIST)/javascool-core.jar $(DIST)/javascool-proglet-$(tstP)-html
	firefox $(DIST)/javascool-proglet-$(tstP)-html/index.html
#	java -jar $(DIST)/javascool-proglet-$(tstP)-html/javascool-proglet-$(tstP).jar
#	java -jar $(DIST)/javascool-proglet-algoDeMaths-html/JeuSimon.jvs.jar 

tst2 : cleanall cpv $(DIST)/javascool-core.jar
	java -cp $(DIST)/javascool-core.jar org.javascool.core.Proglet2Html

########################################################################################################################################################

# Publie les fichiers du site web

www : clean cnv cpv $(DIST)/javascool-core.jar $(DIST)/javascool-core-api $(patsubst sketchbook/%,$(DIST)/javascool-proglet-%-html,$(wildcard sketchbook/*))
#	$(MAKE) -C $(GIT_HOME) push

# Construit les proglets

$(DIST)/javascool-proglet-%-html : sketchbook/%
	echo build $*
	java -jar $(DIST)/javascool-core.jar $@ $^

# Construit la jarre de javascool 

SRC = $(wildcard lib4/*.jar) $(shell find src4+5 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm' -o -name '*.zip')

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
	rm -rf tmp-*
	svn status
	cd $(GIT_HOME) ; git status

cleanall : clean
	rm -rf $(DIST)/*

# Test des différences entre jvs4 et jvs5

diff : 
	sh bin/diffv4v5.sh

########################################################################################################################################################
