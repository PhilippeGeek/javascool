rm jvsbuilder.log >/dev/null 2>&1
rm summary.log >/dev/null 2>&1

for proglet in `ls -p|grep "/"|sed 's/\/$//'`
do
	status="FAIL"
	echo "**************** Traitement de la proglet ${proglet} ****************"
	echo Création du dossier temporaire
	mkdir ../sketchbook_${proglet} >/dev/null 2>&1
	if [ -d ../sketchbook_${proglet} ]
	then
		echo Copie des fichiers
		cp -rf ${proglet} ../sketchbook_${proglet} >/dev/null 2>&1
		if [ -d ../sketchbook_${proglet}/${proglet} ]
		then
			cp -rf javascool-builder.jar ../sketchbook_${proglet} >/dev/null 2>&1
			if [ -f ../sketchbook_${proglet}/javascool-builder.jar ]
			then
				cd ../sketchbook_${proglet}
				echo Lancement de javascoolbuilder
				echo >>../sketchbook/jvsbuilder.log
				echo >>../sketchbook/jvsbuilder.log
				echo >>../sketchbook/jvsbuilder.log
				echo "**************************** RUNNING JAVASCOOLBUILDER ON PROGLET ${proglet} ****************************">>../sketchbook/jvsbuilder.log
				java -jar javascool-builder.jar -q >>../sketchbook/jvsbuilder.log 2>&1
				if [ -f javascool-personnel.jar ]
				then
					echo Extraction du jar
					jar xf javascool-personnel.jar >/dev/null 2>&1
					if [ -d org/javascool/proglets/${proglet} ]
					then
						echo Copie des fichiers de la proglet dans web
						cp -rf org/javascool/proglets/${proglet} ../web/proglets >/dev/null 2>&1
						mv javascool-personnel.jar ../web/proglets/javascool-proglet-${proglet}.jar >/dev/null 2>&1
						status="OK"
					else
						echo Erreur ! La proglet ${proglet} n\'a pas pu être compilée, voir jvsbuilder.log 1>&2
					fi
					cd ..
					echo Suppression du dossier temporaire
					rm -rf sketchbook_${proglet}
					cd sketchbook
				else
					cd ../sketchbook
					rm -rf ../sketchbook_${proglet}
					echo JavaScoolBuilder a échoué, voir jvsbuilder.log 1>&2
				fi
			else
				rm -rf ../sketchbook_${proglet}
				echo Impossible de copier javascool-builder.jar, JavaScoolBuilder est-il bien compilé ? Abandon 1>&2
			fi
		else
			rm -rf ../sketchbook_${proglet}
			echo Le dossier de la proglet n\'a pas pu être créé, abandon 1>&2
		fi
	else
		echo Le dossier temporaire n\'a pas pu être créé, abandon 1>&2
	fi
	echo -e "${proglet}  \t => \t ${status}">>summary.log
done

echo
echo
echo "**************************** SUMMARY ****************************"
cat summary.log
echo
rm summary.log >/dev/null 2>&1
