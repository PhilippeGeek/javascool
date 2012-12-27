
#
# Effectue la convertion des proglets jvs4 en jvs5
#

# Positionnement à la racine du svn
cd `dirname $0`/../..

# Définit le répertoire source et cible et les proglets à convertir

srcDir="`pwd`/sketchbook"
dstDir="`pwd`/dev5/sketchbook"
srcProglets="`find $srcDir -mindepth 1 -maxdepth 1 -type d -exec basename {} \; | egrep -v 'jeux2D|sampleCode'`"

echo "Conversion de «" $srcProglets "» de '$srcDir' à '$dstDir'"

# Référence les librairies à utiliser
jvs="`pwd`/work/dist/javascool-builder.jar" 
jsx="`pwd`/work/lib/saxon.jar" 
xslt="`pwd`/work/jsrc/builder/hdoc2htm.xslt" 

# Boucle sur les proglets

for p in $srcProglets
do s="$srcDir/$p" ; d="$dstDir/$p"

  # Copie en miroir des fichiers
  /bin/rm -rf $d ;  svn -q export --force $s $d
  
  # Translation des fichiers de ressource
  pushd $d
  if [ -f proglet.pml ] ; then java -cp $jvs org.javascool.tools.Pml proglet.pml proglet.json ; rm proglet.pml ; else echo "Erreur : y'a pas de proglet.pml !" ; fi
  if [ -f completion.xml ] ; then java -cp $jvs org.javascool.tools.Pml completion.xml completion.json ; rm completion.xml ; fi
  # Translation des fichiers *.html
  for f in *.xml ; do n=`echo $f | sed s'/.xml$//'` ; java -jar $jsx -o $n.html $n.xml $xslt ; rm $f ; done
    for f in *.html ; do cp $f $f~ ; sed s'/.htm"/.html"/g' < $f~ > $f ; done
    rm -f *~
  popd > /dev/null

  # Listing du resultat
  ls -l $d

done
