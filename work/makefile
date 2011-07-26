dft : cmp

export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)
export CP = $(PWD)/../javascool/lib/RSyntaxTextArea.jar:$(PWD)/../javascool/lib/tools.zip

TMP = $(PWD)/tmp
SRC = $(PWD)/../javascool/src

web :
	svn up .. ; cd ../web ; ./start.sh -show

doc :
	rm -rf tmp/jdoc ; 	mkdir -p tmp/jdoc
	cd $(SRC) ; javadoc -classpath $(CP) -quiet -nohelp -nodeprecated -nonavbar -nosince -notree -noindex -author -use -charset "UTF-8" -windowtitle "Java's Cool v4" -public -link http://download.oracle.com/javase/6/docs/api -link http://javadoc.fifesoft.com/rsyntaxtextarea/ -d $(TMP)/jdoc `find . -name '*.java'` 
	firefox $(TMP)/jdoc/index.html

cmp :
	cd javascool ; javac -Xlint -cp $(CP) `find $(SRC) -name '*.java'`
	echo 'Main-Class: org.javascool.JVSMain' > $(TMP)/manifest.mf
	cd $(SRC) ; jar cfm $(TMP)/javascool.jar $(TMP)/manifest.mf `find . -name '*.class'` ; find -name '*.class' -exec rm {} \;
	java -cp $(CP) -jar $(TMP)/javascool.jar

clean:
	cd $(PWD)/.. ; find -name '*.class' -exec rm {} \;
	rm -rf tmp/*
	svn status $(PWD)/..

