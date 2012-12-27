
#
# Effectue la compilation des proglets en html/jar
#

# Usage
if [ \( "$#" \!= "2" \) -o \( \! -d "$1" \) ] ; then cat <<EOF
Usage : $0 <proglet-directory> <target-directory> 
 Effectue la compilation des proglets en html/jar
EOF
exit -1
fi

# Mise en place du répertoire cible
/bin/rm -rf $2 ; mkdir -p $2

# 
