########################################################################################################################################################
#
# @TODO :
#
# Voir le pb du javaStart sous windows 
#
# Valider les Jvs2Jar en creant les jar des jvs mettant des (@run dans les liens le jvs.html -> jvs.jar)
# Créer les WebServices : proglet2zip jvs2jar ; voir l'idée d'ajouter un fichier la jarre
#
# Valider le site javascool-5 avec juste les icones des proglets (generation via makefile) et un lokk bootsrap et menu minima
# Voir comment se gère les proglets processing et bien valider les liens vers le site fuscia
# Valider le make www de ici et le git (avec le wget de http://javascool.gforge.inria.fr/v5 dans javascool.github.com/wproglets)
# http://javascool.github.com/wpages/JavaScool:Ressources.html

# - Mettre Build.java dans builder et About.java + Core.java dans gui et créer un javascool/core/Utils.java ; et mettre  jour build.xml
# - Copier dans javascool-core les classes images, socket et son
# - Ajouter json à Pml en entrée/sortie.
# - Recuperer l'éditeur HTML5 de Philippe est en faire un produit autonome qui se connecte aux web services
# - Voir au déploiement par jnlp des jars utilisés
# - Voir jar jbed jad http://www.developpez.net/forums/d922916/java/general-java/java-mobiles/android/android-jar
# - Noter virer les classes redondantes dans les proglets cet été
#
########################################################################################################################################################

# Test en cours 

dft : tst1

tst2 : clean cpv dist/javascool-core.jar
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Html

tstP = algoDeMaths

tst1  : clean cpv dist/javascool-core.jar dist/javascool-proglet-$(tstP)-html
#	firefox dist/javascool-proglet-$(tstP)-html/index.html
	java -jar dist/javascool-proglet-$(tstP)-html/javascool-proglet-$(tstP).jar
#	java -jar dist/javascool-proglet-algoDeMaths-html/JeuSimon.jvs.jar 

########################################################################################################################################################

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Installation du web github

GIT_HOME=./javascool.github.com

DIST=$(GIT_HOME)/wproglets

# Publie les fichiers du site web

www : clean cnv cpv $(DIST)/javascool-core.jar $(DIST)/javascool-core-api $(patsubst sketchbook/%,$(DIST)/javascool-proglet-%-html,$(wildcard sketchbook/*))
	cd $(GIT_HOME) ; git status
#	$(MAKE) -C $(GIT_HOME) push

# Construit les proglets

$(DIST)/javascool-proglet-%-html : sketchbook/%
	echo java -cp $(DIST)/javascool-core.jar org.javascool.core.Proglet2Html $@ $^

# Construit la jarre de javascool 

SRC = $(wildcard lib4/*.jar) $(shell find src4+5 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm' -o -name '*.zip')

$(DIST)/javascool-core.jar : $(SRC)
	sh ./bin/java2jar.sh -static -main=org.javascool.core.Proglet2Html $@ $^

$(DIST)/javascool-core-api : $(SRC)
	java -jar $(DIST)/javascool-core.jar javascool-core src4+5 $@

# Effectue la copie des src/lib de jvs4 vers jvs5

cpv :
	sh ./bin/srclib4to5.sh

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh

# Nettoie les répertoires générés

clean :
	rm -rf lib-*
	svn status

# Test des différences entre jvs4 et jvs5

diff : 
	sh bin/diffv4v5.sh

########################################################################################################################################################
