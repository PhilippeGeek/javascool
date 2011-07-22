
if [ -z "$1" ] 
then
  echo "ok à titre de demo voici sketchbook/tortueLogo/Proglet.php"
  $0 sketchbook/tortueLogo/Proglet.pml sketchbook/tortueLogo/Proglet.php
  cat sketchbook/tortueLogo/Proglet.php
else
  J=../javascool/src/org/javascool
  javac $J/pml/Pml.java  $J/tools/Utils.java 
  java -cp ../javascool/src org.javascool.pml.Pml $1 $2
fi

