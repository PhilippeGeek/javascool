#
# Pb de lock sous linux:
# s/uucp:x:14:uucp,vthierry/uucp:x:14:uucp,vthierry/ in /etc/group
# s/lock:x:54:/lock:x:54:vthierry/ in /etc/group
# chgrp lock /var/lock ; chmod 775 /var/lock
#


export CLASSPATH=.:RXTXcomm.jar
export LD_LIBRARY_PATH=../../documents/sketchbook/commSerie/lib

all :
	javac -Xlint SerialInterface.java
	java SerialInterface

clean :
	/bin/rm -f *.class


