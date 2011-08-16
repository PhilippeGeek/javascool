# Used to use sun sun-jdk instead of open-jdk because of processing and windows platforms
export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)

#
# Makefile de développement de jvs4.alpha.2 à vthierry (à terme seul ant sera utilisé)
#
JVS = $(realpath ..)

ant :
#	Lance ant sur son build.xml
#	ant run
	ant brun
#	ant javadoc ; firefox $(JVS)/build/web/api/index.html 

#
# Commandes de service pour gerer le site javascool
#

JVS = $(realpath ..)

pwd :
	nautilus $(JVS)

zip :
	svn export . ~/Desktop/work
	svn export ../sketchbook ~/Desktop/sketchbook

ssh :
	@gnome-terminal -e "ssh scm.gforge.inria.fr 'cd /home/groups/javascool/htdocs ; bash -i'" 

svn : 
	ant clean > /dev/null
	cd $(JVS) ; svn cleanup ; svn up ; svn ci -m 'modif mineure vthierry' ; svn status

pub :
	svn up ; cd ../web ; ./index/start.sh #; ./publis.sh
	firefox http://127.0.0.1:1234

bty :
	for f in `find src -name '*.java' -o -name '*.jvs'` ; do uncrustify -q -c lib/uncrustify.cfg --replace $$f ; done
	for f in `find ../sketchbook -name '*.java' -o -name '*.jvs'` ; do uncrustify -q -c lib/uncrustify.cfg --replace $$f ; done


P_HOME=/usr/java/processing-1.2.1
J_HOME=$(P_HOME)/java
export PATH:=$(J_HOME)/bin:$(PATH)
export CLASSPATH=$(shell echo '$(J_HOME)/lib/tools.jar $(J_HOME)/jre/lib/rt.jar $(wildcard $(P_HOME)/lib/*.jar)' | sed 's/ /:/g')

pro : 
#	@rm -rf libraries ../sketchbook/*/applet ../sketchbook/*/Exemple.java
	@cd $(P_HOME) ; java processing.app.Base
