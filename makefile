app: src/Main.java
	javac -cp "bin/*:src/*" -d bin/ src/*.java
	java -cp bin Main
