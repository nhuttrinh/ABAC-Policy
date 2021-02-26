JFLAGS=-g
JC=javac
MAIN=Main
ZIP=abacmonitor.zip
DIR=$(PWD)
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	ABACMonitor.java \
	ABACPolicyLoader.java \
	Main.java
all: $(CLASSES) classes
	echo "java -cp '$(DIR)' $(MAIN)" '$$1' > abacmonitor
	chmod 777 abacmonitor
$(CLASSES): unzip
unzip: $(ZIP)
	unzip $(ZIP)
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class abacmonitor *.java
	

