# vthierry's commands

# Using sun-jdk because of processing and windows platforms
export JHOME=/usr/java/jdk1.6.0_22
export PATH:=$(JHOME)/bin:$(PATH)

ant :
#	ant jrun
#	ant prun
	ant webproglets; appletviewer dist/proglets/jar/org/javascool/proglets/tortueLogo/applet-tag.htm 
#	ant javascool-proglets.jar ; java -jar dist/proglets/javascool-proglet-sampleCode.jar 

svn :
	cd .. ; ant svn

nbe :
	netbeans --jdkhome $(JHOME) &

pro :
	ant processing

#vendor=thierry.vieville@inria.fr, ou=javascool.gforge.inria.fr, o=inria.fr, c=fr
#public-key=hello-world
#private-key=mer,d,azof
#	@#Build the raw jar
#	@cd tmp ; jar cfm ../tmp.jar ../lib/js-manifest.mf *
#	@#Sign the jar file
#	@keytool -genkey -alias javascool -validity 3000 -dname cn='$(vendor)' -storepass '$(public-key)' -keypass '$(private-key)' -keystore tmp.key
#	@jarsigner -storepass '$(public-key)' -keypass '$(private-key)' -keystore tmp.key -signedjar $@ tmp.jar javascool
