
ISN_SOURCES = /home/vthierry/Desktop/Isn

all : clean cmp $(patsubst $(ISN_SOURCES)/%.java,../%.jvs,$(wildcard $(ISN_SOURCES)/*.java))

../%.jvs : $(ISN_SOURCES)/%.java
	echo jvs2java $*
	uncrustify -q -c ../../../work/lib/uncrustify.cfg --replace $^
	java -cp ../../../work/dist/javascool-proglets.jar:. org.javascool.core.Java2Jvs $^ $@
	java -cp ../../../work/dist/javascool-proglets.jar org.javascool.core.Jvs2Java org.javascool.proglets.manuelIsn $@ JvsToJavaTranslated1.java
	javac -cp ../../../work/dist/javascool-proglets.jar JvsToJavaTranslated1.java

cmp :
	javac -cp ../../../work/dist/javascool-proglets.jar Java2Jvs.java

clean :
	rm -rf *.class JvsToJavaTranslated1.java

