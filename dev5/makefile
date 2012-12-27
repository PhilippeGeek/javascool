
dft :
	rm -f lib/javascool-all.jar
	$(MAKE) cmp

# Choix de la JDK

export JAVA_HOME=/usr/java/jdk1.7.0_07
export PATH:="$(JAVA_HOME)/bin:$(PATH)"

# Compile les proglets en html/jar

cmp : lib/javascool-all.jar
	cd sketchbook ; for p in * ; do sh ../bin/proglet2htmjar.sh $$p ../lib/$$p ; done

lib/javascool-all.jar : $(shell ls ../work/lib/{javac,jarsigner,jvs2html,javadoc,RSyntaxTextArea,autocomplete}.jar ../work/src/org/javascool/{Core,Build,About}.java ../work/src/org/javascool/{builder,core,gui,macros,tools,widgets}/*.java)
	sh ./bin/java2jar.sh $@ $^

# Effectue la conversion des proglets jvs4 en jvs5

cnv :
	sh ./bin/sketchbook4to5.sh

# Cree les zip des sources jvs5 

zip :
	cd sketchbook ; for p in * ; do z="../lib/javascool-$$p.zip" ; rm -f $$z ; zip -9qr $$z $$p ; done
