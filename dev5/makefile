#
# @TODO :
#
# Diagnostiquer le pb des core.Proglet
# Voir a reajuster proglet.pml->.json et *.hym->*.html (peut etre commencer un core2.Proglet ?)
# Copier les fichier dans org.javascool.proglets.$$.  
# Relier javadoc au site html
#
# Degraisser jvs4 de gui2 de l editeur dans widget de AppletProut (a mettre dans jvs4 poubelle) et Jvs2Jar etc...
#
# Voir a appeler core2 build
# Recuperer le site jvs5 sous dev5
# Creer des web service pour tout ça
# Recuperer l'éditeur HTML5 de Philippe est en faire un produit 
# Et . . detruire les repertoires github de jvs5
# 

dft : clean cnv proglet

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

export JVS_HOME=/home/vthierry/Work/culsci/jvs4

clean :
	rm -rf ../work/dist/javascool-core.jar ../work/dist/javascool-proglet-*.jar

# Construction des jars

testProglet = algoDeMaths

proglet  : jar ../work/dist/javascool-proglet-$(testProglet).jar
	java -cp ../work/dist/javascool-proglet-$(testProglet).jar org.javascool.Core

proglets : jar $(patsubst sketchbook/%,../work/dist/javascool-proglet-%.jar,$(wildcard sketchbook/*))

../work/dist/javascool-proglet-%.jar : sketchbook/%
	java -cp ../work/dist/javascool-core.jar org.javascool.core2.Proglet2Jar $@ $^
	for f in $^/*.jvs ;\
	 do n=`echo $$f | sed 's/.*\/\([^\.]*\)\.jvs/\1/'` ;\
	 java -cp ../work/dist/javascool-core.jar org.javascool.core2.Jvs2Jar ../work/dist/javascool-proglet-$*-$$n.jar $* $$f ;\
	done

# Construit le jar des classes de jvs4

SRC = $(patsubst %,$(JVS_HOME)/work/lib/%.jar, javac7 RSyntaxTextArea json java2html jarsigner javadoc) $(shell find $(JVS_HOME)/work/src -name '*.java' -o -name '*.xml' -o -name '*.png' -o -name '*.htm')

jar : ../work/dist/javascool-core.jar

../work/dist/javascool-core.jar : $(SRC)
	echo "build $@"
	sh ./bin/java2jar.sh -static $@ $^

# Teste la creation d'une proglet

crt : jar
	/bin/rm -rf /tmp/bidon
	java -cp ../work/dist/javascool-core.jar org.javascool.core2.ProgletCreate /tmp/bidon
	ls -l /tmp/bidon
	cat /tmp/bidon/proglet.json

# Teste l'ouverture d'une proglet

run : ../work/dist/javascool-proglets.jar
	java -jar ../work/dist/javascool-proglets.jar $(testProglet)

../work/dist/javascool-proglets.jar : $(SRC)
	cd ../work ; ant clean ; ant javascool-proglets.jar

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	cd work ; ant javascool-builder.jar
	sh ./bin/sketchbook4to5.sh
