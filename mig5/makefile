#
# Migration de jvs4 vers jvs5 . . 
#
# Todo :
#  - travailler la conversion des liens vers http://javascool.gforge.inria.fr ou des liens de types ../<auterproglet>
#  - définir les cdc des proglets
#  - créer un compilateur de proglet (java -> class + javadoc) d'abord local
# A voir avec Philou :
#  - Si ok le format de completion.json \ completion.xml
#  - La mise en place des javadoc et jsdoc
#  - Ajoute t'on la liste explicite des images a mettre dans le jar dans le proglet.json pour ne pas y coller les images du site ?
#  - Cree t'on des repertoires locaux avec dll et am64 et i383 pour stcoker les libs ?
#  - Genere t'on les jars etc // dans un sous-repertoire applet comme pour processing pour avoir la meme structure ?
#  - Le web service du builder pourrait'il prendre un line github et rendre une page d'erreur ou le zip du sous-repertoire applet ?

# Basculement des proglets vers jvs5

dproglets = $(patsubst ../sketchbook/%,%,$(wildcard ../sketchbook/*))

proglets : 
	for p in $(dproglets) ; do echo "j2to5 $$p" ; ./jvs4tojvs5.sh -e ../sketchbook/$$p ../../jvs5/proglet-$$p ; done

# Report des modifs de jvs4 vers jvs5 

jfiles = core/Jvs2Java

jsources : $(patsubst %,../../jvs5/javascool-framework/src/org/javascool/%.java,$(jfiles))

../../jvs5/javascool-framework/src/org/javascool/%.java : ../work/src/org/javascool/%.java
	echo "jvs4->jvs5 $*"
	cd ../../jvs5/javascool-framework ; git pull -q
	diff $^ $@
	cp $^ $@
	cd ../../jvs5/javascool-framework ; git commit -a -m "maj de jvs4 vers jvs5 d'un source" ; git push

