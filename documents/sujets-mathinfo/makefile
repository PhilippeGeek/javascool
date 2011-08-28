all : $(patsubst %.xml,%.htm,$(wildcard *.xml)) $(patsubst %.jvs,%.htm,$(wildcard *.jvs))

%.htm : %.xml
	java -jar  ../../work/lib/saxon.jar -o $@ $^ ../../work/src/org/javascool/builder/htm2hml.xslt 

%.htm : %.jvs
	java -jar ../../work/lib/java2html.jar -srcfile ./$^ -targetfile ./$@ 
 

