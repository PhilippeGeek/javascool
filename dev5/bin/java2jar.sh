
#
# Effectue la compilation de sources java dans un jar
#

# Analyse de la ligne de commande
cp="."
for f in $* ; do e="`echo $f | sed 's/.*\.//'`"
 case "$f" in 
  -static ) static=1;;
  * ) 
    if [ -z "$jar" ] ; then 
      if [ "$e" = "jar" ] ; then jar="`pwd`/$f" ; else echo "Invalid extension for the target jar-file $f" ; exit -1; fi
    else 
      if [ \! -f "$f" ] ; then echo "File not found $f"; exit -1 ; fi
       case "$e" in
         java ) src="$src $f";;
         jar )  cp="$cp:$f" ; jrc="$jrc $f";;
         * )    res="$res $f";;
        esac
    fi;;
  esac
done

# Usage 
if [ -z "$jar" -o -a "$src" ] ; then cat <<EOF
Usage : $0 [-static] <jar-file> <java-files|jar-files> 
 Effectue la compilation dans une jarre de sources java complétés de jar externes
EOF
exit -1
fi

# Répertoire de travail
d="lib-$$" ; /bin/rm -rf $d ; mkdir $d

# Inclut les sources et fichiers de ressource
for f in $res $src ; do t=$d/`echo $f | sed 's/.*\/src\///'`; mkdir -p `dirname $t` ; cp $f $t ; done

# Inclut les jarres du classpath en mode static
if [ "$static" = 1 ] ; then for j in $jrc ; do unzip -d $d -oq $j ; done ; fi

# Compilation
javac -d $d -cp $cp $src
rm -f $jar ; pushd $d > /dev/null ; jar cfM $jar . ; popd > /dev/null

/bin/rm -rf $d


