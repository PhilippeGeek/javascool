
ok :
	for f in `find src/org/javascool -name .svn -prune -o \( -name '*.*' \)` ;\
	 do if [ -f $$f ] ; then g="../../work/$$f" ;\
           if [ -f $$g ] ; then if [ \! -z "`diff -q $$g $$f`" ] ;\
           then echo "-----------------------------------------"; echo "diff $$g $$f" ; diff $$f $$g ;\
	   fi ; fi ;\
	 fi ; done | tee diff.txt ; ok=1


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
