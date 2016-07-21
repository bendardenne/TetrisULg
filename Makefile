default: 
	javac *.java */*.java

run:
	java TetrisULg

dist-clean:
	rm *.class
	rm */*.class

tar:
	tar zvc game util control view network *.java Makefile rapport.pdf   -f ppoo_dardenne_wandji.tar.gz
