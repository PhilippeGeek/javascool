
cmp : 
	rm -f htm2hml.java ; $(MAKE) htm2hml.java
	javac -cp ../../../work/dist/javascool-base.jar Htm2Hml.java htm2hml.java 
	appletviewer htm2hml.php

%.java : %.xslt
	echo "class $* { public static String data = " >> $@
	cat $^ | sed 's/"/\\"/g' | sed 's/\(.*\)/"\1"+/' >> $@
	echo " \"\"; }" >> $@

all :
	cd ../.. ; ant wpublish

clean :
	rm -f *.class

