
dft : run

www : ./notrejeu.html
	firefox $^

run : ./notrejeu.jar
	java -jar $^

./%.html ./%.jar ./%.zip : %.jvs
	../../../../work/lib/jvs2jar.sh $*.jvs

where = /net/servers/www-sop/websites/science-participative/bahhz

pub : ./index.html ./notrejeu.jar ./notrejeu.html ./notrejeu.zip 
	mkdir -p $(where) ; cp $^ $(where)
	firefox http://www-sop.inria.fr/science-participative/bahhz/index.html
