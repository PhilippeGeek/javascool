########################################################################################################################################################
#
# @TODO :
#
# Voir userconfig . factoriser applet
# Faire un document qui décrit ce qui a été allégé de jvs4 à jvs4.9 (json, plus de demo, plus de xml, ..)
# Voir le pb du javaStart sous windows 
#
# Valider les Jvs2Jar en creant les jar des jvs mettant des (@run dans les liens le jvs.html -> jvs.jar)
# Créer les WebServices : proglet2zip jvs2jar ; voir l'idée d'ajouter un fichier la jarre
#
# Valider le site javascool-5 avec juste les icones des proglets (generation via makefile) et un lokk bootsrap et menu minima
# Voir comment se gère les proglets processing et bien valider les liens vers le site fuscia
# Valider le make www de ici et le git (avec le wget de http://javascool.gforge.inria.fr/v5 dans javascool.github.com/wproglets)
#
# - Mettre Build.java dans builder et About.java + Core.java dans gui et créer un javascool/core/Utils.java ; et mettre  jour build.xml
# - Ajouter json à Pml en entrée/sortie.
# - Recuperer l'éditeur HTML5 de Philippe est en faire un produit autonome qui se connecte aux web services
# - Voir jar jbed jad http://www.developpez.net/forums/d922916/java/general-java/java-mobiles/android/android-jar/
#
########################################################################################################################################################

# Test en cours 

dft : tst2

tst2 : clean cpv dist/javascool-core.jar
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Html

tstP = algoDeMaths

tst1  : clean cpv dist/javascool-core.jar dist/javascool-proglet-$(tstP)-html
#	firefox dist/javascool-proglet-$(tstP)-html/index.html
#	java -cp dist/javascool-proglet-$(tstP)-html/javascool-proglet-$(tstP).jar org.javascool.Core

########################################################################################################################################################

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Publie les fichiers du site web

www : clean cnv cpv dist/javascool-core.jar dist/javascool-core-api $(patsubst sketchbook/%,dist/javascool-proglet-%-html,$(wildcard sketchbook/*))
	rsync --rsh='ssh -C' -rl dist/* scm.gforge.inria.fr:/home/groups/javascool/htdocs/v5

dist/javascool-proglet-%-html : sketchbook/%
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Html $@ $^

# Publie les fichiers du site github

GIT_HOME=/home/vthierry/Work/culsci/old/jvs5/javascool.github.com

git :
	rsync -rl doc5/* $(GIT_HOME)/doc
	cd $(GIT_HOME) ; git status
#	$(MAKE) -C $(GIT_HOME) push

# Construit la jarre de javascool 

SRC = $(wildcard lib4/*.jar) $(shell find src4+5 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm' -o -name '*.zip')

dist/javascool-core.jar : $(SRC)
	sh ./bin/java2jar.sh -static $@ $^

dist/javascool-core-api : $(SRC)
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Html javascool-core src4+5 $@

# Effectue la copie des src/lib de jvs4 vers jvs5

cpv :
	sh ./bin/srclib4to5.sh

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh

# Nettoie les répertoires générés

clean :
	rm -rf dist/* lib-*
	svn status

# Test des différences entre jvs4 et jvs5

diff : 
	sh bin/diffv4v5.sh > dist/diffv4v5.txt

########################################################################################################################################################
