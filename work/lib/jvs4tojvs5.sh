#
# Note
#  Ce script n'est plus  jour
#
exit

echo 'Convertir les fichiers de proglets de jvs4 en jvs5'

cd `dirname $0`/../../
jvs="`pwd`/work/dist/javascool-builder.jar" ; jsx="`pwd`/work/lib/saxon.jar" ; xslt="`pwd`/work/jsrc/builder/hdoc2htm.xslt" 

for p4 in sketchbook/* ; do p="`echo $p4 | sed 's/sketchbook\///'`" ; if [ "$p" != package.txt -a "$p" != sampleCode ] ; then p5="../jvs5/proglet-$p" 

  echo "$p : $p4 -> /tmp/proglet-$p -> $p5"

  # Copie en miroir des fichiers vers le répertoire temporaire
  if [ \! -d "$p4" ] ; then echo "Erreur : la location source $p4 n'est pas un répertoire, bye." ; exit -1 ; fi
  /bin/rm -rf /tmp/proglet-$p ; mkdir -p /tmp/proglet-$p
  if [ -d ".svn" ] ; then svn -q export --force $p4 /tmp/proglet-$p/ ; else cp -r --force $p4 /tmp/proglet-$p ; fi

  # Translation des fichiers
  pushd /tmp/proglet-$p > /dev/null
  if [ -f proglet.pml ] ; then java -cp $jvs org.javascool.tools.Pml proglet.pml proglet.json ; rm proglet.pml ; else echo "Erreur : y'a pas de proglet.pml !" ; fi
  if [ -f completion.xml ] ; then java -cp $jvs org.javascool.tools.Pml completion.xml completion.json ; rm completion.xml ; fi
  for f in *.xml ; do n=`echo $f | sed s'/.xml$//'` ; java -jar $jsx -o $n.html $n.xml $xslt ; rm $f ; done
  for f in *.html ; do cp $f $f~ ; sed s'/.htm"/.html"/g' < $f~ > $f ; done
  popd > /dev/null

  # Transfert dans les proglets 
  cp /tmp/proglet-$p/*.html /tmp/proglet-$p/*.jvs $p5
  pushd $p5  > /dev/null ; git status ; popd > /dev/null
fi done

# That's all !
