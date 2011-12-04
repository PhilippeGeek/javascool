all :
	javac *.java
	rm -f grafikformate.jar; jar cf grafikformate.jar *.class icons ; rm -f *.class
	appletviewer grafikformate.html

zip :
	rm -rf ../.tmp ../grafikformate.zip ; mkdir -p ../.tmp
	cd .. ; svn export grafikformate .tmp/grafikformate
	cd ../.tmp ; zip -9qr ../grafikformate.zip grafikformate
	rm -rf ../.tmp

pub :
	firefox https://wiki.inria.fr/sciencinfolycee/Javascoolgrafikformate




#cnv :
#	for f in *.java ; do mv $$f $$f.save ; iconv -f ISO8859-1 -t UTF-8 $$f.save > $$f ; done

