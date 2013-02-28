
# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

export JVS_HOME=/home/vthierry/Work/culsci/jvs4

# Ouvre les doc de jvs4

doc :
	firefox http://javascool.gforge.inria.fr/index.php?page=api

# Construit le jar des classes de jvs4

bin/javascool-core.jar : $(patsubst %,$(JVS_HOME)/work/lib/%.jar, javac7 RSyntaxTextArea java2html jarsigner javadoc) $(shell find $(JVS_HOME)/work/src -name '*.java')
	sh ./bin/java2jar.sh -static $@ $^

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh
