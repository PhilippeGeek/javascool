#
# Pb de lock sous linux:
#  Once: s/uucp:x:14:uucp,vthierry/uucp:x:14:uucp,vthierry/ s/lock:x:54:/lock:x:54:vthierry/  in /etc/group
#  After reboot : chgrp lock /var/lock ; chmod 775 /var/lock
#


export CLASSPATH=tmp:RXTXcomm.jar:../../work/dist/javascool-base.jar
export LD_LIBRARY_PATH=../../documents/sketchbook/commSerie/lib

all :
	mkdir -p tmp
	javac -Xlint -d tmp Panel.java Functions.java ConvertisseurPanel.java SerialInterfacePanel.java SerialInterface.java
	java org.javascool.proglets.commSerie.SerialInterfacePanel

clean :
	/bin/rm -rf tmp


