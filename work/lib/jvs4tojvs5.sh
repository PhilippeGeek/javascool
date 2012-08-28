#
# To do liste:
#   Faire un diff avant de propager les source java
#   Les liens pointent vers des fichiers htm alors qu'ils sont tous en html.
#   Les fichiers sont enrobé de bootstrap, cela est inutile et impose un lourd calcule pour retrouver le vrai contenu de la page à afficher dans le WebPage.
#

echo 'Convertir les fichiers de proglets de jvs4 en jvs5'

cd `dirname $0`/../../
jvs="`pwd`/work/dist/javascool-builder.jar" ; jsx="`pwd`/work/lib/saxon.jar" ; xslt="`pwd`/work/jsrc/builder/hdoc2htm.xslt" 

for p4 in sketchbook/* ; do p="`echo $p4 | sed 's/sketchbook\///'`" ; if [ "$p" != package.txt -a "$p" != sampleCode ] ; then p5="../jvs5/proglet-$p" 

  # Copie en miroir des fichiers vers la cible
  if [ \! -d "$p4" ] ; then echo "Erreur : la location source $p4 n'est pas un répertoire, bye." ; exit -1 ; fi
  if [ \! -d "$p5" ] ; then mkdir -p $p5 ; fi
  if [ -d ".svn" ] ; then svn -q export --force $p4 $p5/ ; else cp -r --force $p4 $p5 ; fi

  echo "$p : $p4 -> $p5"

  # Translation des fichiers
  pushd $p5 > /dev/null
  if [ -f proglet.pml ] ; then java -cp $jvs org.javascool.tools.Pml proglet.pml proglet.json ; rm proglet.pml ; else echo "Erreur : y'a pas de proglet.pml !" ; fi
  if [ -f completion.xml ] ; then java -cp $jvs org.javascool.tools.Pml completion.xml completion.json ; rm completion.xml ; fi
  for f in *.xml ; do n=`echo $f | sed s'/.xml$//'` ; java -jar $jsx -o $n.html $n.xml $xslt ; rm $f ; done
  popd > /dev/null
fi done

# That's all !
