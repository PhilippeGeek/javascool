dft : run

www : index.html
	firefox $^

run : ./notrejeu.jar
	java -jar $^

./index.html : makefile ./notrejeu.html $(patsubst %.html,%.htm,$(shell find -name '*.html' \! -name 'index.html'))
	echo '<script>location.replace("apropos.htm");</script>' > $@

./%.html ./%.jar ./%.zip : %.jvs 
	../../../../work/lib/jvs2jar.sh $*.jvs

./%.htm : assets/header.html.inc ./%.html assets/trailer.html.inc 
	cat $^ > $@

where = /net/servers/www-sop/websites/science-participative/bahhz

pub : assets $(wildcard *.*)
	mkdir -p $(where) ; cp -r $^ $(where)
	firefox http://www-sop.inria.fr/science-participative/bahhz
