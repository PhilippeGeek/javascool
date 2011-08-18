
#
# Makefile de développement de jvs4.alpha.2 à vthierry (à terme seul ant sera utilisé)
#

# Used to use sun sun-jdk instead of open-jdk because of processing and windows platforms
export PATH:=/usr/java/jdk1.6.0_22/bin:$(PATH)

all :
	ant prun

clean :
	ant clean > /dev/null

svn :
	cd .. ; ant svn

nbe :
	@gnome-terminal --hide-menubar --geometry 1x1-1-1 -e "/usr/java/netbeans-7.0/bin/netbeans" &

pro :
	ant processing
