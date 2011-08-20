
- finir la section dvelopers
- ajouter l acces à ant via show.sh
- ajouter image cycle sur documents/javadoc pour ajout a javadoc et virer de core
- 

#
# Makefile interface avec le ant
#

# Used to use sun sun-jdk instead of open-jdk because of processing and windows platforms
export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)

default :
	ant prun

clean :
	ant clean > /dev/null

svn :
	cd .. ; ant svn

nbe :
	netbeans --jdkhome /usr/java/jdk1.6.0_22 &

pro :
	ant processing
