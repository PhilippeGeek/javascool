dft : cmp2

export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)

JVS=/user/vthierry/home/Work/culsci/jvs
export CP1 = $(JVS)/javascool/lib/RSyntaxTextArea.jar:$(JVS)/javascool/lib/tools.zip
export CP2 = $(JVS)/javascoolbuilder/lib/ant/ant.jar:$(JVS)/javascoolbuilder/lib/lang3.jar:$(JVS)/javascoolbuilder/lib/tools.jar

TMP = $(PWD)/tmp
SRC1 = $(JVS)/javascool/src
SRC2 = $(JVS)/javascoolbuilder/src

svn : 
	cd .. ; svn cleanup ; svn up ; svn ci -m 'modif mineure vthierry' ; svn status

web :
	svn up .. ; cd ../web ; ./start.sh -show ; ./publis.sh

doc1 :
	rm -rf tmp/jdoc ; 	mkdir -p tmp/jdoc
	cd $(SRC1) ; javadoc -classpath $(CP1) -quiet -nohelp -nodeprecated -nonavbar -nosince -notree -noindex -author -use -charset "UTF-8" -windowtitle "Java's Cool v4" -public -link http://download.oracle.com/javase/6/docs/api -link http://javadoc.fifesoft.com/rsyntaxtextarea/ -d $(TMP)/jdoc `find . -name '*.java'` 
	firefox $(TMP)/jdoc/index.html

cmp1 :
	cd javascool ; javac -Xlint -classpath $(CP1) `find $(SRC1) -name '*.java'`
	echo 'Main-Class: org.javascool.JVSMain' > $(TMP)/manifest.mf
	cd $(SRC1) ; jar cfm $(TMP)/javascool.jar $(TMP)/manifest.mf `find . -name '*.class'` ; find -name '*.class' -exec rm {} \;
	java -classpath $(CP1) -jar $(TMP)/javascool.jar

doc2 :
	rm -rf tmp/jdoc ; 	mkdir -p tmp/jdoc
	cd $(SRC2) ; javadoc -classpath $(CP2) -quiet -nohelp -nodeprecated -nonavbar -nosince -notree -noindex -author -use -charset "UTF-8" -windowtitle "Java's Cool v4" -public -link http://download.oracle.com/javase/6/docs/api -link http://javadoc.fifesoft.com/rsyntaxtextarea/ -d $(TMP)/jdoc `find . -name '*.java'` 
	firefox $(TMP)/jdoc/index.html

cmp2 :
	cd javascoolbuilder ; javac -Xlint -classpath $(CP2) `find $(SRC2) -name '*.java'`
	echo 'Main-Class: org.javascool.builder.JVSBMain' > $(TMP)/manifest.mf
	cd $(SRC2) ; jar cfm $(TMP)/javascoolbuilder.jar $(TMP)/manifest.mf `find . -name '*.class'` ; find -name '*.class' -exec rm {} \;
	java -classpath $(CP2) -jar $(TMP)/javascoolbuilder.jar

ssh :
	@gnome-terminal -e "ssh scm.gforge.inria.fr 'cd /home/groups/javascool/htdocs ; bash -i'" 

clean:
	cd $(JVS) ; find -name '*.class' -exec rm {} \;
	rm -rf tmp/*
	svn status $(JVS)

