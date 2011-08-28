# vthierry's commands et tbd
### http://code.google.com/p/javascool/wiki/JavaScoolOnGoingWork
### http://code.google.com/p/javascool/issues/list

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

ant :
	ant prun

svn :
	cd .. ; ant svn

nbe :
	netbeans --jdkhome $(JHOME) &

pro :
	for f in ../sketchbook/*/applet/.svn ; do cp -r $$f $$f/../../svn ; done
	ant processing
	for f in ../sketchbook/*/svn ; do mv $$f `echo $$f | sed 's/svn/applet\/.svn/'` ; done
	svn status ../sketchbook
