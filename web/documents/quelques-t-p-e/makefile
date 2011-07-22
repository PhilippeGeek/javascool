
all : $(patsubst %.xml,%.html,$(wildcard *.xml))
	$(MAKE) -C ../../.. wpub

%.html : %.xml index.xsl
	java -jar ../../../lib/saxon.jar -o $@ $^
