
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
	netbeans &

pro :
	ant processing

#############################################################################################

# Travail sur les pages du site
