#
# regler les warnings 
## : [static] static method should be qualified by type name, 
## : [static] static variable should be qualified by type name,
## : [unchecked] unchecked call to JComboBox(E[]) as a member of the raw type JComboBox


# Choix de la JDK

#export JAVA_HOME=/usr/java/jdk1.7.0_07
export JAVA_HOME=/usr/java/jdk1.6.0_25
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

export JVS_HOME=/home/vthierry/Work/culsci/jvs4

dft : run

# Ouvre les doc de jvs4

doc :
	cd ../work ; ant javadoc ; firefox ./dist/javadoc/overview-summary.html

# Construction des jars

proglets : jar $(patsubst sketchbook/%,../work/dist/javascool-proglet-%.jar,$(wildcard sketchbook/*))

../work/dist/javascool-proglet-%.jar : sketchbook/%
	java -cp ../work/dist/javascool-core.jar org.javascool.core2.Proglet2Jar $@ $^
	for f in $^/*.jvs ;\
	 do n=`echo $$f | sed 's/.*\/\([^\.]*\)\.jvs/\1/'` ;\
	 java -cp ../work/dist/javascool-core.jar org.javascool.core2.Jvs2Jar ../work/dist/javascool-proglet-$*-$$n.jar $* $$f ;\
	done

# Construit le jar des classes de jvs4

SRC = $(patsubst %,$(JVS_HOME)/work/lib/%.jar, javac7 RSyntaxTextArea java2html jarsigner javadoc) $(shell find $(JVS_HOME)/work/src -name '*.java')

jar : ../work/dist/javascool-core.jar

../work/dist/javascool-core.jar : $(SRC)
	sh ./bin/java2jar.sh -static $@ $^
	java -cp ../work/dist/javascool-core.jar org.javascool.Core

# Teste l'ouverture d'une proglet

run : ../work/dist/javascool-proglets.jar
	java -jar ../work/dist/javascool-proglets.jar abcdAlgos

../work/dist/javascool-proglets.jar : $(SRC)
	cd ../work ; ant javascool-proglets.jar

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh
