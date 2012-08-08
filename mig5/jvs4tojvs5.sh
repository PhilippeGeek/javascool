if [ \! \( "$#" = 2 -o \( "$1" = "-e" -a "$#" = 3 \) \) ] ; then cat <<EOF
 Usage : $0 [-e] <source-proglet-directory> <target-proglet-directory>
   Convertir les fichiers de proglets de jvs4 en jvs5
  -e : Efface la cible avant copie
EOF
exit
fi

# Copie en miroir des fichiers vers la cible

if [ "$1" = "-e" ] ; then shift ; mv $2 /tmp/$2-$$ ; fi
if [ \! -d "$1" ] ; then echo "Erreur : la location source $1 n'est pas un répertoire, bye." ; fi
if [ -f "$2" ] ; then echo "Erreur : la location cible $2 existe déjà, bye." ; fi
if [ -d ".svn" ] ; then svn export $1 $2 ; else cp -r $1 $2 ; fi
cd $2

# Translation des fichiers

java="java -cp `diname $0`/../work/dist/javascool-proglets.jar"

if [ \! -f "proglet.pml" ] ; then echo "Erreur : ce n'est pas un répertoire de proglet ou la conversion est déjà faite, bye." ; fi
$java org.javascool.tools.Pml proglet.pml proglet.json

for f in *.xml ; do n=`echo $ | sed s'/.xml$//'` ; $java org.javascool.tools.Xml2Xml $n.xml ../work/jsrc/builder/hdoc2htm.xslt $n.html ; done

