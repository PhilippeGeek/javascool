#
# Makefile de développement de jvs4.alpha.2 à vthierry (à terme seul ant sera utilisé)
#
JVS = $(realpath ..)

ant :
#	Lance ant sur son build.xml
	ant run
#	ant javadoc #; firefox $(JVS)/tags/build/web/api/index.html 
#	java -cp $(JVS)/tags/javascool.jar:lib/jvs2html.jar:lib/lang3.jar org.javascool.tools.Jvs2Html tools/Jvs2Html.java

#
# Commandes de service pour gerer le site javascool
#

JVS = $(realpath ..)

pwd :
	nautilus $(JVS)

ssh :
	@gnome-terminal -e "ssh scm.gforge.inria.fr 'cd /home/groups/javascool/htdocs ; bash -i'" 

svn : 
	ant clean > /dev/null
	cd $(JVS) ; svn cleanup ; svn up ; svn ci -m 'modif mineure vthierry' ; svn status

pub :
	svn up .. ; cd ../web ; ./start.sh -show #; ./publis.sh

bty :
	for f in `find src -name '*.java' -o -name '*.jvs'` ; do uncrustify -q -c lib/uncrustify.cfg --replace $$f ; done


P_HOME=/usr/java/processing-1.2.1
J_HOME=$(P_HOME)/java
export PATH:=$(J_HOME)/bin:$(PATH)
export CLASSPATH=$(shell echo '$(J_HOME)/lib/tools.jar $(J_HOME)/jre/lib/rt.jar $(wildcard $(P_HOME)/lib/*.jar)' | sed 's/ /:/g')

pro : 
	@rm -rf libraries ../sketchbook/*/applet ../sketchbook/*/Exemple.java
	@cd $(P_HOME) ; java processing.app.Base
