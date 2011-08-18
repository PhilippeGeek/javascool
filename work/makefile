# Used to use sun sun-jdk instead of open-jdk because of processing and windows platforms
export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)

#
# Makefile de développement de jvs4.alpha.2 à vthierry (à terme seul ant sera utilisé)
#
JVS = $(realpath ..)

ant :
	ant jrun
#	ant javadoc ; firefox $(JVS)/build/web/api/index.html 

nbe :
	@gnome-terminal --hide-menubar --geometry 1x1-1-1 -e "/usr/java/netbeans-7.0/bin/netbeans" &

clean :
	ant clean > /dev/null

#
# ./build
#

svn : clean
	cd $(JVS) ; svn cleanup ; svn up ; svn ci -m 'modif mineure vthierry' ; svn status

#
# web/build
#

ssh :
	@gnome-terminal -e "ssh scm.gforge.inria.fr 'cd /home/groups/javascool/htdocs ; bash -i'" &

lcheck :
	ant javascool.jar
	java -cp ../build/javascool.jar org.javascool.tools.LinkCheck -recursive http://javascool.gforge.inria.fr/v4/

pub :
	svn up ; cd ../web ; ./index/start.sh #; ./publis.sh
	firefox http://127.0.0.1:1234

#
# work/build
#

P_HOME=/usr/java/processing-1.2.1
J_HOME=$(P_HOME)/java
export PATH:=$(J_HOME)/bin:$(PATH)
export CLASSPATH=$(shell echo '$(J_HOME)/lib/tools.jar $(J_HOME)/jre/lib/rt.jar $(wildcard $(P_HOME)/lib/*.jar)' | sed 's/ /:/g')

pro : 
	@cd $(P_HOME) ; java processing.app.Base
