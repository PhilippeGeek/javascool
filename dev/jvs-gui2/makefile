cmp :
	for f in `find -name '*.java'` ; do if [ -f ../../work/$$f ] ; then if diff $$f ../../work/$$f > /dev/null ; then ok=1; else echo ; echo ">>> $$f" ; echo ; diff $$f ../../work/$$f ; fi ; fi ; done > diff.txt ; ok=1


