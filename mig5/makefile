#
# Migration de jvs4 vers jvs5 . . 
#

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

