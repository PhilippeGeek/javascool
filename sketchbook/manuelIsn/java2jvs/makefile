
all : clean Isn.java cmp $(patsubst ../javaIsn/%.java,../%.jvs,$(wildcard ../javaIsn/*.java))

../%.jvs : ../javaIsn/%.java
	echo jvs2java $*
	uncrustify -q -c ../../../work/lib/uncrustify.cfg --replace $^
	java -cp ../../../work/dist/javascool-proglets.jar:. Java2Jvs $^ $@
	java -cp ../../../work/dist/javascool-proglets.jar org.javascool.core.Jvs2Java org.javascool.proglets.manuelIsn $@ JvsToJavaTranslated1.java
	javac -cp ../../../work/dist/javascool-proglets.jar JvsToJavaTranslated1.java

cmp :
	javac -cp ../../../work/dist/javascool-proglets.jar Java2Jvs.java

Isn.java : ../javaIsn/Isn.java
	cp $^ $@
	uncrustify -q -c ../../../work/lib/uncrustify.cfg --replace $@

clean :
	rm -rf *.class JvsToJavaTranslated1.java ../*.jvs

