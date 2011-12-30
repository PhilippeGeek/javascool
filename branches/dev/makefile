
ok :
	for f in `find src/org/javascool -name .svn -prune -o \( -name '*.*' \)` ;\
	 do if [ -f $$f ] ; then g="../../work/$$f" ;\
           if [ -f $$g ] ; then if [ \! -z "`diff -q $$g $$f`" ] ;\
           then echo "diff $$g $$f" ;\
           #if [ -z "`echo $$f | grep '/gui'`" ] ; then diff $$f $$g ; echo ; fi ;\
	   fi ; fi ;\
	 fi ; done ; ok=1


ok2 :
	for f in `find src/org/javascool -name .svn -prune -o \( -name '*.*' \)` ;\
	 do if [ -f $$f ] ; then g="../../work/$$f" ;\
           if [ \! -f $$g ] ;\
           then echo "rm $$f # car ajouté" ;\
	   fi ;\
	 fi ; done ; ok=1
	cd ../../work ; for f in `find src/org/javascool -name .svn -prune -o \( -name '*.*' \)` ;\
	do if [ -f $$f ] ; then g="../branches/dev/$$f" ;\
           if [ \! -f $$g ] ;\
           then echo "cp $$f $$g # car disparu" ;\
	   fi ;\
	fi ; done ; ok=1
