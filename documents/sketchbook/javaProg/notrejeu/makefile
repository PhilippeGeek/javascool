
dft : run

www : ./notrejeu.html
	firefox $^

run : ./notrejeu.jar
	java -jar $^

./%.html ./%.jar : %.jvs
	../../../../work/lib/jvs2jar.sh $*.jvs



