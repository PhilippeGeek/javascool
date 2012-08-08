if [ \! \( "$#" = 2 -o \( "$1" = "-e" -a "$#" = 3 \) \) ] ; then cat <<EOF
 Usage : $0 [-e] <source-proglet-directory> <target-proglet-directory>
   Convertir les fichiers de proglets de jvs4 en jvs5
  -e : Efface la cible avant copie
EOF
exit
fi

# Calcul des path

pushd `dirname $0` > /dev/null 
 jvs="`pwd`/../work/dist/javascool-builder.jar" ; jsx="`pwd`/../work/lib/saxon.jar" ; xslt="`pwd`/../work/jsrc/builder/hdoc2htm.xslt" 
popd > /dev/null

# Copie en miroir des fichiers vers la cible

if [ "$1" = "-e" ] ; then shift ; if [ -d "$2" ] ; then mv $2 /tmp/`basename $2`-$$ ; fi ; fi
if [ \! -d "$1" ] ; then echo "Erreur : la location source $1 n'est pas un répertoire, bye." ; exit -1 ; fi
if [ -d "$2" ] ; then echo "Erreur : la location cible $2 existe déjà, bye." ; exit -1 ; fi
if [ -d ".svn" ] ; then svn -q export $1 $2 ; else cp -r $1 $2 ; fi
cd $2

# Translation des fichiers

if [ -f proglet.pml ] ; then java -cp $jvs org.javascool.tools.Pml proglet.pml proglet.json ; rm proglet.pml ; else echo "Erreur : y'a pas de proglet.pml !" ; fi

if [ -f completion.xml ] ; then java -cp $jvs org.javascool.tools.Pml completion.xml completion.json ; rm completion.xml ; fi

for f in *.xml ; do n=`echo $f | sed s'/.xml$//'` ; java -jar $jsx -o $n.html $n.xml $xslt ; rm $f ; done

# That's all !
