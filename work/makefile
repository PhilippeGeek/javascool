#
# Makefile d'utilisaires développement de jvs4 (à terme seul ant sera utilisé)
#
JVS = $(realpath ..)

ant :
#	Lance ant sur son build.xml
	ant run
	firefox ../tags/build/web/api/index.html 

#
# Commandes de service pour gerer le site javascool
#

JVS = $(realpath ..)

ssh :
	@gnome-terminal -e "ssh scm.gforge.inria.fr 'cd /home/groups/javascool/htdocs ; bash -i'" 

svn : 
	rm -rf ../tags/build
	cd $(JVS) ; svn cleanup ; svn up ; svn ci -m 'modif mineure vthierry' ; svn status

docweb :
	svn up .. ; cd ../web ; ./start.sh -show #; ./publis.sh

docant :
	firefox file:///home/vthierry/doc/soft/ant/index.html

docjvs: 
	http://javascool.gforge.inria.fr/v3/?page=api:org/javascool/package-summary.html
