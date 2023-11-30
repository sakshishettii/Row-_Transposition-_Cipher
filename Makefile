run:
	javac Trans.java
	java Trans 7 3421567 in out enc
	java Trans 7 3421567 out in1 dec
	rm -rf *.class
