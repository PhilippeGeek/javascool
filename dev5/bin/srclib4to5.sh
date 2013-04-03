
#
# Effectue la copie des source jvs4 pour leur utilisation dans jvs5
#   ce script est un mecanisme temporaire et local à l'environnement de vthierry, il n'a pas vocation à être utilisé après le basculement v4->v5
#

# Positionnement à la racine du svn
cd /home/vthierry/Work/culsci/jvs4

# Copy des libs nécessaires
for f in javac7 RSyntaxTextArea json java2html javadoc7
do make -f - <<EOF
dev5/lib4/$f.jar : work/lib/$f.jar
	cp \$^ \$@
EOF
done
echo "CE REPERTOIRE MIROIRE LES LIB DE JVS4 : NE PAS TOUCHER" > dev5/lib4/README.TXT

# Copy des srcs nécessaires
for f in org/javascool/{{Core,About},{core,gui,macros,tools,widgets}/*}.java
do make -f - <<EOF
dev5/src4/$f : work/src/$f
	mkdir -p \$(@D)
	cp \$^ \$(@D)
EOF
done
echo "CE REPERTOIRE MIROIRE LES SRC DE JVS4 : NE PAS TOUCHER" > dev5/src4/README.TXT


