#
# @TODO :
# Valider Proglet2Html
# Créer site javascool-5 avec juste les icones des proglets (generation via makefile) et un lokk bootsrap et menu minima;
# Créer les WebServices : proglet2zip jvs2jar ; voir l'idée de lancer avec des paramètres dans le manifeste, sinon compiler un wrapper ?
# Voir a reajuster proglet.pml->.json et *.hym->*.html
# Changer /home/vthierry/Work/culsci/jvs4/dev5/dist/javascool-core.jar
# Relier javadoc au site html
# Recuperer le site jvs5 sous dev5
# Creer des web service pour tout ça
# Recuperer l'éditeur HTML5 de Philippe est en faire un produit 
# Et . . detruire les repertoires github de jvs5 ET
# Virer le jvs3 sur le site
# 
#
# - Reduire ./widgets/PanelApplet.java ./tools/Invoke.java ./core/ProgletEngine.java
# - Ajouter json à Pml en entrée/sortie.
#

# Test en cours 

dft : tst

tstP = algoDeMaths

tst  : clean jar dist/javascool-proglet-$(tstP)-html
#	firefox dist/javascool-proglet-$(tstP)-html
#	java -cp dist/javascool-proglet-$(tstP)-html/javascool-proglet-$(tstP).jar org.javascool.Core

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

export JVS_HOME=/home/vthierry/Work/culsci/jvs4

# Construction des jars

proglets : jar $(patsubst sketchbook/%,dist/javascool-proglet-%-html,$(wildcard sketchbook/*))

dist/javascool-proglet-%-html : sketchbook/%
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Html $@ $^

dist/javascool-proglet-%.jar : sketchbook/%
	java -cp dist/javascool-core.jar org.javascool.core.Proglet2Jar $@ $^
	for f in $^/*.jvs ;\
	 do n=`echo $$f | sed 's/.*\/\([^\.]*\)\.jvs/\1/'` ;\
	 java -cp dist/javascool-core.jar org.javascool.core.Jvs2Jar dist/javascool-proglet-$*-$$n.jar $* $$f ;\
	done

# Construit la jarre de javascool 

jar : cpv dist/javascool-core.jar 

dist/javascool-core.jar : $(wildcard lib4/*.jar) $(shell find src4 -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm') $(shell find src5 -name '*.java' -o -name '*.zip' )
	sh ./bin/java2jar.sh -static $@ $^

# Effectue la copie des src/lib de jvs4 vers jvs5

cpv :
	sh ./bin/srclib4to5.sh

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh

# Nettoie les répertoires générés

clean :
	rm -rf dist/* lib-*

