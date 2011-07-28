for proglet in `ls -p|grep "/"|sed 's/\/$//'`
do
	echo "**************** Traitement de la proglet ${proglet} ****************"
	echo Création du dossier temporaire
	mkdir ../sketchbook_${proglet}
	echo Copie des fichiers
	cp -rf ${proglet} ../sketchbook_${proglet}
	cp -rf javascool-builder.jar ../sketchbook_${proglet}
	cd ../sketchbook_${proglet}
	echo Lancement de javascoolbuilder
	java -jar javascool-builder.jar -q >/dev/null 2>&1
	echo Extraction du jar
	jar xf javascool-personel.jar
	if [ -d org/javascool/proglets/${proglet} ]
	then
		echo Copie des fichiers de la proglet dans web
		cp -rf org/javascool/proglets/${proglet} ../web/proglets
		mv javascool-personel.jar ../web/proglets/javascool-proglet-${proglet}.jar
	else
		echo Erreur ! La proglet ${proglet} n\'a pas pu être compilée
	fi
	cd ..
	echo Suppression du dossier temporaire
	rm -rf sketchbook_${proglet}
	cd sketchbook
done
